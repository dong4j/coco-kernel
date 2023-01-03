package io.github.dong4j.coco.kernel.test;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

import io.github.dong4j.coco.kernel.common.constant.App;
import io.github.dong4j.coco.kernel.common.constant.ConfigDefaultValue;
import io.github.dong4j.coco.kernel.common.constant.ConfigKey;
import io.github.dong4j.coco.kernel.common.env.DefaultEnvironment;
import io.github.dong4j.coco.kernel.common.start.LauncherInitiation;
import io.github.dong4j.coco.kernel.common.util.StartUtils;
import io.github.dong4j.coco.kernel.common.util.Tools;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description:  </p>
 *
 * @author dong4j
 * @version 1.3.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.03.23 16:34
 * @since 1.0.0
 */
@Slf4j
public class CocoSpringBootContextLoader extends SpringBootContextLoader {
    /** Environment */
    private ConfigurableEnvironment environment;

    /**
     * Gets environment *
     *
     * @return the environment
     * @since 1.5.0
     */
    @Override
    protected ConfigurableEnvironment getEnvironment() {
        if (this.environment == null) {
            this.environment = new DefaultEnvironment();
        }

        // 各个模块通过 SPI 加载的默认配置
        Properties defaultProperties = setUpTestClass(environment);
        CocoTestPropertySourceUtils.addInlinedPropertiesToEnvironment(environment, defaultProperties);

        return environment;
    }

    /**
     * 通过 SPI 加载所有模块的默认配置（优先级最低）
     *
     * @param environment environment
     * @return the up test class
     * @since 1.0.0
     */
    private static @NotNull Properties setUpTestClass(@NotNull ConfigurableEnvironment environment) {
        StartUtils.setFrameworkVersion();
        System.setProperty(App.START_TYPE, App.START_JUNIT);

        // 读取环境变量,使用 spring boot 的规则 (获取系统参数和 JVM 参数)
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource());

        String appName = System.getProperty(ConfigKey.SpringConfigKey.APPLICATION_NAME);
        // 启动标识
        System.setProperty(App.START_COCO_APPLICATION, App.START_COCO_APPLICATION);
        System.setProperty(ConfigKey.SERVICE_VERSION, "");

        Properties defaultProperties = new Properties();
        defaultProperties.put(ConfigKey.POM_INFO_VERSION, "");
        defaultProperties.put(ConfigKey.POM_INFO_GROUPID, App.BASE_PACKAGES);
        defaultProperties.put(ConfigKey.POM_INFO_ARTIFACTID, appName);
        defaultProperties.put(ConfigKey.SpringConfigKey.MAIN_ALLOW_BEAN_DEFINITION_OVERRIDING, ConfigDefaultValue.TRUE);

        // 加载自定义组件
        List<LauncherInitiation> launcherList = new ArrayList<>();
        ServiceLoader.load(LauncherInitiation.class).forEach(launcherList::add);
        launcherList.stream().sorted(Comparator.comparing(LauncherInitiation::getOrder))
            .toList()
            .forEach(launcherService -> launcherService.launcherWrapper(environment,
                                                                        defaultProperties,
                                                                        appName,
                                                                        true));

        propertySources.addLast(new MapPropertySource(DefaultEnvironment.DEFAULT_PROPERTIES_PROPERTY_SOURCE_NAME,
                                                      Tools.getMapFromProperties(defaultProperties)));

        return defaultProperties;
    }

}
