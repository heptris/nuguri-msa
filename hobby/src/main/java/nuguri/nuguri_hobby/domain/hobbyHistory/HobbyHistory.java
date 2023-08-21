package nuguri.nuguri_hobby.domain.hobbyHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.domain.BaseEntity;
import nuguri.nuguri_hobby.domain.hobby.Hobby;
import nuguri.nuguri_hobby.dto.HobbyHistoryDto;

import javax.persistence.*;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class HobbyHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_history_id")
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    private boolean isPromoter;

    @Enumerated(EnumType.STRING)
    private ApproveStatus approveStatus;

    public void updateApproveStatus(ApproveStatus approveStatus){
        this.approveStatus = approveStatus;
    }

    public HobbyHistoryDto EntityToDto() {
        return HobbyHistoryDto.builder()
                .hobbyHistoryId(this.id)
                .memberId(this.memberId)
                .hobbyId(this.hobby.getId())
                .isPromoter(this.isPromoter)
                .approveStatus(this.approveStatus)
                .build();
    }
}
