package com.giunne.memberservice;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.giunne.memberservice", "com.giunne.commonservice"})
public class MemberTestConfiguration {
}
