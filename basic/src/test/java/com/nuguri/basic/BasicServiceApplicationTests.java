package com.nuguri.basic;

import com.nuguri.basic.dto.baseaddress.BaseAddressDto;
import com.nuguri.basic.service.baseaddress.BaseaddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class BasicServiceApplicationTests {

	@Autowired
	BaseaddressService baseaddressService;

	@Test
	public void 전체목록조회() throws Exception{
		List<BaseAddressDto> allBaseAddress = baseaddressService.findAllBaseAddress();
		for (BaseAddressDto baseAddress : allBaseAddress) {
			System.out.println("baseAddress = " + baseAddress);
		}
	}

}
