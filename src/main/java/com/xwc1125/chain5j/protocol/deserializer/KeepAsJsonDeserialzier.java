package com.xwc1125.chain5j.protocol.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  11:16 <br>
 */
public class KeepAsJsonDeserialzier extends JsonDeserializer<String> {
    public KeepAsJsonDeserialzier() {
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode tree = jp.getCodec().readTree(jp);
        return tree.toString();
    }
}
