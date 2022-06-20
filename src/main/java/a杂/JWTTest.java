package a杂;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-07 20:00
 */
public class JWTTest {

    private static String signature;

    public static void main(String[] args) {


        String token = createToken(signature,5000L);

        System.out.println(token);

        parseToken(token, signature);

        boolean b = checkTokenIsValid(token);
    }

    public static String createToken(String signature,Long expiration) {
        return Jwts.builder()
                    //header
                    .setHeaderParam("alg", "HS256")
                    //payload
                    .claim("username", "tom")
                    .claim("role", "admin")
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))  //5s后到期
                    .setId(UUID.randomUUID().toString())
                    //signature
                    .signWith(SignatureAlgorithm.HS256, signature)
                    .compact();
    }

    public static void parseToken(String token, String signature) {
        Claims claims = Jwts.parser() //
                .setSigningKey(signature).parseClaimsJws(token).getBody();

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
}
