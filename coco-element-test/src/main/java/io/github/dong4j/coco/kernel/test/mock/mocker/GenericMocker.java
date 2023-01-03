package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.ParameterizedType;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;

/**
 * <p>Description: 模拟泛型</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:20
 * @since 1.0.0
 */
public class GenericMocker implements Mocker<Object> {

    /** Type */
    private final ParameterizedType type;

    /**
     * Instantiates a new Generic mocker.
     *
     * @param type the type
     * @since 1.0.0
     */
    @Contract(pure = true)
    GenericMocker(ParameterizedType type) {
        this.type = type;
    }

    /**
     * Mocks object.
     *
     * @param mockConfig the mock config
     * @return the object
     * @since 1.0.0
     */
    @Override
    public Object mock(MockConfig mockConfig) {
        return new BaseMocker(type.getRawType(), type.getActualTypeArguments()).mock(mockConfig);
    }

}
