package hospital.vital.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Breno
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    //Ao criar essa configuração ele desabilita o formulário de login e senha para poder fazer requisições da aplicação
    //Adicionar a seguinte anotação caso na classe de segurança caso eu queira fazer a checagem de segurança como o shiro faz
    //Encima dos metodos de requisição eu adiciona a role necessaria @Secured("ROLE_ADMIN")
    //@EnableMethodSecurity(securedEnabled = true)

    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //CSRF Cross-Site Request Forgery
        //sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) é para indicar que eu estou usando autenticação Stateless
        //Exemplo para bloquear requisição caso o usuário não tenha permissão
        //.requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
        //.requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")

        return http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
