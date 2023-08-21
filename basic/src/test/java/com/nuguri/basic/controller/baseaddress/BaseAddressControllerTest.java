package com.nuguri.basic.controller.baseaddress;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class BaseAddressControllerTest {

    @Autowired
    BaseAddressController baseAddressController;

    @Test
    public void 전체목록조회() throws Exception{
        ResponseEntity responseEntity = baseAddressController.allList();
//        ResponseDto list = responseEntity.getBody();
    }
}