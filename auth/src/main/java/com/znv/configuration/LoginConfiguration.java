package com.znv.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "login")
public class LoginConfiguration {
    private boolean enableLoginAtSameTime;
    private List<String> persistTokenUsers = new ArrayList<String>();
}
