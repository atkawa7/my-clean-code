package com.example.lostandfound.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemLostRepository extends JpaRepository<ItemLost, Long>, JpaSpecificationExecutor<ItemLost> {
}