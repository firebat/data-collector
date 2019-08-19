package io.github.firebat.collector;

public interface Record extends Flushable{

    Record set(String key, String value);

    Record set(String key, double value);

    Record set(String key, long value);
}
