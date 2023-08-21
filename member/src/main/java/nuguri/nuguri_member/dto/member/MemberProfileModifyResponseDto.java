package nuguri.nuguri_member.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileModifyResponseDto {
    private String profileImage;
    private String nickname;
}
