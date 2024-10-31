package com.alluz.m2m.domain;

import java.util.ArrayList;
import java.util.List;

public class M2MChain {

    Long id;

    private final List<M2MHandler> handlers = new ArrayList<>();

    public void addHandler(M2MHandler m2MHandler) {
        handlers.add(m2MHandler);
    }

    public void setId(Long chainId) {
        this.id = chainId;
    }

    public Long getId() {
        return id;
    }

    public List<M2MHandler> getHandlers() {
        return handlers;
    }
}
