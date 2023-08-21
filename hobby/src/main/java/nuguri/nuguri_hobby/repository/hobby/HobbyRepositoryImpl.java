package nuguri.nuguri_hobby.repository.hobby;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.dto.HobbyDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryRegionCategoryRequestDto;
import nuguri.nuguri_hobby.dto.HobbyHistoryResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;
import static nuguri.nuguri_hobby.domain.hobby.QHobby.hobby;
import static nuguri.nuguri_hobby.domain.hobbyFavorite.QHobbyFavorite.hobbyFavorite;
import static nuguri.nuguri_hobby.domain.hobbyHistory.QHobbyHistory.hobbyHistory;

public class HobbyRepositoryImpl implements HobbyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public HobbyRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    public List<HobbyHistoryRegionCategoryRequestDto> findByRegionAndCategory(Long RegionId, Long CategoryId) {
        List<HobbyHistoryRegionCategoryRequestDto> hobbyHistoryRegionCategoryRequestDto = queryFactory.select(Projections.constructor(HobbyHistoryRegionCategoryRequestDto.class,
                        hobby.id,
                        hobby.localId,
                        hobby.categoryId,
                        hobby.rowAgeLimit,
                        hobby.highAgeLimit,
                        hobby.sexLimit,
                        hobby.hobbyImage
                ))
                .from(hobby)
                .where(hobby.isClosed.eq(Boolean.FALSE))
                .fetch();
        return hobbyHistoryRegionCategoryRequestDto;
    }


    private BooleanExpression RegionEq(Long RegionId) {
        return RegionId == null ? null : hobby.localId.eq(RegionId);
    }
    private BooleanExpression CategoryEq(Long CategoryId) {
        return CategoryId == null ? null : hobby.categoryId.eq(CategoryId);
    }

    @Override
    public HobbyDto hobbyDetail(Long hobbyId) {
        HobbyDto hobbyDto = queryFactory.select(Projections.constructor(HobbyDto.class,
                        hobby.id,
                        hobby.localId,
                        hobby.categoryId,
                        hobby.memberId,
                        Expressions.asString("dummy").as("nickname"),
                        hobby.title,
                        hobby.content,
                        hobby.endDate,
                        hobby.meetingPlace,
                        hobby.isClosed,
                        hobby.curNum,
                        hobby.maxNum,
                        hobby.fee,
                        hobby.rowAgeLimit,
                        hobby.highAgeLimit,
                        hobby.sexLimit,
                        hobby.hobbyImage,
                        Expressions.asString("dummy").as("profileImage")
                ))
                .from(hobby)
                .where(hobby.id.eq(hobbyId))
                .fetchOne();
        return hobbyDto;
    }


    // 승호님 코드
    @Override
    public List<HobbyHistoryResponseDto> findByMemberIdAndStatus(Long memberId, ApproveStatus approveStatus) {
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList = queryFactory.select(Projections.constructor(HobbyHistoryResponseDto.class,
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
                        hobby.hobbyImage
                ))
                .from(hobby)
                .where(
                        hobbyHistory.memberId.eq(memberId),
                        hobbyHistory.approveStatus.eq(approveStatus)
                )
                .fetch();
        return hobbyHistoryResponseDtoList;
    }

    @Override
    public List<HobbyHistoryResponseDto> findByMemberIdAndPromoter(Long memberId) {
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList = queryFactory.select(Projections.constructor(HobbyHistoryResponseDto.class,
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
                        hobby.hobbyImage
                ))
                .from(hobby)
                .where(
                        hobbyHistory.memberId.eq(memberId),
                        hobbyHistory.isPromoter.eq(Boolean.TRUE)
                )
                .fetch();
        return hobbyHistoryResponseDtoList;
    }

    @Override
    public List<HobbyHistoryResponseDto> findByMemberIdAndFavorite(Long memberId) {
        List<HobbyHistoryResponseDto> hobbyHistoryResponseDtoList = queryFactory.select(Projections.constructor(HobbyHistoryResponseDto.class,
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
                        hobby.hobbyImage
                ))
                .from(hobby)
                .innerJoin(hobby.hobbyFavoriteList, hobbyFavorite)
                .where(
                        hobbyHistory.memberId.eq(memberId),
                        hobbyFavorite.isFavorite.eq(Boolean.TRUE)
                )
                .fetch();
        return hobbyHistoryResponseDtoList;
    }




}
