package io.github.dong4j.coco.kernel.test.mock.mocker;

import io.github.dong4j.coco.kernel.test.mock.MockConfig;
import io.github.dong4j.coco.kernel.test.mock.Mocker;
import io.github.dong4j.coco.kernel.test.mock.util.RandomUtils;

/**
 * <p>Description: Character对象模拟器</p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:19
 * @since 1.0.0
 */
public class CharacterMocker implements Mocker<Character> {

    /**
     * Mocks character.
     *
     * @param mockConfig the mock config
     * @return the character
     * @since 1.0.0
     */
    @Override
    public Character mock(MockConfig mockConfig) {
        char[] charSeed = mockConfig.getCharSeed();
        return charSeed[RandomUtils.nextInt(0, charSeed.length)];
    }

}
