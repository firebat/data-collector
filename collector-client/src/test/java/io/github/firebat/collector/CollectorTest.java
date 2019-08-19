package io.github.firebat.collector;

import io.github.firebat.collector.config.CollectorConfig;
import io.github.firebat.collector.config.GlobalConfig;
import io.github.firebat.collector.Collector;
import io.github.firebat.collector.CollectorFactory;

import java.util.HashMap;
import java.util.Map;

public class CollectorTest {

    public static void main(String[] args) {
        GlobalConfig config = new GlobalConfig();
        config.setPath("/tmp");
        config.setFile("data-collector.log");
        config.setMaxHistory(7);
        config.setPattern("data-collector.%d{yyyy-MM-dd-HH}.log.gz");

        Map<String, CollectorConfig> collector = new HashMap<>();
        CollectorConfig c = new CollectorConfig();
        c.setEnabled(true);
        collector.put("exec", c);
        config.setConfigMap(collector);

        CollectorFactory.configure(config);

        Collector call = CollectorFactory.getCollector("exec");
        call.track().set("name", "alice").set("id", "SDF32XDF").set("created", 1234).flush();

        Map<String, String> record = new HashMap<>();
        record.put("hello", "world");
        record.put("caller", "198287899");
        call.track(record).flush();
    }
}
