package io.github.dong4j.coco.kernel.common.api;

import io.github.dong4j.coco.kernel.common.KernelBundle;
import io.github.dong4j.coco.kernel.common.annotation.BusinessLevel;
import io.github.dong4j.coco.kernel.common.annotation.ModelSerial;
import io.github.dong4j.coco.kernel.common.annotation.SystemLevel;
import io.github.dong4j.coco.kernel.common.assertion.KernelExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 通用返回结果 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.02 11:36
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@ModelSerial
public enum BaseCodes implements KernelExceptionAssert {
    /** 成功 */
    SUCCESS(Integer.parseInt(Result.SUCCESS_CODE), Result.SUCCESS_MESSAGE),
    /** 默认没有数据响应 */
    @SystemLevel
    DEFAULT_NULL_DATA(2222, KernelBundle.message("code.default.null.data")),
    /** 默认的失败响应 */
    @SystemLevel
    FAILURE(Integer.parseInt(Result.FAILURE_CODE), Result.FAILURE_MESSAGE),
    /** 参数校验失败 */
    @BusinessLevel
    PARAM_VERIFY_ERROR(4100, KernelBundle.message("code.param.verify.error")),
    /** 数据不存在 */
    @SystemLevel
    DATA_ERROR(4101, KernelBundle.message("code.data.error")),
    /** 操作失败 */
    @SystemLevel
    OPTION_FAILURE(4102, KernelBundle.message("code.option.failure")),
    /** Config error base codes */
    @SystemLevel
    CONFIG_ERROR(7000, KernelBundle.message("code.config.error")),
    /** Server inner error base codes. */
    @SystemLevel
    SERVER_INNER_ERROR(5000, KernelBundle.message("code.server.inner.error")),
    /** Service invoke error base codes */
    @SystemLevel
    SERVICE_INVOKE_ERROR(5001, KernelBundle.message("code.service.invoke.error")),
    /** Agent exception */
    @SystemLevel
    AGENT_INVOKE_EXCEPTION(5002, KernelBundle.message("code.client.invoke.error")),
    /** Agent enable exception base codes */
    @SystemLevel
    AGENT_DISABLE_EXCEPTION(5003, KernelBundle.message("code.agent.disable.error")),
    /** Rpc error base codes */
    @SystemLevel
    RPC_ERROR(5004, KernelBundle.message("code.rpc.invoke.error")),
    /** Gateway not fund instances error base codes */
    GATEWAY_NOT_FUND_INSTANCES_ERROR(5005, KernelBundle.message("code.gateway.instances.error")),
    /** 路由配置错误 */
    GATEWAY_ROUTER_ERROR(5006, KernelBundle.message("code.gateway.router.error")),
    /** Agent service not found error base codes */
    @BusinessLevel
    AGENT_SERVICE_NOT_FOUND_ERROR(5007, KernelBundle.message("code.agent.not.found.error")),
    /** 服务器繁忙,请稍后重试 */
    @SystemLevel
    SERVER_BUSY(9998, KernelBundle.message("code.server.busy")),
    /** 服务器异常,无法识别的异常,尽可能对通过判断减少未定义异常抛出 */
    @SystemLevel
    SERVER_ERROR(9999, KernelBundle.message("code.server.error"));

    /** 返回码 */
    private final Integer code;
    /** 返回消息 */
    private final String message;
}
