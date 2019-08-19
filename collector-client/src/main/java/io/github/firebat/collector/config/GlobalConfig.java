package io.github.firebat.collector.config;

import com.google.common.collect.Maps;

import java.util.Map;

public class GlobalConfig {

    private Map<String, CollectorConfig> configMap = Maps.newHashMap();

    // 根路径
    private String path;

    // 文件名
    private String file;

    // 压缩文件名
    private String pattern;

    // 最多保留历史个数
    private int maxHistory = 7;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getMaxHistory() {
        return maxHistory;
    }

    public void setMaxHistory(int maxHistory) {
        this.maxHistory = maxHistory;
    }

    public Map<String, CollectorConfig> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, CollectorConfig> configMap) {
        this.configMap = configMap;
    }

}
