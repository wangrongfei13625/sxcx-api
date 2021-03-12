package com.huaxin.member.service.impl;

import com.huaxin.member.mapper.*;
import com.huaxin.member.model.ExamInfo;
import com.huaxin.member.service.ExamInfoService;
import com.huaxin.member.util.PrefixExpression;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExamInfoServiceImpl implements ExamInfoService {

    @Autowired
    private ExamInfoMapper examInfoMapper;

    @Autowired
    private ManageTypeInfoMapper manageTypeInfoMapper;


    @Autowired
    private ExamScoresInfoMapper examScoresInfoMapper;


    public List<Map<String,Object>> findList(Map<String,Object> params){

        //查询题库中题目 如果题目被 该用户 做过， 则显示 做过结果
        if(params.get("libraryType").toString().equals("1")){
            //定量
            List<Map<String,Object>> rationQuestion = examInfoMapper.findListOfRationQuestion(params);
            for(Map<String,Object> confidence:rationQuestion){

                Map<String,Object> confidenceMap = new HashMap<>();

                // 根据条件 查询出 需要 展示的题目
                params.put("libraryId",confidence.get("id").toString());
                List<Map<String,Object>> list  = examInfoMapper.findList(params);
                if(list!=null && list.size()>0){
                    confidenceMap.put("confidenceValue",list.get(0).get("confidenceValue").toString());
                }else{
                    confidenceMap.put("confidenceValue","");
                }
                confidenceMap.put("rationId",confidence.get("id").toString());
                List<Map<String,Object>> answer = examInfoMapper.findListOfRationConfidence(confidenceMap);
                confidence.put("confidence",answer);
            }
           return rationQuestion;
        }else {
            //定性
            List<Map<String,Object>> qualitative = examInfoMapper.findListOfQualitative(params);
            for(Map<String,Object> confidence:qualitative){
                // 根据条件 查询出 需要 展示的题目
                params.put("libraryId",confidence.get("id").toString());
                List<Map<String,Object>> list  = examInfoMapper.findList(params);
                if(list!=null && list.size()>0){
                    confidence.put("confidence",list.get(0).get("answer").toString());
                }else{
                    confidence.put("confidence","");
                }

            }
            return qualitative;
        }

    }


    /**
     * 提交
     * @param params
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveExamInfo(List<ExamInfo> params){

        String Formula="";
        Double confidence=0.0;
        String formula = "";
        int i=0;

        // 总题数
        Map<String,Object> qualitativeMap = new HashMap<>();

        Map<String,Object> managesMap = new HashMap<>();
        for(ExamInfo examInfo : params){

            if(examInfo.getId()!=null){
                examInfoMapper.updateExamInfo(examInfo);
            }else{
                examInfoMapper.saveExamInfoId(examInfo);
            }

            // 定量
            if(examInfo.getLibraryType().toString().equals("1")){
                String libraryId = examInfo.getLibraryId().toString();
                confidence +=Double.parseDouble(examInfo.getConfidenceValue());
                String num = examInfo.getAnswer();
                Map<String,Object>  ration = new HashMap<>();
                ration.put("libraryId",libraryId);

                List<Map<String,Object>> rationQuestion = examInfoMapper.findListOfRationQuestion(ration);

                // 取出变量代码  查询得到 计算公式 E7 根据E7  查询数据字典 得到  计算公式
                String rationCode = rationQuestion.get(0).get("rationCode").toString();

                Map<String,Object> manageMap = new HashMap<>();
                manageMap.put("rationCode",rationCode);
                manageMap.put("manageType",4);
                List<Map<String,Object>> manageList = manageTypeInfoMapper.findList(manageMap);



                //通过取值方位取值范围 得到的值 为 0 ， 不需要计算  将0 作为最后 结果存入表中 反之
                Map<String,Object> pidMap = new HashMap<>();
                pidMap.put("id",manageList.get(0).get("pid"));
                List<Map<String,Object>> pidList = manageTypeInfoMapper.findList(pidMap);


                manageMap.put("rationCode",pidList.get(0).get("manageName"));
                manageMap.put("manageType",5);
                // 根据 manageName  manageType 得到取值范围 中的计算公式（指标公式）
                List<Map<String,Object>> rangeList = manageTypeInfoMapper.findList(manageMap);


                for(Map<String,Object> range:rangeList){

                    boolean flag = isNumOfTrue(range.get("manageRange").toString(),num);//25<x&&x<=100
                    if(flag){
                        if(StringUtils.isEmpty(formula)){
                            formula = range.get("remark").toString();//1.33*X-33.33
                        }
                    }
                }

                Map<String,Object> objectMap = new HashMap<>();
                String manageName  = pidList.get(0).get("manageName").toString();
                objectMap.put("manageName",manageName);
                objectMap.put("userId",examInfo.getUserId());
                objectMap.put("libraryType",examInfo.getLibraryType().toString());
                objectMap.put("examInfoId",examInfo.getId());
                objectMap.put("manageId",examInfo.getManageId());

                i++;
                String expression ="";
                if(!Formula.equals("")){
                    expression = Formula;
                }else{
                    expression = pidList.get(0).get("remark").toString(); // 得到 计算公式 E8/E7*100
                }

                String formulas = expression.replace(rationCode,num);
                String express = formulas.replaceAll("[+ \\- * / ]","").trim();
                // 剔除  加减乘除 后 得到的结果全为 数字的话， 认为改公式已经替换完成， 可以进行计算，
                // 并将计算得到的结果 存入数据库中
                if(!isNumeric(express)){// true / false
                    Formula = formulas;
                }else{
                    //将得到的计算公式 进行计算 得出值 并存入表中 进行 最后统计计算
                    String infixExpression = PrefixExpression.toInfixExpression(formulas);
                    Double value  = PrefixExpression.reversePolishNotation(infixExpression);

                    //得到最后值
                    BigDecimal endValue = PrefixExpression.getResult(formula.replace("X",String.valueOf(value)));
                    //Double endValue  = PrefixExpression.reversePolishNotation(endExpression);

                    // 根据libraryIds 算出 置性度 平均值
                    Double avg = confidence/i;

                    //fw1 =  指标 * 置性度 * 权重比例
                    // 查询得到权重比例

                    pidMap.put("id",pidList.get(0).get("pid"));
                    List<Map<String,Object>> weightList = manageTypeInfoMapper.findList(pidMap);

                    //FW1 的值
                    String finalValue = endValue.multiply(new BigDecimal(avg)).multiply(new BigDecimal(weightList.get(0).get("remark").toString())).setScale(2, RoundingMode.HALF_UP).toPlainString();

                    objectMap.put("finalValue",finalValue);

                    examScoresInfoMapper.saveExamScoresInfo(objectMap);

                    confidence=0.0;
                    formula = "";
                    Formula="";
                    i=0;

                }
            }
            else {//定性
                // 需要得到 总题数  置性度 之和  权重比例  得到 单分类得分
                if(qualitativeMap != null&&qualitativeMap.size()>0){

                }else{
                    qualitativeMap.put("manageId",examInfo.getManageId());// 类别 --服务类
                    qualitativeMap.put("questionnaireType",examInfo.getQuestionnaireType()); // 类别 --营业服务
                    qualitativeMap.put("edition",examInfo.getEdition());
                }


                List<Map<String,Object>> qualitative = examInfoMapper.findListOfQualitative(qualitativeMap);

                Integer numOfQuestions =  qualitative.size();

                // 置性度 之和
                confidence +=Double.parseDouble(examInfo.getConfidenceValue());

                // 权重比例查询得到
                if(managesMap!=null && managesMap.size()>0){

                }else{
                    managesMap.put("id",examInfo.getQuestionnaireType());
                }


                List<Map<String,Object>> manageList =  manageTypeInfoMapper.findList(managesMap);

                String  finalValue = new BigDecimal(confidence).divide(new BigDecimal(numOfQuestions))
                        .multiply(new BigDecimal("100")).multiply(new BigDecimal(manageList.get(0).get("remark").toString()))
                        .setScale(2, RoundingMode.HALF_UP).toPlainString();



                i++;

                if(i== params.size()){
                    Map<String,Object> objectMap = new HashMap<>();
                    String manageName  = manageList.get(0).get("manageName").toString();
                    objectMap.put("manageName",manageName);
                    objectMap.put("userId",examInfo.getUserId());
                    objectMap.put("libraryType",examInfo.getLibraryType().toString());
                    objectMap.put("examInfoId",examInfo.getId());
                    objectMap.put("finalValue",finalValue);
                    objectMap.put("manageId",examInfo.getManageId());
                    objectMap.put("questionnaireId",examInfo.getQuestionnaireType());

                    examScoresInfoMapper.saveExamScoresInfo(objectMap);
                    confidence=0.0;
                    i=0;
                }




            }

        }

    }

    @Override
    public Map<String,Object> countExam(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        // 根据 loginName 版本号  时间  查询得到所有的分数， 并 统计出 分数  总分

        //定量-服务类 服务类 （20%） 运行类 （45%） 资源类 （5%） 资产类 （5%） 财经类 （20%） 人事类
        params.put("manageId","1");
        params.put("libraryType","1");
        List<Map<String,Object>> list = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal serviceNum = new BigDecimal(list.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.7")).setScale(2, RoundingMode.HALF_UP);
        map.put("serviceNum_ration",serviceNum);
        //运行类
        params.put("manageId","2");
        List<Map<String,Object>> list2 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal runNum = new BigDecimal(list2.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.7")).setScale(2, RoundingMode.HALF_UP);
        map.put("runNum_ration",runNum);

        //资源类
        params.put("manageId","3");
        List<Map<String,Object>> list3 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal resourcesNum = new BigDecimal(list3.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.6")).setScale(2, RoundingMode.HALF_UP);
        map.put("resourcesNum_ration",resourcesNum);


        //资产类
        params.put("manageId","4");
        List<Map<String,Object>> list4 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal assetsNum = new BigDecimal(list4.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.6")).setScale(2, RoundingMode.HALF_UP);
        map.put("assetsNum_ration",assetsNum);

        //人事类
        params.put("manageId","5");
        List<Map<String,Object>> list5 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal personnelNum = new BigDecimal(list5.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.7")).setScale(2, RoundingMode.HALF_UP);
        map.put("personnelNum_ration",personnelNum);

        //财经类
        params.put("manageId","6");
        List<Map<String,Object>> list6 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal financeNum = new BigDecimal(list6.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.HALF_UP);
        map.put("financeNum_ration",financeNum);



        //定性-服务类
        params.put("manageId","1");
        params.put("libraryType","0");
        List<Map<String,Object>> list_1 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal serviceNum_1 = new BigDecimal(list_1.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.HALF_UP);
        map.put("serviceNum_qualitative",serviceNum_1);

        BigDecimal service_count = serviceNum.add(serviceNum_1).multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.HALF_UP);
        map.put("service_count",service_count);



        //运行类
        params.put("manageId","2");
        List<Map<String,Object>> list_2 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal runNum_1 = new BigDecimal(list_2.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.HALF_UP);
        map.put("runNum_qualitative",runNum_1);

        BigDecimal runNum_count = runNum.add(runNum_1).multiply(new BigDecimal("0.45")).setScale(2, RoundingMode.HALF_UP);
        map.put("runNum_count",runNum_count);

        //资源类
        params.put("manageId","3");
        List<Map<String,Object>> list_3 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal resourcesNum_1 = new BigDecimal(list_3.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.4")).setScale(2, RoundingMode.HALF_UP);
        map.put("resourcesNum_qualitative",resourcesNum_1);

        BigDecimal resourcesNum_count = resourcesNum.add(resourcesNum_1).multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
        map.put("resourcesNum_count",resourcesNum_count);

        //资产类
        params.put("manageId","4");
        List<Map<String,Object>> list_4 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal assetsNum_1 = new BigDecimal(list_4.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.4")).setScale(2, RoundingMode.HALF_UP);
        map.put("assetsNum_qualitative",assetsNum_1);

        BigDecimal assetsNum_count = assetsNum.add(assetsNum_1).multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
        map.put("assetsNum_count",assetsNum_count);


        //人事类
        params.put("manageId","5");
        List<Map<String,Object>> list_5 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal personnelNum_1 = new BigDecimal(list_5.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.HALF_UP);
        map.put("personnelNum_qualitative",personnelNum_1);

        BigDecimal personnelNum_count = personnelNum.add(personnelNum_1).multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
        map.put("personnelNum_count",personnelNum_count);


        //财经类
        params.put("manageId","6");
        List<Map<String,Object>> list_6 = examScoresInfoMapper.findSumOfUserId(params);
        BigDecimal financeNum_1 = new BigDecimal(list_6.get(0).get("finalValue").toString()).multiply(new BigDecimal("0.7")).setScale(2, RoundingMode.HALF_UP);
        map.put("financeNum_qualitative",financeNum_1);

        BigDecimal financeNum_count = financeNum.add(financeNum_1).multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.HALF_UP);
        map.put("financeNum_count",financeNum_count);





        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateExamInfo(List<ExamInfo> params){
        for(ExamInfo examInfo : params){
            if(examInfo.getId()!=null){
                examInfoMapper.updateExamInfo(examInfo);
            }else{
                examInfoMapper.saveExamInfoId(examInfo);
            }
        }
    }


    //判断数值 是否在区间

    public Boolean isNumOfTrue(String expression,String num){
        NashornScriptEngineFactory scriptEngineManager = new NashornScriptEngineFactory();
        ScriptEngine scriptEngine = scriptEngineManager.getScriptEngine();
        expression = replaceAll(expression,"x",num);
        String result="";
        try {
            result= String.valueOf(scriptEngine.eval(expression));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(result);

    }

    public  String replaceAll(String input, String regex, String replacement) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        String result = m.replaceAll(replacement);
        return result;
    }

    public boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }



    public static void main(String[] args) {
        //String str = "E8/E7*100";
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        String str="8/7*100-100+5";
        String express = str.replaceAll("[+ \\- * / ]","").trim();
        Matcher isNum = pattern.matcher(express);

        System.out.println(express);
        System.out.println( isNum.matches());

    }













    }
