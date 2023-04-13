//package toyproject.datasource;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "toyproject.repository.blog",
//        entityManagerFactoryRef = "blogEntityManager",
//        transactionManagerRef = "blogTxManager"
//)
//public class BlogDataSourceConfig {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean blogEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(blogDataSource());
//        em.setPackagesToScan(new String[] {"toyproject.repository.blog"});
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return em;
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource-blog")
//    public DataSource blogDataSource() {
//        DataSourceBuilder builder = DataSourceBuilder.create();
//        builder.type(HikariDataSource.class);
//        return builder.build();
//    }
//
//    @Bean
//    public PlatformTransactionManager blogTxManager() {
//        JpaTransactionManager tx = new JpaTransactionManager();
//        tx.setEntityManagerFactory(blogEntityManager().getObject());
//        return tx;
//    }
//}
