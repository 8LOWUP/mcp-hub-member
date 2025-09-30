package com.mcphub.config;

import io.sentry.Sentry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Bean;

@Configuration
@Profile("dev") // dev 환경에서만 활성화
public class SentryDevConfig {

    @Bean
    public Sentry.OptionsConfiguration<SentryOptions> sentryOptions() {
        return options -> {
            options.setEnvironment("member-service"); // 여기서 override
        };
    }
}
