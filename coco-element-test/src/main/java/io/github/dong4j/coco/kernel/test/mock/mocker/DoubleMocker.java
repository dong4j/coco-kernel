package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.NotNull;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: Double对象模拟器</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:20
 * @since 1.0.0
 */
public class DoubleMocker implements Mocker<Double> {

    /**
     * Mocks double.
     *
     * @param mockConfig the mock config
     * @return the double
     * @since 1.0.0
     */
    @Override
    public Double mock(@NotNull MockConfig mockConfig) {
        return RandomUtils.nextDouble(mockConfig.getDoubleRange()[0], mockConfig.getDoubleRange()[1]);
    }

}
