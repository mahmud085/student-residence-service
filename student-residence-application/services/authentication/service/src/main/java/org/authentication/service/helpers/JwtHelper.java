package org.authentication.service.helpers;

import io.jsonwebtoken.*;
import org.authentication.common.exceptions.InvalidAccessTokenException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtHelper {
    private static String SECRET_KEY = "student-residence-auth-key";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public static String issueToken(String subject, String issuer, Date expiresAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(expiresAt)
                .signWith(SIGNATURE_ALGORITHM, getSigningKey())
                .compact();
    }

    public static void validateToken(String token) throws InvalidAccessTokenException {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token).getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidAccessTokenException("Access token is invalid/expired");
        }
    }

    private static SignatureAlgorithm getSignatureAlgorithm() {
        return SignatureAlgorithm.HS256;
    }

    private static Key getSigningKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        return new SecretKeySpec(secretKeyBytes, getSignatureAlgorithm().getJcaName());
    }
}
