package net.bitnine.template.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DatabaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String userId;
    @Column(nullable = false)
    String dbUrl;
    @Column(nullable = false)
    String dbUsername;
    @Column(nullable = false)
    String dbPassword;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    LocalDateTime createDt;
    @Column(updatable = false)
    @CreatedBy
    String createUserId;
    @LastModifiedDate
    LocalDateTime updateDt;
    @LastModifiedBy
    String updateUserId;

    @Builder
    public DatabaseInfo(String userId, String dbUrl, String dbUsername, String dbPassword) {
        this.userId = userId;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public DatabaseInfo update(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        return this;
    }
}
