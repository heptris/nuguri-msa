package nuguri.nuguri_member.dto.hobby;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HobbyHistoryResponseDto {

    private Long categoryId;

    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Integer curNum;

    private Integer maxNum;

    private Long wishlistNum;

    private Integer chatNum;

    private String imageurl;

    private ApproveStatus approveStatus;


}
