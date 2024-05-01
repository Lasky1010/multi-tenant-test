package org.example.multitenanttest.data.constant;

public interface MultiTenantConstants {
    String DEFAULT_TENANT_ID = "public";
    String CURRENT_TENANT_IDENTIFIER = "CURRENT_TENANT_IDENTIFIER";
    String TENANT_KEY = "tenant-id";
    String TENANT_NOT_FOUND_ERROR = "X-TenantID not present in the Request Header";
    int BAD_REQUEST_HTTP_CODE_ERROR = 400;
}