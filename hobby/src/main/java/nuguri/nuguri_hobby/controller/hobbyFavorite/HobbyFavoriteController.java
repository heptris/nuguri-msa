package nuguri.nuguri_hobby.controller.hobbyFavorite;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import nuguri.nuguri_hobby.dto.response.ResponseDto;
import nuguri.nuguri_hobby.service.hobbyFavorite.HobbyFavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/hobby/favorite")
public class HobbyFavoriteController {


    private final HobbyFavoriteService hobbyFavoriteService;

    @ApiOperation(value = "취미방 즐겨찾기 등록/해제")
    @PostMapping("regist/{hobbyId}")
    public ResponseEntity createOrModifyHobbyFavorite(@PathVariable Long hobbyId, Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "취미방 즐겨찾기 등록/해제", (hobbyFavoriteService.createOrModifyHobbyFavorite(hobbyId, memberId))?"즐겨찾기 등록":"즐겨찾기 해제")
        );
    }

    @ApiOperation(value = "취미방에 등록된 즐겨찾기 수")
    @GetMapping("/cnt/{hobbyId}")
    public ResponseEntity getFavoriteCnt(@PathVariable Long hobbyId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(),"취미방의 즐겨찾기 수",hobbyFavoriteService.getFavoriteCnt(hobbyId))
        );
    }

    @ApiOperation(value = "유저가 특정 취미방을 즐겨찾기 했는지 여부")
    @GetMapping("/favoritecheck/{hobbyId}")

    public ResponseEntity isFavorite(@PathVariable Long hobbyId, Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(),"취미방 즐겨찾기 여부",hobbyFavoriteService.favoritecheck(hobbyId, memberId))
        );
    }



}
