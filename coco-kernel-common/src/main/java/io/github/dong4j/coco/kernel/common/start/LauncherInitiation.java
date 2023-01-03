package io.github.dong4j.coco.kernel.common.start;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;
import java.util.Properties;

import io.github.dong4j.coco.kernel.common.util.ConfigKit;
import io.github.dong4j.coco.kernel.common.util.JsonUtils;

/**
 * <p>Description: launcher 扩展 用于一些组件发现</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 04:12
 * @since 1.0.0
 */
public interface LauncherInitiation extends Ordered {
    /** log */
    Logger LOG = LoggerFactory.getLogger(LauncherInitiation.class);

    /**
     * 启动时 处理 SpringApplicationBuilder
     *
     * @param env               系统变量 Environment
     * @param defaultProperties 默认配置
     * @param appName           服务名
     * @param isLocalLaunch     is local launch
     * @since 1.0.0
     */
    default void launcherWrapper(ConfigurableEnvironment env,
                                 @NotNull Properties defaultProperties,
                                 String appName,
                                 boolean isLocalLaunch) {
        this.advance(appName);
        Map<String, Object> map = this.launcher(env, appName, isLocalLaunch);
        // 优先使用 192.168 网段, 避免后期因服务器存在多个网卡导致 ip 获取错误
        map.putIfAbsent("spring.cloud.inetutils.preferred-networks", "192.168.2,192.168.8");
        map.putIfAbsent("spring.cloud.inetutils.ignored-interfaces[0]", "veth*");
        map.putIfAbsent("spring.cloud.inetutils.ignored-interfaces[1]", "vnet*");
        map.putIfAbsent("spring.cloud.inetutils.ignored-interfaces[2]", "docker*");

        if (ConfigKit.isDebugModel()) {
            LOG.debug("{} 组件默认配置:\n{}", this.getName(), JsonUtils.toJson(map, true));
        }

        defaultProperties.putAll(map);
    }

    /**
     * 在启动 Spring Boot 之前执行自定义逻辑
     *
     * @param appName app name
     * @since 1.0.0
     */
    default void advance(String appName) {}

    /**
     * Launcher wrapper *
     *
     * @param env           env
     * @param appName       app name
     * @param isLocalLaunch is local launch
     * @return the chain map
     * @since 1.7.1
     */
    Map<String, Object> launcher(ConfigurableEnvironment env, String appName, boolean isLocalLaunch);

    /**
     * 获取排列顺序
     *
     * @return order order
     * @since 1.0.0
     */
    @Override
    default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 获取组件名
     *
     * @return the name
     * @since 1.0.0
     */
    String getName();

}
