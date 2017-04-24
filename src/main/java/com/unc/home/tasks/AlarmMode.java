package com.unc.home.tasks;

import com.unc.home.Utils;
import com.unc.home.smarthome.AdditionalParameters;

import java.util.Map;


public class AlarmMode implements Task<String> {

    @Override
    public String action(Map<String, AdditionalParameters> parameters) {
        Utils.isAlarm=true;
        return "Alarm mode enable";
    }
}
