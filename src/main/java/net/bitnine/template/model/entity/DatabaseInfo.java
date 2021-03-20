package net.bitnine.template.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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
    @Column(nullable = false, updatable = false)
    @CreatedBy
    String createUserId;
    @LastModifiedDate
    LocalDateTime updateDt;
    @LastModifiedBy
    String updateUserId;
}
