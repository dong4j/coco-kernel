package io.github.dong4j.coco.kernel.common.util;

import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.WebApplicationType;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import io.github.dong4j.coco.kernel.common.VersionBundle;
import io.github.dong4j.coco.kernel.common.constant.App;
import io.github.dong4j.coco.kernel.common.constant.ConfigKey;
import io.github.dong4j.coco.kernel.common.enums.LibraryEnum;
import io.github.dong4j.coco.kernel.common.start.CocoAutoConfiguration;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: 启动相关工具类 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.11 12:56
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public class StartUtils {

    /** COMPONENTS_INFO */
    private static final List<String> COMPONENTS_INFO = Lists.newArrayList();
    /** CUSTOM_COMPONENT_INFOS */
    private static final List<CustomInfo> CUSTOM_COMPONENT = Lists.newArrayList();

    /**
     * Set custom info
     *
     * @param customInfo custom info
     * @since 1.4.0
     */
    public static void addCustomInfo(CustomInfo customInfo) {
        CUSTOM_COMPONENT.add(customInfo);
    }

    /**
     * Sets custom info *
     *
     * @param customInfo custom info
     * @since 1.4.0
     */
    public static void addCustomInfo(List<CustomInfo> customInfo) {
        CUSTOM_COMPONENT.addAll(customInfo);
    }

    /**
     * 向 MDC 中设置组件名, 当应用启动完成后根据相应模块名输出信息
     *
     * @param componetName the componet name
     * @since 1.0.0
     */
    public static void loadComponent(String componetName) {
        log.trace("初始化自动装类: [{}]", componetName);
        String components = MDC.get(App.LIBRARY_NAME);
        String str = StrUtil.isNotBlank(components) ? componetName + StrPool.AT + components : componetName + StrPool.AT;
        MDC.put(App.LIBRARY_NAME, str);
    }

    /**
     * Print simple info.
     *
     * @since 1.0.0
     */
    public static void printSimpleInfo() {
        printStartedInfo(new String[0]);
    }

    /**
     * 启动完成后输出 url 方便快速验证.
     *
     * @param compoments 应用依赖的组件
     * @since 1.0.0
     */
    public static void printStartedInfo(@NotNull String[] compoments) {
        log.debug("{}", Arrays.toString(compoments));
        String appName = ConfigKit.getAppName();
        // [xxxxx] start finished, perfect!
        String startInfo = "[" + appName + "] start finished, perfect!";

        int maxLength = startInfo.length();

        if (!WebApplicationType.NONE.name().equals(System.getProperty(App.APP_TYPE))) {

            List<LibraryEnum> companentList = CocoAutoConfiguration.Constant.COMPONENTS;
            Collections.sort(companentList);

            for (LibraryEnum libraryEnum : companentList) {
                Integer portStr = ConfigKit.getPort();
                String hostUrl = buildUrl(portStr);

                if (LibraryEnum.REST.getName().equals(libraryEnum.getName())) {
                    portStr = ConfigKit.getPort();
                    hostUrl = buildUrl(portStr);
                    System.setProperty(App.START_URL, hostUrl + libraryEnum.getUri());
                }

                StringBuilder urlInfo = padding(libraryEnum.getName()).append(hostUrl).append(libraryEnum.getUri());

                maxLength = Math.max(maxLength, urlInfo.length());
                COMPONENTS_INFO.add(urlInfo.toString());
                urlInfo.setLength(0);
            }
            companentList.clear();
        }

        showStartInfo(startInfo, maxLength);
    }

    /**
     * Padding
     *
     * @param libraryName library name
     * @return the string
     * @since 1.5.0
     */
    public static @NotNull StringBuilder padding(String libraryName) {
        return new StringBuilder()
            .append(StrUtil.padAfter(libraryName, 20, " "))
            .append(": ");
    }

    /**
     * Show start info
     *
     * @param startInfo start info
     * @param maxLength max length
     * @since 1.4.0
     */
    private static void showStartInfo(String startInfo, int maxLength) {
        int currentMaxLength = maxLength;

        // 添加自定义启动信息
        for (CustomInfo customInfo : CUSTOM_COMPONENT) {
            COMPONENTS_INFO.add(customInfo.custom());
            currentMaxLength = Math.max(currentMaxLength, customInfo.custom().length());
        }

        // 确定信息长度
        String separate = "-";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= currentMaxLength; i++) {
            stringBuilder.append(separate);
        }

        // 日志配置文件: MarkerPatternSelector.PatternMatch.key = processor
        Marker processorMarker = MarkerFactory.getMarker("processor");

        log.info(processorMarker, "");
        log.info(processorMarker, "");
        log.info(processorMarker, stringBuilder.toString());
        log.error(processorMarker, showInfo(currentMaxLength, startInfo));
        log.info(processorMarker, stringBuilder.toString());

        if (CollectionUtil.isNotEmpty(COMPONENTS_INFO)) {
            log.info(processorMarker, showInfo(currentMaxLength, ":::: usefull information ::::"));
            log.info(processorMarker, stringBuilder.toString());

            for (String component : COMPONENTS_INFO) {
                log.info(processorMarker, component);
            }
            log.info(processorMarker, stringBuilder.toString());
        }
        log.info(processorMarker, "");
        log.info(processorMarker, "");

        COMPONENTS_INFO.clear();
        CUSTOM_COMPONENT.clear();
    }

    /**
     * <p>Description: </p>
     *
     * @author dong4j
     * @version 1.4.0
     * @email "mailto:dong4j@gmail.com"
     * @date 2020.05.15 04:12
     * @since 1.4.0
     */
    public interface CustomInfo {

        /**
         * Custom
         *
         * @return the string
         * @since 1.4.0
         */
        String custom();
    }

    /**
     * Build url string
     *
     * @return the string
     * @since 1.0.0
     */
    public static String buildUrl() {
        return buildUrl(ConfigKit.getPort());
    }

    /**
     * Build url string
     *
     * @param port port
     * @return the string
     * @since 1.0.0
     */
    private static String buildUrl(Integer port) {
        return buildUrl(NetUtil.getLocalhostStr(), port);
    }

    /**
     * Build url string
     *
     * @param ip   ip
     * @param port port
     * @return the string
     * @since 1.0.0
     */
    @SuppressWarnings("HttpUrlsUsage")
    private static String buildUrl(String ip, Integer port) {
        String contextPath = ConfigKit.getContextPath();
        return StrFormatter.format("http://{}:{}{}",
                                   ip,
                                   port,
                                   contextPath);
    }

    /**
     * 将信息输出在中间
     *
     * @param maxLength the max length
     * @param info      the info
     * @return the string
     * @since 1.0.0
     */
    public static @NotNull String showInfo(int maxLength, @NotNull String info) {
        if (maxLength > info.length()) {
            int disparity = (maxLength - info.length()) / 2;
            if (disparity > 0) {
                return generateEmptyString(disparity) + info;
            }
        }
        return info;
    }

    /**
     * Generate empty string string
     *
     * @param length length
     * @return the string
     * @since 1.0.0
     */
    @NotNull
    private static String generateEmptyString(int length) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            str.append(" ");
        }
        return str.toString();
    }

    /**
     * 直接添加 url
     *
     * @param url the url
     * @since 1.0.0
     */
    public static void addUrl(String url) {
        if (StrUtil.isNotBlank(url)) {
            COMPONENTS_INFO.add(url);
        }
    }

    /**
     * Java version string.
     *
     * @return the string
     * @since 1.0.0
     */
    @SuppressWarnings("java:S108")
    public static String javaVersion() {
        return "JDK " + SystemUtils.JAVA_VERSION;
    }

    /**
     * Gets classpath.
     *
     * @return the classpath
     * @since 1.0.0
     */
    @NotNull
    public static String getClasspath() {
        String classPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        return classPath.replace(StrUtil.SLASH, File.separator).replace("\\", File.separator);
    }

    /**
     * Set framework version
     *
     * @since 1.4.0
     */
    public static void setFrameworkVersion() {
        System.setProperty(ConfigKey.APPLICATION_FRAMEWORK_VERSION,
                           App.FRAMEWORK_VERSION_PREFIX + VersionBundle.message("coco.framework.version"));
    }

}
