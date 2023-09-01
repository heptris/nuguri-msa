package nuguri.nuguri_hobby.repository.hobby;

import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.dto.HobbyHistoryResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HobbyRepositoryImplTest {

    @Autowired
    HobbyRepository hobbyRepository;

    @Test
    public void 멤버가만든취미방상태확인(){
        // APPROVE인 경우
        List<HobbyHistoryResponseDto> byMemberIdAndStatus = hobbyRepository.findByMemberIdAndStatus(1L, ApproveStatus.APPROVE);
        for (HobbyHistoryResponseDto memberIdAndStatus : byMemberIdAndStatus) {
            System.out.println("memberIdAndStatus = " + memberIdAndStatus);
        }

        System.out.println("=====================================");

        // READY인 경우
        List<HobbyHistoryResponseDto> byMemberIdAndStatus1 = hobbyRepository.findByMemberIdAndStatus(1L, ApproveStatus.READY);
        for (HobbyHistoryResponseDto hobbyHistoryResponseDto : byMemberIdAndStatus1) {
            System.out.println("hobbyHistoryResponseDto = " + hobbyHistoryResponseDto);
        }

    }
}