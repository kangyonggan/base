package com.kangyonggan.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kangyonggan
 */
public class StringUtil {

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{8,20}$";

    public static final String SPLIT_CTRL = "_";

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-3,5-9]))\\d{8}$";

    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    private static Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean hasEmpty(String... arr) {
        for (String str : arr) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String... arr) {
        for (String str : arr) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            return str;
        }

        return new StringBuilder(str.length())
                .append(Character.toTitleCase(firstChar))
                .append(str.substring(1))
                .toString();
    }

    public static boolean in(String str, String... arr) {
        for (String s : arr) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String[] camelToArray(String word) {
        if (isEmpty(word)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isUpperCase(ch)) {
                sb.append(SPLIT_CTRL).append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }

        if (sb.toString().startsWith(SPLIT_CTRL)) {
            sb.deleteCharAt(0);
        }
        if (sb.toString().endsWith(SPLIT_CTRL)) {
            sb.deleteCharAt(sb.lastIndexOf(SPLIT_CTRL));
        }

        return sb.toString().split(SPLIT_CTRL);
    }

    public static String firstToUpperCase(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String toLikeString(String str) {
        if (str == null) {
            str = "";
        }
        return String.format("%%%s%%", str);
    }

    public static boolean isWrapClass(Class clazz) {
        try {
            if (String.class.getSimpleName().equals(clazz.getSimpleName())) {
                return true;
            }
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    public static String decode(String data) {
        try {
            return new String(data.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return data;
        }
    }

    public static String convertTableName(String tableName) {
        boolean isLine = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableName.length(); i++) {
            char ch = tableName.charAt(i);

            // 首字母转大写
            if (i == 0) {
                sb.append(Character.toUpperCase(ch));
            } else {
                // 下划线后面的字母转大写
                if (isLine) {
                    sb.append(Character.toUpperCase(ch));
                } else {
                    sb.append(ch);
                }
            }

            if (ch == '_') {
                sb.deleteCharAt(sb.lastIndexOf("_"));
                isLine = true;
            } else {
                isLine = false;
            }
        }

        return sb.toString();
    }

    public static String convertCamelToUnderLine(String data) {
        if (isEmpty(data)) {
            return data;
        }

        data = String.valueOf(data.charAt(0)).toUpperCase().concat(data.substring(1));

        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == data.length() ? "" : "_");
        }
        return sb.toString();
    }
}
