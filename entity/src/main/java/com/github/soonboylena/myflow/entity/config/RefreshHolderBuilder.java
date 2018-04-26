package com.github.soonboylena.myflow.entity.config;

import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

public class RefreshHolderBuilder {

    private static final Logger logger = LoggerFactory.getLogger(RefreshHolderBuilder.class);

    private final ResourceLoader defaultResourceLoader = new DefaultResourceLoader();

    public ConfigureHolder register(String location, ConfigureBuilder builder) {

        Resource resource = defaultResourceLoader.getResource(location);
        ConfigureHolder build = builder.build(location);

        RefreshableConfigHolder refreshableConfigHolder = new RefreshableConfigHolder(build);

        Thread dirWatcherThread = new Thread(() -> {

            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();

                File file = resource.getFile();
                String fileName = resource.getFilename();
                logger.trace("监控文件名：{}", fileName);
                String parent = file.getParent();
                logger.trace("监控文件所在文件夹：{}", parent);
                Path path = Paths.get(parent);

                boolean stop = false;

                WatchKey watchKey = path.register(watchService
                        , ENTRY_MODIFY
                        , OVERFLOW
//                        , ENTRY_DELETE
                );

                logger.debug("开始监控文件变更");
                while (!stop) {
                    WatchKey key = watchService.poll(2, TimeUnit.SECONDS);
                    if (key == null) {
                        Thread.yield();
                        continue;
                    }
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path change = ev.context();
                        logger.trace("changedFile: {}", change);
                        // TODO : chage格式为什么有后缀？
                        if (!change.toString().startsWith(fileName)) {
                            break;
                        }

                        if (kind == OVERFLOW) {
                            logger.debug("文件 {} overflow", change);
                            Thread.yield();
                            continue;
                        } else if (kind == ENTRY_DELETE) {
                            stop = true;
                            logger.debug("文件 {} 被删除了，不再监控配置文件", change);
                            break;
                        } else if (kind == ENTRY_MODIFY) {
                            logger.debug("文件 {} 被更改了，重新读取配置", change);
                            ConfigureHolder newHolder = builder.build(location);
                            refreshableConfigHolder.refresh(newHolder);
                            logger.debug("文件 {} 重新加载完毕", change);
                        }
                        boolean valid = key.reset();
                        if (!valid) {
                            break;
                        }
                    }
                    Thread.yield();
                }

            } catch (IOException | InterruptedException e) {
                logger.error("文件监控出错了。配置文件更新将停止. ", e);
            }
        });

        dirWatcherThread.start();
        // 支持热更新
        return refreshableConfigHolder;
    }
}
