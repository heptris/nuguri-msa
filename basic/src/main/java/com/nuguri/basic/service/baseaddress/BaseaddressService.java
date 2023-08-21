package com.nuguri.basic.service.baseaddress;

import com.nuguri.basic.domain.baseaddress.BaseAddress;
import com.nuguri.basic.dto.baseaddress.*;
import com.nuguri.basic.exception.ex.CustomException;
import com.nuguri.basic.repository.baseaddress.BaseaddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nuguri.basic.exception.ex.ErrorCode.BASEADDRESS_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BaseaddressService {
    private final BaseaddressRepository baseaddressRepository;


    public BaseAddressIdResponseDto findBySidoGugunDong(BaseAddressSidoGugunDongDto requestDto){
        BaseAddress baseAddress = baseaddressRepository.findBySidoAndGugunAndDong(requestDto.getSido(), requestDto.getGugun(), requestDto.getDong()).orElseThrow(() -> new CustomException(BASEADDRESS_NOT_FOUND));
        BaseAddressIdResponseDto responseDto = BaseAddressIdResponseDto.builder()
                .id(baseAddress.getId())
                .build();

        return responseDto;
    }

    public BaseAddressSidoGugunDongDto findByLocalId(BaseAddressIdRequestDto requestDto){
        BaseAddress baseAddress = baseaddressRepository.findById(requestDto.getLocalId()).orElseThrow(() -> new CustomException(BASEADDRESS_NOT_FOUND));
        BaseAddressSidoGugunDongDto responseDto = BaseAddressSidoGugunDongDto.builder()
                .sido(baseAddress.getSido())
                .gugun(baseAddress.getGugun())
                .dong(baseAddress.getDong())
                .build();

        return responseDto;
    }

    public List<BaseAddressResponseDto> findBaseaddressByDong(String keyword){
        return baseaddressRepository.findBaseaddressByDong(keyword);
    }

    public List<BaseAddressSidoDto> sidoList(){
        return baseaddressRepository.findSidoList();
    }

    public List<BaseAddressGugunDto> gugunList(String sido){
        return baseaddressRepository.findGugunList(sido);
    }

    public List<BaseAddressDongDto> dongList(String gugun){
        return baseaddressRepository.findDongList(gugun);
    }

    public List<BaseAddressDto> findAllBaseAddress(){
        return baseaddressRepository.findAllBaseAddress();
    }
}
