package com.itmuch.cloud.study.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;



@RestController
public class  TestNameChange{
	@Autowired
	private DiscoveryClient  discoveryClient;
	
	 @Autowired
	 private EurekaClient eurekaClient;
	 
	 @Autowired
	 private RestTemplate restTemplate;
	 
	 @Autowired
	 private LoadBalancerClient loadBalancerClient;
	 
	 @GetMapping("/test/{id}")
	 public String findById(@PathVariable String id){
		 this.restTemplate.getForObject("http://config-discovery-eureka-client-producer/append/"+id, String.class);
		 this.restTemplate.getForObject("http://config-discovery-eureka-client-producer-duplicate/append/"+id, String.class);
		 
		 return "findById";
			
	 }
	 
	 @GetMapping("/ribbonproperties")
	 public String ribbonproperties(){
		 ServiceInstance instance = loadBalancerClient.choose("config-discovery-eureka-client-producer");
		 System.out.println(instance.getHost()+":"+instance.getPort()+":"+instance.getServiceId()); 
		 return "ribbonproperties";
			
	 }
	 
	 
	 @GetMapping("/ribbonClient")
	 public String getRibbonClient(){
		 ServiceInstance instance = loadBalancerClient.choose("config-discovery-eureka-client-producer");
		 System.out.println(instance.getHost()+":"+instance.getPort()+":"+instance.getServiceId());
		 
		 
		 ServiceInstance instanceDup = loadBalancerClient.choose("config-discovery-eureka-client-producer-duplicate");
		 System.out.println(instanceDup.getHost()+":"+instanceDup.getPort()+":"+instanceDup.getServiceId());
		 
		 return "ribbonClient";
	 }
	
	
	  /**
	   * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
	   * @RequestMapping(value = "/id", method = RequestMethod.GET)
	   * 类似的注解还有@PostMapping等等
	   * **/
	 @GetMapping("/instanceinfo")
	public ServiceInstance getServiceInstance(){
		ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
	    return localServiceInstance;
	}
	 
	 
	 @GetMapping("/add/{a}/{b}")
		public int add(@PathVariable int a ,@PathVariable int b){
		    return a+b;
		}
	 
	
	 @GetMapping("/eureka-info")
	 public String serviceUrl() {
	     InstanceInfo instance = eurekaClient.getNextServerFromEureka("localhost", false);
	     return instance.getHomePageUrl();
	 }

}
