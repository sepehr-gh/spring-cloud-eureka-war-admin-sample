package com.example.admin;

import com.netflix.appinfo.InstanceInfo;
import de.codecentric.boot.admin.server.cloud.discovery.EurekaServiceInstanceConverter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class MyEurekaServiceInstanceConverter extends EurekaServiceInstanceConverter {
    protected URI getServiceUrl(ServiceInstance instance) {
        Assert.isInstanceOf(EurekaServiceInstance.class, instance, "serviceInstance must be of type EurekaServiceInstance");
        InstanceInfo instanceInfo = ((EurekaServiceInstance)instance).getInstanceInfo();
        String contextPath = instanceInfo.getMetadata().get("contextPath");
        if(contextPath != null)
            return UriComponentsBuilder.fromUri(instance.getUri()).path(contextPath).build().toUri();
        else
            return instance.getUri();
    }
}
