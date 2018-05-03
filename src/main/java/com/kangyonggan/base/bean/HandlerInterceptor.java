package com.kangyonggan.base.bean;

import com.kangyonggan.base.annotation.Token;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author kangyonggan
 */
public class HandlerInterceptor extends HandlerInterceptorAdapter {

    private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Token token = method.getMethodAnnotation(Token.class);
            if (token != null && token.type() == Token.Type.CHECK) {
                if (isRepeatSubmit(request, token)) {
                    return false;
                }
            }
        }

        // 保存当前请求
        currentRequest.set(request);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Token token = method.getMethodAnnotation(Token.class);
            if (token != null && token.type() == Token.Type.GENERATE) {
                String random = UUID.randomUUID().toString();
                modelAndView.addObject("_token", random);
                request.getSession().setAttribute(token.key(), random);
            }
        }

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 从本地线程中移除请求
        currentRequest.remove();
    }

    public static HttpServletRequest getRequest() {
        return currentRequest.get();
    }

    /**
     * @param request
     * @param token
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request, Token token) {
        try {
            String random = request.getParameter("_token");
            String sessionRandom = (String) request.getSession().getAttribute(token.key());
            if (random == null) {
                return true;
            }
            return !random.equals(sessionRandom);
        } catch (Exception e) {
            return true;
        } finally {
            request.getSession().removeAttribute(token.key());
        }
    }
}
