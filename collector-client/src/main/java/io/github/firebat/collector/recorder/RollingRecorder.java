package io.github.firebat.collector.recorder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.encoder.EchoEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.StatusPrinter;
import common.json.JsonMapper;
import common.json.MapperBuilder;
import io.github.firebat.collector.DataRecord;
import io.github.firebat.collector.Recorder;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class RollingRecorder implements Recorder {
    private final JsonMapper mapper = MapperBuilder.getDefaultMapper();
    private final RollingFileAppender<String> appender;
    private final FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");

    public RollingRecorder(String file, String pattern, int maxHistory) {
        LoggerContext context = new LoggerContext();
        context.setName("collectorLoggerContext");

        appender = new RollingFileAppender<>();
        appender.setContext(context);
        appender.setName("collectorAppender");
        appender.setFile(file);

        TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy<>();
        policy.setMaxHistory(maxHistory);
        policy.setFileNamePattern(pattern);
        policy.setParent(appender);
        policy.setContext(context);
        policy.start();

//        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//        encoder.setContext(context);
//        encoder.setPattern("%m%n");
//        encoder.start();

        EchoEncoder<String> encoder = new EchoEncoder<>();
        encoder.setContext(context);
        encoder.start();

        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.start();

        StatusPrinter.print(context);
    }

    @Override
    public void record(String collectorName, DataRecord record) {
        // FIXME async
        appender.doAppend(df.format(new Date()) + "\t" + collectorName + "\t" + mapper.writeValueAsString(record.getData()));
    }

    @Override
    public void close() {
        appender.stop();
    }
}
