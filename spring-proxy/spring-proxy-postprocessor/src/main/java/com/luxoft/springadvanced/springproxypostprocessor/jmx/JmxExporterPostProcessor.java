package com.luxoft.springadvanced.springproxypostprocessor.jmx;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.management.DynamicMBean;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;

@Component
public class JmxExporterPostProcessor implements BeanPostProcessor {

    @Override
    @SuppressWarnings("NullableProblems")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        configureProxy(bean, beanName);
        return bean;
    }

    private static void configureProxy(Object bean, String beanName) {
        final Class<?> aClass = bean.getClass();
        final var annotation = aClass.getAnnotation(JmxExporter.class);
        if (annotation == null) return;

        try {
            var name = StringUtils.hasText(annotation.name())
                    ? annotation.name()
                    : beanName;

            final var objectName = new ObjectName(aClass.getPackageName() + ":type=basic,name=" + name);

            final var platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            final var proxy = getDynamicMBean(bean);
            platformMBeanServer.registerMBean(proxy, objectName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static DynamicMBean getDynamicMBean(Object bean) {
        return (DynamicMBean) Proxy.newProxyInstance(
                JmxExporterPostProcessor.class.getClassLoader(),
                new Class[]{DynamicMBean.class},
                new JmxWrapperInvocationHandler(bean));
    }
}
