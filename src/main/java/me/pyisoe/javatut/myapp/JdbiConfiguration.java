package me.pyisoe.javatut.myapp;

import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.spring.SpringConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbiConfiguration {
    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        ConnectionFactory cf = new SpringConnectionFactory(dataSource);
        final Jdbi jdbi = Jdbi.create(cf);
        /* additional configuration goes here */
        return jdbi;
    }
}