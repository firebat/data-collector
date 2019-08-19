## collector-client
业务数据收集客户端, 规范日志格式，通常搭配Kafka使用，输出到HBase或Spark。

数据收集配置

    # 默认配置
    GlobalConfig config = new GlobalConfig();
    config.setPath("/home/xxx/logs");
    config.setFile("data-collector.log");
    config.setPattern("data-collector.%d{yyyy-MM-dd}.log.gz");
    config.setMaxHistory(7);
    
    CollectorFactory.configure(config);

业务系统编写收集内容

    Collector call = CollectorFactory.get("exec");
    
    # 自定义值
    call.track().set("uid", "FFXXDEF").set("name", "alice")... .flush()
    
    # 普通对象
    call.track(object).flush()

在 `data-collector.log` 文件中输出

    $timestamp $name {"uid": FFXXDEF", "name": "alice .....}
