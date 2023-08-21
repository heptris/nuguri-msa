package nuguri.nuguri_hobby.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToMemberTempDto {

    private Long hobbyOwnerId;
}
