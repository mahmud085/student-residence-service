package org.authentication.web.helpers;

import io.jsonwebtoken.*;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.InvalidAccessTokenException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token).getBody();
            LocalDateTime expiresAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(claims.get("exp").toString()) * 1000), ZoneId.systemDefault());

            if (LocalDateTime.now().isAfter(expiresAt)) {
                throw new InvalidAccessTokenException(Messages.INVALID_OR_EXPIRED_ACCESS_TOKEN);
            }
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidAccessTokenException(Messages.INVALID_OR_EXPIRED_ACCESS_TOKEN);
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
