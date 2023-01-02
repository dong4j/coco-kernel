package io.github.dong4j.coco.kernel.common.assertion;

import io.github.dong4j.coco.kernel.common.api.IResultCode;
import io.github.dong4j.coco.kernel.common.exception.KernelException;

/**
 * <p>Description: 全局错误异常断言 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2019.12.24 12:20
 * @since 1.0.0
 */
public interface KernelExceptionAssert extends IResultCode, IAssert {
    /** serialVersionUID */
    long serialVersionUID = 3077918845714343375L;

    /**
     * New exceptions base exception.
     *
     * @param args the args
     * @return the base exception
     * @since 1.0.0
     */
    @Override
    default KernelException newException(Object... args) {
        return new KernelException(this, args, this.getMessage());
    }

    /**
     * New exceptions base exception.
     *
     * @param t    the t
     * @param args the args
     * @return the base exception
     * @since 1.0.0
     */
    @Override
    default KernelException newException(Throwable t, Object... args) {
        return new KernelException(this, args, this.getMessage(), t);
    }

}
