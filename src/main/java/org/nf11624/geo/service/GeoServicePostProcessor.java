package org.nf11624.geo.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// Post process Geo Service Bean
public class GeoServicePostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean != null && bean instanceof GeoService) {
			GeoService geo = (GeoService) bean;
			geo.setStates(geo.generateStates());
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
