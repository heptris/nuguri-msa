package nuguri.nuguri_hobby.repository.hobbyFavorite;


import nuguri.nuguri_hobby.domain.hobbyFavorite.HobbyFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyFavoriteRepository extends JpaRepository<HobbyFavorite,Long>,HobbyFavoriteRepositoryCustom {

}
