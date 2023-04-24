package hospital.vital.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import hospital.vital.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Breno
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        //withClaim utilizado para adicionar campos ao JWT
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Login com SpringBoot")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId())
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("Login com SpringBoot")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        //Expira o token em duas horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
