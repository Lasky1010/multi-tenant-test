package org.example.multitenanttest.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {

    private final String X_TENANT_ID = "X-TenantID";
    private final String TENANT_NOT_FOUND_ERROR = "X-TenantID not present in the Request Header";
    private final int BAD_REQUEST_HTTP_CODE_ERROR = 400;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String tenantId = request.getHeader(X_TENANT_ID);

        System.out.println("RequestURI::" + requestURI + " || Search for X-TenantID  :: " + tenantId);

        if (tenantId == null) {
            response.getWriter().write(TENANT_NOT_FOUND_ERROR);
            response.setStatus(BAD_REQUEST_HTTP_CODE_ERROR);
            return false;
        }

        TenantContext.setCurrentTenant(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}