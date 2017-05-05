package com.unc.home.smarthome.policy;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "operator", "class", "params", "children"})
public class Condition implements Serializable {
    private long id;
    private String operator;
    private String conditionClass;
    private Params params;
    private List<Condition> children;

    public Condition() {
    }

    public Condition(long id, String operator, String conditionClass, Params params, List<Condition> children) {
        this.id = id;
        this.operator = operator;
        this.conditionClass = conditionClass;
        this.params = params;
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonGetter("class")
    public String getConditionClass() {
        return conditionClass;
    }

    @JsonSetter("class")
    public void setConditionClass(String conditionClass) {
        this.conditionClass = conditionClass;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public List<Condition> getChildren() {
        return children;
    }

    public void setChildren(List<Condition> children) {
        this.children = children;
    }
}

