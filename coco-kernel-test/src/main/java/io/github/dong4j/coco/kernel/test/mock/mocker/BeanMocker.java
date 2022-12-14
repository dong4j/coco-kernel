package io.github.dong4j.coco.kernel.test.mock.mocker;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map.Entry;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.MockException;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.annotation.MockIgnore;
import io.github.dong4j.coco.kernel.test.mock.util.ReflectionUtils;

/**
 * <p>Description: </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:20
 * @since 1.0.0
 */
public class BeanMocker implements Mocker<Object> {

    /** Clazz */
    private final Class<?> clazz;

    /**
     * Instantiates a new Bean mocker.
     *
     * @param clazz the clazz
     * @since 1.0.0
     */
    @Contract(pure = true)
    BeanMocker(Class<?> clazz) {
        this.clazz = clazz;
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
        try {
            if (mockConfig.isEnabledCircle()) {
                Object cacheBean = mockConfig.getcacheBean(this.clazz.getName());
                if (cacheBean != null) {
                    return cacheBean;
                }
            }
            Object result = this.clazz.getDeclaredConstructor().newInstance();
            mockConfig.cacheBean(this.clazz.getName(), result);
            for (Class<?> currentClass = this.clazz; currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
                // 模拟有setter方法的字段
                for (Entry<Field, Method> entry : ReflectionUtils.fieldAndSetterMethod(currentClass).entrySet()) {
                    Field field = entry.getKey();
                    if (field.isAnnotationPresent(MockIgnore.class)) {
                        continue;
                    }
                    ReflectionUtils
                        .setRefValue(result, entry.getValue(), new BaseMocker<>(field.getGenericType()).mock(mockConfig));
                }
            }
            return result;
        } catch (Exception e) {
            throw new MockException(e);
        }
    }

}
