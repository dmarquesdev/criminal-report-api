package br.ufsc.inf.lapesd.criminal.report.api.config;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.ufsc.inf.lapesd.criminal.report.api")
public class WebApiApplication {

    public static void main(String[] args) throws UnsupportedEncodingException {
        SpringApplication.run(WebApiApplication.class, args);
    }
}