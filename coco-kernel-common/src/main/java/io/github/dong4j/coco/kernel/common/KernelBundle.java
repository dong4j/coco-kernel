package io.github.dong4j.coco.kernel.common;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

import io.github.dong4j.coco.kernel.common.bundle.BundleBase;
import io.github.dong4j.coco.kernel.common.bundle.BundleUtils;

/**
 * <p>Description: </p>
 *
 * @author dong4j
 * @version 1.0.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.05.19 10:22
 * @since 1.4.0
 */
@SuppressWarnings("all")
public final class KernelBundle extends BundleBase {
    /** BUNDLE */
    private static final String BUNDLE = "messages.KernelBundle";
    /** ourBundle */
    private static ResourceBundle ourBundle;

    /**
     * Util bundle
     *
     * @since 1.4.0
     */
    private KernelBundle() {}

    /**
     * Message
     *
     * @param key    key
     * @param params params
     * @return the string
     * @since 1.4.0
     */
    @Nls
    @NotNull
    public static String message(@NotNull String key, Object... params) {
        return message(getUtilBundle(), key, params);
    }

    /**
     * Gets util bundle *
     *
     * @return the util bundle
     * @since 1.4.0
     */
    @NotNull
    private static ResourceBundle getUtilBundle() {
        if (ourBundle != null) {
            return ourBundle;
        }
        ourBundle = ResourceBundle.getBundle(BUNDLE);
        return ourBundle;
    }

    /**
     * Load bundle from plugin
     *
     * @param pluginClassLoader plugin class loader
     * @since 1.4.0
     */
    public static void loadBundleFromPlugin(@Nullable ClassLoader pluginClassLoader) {
        ResourceBundle bundle = BundleUtils.loadLanguageBundle(pluginClassLoader, BUNDLE);
        if (bundle != null) {
            ourBundle = bundle;
        }
    }
}
