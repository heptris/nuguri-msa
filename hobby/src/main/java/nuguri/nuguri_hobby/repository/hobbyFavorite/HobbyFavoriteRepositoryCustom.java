package nuguri.nuguri_hobby.repository.hobbyFavorite;


import nuguri.nuguri_hobby.domain.hobbyFavorite.HobbyFavorite;

public interface HobbyFavoriteRepositoryCustom {
    HobbyFavorite findByMemberIdAndHobbyId(Long memberId, Long hobbyId);
    Integer getFavoriteNumberByHobbyId(Long hobbyId);

    boolean favoritecheck(Long memberId, Long hobbyId);

}
