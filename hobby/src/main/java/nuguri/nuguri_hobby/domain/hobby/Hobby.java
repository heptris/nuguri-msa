package nuguri.nuguri_hobby.domain.hobby;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuguri.nuguri_hobby.domain.BaseEntity;
import nuguri.nuguri_hobby.domain.hobbyFavorite.HobbyFavorite;
import nuguri.nuguri_hobby.domain.hobbyHistory.HobbyHistory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class Hobby extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    private List<HobbyHistory> hobbyHistoryList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    private List<HobbyFavorite> hobbyFavoriteList = new ArrayList<>();

    private Long localId;

    private Long categoryId;
    @Column(name = "member_id")
    private Long memberId;

    private String title;

    private String content;

    private LocalDateTime endDate;

    private String meetingPlace;

    private boolean isClosed;

    private int curNum;

    private int maxNum;

    private int fee;

    private int rowAgeLimit;

    private int highAgeLimit;

    private char sexLimit;

    private String hobbyImage;

}
