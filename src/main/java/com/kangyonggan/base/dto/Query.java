package com.kangyonggan.base.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author kangyonggan
 */
public class Query extends HashMap<String, Object> {

    private static final String PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN);

    public String getString(String name) {
        return (String) get(name);
    }

    public Integer getInteger(String name) {
        return (Integer) get(name);
    }

    public Date getDate(String name) {
        String date = getString(name);
        if (date != null && date.trim().length() > 0) {
            if (date.length() == PATTERN.length()) {
                try {
                    return DATE_FORMAT.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException("日期解析异常：" + date);
                }
            }
        }

        return null;
    }

}

