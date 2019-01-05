package com.xwc1125.chain5j.protocol.deserializer;

import com.xwc1125.chain5j.protocol.entity.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:12 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
public class RawResponseDeserializer extends StdDeserializer<Response> implements ResolvableDeserializer {
    private final JsonDeserializer<?> defaultDeserializer;

    public RawResponseDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(Response.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public Response deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Response deserializedResponse = (Response)this.defaultDeserializer.deserialize(jp, ctxt);
        deserializedResponse.setRawResponse(this.getRawResponse(jp));
        return deserializedResponse;
    }

    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer)this.defaultDeserializer).resolve(ctxt);
    }

    private String getRawResponse(JsonParser jp) throws IOException {
        InputStream inputSource = (InputStream)jp.getInputSource();
        if (inputSource == null) {
            return "";
        } else {
            inputSource.reset();
            return this.streamToString(inputSource);
        }
    }

    private String streamToString(InputStream input) throws IOException {
        return (new Scanner(input, StandardCharsets.UTF_8.name())).useDelimiter("\\Z").next();
    }
}
