package nuguri.nuguri_member.service;

import nuguri.nuguri_member.dto.deal.DealListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void deal목록조회() throws Exception{
        List<DealListDto> testtest = memberService.testtest(1L);
        for (DealListDto dealListDto : testtest) {
            System.out.println("dealListDto = " + dealListDto);
        }
    }

}