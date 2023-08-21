package nuguri.nuguri_hobby.repository.hobbyHistory;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.domain.hobby.Hobby;
import nuguri.nuguri_hobby.dto.HobbyDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryListDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;
import static java.lang.Boolean.TRUE;
import static nuguri.nuguri_hobby.domain.hobby.QHobby.hobby;
import static nuguri.nuguri_hobby.domain.hobbyFavorite.QHobbyFavorite.hobbyFavorite;
import static nuguri.nuguri_hobby.domain.hobbyHistory.QHobbyHistory.hobbyHistory;


public class HobbyHistoryRepositoryImpl implements HobbyHistoryRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    @Autowired
    public HobbyHistoryRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<HobbyHistoryListDto> userByStatus(Long hobbyId, ApproveStatus approveStatus) {
        List<HobbyHistoryListDto> hobbyHistoryDtoList = new LinkedList<HobbyHistoryListDto>();
        List<HobbyHistoryDto> temp = queryFactory.select(Projections.constructor(HobbyHistoryDto.class,
                        hobbyHistory.id,
                        hobbyHistory.hobby.id,
                        hobbyHistory.memberId,
                        hobbyHistory.isPromoter,
                        hobbyHistory.approveStatus
                ))
                .from(hobbyHistory)
                .where(hobbyHistory.hobby.id.eq(hobbyId)
                        .and(hobbyHistory.approveStatus.eq(approveStatus)))
                .fetch();
        for (int i = 0; i < temp.size(); i++) {
            HobbyHistoryDto now = temp.get(i);
            Long memberId = now.getMemberId();
            hobbyHistoryDtoList.add(HobbyHistoryListDto.builder()
                            .hobbyHistoryId(now.getHobbyHistoryId())
                            .hobbyId(now.getHobbyId())
                            .memberId(now.getMemberId())
                            .isPromoter(now.isPromoter())
                            .approveStatus(now.getApproveStatus())
//                            .nickname()
//                            .imageUrl()
                    .build());

        }
        return hobbyHistoryDtoList;
    }
    @Override
    public ApproveStatus changeStatus(Long hobbyHistoryId, ApproveStatus status) {
        queryFactory.selectFrom(hobbyHistory)
                .where(hobbyHistory.id.eq(hobbyHistoryId))
                .fetchOne()
                .updateApproveStatus(status);
        return status;
    }


    @Override
    public HobbyHistoryDto findByHobbyAndMemberIdDto(Long hobbyId, Long memberId){
        HobbyHistoryDto hobbyHistoryDto = queryFactory
                .select(Projections.constructor(HobbyHistoryDto.class,
                        hobbyHistory.id,
                        hobbyHistory.hobby.id,
                        hobbyHistory.memberId,
                        hobbyHistory.isPromoter,
                        hobbyHistory.approveStatus
                ))
                .from(hobbyHistory)
                .where(hobbyHistory.hobby.id.eq(hobbyId),
                        hobbyHistory.memberId.eq(memberId),
                        hobbyHistory.approveStatus.eq(ApproveStatus.READY))
                .fetchOne();
        return hobbyHistoryDto;
    }

    @Override
    public HobbyHistoryDto findByIdDto(Long hobbyHistoryId) {
        HobbyHistoryDto hobbyHistoryDto = queryFactory
                .select(Projections.constructor(HobbyHistoryDto.class,
                        hobbyHistory.id,
                        hobbyHistory.hobby.id,
                        hobbyHistory.memberId,
                        hobbyHistory.isPromoter,
                        hobbyHistory.approveStatus
                        ))
                .from(hobbyHistory)
                .where(hobbyHistory.id.eq(hobbyHistoryId))
                .fetchOne();
        return hobbyHistoryDto;
    }

    @Override
    public List<HobbyHistoryResponseDto> findHistoryList(Long memberId) {
        return queryFactory
                .select(Projections.constructor(HobbyHistoryResponseDto.class,
                        hobby.id,
                        hobby.localId,
                        hobby.categoryId,
                        hobby.title,
                        hobby.endDate,
                        hobby.isClosed,
                        hobby.curNum,
                        hobby.maxNum,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(count(hobbyFavorite.id))
                                        .from(hobbyFavorite)
                                        .where(hobbyFavorite.hobby.id.eq(hobby.id)),"wishlistNum"
                        ),
                        hobby.hobbyImage,
                        hobbyHistory.approveStatus
                        ))
                .from(hobbyHistory)
                .where(hobbyHistory.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public Long findOwnerId(Hobby hobby) {
        Long ownerId = queryFactory
                .select(hobbyHistory.memberId)
                .from(hobbyHistory)
                .where(hobbyHistory.hobby.eq(hobby).and(hobbyHistory.isPromoter.eq(TRUE)))
                .fetchOne();
        return ownerId;
    }

    @Override
    public boolean DuplicateCheck(Long memberId, Long hobbyId) {
        int result = queryFactory.selectFrom(hobbyHistory)
                .where(hobbyHistory.memberId.eq(memberId)
                        ,hobbyHistory.hobby.id.eq(hobbyId)).fetch().size();
        if(result == 0) return false; // 신규
        else return true; // 이미 있음
    }


}
