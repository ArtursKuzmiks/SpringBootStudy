package demo;

import demo.Application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Artur Kuzmik on 18.9.6
 */
@SpringBootApplication(scanBasePackages = "demo")
public class Main implements CommandLineRunner {

    @Autowired
    private Application application;

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        application.run();
    }
}
