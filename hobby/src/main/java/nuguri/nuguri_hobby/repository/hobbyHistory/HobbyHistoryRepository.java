package nuguri.nuguri_hobby.repository.hobbyHistory;


import nuguri.nuguri_hobby.domain.hobbyHistory.HobbyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyHistoryRepository extends JpaRepository<HobbyHistory, Long>, HobbyHistoryRepositoryCustom {

}
