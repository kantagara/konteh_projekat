package com.garabanda.KontehProject.documentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public Docket swaggerSpringfoxDocket() {

        log.debug("Initializing swagger...");

        final StopWatch watch = new StopWatch();
        watch.start();

        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(apiPaths())
                .build();

        watch.stop();
        log.debug("Swagger started in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    @SuppressWarnings("unchecked")
    private Predicate<String> apiPaths() {
        return Predicates.or(PathSelectors.regex("/api/.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Konteh project API documentation")
                .description("The reference documentation for Konteh project.")
                .build();
    }
}
