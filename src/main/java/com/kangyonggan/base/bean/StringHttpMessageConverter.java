package com.kangyonggan.base.bean;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 */
public class StringHttpMessageConverter extends org.springframework.http.converter.StringHttpMessageConverter {

    public StringHttpMessageConverter() {
        super(Charset.forName("UTF-8"));
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);
    }

}
