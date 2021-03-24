package net.bitnine.template.service;

import java.sql.Connection;
import net.bitnine.template.model.dto.DbConnectionInfoDto;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DbConnectionManageService {
    public Connection getConnection(DbConnectionInfoDto dbConnectionInfoDto) {
        // 해당 dbConnectionInfoDto 존재하면 기존 Connection 반환 아니면 create
        //dbConnectionInfoDto

        return null;
    }

    public Connection createConnection() {
        return null;
    }

    public void closeConnection() {

    }
}
