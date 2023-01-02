package io.github.dong4j.coco.kernel.common.asserts;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import io.github.dong4j.coco.kernel.common.exception.AssertionFailedException;
import io.github.dong4j.coco.kernel.common.function.CheckedRunnable;

/**
 * <p>Description: </p>
 *
 * @author dong4j
 * @version 1.0.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.11.18 17:03
 * @since 1.7.0
 */
final class AssertNull {

    /**
     * Assert null
     *
     * @since 1.7.0
     */
    private AssertNull() {
    }

    /**
     * Is null
     *
     * @param object object
     * @since 1.6.0
     */
    static void isNull(@Nullable Object object) {
        isNull(object, "参数必须是 null");
    }

    /**
     * Is null
     *
     * @param object  object
     * @param message message
     * @since 1.6.0
     */
    static void isNull(@Nullable Object object, String message) {
        isNull(object, () -> new AssertionFailedException(message));
    }

    /**
     * Is null
     *
     * @param object            object
     * @param exceptionSupplier message supplier
     * @since 1.6.0
     */
    static void isNull(@Nullable Object object, Supplier<? extends RuntimeException> exceptionSupplier) {
        isNull(object, exceptionSupplier, () -> {});
    }

    /**
     * Is null
     *
     * @param object            object
     * @param exceptionSupplier exception supplier
     * @param runnable          runnable
     * @since 1.7.0
     */
    static void isNull(@Nullable Object object, Supplier<? extends RuntimeException> exceptionSupplier, CheckedRunnable runnable) {
        if (object != null) {
            AssertUtils.fail(exceptionSupplier, runnable);
        }
    }
}
