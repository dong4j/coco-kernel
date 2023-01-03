package io.github.dong4j.coco.kernel.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.github.dong4j.coco.kernel.common.constant.App;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 环境变量 (profile 类型)</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 14:45
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum CocoEnv {
    /** local 默认 */
    LOCAL(App.ENV_LOCAL, "DEBUG"),
    /** dev (开发) */
    DEV(App.ENV_DEV, "DEBUG"),
    /** test (测试) */
    TEST(App.ENV_TEST, "INFO"),
    /** staging (预演环境) */
    PREV(App.ENV_PREV, "INFO"),
    /** prod (正式) */
    PROD(App.ENV_PROD, "INFO");

    /** 等级 */
    private final String name;
    /** log 文件的级别 */
    private final String logLevel;

    /**
     * 获取环境列表
     *
     * @return 环境列表 env list
     * @since 1.0.0
     */
    public static List<String> getEnvList() {
        return Arrays.stream(CocoEnv.values())
            .map(CocoEnv::getName)
            .collect(Collectors.toList());
    }

    /**
     * 环境转换
     *
     * @param env the env
     * @return 环境 coco env
     * @since 1.0.0
     */
    public static CocoEnv of(String env) {
        CocoEnv[] values = CocoEnv.values();
        for (CocoEnv micaEnv : values) {
            if (micaEnv.name.equals(env)) {
                return micaEnv;
            }
        }
        return CocoEnv.DEV;
    }
}
