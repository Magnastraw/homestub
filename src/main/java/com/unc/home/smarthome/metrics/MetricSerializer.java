package com.unc.home.smarthome.metrics;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;


public class MetricSerializer extends StdSerializer<MetricValue> {

    public MetricSerializer() {
        this(null);
    }

    public MetricSerializer(Class<MetricValue> t) {
        super(t);
    }

    @Override
    public void serialize(MetricValue metricValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("objectId", metricValue.getMetric().getObjectId());
        jsonGenerator.writeNumberField("subobjectId", metricValue.getMetric().getSubobjectId());
        jsonGenerator.writeNumberField("value", metricValue.getValue());
        jsonGenerator.writeStringField("registryDate", metricValue.getRegistryDate());
        jsonGenerator.writeNumberField("specId", metricValue.getMetric().getSpecId());
        jsonGenerator.writeEndObject();
    }
}
