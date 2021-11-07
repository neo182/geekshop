package org.springframework.demo.geekshop.controller.rest;

/**
 * Created for @JsonView to avoid the need of DTO classes
 * Read more about it : https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring
 * and
 */
public class RestView {
    public interface NormalUser {}
    public interface Admin extends NormalUser {}
}
