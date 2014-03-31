package org.fenixedu.bennu;

import org.fenixedu.cms.domain.Component;
import org.fenixedu.cms.domain.ComponentType;
import org.fenixedu.cms.domain.RegisterSiteTemplate;
import org.fenixedu.cms.domain.Site;
import org.fenixedu.cms.domain.SiteTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import pt.ist.fenixframework.DomainObject;

@Configuration
@ComponentScan("org.fenixedu.cms")
public class CMSConfiguration implements InitializingBean {
    private static final int FILE_MAX_SIZE_IN_BYTES = 10 * 1024 * 1024;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(FILE_MAX_SIZE_IN_BYTES);
        return commonsMultipartResolver;
    }

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (SiteTemplate instance: context.getBeansOfType(SiteTemplate.class).values()) {
            RegisterSiteTemplate annotation = (RegisterSiteTemplate) instance.getClass().getAnnotation(RegisterSiteTemplate.class);
            Site.register(annotation.type(), instance.getClass());
        }
        
        for (Object instance: context.getBeansOfType(ComponentType.class).values()) {
            ComponentType annotation = instance.getClass().getAnnotation(ComponentType.class);
            Component.register(annotation.type(), instance.getClass());
        }
    }
}
