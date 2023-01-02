package io.github.dong4j.coco.kernel.common.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import cn.hutool.core.util.StrUtil;

/**
 * <p>Description:  </p>
 *
 * @author dong4j
 * @version 1.0.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2021.05.26 16:40
 * @since 1.9.0
 */
public class StringTrimmerSerializer extends JsonSerializer<String> {

    /**
     * Serialize
     *
     * @param s                  s
     * @param jsonGenerator      json generator
     * @param serializerProvider serializer provider
     * @throws IOException io exception
     * @since 1.9.0
     */
    @Override
    @SuppressWarnings("java:S3252")
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(StrUtil.trim(s));
    }
}
