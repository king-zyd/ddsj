package org.ddhg;

import com.zyd.core.platform.AbstractSiteConfig;
import com.zyd.core.platform.PlatformScopeResolver;
import com.zyd.core.platform.web.DeploymentSettings;
import com.zyd.core.platform.web.site.SiteSettings;
import com.zyd.core.platform.web.site.session.SessionProviderType;
import com.zyd.core.util.TimeLength;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

/**
 * @author zyd
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = WebConfig.class, scopeResolver = PlatformScopeResolver.class)
public class WebConfig extends AbstractSiteConfig {
    @Inject
    Environment env;

    @Inject
    ServletContext servletContext;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @Override
    public DeploymentSettings deploymentSettings() {
        DeploymentSettings settings = super.deploymentSettings();
        settings.setDeploymentContext(env.getProperty("site.deploymentContext"), servletContext);
        settings.setHTTPPort(env.getRequiredProperty("site.httpPort", int.class));
        settings.setHTTPSPort(env.getRequiredProperty("site.httpsPort", int.class));
        return settings;
    }

    @Override
    public SiteSettings siteSettings() {
        SiteSettings settings = new SiteSettings();
        settings.setErrorPage("/error");
        settings.setResourceNotFoundPage("forward:/error/resource-not-found");
        settings.setSessionProviderType(env.getProperty("site.sessionProvider", SessionProviderType.class));
        settings.setSessionTimeOut(TimeLength.minutes(30));
        settings.setJSDir(env.getProperty("site.jsDir"));
        settings.setRemoteSessionServers(env.getRequiredProperty("site.remoteSessionServers"));
        return settings;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/WEB-INF/favicon.ico");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exceptionInterceptor());
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(httpSchemeEnforceInterceptor());
        registry.addInterceptor(cookieInterceptor());
        registry.addInterceptor(sessionInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home");
    }

}
