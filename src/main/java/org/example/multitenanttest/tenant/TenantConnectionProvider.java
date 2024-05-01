package org.example.multitenanttest.tenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.multitenanttest.data.constant.MultiTenantConstants;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

    private final DataSource datasource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }


    @Override
    public Connection getConnection(Object tenantId) throws SQLException {
        String tenantIdentifier = (String) tenantId;
        log.debug("Get connection for tenant {}", tenantIdentifier);
        final Connection connection = getAnyConnection();
        try {
            if (tenantIdentifier != null) {
                connection.createStatement().execute("SET search_path TO " + tenantIdentifier);
            } else {
                connection.createStatement().execute("SET search_path TO " + MultiTenantConstants.DEFAULT_TENANT_ID);
            }
        } catch (SQLException e) {
            throw new HibernateException(
                    "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e
            );
        }
        return connection;
    }

    @Override
    public void releaseConnection(Object tenantId, Connection connection) throws SQLException {
        String tenantIdentifier = (String) tenantId;
        log.debug("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(MultiTenantConstants.DEFAULT_TENANT_ID);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}