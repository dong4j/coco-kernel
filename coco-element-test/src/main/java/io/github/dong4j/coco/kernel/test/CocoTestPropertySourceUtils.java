package io.github.dong4j.coco.kernel.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Properties;

import io.github.dong4j.coco.kernel.common.env.DefaultEnvironment;
import io.github.dong4j.coco.kernel.common.util.Tools;
import lombok.experimental.UtilityClass;

/**
 * <p>Description:  </p>
 *
 * @author dong4j
 * @version 1.3.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.03.23 16:47
 * @since 1.0.0
 */
@UtilityClass
class CocoTestPropertySourceUtils extends TestPropertySourceUtils {
    /** logger */
    private static final Log LOGGER = LogFactory.getLog(CocoTestPropertySourceUtils.class);
    /** INLINED_PROPERTIES_PROPERTY_SOURCE_NAME */
    private static final String INLINED_PROPERTIES_PROPERTY_SOURCE_NAME = DefaultEnvironment.DEFAULT_PROPERTIES_PROPERTY_SOURCE_NAME;

    /**
     * Add inlined properties to environment *
     *
     * @param environment       environment
     * @param defaultProperties default properties
     * @since 1.0.0
     */
    static void addInlinedPropertiesToEnvironment(ConfigurableEnvironment environment, Properties defaultProperties) {
        Assert.notNull(environment, "'environment' must not be null");
        Assert.notNull(defaultProperties, "'defaultProperties' must not be null");
        if (!ObjectUtils.isEmpty(defaultProperties)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("添加各个模块默认配置到 environment: "
                             + ObjectUtils.nullSafeToString(defaultProperties));
            }
            MapPropertySource ps = (MapPropertySource)
                environment.getPropertySources().get(INLINED_PROPERTIES_PROPERTY_SOURCE_NAME);
            if (ps == null) {
                ps = new MapPropertySource(INLINED_PROPERTIES_PROPERTY_SOURCE_NAME, new LinkedHashMap<>());
                // 优先级最低, 作为默认配置, 保证应用启动正常
                environment.getPropertySources().addLast(ps);
                environment.setDefaultProfiles("junit", "default");
            }
            ps.getSource().putAll(Tools.getMapFromProperties(defaultProperties));

        }
    }
}
