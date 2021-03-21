package net.bitnine.template.repository;

import net.bitnine.template.model.entity.DatabaseInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseInfoRepository extends CrudRepository<DatabaseInfo, Long> {

}
