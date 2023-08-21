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
public class HobbyDto {
    private Long hobbyId;
    private Long localId;
    private Long categoryId;
    private Long memberId;
    private String nickname;
    private String title;
    private String content;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String meetingPlace;

    private boolean isClosed;

    private int curNum;

    private int maxNum;

    private int fee;

    private int rowAgeLimit;

    private int highAgeLimit;

    private char sexLimit;

    private String imageurl;
    private String profileImage;

    public void setCreatorNickname(String nickname){
        this.nickname = nickname;
    }
    public void setCreatorProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

}