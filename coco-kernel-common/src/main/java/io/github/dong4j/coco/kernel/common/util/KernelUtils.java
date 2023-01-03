package io.github.dong4j.coco.kernel.common.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import io.github.dong4j.coco.kernel.common.constant.ConfigKey;
import lombok.experimental.UtilityClass;

/**
 * <p>Description:  </p>
 *
 * @author dong4j
 * @version 1.0.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.06.19 15:27
 * @since 1.5.0
 */
@UtilityClass
public class KernelUtils {

    /**
     * Is v 5 framework
     *
     * @return the boolean
     * @since 1.4.0
     */
    public static boolean isV5Framework() {
        String frameworkVersion = System.getProperty(ConfigKey.APPLICATION_FRAMEWORK_VERSION);
        return StringUtils.hasText(frameworkVersion)
               && frameworkVersion.startsWith("V5_");
    }

    /**
     * Gets app version *
     *
     * @return the app version
     * @since 1.4.0
     */
    public static String getAppVersion() {
        return getVersion(ConfigKey.SERVICE_VERSION);
    }

    /**
     * Gets framework version *
     *
     * @return the framework version
     * @since 1.4.0
     */
    public static String getFrameworkVersion() {
        return getVersion(ConfigKey.APPLICATION_FRAMEWORK_VERSION);
    }

    /**
     * Get version
     *
     * @param key key
     * @return the string
     * @since 1.4.0
     */
    private static String getVersion(String key) {
        if (!isV5Framework()) {
            return "null";
        }
        return System.getProperty(key, "null");
    }

    /**
     * 获取启动模式
     * 本地开发模式还是服务器部署模式
     *
     * @return the string
     * @since 1.0.0
     * @deprecated 将在 v4 全部迁移到 v5 后删除, v5 请使用 ConfigKit
     */
    @NotNull
    @Deprecated
    public static Boolean isLocalLaunch() {
        return !notLocalLaunch();
    }

    /**
     * 非本地开发环境 (只要 start.type = shell 都认为是非开发环境)
     *
     * @return the boolean
     * @since 1.0.0
     * @deprecated 将在 v4 全部迁移到 v5 后删除, v5 请使用 ConfigKit
     */
    @NotNull
    @Deprecated
    public static Boolean notLocalLaunch() {
        return "shell".equals(System.getProperty("start.type"));
    }
}
