package com.xwc1125.chain5j.protocol.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xwc1125.chain5j.protocol.deserializer.KeepAsJsonDeserialzier;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @date 2019-01-04  11:08 <br>
 * <p>
 * Copyright (c) 2019 <br>
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class Response<T> {
    private long id;
    private String jsonrpc;
    private T result;
    private Response.Error error;
    private String rawResponse;

    public Response() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return this.jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Response.Error getError() {
        return this.error;
    }

    public void setError(Response.Error error) {
        this.error = error;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public static class Error {
        private int code;
        private String message;
        @JsonDeserialize(
                using = KeepAsJsonDeserialzier.class
        )
        private String data;

        public Error() {
        }

        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Response.Error)) {
                return false;
            } else {
                Response.Error error = (Response.Error) o;
                if (this.getCode() != error.getCode()) {
                    return false;
                } else {
                    if (this.getMessage() != null) {
                        if (this.getMessage().equals(error.getMessage())) {
                            return this.getData() != null ? this.getData().equals(error.getData()) : error.getData() == null;
                        }
                    } else if (error.getMessage() == null) {
                        return this.getData() != null ? this.getData().equals(error.getData()) : error.getData() == null;
                    }

                    return false;
                }
            }
        }

        @Override
        public int hashCode() {
            int result = this.getCode();
            result = 31 * result + (this.getMessage() != null ? this.getMessage().hashCode() : 0);
            result = 31 * result + (this.getData() != null ? this.getData().hashCode() : 0);
            return result;
        }
    }
}
