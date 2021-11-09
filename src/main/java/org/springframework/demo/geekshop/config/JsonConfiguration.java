package org.springframework.demo.geekshop.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration is needed to solve the following error :
 * Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]
 * See the link below for the solution
 * https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
 */
@Configuration
public class JsonConfiguration {
    @Bean
    public Module hibernateModule() {
        return new Hibernate5Module();
    }
}
