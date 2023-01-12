package dbconnection.demo.config.security;

import dbconnection.demo.config.AccessToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

public class RequestAuthenticatedProvider {




    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AccessToken getAuthenticatedUserInRequest() {
        SecurityContext context = SecurityContextHolder.getContext();
//        if (context == null) {
//            return null;
//        }

        Authentication authentication = context.getAuthentication();
//        if (authentication == null) {
//            return null;
//        }

        Object details = authentication.getDetails();
//        if (!(details instanceof AccessToken)) {
//            return null;
//        }

        return (AccessToken) authentication.getDetails();
    }
}
