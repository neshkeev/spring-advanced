package com.luxoft.springadvanced.springdatarest.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppRepository extends JpaRepository<App, UUID> {
    void deleteAllByName(String name);
}
