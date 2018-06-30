package demo.controller.AppConfig;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Artur Kuzmik on 18.21.6
 */

public abstract class AbstractAppController extends Application {


    private static String[] savedArgs;

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(Class<? extends AbstractAppController> appClass, String[] args) {
        AbstractAppController.savedArgs = args;
        Application.launch(appClass, args);
    }
}
