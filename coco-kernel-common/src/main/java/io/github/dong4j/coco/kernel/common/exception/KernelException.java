package io.github.dong4j.coco.kernel.common.exception;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.io.Serial;

import cn.hutool.core.text.StrFormatter;
import io.github.dong4j.coco.kernel.common.api.BaseCodes;
import io.github.dong4j.coco.kernel.common.api.IResultCode;
import io.github.dong4j.coco.kernel.common.util.ResultCodeUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: 框架基础异常基类 </p>
 *
 * @author dong4j
 * @version 1.0.4
 * @email "mailto:dongshijie@gmail.com"
 * @date 2020.02.09 16:50
 * @since 1.0.4
 */
@Slf4j
@SuppressWarnings("java:S1165")
public class KernelException extends RuntimeException {

    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 1L;

    /** 返回码 */
    protected IResultCode resultCode;
    /** 异常消息参数 */
    protected Object[] args;

    /**
     * Instantiates a new Base exception.
     *
     * @since 1.0.0
     */
    public KernelException() {
        super(BaseCodes.FAILURE.getMessage());
        this.resultCode = BaseCodes.FAILURE;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param msg the msg
     * @since 1.0.0
     */
    public KernelException(String msg) {
        super(msg);
        this.resultCode = BaseCodes.FAILURE;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param code the code
     * @param msg  the msg
     * @since 1.0.0
     */
    public KernelException(int code, String msg) {
        super(msg);
        this.resultCode = new IResultCode() {
            @Serial
            private static final long serialVersionUID = 2590640370242410124L;

            @Override
            public String getMessage() {
                return msg;
            }

            @Override
            public Integer getCode() {
                return code;
            }
        };
    }

    /**
     * Kernel exception
     *
     * @param code code
     * @param msg  msg
     * @since 2023.1.1
     */
    public KernelException(String code, String msg) {
        super(msg);
        this.resultCode = new IResultCode() {
            @Serial
            private static final long serialVersionUID = 2590640370242410124L;

            /**
             * Gets message *
             *
             * @return the message
             * @since 1.6.0
             */
            @Override
            public String getMessage() {
                return msg;
            }

            @Override
            public Integer getCode() {
                return ResultCodeUtils.convert(code);
            }
        };
    }

    /**
     * msg 占位符替换
     *
     * @param msg  msg
     * @param args args
     * @since 1.0.0
     */
    public KernelException(String msg, Object... args) {
        super(StrFormatter.format(msg, args));
        this.resultCode = BaseCodes.FAILURE;
    }

    /**
     * Base exception
     *
     * @param cause cause
     * @since 1.0.0
     */
    public KernelException(Throwable cause) {
        super(cause);
        this.resultCode = BaseCodes.FAILURE;
    }

    /**
     * Base exception
     *
     * @param msg   msg
     * @param cause cause
     * @since 1.0.0
     */
    public KernelException(String msg, Throwable cause) {
        super(msg, cause);
        this.resultCode = BaseCodes.FAILURE;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param resultCode the response enum
     * @since 1.0.0
     */
    public KernelException(@NotNull IResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    /**
     * Base exception
     *
     * @param resultCode result code
     * @param cause      cause
     * @since 1.0.0
     */
    public KernelException(@NotNull IResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param code  the code
     * @param msg   the msg
     * @param cause cause
     * @since 1.0.0
     */
    public KernelException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.resultCode = new IResultCode() {
            @Serial
            private static final long serialVersionUID = 2590640370242410124L;

            @Override
            public String getMessage() {
                return msg;
            }

            @Override
            public Integer getCode() {
                return code;
            }

        };
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param resultCode the response enum
     * @param args       the args
     * @param msg        msg        替换占位符后的消息
     * @since 1.0.0
     */
    public KernelException(IResultCode resultCode, Object[] args, String msg) {
        super(StrFormatter.format(msg, args));
        this.resultCode = resultCode;
        this.args = args;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param resultCode the response enum
     * @param args       the args
     * @param msg        msg
     * @param cause      the cause
     * @since 1.0.0
     */
    public KernelException(IResultCode resultCode, Object[] args, String msg, Throwable cause) {
        super(StrFormatter.format(msg, args), cause);
        this.resultCode = resultCode;
        this.args = args;
    }

    /**
     * Get code
     *
     * @return the string
     * @since 1.6.0
     */
    public String getCode() {
        return ResultCodeUtils.generateCode(this.resultCode);
    }

    /**
     * 重写打印异常堆栈, 转为日志输出.
     *
     * @since 1.0.0
     */
    @Override
    public void printStackTrace() {
        log.error("", this);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s {@code PrintStream} to use for output
     * @since 1.6.0
     */
    @Override
    public void printStackTrace(PrintStream s) {
        log.error("", this);
    }
}
