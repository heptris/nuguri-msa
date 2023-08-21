package nuguri.nuguri_hobby.repository.hobby;




import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.dto.HobbyDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryRegionCategoryRequestDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryResponseDto;

import java.util.List;

public interface HobbyRepositoryCustom {
    // 특정 지역에서 특정 카테고리에 포함되는 모든 취미방 목록
    List<HobbyHistoryRegionCategoryRequestDto> findByRegionAndCategory(Long RegionId, Long CategoryId);

    // hobbyId로 취미방 찾기(취미방 상세보기 클릭 시)
    HobbyDto hobbyDetail(Long hobbyId);
    List<HobbyHistoryResponseDto> findByMemberIdAndStatus(Long memberId, ApproveStatus approveStatus);
    List<HobbyHistoryResponseDto> findByMemberIdAndPromoter(Long memberId);
    List<HobbyHistoryResponseDto> findByMemberIdAndFavorite(Long memberId);

}
