package com.unc.home.smarthome.policy;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "name", "assigned_objects", "rules"})
public class Policy implements Serializable {
    private long id;
    private String name;
    private List<Long> assignedObjects;
    private List<Rule> rules;

    public Policy() {
    }

    public Policy(long id, String name, List<Long> assignedObjects, List<Rule> rules) {
        this.id = id;
        this.name = name;
        this.assignedObjects = assignedObjects;
        this.rules = rules;
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

    @JsonGetter("assigned_objects")
    public List<Long> getAssignedObjects() {
        return assignedObjects;
    }

    @JsonSetter("assigned_objects")
    public void setAssignedObjects(List<Long> assignedObjects) {
        this.assignedObjects = assignedObjects;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
