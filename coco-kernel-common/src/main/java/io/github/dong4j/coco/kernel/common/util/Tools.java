package io.github.dong4j.coco.kernel.common.util;

import com.google.common.primitives.Ints;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 * <p>Description: 工具包集合,只做简单的调用,不删除原有工具类 </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.01.27 18:13
 * @since 1.0.0
 */
@UtilityClass
@SuppressWarnings("all")
public class Tools {
    /** 特殊字符正则,sql特殊字符和空白符 */
    public static final Pattern SPECIAL_CHARS_REGEX = Pattern.compile("[`'\"|/,;()-+*%#·•�　\\s]");

    /**
     * 断言,必须不能为 null
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = $.requireNotNull(bar);
     * }
     * </pre></blockquote>
     *
     * @param <T> the type of the reference
     * @param obj the object reference to check for nullity
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     * @since 1.0.0
     */
    @Contract(value = "null -> fail; !null -> param1", pure = true)
    public static <T> T requireNotNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    /**
     * 断言,必须不能为 null
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = $.requireNotNull(bar, "bar must not be null");
     *     this.baz = $.requireNotNull(baz, "baz must not be null");
     * }
     * </pre></blockquote>
     *
     * @param <T>     the type of the reference
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     * @since 1.0.0
     */
    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static <T> T requireNotNull(T obj, String message) {
        return Objects.requireNonNull(obj, message);
    }

    /**
     * 断言,必须不能为 null
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = $.requireNotNull(bar, () -> "bar must not be null");
     * }
     * </pre></blockquote>
     *
     * @param <T>             the type of the reference
     * @param obj             the object reference to check for nullity
     * @param messageSupplier supplier of the detail message to be used in the event that a {@code NullPointerException} is thrown
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     * @since 1.0.0
     */
    @Contract("null, _ -> fail; !null, _ -> param1")
    public static <T> T requireNotNull(T obj, Supplier<String> messageSupplier) {
        return Objects.requireNonNull(obj, messageSupplier);
    }

    /**
     * 判断对象为true
     *
     * @param object 对象
     * @return 对象是否为true boolean
     * @since 1.0.0
     */
    @Contract(value = "null -> false", pure = true)
    public static boolean isTrue(@Nullable Boolean object) {
        return BooleanUtil.isTrue(object);
    }

    /**
     * 判断对象为false
     *
     * @param object 对象
     * @return 对象是否为false boolean
     * @since 1.0.0
     */
    @Contract(value = "null -> true", pure = true)
    public static boolean isFalse(@Nullable Boolean object) {
        return BooleanUtil.isFalse(object);
    }

