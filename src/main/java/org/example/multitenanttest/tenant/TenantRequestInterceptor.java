package org.example.multitenanttest.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.multitenanttest.data.constant.MultiTenantConstants;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static org.example.multitenanttest.data.constant.MultiTenantConstants.BAD_REQUEST_HTTP_CODE_ERROR;
import static org.example.multitenanttest.data.constant.MultiTenantConstants.TENANT_NOT_FOUND_ERROR;

@RequiredArgsConstructor
@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String tenantId = request.getHeader(MultiTenantConstants.TENANT_KEY);

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