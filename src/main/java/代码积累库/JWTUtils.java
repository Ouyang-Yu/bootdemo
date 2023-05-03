package 代码积累库;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-07 20:00
 */
public class JWTUtils {

    private static String signature;



    public static String createToken(Map<String,Object> claims) {
        return Jwts.builder()
                //header
              //  .setHeaderParam("alg", "HS256")
                //payload 包括claims expiration id subject
                //.setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() +5*1000 ))  //5s后到期
                //signature
                .signWith(SignatureAlgorithm.HS256, signature) //签名加盐
                .compact();//组合三个部分
    }

    public static String parseToken(String token,String key) {
        Claims claims = Jwts.parser() //
                .setSigningKey(signature)
                .parseClaimsJws(token)
                .getBody();

        return Optional.ofNullable(claims.get(key).toString()).orElse("");
    }

    public static boolean checkTokenIsValid(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        } catch (Exception e) {
            return false; //如果token 解析失败 这说明无效  不需要关心里面的内容
        }
        return true;
    }

    /**
     *  获取原来claims,并以此重新重新生成
      * @param token
     * @return
     */
    static String refreshToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signature)
                .parseClaimsJws(token)
                .getBody();
        return createToken(claims);
    }
}
