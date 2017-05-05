package com.unc.home.smarthome.policy;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "name", "root_condition", "then_actions", "else_actions"})
public class Rule implements Serializable {
    private long id;
    private String name;
    private Condition rootCondition;
    private List<Action> thenActions;
    private List<Action> elseActions;

    public Rule() {
    }

    public Rule(long id, String name, Condition rootCondition, List<Action> thenActions, List<Action> elseActions) {
        this.id = id;
        this.name = name;
        this.rootCondition = rootCondition;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("root_condition")
    public Condition getRootCondition() {
        return rootCondition;
    }

    @JsonSetter("root_condition")
    public void setRootCondition(Condition rootCondition) {
        this.rootCondition = rootCondition;
    }

    @JsonGetter("then_actions")
    public List<Action> getThenActions() {
        return thenActions;
    }

    @JsonSetter("then_actions")
    public void setThenActions(List<Action> thenActions) {
        this.thenActions = thenActions;
    }

    @JsonGetter("else_actions")
    public List<Action> getElseActions() {
        return elseActions;
    }

    @JsonSetter("else_actions")
    public void setElseActions(List<Action> elseActions) {
        this.elseActions = elseActions;
    }
}
