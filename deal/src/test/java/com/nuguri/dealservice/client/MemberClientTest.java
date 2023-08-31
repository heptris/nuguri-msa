package com.nuguri.dealservice.client;

import com.nuguri.dealservice.dto.member.MemberIdRequestDto;
import com.nuguri.dealservice.dto.member.MemberInfoResponseDto;
import com.nuguri.dealservice.dto.member.MemberNicknameResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberClientTest {

    @Autowired
    MemberClient memberClient;

    @Test
    public void memberTest(){
        MemberInfoResponseDto memberInfoById = memberClient.getMemberInfoById(MemberIdRequestDto.builder()
                .memberId(1L).build()).getBody();
        System.out.println("memberInfoById = " + memberInfoById);

    }

    @Test
    public void getNickNameTest(){
        MemberNicknameResponseDto nicknameByMemberId = memberClient.getNicknameByMemberId(MemberIdRequestDto.builder()
                .memberId(1L).build());
        System.out.println("nicknameByMemberId = " + nicknameByMemberId);
    }

}