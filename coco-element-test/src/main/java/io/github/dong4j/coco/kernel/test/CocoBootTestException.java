package io.github.dong4j.coco.kernel.test;

import java.io.Serial;

/**
 * <p>Description: test 异常 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:22
 * @since 1.0.0
 */
class CocoBootTestException extends RuntimeException {

    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = -286968533868184170L;

    /**
     * Instantiates a new Coco boot test exception.
     *
     * @param message the message
     * @since 1.0.0
     */
    CocoBootTestException(String message) {
        super(message);
    }
}
