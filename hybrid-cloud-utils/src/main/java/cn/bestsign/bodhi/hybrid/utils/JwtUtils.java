package org.github.bodhi.hybrid.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class JwtUtils {

    private static final String ISS = "bodhi";

    public static String createJwtToken(Map<String, Object> payLoad, String secret, long expireTime) throws UnsupportedEncodingException {
        // HMAC
        Algorithm algorithm = Algorithm.HMAC256(secret);
        long expMillis = System.currentTimeMillis() + expireTime;
        Date expiresAt = new Date(expMillis);
        JWTCreator.Builder builder = JWT.create().withIssuer(ISS)
                .withExpiresAt(expiresAt);
        for (Entry<String, Object> entry : payLoad.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
        }
        return builder.sign(algorithm);
    }

    public static Map<String, Claim> parseJwtToken(String secret, String jwtToken) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISS).build();
        DecodedJWT jwt = verifier.verify(jwtToken);
        return jwt.getClaims();
    }

    /**
     * 创建RSA加密的JWT token
     *
     * @param information
     * @return
     */
    public static String createJwtTokenRSA256(Map<String, Object> information, String privateKey, long expiredTime) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // RSA256
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        Algorithm algorithm = Algorithm.RSA256(null, rsaPrivateKey);
        long expMillis = System.currentTimeMillis() + expiredTime;
        Date expiresAt = new Date(expMillis);
        JWTCreator.Builder builder = JWT.create().withIssuer(ISS)
                .withExpiresAt(expiresAt);
        for (Entry<String, Object> entry : information.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
        }
        return builder.sign(algorithm);
    }

    /**
     * RSA解密的JWT token
     *
     * @param jwtToken
     * @return
     */
    public static Map<String, Claim> parseJwtTokenRSA256(String jwtToken, String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISS).build();
        DecodedJWT jwt = verifier.verify(jwtToken);
        return jwt.getClaims();
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }
}
