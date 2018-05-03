package com.kangyonggan.base.dto;

import com.kangyonggan.base.constants.Resp;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author kangyonggan
 */
public class Response extends HashMap<String, Object> implements Serializable {

    /**
     * 响应码的key
     */
    public static final String RESP_CO = "respCo";

    /**
     * 响应消息的key
     */
    public static final String RESP_MSG = "respMsg";

    private Response() {

    }

    public static Response getResponse() {
        return new Response();
    }

    public static Response getSuccessResponse() {
        Response response = new Response();
        response.put(RESP_CO, Resp.SUCCESS.getRespCo());
        response.put(RESP_MSG, Resp.SUCCESS.getRespMsg());
        return response;
    }

    public static Response getFailureResponse() {
        Response response = new Response();
        response.put(RESP_CO, Resp.FAILURE.getRespCo());
        response.put(RESP_MSG, Resp.FAILURE.getRespMsg());
        return response;
    }

    public Response failure() {
        failure(Resp.FAILURE.getRespMsg());
        return this;
    }

    public Response failure(String msg) {
        put(RESP_CO, Resp.FAILURE.getRespCo());
        put(RESP_MSG, msg);
        return this;
    }

}
