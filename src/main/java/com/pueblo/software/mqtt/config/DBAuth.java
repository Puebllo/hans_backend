package com.pueblo.software.mqtt.config;

import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pueblo.software.service.NodeService;

import io.moquette.broker.security.IAuthenticator;


@Component
@EnableTransactionManagement
public class DBAuth implements IAuthenticator,ApplicationContextAware{

    private static ApplicationContext context;
    
    private HashMap<String,Boolean> checkedNodesHM = new HashMap<>();
	  
    public boolean checkValid(String clientId, String user, byte[] passwd) {
    	NodeService dao = getBean(NodeService.class);
    	boolean verified = false;
    	if (!checkedNodesHM.containsKey(clientId)) { //FIXME: zastanowic sie czy nie sprawdzac za kazdym polaczeniem ?!!
        	 verified = dao.verifyNode(clientId, user,new String(passwd));
         	checkedNodesHM.put(clientId, verified);
		}else {
			verified = checkedNodesHM.get(clientId);
		}
        return verified;
    }

    
    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (context==null) {
			DBAuth.context= applicationContext;
		}
	}

}