    /**
     * 判断对象是否为null
     * <p>
     * This method exists to be used as a
     * {@link java.util.function.Predicate}, {@code context($::isNull)}
     * </p>
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is {@code null} otherwise     {@code false}
     * @see java.util.function.Predicate
     * @since 1.0.0
     */
    @Contract(value = "!null -> false; null -> true", pure = true)
    public static boolean isNull(@Nullable Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * 判断对象是否 not null
     * <p>
     * This method exists to be used as a
     * {@link java.util.function.Predicate}, {@code context($::notNull)}
     * </p>
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is non-{@code null}     otherwise {@code false}
     * @see java.util.function.Predicate
     * @since 1.0.0
     */
    @Contract(value = "!null -> true; null -> false", pure = true)
    public static boolean isNotNull(@Nullable Object obj) {
        return Objects.nonNull(obj);
    }

    /**
     * 首字母变小写
     *
     * @param str 字符串
     * @return {String}
     * @since 1.0.0
     */
    @NotNull
    public static String firstCharToLower(String str) {
        return StrUtil.lowerFirst(str);
    }

    /**
     * 首字母变大写
     *
     * @param str 字符串
     * @return {String}
     * @since 1.0.0
     */
    @NotNull
    public static String firstCharToUpper(String str) {
        return StrUtil.upperFirst(str);
    }

    /**
     * 判断不为空字符串
     * <pre>
     * $.isNotBlank(null)     = false
     * $.isNotBlank("")          = false
     * $.isNotBlank(" ")     = false
     * $.isNotBlank("bob")     = true
     * $.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is     not empty and not null and not whitespace
     * @see Character#isWhitespace
     * @since 1.0.0
     */
    @Contract("null -> false")
    public static boolean isNotBlank(@Nullable CharSequence cs) {
        return StrUtil.isNotBlank(cs);
    }

    /**
     * 有 任意 一个 Blank
     *
     * @param css CharSequence
     * @return boolean boolean
     * @since 1.0.0
     */
    public static boolean isAnyBlank(CharSequence... css) {
        return StrUtil.hasBlank(css);
    }

    /**
     * 判断是否全为非空字符串
     *
     * @param css CharSequence
     * @return boolean boolean
     * @since 1.0.0
     */
    public static boolean isNoneBlank(CharSequence... css) {
        return StrUtil.isAllBlank(css);
    }

    /**
     * 判断对象是数组
     *
     * @param obj the object to check
     * @return 是否数组 boolean
     * @since 1.0.0
     */
    @Contract("null -> false")
    public static boolean isArray(@Nullable Object obj) {
        return ArrayUtil.isArray(obj);
    }

    /**
     * 对象不为空 object、map、list、set、字符串、数组
     *
     * @param obj the object to check
     * @return 是否不为空 boolean
     * @since 1.0.0
     */
    @Contract("null -> false")
    public static boolean isNotEmpty(@Nullable Object obj) {
        return ObjectUtil.isNotEmpty(obj);
    }

    /**
     * 判断数组为空
     *
     * @param array the array to check
     * @return 数组是否为空 boolean
     * @since 1.0.0
     */
    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(@Nullable Object[] array) {
        return ObjectUtil.isEmpty(array);
    }

    /**
     * 判断数组不为空
     *
     * @param array 数组
     * @return 数组是否不为空 boolean
     * @since 1.0.0
     */
    @Contract("null -> false")
    public static boolean isNotEmpty(@Nullable Object[] array) {
        return ObjectUtil.isNotEmpty(array);
    }

    /**
     * 对象组中是否存在 Empty Object
     *
     * @param os 对象组
     * @return boolean boolean
     * @since 1.0.0
     */
    public static boolean hasEmpty(@NotNull Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断空对象 object、map、list、set、字符串、数组
     *
     * @param obj the object to check
     * @return 数组是否为空 boolean
     * @since 1.0.0
     */
    @Contract("null -> true")
    public static boolean isEmpty(@Nullable Object obj) {
        return ObjectUtil.isEmpty(obj);
    }

    /**
     * 对象组中是否全是 Empty Object
     *
     * @param os 对象组
     * @return boolean boolean
     * @since 1.0.0
     */
    public static boolean allEmpty(@NotNull Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将字符串中特定模式的字符转换成map中对应的值
     * <p>
     * use: format("my name is ${name}, and i like ${like}!", {"name":"L.cm", "like": "Java"})
     *
     * @param message 需要转换的字符串
     * @param params  转换所需的键值对集合
     * @return 转换后的字符串 string
     * @since 1.0.0
     */
    @Contract("null, _ -> null; !null, null -> !null")
    public static String format(@Nullable String message, @Nullable Map<String, Object> params) {
        return StrFormatter.format(message, params);
    }

    /**
     * 同 log 格式的 format 规则
     * <p>
     * use: format("my name is {}, and i like {}!", "L.cm", "Java")
     *
     * @param message   需要转换的字符串
     * @param arguments 需要替换的变量
     * @return 转换后的字符串 string
     * @since 1.0.0
     */
    @Contract("null, _ -> null")
    public static String format(@Nullable String message, @Nullable Object... arguments) {
        return StrFormatter.format(message, arguments);
    }

    /**
     * 清理字符串,清理出某些不可见字符和一些sql特殊字符
     *
     * @param txt 文本
     * @return {String}
     * @since 1.0.0
     */
    @Contract("null -> null")
    @Nullable
    public static String cleanText(@Nullable String txt) {
        if (txt == null) {
            return null;
        }
        return SPECIAL_CHARS_REGEX.matcher(txt).replaceAll("");
    }

    /**
     * 获取标识符,用于参数清理
     *
     * @param param 参数
     * @return 清理后的标识符 string
     * @since 1.0.0
     */
    @Contract("null -> null")
    @Nullable
    public static String cleanIdentifier(@Nullable String param) {
        if (param == null) {
            return null;
        }
        StringBuilder paramBuilder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            if (Character.isJavaIdentifierPart(c)) {
                paramBuilder.append(c);
            }
        }
        return paramBuilder.toString();
    }

    /**
     * 比较两个对象是否相等. <br>
     * 相同的条件有两个,满足其一即可: <br>
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等 boolean
     * @since 1.0.0
     */
    @Contract(value = "null, !null -> false; !null, null -> false", pure = true)
    public static boolean equals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * 比较两个对象是否不相等. <br>
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return 是否不eq boolean
     * @since 1.0.0
     */
    @Contract(value = "null, !null -> true; !null, null -> true", pure = true)
    public static boolean isNotEqual(Object o1, Object o2) {
        return !Objects.equals(o1, o2);
    }

    /**
     * 返回对象的 hashCode
     *
     * @param obj Object
     * @return hashCode int
     * @since 1.0.0
     */
    public static int hashCode(@Nullable Object obj) {
        return Objects.hashCode(obj);
    }

    /**
     * 如果对象为null,返回默认值
     *
     * @param object       Object
     * @param defaultValue 默认值
     * @return Object object
     * @since 1.0.0
     */
    @Contract(value = "!null, _ -> param1; null, _ -> param2", pure = true)
    public static Object defaultIfNull(@Nullable Object object, Object defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * 判断一个字符串是否是数字
     *
     * @param cs the CharSequence to check, may be null
     * @return {boolean}
     * @since 1.0.0
     */
    public static boolean isNumeric(CharSequence cs) {
        return NumberUtil.isNumber(cs);
    }

    /**
     * <p>Convert a <code>String</code> to an <code>int</code>, returning
     * <code>zero</code> if the conversion fails.</p>
     * <p>If the string is <code>null</code>, <code>zero</code> is returned.</p>
     * <pre>
     *   $.toInt(null) = 0
     *   $.toInt("")   = 0
     *   $.toInt("1")  = 1
     * </pre>
     *
     * @param value the string to convert, may be null
     * @return the int represented by the string, or <code>zero</code> if     conversion fails
     * @since 1.0.0
     */
    public static int toInt(Object value) {
        return NumberUtil.parseInt(String.valueOf(value));
    }

    /**
     * <p>Convert a <code>String</code> to a <code>long</code>, returning
     * <code>zero</code> if the conversion fails.</p>
     * <p>If the string is <code>null</code>, <code>zero</code> is returned.</p>
     * <pre>
     *   $.toLong(null) = 0L
     *   $.toLong("")   = 0L
     *   $.toLong("1")  = 1L
     * </pre>
     *
     * @param value the string to convert, may be null
     * @return the long represented by the string, or <code>0</code> if     conversion fails
     * @since 1.0.0
     */
    public static long toLong(Object value) {
        return NumberUtil.parseLong(String.valueOf(value));
    }

    /**
     * <p>Convert a <code>String</code> to a <code>long</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toLong(null, 1L) = 1L
     *   $.toLong("", 1L)   = 1L
     *   $.toLong("1", 0L)  = 1L
     * </pre>
     *
     * @param value        the string to convert, may be null
     * @param defaultValue the default value
     * @return the long represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static long toLong(Object value, long defaultValue) {
        try {
            return toLong(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * <p>Convert a <code>String</code> to an <code>Double</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toDouble(null, 1) = 1.0
     *   $.toDouble("", 1)   = 1.0
     *   $.toDouble("1", 0)  = 1.0
     * </pre>
     *
     * @param value the string to convert, may be null
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static Double toDouble(Object value) {
        return NumberUtil.parseDouble(String.valueOf(value));
    }

    /**
     * <p>Convert a <code>String</code> to an <code>Double</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toDouble(null, 1) = 1.0
     *   $.toDouble("", 1)   = 1.0
     *   $.toDouble("1", 0)  = 1.0
     * </pre>
     *
     * @param value        the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static Double toDouble(Object value, Double defaultValue) {
        try {
            return toDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * <p>Convert a <code>String</code> to an <code>Float</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toFloat(null, 1) = 1.00f
     *   $.toFloat("", 1)   = 1.00f
     *   $.toFloat("1", 0)  = 1.00f
     * </pre>
     *
     * @param value the string to convert, may be null
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static Float toFloat(Object value) {
        return NumberUtil.parseFloat(String.valueOf(value));
    }

    /**
     * <p>Convert a <code>String</code> to an <code>Float</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toFloat(null, 1) = 1.00f
     *   $.toFloat("", 1)   = 1.00f
     *   $.toFloat("1", 0)  = 1.00f
     * </pre>
     *
     * @param value        the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static Float toFloat(Object value, Float defaultValue) {
        try {
            return toFloat(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * <p>Convert a <code>String</code> to an <code>Boolean</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toBoolean("true", true)  = true
     *   $.toBoolean("false")        = false
     *   $.toBoolean("", false)       = false
     * </pre>
     *
     * @param value the string to convert, may be null
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static Boolean toBoolean(Object value) {
        return toBoolean(value, null);
    }

    /**
     * <p>Convert a <code>String</code> to an <code>Boolean</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toBoolean("true", true)  = true
     *   $.toBoolean("false")        = false
     *   $.toBoolean("", false)       = false
     * </pre>
     *
     * @param value        the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    @Contract("null, _ -> param2")
    public static Boolean toBoolean(Object value, Boolean defaultValue) {
        if (value != null) {
            String val = String.valueOf(value);
            val = val.toLowerCase().trim();
            return Boolean.parseBoolean(val);
        }
        return defaultValue;
    }

    /**
     * 转换为Integer集合<br>
     *
     * @param str 结果被转换的值
     * @return 结果 list
     * @since 1.0.0
     */
    @NotNull
    public static List<Integer> toIntList(String str) {
        return Arrays.asList(toIntArray(str));
    }

    /**
     * 转换为Integer数组<br>
     *
     * @param str 被转换的值
     * @return 结果 integer [ ]
     * @since 1.0.0
     */
    public static Integer[] toIntArray(String str) {
        return toIntArray(",", str);
    }

    /**
     * 转换为Integer数组<br>
     *
     * @param split 分隔符
     * @param str   被转换的值
     * @return 结果 integer [ ]
     * @since 1.0.0
     */
    public static Integer[] toIntArray(String split, String str) {
        if (StrUtil.isEmpty(str)) {
            return new Integer[] {};
        }
        String[] arr = str.split(split);
        Integer[] ints = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int v = toInt(arr[i], 0);
            ints[i] = v;
        }
        return ints;
    }

    /**
     * <p>Convert a <code>String</code> to an <code>int</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string is <code>null</code>, the default value is returned.</p>
     * <pre>
     *   $.toInt(null, 1) = 1
     *   $.toInt("", 1)   = 1
     *   $.toInt("1", 0)  = 1
     * </pre>
     *
     * @param value        the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     * @since 1.0.0
     */
    public static int toInt(Object value, int defaultValue) {
        try {
            return NumberUtil.parseInt(String.valueOf(value));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 转换为Integer集合<br>
     *
     * @param split 分隔符
     * @param str   被转换的值
     * @return 结果 list
     * @since 1.0.0
     */
    @NotNull
    public static List<Integer> toIntList(String split, String str) {
        return Arrays.asList(toIntArray(split, str));
    }

    /**
     * 转换为String集合<br>
     *
     * @param str 结果被转换的值
     * @return 结果 list
     * @since 1.0.0
     */
    @NotNull
    public static List<String> toStrList(String str) {
        return Arrays.asList(toStrArray(str));
    }

    /**
     * 转换为String数组<br>
     *
     * @param str 被转换的值
     * @return 结果 string [ ]
     * @since 1.0.0
     */
    @NotNull
    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    /**
     * 转换为String数组<br>
     *
     * @param split 分隔符
     * @param str   被转换的值
     * @return 结果 string [ ]
     * @since 1.0.0
     */
    @NotNull
    public static String[] toStrArray(String split, String str) {
        if (isBlank(str)) {
            return new String[0];
        }
        return str.split(split);
    }

    /**
     * 判断是否为空字符串
     * <pre class="code">
     * $.isBlank(null) = true
     * $.isBlank("") = true
     * $.isBlank(" ") = true
     * $.isBlank("12345") = false
     * $.isBlank(" 12345 ") = false
     * </pre>
     *
     * @param cs the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},     its length is greater than 0, and it does not contain
     * whitespace only
     * @see Character#isWhitespace
     * @since 1.0.0
     */
    @Contract("null -> true")
    public static boolean isBlank(@Nullable CharSequence cs) {
        return StrUtil.isBlank(cs);
    }

    /**
     * 转换为String集合<br>
     *
     * @param split 分隔符
     * @param str   被转换的值
     * @return 结果 list
     * @since 1.0.0
     */
    @NotNull
    public static List<String> toStrList(String split, String str) {
        return Arrays.asList(toStrArray(split, str));
    }

    /**
     * 将集合拼接成字符串,默认使用`,`拼接
     *
     * @param coll the {@code Collection} to convert
     * @return the delimited {@code String}
     * @since 1.0.0
     */
    @NotNull
    public static String join(Collection<?> coll) {
        return StrUtil.join(",", coll);
    }

    /**
     * 将集合拼接成字符串,默认指定分隔符
     *
     * @param coll  the {@code Collection} to convert
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     * @since 1.0.0
     */
    @NotNull
    public static String join(Collection<?> coll, String delim) {
        return StrUtil.join(delim, coll);
    }

    /**
     * 将数组拼接成字符串,默认使用`,`拼接
     *
     * @param arr the array to display
     * @return the delimited {@code String}
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static String join(Object[] arr) {
        return StrUtil.join(",", arr);
    }

    /**
     * 将数组拼接成字符串,默认指定分隔符
     *
     * @param arr   the array to display
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     * @since 1.0.0
     */
    @NotNull
    @Contract(pure = true)
    public static String join(Object[] arr, String delim) {
        return StrUtil.join(delim, arr);
    }

    /**
     * 分割 字符串
     *
     * @param str       字符串
     * @param delimiter 分割符
     * @return 字符串数组 string [ ]
     * @since 1.0.0
     */
    @NotNull
    public static String[] split(@Nullable String str, @Nullable String delimiter) {
        return StrUtil.splitToArray(str, delimiter);
    }

    /**
     * 分割 字符串 删除常见 空白符
     *
     * @param str       字符串
     * @param delimiter 分割符
     * @return 字符串数组 string [ ]
     * @since 1.0.0
     */
    @NotNull
    public static String[] splitTrim(@Nullable String str, @Nullable String delimiter) {
        return StrUtil.splitTrim(str, delimiter).toArray(new String[0]);
    }

    /**
     * 生成uuid
     *
     * @return UUID string
     * @since 1.0.0
     */
    @NotNull
    public static String randomUid() {
        return UUID.fastUUID().toString();
    }

    /**
     * 随机数生成
     *
     * @param count 字符长度
     * @return 随机数 string
     * @since 1.0.0
     */
    @NotNull
    public static String random(int count) {
        return RandomUtil.randomNumbers(count);
    }


    /**
     * 将对象序列化成json字符串
     *
     * @param object javaBean
     * @return jsonString json字符串
     * @since 1.0.0
     */
    @NotNull
    public static String toJson(Object object) {
        return JsonUtils.toJson(object);
    }

    /**
     * 将对象序列化成 json byte 数组
     *
     * @param object javaBean
     * @return jsonString json字符串
     * @since 1.0.0
     */
    public static byte[] toJsonAsBytes(Object object) {
        return JsonUtils.toJsonAsBytes(object);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(String jsonString) {
        return JsonUtils.readTree(jsonString);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param in InputStream
     * @return jsonString json字符串
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(InputStream in) {
        return JsonUtils.readTree(in);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param content content
     * @return jsonString json字符串
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(byte[] content) {
        return JsonUtils.readTree(content);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonParser JsonParser
     * @return jsonString json字符串
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(JsonParser jsonParser) {
        return JsonUtils.readTree(jsonParser);
    }

    /**
     * 将json byte 数组反序列化成对象
     *
     * @param <T>       T 泛型标记
     * @param bytes     json bytes
     * @param valueType class
     * @return Bean t
     * @since 1.0.0
     */
    @Nullable
    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        return JsonUtils.parse(bytes, valueType);
    }

    /**
     * 将json反序列化成对象
     *
     * @param <T>        T 泛型标记
     * @param jsonString jsonString
     * @param valueType  class
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(String jsonString, Class<T> valueType) {
        return JsonUtils.parse(jsonString, valueType);
    }

    /**
     * 将json反序列化成对象
     *
     * @param <T>       T 泛型标记
     * @param in        InputStream
     * @param valueType class
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(InputStream in, Class<T> valueType) {
        return JsonUtils.parse(in, valueType);
    }

    /**
     * 将json反序列化成对象
     *
     * @param <T>           T 泛型标记
     * @param bytes         bytes
     * @param typeReference 泛型类型
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(byte[] bytes, TypeReference<T> typeReference) {
        return JsonUtils.parse(bytes, typeReference);
    }

    /**
     * 将json反序列化成对象
     *
     * @param <T>           T 泛型标记
     * @param jsonString    jsonString
     * @param typeReference 泛型类型
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(String jsonString, TypeReference<T> typeReference) {
        return JsonUtils.parse(jsonString, typeReference);
    }

    /**
     * 将json反序列化成对象
     *
     * @param <T>           T 泛型标记
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(InputStream in, TypeReference<T> typeReference) {
        return JsonUtils.parse(in, typeReference);
    }

    /**
     * 读取集合
     *
     * @param <T>          泛型
     * @param content      bytes
     * @param elementClass elementClass
     * @return 集合 list
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toList(@Nullable byte[] content, Class<T> elementClass) {
        return JsonUtils.toList(content, elementClass);
    }

    /**
     * 读取集合
     *
     * @param <T>          泛型
     * @param content      InputStream
     * @param elementClass elementClass
     * @return 集合 list
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toList(@Nullable InputStream content, Class<T> elementClass) {
        return JsonUtils.toList(content, elementClass);
    }

    /**
     * 读取集合
     *
     * @param <T>          泛型
     * @param content      bytes
     * @param elementClass elementClass
     * @return 集合 list
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toList(@Nullable String content, Class<T> elementClass) {
        return JsonUtils.toList(content, elementClass);
    }

    /**
     * 读取集合
     *
     * @param <K>        泛型
     * @param <V>        泛型
     * @param content    bytes
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @return 集合 map
     * @since 1.0.0
     */
    @NotNull
    public static <K, V> Map<K, V> toMap(@Nullable byte[] content, Class<?> keyClass, Class<?> valueClass) {
        return JsonUtils.toMap(content, keyClass, valueClass);
    }

    /**
     * 读取集合
     *
     * @param <K>        泛型
     * @param <V>        泛型
     * @param content    InputStream
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @return 集合 map
     * @since 1.0.0
     */
    @NotNull
    public static <K, V> Map<K, V> toMap(@Nullable InputStream content, Class<?> keyClass, Class<?> valueClass) {
        return JsonUtils.toMap(content, keyClass, valueClass);
    }

    /**
     * 读取集合
     *
     * @param <K>        泛型
     * @param <V>        泛型
     * @param content    bytes
     * @param keyClass   key类型
     * @param valueClass 值类型
     * @return 集合 map
     * @since 1.0.0
     */
    @NotNull
    public static <K, V> Map<K, V> toMap(@Nullable String content, Class<?> keyClass, Class<?> valueClass) {
        return JsonUtils.toMap(content, keyClass, valueClass);
    }

    /**
     * 初始化 map 容量
     *
     * @param expectedSize the expected size
     * @return the int
     * @since 1.0.0
     */
    @Contract(pure = true)
    @SuppressWarnings("PMD.UndefineMagicConstantRule")
    public static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            if (expectedSize < 0) {
                throw new IllegalArgumentException("expectedSize cannot be negative but was: " + expectedSize);
            }
            return expectedSize + 1;
        }
        if (expectedSize < Ints.MAX_POWER_OF_TWO) {
            // This is the calculation used in JDK8 to resize when a putAll
            // happens; it seems to be the most conservative calculation we
            // can make.  0.75 is the default load factor.
            return (int) ((float) expectedSize / 0.75F + 1.0F);
        }
        // any large value
        return Integer.MAX_VALUE;
    }
}
