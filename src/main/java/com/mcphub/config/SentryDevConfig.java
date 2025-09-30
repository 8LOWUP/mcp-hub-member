package com.mcphub.config;

import io.sentry.SentryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") // dev 환경에서만 활성화
public class SentryDevConfig {

    @Bean
    public SentryOptions.OptionsConfiguration<SentryOptions> sentryOptions() {
        return options -> {
            options.setEnvironment("member-service"); // 여기서 override
        };
    }
}
