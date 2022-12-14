package io.github.dong4j.coco.kernel.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.MapType;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dong4j.coco.kernel.common.asserts.Assertions;
import io.github.dong4j.coco.kernel.common.constant.ConfigDefaultValue;
import io.github.dong4j.coco.kernel.common.constant.ConfigKey;
import io.github.dong4j.coco.kernel.common.serialize.StringTrimmerDeserializer;
import io.github.dong4j.coco.kernel.common.serialize.StringTrimmerSerializer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: Jackson?????????
 * ?????????????????? {@link ObjectMapper} ???????????????????????????????????????, ?????? 2 ?????????:
 * 1. {@link JsonUtils#getInstance()};
 * 2. {@link JsonUtils#getCopyMapper()};
 * ??????????????????????????????????????????, ?????????????????? {@link ObjectMapper} ???????????????????????? {@link ObjectMapper} ?????????, ????????????????????? {@link ObjectMapper},
 * ????????????????????????, ???????????????????????????????????? {@link ObjectMapper} ??????.
 * ??????????????????????????????????????????, ??????????????????????????? {@link ObjectMapper} ??? json ??????, ??????????????? {@link ObjectMapper} ?????????????????????.
 * </p>
 *
 * @author dong4j
 * @version 1.2.3
 * @email "mailto:dong4j@gmail.com"
 * @date 2019.12.26 21:35
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
@SuppressWarnings("checkstyle:MethodLimit")
public class JsonUtils {
    /** PATTERN_DATETIME */
    public static final String PATTERN_DATETIME = System.getProperty(ConfigKey.JSON_DATE_FORMAT, ConfigDefaultValue.DEFAULT_DATE_FORMAT);
    /** Empty array */
    public static final byte[] EMPTY_ARRAY = new byte[0];
    /** MESSAGE */
    private static final String MESSAGE = "????????????????????????";

    /**
     * ?????????????????????, ??????????????????, ?????????????????? {@see JacksonHolder.INSTANCE} ???????????????????????????????????????.
     *
     * @return the object mapper
     * @since 1.0.0
     */
    public static ObjectMapper getCopyMapper() {
        return getInstance().copy();
    }

    /**
     * ??????????????????, ????????????????????????????????????, ?????????????????????????????????, ????????????????????????, ????????? {@link JsonUtils#getCopyMapper()}.
     *
     * @return the instance
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    /**
     * Create Object Node
     *
     * @return the object node
     * @since 2.1.0
     */
    public static ObjectNode createNode() {
        return getInstance().createObjectNode();
    }

    /**
     * Create Object Node
     *
     * @param mapper mapper
     * @return the object node
     * @since 2.1.0
     */
    public static @NotNull ObjectNode createNode(@NotNull ObjectMapper mapper) {
        return mapper.createObjectNode();
    }

    /**
     * ???????????????????????? json ??????.
     *
     * @param jsonInString json in string
     * @return the boolean
     * @since 1.0.0
     */
    public static boolean isJson(String jsonInString) {
        return isJson(getInstance(), jsonInString);
    }

    /**
     * ???????????????
     *
     * @param json json
     * @return the string
     * @since 1.6.0
     */
    public static @NotNull String compress(String json) {
        return toJson(json);
    }

    /**
     * ??????????????? mapper ???????????????????????? json ??????.
     * ????????????????????????????????????????????????
     *
     * @param mapper       mapper
     * @param jsonInString json in string
     * @return the boolean
     * @since 1.0.0
     */
    @SuppressWarnings("java:S3252")
    public static boolean isJson(@NotNull ObjectMapper mapper, String jsonInString) {
        if (StrUtil.isBlank(jsonInString)) {
            return false;
        }
        try {
            JsonNode jsonNode = mapper.readTree(jsonInString);
            String jStr = jsonNode.toString();
            return StrUtil.trim(jStr).equals(StrUtil.trim(jsonInString));
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * ??????????????????????????? json ??????.
     *
     * @param jsonBytes json bytes
     * @return the boolean
     * @since 1.0.0
     */
    public static boolean isJson(byte[] jsonBytes) {
        return isJson(getInstance(), jsonBytes);
    }

    /**
     * ??????????????? mapper ??????????????????????????? json ??????.
     *
     * @param mapper    mapper
     * @param jsonBytes json bytes
     * @return the boolean
     * @since 1.0.0
     */
    public static boolean isJson(@NotNull ObjectMapper mapper, byte[] jsonBytes) {
        if (jsonBytes.length == 0) {
            return false;
        }
        try {
            mapper.readTree(jsonBytes);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * ????????????????????? json ?????????.
     *
     * @param object javaBean
     * @return jsonString json ?????????
     * @since 1.0.0
     */
    @NotNull
    public static String toJson(Object object) {
        return toJson(object, false);
    }

    /**
     * ????????????????????? json ?????????, ?????? pretty ????????????????????? json.
     *
     * @param object object
     * @param pretty pretty
     * @return the string
     * @since 1.0.0
     */
    @NotNull
    public static String toJson(Object object, boolean pretty) {
        return toJson(getInstance(), object, pretty);
    }

    /**
     * ??????????????? mapper ????????????????????? json ?????????.
     *
     * @param mapper mapper
     * @param object object
     * @return the string
     * @since 1.0.0
     */
    public static String toJson(ObjectMapper mapper, Object object) {
        return toJson(mapper, object, false);
    }

    /**
     * ?????????????????? mapper ????????????????????? json ?????????, ?????? pretty ????????????????????? json,
     * ?????? object ??? String ??????, ??????????????????????????????.
     *
     * @param mapper mapper
     * @param object object
     * @param pretty pretty
     * @return the string
     * @since 1.0.0
     */
    @SuppressWarnings("java:S3252")
    public static String toJson(ObjectMapper mapper, Object object, boolean pretty) {
        if (object == null) {
            return StrPool.EMPTY_JSON;
        }
        // ????????? string, ????????? object ????????? json, ??????????????????
        if (object instanceof String) {
            String str = StrUtil.trim((String) object);
            if (isJson(mapper, str)) {
                object = parse(str, Object.class);
            } else {
                // ??? json ?????????, ???????????????????????????
                return str;
            }
        }

        try {
            String json;
            if (pretty) {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            } else {
                json = mapper.writeValueAsString(object);
            }
            return json;
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ????????????????????? json byte ??????.
     *
     * @param object javaBean
     * @return jsonString json?????????
     * @since 1.0.0
     */
    @Contract("null -> new")
    public static byte[] toJsonAsBytes(Object object) {
        return toJsonAsBytes(getInstance(), object);
    }

    /**
     * ??????????????? mapper ????????????????????? json byte ??????.
     *
     * @param mapper mapper
     * @param object object
     * @return the byte [ ]
     * @since 1.0.0
     */
    public static byte[] toJsonAsBytes(ObjectMapper mapper, Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ??? json ??????????????? JsonNode.
     *
     * @param jsonString jsonString
     * @return jsonString json?????????
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(String jsonString) {
        return readTree(getInstance(), jsonString);
    }

    /**
     * ??????????????? mapper ??? json ??????????????? JsonNode.
     *
     * @param mapper     mapper
     * @param jsonString json string
     * @return the json node
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(@NotNull ObjectMapper mapper, String jsonString) {
        Assertions.notBlank(jsonString, "jsonString ????????????");
        try {
            return mapper.readTree(jsonString);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ??? json ???????????????????????? JsonNode.
     *
     * @param in InputStream
     * @return jsonString json?????????
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(InputStream in) {
        return readTree(getInstance(), in);
    }

    /**
     * ??????????????? mapper ??? json ???????????????????????? JsonNode.
     *
     * @param mapper mapper
     * @param in     in
     * @return the json node
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(@NotNull ObjectMapper mapper, InputStream in) {
        Assert.notNull(in, "inputstream ????????????");
        try {
            return mapper.readTree(in);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ????????????????????? JsonNode.
     *
     * @param content content
     * @return jsonString json?????????
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(byte[] content) {
        return readTree(getInstance(), content);
    }

    /**
     * ??????????????? mapper ????????????????????? JsonNode.
     *
     * @param mapper  mapper
     * @param content content
     * @return the json node
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(@NotNull ObjectMapper mapper, byte[] content) {
        Assert.notNull(content, "byte[] ????????????");
        try {
            return mapper.readTree(content);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ??? JsonParser ?????? JsonNode.
     *
     * @param jsonParser JsonParser
     * @return jsonString json?????????
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(JsonParser jsonParser) {
        return readTree(getInstance(), jsonParser);
    }

    /**
     * ??????????????? mapper ??? JsonParser ?????? JsonNode.
     *
     * @param mapper     mapper
     * @param jsonParser json parser
     * @return the json node
     * @since 1.0.0
     */
    @NotNull
    public static JsonNode readTree(@NotNull ObjectMapper mapper, JsonParser jsonParser) {
        Assert.notNull(jsonParser, "jsonParser ????????????");
        try {
            return mapper.readTree(jsonParser);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ??? json ?????????????????????, ?????? T ?????????????????????????????????.
     *
     * @param <T>        T ????????????
     * @param jsonString jsonString
     * @param valueType  class
     * @return Bean t
     * @since 1.0.0
     */
    public static <T> T parse(String jsonString, Class<T> valueType) {
        return parse(getInstance(), jsonString, valueType);
    }

    /**
     * ??????????????? mapper ??? json ?????????????????????, ?????? T ?????????????????????????????????.
     *
     * @param <T>        parameter
     * @param mapper     mapper
     * @param jsonString json string
     * @param valueType  value type
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parse(@NotNull ObjectMapper mapper, String jsonString, Class<T> valueType) {
        Assertions.notBlank(jsonString, MESSAGE);
        try {
            return mapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ??? json byte ???????????????????????????.
     *
     * @param <T>       T ????????????
     * @param content   json bytes
     * @param valueType class
     * @return Bean t
     * @since 1.0.0
     */
    @Nullable
    public static <T> T parse(byte[] content, Class<T> valueType) {
        return parse(getInstance(), content, valueType);
    }

    /**
     * Parse t
     *
     * @param <T>       parameter
     * @param mapper    mapper
     * @param content   content
     * @param valueType value type
     * @return the t
     * @since 1.0.0
     */
    @Nullable
    public static <T> T parse(@NotNull ObjectMapper mapper, byte[] content, @NotNull Class<T> valueType) {
        if (Void.class.getTypeName().equals(valueType.getTypeName())) {
            return null;
        }
        Assert.notNull(content, MESSAGE);
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ???json?????????????????????
     *
     * @param <T>       T ????????????
     * @param in        InputStream
     * @param valueType class
     * @return Bean t
     * @since 1.0.0
     */
    public static <T> T parse(InputStream in, Class<T> valueType) {
        return parse(getInstance(), in, valueType);
    }

    /**
     * Parse t
     *
     * @param <T>       parameter
     * @param mapper    mapper
     * @param in        in
     * @param valueType value type
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parse(@NotNull ObjectMapper mapper, InputStream in, Class<T> valueType) {
        Assert.notNull(in, MESSAGE);
        try {
            return mapper.readValue(in, valueType);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ???json?????????????????????
     *
     * @param <T>           T ????????????
     * @param content       bytes
     * @param typeReference ????????????
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(byte[] content, TypeReference<T> typeReference) {
        return parse(getInstance(), content, typeReference);
    }

    /**
     * Parse t
     *
     * @param <T>           parameter
     * @param mapper        mapper
     * @param content       content
     * @param typeReference type reference
     * @return the t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(@NotNull ObjectMapper mapper, byte[] content, TypeReference<T> typeReference) {
        Assert.notNull(content, MESSAGE);
        try {
            return mapper.readValue(content, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ???json?????????????????????
     *
     * @param <T>           T ????????????
     * @param jsonString    jsonString
     * @param typeReference ????????????
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(String jsonString, TypeReference<T> typeReference) {
        return parse(getInstance(), jsonString, typeReference);
    }

    /**
     * Parse t
     *
     * @param <T>           parameter
     * @param mapper        mapper
     * @param jsonString    json string
     * @param typeReference type reference
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parse(@NotNull ObjectMapper mapper, String jsonString, TypeReference<T> typeReference) {
        Assertions.notBlank(jsonString, MESSAGE);
        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * Parse t
     *
     * @param <T>           parameter
     * @param jsonString    json string
     * @param typeReference type reference
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parse(String jsonString, Object typeReference) {
        return parse(getInstance(), jsonString, typeReference);
    }

    /**
     * Parse t
     *
     * @param <T>           parameter
     * @param mapper        mapper
     * @param jsonString    json string
     * @param typeReference type reference
     * @return the t
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T parse(@NotNull ObjectMapper mapper, String jsonString, Object typeReference) {
        Assertions.notBlank(jsonString, MESSAGE);
        Assert.notNull(typeReference, "????????????????????????");
        try {
            if (typeReference instanceof TypeReference) {
                return mapper.readValue(jsonString, (TypeReference<T>) typeReference);
            } else {
                T t;
                Class<T> clazz = (Class<T>) typeReference;
                // ????????? String, ????????????, ????????????
                if (String.class.isAssignableFrom(clazz)) {
                    t = (T) jsonString;
                } else if (Date.class.isAssignableFrom(clazz)) {
                    // ?????????????????????, ??????????????? Date, ????????????
                    final DateTime dateTime = DateUtil.parse(jsonString, PATTERN_DATETIME);
                    t = (T) dateTime;
                } else {
                    t = mapper.readValue(jsonString, (Class<T>) typeReference);
                }
                return t;
            }
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ???json?????????????????????
     *
     * @param <T>           T ????????????
     * @param in            InputStream
     * @param typeReference ????????????
     * @return Bean t
     * @since 1.0.0
     */
    @NotNull
    public static <T> T parse(InputStream in, TypeReference<T> typeReference) {
        return parse(getInstance(), in, typeReference);
    }

    /**
     * Parse t
     *
     * @param <T>           parameter
     * @param mapper        mapper
     * @param in            in
     * @param typeReference type reference
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parse(@NotNull ObjectMapper mapper, InputStream in, TypeReference<T> typeReference) {
        Assertions.notNull(in, MESSAGE);
        try {
            return mapper.readValue(in, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ???????????????????????????????????????, ?????????????????? {@link javax.validation.constraints} ????????????????????????.
     *
     * @param <T>       parameter
     * @param content   content
     * @param valueType value type
     * @param validator validator
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parseAndValidate(byte[] content, Class<T> valueType, Validator validator) {
        T t = parse(content, valueType);
        validate(t, validator);
        return t;
    }

    /**
     * Parse and validate t
     *
     * @param <T>       parameter
     * @param mapper    mapper
     * @param content   content
     * @param valueType value type
     * @param validator validator
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parseAndValidate(@NotNull ObjectMapper mapper, byte[] content, Class<T> valueType, Validator validator) {
        T t = parse(mapper, content, valueType);
        validate(t, validator);
        return t;
    }

    /**
     * Parse and validate t
     *
     * @param <T>        parameter
     * @param jsonString json string
     * @param valueType  value type
     * @param validator  validator
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parseAndValidate(String jsonString, Class<T> valueType, Validator validator) {
        T t = parse(jsonString, valueType);
        validate(t, validator);
        return t;
    }

    /**
     * Parse and validate t
     *
     * @param <T>        parameter
     * @param mapper     mapper
     * @param jsonString json string
     * @param valueType  value type
     * @param validator  validator
     * @return the t
     * @since 1.0.0
     */
    public static <T> T parseAndValidate(@NotNull ObjectMapper mapper, String jsonString, Class<T> valueType, Validator validator) {
        T t = parse(mapper, jsonString, valueType);
        validate(t, validator);
        return t;
    }

    /**
     * Validate *
     *
     * @param <T>       parameter
     * @param t         t
     * @param validator validator
     * @since 1.0.0
     */
    private static <T> void validate(T t, @NotNull Validator validator) {
        Set<ConstraintViolation<T>> validate = validator.validate(t);
        if (!validate.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : validate) {
                sb.append(violation.getPropertyPath().toString()).append(": ").append(violation.getMessage()).append(" ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /**
     * ????????????
     *
     * @param <T>          ??????
     * @param content      bytes
     * @param elementClass elementClass
     * @return ?????? list
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toList(byte[] content, Class<T> elementClass) {
        return toList(getInstance(), content, elementClass);
    }

    /**
     * To list list
     *
     * @param <T>          parameter
     * @param mapper       mapper
     * @param content      content
     * @param elementClass element class
     * @return the list
     * @since 1.0.0
     */
    public static <T> List<T> toList(@NotNull ObjectMapper mapper, byte[] content, Class<T> elementClass) {
        if (ObjectUtil.isEmpty(content)) {
            return Collections.emptyList();
        }
        try {
            return mapper.readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ?????? map type
     *
     * @param elementClass ???????????????
     * @return CollectionLikeType list type
     * @since 1.0.0
     */
    public static CollectionLikeType getListType(Class<?> elementClass) {
        return getListType(getInstance(), elementClass);
    }

    /**
     * Gets list type *
     *
     * @param mapper       mapper
     * @param elementClass element class
     * @return the list type
     * @since 1.0.0
     */
    public static CollectionLikeType getListType(@NotNull ObjectMapper mapper, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionLikeType(List.class, elementClass);
    }

    /**
     * ????????????
     *
     * @param <T>          ??????
     * @param content      InputStream
     * @param elementClass elementClass
     * @return ?????? list
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toList(InputStream content, Class<T> elementClass) {
        return toList(getInstance(), content, elementClass);
    }

    /**
     * To list list
     *
     * @param <T>          parameter
     * @param mapper       mapper
     * @param content      content
     * @param elementClass element class
     * @return the list
     * @since 1.0.0
     */
    @Contract("_, null, _ -> !null")
    public static <T> List<T> toList(@NotNull ObjectMapper mapper, InputStream content, Class<T> elementClass) {
        if (content == null) {
            return Collections.emptyList();
        }
        try {
            return mapper.readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ????????????
     *
     * @param <T>          ??????
     * @param content      bytes
     * @param elementClass elementClass
     * @return ?????? list
     * @since 1.0.0
     */
    @NotNull
    public static <T> List<T> toList(String content, Class<T> elementClass) {
        return toList(getInstance(), content, elementClass);
    }

    /**
     * To list list
     *
     * @param <T>          parameter
     * @param mapper       mapper
     * @param content      content
     * @param elementClass element class
     * @return the list
     * @since 1.0.0
     */
    public static <T> List<T> toList(@NotNull ObjectMapper mapper, String content, Class<T> elementClass) {
        if (ObjectUtil.isEmpty(content)) {
            return Collections.emptyList();
        }
        try {
            return mapper.readValue(content, getListType(elementClass));
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ????????????
     *
     * @param <K>        ??????
     * @param <V>        ??????
     * @param content    bytes
     * @param keyClass   key??????
     * @param valueClass ?????????
     * @return ?????? map
     * @since 1.0.0
     */
    @NotNull
    public static <K, V> Map<K, V> toMap(byte[] content, Class<?> keyClass, Class<?> valueClass) {
        return toMap(getInstance(), content, keyClass, valueClass);
    }

    /**
     * To map map
     *
     * @param <K>        parameter
     * @param <V>        parameter
     * @param mapper     mapper
     * @param content    content
     * @param keyClass   key class
     * @param valueClass value class
     * @return the map
     * @since 1.0.0
     */
    public static <K, V> Map<K, V> toMap(@NotNull ObjectMapper mapper, byte[] content, Class<?> keyClass, Class<?> valueClass) {
        if (ObjectUtil.isEmpty(content)) {
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ?????? map type
     *
     * @param keyClass   key ??????
     * @param valueClass value ??????
     * @return MapType map type
     * @since 1.0.0
     */
    public static MapType getMapType(Class<?> keyClass, Class<?> valueClass) {
        return getMapType(getInstance(), keyClass, valueClass);
    }

    /**
     * Gets map type *
     *
     * @param mapper     mapper
     * @param keyClass   key class
     * @param valueClass value class
     * @return the map type
     * @since 1.0.0
     */
    public static MapType getMapType(@NotNull ObjectMapper mapper, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
    }

    /**
     * ????????????
     *
     * @param <K>        ??????
     * @param <V>        ??????
     * @param content    InputStream
     * @param keyClass   key??????
     * @param valueClass ?????????
     * @return ?????? map
     * @since 1.0.0
     */
    @NotNull
    public static <K, V> Map<K, V> toMap(InputStream content, Class<?> keyClass, Class<?> valueClass) {
        return toMap(getInstance(), content, keyClass, valueClass);
    }

    /**
     * To map map
     *
     * @param <K>        parameter
     * @param <V>        parameter
     * @param mapper     mapper
     * @param content    content
     * @param keyClass   key class
     * @param valueClass value class
     * @return the map
     * @since 1.0.0
     */
    public static <K, V> Map<K, V> toMap(@NotNull ObjectMapper mapper, InputStream content, Class<?> keyClass, Class<?> valueClass) {
        if (ObjectUtil.isEmpty(content)) {
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * ????????????
     *
     * @param <K>        ??????
     * @param <V>        ??????
     * @param content    bytes
     * @param keyClass   key??????
     * @param valueClass ?????????
     * @return ?????? map
     * @since 1.0.0
     */
    @NotNull
    public static <K, V> Map<K, V> toMap(String content, Class<?> keyClass, Class<?> valueClass) {
        return toMap(getInstance(), content, keyClass, valueClass);
    }

    /**
     * To map map
     *
     * @param <K>        parameter
     * @param <V>        parameter
     * @param mapper     mapper
     * @param content    content
     * @param keyClass   key class
     * @param valueClass value class
     * @return the map
     * @since 1.0.0
     */
    public static <K, V> Map<K, V> toMap(@NotNull ObjectMapper mapper, String content, Class<?> keyClass, Class<?> valueClass) {
        if (ObjectUtil.isEmpty(content)) {
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(content, getMapType(keyClass, valueClass));
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * <p>Description: ??????????????????????????????????????????, ??? ObjectMapper ?????????????????? </p>
     *
     * @author dong4j
     * @version 1.2.3
     * @email "mailto:dong4j@gmail.com"
     * @date 2019.12.26 21:35
     * @since 1.0.0
     */
    @SuppressWarnings("all")
    private static class JacksonHolder {
        /** INSTANCE */
        private static final ObjectMapper INSTANCE;
        /** CHINA */
        private static final Locale CHINA = Locale.CHINA;

        static {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setLocale(CHINA);
            // ????????????, ????????????????????? (JVM ????????????)
            objectMapper.setDateFormat(new SimpleDateFormat(PATTERN_DATETIME, CHINA));
            // ??????????????????????????????
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // ???????????????????????????
            String zone = System.getProperty(ConfigKey.JSON_TIME_ZONE, ConfigDefaultValue.DEFAULT_TIME_ZONE);
            objectMapper.setTimeZone(TimeZone.getTimeZone(zone));
            // ?????????
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // ???????????????????????????????????????, ??????: false, ????????????
            objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
            // ?????? json ???????????????????????????????????? (?????????32???ASCII??????, ???????????????????????????)
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            // ?????? json ??????????????????????????????
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // ?????? Jackson ??????????????????????????????????????? objectMapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // ???????????????????????????
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.findAndRegisterModules();

            registerSubtypes(objectMapper);
            trimmer(objectMapper);
            INSTANCE = objectMapper;
        }

        /**
         * String ????????????????????????
         *
         * @param objectMapper object mapper
         * @since 1.9.0
         */
        private static void trimmer(ObjectMapper objectMapper) {
            SimpleModule stringTrimmerModule = new SimpleModule("String-Trim-Converter", PackageVersion.VERSION);
            stringTrimmerModule.addDeserializer(String.class, new StringTrimmerDeserializer());
            stringTrimmerModule.addSerializer(String.class, new StringTrimmerSerializer());
            objectMapper.registerModule(stringTrimmerModule);
        }

        /**
         * ?????????????????????/???????????????, ??????????????????????????????????????????:
         * 1. ????????????/?????????????????? @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
         * 2. ?????????????????????????????? @JsonTypeName(value = "??????")
         *
         * @param objectMapper object mapper
         * @since 1.6.0
         */
        private static void registerSubtypes(@NotNull ObjectMapper objectMapper) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            ConfigurationBuilder config = new ConfigurationBuilder();
            config.filterInputsBy(new FilterBuilder().includePackage(ConfigDefaultValue.BASE_PACKAGES));
            config.addUrls(ClasspathHelper.forPackage(ConfigDefaultValue.BASE_PACKAGES));
            config.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner(false));
            config.setExpandSuperTypes(false);
            Reflections reflections = new Reflections(config);

            // ?????? JsonTypeName ??????, ??????????????????
            Set<Class<?>> subTypes = reflections.getTypesAnnotatedWith(JsonTypeName.class);
            if (log.isTraceEnabled()) {
                subTypes.forEach(c -> log.trace("{}", c.getName()));
            }
            // ?????????????????????
            objectMapper.registerSubtypes(subTypes.toArray(new Class[0]));
            stopWatch.stop();
            log.debug("??? Jackson ???????????????????????????: {}, @JsonTypeName ???????????????: {}", stopWatch, subTypes.size());
        }
    }

}
