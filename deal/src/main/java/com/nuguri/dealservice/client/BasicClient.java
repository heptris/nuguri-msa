package com.nuguri.dealservice.client;

import com.nuguri.dealservice.dto.baseaddress.BaseAddressDto;
import com.nuguri.dealservice.dto.category.CategoryListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "basic-service")
public interface BasicClient {

    @GetMapping("/app/base-address/list")
    List<BaseAddressDto> getAllBaseaddress();

    @GetMapping("/app/category/list")
    List<CategoryListDto> getAllCategory();

}
