package nuguri.nuguri_hobby.service.hobbyFavorite;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_hobby.client.AuthClient;
import nuguri.nuguri_hobby.domain.hobby.Hobby;
import nuguri.nuguri_hobby.domain.hobbyFavorite.HobbyFavorite;
import nuguri.nuguri_hobby.dto.HobbyIdRequestDto;
import nuguri.nuguri_hobby.exception.ex.CustomException;
import nuguri.nuguri_hobby.repository.hobby.HobbyRepository;
import nuguri.nuguri_hobby.repository.hobbyFavorite.HobbyFavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nuguri.nuguri_hobby.exception.ex.ErrorCode.HOBBY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HobbyFavoriteService {
    private final HobbyFavoriteRepository hobbyFavoriteRepository;
    private final HobbyRepository hobbyRepository;

    @Transactional
    public boolean createOrModifyHobbyFavorite(Long hobbyId, Long memberId) {
        HobbyFavorite hobbyFavorite = hobbyFavoriteRepository.findByMemberIdAndHobbyId(memberId, hobbyId);
        if(hobbyFavorite == null){
            Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(()-> new CustomException(HOBBY_NOT_FOUND));
            HobbyFavorite newHobbyFavorite = HobbyFavorite.builder()
                    .memberId(memberId)
                    .hobby(hobby)
                    .isFavorite(true)
                    .build();
            hobbyFavoriteRepository.save(newHobbyFavorite);
            return true;
        }else{
            return hobbyFavorite.changeFavorite();
        }
    }

    public Integer getFavoriteCnt(Long hobbyId){
        return hobbyFavoriteRepository.getFavoriteNumberByHobbyId(hobbyId);
    }

    public boolean favoritecheck(Long hobbyId, Long memberId){
        return hobbyFavoriteRepository.favoritecheck(memberId,hobbyId);
    }


}
