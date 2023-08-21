package nuguri.nuguri_hobby.repository.hobby;

import nuguri.nuguri_hobby.domain.hobby.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby,Long>, HobbyRepositoryCustom {
}
