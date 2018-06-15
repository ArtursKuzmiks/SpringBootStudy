package demo.AppConfig;

import demo.Main;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author Artur Kuzmik on 18.29.5
 */

@Configuration
@PropertySource(value = {"classpath:app.properties"})
@Profile("dev")
public class ProductionConfig {

    private Environment env;

    @Autowired
    public ProductionConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(dataSource());
        emFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProp = new Properties();
        jpaProp.setProperty("hibernate.dialect","com.enigmabridge.hibernate.dialect.SQLiteDialect");
        jpaProp.setProperty("hibernate.hbm2ddl.auto","update");
        jpaProp.setProperty("hibernate.order_inserts","true");
        jpaProp.setProperty("hibernate.order_update","true");
        jpaProp.setProperty("hibernate.jdbc.batch_size","50");
        jpaProp.setProperty("hibernate.jdbc.fetch_size","50");
        emFactory.setJpaProperties(jpaProp);
        emFactory.setPackagesToScan(Main.class.getPackage().getName());

        return emFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }



}
