package io.github.firebat.collector;

import io.github.firebat.collector.config.CollectorConfig;
import io.github.firebat.collector.config.GlobalConfig;
import io.github.firebat.collector.recorder.RollingRecorder;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.File;

public class CollectorFactory {

    private static GlobalConfig config;
    private Recorder recorder;

    public static void configure(GlobalConfig config) {
        CollectorFactory.config = config;
    }

    private static final Supplier<CollectorFactory> supplier = Suppliers.memoize(() -> new CollectorFactory());

    final LoadingCache<String, Collector> collectors = CacheBuilder.newBuilder()
            .build(new CacheLoader<String, Collector>() {

                @Override
                public Collector load(String key) throws Exception {

                    CollectorConfig _config = config.getConfigMap().get(key);

                    DefaultCollector collector = new DefaultCollector(key, recorder);
                    if (_config != null) {
                        collector.setEnabled(_config.isEnabled());
                    }

                    return collector;
                }
            });

    private CollectorFactory() {

        if (config == null) {
            config = new GlobalConfig();
        }

        if (Strings.isNullOrEmpty(config.getPath())) {
            String home = System.getProperty("user.home");
            String path = System.getProperty("LOG_PATH", home + "/logs");
            config.setPath(path);
        }

        if (Strings.isNullOrEmpty(config.getFile())) {
            config.setFile("data-collector.log");
        }

        if (Strings.isNullOrEmpty(config.getPattern())) {
            config.setPattern("data-collector.%d{yyyy-MM-dd}.log.gz");
        }

        File logs = new File(config.getPath());
        String file = new File(logs, config.getFile()).getAbsolutePath();
        String pattern = new File(logs, config.getPattern()).getAbsolutePath();

        recorder = new RollingRecorder(file, pattern, config.getMaxHistory());
    }

    public static Collector getCollector(String name) {
        return supplier.get().collectors.getUnchecked(name);
    }
}
