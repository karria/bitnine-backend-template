package net.bitnine.template.model.dto;

import java.sql.Connection;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DbConnectionWrapperDto {
    DbConnectionInfoDto dbConnectionInfoDto;
    Connection connection;
    LocalDateTime lastUsedDateTime;
}
