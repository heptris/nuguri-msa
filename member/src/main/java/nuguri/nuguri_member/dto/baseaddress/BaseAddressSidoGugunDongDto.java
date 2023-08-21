package nuguri.nuguri_member.dto.baseaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseAddressSidoGugunDongDto {
    private String sido;
    private String gugun;
    private String dong;
}
