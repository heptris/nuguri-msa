package com.nuguri.basic.repository.baseaddress;

import com.nuguri.basic.dto.baseaddress.*;

import java.util.List;

public interface BaseaddressRepositoryCustom {
    List<BaseAddressResponseDto> findBaseaddressByDong(String keyword);

    List<BaseAddressSidoDto> findSidoList();

    List<BaseAddressGugunDto> findGugunList(String sido);

    List<BaseAddressDongDto> findDongList(String gugun);

    List<BaseAddressDto> findAllBaseAddress();
}
