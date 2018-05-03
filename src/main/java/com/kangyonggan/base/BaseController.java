package com.kangyonggan.base;

import com.kangyonggan.base.bean.HandlerInterceptor;
import com.kangyonggan.base.dto.Params;
import com.kangyonggan.base.dto.Query;
import com.kangyonggan.base.util.StringUtil;

import java.util.Map;

/**
 * @author kangyonggan
 */
public class BaseController {

    protected static final String DASHBOARD = "dashboard";
    private String pathRoot;
    private static final String LIST = "/list";
    private static final String INDEX = "/index";
    private static final String FORM = "/form";
    private static final String FORM_MODAL = "/form-modal";
    private static final String DETAIL = "/detail";
    private static final String DETAIL_MODAL = "/detail-modal";
    private static final String TABLE_TR = "/table-tr";

    public BaseController() {
        String className = getClass().getSimpleName();
        String[] arr = StringUtil.camelToArray(className);

        pathRoot = "";
        for (int i = 0; i < arr.length - 1; i++) {
            if (i != 0) {
                pathRoot += "/";
            }
            pathRoot += arr[i];
        }

        if (!pathRoot.startsWith(DASHBOARD)) {
            pathRoot = "web/" + pathRoot;
        }
    }

    protected Params getRequestParams() {
        Params params = new Params();

        // 分页相关参数
        params.setPageSize(getIntegerParam("limit", 10));
        int offset = getIntegerParam("offset", 0);
        params.setPageNum(offset / params.getPageSize() + 1);

        String sort = getStringParam("sort");
        params.setSort(StringUtil.convertCamelToUnderLine(sort));
        params.setOrder(getStringParam("order", "asc"));

        // 其他查询条件
        params.setQuery(getQuery());

        return params;
    }

    protected Query getQuery() {
        return getQuery("query");
    }

    protected Query getQuery(String name) {
        Query query = new Query();
        name += ".";
        Map<String, String[]> parameterMap = HandlerInterceptor.getRequest().getParameterMap();
        for (String key : parameterMap.keySet()) {
            if (key.startsWith(name)) {
                String[] value = parameterMap.get(key);
                if (value != null && value.length == 1) {
                    query.put(key.substring(name.length()), value[0]);
                } else {
                    query.put(key.substring(name.length()), value);
                }
            }
        }

        return query;
    }

    protected String getStringParam(String name) {
        return HandlerInterceptor.getRequest().getParameter(name);
    }

    protected String getStringParam(String name, String defaultValue) {
        String param = HandlerInterceptor.getRequest().getParameter(name);
        return param == null ? defaultValue : param;
    }

    protected int getIntegerParam(String name) {
        return Integer.parseInt(HandlerInterceptor.getRequest().getParameter(name));
    }

    protected int getIntegerParam(String name, int defaultValue) {
        try {
            return Integer.parseInt(HandlerInterceptor.getRequest().getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected String getPathRoot() {
        return pathRoot;
    }

    protected String getPathIndex() {
        return pathRoot + INDEX;
    }

    protected String getPathList() {
        return pathRoot + LIST;
    }

    protected String getPathForm() {
        return pathRoot + FORM;
    }

    protected String getPathDetail() {
        return pathRoot + DETAIL;
    }

    protected String getPathFormModal() {
        return pathRoot + FORM_MODAL;
    }

    protected String getPathDetailModal() {
        return pathRoot + DETAIL_MODAL;
    }

    protected String getPathTableTr() {
        return pathRoot + TABLE_TR;
    }
}
