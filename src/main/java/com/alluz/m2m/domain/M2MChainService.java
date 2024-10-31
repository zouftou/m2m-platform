package com.alluz.m2m.domain;

import com.alluz.m2m.sender.http.HttpSender;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Service;

@Service
public class M2MChainService {

    private final M2MChainRepository m2mChainRepository;

    public M2MChainService(M2MChainRepository m2mChainRepository) {
        this.m2mChainRepository = m2mChainRepository;
    }

    public void startChain(Long chainId) {

        M2MChain m2mChain = m2mChainRepository.findChainById(chainId);

        DefaultListableBeanFactory context = new DefaultListableBeanFactory();

        for(M2MHandler handler : m2mChain.getHandlers()) {
            GenericBeanDefinition gbd = new GenericBeanDefinition();
            gbd.setBeanClass(handler.getClassName());

            MutablePropertyValues mpv = new MutablePropertyValues();
            handler.getProperties().forEach(mpv::add);
            gbd.setPropertyValues(mpv);

            context.registerBeanDefinition(handler.getClassName().getName() + m2mChain.getId(), gbd);
            //HttpSender bean = (HttpSender) context.getBean(handler.getClassName().getName() + m2mChain.getId());
            //bean.start();
        }
    }

    public void stopChain(Long chainId) {
        M2MChain m2mChain = m2mChainRepository.findChainById(chainId);
        DefaultListableBeanFactory context = new DefaultListableBeanFactory();
        for(M2MHandler handler : m2mChain.getHandlers()) {
            context.removeBeanDefinition(handler.getClassName().getName() + chainId);
        }
    }
}
