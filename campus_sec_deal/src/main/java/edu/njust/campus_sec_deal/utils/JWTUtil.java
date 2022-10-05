/**
 * @title: JWTUtil
 * @date: 2022/9/19 18:50
 * @projectName: campus_sec_deal
 * @author: Colyn
 * @builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;


@Component
public class JWTUtil {
    /**
     * 过期时间，单位：秒，默认半小时过期
     **/
    private static final int EXPIRATION = 7;
    /**
     * 密钥，一般长度较长，内容较复杂
     **/
    private static final String SECRET = "d4e131a2a6f543c8a4995d7576de58f2";

    private JWTUtil() {
    }
    /**
     * 生成token      header.payload.signature
     * @param map 用户信息，以 Map<String, String> 类型封装
     * @return token字符串
     */
    public static String getToken(Map<String, String> map) {
        System.out.println("----------------------------------------\n密钥:"+SECRET+"\n-------------------------------------");
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, EXPIRATION);     // 默认7天过期

        //创建 JWTBuilder
        JWTCreator.Builder builder = JWT.create();

        //header不写则使用默认值
        // payload
        map.forEach(builder::withClaim);

        String token = builder
                .withExpiresAt(instance.getTime())      //过期时间
                .sign(Algorithm.HMAC256(SECRET));         //签名算法

        return token;
    }

    /**
     * 验证token是否合法，若不合法则会抛出异常
     * @param token token字符串
     */
    public static String verifyToken(String token, String userID) {
        try {
            DecodedJWT token_value = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return token_value.getClaim(userID).asString();
        } catch (JWTVerificationException | IllegalArgumentException e) {
            e.printStackTrace();
            //若token校验失败，则返回null
            return null;
        }

    }

    /**
     * 获取token的信息，通过调用 DecodedJWT 的 get 方法，可以得到 token 的各种信息
     * 该方法也可以和验证 token 方法合并
     * @param token token字符串
     * @return DecodedJWT
     */
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
}
