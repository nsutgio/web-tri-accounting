package com.tri.erp.spring.commons;

import java.util.ArrayList;

/**
 * Created by TSI Admin on 9/10/2014.
 */
public abstract class Response {
    private boolean success;
    private ArrayList<String> fields = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();
    private String successMessage;
    private int modelId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
}
