package com.alluz.m2m.domain;

import com.alluz.m2m.receiver.pojo.POJOReceiver;
import com.alluz.m2m.sender.http.HttpSender;

public class M2MChainRepository {
    public M2MChain findChainById(Long chainId) {

        M2MChain m2mChain = new M2MChain();
        m2mChain.setId(chainId);

        M2MHandler handler1 = new M2MHandler();
        handler1.setClassName(POJOReceiver.class);

        M2MHandler handler2 = new M2MHandler();
        handler1.setClassName(HttpSender.class);
        handler1.addProperty("url","http://localhost:8080/api/sensorData");

        m2mChain.addHandler(handler1);
        m2mChain.addHandler(handler2);

        return m2mChain;
    }
}
