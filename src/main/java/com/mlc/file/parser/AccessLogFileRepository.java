package com.mlc.file.parser;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccessLogFileRepository extends CrudRepository<AccessLog, Long> {

    @Query("SELECT al.originIp FROM AccessLog al WHERE eventTime >= ?1 AND eventTime <= ?2 GROUP BY al.originIp HAVING COUNT(al.originIp) > ?3")
    List<AccessLog> findByEventTimeBetweenHavingCountMoreThan(Date startDate, Date finalDate, long threshold);

}
