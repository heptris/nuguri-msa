package nuguri.nuguri_member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String nickname;

    private String description;

    private Double temperature;

    private Integer age;

    private Character sex;

    private String profileImage;

    private Long localId;

    public void changeProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void changeLocalId(Long localId){
        this.localId = localId;
    }

    public void changeTemperature(Double temperature){
        this.temperature = temperature;
    }

    public void changeMemberId(Long memberId) {
        this.id = memberId;
    }

    public void profileModify(String profileImage, String nickname) {
        this.profileImage = profileImage;
        this.nickname = nickname;
    }
}
