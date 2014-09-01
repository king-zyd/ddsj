package org.ddsj.web;

import com.zyd.core.database.ConnectionPoolDataSource;
import com.zyd.core.database.JPAAccess;
import com.zyd.core.log.LogSettings;
import com.zyd.core.platform.DefaultAppConfig;
import com.zyd.core.platform.runtime.RuntimeEnvironment;
import com.zyd.core.platform.runtime.RuntimeSettings;
import com.zyd.core.template.FreemarkerTemplate;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

@Configuration
public class AppConfig extends DefaultAppConfig {
    @Inject
    Environment env;

    @Inject
    ServletContext servletContext;

    @Override
    public LogSettings logSettings() {
        LogSettings logSettings = LogSettings.get();
        logSettings.setAlwaysWriteTraceLog(env.getProperty("site.forceTraceLog", Boolean.class));
        return logSettings;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(AppConfig.class.getPackage().getName());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(MySQLDialect.class.getName());
        vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        return factoryBean;
    }

    @Bean
    public FreemarkerTemplate freemarkerTemplate() {
        return new FreemarkerTemplate();
    }

    @Override
    public RuntimeSettings runtimeSettings() {
        RuntimeSettings settings = super.runtimeSettings();
        settings.setEnvironment(env.getProperty("site.environment", RuntimeEnvironment.class, RuntimeEnvironment.PROD));
        settings.setVersion(getVersion());
        return settings;
    }

    @Bean
    public DataSource dataSource() {
        ConnectionPoolDataSource dataSource = new ConnectionPoolDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driverClassName"));
        dataSource.setDatabaseName(env.getProperty("db.databaseName"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }

    @Bean
    public JPAAccess jpaAccess() {
        return new JPAAccess();
    }

    private String getVersion() {
        String version = env.getProperty("runtime.version");
        if (!StringUtils.hasText(version) || version.contains("${"))
            return "current"; // for local build, where version is not populated
        return version;
    }
}