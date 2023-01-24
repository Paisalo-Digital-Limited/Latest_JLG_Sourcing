package com.softeksol.paisalo.jlgsourcing.entities.dto;

import java.io.Serializable;

public class OperationItem implements Serializable {
    private int id;
    private String oprationName;
    private int optionColor;
    private String urlController;
    private String urlEndpoint;

    public OperationItem() {
    }

    public OperationItem(int id, String oprationName, int optionColor) {
        this.id = id;
        this.oprationName = oprationName;
        this.optionColor = optionColor;
    }

    public OperationItem(int id, String oprationName, int optionColor, String urlController, String urlEndpoint) {
        this.id = id;
        this.oprationName = oprationName;
        this.optionColor = optionColor;
        this.urlController = urlController;
        this.urlEndpoint = urlEndpoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOprationName() {
        return oprationName;
    }

    public void setOprationName(String oprationName) {
        this.oprationName = oprationName;
    }

    public int getOptionColor() {
        return optionColor;
    }

    public void setOptionColor(int optionColor) {
        this.optionColor = optionColor;
    }

    public String getUrlEndpoint() {
        return urlEndpoint;
    }

    public void setUrlEndpoint(String urlEndpoint) {
        this.urlEndpoint = urlEndpoint;
    }

    public String getUrlController() {
        return urlController;
    }

    public void setUrlController(String urlController) {
        this.urlController = urlController;
    }
}
