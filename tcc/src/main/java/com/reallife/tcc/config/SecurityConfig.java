package com.reallife.tcc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //  Desativa CSRF (necessário pra APIs REST)
                .csrf(csrf -> csrf.disable())

                //  Ativa CORS (usa o CorsConfig)
                .cors(Customizer.withDefaults())

                //  Define quais rotas são públicas ou privadas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/**").permitAll() // exemplo: rota pública
                        .anyRequest().authenticated() // o resto precisa estar autenticado
                )

                //  Autenticação HTTP básica (útil pra testes)
                .httpBasic(Customizer.withDefaults())

                //  Desativa formulário de login padrão do Spring
                .formLogin(form -> form.disable());

        return http.build();
    }

    // Usuário em memória (apenas pra testes)
    // Depois você pode remover e usar autenticação via banco
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("admin")
                .password("{noop}123456") // {noop} -> sem encoder
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
