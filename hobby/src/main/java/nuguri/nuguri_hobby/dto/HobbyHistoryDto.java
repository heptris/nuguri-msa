package nuguri.nuguri_hobby.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuguri.nuguri_hobby.domain.ApproveStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HobbyHistoryDto {
    private Long hobbyHistoryId;
    private Long hobbyId;
    private Long memberId;
    private boolean isPromoter;
    private ApproveStatus approveStatus;

}
