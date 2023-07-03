package ru.otus.nyuriv.socialnet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "settings")
@Data
public class AppSettings {

    /**
     * Секретный ключ для шифрования паролей
     */
    private String secretKey;

    /**
     * Время жизни сессии, в минутах
     */
    private int sessionLifetimeMinutes = 60;
}
