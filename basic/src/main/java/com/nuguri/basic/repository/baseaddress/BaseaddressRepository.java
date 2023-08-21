package com.nuguri.basic.repository.baseaddress;

import com.nuguri.basic.domain.baseaddress.BaseAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseaddressRepository extends JpaRepository<BaseAddress, Long>, BaseaddressRepositoryCustom {
    Optional<BaseAddress> findBySidoAndGugunAndDong(String sido, String gugun, String dong);
}
