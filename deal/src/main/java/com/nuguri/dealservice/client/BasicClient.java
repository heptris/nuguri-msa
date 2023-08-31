package com.nuguri.dealservice.client;

import com.nuguri.dealservice.dto.baseaddress.BaseAddressDto;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressIdRequestDto;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressSidoGugunDongDto;
import com.nuguri.dealservice.dto.category.CategoryListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "basic-service")
public interface BasicClient {

    @GetMapping("/app/base-address/list")
    List<BaseAddressDto> getAllBaseaddress();

    @PostMapping("/app/base-address/local/search/local-id")
    BaseAddressSidoGugunDongDto findByLocalId(@RequestBody BaseAddressIdRequestDto requestDto);

    @GetMapping("/app/category/list")
    List<CategoryListDto> getAllCategory();

}
