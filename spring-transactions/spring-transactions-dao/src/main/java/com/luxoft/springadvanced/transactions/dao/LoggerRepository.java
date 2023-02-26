package com.luxoft.springadvanced.transactions.dao;

import com.luxoft.springadvanced.transactions.domain.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface LoggerRepository extends CrudRepository<Log, Integer> {

    @Override
    <S extends Log> S save(S entity);
}
