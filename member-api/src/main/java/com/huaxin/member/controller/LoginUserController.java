package com.huaxin.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.member.model.LoginUser;
import com.huaxin.member.service.LoginUserService;
import com.huaxin.member.util.base.Result;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginUserController {

    private Logger logger = LogManager.getLogger(this.getClass());



    @Autowired
    private LoginUserService loginUserService;

    @Value("${jwt.secret}")
    private String secret;

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
        LoginUser user = loginUserService.login(loginName, pwd);
        Result result = new Result();
        if (null != user) {
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
                Map<String,Object> map = new HashMap<>();
                map.put("loginName", s);
                map.put("session", loginUser);
                proxy.add(map);
            }
            resultMap.put("proxy", proxy);
            result.setData(resultMap);

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


}
