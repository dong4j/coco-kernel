package io.github.dong4j.coco.kernel.common.api;

import com.google.common.collect.Maps;

import com.fasterxml.jackson.annotation.JsonTypeName;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.Collections;
import java.util.Map;

import cn.hutool.core.text.StrFormatter;
import io.github.dong4j.coco.kernel.common.util.ChainMap;
import io.github.dong4j.coco.kernel.common.util.ResultCodeUtils;

/**
 * <p>Description: 将标准返回的字段全部放在了 {@link Result},此类在 v5 使用 </p>
 *
 * @param <T> parameter
 * @author dong4j
 * @version 1.2.4
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.02.06 19:58
 * @see Result
 * @since 1.0.0
 */
@JsonTypeName(value = Result.TYPE_NAME)
@SuppressWarnings(value = {"PMD.ClassNamingShouldBeCamelRule"})
public final class R<T> extends Result<T> {
    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 3077918845714343375L;

    /**
     * R
     *
     * @param code    code
     * @param message message
     * @param data    data
     * @since 1.0.0
     */
    @Contract(pure = true)
    private R(String code, String message, T data) {
        super(code, message, data);
    }

    /**
     * Succeed result
     *
     * @param <T> parameter
     * @return the result
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> Result<T> succeed() {
        return succeed((T) Collections.emptyMap());
    }

    /**
     * Succeed result
     *
     * @param <T>  parameter
     * @param data data
     * @return the result
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    public static <T> Result<T> succeed(T data) {
        return succeed(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    /**
     * Succeed result
     *
     * @param <T>  parameter
     * @param code code
     * @param msg  msg
     * @param data data
     * @return the result
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    public static <T> Result<T> succeed(String code, String msg, T data) {
        return build(code, msg, data);
    }

    /**
     * Failed result
     *
     * @param <T> parameter
     * @return the result
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    public static <T> Result<T> failed() {
        return failed(FAILURE_MESSAGE);
    }

    /**
     * Failed result
     *
     * @param <T> parameter
     * @param msg msg
     * @return the result
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    public static <T> Result<T> failed(String msg) {
        return failed(FAILURE_CODE, msg);
    }

    /**
     * Failed result
     *
     * @param <T>  parameter
     * @param code code
     * @param msg  msg
     * @return the result
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    public static <T> Result<T> failed(String code, String msg) {
        return failed(code, msg, (T) Collections.emptyMap());
    }

    /**
     * Failed result
     *
     * @param <T>        parameter
     * @param resultCode result code
     * @return the result
     * @since 1.0.0
     */
    @NotNull
    public static <T> Result<T> failed(@NotNull IResultCode resultCode) {
        return failed(ResultCodeUtils.generateCode(resultCode), resultCode.getMessage());
    }

    /**
     * Failed result
     *
     * @param <T>        parameter
     * @param resultCode result code
     * @param msg        msg
     * @return the result
     * @since 1.0.0
     */
    @NotNull
    public static <T> Result<T> failed(@NotNull IResultCode resultCode, String msg) {
        return failed(ResultCodeUtils.generateCode(resultCode), StrFormatter.format(resultCode.getMessage(), msg));
    }

    /**
     * Failed result
     *
     * @param <T>        parameter
     * @param resultCode result code
     * @param msg        msg
     * @param data       data
     * @return the result
     * @since 1.0.0
     */
    @NotNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static <T> Result<T> failed(@NotNull IResultCode resultCode, String msg, T data) {
        return failed(ResultCodeUtils.generateCode(resultCode), StrFormatter.format(resultCode.getMessage(), msg), data);
    }

    /**
     * Failed result
     *
     * @param <T>  parameter
     * @param code code
     * @param msg  msg
     * @param data data
     * @return the result
     * @since 1.0.0
     */
    @NotNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static <T> Result<T> failed(String code, String msg, T data) {
        return build(code, msg, data);
    }

    /**
     * Build result
     *
     * @param <T>  parameter
     * @param code code
     * @param msg  msg
     * @return the result
     * @since 1.7.0
     */
    @Contract("_, _ -> new")
    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> Result<T> build(String code, String msg) {
        return build(code, msg, (T) Collections.emptyMap());
    }

    /**
     * Build result
     *
     * @param <T>  parameter
     * @param code code
     * @param msg  msg
     * @param data data
     * @return the result
     * @since 1.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    @NotNull
    public static <T> Result<T> build(String code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    /**
     * Status result
     *
     * @param <T>        parameter
     * @param expression expression
     * @return the result
     * @since 1.0.0
     */
    @Contract("_ -> !null")
    public static <T> Result<T> status(boolean expression) {
        return status(expression, "");
    }

    /**
     * Status result
     *
     * @param <T>        parameter
     * @param expression expression
     * @param resultCode result code
     * @return the result
     * @since 1.0.0
     */
    @Contract("_, _ -> !null")
    public static <T> Result<T> status(boolean expression, @NotNull IResultCode resultCode) {
        return status(expression, resultCode.getMessage());
    }

    /**
     * Status result
     *
     * @param <T>        parameter
     * @param expression expression
     * @param message    message
     * @return the result
     * @since 1.0.0
     */
    @Contract("_, _ -> !null")
    public static <T> Result<T> status(boolean expression, String message) {
        return expression ? succeed() : failed(message);
    }

    /**
     * Values result
     *
     * @param args 键值对一一对应
     * @return the result
     * @since 1.0.0
     */
    @NotNull
    public static Result<Map<String, Object>> values(@NotNull Object... args) {
        int separate = 2;
        if (args.length % separate != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        }
        Map<String, Object> keyValueMap = Maps.newHashMapWithExpectedSize(args.length / 2);
        for (int i = args.length - separate; i >= 0; i -= separate) {
            keyValueMap.put((String) args[i], args[i + 1]);
        }
        return succeed(keyValueMap);
    }

    /**
     * Map map
     *
     * @param <T>  parameter
     * @param data data
     * @return the map
     * @since 1.0.0
     */
    @NotNull
    public static <T> Map<String, Object> map(T data) {
        return map(ResultCodeUtils.generateCode(BaseCodes.SUCCESS),
                   true,
                   data,
                   BaseCodes.SUCCESS.getMessage());
    }

    /**
     * Map map
     *
     * @param <T>     parameter
     * @param code    code
     * @param success success
     * @param data    data
     * @param message message
     * @return the map
     * @since 1.0.0
     */
    @NotNull
    public static <T> Map<String, Object> map(String code,
                                              boolean success,
                                              T data,
                                              String message) {

        return ChainMap.build(5)
            .put(CODE, code)
            .put(SUCCESS, success)
            .put(DATA, data)
            .put(MESSAGE, message);

    }

    /**
     * Fail map map
     *
     * @param resultCode result code
     * @return the map
     * @since 1.0.0
     */
    @NotNull
    public static Map<String, Object> failMap(@NotNull IResultCode resultCode) {
        return map(ResultCodeUtils.generateCode(resultCode), false, null, resultCode.getMessage());
    }

    /**
     * Map map
     *
     * @param <T>        parameter
     * @param resultCode result code
     * @param data       data
     * @return the map
     * @since 1.0.0
     */
    @NotNull
    public static <T> Map<String, Object> map(@NotNull IResultCode resultCode, T data) {
        return map(ResultCodeUtils.generateCode(resultCode),
                   BaseCodes.SUCCESS.getCode().equals(resultCode.getCode()),
                   data, resultCode.getMessage());
    }
}
