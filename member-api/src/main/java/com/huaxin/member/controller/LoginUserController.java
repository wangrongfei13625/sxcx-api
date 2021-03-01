package com.huaxin.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huaxin.common.Constants;
import com.huaxin.member.model.LoginUser;
import com.huaxin.member.service.LoginUserService;
import com.huaxin.member.util.base.Result;
import com.huaxin.member.util.base.ResultCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@RestController
@RequestMapping("/login")
public class LoginUserController {

    private Logger logger = LogManager.getLogger(this.getClass());



    @Autowired
    private LoginUserService loginUserService;

    @Value("${jwt.secret}")
    private String secret;


    /**
     * 查询未分配给该角色的用户
     * @param params
     * @return
     */
    @RequestMapping(value = "/findNotOfRoleId", method = RequestMethod.POST)
    public Result findNotOfRoleId(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            //String roleId = params.get("roleId").toString();
            List<Map<String,Object>> list = loginUserService.findNotOfRoleId(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 查询分配给该角色的用户
     * @param params
     * @return
     */
    @RequestMapping(value = "/findOfRoleId", method = RequestMethod.POST)
    public Result findOfRoleId(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            String roleId = params.get("roleId").toString();
            List<Map<String,Object>> list = loginUserService.findOfRoleId(roleId);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询用户
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = loginUserService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增用户
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveHxUser", method = RequestMethod.POST)
    public Result saveHxUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            loginUserService.saveHxUser(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 新增/更新用户
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result updateUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            loginUserService.updateUser(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 删除用户
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public Result deleteUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            loginUserService.deleteUser(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    /**
     * 登录
     *
     * @param params
     * @param request
     * @return @
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String loginName = params.get("loginName").toString();
        String pwd = params.get("pwd").toString();
        //String pwdMD5 = new SimpleHash("SHA-1", Constants.SESSION_SALT, pwd).toString();
        // 根据 用户名查询得到 是否有该用户
        LoginUser user = loginUserService.login(loginName, pwd);
        Result result = new Result();
        if (null != user) {
            if(pwd.equals(user.getLoginPassword())){
                HttpSession session = request.getSession();
                String token = generateToken(user);
                user.setToken(token);
                session.setAttribute("currentUser", user);
                session.setMaxInactiveInterval(Integer.MAX_VALUE);
                user.setLoginPassword(null);

                List<String> accountList = new ArrayList<>();
                accountList.add(loginName);

                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("loginName", loginName);
                resultMap.put("key", loginName);

                List<Map<String,Object>> proxy = new ArrayList<>();
                for (String s : accountList) {
                    LoginUser loginUser = loginUserService.findByLoginName(s);
                    loginUser.setToken(token);
                    Map<String,Object> map = new HashMap<>();
                    map.put("loginName", s);
                    map.put("session", loginUser);
                    proxy.add(map);
                }
                resultMap.put("proxy", proxy);
                result.setData(resultMap);

            }else{
                result.setCode(500);
                result.setMsg("密码错误！");
            }
        }else{
            result.setCode(500);
            result.setMsg("用户名不存在！");
        }
        return result;
    }


    private String generateToken(LoginUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("loginName", user.getLoginName());
        claims.put("userName", user.getUserName());
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
        logger.error(token);
        return token;
    }

    private static Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        return calendar.getTime();
    }


    /**
     * 重置密码
     *
     * @param params
     * @return @
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public Result resetPwd(@RequestBody Map<String, Object> params) {
        String loginName = params.get("loginName").toString();

        String pwdMD5 = new SimpleHash("SHA-1", Constants.SESSION_SALT, "123456").toString();
        Result result = new Result();
        try{
            // 根据 用户名查询得到 是否有该用户
            LoginUser user = loginUserService.login(loginName, pwdMD5);
            if (null != user) {
                params.put("loginPassword",pwdMD5);
                loginUserService.updateUser(params);
            } else {
                result.setMsg("用户名不存在，请注册用户！");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
