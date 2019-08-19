package io.github.firebat.collector;

import java.util.concurrent.atomic.AtomicBoolean;

class DefaultCollector implements Collector {

    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final String name;
    private final Recorder recorder;

    DefaultCollector(String name, Recorder recorder) {
        this.name = name;
        this.recorder = recorder;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Record track() {
        if (!enabled.get()) {
            return EMPTY;
        }

        return new MapRecord(this);
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    @Override
    public Flushable track(Object record) {
        if (!enabled.get()) {
            return EMPTY;
        }

        return new DataRecord<>(this, record);
    }

    public void record(DataRecord record) {
        recorder.record(getName(), record);
    }

    private static final Record EMPTY = new Record() {

        @Override
        public Record set(String key, String value) {
            return this;
        }

        @Override
        public Record set(String key, double value) {
            return this;
        }

        @Override
        public Record set(String key, long value) {
            return this;
        }

        @Override
        public void flush() {
        }
    };
}
