package bcph.apps.messageWebApp.config;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
@EnableJpaRepositories( basePackages ={ "bcph.app.messageWebApp.model"})
public class JPAConfig {
	
	static String LOCAL_DATABASE_NAME = "testSens";
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());
		em.setPackagesToScan(new String[] { "bcph.app.messageWebApp.model"  });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	//Aditional config to hibernate
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");

		return properties;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {

		String databaseURL = System.getenv().getOrDefault("DATABASE_URL1",
				"jdbc:postgresql://localhost:5432/" + LOCAL_DATABASE_NAME + "?createDatabaseIfNotExist=true");
		String databaseUser = System.getenv().getOrDefault("DATABASE_USER", "postgres");
		String databasePassword = System.getenv().getOrDefault("DATABASE_PASSWORD", "1390");

		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("org.postgresql.Driver");
		datasource.setUrl(databaseURL);
		datasource.setUsername(databaseUser);
		datasource.setPassword(databasePassword);

		return datasource;

	}

}
