package com.github.line.sheduleupdateapi.config;

import com.github.line.sheduleupdateapi.service.Observer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@ImportResource({"classpath*:dbcfg.xml"})
public class ApplicationConfig {


}
