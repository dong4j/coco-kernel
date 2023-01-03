package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: BigDecimal对象模拟器 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:20
 * @since 1.0.0
 */
public class BigDecimalMocker implements Mocker<BigDecimal> {

    /**
     * Mocks big decimal.
     *
     * @param mockConfig the mock config
     * @return the big decimal
     * @since 1.0.0
     */
    @Override
    public BigDecimal mock(@NotNull MockConfig mockConfig) {
        return BigDecimal.valueOf(RandomUtils.nextDouble(mockConfig.getDoubleRange()[0], mockConfig.getDoubleRange()[1]));
    }

}
