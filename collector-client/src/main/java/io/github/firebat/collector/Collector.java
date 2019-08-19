package io.github.firebat.collector;

public interface Collector {

    String getName();

    /**
     * 结构化数据
     */
    Record track();

    /**
     * 外部数据
     */
    Flushable track(Object record);
}
