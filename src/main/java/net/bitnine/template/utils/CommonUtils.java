package net.bitnine.template.utils;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.bitnine.template.model.SessionUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtils {

    public static HttpSession getSession() {
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
            .currentRequestAttributes();
        return servletRequestAttribute.getRequest().getSession(true);
    }

    public static SessionUser getSessionUser() throws NullPointerException {
        SessionUser user = (SessionUser) CommonUtils.getSession().getAttribute("user");
        return user;
    }

    public static HttpServletRequest getHttpRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
            .requireNonNull(RequestContextHolder.getRequestAttributes()))
            .getRequest();
        return request;
    }

    public static String getServiceUrl() {
        HttpServletRequest req = CommonUtils.getHttpRequest();
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/" + req
            .getContextPath();
    }
}