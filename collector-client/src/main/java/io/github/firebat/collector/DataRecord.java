package io.github.firebat.collector;

public class DataRecord<T> implements Flushable {

    protected final DefaultCollector collector;
    protected final T data;

    DataRecord(DefaultCollector collector, T data) {
        this.collector = collector;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void flush() {
        collector.record(this);
    }
}
