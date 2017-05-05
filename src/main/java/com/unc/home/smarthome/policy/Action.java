package com.unc.home.smarthome.policy;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

@JsonPropertyOrder({"id", "order", "class", "params"})
public class Action implements Serializable {
    private long id;
    private long order;
    private String actionClass;
    private Params params;

    public Action() {
    }

    public Action(long id, long order, String actionClass, Params params) {
        this.id = id;
        this.order = order;
        this.actionClass = actionClass;
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    @JsonGetter("class")
    public String getActionClass() {
        return actionClass;
    }

    @JsonSetter("class")
    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}

