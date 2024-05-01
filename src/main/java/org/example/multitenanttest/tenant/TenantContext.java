package org.example.multitenanttest.tenant;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public abstract class TenantContext {
    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
