package net.bitnine.template.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import net.bitnine.template.model.dto.DbConnectionInfoDto;
import org.junit.jupiter.api.Test;

class DbConnectionManagerTest {
    @Test
    void dbConnectionManagerInstance() {
        assertThat(DbConnectionManager.getInstance()).isNotNull();
    }

    @Test
    void getConnectionWhenCorrectInfoThenConnection() throws SQLException {
        // Mock
        DbConnectionInfoDto connInfo = mock(DbConnectionInfoDto.class);
        when(connInfo.getDbUrl()).thenReturn("jdbc:postgresql://localhost:15432/template");
        when(connInfo.getDbUsername()).thenReturn("template");
        when(connInfo.getDbPassword()).thenReturn("template");

        // test
        Connection conn = DbConnectionManager.getInstance().getConnection(connInfo);
        assertThat(conn).isNotNull();
        assertThat(conn.getMetaData().getURL()).isEqualTo(connInfo.getDbUrl());
    }

    @Test
    void getConnectionWhenIncorrectUrlThenErrorMessage() {
        // Mock
        DbConnectionInfoDto connInfo = mock(DbConnectionInfoDto.class);
        when(connInfo.getDbUrl()).thenReturn("jdbc:postgresql://localhost:15432/template");
        when(connInfo.getDbUsername()).thenReturn("template");
        when(connInfo.getDbPassword()).thenReturn("template");

        // test
        Connection conn = DbConnectionManager.getInstance().getConnection(connInfo);
        assertThat(conn).isNull();
    }
}