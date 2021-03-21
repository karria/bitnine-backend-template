package net.bitnine.template.utils;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.bitnine.template.model.SessionUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtils {

    /**
     * 현재 사용자의 HttpServletRequest 반환
     *
     * @return httpServletRequest
     */
    public static HttpServletRequest getHttpRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
            .requireNonNull(RequestContextHolder.getRequestAttributes()))
            .getRequest();
        return request;
    }

    /**
     * 현재 사용자의 HttpSession 반환
     *
     * @return httpSession
     */
    public static HttpSession getSession() {
        return CommonUtils.getHttpRequest().getSession(true);
    }

    /**
     * 현재 사용자의 Bitnine 공통 SessionUser 반환
     *
     * @return sessionUser
     * @throws NullPointerException
     */
    public static SessionUser getSessionUser() throws NullPointerException {
        return (SessionUser) CommonUtils.getSession().getAttribute("user");
    }


    /**
     * 현재 서비스의 contextPath 까지의 domain 주소 반환
     *
     * @return domain 주소
     */
    public static String getServiceUrl() {
        HttpServletRequest req = CommonUtils.getHttpRequest();
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/" + req
            .getContextPath();
    }
}