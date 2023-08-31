package com.nuguri.dealservice.client;

import com.nuguri.dealservice.dto.baseaddress.BaseAddressDto;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressIdRequestDto;
import com.nuguri.dealservice.dto.baseaddress.BaseAddressSidoGugunDongDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BasicClientTest {

    @Autowired
    BasicClient basicClient;

    @Test
    public void t(){
        List<BaseAddressDto> allBaseaddress = basicClient.getAllBaseaddress();
        for (BaseAddressDto baseaddress : allBaseaddress) {
            System.out.println("baseaddress = " + baseaddress);
        }
    }

    @Test
    public void findByLocalIdTest(){
        BaseAddressSidoGugunDongDto data = basicClient.findByLocalId(new BaseAddressIdRequestDto(19L));
        System.out.println("data = " + data);
    }
}