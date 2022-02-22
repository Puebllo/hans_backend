package com.pueblo.software.mqtt.config;

import io.moquette.broker.security.IAuthorizatorPolicy;
import io.moquette.broker.subscriptions.Topic;

public class AuthPolicy implements IAuthorizatorPolicy{

	@Override
	public boolean canRead(Topic arg0, String arg1, String arg2) {
        System.out.println("Moze czytac");
        return true;
	}

	@Override
	public boolean canWrite(Topic arg0, String arg1, String arg2) {
        System.out.println("Moze wysylac");
        return true;
	}

}
