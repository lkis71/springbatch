//package toyproject.datasource;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import toyproject.entity.User;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "toyproject.repository.batch",
//        entityManagerFactoryRef = "batchEntityManager",
//        transactionManagerRef = "batchTxManager"
//)
//public class BatchDataSourceConfig {
//
//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean batchEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(batchDataSource());
//        em.setPackagesToScan(new String[] {"toyproject.entity"});
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return em;
//    }
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource-batch")
//    public DataSource batchDataSource() {
//        DataSourceBuilder builder = DataSourceBuilder.create();
//        builder.type(HikariDataSource.class);
//        return builder.build();
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager batchTxManager() {
//        JpaTransactionManager tx = new JpaTransactionManager();
//        tx.setEntityManagerFactory(batchEntityManager().getObject());
//        return tx;
//    }
//}
