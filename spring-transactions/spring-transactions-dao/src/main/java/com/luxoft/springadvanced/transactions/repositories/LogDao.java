package com.luxoft.springadvanced.transactions.repositories;

import com.luxoft.springadvanced.transactions.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDao extends JpaRepository<Log, Integer>, LogDaoCustom {
}
