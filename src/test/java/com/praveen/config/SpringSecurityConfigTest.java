/*
package com.praveen.config;

import com.praveen.cms.api.security.UserPrincipal;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityConfigTest {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserPrincipal simpleUser = UserPrincipal
                .builder()
                .name("Simple User")
                .email("simple_user@store.com.br")
                .password("$2a$10$oYjYwYZ41S3qlcDoPn7oFuIbKDah1CKgPm.uE.5KRiDg4E9QNewUO")
                .authorities(
                        Arrays.asList(
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                )
                .build();

        UserPrincipal adminUser = UserPrincipal
                .builder()
                .name("Admin user")
                .email("admin_user@store.com.br")
                .password("$2a$10$oYjYwYZ41S3qlcDoPn7oFuIbKDah1CKgPm.uE.5KRiDg4E9QNewUO")
                .authorities(
                        Arrays.asList(
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        )
                )
                .build();

        return new InMemoryUserDetailsManager(Arrays.asList(adminUser,simpleUser));
    }
}
*/
