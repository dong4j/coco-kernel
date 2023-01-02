package io.github.dong4j.coco.kernel.common.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: 枚举工具类</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:22
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
@SuppressWarnings("checkstyle:IllegalImport")
public class EnumUtils {
    /** 枚举类缓存 */
    private static final Map<Class<?>, Object> ENUM_MAP = new ConcurrentHashMap<>();

    /**
     * 根据条件获取枚举对象
     *
     * @param <T>       the type parameter
     * @param cla       枚举类
     * @param predicate 筛选条件
     * @return enum optional
     * @since 1.0.0
     */
    public static <T> Optional<T> of(@NotNull Class<T> cla, Predicate<T> predicate) {
        if (!cla.isEnum()) {
            log.info("Class 不是枚举类 cla ={}", cla);
            return Optional.empty();
        }
        Object obj = ENUM_MAP.get(cla);
        T[] ts;
        if (obj == null) {
            ts = cla.getEnumConstants();
            ENUM_MAP.put(cla, ts);
        } else {
            // noinspection unchecked
            ts = (T[]) obj;
        }
        return Arrays.stream(ts).filter(predicate).findAny();
    }

    /**
     * 通过枚举的 index 获取枚举
     *
     * @param <T>     the type parameter
     * @param clazz   the clazz
     * @param ordinal the ordinal   需要的枚举值在设定的枚举类中的顺序, 以 0 开始
     * @return t t
     * @author xiehao
     * @since 1.0.0
     */
    public static <T extends Enum<T>> T indexOf(@NotNull Class<T> clazz, int ordinal) {
        return clazz.getEnumConstants()[ordinal];
    }

    /**
     * 传入的参数 name 指的是枚举值的名称, 一般是大写加下划线的
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @param name  the name
     * @return Enum T
     * @author xiehao
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Enum<T>> T nameOf(Class<T> clazz, String name) {
        return Enum.valueOf(clazz, name);
    }
}
