package org.springframework.demo.geekshop.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defined to filter the generation of Swagger Doc.
 * This annotation is used in {@link SwaggerDocConfig}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GenerateSwaggerDoc {
}
