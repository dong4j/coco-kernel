package io.github.dong4j.coco.kernel.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

import cn.hutool.core.text.StrPool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Description: 请求响应返回结构封装, 所有请求都要求返回此类.
 * 为避免在 v4 中引入过多的 v5 依赖, 我们将 {@link Result} 分为了 2 个子类, 分别是 {@link StandardResult} 和 {@link info.spark.starter.core.api.R},
 * StandardResult 在 v4 使用, R 在 v5 使用.
 * {@code
 * Result<Data> result = StandardResult.success(data);
 * Result<Data> result = R.success(data);
 * <p>
 * 反序列化:
 * Result<Data> result = JsonUtils.parse(json, new TypeReference<Result<Data>>(){});
 * Result result = JsonUtils.parse(json, Result.class);
 * }**
 * <p>
 * 由于 jackson 在反序列化抽象类时存在多态问题, 我们使用 {@link Result#TYPE_NAME} 来标识 json 需要被反序列化的 class, 为了避免 v5 的响应 json 数据在 v4 中反序列化失败,
 * 我们将所有的 json 都添加了 {@link Result#TYPE_NAME}, 全部都将反序列化为 {@link StandardResult}, 业务端在处理 json 结果时, 应该忽略 'type' 字段.
 * 比较推荐的反序列化方式是根据不同的框架来进行反序列化, 避免使用 {@link Result}:
 * {@code
 * v4: Result result = JsonUtils.parse(json, StandardResult.class);
 * v5: Result result = JsonUtils.parse(json, R.class);
 * }**
 * </p>
 *
 * @param <T> parameter
 * @author dong4j
 * @version 1.2.4
 * @email "mailto:dongshijie@gmail.com"
 * @date 2020.02.06 17:44
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuppressWarnings("all")
public abstract class Result<T> implements Serializable {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;
    /** CODE */
    public static final String CODE = "code";
    /** SUCCESS */
    public static final String SUCCESS = "success";
    /** DATA */
    public static final String DATA = "data";
    /** MESSAGE */
    public static final String MESSAGE = "message";
    /** TRACE_ID */
    public static final String TRACE_ID = "traceId";
    /** EXTEND */
    public static final String EXTEND = "extend";
    /** 反序列化时处理多态问题的标识 */
    public static final String TYPE_NAME = "StandardResult";
    /** 请求成功代码 */
    public static final String SUCCESS_CODE = "2000";
    /** 请求成功消息 */
    public static final String SUCCESS_MESSAGE = KernelBundle.message("success.message");
    /** 默认的失败代码 */
    public static final String FAILURE_CODE = "4000";
    /** 默认的失败消息 */
    public static final String FAILURE_MESSAGE = KernelBundle.message("failure.message");

    /** 请求响应状态码 */
    @ApiModelProperty(value = "状态码", required = true, example = "2000")
    protected String code;
    /** 请求响应成功标识 */
    @ApiModelProperty(value = "请求成功的状态", required = true, example = "true")
    protected boolean success;
    /** 请求响应的数据 */
    @ApiModelProperty(value = "承载的数据", required = true)
    protected T data;
    /** 请求响应的消息 */
    @ApiModelProperty(value = "返回的消息", required = true, example = "操作成功")
    protected String message = "";

    /**
     * Result
     *
     * @param code    code
     * @param message message
     * @param data    data
     * @since 1.0.0
     */
    @Contract(pure = true)
    protected Result(@NotNull String code, String message, T data) {
        this.code = code;
        if (!SUCCESS_CODE.equals(code) && !code.contains(StrPool.DASHED) && !code.contains(StrPool.DOT)) {
            this.code = "S.F-" + code;
        }
        this.data = data;
        this.message = message;
        this.success = (SUCCESS_CODE.equals(code));
    }

    /**
     * Is ok boolean
     *
     * @return the boolean
     * @since 1.0.0
     */
    @JsonIgnore
    public boolean isOk() {
        return isOk(this);
    }

    /**
     * 请求是否成功
     *
     * @param result result
     * @return the boolean
     * @since 1.0.0
     */
    @Contract("null -> false")
    public static boolean isOk(@Nullable Result<?> result) {
        return result != null && (SUCCESS_CODE.equals(result.getCode()) && result.isSuccess());
    }

    /**
     * Is fail boolean
     *
     * @return the boolean
     * @since 1.0.0
     */
    @JsonIgnore
    public boolean isFail() {
        return isFail(this);
    }

    /**
     * 请求是否失败
     *
     * @param result result
     * @return the boolean
     * @since 1.0.0
     */
    @Contract("null -> true")
    public static boolean isFail(@Nullable Result<?> result) {
        return !isOk(result);
    }

}
