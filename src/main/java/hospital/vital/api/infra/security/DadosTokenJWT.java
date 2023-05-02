package hospital.vital.api.infra.security;

import hospital.vital.api.domain.usuario.Usuario;

/**
 * @author Breno
 */

public record DadosTokenJWT(String token, Long id, String nome) {
}
