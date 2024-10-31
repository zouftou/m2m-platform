package com.alluz.m2m.chain;

import com.alluz.m2m.handler.M2MMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a stack which holds the handler chain as defined within the stack config xml file.
 * As Spring seems to instantiate the beans from the end to the top, we need to realize this as a LIFO stack.
 * Currently, this is NOT used and NOT configured by Spring DI (therefore it's a 'traditional' Singleton).
 *
 */
public class M2MChainHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(M2MChainHolder.class);

    private List<M2MMessageHandler> stack = new ArrayList<>();

    private int stackPos = 0;

    // the initial value
    private int cursor = 1;
    // are we through?
    private boolean gotAll;

    public void addHandler(M2MMessageHandler handler) {
        if(null != handler) {
            LOGGER.debug("adding 2 stack: {}",handler.getClass().getSimpleName());
            this.stack.add(stackPos, handler);
            stackPos++;
            LOGGER.debug("stack size: {}",stack.size());
        }
    }

    public M2MMessageHandler getNextHandler() {

        M2MMessageHandler next = null;

        if(! this.gotAll) {

            if(cursor != this.stack.size()) {
                next = this.stack.get(cursor);
                cursor++;
            } else {
                this.gotAll = true;
            }
        }

        if(null != next) {
            LOGGER.debug("returning: {}",next.getClass().getSimpleName());
        } else {
            LOGGER.debug("all elements retrieved, returning null");
        }

        return next;
    }

    private void init() {
        StringBuilder str = new StringBuilder();
        for (M2MMessageHandler m2mMessageHandler : stack) {
            str.append(" -> ");
            str.append(m2mMessageHandler.getClass().getSimpleName());
        }
        LOGGER.debug("Stack is composed of: " + stack.size() + " elements: " + str.toString());
    }

    public void reset() {
        this.cursor = 0;
        this.gotAll = false;
    }

    public List<M2MMessageHandler> getStack() {
        return stack;
    }

    public void setStack(List<M2MMessageHandler> stack) {
        this.stack = stack;
    }
}
