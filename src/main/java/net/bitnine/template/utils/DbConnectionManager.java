package net.bitnine.template.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.bitnine.template.model.dto.DbConnectionInfoDto;
import net.bitnine.template.model.dto.DbConnectionWrapperDto;

/**
 * 사용자별 Db Connection 연결을 관리하는 Class 시스템 전체에 하나만 존재하여야 함
 */
@Slf4j
public class DbConnectionManager {

    private static DbConnectionManager instance;
    private static List<DbConnectionWrapperDto> dbConnectionWrapperDtoList;

    protected static final int MAX_CONNECTION_CNT = 100;

    private DbConnectionManager() {
        if (dbConnectionWrapperDtoList == null) {
            dbConnectionWrapperDtoList = new ArrayList<>();
        }
    }

    private static class InnerInstanceClazz {
        private static final DbConnectionManager instance = new DbConnectionManager();
    }

    public static DbConnectionManager getInstance() {
        return InnerInstanceClazz.instance;
    }

    public Connection getConnection(DbConnectionInfoDto connInfo) throws RuntimeException {
        return getDbConnectionOrCreate(connInfo).getConnection();
    }

    public void disconnect(DbConnectionInfoDto connInfo) {
        Optional<DbConnectionWrapperDto> connInfoOpt = getDbConnectionInfoDtoIsExist(connInfo);
        if(connInfoOpt.isPresent()) {
            DbConnectionWrapperDto wrapperDto = connInfoOpt.get();
            connectionClose(wrapperDto.getConnection());
            dbConnectionWrapperDtoList.remove(wrapperDto);
        }
    }

    private static void connectionClose(Connection conn) {
        try {
            if(conn != null) conn.close();
        } catch (SQLException se) {
            log.error(se.toString(), se);
        }
    }

    private static void lastUsedWrapperRemove() {
        DbConnectionWrapperDto wrapperDto =
            dbConnectionWrapperDtoList.stream().sorted().skip(1).collect(toSingleton());
        connectionClose(wrapperDto.getConnection());
        dbConnectionWrapperDtoList.remove(wrapperDto);
    }

    private static DbConnectionWrapperDto getLastUsedWrapper() {
        Comparator<DbConnectionWrapperDto> lastUsedComparator = (o1, o2) ->
            (o1.getLastUsedDateTime().isBefore(o2.getLastUsedDateTime())) ? 1 : 0;

        return dbConnectionWrapperDtoList.stream()
            .sorted(lastUsedComparator)
            .skip(1)
            .collect(toSingleton());
    }

    private static DbConnectionWrapperDto createConnection(DbConnectionInfoDto connInfo) {
        if(dbConnectionWrapperDtoList.size() >= MAX_CONNECTION_CNT) lastUsedWrapperRemove();

        return DbConnectionWrapperDto.builder().dbConnectionInfoDto(connInfo)
            .connection(connection(connInfo))
            .lastUsedDateTime(LocalDateTime.now())
            .build();
    }

    private static Connection connection(DbConnectionInfoDto connInfo) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connInfo.getDbUrl(), connInfo.getDbUsername(),
                connInfo.getDbPassword());
        } catch (SQLException se) {
            log.error(se.toString(), se);
        }
        return conn;
    }

    private static DbConnectionWrapperDto getDbConnectionOrCreate(
        DbConnectionInfoDto connInfo) {
        DbConnectionWrapperDto wrapperDto = getDbConnectionInfoDtoIsExist(connInfo)
            .orElseGet(() -> createConnection(connInfo));

        wrapperDto.setLastUsedDateTime(LocalDateTime.now());

        return wrapperDto;
    }

    private static Optional<DbConnectionWrapperDto> getDbConnectionInfoDtoIsExist(
        DbConnectionInfoDto connInfo) {
        return Optional.ofNullable(getDbConnectionInfoDto(connInfo));
    }

    private static DbConnectionWrapperDto getDbConnectionInfoDto(
        DbConnectionInfoDto connInfo) {
        return dbConnectionWrapperDtoList.stream()
            .filter(t -> t.getDbConnectionInfoDto().equalsDbUrl(connInfo))
            .collect(toSingleton());
    }

    private static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                if (list.size() != 1) {
                    return null;
                }
                return list.get(0);
            }
        );
    }
}
