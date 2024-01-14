package com.davi.Screescreenmatch;

import com.davi.Screescreenmatch.Application.Application;
import com.davi.Screescreenmatch.Repository.SerieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private SerieRepository serieRepository;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Application application = new Application();
        application.app(serieRepository);
    }
}
