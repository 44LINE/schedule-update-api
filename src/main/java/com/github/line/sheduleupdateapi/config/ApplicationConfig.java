package com.github.line.sheduleupdateapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:dbcfg.xml"})
public class ApplicationConfig {
}
