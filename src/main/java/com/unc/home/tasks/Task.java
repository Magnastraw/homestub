package com.unc.home.tasks;

import com.unc.home.requests.RequestObject;
import com.unc.home.smarthome.AdditionalParameters;

import java.util.List;
import java.util.Map;


public interface Task {
    void action(Map<String, AdditionalParameters> parameters);
}
