package com.xwc1125.chain5j.protocol;

import com.xwc1125.chain5j.protocol.deserializer.RawResponseDeserializer;
import com.xwc1125.chain5j.protocol.entity.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  11:12 <br>
 */
public class ObjectMapperFactory {
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    public ObjectMapperFactory() {
    }

    public static ObjectMapper getObjectMapper() {
        return getObjectMapper(false);
    }

    public static ObjectMapper getObjectMapper(boolean shouldIncludeRawResponses) {
        return !shouldIncludeRawResponses ? DEFAULT_OBJECT_MAPPER : configureObjectMapper(new ObjectMapper(), true);
    }

    public static ObjectReader getObjectReader() {
        return DEFAULT_OBJECT_MAPPER.reader();
    }

    private static ObjectMapper configureObjectMapper(ObjectMapper objectMapper, boolean shouldIncludeRawResponses) {
        if (shouldIncludeRawResponses) {
            SimpleModule module = new SimpleModule();
            module.setDeserializerModifier(new BeanDeserializerModifier() {
                public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                    return (JsonDeserializer)(Response.class.isAssignableFrom(beanDesc.getBeanClass()) ? new RawResponseDeserializer(deserializer) : deserializer);
                }
            });
            objectMapper.registerModule(module);
        }

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    static {
        configureObjectMapper(DEFAULT_OBJECT_MAPPER, false);
    }
}
