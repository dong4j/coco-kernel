package io.github.dong4j.coco.kernel.common.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import cn.hutool.core.text.StrPool;
import io.github.dong4j.coco.kernel.common.annotation.BusinessLevel;
import io.github.dong4j.coco.kernel.common.annotation.ModelSerial;
import io.github.dong4j.coco.kernel.common.annotation.SystemLevel;
import io.github.dong4j.coco.kernel.common.annotation.ThirdLevel;
import io.github.dong4j.coco.kernel.common.api.IResultCode;
import io.github.dong4j.coco.kernel.common.api.Result;
import io.github.dong4j.coco.kernel.common.asserts.Assertions;
import lombok.experimental.UtilityClass;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * <p>Description: 根据错误码生成一定规则的错误编码【A-项目标识-错误码】 </p>
 *
 * @author zhubo
 * @version 1.5.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.06.11 09:58
 * @since 1.4.0
 */
@UtilityClass
public class ResultCodeUtils {
    /** 枚举类缓存Map */
    private static final Map<String, Map<String, Field>> ENUM_FIELD_CACHE = new ConcurrentHashMap<>();

    /**
     * 初始化枚举类字段等到缓存Map中
     *
     * @param resultCode result code
     * @since 1.5.0
     */
    private void initEnumMap(@NotNull IResultCode resultCode) {
        String simpleName = resultCode.getClass().getSimpleName();
        Field[] fields = resultCode.getClass().getFields();

        Map<String, Field> collect = Stream.of(fields).collect(toMap(Field::getName, identity(), (k, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", k));
        }, LinkedHashMap::new));

        ENUM_FIELD_CACHE.put(simpleName, collect);
    }

    /**
     * 根据错误码生成一定规则的错误编码【B-项目标识-错误码】
     *
     * @param resultCode result code
     * @return the string
     * @since 1.5.0
     */
    public @NotNull String generateCode(@NotNull IResultCode resultCode) {
        String enumName = resultCode.name();
        String simpleName = resultCode.getClass().getSimpleName();
        // 处理错误码中的项目标识部分
        String modelNameSerial = ModelSerial.DEFAULT;
        if (resultCode.getClass().isAnnotationPresent(ModelSerial.class)) {
            modelNameSerial = resultCode.getClass().getAnnotation(ModelSerial.class).value();
        }
        modelNameSerial = modelNameSerial.concat(StrPool.DASHED);

        // 缓存中获取, 如果缓存中没有, 则需要先初始化到缓存
        if (!ENUM_FIELD_CACHE.containsKey(simpleName)) {
            initEnumMap(resultCode);
        }
        Map<String, Field> stringFieldMap = ENUM_FIELD_CACHE.get(simpleName);

        // 处理错误码中的错误来源部分
        String errorSource = "B";
        if (stringFieldMap.containsKey(enumName)) {
            Field field = stringFieldMap.get(enumName);
            // 获取错误来源S(系统错误)、B(业务错误)、T(第三方服务错误)
            if (field != null && field.isAnnotationPresent(BusinessLevel.class)) {
                errorSource = BusinessLevel.class.getSimpleName().substring(0, 1);
            }
            if (field != null && field.isAnnotationPresent(SystemLevel.class)) {
                errorSource = SystemLevel.class.getSimpleName().substring(0, 1);
            }
            if (field != null && field.isAnnotationPresent(ThirdLevel.class)) {
                errorSource = ThirdLevel.class.getSimpleName().substring(0, 1);
            }
        }
        errorSource = errorSource.concat(StrPool.DOT);

        return errorSource + modelNameSerial + resultCode.getCode();
    }

    /**
     * 将【B-项目标识-错误码】格式的 code 反解析为 int 类型 错误码
     *
     * @param code code
     * @return the integer
     * @since 1.5.0
     */
    @SuppressWarnings("all")
    public @NotNull Integer convert(String code) {
        Assertions.notBlank(code, "code 不能为 空");

        String[] split = code.split(StrPool.DASHED);
        if (split.length == 2) {
            return Integer.parseInt(split[1]);
        } else if (split.length == 3) {
            return Integer.parseInt(split[2]);
        } else {
            return Integer.parseInt(code);
        }
    }

    /**
     * Convert
     *
     * @param result result
     * @return the integer
     * @since 1.5.0
     */
    public @NotNull Integer convert(Result<?> result) {
        Assertions.notNull(result, "result 不能为 空");
        return convert(result.getCode());
    }
}
