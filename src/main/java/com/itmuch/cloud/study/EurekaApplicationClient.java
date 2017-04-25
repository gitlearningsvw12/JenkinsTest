package com.itmuch.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION, value = ExcludeFromComponentScan.class)})
@RibbonClient(name = "config-discovery-eureka-client-producer", configuration = TestRibbonConfig.class)
public class EurekaApplicationClient {
	
	
	
  @Bean
  @LoadBalanced
   public RestTemplate restTemplate(){
	   return new RestTemplate();
   }
  public static void main(String[] args) {
    SpringApplication.run(EurekaApplicationClient.class, args);
  }
}
