package com.huaxin.member.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PrefixExpression {

    /**
     * 将中缀表达式转化为前缀表达式
     * @param input 要被转化的中缀表达式
     */
    public static void  toPrefixExpression(String input){
        int len = input.length();//中缀表达式的长度
        //System.out.println("读到的字符串为："+input+"长度为："+ len);
        char c,tempChar;//这两个都是中间变量，c用来存放循环中的对应位置的字符，
        Stack<Character> s1 = new Stack<Character>();//实例化一个符号栈
        Stack<Integer> s2 = new Stack<Integer>();//实例化一个数字栈
        Stack< Object> expression = new Stack< Object>();  //实例化一个前缀表达式的栈
        //从右至左扫描表达式
        for (int i=len-1; i>=0; --i) {
            c = input.charAt(i);
            //判断读取的是不是数字，是的话将数字压入操作数栈和表达式栈
            if (Character.isDigit(c)) {
                String s = String. valueOf(c);
                //再转换成 Int类型：
                int j = Integer.parseInt(s);
                s2.push(j);
                expression.push(j);
            } else if (isOperator(c)) {
                //如果当前字符为运算符（包含括号）
                while (!s1.isEmpty()
                        && s1.peek() != ')'
                        && priorityCompare(c, s1.peek()) < 0) {
                    //当前运算符栈不为空且要运算符栈顶运算符不是右括号且当前运算符的优先级比运算符栈顶运算符的优先级低，
                    //则将运算符栈栈顶元素拿出来与操作数栈的两个栈顶元素进行运算并把运算结果压入操作数栈
                    expression.push(s1.peek());
                    s2.push( calc(s2.pop(), s2.pop(), s1.pop()));
                }
                s1.push(c);
            } else if (c == ')' ) {
                //因为我们是从右至左来扫描中缀表达式的所以对于一个“（）”而言一定是先读到右边的括号
                s1.push(c);
            } else if (c == '(' ) {
                //如果是左括号“(”，则依次弹出S1栈顶的运算符，并压入表达式栈，直到遇到左括号为止，此时将这一对括号丢弃；
                while ((tempChar=s1.pop()) != ')' ) {
                    expression.push(tempChar);
                    s2.push( calc(s2.pop(), s2.pop(), tempChar));
                    if (s1.isEmpty()) {
                        throw new IllegalArgumentException(
                                "bracket dosen't match, missing right bracket ')'.");
                    }
                }
            } else if (c == ' ' ) {
                // 如果表达式里包含空格则不处理空格
            } else {
                throw new IllegalArgumentException(
                        "wrong character '" + c + "'" );
            }
        }
        while (!s1.isEmpty()) {
            tempChar = s1.pop();
            expression.push(tempChar);
            s2.push( calc(s2.pop(), s2.pop(), tempChar));
        }
        while (!expression.isEmpty()) {
            System. out .print(expression.pop() + " " );
        }
        int result = s2.pop();
        if (!s2.isEmpty())
            throw new IllegalArgumentException( "input is a wrong expression.");
        System. out .println();
        System. out .println("计算结果为： " + result);

    }
    /**
     * 对给定的两个操作数及操作符进行计算
     * @param num1
     * @param num2
     * @param op
     * @return 返回计算整型结果
     */
    private static Integer calc( int num1, int num2, char op) {

        switch (op) {
            case '+' :
                return num1 + num2;
            case '-' :
                return num1 - num2;
            case '*' :
                return num1 * num2;
            case '/' :
                if (num2 == 0) throw new IllegalArgumentException("divisor can't be 0." );
                return num1 / num2;
            default :
                return 0; // will never catch up here
        }

    }
    /**
     * 判断字符是否是操作符的方法
     * @param c
     * @return
     */
    private static boolean isOperator( char c) {
        return (c=='+' || c=='-' || c=='*' || c=='/' );
    }
    /**
     * 判断运算符优先级的方法
     * @param op1
     * @param op2
     * @return 返回值如果是:
     *          - 1则表示op1的优先级低于op2,
     *           0 则表示op1的优先级等于op2，
     *           1则表示op1的优先级高于op2。
     */
    private static int priorityCompare( char op1, char op2) {
        switch (op1) {
            case '+' : case '-':
                return (op2 == '*' || op2 == '/' ? -1 : 0);
            case '*' : case '/':
                return (op2 == '+' || op2 == '-' ? 1 : 0);
        }
        return 1;
    }



    //中缀表达式转后缀表达式
    public static String toInfixExpression(String expression) {
        int index = 0;
        Stack<String> s1 = new Stack<>();
        String s2 = "";
        while (index < expression.length()) {
            char temp = expression.charAt(index);
            //遍历到数字直接入s2，并index++
            if (temp > 47 && temp < 58) {
                String num = "";
                while (index < expression.length() && expression.charAt(index) > 47 && expression.charAt(index) < 58) {
                    num += expression.charAt(index);
                    index++;
                }
                s2 += num + " ";
            } else if (temp == '+' || temp == '-' || temp == '*' || temp == '/' || temp == '(') {
                //遍历到运算符判断优先级
                //这里之所以加入(处理是因为左括号也会入栈，优先级比较可能出现运算符与括号比较
                String symbol = temp + "";
                boolean flag = true;
                while (!s1.isEmpty() && !symbol.equals("(") && priority(symbol) <= priority(s1.peek())) {
                    s2 = s2 + s1.pop() + " ";
                    flag = false;
                }
                s1.add(symbol);
                index++;
            } else if (temp == ')') {
                //对于右括号，我们只需要将栈中左括号以上运算符出s1栈到s2中即可
                if (!s1.peek().equals("(")) {
                    s2 += s1.pop() + " ";
                }
                //注意左括号本身也需要出栈
                s1.pop();
                index++;
            } else {
                throw new RuntimeException("请输入规范的字符串");
            }
        }
        while (!s1.isEmpty()) {
            s2 += s1.pop() + " ";
        }
        return s2;
    }

    //后缀表达式运算
    public static Double reversePolishNotation(String expression) {
        String[] elements = expression.split(" ");
        Stack<Double> stack = new Stack<>();
        for (String element : elements) {
            if (element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/")) {
                Double a = stack.pop();
                Double b = stack.pop();
                Double result = calculator(a, b, element);
                stack.add(result);
            } else if (element.matches("\\d+")) {
                stack.add(Double.parseDouble(element));
            } else {
                throw new RuntimeException("字符串不符合规范");
            }
        }
        return stack.peek();
    }

    private static double  calculator(double a, double b, String element) {
        switch (element) {
            case "+":
                return a + b;
            case "-":
                return b - a;
            case "*":
                return a * b;
            case "/":
                return b / a;
        }
        throw new RuntimeException("操作符有误");
    }

    public static int priority(String ch) {

        if (ch.equals("*") || ch.equals("/")) {
            return 1;
        }
        if (ch.equals("+") || ch.equals("-")) {
            return 0;
        }
        //因为如果遇见左括号需要直接将运算符入栈，所以将左括号优先级设为-1
        if (ch.equals("(")) return -1;
        throw new RuntimeException("符号不正确");
    }


    public static void main(String[] args) {
        String expression = "0*150.0-33.33";
        BigDecimal infixExpression = getResult(expression);
        System.out.println(infixExpression);
        //System.out.println(changeInfixExpressionToPostfixExpression(infixExpression));



    }


    public static BigDecimal getResult(String input){
        //规范输入形式,避免用户输入中文括号
        input=input.replaceAll("（","(");
        input=input.replaceAll("）",")");
        //对输入公式,按符号/数字,用空格分开,以便后面分组
        String[] inputs=input.split("");
        String format="";
        for (int i=0;i<inputs.length;i++){
            if (inputs[i].equals(" ")){
                continue;
            }else if (inputs[i].equals("(")||inputs[i].equals(")")||inputs[i].equals("+")||inputs[i].equals("-")||inputs[i].equals("*")||inputs[i].equals("/")){
                format+=" "+inputs[i]+" ";
            }else {
                format+=inputs[i];
            }
        }
        List<String> strings=changeInfixExpressionToPostfixExpression(format);
        Stack<String> stack=new Stack<String>();
        for (int i=0;i<strings.size();i++){
            if (strings.get(i).equals("+")){
                BigDecimal a=new BigDecimal(stack.pop());
                BigDecimal b=new BigDecimal(stack.pop());
                stack.add(b.add(a).toString());
            }else if (strings.get(i).equals("-")){
                BigDecimal a=new BigDecimal(stack.pop());
                BigDecimal b=new BigDecimal(stack.pop());
                stack.add(b.subtract(a).toString());
            }else if (strings.get(i).equals("*")){
                BigDecimal a=new BigDecimal(stack.pop());
                BigDecimal b=new BigDecimal(stack.pop());
                stack.add(b.multiply(a).toString());
            }else if (strings.get(i).equals("/")){
                BigDecimal a=new BigDecimal(stack.pop());
                BigDecimal b=new BigDecimal(stack.pop());
                //这里的1000是做除法以后计算的精确位数,就算1000位也并不会拖慢程序速度,一个公式0.01秒内就能算完,后面的是除不尽的四舍五入
                stack.add(b.divide(a,1000,BigDecimal.ROUND_HALF_DOWN).toString());
            }else {
                stack.add(strings.get(i));
            }
        }
        //返回的时候格式化一下,取四舍五入小数点后两位
        return new BigDecimal(stack.pop()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }


    /**
     * 这个方法呢,就是将输入的中缀表达式转成后缀表达式:
     */


    public static List changeInfixExpressionToPostfixExpression(String input){
        List<String> resultList=new ArrayList<String>();
        Stack<String> tempStack=new Stack<String>();
        String[] splitArray=input.split(" ");
        for (int i=0;i<splitArray.length;i++){
            if (splitArray[i].equals("")){
                continue;
            }
            //如果字符是右括号的话,说明前面一定有过左括号,将栈里第一个左括号之前全部添加到List里
            if (splitArray[i].equals(")")){
                while (!tempStack.peek().equals("(")){
                    resultList.add(tempStack.pop());
                }
                tempStack.pop();//去除前面的左括号
            }else if (splitArray[i].equals("(")){
                //如果是左括号,那么直接添加进去
                tempStack.add("(");
            }else if (splitArray[i].equals("+")||splitArray[i].equals("-")){
                //如果是加减号,还需要再判断
                if (tempStack.empty()||tempStack.peek().equals("(")){
                    tempStack.add(splitArray[i]);
                }else if (tempStack.peek().equals("+")||tempStack.peek().equals("-")){
                    //读临时栈里的顶部数据,如果也是加减就取出来一个到结果列,这个放临时栈,如果是乘除就开始取到右括号为止
                    resultList.add(tempStack.pop());
                    tempStack.add(splitArray[i]);
                }else {
                    while (!tempStack.empty()){
                        if (tempStack.peek().equals("(")){
                            break;
                        }else {
                            resultList.add(tempStack.pop());
                        }
                    }
                    tempStack.add(splitArray[i]);
                }
            }else if (splitArray[i].equals("*")||splitArray[i].equals("/")){
                //如果是乘除
                if (!tempStack.empty()){
                    //判断临时栈里栈顶是啥,如果是乘除,取一个出来到结果列,添这个进临时栈
                    if (tempStack.peek().equals("*")||tempStack.peek().equals("/")){
                        resultList.add(tempStack.pop());
                    }
                }
                tempStack.add(splitArray[i]);
            }else {
                //说明是非符号,都添加进去
                resultList.add(splitArray[i]);
            }
        }
        //遍历完了,把临时stack里的东西都加到结果stack里去
        while (!tempStack.empty()){
            resultList.add(tempStack.pop());
        }
        return resultList;
    }




}
