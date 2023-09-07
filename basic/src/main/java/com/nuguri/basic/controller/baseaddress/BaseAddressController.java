package com.nuguri.basic.controller.baseaddress;

import com.nuguri.basic.dto.baseaddress.BaseAddressIdRequestDto;
import com.nuguri.basic.dto.baseaddress.BaseAddressSidoGugunDongDto;
import com.nuguri.basic.dto.response.ResponseDto;
import com.nuguri.basic.service.baseaddress.BaseaddressService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/base-address")
@RequiredArgsConstructor
@Slf4j
public class BaseAddressController {

    private final BaseaddressService baseaddressService;

    @PostMapping("/local/search")
    public ResponseEntity findBySidoGugunDong(@RequestBody BaseAddressSidoGugunDongDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                        baseaddressService.findBySidoGugunDong(requestDto).getId()
        );
    }

    @PostMapping("/local/search/local-id")
    public ResponseEntity findByLocalId(@RequestBody BaseAddressIdRequestDto requestDto){
        log.info("before retrieve basic data");
        return ResponseEntity.status(HttpStatus.OK).body(
                        baseaddressService.findByLocalId(requestDto)
        );
    }

    @ApiOperation(value = "지역 검색")
    @PostMapping("/{keyword}/search")
    public ResponseEntity findBaseaddressByDongList(@PathVariable String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "지역 검색 목록",
                        baseaddressService.findBaseaddressByDong(keyword))
        );
    }

    @ApiOperation(value = "시/도 목록")
    @GetMapping("/sido")
    public ResponseEntity sidoList(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "시/도 목록",
                        baseaddressService.sidoList())
        );
    }

    @ApiOperation(value = "구군 목록")
    @GetMapping("/gugun")
    public ResponseEntity gugunList(String sido){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "구군 목록",
                        baseaddressService.gugunList(sido))
        );
    }

    @ApiOperation(value = "동 목록")
    @GetMapping("/dong")
    public ResponseEntity dongList(String gugun){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "동 목록",
                        baseaddressService.dongList(gugun))
        );
    }

    @ApiOperation(value = "전체 지역 목록")
    @GetMapping("/list")
    public ResponseEntity allList(){
        return ResponseEntity.status(HttpStatus.OK).body(baseaddressService.findAllBaseAddress());
    }

}
