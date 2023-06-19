package pl.zajavka.infrastructure.configuration;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import pl.zajavka.infrastructure.database.entity._EntityMarker;
import pl.zajavka.infrastructure.database.repository.jpa._JpaRepositoriesMarker;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@EnableJpaRepositories(basePackageClasses = _JpaRepositoriesMarker.class)
@PropertySource({"classpath:database.properties"})
public class PersistenceJPAConfiguration {

//    private static final Map<String, Object> SETTINGS = Map.ofEntries(
//            Map.entry(Environment.DRIVER, "org.postgresql.Driver"),
//            Map.entry(
//                    Environment.CONNECTION_PROVIDER,
//                    "org.hibernate.hikaricp.internal.HikariCPConnectionProvider"
//            ),
//            Map.entry(Environment.HBM2DDL_AUTO, "validate"),
//            Map.entry(Environment.SHOW_SQL, true),
//            Map.entry(Environment.FORMAT_SQL, false)
////            Map.entry(Environment.GENERATE_STATISTICS, true),
////            Map.entry(Environment.USE_SQL_COMMENTS, false)
//    );
//
//    private static final Map<String, Object> HIKARI_CP_SETTINGS = Map.ofEntries(
//            Map.entry("hibernate.hikari.connectionTimeout", "20000"),
//            Map.entry("hibernate.hikari.minimumIdle", "20"),
//            Map.entry("hibernate.hikari.maximumPoolSize", "20"),
//            Map.entry("hibernate.hikari.idleTimeout", "300000")
//    );

    private final org.springframework.core.env.Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUrl(environment.getProperty("jdbc.user"));
        dataSource.setUrl(environment.getProperty("jdbc.pass"));
        return dataSource;
    }

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(_EntityMarker.class.getPackageName());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
    }

    private Properties jpaProperties() {
        final Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, environment.getProperty(Environment.DIALECT));
        properties.setProperty(Environment.HBM2DDL_AUTO, environment.getProperty(Environment.HBM2DDL_AUTO));
        properties.setProperty(Environment.SHOW_SQL, environment.getProperty(Environment.SHOW_SQL));
        properties.setProperty(Environment.FORMAT_SQL, environment.getProperty(Environment.FORMAT_SQL));
        return properties;
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setBaselineOnMigrate(true);
        configuration.setLocations(new Location("filesystem:src/main/resources/flyway/migrations"));
        configuration.setDataSource(dataSource());
        return new Flyway(configuration);
    }
}
