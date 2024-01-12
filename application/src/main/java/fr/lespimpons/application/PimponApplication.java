package fr.lespimpons.application;

import fr.lespimpons.application.logic.LogicManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()
public class PimponApplication {

    public static void main(String[] args) {
        SpringApplication.run(PimponApplication.class, args);
        LogicManagement.getInstance();
    }

}
