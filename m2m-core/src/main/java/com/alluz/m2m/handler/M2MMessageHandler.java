package com.alluz.m2m.handler;

import com.alluz.m2m.message.M2MCommunicationException;
import com.alluz.m2m.message.M2MMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * The abstract super class according to Chain of Responsibility pattern plus Template Method Pattern.
 * <p>
 * Check out for the methods you have to overwrite and for the methods you might overwrite plus for the
 * methods you can't overwrite. Please note:
 * <p>
 * -> everything which should happen you might implement within handleMessage.
 * -> everything starts with 'onMessageEvent' at the first element of the chain
 */
public abstract class M2MMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(M2MMessageHandler.class);

    /** the next in line */
    private M2MMessageHandler next;

    /** the type of message the handler can deal with */
    private Class<?> inType;

    /** the type of message the handler will send */
    private Class<?> outType;

    /** the message we are dealing with */
    private M2MMessage message;

    /** enforce to skip doChain() */
    private boolean continueProceeding = true;

    /** Constructor */
    protected M2MMessageHandler() {
        // lazy
    }

    /** Constructor */
    protected M2MMessageHandler(String inTypeClassName, String outTypeClassName) {
        try {
            this.setInType(Class.forName(inTypeClassName));
            this.setOutType(Class.forName(outTypeClassName));
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Class not found", ex);
        }
    }

    /**
     * template method implementation
     */
    public final <T extends M2MMessage> void onMessageEvent(T message) throws M2MCommunicationException {
        // can we deal with incoming message?
        this.checkInMessageType(message);

        //setting the message to the instance
        this.setMessage(message);

        // hand over to subclass
        this.handleMessage(message);

        // we will continue if ...
        if(null != this.getNext() && this.getContinueProceeding()) {
            this.checkOutMessageType(this.getMessage());
            this.doChain(this.getMessage());
        } else if (! this.getContinueProceeding()){
            // stopping
            LOGGER.debug("Waiting to continue");
        } else if (null == this.next){
            // termination of chain
            LOGGER.debug("End of chain");
        }
    }

    /** this needs to be overwritten ... individual handling of message by subclass */
    public abstract <T extends M2MMessage> void handleMessage(T message) throws M2MCommunicationException;

    /** checking 4 correct inbound message type */
    private  <T extends M2MMessage> void checkInMessageType(T message) throws M2MCommunicationException {
        LOGGER.trace("checking incoming message type: {}",message.getClass().getSimpleName());

        // it is important to make use of getters instead of direct access to field as otherwise
        // CGLIB proxies used in AOP will provide null values for the fields
        if(! this.getInType().isInstance(message)) {
            Object[] data = {this.getClass().getSimpleName(), message.getClass().getSimpleName(), this.getInType().getSimpleName()};
            MessageFormat formatter = new MessageFormat("{0} -> inbound MessageType: {1} can not be handled by adapter, should be: {2},  check config");
            throw new M2MCommunicationException(formatter.format(data));
        }
    }

    /** checking 4 correct outbound message type */
    private <T extends M2MMessage> void checkOutMessageType(T message) throws M2MCommunicationException {
        LOGGER.trace("checking outgoing message type: {}",message.getClass().getSimpleName());

        // it is important to make use of getters instead of direct access to field as otherwise
        // CGLIB proxies used in AOP will provide null values for the fields
        if(! this.getOutType().isInstance(message)) {
            Object[] data = {this.getClass().getSimpleName(), message.getClass().getSimpleName(), this.getOutType().getSimpleName()};
            MessageFormat form = new MessageFormat("{0}-> outbound MessageType: {1} can not be handled by adapter, should be: {2}, check config");
            throw new M2MCommunicationException(form.format(data));
        }
    }

    /**
     * going for the next one ...
     */
    protected final <T extends M2MMessage> void doChain(T message) throws M2MCommunicationException {
        LOGGER.debug("chaining");
        this.getNext().onMessageEvent(message);
    }

    public final M2MMessageHandler getNext() {
        return next;
    }

    public final void setNext(M2MMessageHandler next) {
        this.next = next;
    }

    public boolean getContinueProceeding() {
        return continueProceeding;
    }

    public void setContinueProceeding(boolean stopProceeding) {
        this.continueProceeding = stopProceeding;
    }

    public M2MMessage getMessage() {
        return message;
    }

    public void setMessage(M2MMessage message) {
        this.message = message;
    }

    public Class<?> getInType() {
        return inType;
    }

    public void setInType(Class<?> inType) {
        this.inType = inType;
    }

    public Class<?> getOutType() {
        return outType;
    }

    public void setOutType(Class<?> outType) {
        this.outType = outType;
    }
}
