package com.giunne.itemservice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.giunne.itemservice", "com.giunne.commonservice"})
public class ItemTestConfiguration {
}
