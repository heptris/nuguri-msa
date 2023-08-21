package nuguri.nuguri_hobby.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HobbyHistoryRequestDto {
    Long memberId;
    Long hobbyId;
}
