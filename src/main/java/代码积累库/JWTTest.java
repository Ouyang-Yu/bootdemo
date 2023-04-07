package 代码积累库;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-07 20:00
 */
public class JWTTest {

    private static String signature;



    public static String createToken(Map<String,Object> claims) {
        return Jwts.builder()
                //header
                .setHeaderParam("alg", "HS256")
                //payload 包括claims expiration id subject
//                .claim("username", "tom")
//                .claim("role", "admin") //也可以setClaims(map)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() +5*1000 ))  //5s后到期
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256, signature) //签名加盐
                .compact();//组合三个部分
    }

    public static void parseToken(String token, String signature) {
        Claims claims = Jwts.parser() //
                .setSigningKey(signature)
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getExpiration());
        System.out.println(claims.getId());
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
