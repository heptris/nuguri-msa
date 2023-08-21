package com.nuguri.dealservice.client;

import com.nuguri.dealservice.dto.member.MemberIdRequestDto;
import com.nuguri.dealservice.dto.member.MemberInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service", path = "/app/member")
public interface MemberClient {

    @PostMapping("/to/hobby/constraints")
    ResponseEntity<MemberInfoResponseDto> getMemberInfoById(@RequestBody MemberIdRequestDto memberIdRequestDto);
}
