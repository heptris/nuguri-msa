package nuguri.nuguri_hobby.dto;

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
public class HobbyHistoryRegionCategoryRequestDto {
    private Long hobbyId;

    private Long localId;

    private Long categoryId;

    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private boolean isClosed;

    private Integer curNum;

    private Integer maxNum;

    private Long wishlistNum;

    private int rowAgeLimit;

    private int highAgeLimit;

    private char sexLimit;

    private String imageurl;

}
