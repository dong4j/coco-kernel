package io.github.dong4j.coco.kernel.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description: 项目标识 </p>
 *
 * @author zhubo
 * @version 1.0.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.06.10 16:16
 * @since 1.5.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelSerial {
    /** DEFAULT */
    String DEFAULT = "M";

    /**
     * Value
     *
     * @return the string
     * @since 1.6.0
     */
    String value() default DEFAULT;
}
