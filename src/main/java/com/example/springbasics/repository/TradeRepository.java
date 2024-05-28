package com.example.springbasics.repository;

import com.example.springbasics.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends CrudRepository<Trade, Long>, JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {
}
