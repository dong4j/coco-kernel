package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: BigInteger对象模拟器 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:20
 * @since 1.0.0
 */
public class BigIntegerMocker implements Mocker<BigInteger> {

    /**
     * Mocks big integer.
     *
     * @param mockConfig the mock config
     * @return the big integer
     * @since 1.0.0
     */
    @Override
    public BigInteger mock(@NotNull MockConfig mockConfig) {
        return BigInteger.valueOf(RandomUtils.nextLong(mockConfig.getLongRange()[0], mockConfig.getLongRange()[1]));
    }

}
