package com.nuguri.dealservice.repository;

import com.nuguri.dealservice.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long>, DealRepositoryCustom {
}
