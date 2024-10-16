package com.linkshrink.authn.configurations;

import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("Auditor")
public class Auditor implements AuditorAware<Integer> {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<Integer> getCurrentAuditor() {
        try {
            var user = extractLoggedInUser();
            return Optional.of(user.getId());
        } catch (Exception e) {
            log.info(e.toString());
        }
        return Optional.of(0);
    }

    private User extractLoggedInUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }
}
