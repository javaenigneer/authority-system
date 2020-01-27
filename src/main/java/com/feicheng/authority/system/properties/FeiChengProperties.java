package com.feicheng.authority.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Lenovo
 */
@SpringBootConfiguration
@PropertySource(value = {"classpath:feicheng.properties"})
@ConfigurationProperties(prefix = "feicheng")
@Data
public class FeiChengProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private boolean autoOpenBrowser = true;

    private String[] autoOpenBrowserEnv = {};

}
