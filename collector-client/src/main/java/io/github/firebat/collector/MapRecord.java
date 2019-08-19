package io.github.firebat.collector;

import com.google.common.collect.Maps;

import java.util.Map;

public class MapRecord extends DataRecord<Map<String, Object>> implements Record {

    MapRecord(DefaultCollector collector) {
        super(collector, Maps.newLinkedHashMap());
    }

    public Record set(String key, String value) {
        data.put(key, value);
        return this;
    }

    public Record set(String key, double value) {
        data.put(key, value);
        return this;
    }

    public Record set(String key, long value) {
        data.put(key, value);
        return this;
    }
}
