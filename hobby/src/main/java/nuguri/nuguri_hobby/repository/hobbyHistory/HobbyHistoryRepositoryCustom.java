package nuguri.nuguri_hobby.repository.hobbyHistory;




import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.domain.hobby.Hobby;
import nuguri.nuguri_hobby.dto.HobbyHistoryDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryListDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryResponseDto;

import java.util.List;

public interface HobbyHistoryRepositoryCustom {

    List<HobbyHistoryListDto> userByStatus(Long hobbyId, ApproveStatus approveStatus);
    // 방장이 승인여부를 변경했을 때 (True -> 승인, False -> 반려)
    ApproveStatus changeStatus(Long hobbyHistoryId, ApproveStatus status);

    HobbyHistoryDto findByHobbyAndMemberIdDto(Long hobbyId, Long memberId);
    HobbyHistoryDto findByIdDto(Long hobbyHistoryId);
    List<HobbyHistoryResponseDto> findHistoryList(Long memberId);

    boolean DuplicateCheck(Long memberId, Long hobbyId);

    Long findOwnerId(Hobby hobby);
}
