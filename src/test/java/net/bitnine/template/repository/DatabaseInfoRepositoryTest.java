package net.bitnine.template.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import net.bitnine.template.config.AuditAwareConfig;
import net.bitnine.template.model.entity.DatabaseInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

/**
 * DatabaseInfoRepository 를 위한 테스트 클래스
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = AuditAwareConfig.class
))
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseInfoRepositoryTest {
    @Autowired
    DatabaseInfoRepository databaseInfoRepository;

    private DatabaseInfo databaseInfo;

    @BeforeEach
    void setUp() {
        databaseInfo = DatabaseInfo.builder()
            .userId("tester")
            .dbUrl("jdbc:postgresql://localhost:5432/template")
            .dbUsername("template")
            .dbPassword("template")
            .build();
    }

    @Test
    void databaseInfoRepositoryThenNotNull() {
        assertThat(databaseInfoRepository).isNotNull();
    }

    @Test
    void saveWhenInsertThenIdNonZero() {
        databaseInfo = databaseInfoRepository.save(databaseInfo);
        assertThat(databaseInfo.getId()).isGreaterThan(0);
    }

    @Test
    void findByIdWhenFindThenEqualValue() {
        databaseInfo = databaseInfoRepository.save(databaseInfo);

        Optional<DatabaseInfo> findById = databaseInfoRepository.findById(databaseInfo.getId());
        assertThat(findById.isPresent()).isTrue();
        assertThat(findById.get().getId()).isEqualTo(databaseInfo.getId());
    }

    @Test
    void saveWhenUpdateThenUpdateDbInfo() {
        DatabaseInfo findDatabaseInfo = databaseInfoRepository.save(databaseInfo);

        String updatedDbUrl = "jdbc:postgresql://localhost:5432/template2";
        String updatedDbUsername = "template2";
        String updatedDbPassword = "template2";
        findDatabaseInfo.update(updatedDbUrl, updatedDbUsername, updatedDbPassword);

        // 객체 update 시 영속석 검증을 위하여 update 후 검증
        Optional<DatabaseInfo> findById = databaseInfoRepository.findById(databaseInfo.getId());
        assertThat(findById.isPresent()).isTrue();
        assertThat(findById.get().getDbUrl()).isEqualTo(updatedDbUrl);
    }

    @Test
    void deleteThenNotFound() {
        databaseInfo = databaseInfoRepository.save(databaseInfo);

        Optional<DatabaseInfo> findById = databaseInfoRepository.findById(databaseInfo.getId());
        assertThat(findById.isPresent()).isTrue();

        databaseInfoRepository.delete(databaseInfo);

        findById = databaseInfoRepository.findById(databaseInfo.getId());
        assertThat(findById.isPresent()).isFalse();
    }
}