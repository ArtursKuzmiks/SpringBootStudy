package demo;

import demo.view.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Artur Kuzmik on 18.9.6
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private ApplicationService applicationService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        applicationService.run();
    }
}
