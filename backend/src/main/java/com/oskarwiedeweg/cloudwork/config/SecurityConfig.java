package com.oskarwiedeweg.cloudwork.config;

import com.oskarwiedeweg.cloudwork.auth.AppExpressionRoot;
import com.oskarwiedeweg.cloudwork.auth.c4.C4MethodSecurityExpressionHandler;
import com.oskarwiedeweg.cloudwork.auth.token.TokenFilter;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Data
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenFilter tokenFilter) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(matcher ->
                matcher.anyRequest().permitAll());

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(PostDao postDao) {
        return new C4MethodSecurityExpressionHandler(() -> new AppExpressionRoot(postDao));
    }

    @Bean
    public SecretGenerator secretGenerator() {
        return new DefaultSecretGenerator(64);
    }

    @Bean
    public QrGenerator qrGenerator() {
        return new ZxingPngQrGenerator();
    }

    @Bean
    public TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

    @Bean
    public CodeGenerator codeGenerator() {
        return new DefaultCodeGenerator(HashingAlgorithm.SHA256);
    }

    @Bean
    public CodeVerifier codeVerifier(CodeGenerator codeGenerator, TimeProvider timeProvider) {
        return new DefaultCodeVerifier(codeGenerator, timeProvider);
    }

}
