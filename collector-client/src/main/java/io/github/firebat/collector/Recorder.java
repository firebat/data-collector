package io.github.firebat.collector;

public interface Recorder {

    void record(String collectorName, DataRecord record);

    void close();
}
