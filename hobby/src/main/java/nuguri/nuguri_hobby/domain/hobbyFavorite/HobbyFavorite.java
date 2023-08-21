package nuguri.nuguri_hobby.domain.hobbyFavorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuguri.nuguri_hobby.domain.hobby.Hobby;

import javax.persistence.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Entity
public class HobbyFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_favorite_id")
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;
    private boolean isFavorite;

    public boolean changeFavorite(){
        this.isFavorite = !this.isFavorite;
        return this.isFavorite;
    }

}
