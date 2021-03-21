package net.bitnine.template.config;

import java.util.Optional;
import net.bitnine.template.model.SessionUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class AuditAwareConfig {
    @Bean
    public Optional<String> auditorAware() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        SessionUser user = (SessionUser) authentication.getPrincipal();
        return Optional.of(user.getUserId());
    }
}
