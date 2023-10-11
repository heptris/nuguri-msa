package com.nuguri.dealservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Setter
@Getter
@ConfigurationProperties("com.sangtae")
@RefreshScope
@ToString
public class TestConfig {

    private String profile;
    private String region;
}
