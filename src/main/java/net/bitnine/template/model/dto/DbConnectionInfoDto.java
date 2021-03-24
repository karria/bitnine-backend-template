package net.bitnine.template.model.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class DbConnectionInfoDto {
    String dbUrl;
    String dbUsername;
    String dbPassword;

    public boolean equalsDbUrl(DbConnectionInfoDto dbConnectionInfoDto) {
        return this.dbUrl.equals(dbConnectionInfoDto.getDbUrl());
    }
}
