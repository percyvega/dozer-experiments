package com.percyvega;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.percyvega.model.Consultant;
import com.percyvega.model.Contractor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Mapper defaultMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }

    @Bean
    public Mapper xmlMapper() {
        List<String> mappingFiles = Arrays.asList(
                "dozer-model-mappings.xml"
        );

        return DozerBeanMapperBuilder.create()
                .withMappingFiles(mappingFiles)
                .build();
    }

    @Bean
    public Mapper programmaticMapper() {
        return DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(
                        new BeanMappingBuilder() {
                            @Override
                            protected void configure() {
                                mapping(Consultant.class, Contractor.class)
                                        .fields(
                                                field("consultingFirmName"),
                                                field("agencyName"))
                                        .fields(
                                                field("salary"),
                                                field("wages"));
                            }
                        }
                ).build();
    }
}
