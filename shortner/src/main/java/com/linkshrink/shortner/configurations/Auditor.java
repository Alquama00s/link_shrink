package com.linkshrink.shortner.configurations;

import com.linkshrink.shortner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("Auditor")
public class Auditor implements AuditorAware<Integer> {

    @Autowired
    UserService userService;

    @Override
    public Optional<Integer> getCurrentAuditor() {
        try {
            var user = userService.getLoggedInUser();
            return Optional.of(user.getId());
        } catch (Exception e) {
            log.info(e.toString());
        }
        return Optional.of(0);
    }
}
