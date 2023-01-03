package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.NotNull;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: Integer对象模拟器</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:19
 * @since 1.0.0
 */
public class IntegerMocker implements Mocker<Integer> {

    /**
     * Mocks integer.
     *
     * @param mockConfig the mock config
     * @return the integer
     * @since 1.0.0
     */
    @Override
    public Integer mock(@NotNull MockConfig mockConfig) {
        return RandomUtils.nextInt(mockConfig.getIntRange()[0], mockConfig.getIntRange()[1]);
    }

}
