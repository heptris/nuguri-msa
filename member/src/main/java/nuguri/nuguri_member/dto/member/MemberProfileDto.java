package nuguri.nuguri_member.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileDto {
    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private String description;
    private Double temperature;
    private String profileImage;
    private String baseAddress;
    private Long localId;
}
