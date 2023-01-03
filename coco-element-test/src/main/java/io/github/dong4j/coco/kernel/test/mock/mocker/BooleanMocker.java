package io.github.dong4j.coco.kernel.test.mock.mocker;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: Boolean对象模拟器 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:20
 * @since 1.0.0
 */
public class BooleanMocker implements Mocker<Boolean> {

    /**
     * Mocks boolean.
     *
     * @param mockConfig the mock config
     * @return the boolean
     * @since 1.0.0
     */
    @Override
    public Boolean mock(MockConfig mockConfig) {
        return RandomUtils.nextBoolean();
    }

}
