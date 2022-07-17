package com.springboot.blog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class SpringbootBlogRestApiApplication {

  @Bean
  public ModelMapper modelMapper(){
         return new ModelMapper();
  }
  @Configuration
  public class BeanConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new Jdk8Module());
      objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
      return objectMapper;
    }

  }

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
