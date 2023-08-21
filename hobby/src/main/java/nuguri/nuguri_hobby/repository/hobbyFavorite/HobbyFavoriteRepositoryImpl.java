package nuguri.nuguri_hobby.repository.hobbyFavorite;

import com.querydsl.jpa.impl.JPAQueryFactory;
import nuguri.nuguri_hobby.domain.hobbyFavorite.HobbyFavorite;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static nuguri.nuguri_hobby.domain.hobbyFavorite.QHobbyFavorite.hobbyFavorite;


public class HobbyFavoriteRepositoryImpl implements HobbyFavoriteRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Autowired
    public HobbyFavoriteRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public HobbyFavorite findByMemberIdAndHobbyId(Long memberId, Long hobbyId) {
        return queryFactory.selectFrom(hobbyFavorite)
                .where(hobbyFavorite.memberId.eq(memberId),
                        hobbyFavorite.hobby.id.eq(hobbyId))
                .fetchOne();
    }

    @Override
    public Integer getFavoriteNumberByHobbyId(Long hobbyId) {
        return queryFactory.selectFrom(hobbyFavorite)
                .where(hobbyFavorite.hobby.id.eq(hobbyId))
                .fetch().size();
    }

    @Override
    public boolean favoritecheck(Long memberId, Long hobbyId) {
        Integer result = queryFactory.selectFrom(hobbyFavorite)
                .where(hobbyFavorite.memberId.eq(memberId)
                        ,hobbyFavorite.hobby.id.eq(hobbyId))
                .fetch().size();
        return (result == 0)?false:true;
    }


}
