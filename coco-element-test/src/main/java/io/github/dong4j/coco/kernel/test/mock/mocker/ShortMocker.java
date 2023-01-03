package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.NotNull;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: 模拟Short对象</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:24
 * @since 1.0.0
 */
public class ShortMocker implements Mocker<Short> {

    /**
     * Mocks short.
     *
     * @param mockConfig the mock config
     * @return the short
     * @since 1.0.0
     */
    @Override
    public Short mock(@NotNull MockConfig mockConfig) {
        return (short) RandomUtils.nextInt(mockConfig.getShortRange()[0], mockConfig.getShortRange()[1]);
    }

}
