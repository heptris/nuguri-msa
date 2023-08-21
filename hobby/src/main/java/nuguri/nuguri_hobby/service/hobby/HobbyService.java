package nuguri.nuguri_hobby.service.hobby;

import lombok.RequiredArgsConstructor;
import nuguri.nuguri_hobby.client.MemberClient;
import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.domain.hobby.Hobby;
import nuguri.nuguri_hobby.domain.hobbyHistory.HobbyHistory;
import nuguri.nuguri_hobby.domain.s3.AwsS3;
import nuguri.nuguri_hobby.dto.*;
import nuguri.nuguri_hobby.dto.member.MemberIdRequestDto;
import nuguri.nuguri_hobby.dto.member.MemberProfileDto;
import nuguri.nuguri_hobby.dto.member.MemberProfileRequestDto;
import nuguri.nuguri_hobby.dto.member.ToMemberTempDto;
import nuguri.nuguri_hobby.messagequeue.KafkaProducer;
import nuguri.nuguri_hobby.repository.hobby.HobbyRepository;
import nuguri.nuguri_hobby.repository.hobbyHistory.HobbyHistoryRepository;
import nuguri.nuguri_hobby.service.s3.AwsS3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyRepository hobbyRepository;
    private final HobbyHistoryRepository hobbyHistoryRepository;
    private final AwsS3Service awsS3Service;
    // kafka
    private final KafkaProducer kafkaProducer;
    // feignClient
    private final MemberClient memberClient;

    @Transactional
    public List<HobbyHistoryRegionCategoryRequestDto> findLocalCategoryHobbyList(HobbyListRequestDto hobbyListRequestDto){ // 지역과 카테고리로 취미방 찾기
        return hobbyRepository.findByRegionAndCategory(hobbyListRequestDto.getRegionId(),hobbyListRequestDto.getCategoryId());
    }

    @Transactional
    public HobbyDto findHobbyDetail(Long hobbyId){ // 취미방 상세보기
        HobbyDto hobbyDto = hobbyRepository.hobbyDetail(hobbyId);

        // feignClient로 유저정보 받아오기
        MemberIdRequestDto memberIdRequestDto = MemberIdRequestDto.builder().memberId(hobbyDto.getMemberId()).build();
        String nickname = memberClient.getNicknameByMemberId(memberIdRequestDto).getBody().getNickname();

        MemberProfileRequestDto memberProfileRequestDto = MemberProfileRequestDto.builder().nickname(nickname).build();
        MemberProfileDto memberProfileDto = memberClient.profile(memberProfileRequestDto).getBody();

        hobbyDto.setCreatorNickname(nickname);
        hobbyDto.setCreatorProfileImage(memberProfileDto.getProfileImage());

        return hobbyDto;
    }

    @Transactional
    public Long createHobby(HobbyCreateRequestDto hobbyCreateRequestDto, MultipartFile hobbyImage,Long memberId){ // 취미방 생성


        String hobbyImageUrl;
        if(hobbyImage == null){
            hobbyImageUrl = "";
        }else{
            AwsS3 awsS3 = new AwsS3();
            try {
                awsS3 = awsS3Service.upload(hobbyImage, "hobbyImage");
            }catch (IOException e){
                System.out.println(e);
            }
            hobbyImageUrl = awsS3.getPath();
        }

        // hobby객체 생성
        Hobby hobbyEntity = Hobby.builder()
                .localId(hobbyCreateRequestDto.getLocalId())
                .categoryId(hobbyCreateRequestDto.getCategoryId())
                .title(hobbyCreateRequestDto.getTitle())
                .content(hobbyCreateRequestDto.getContent())
                .endDate(hobbyCreateRequestDto.getEndDate())
                .meetingPlace(hobbyCreateRequestDto.getMeetingPlace())
                .isClosed(false)
                .curNum(1)
                .maxNum(hobbyCreateRequestDto.getMaxNum())
                .fee(hobbyCreateRequestDto.getFee())
                .rowAgeLimit(hobbyCreateRequestDto.getRowAgeLimit())
                .highAgeLimit(hobbyCreateRequestDto.getHighAgeLimit())
                .sexLimit(hobbyCreateRequestDto.getSexLimit())
                .hobbyImage(hobbyImageUrl)
                .build();

        // hobbyHistory 생성
        HobbyHistory hobbyHistoryEntity = HobbyHistory.builder()
                .memberId(memberId)
                .hobby(hobbyEntity)
                .isPromoter(true)
                .approveStatus(ApproveStatus.APPROVE)
                .build();

        hobbyHistoryRepository.save(hobbyHistoryEntity);

        kafkaProducer.send("hobby-topic",
                ToMemberTempDto.builder().hobbyOwnerId(hobbyEntity.getId()).build());

        return hobbyEntity.getId();
    }

    @Transactional
    public List<HobbyHistoryResponseDto> findByMemberIdAndStatus(Long memberId, ApproveStatus approveStatus){
        return hobbyRepository.findByMemberIdAndStatus(memberId,approveStatus);
    }

    @Transactional
    public List<HobbyHistoryResponseDto> findByMemberIdAndPromoter(Long memberId) {
        return hobbyRepository.findByMemberIdAndPromoter(memberId);
    }
    @Transactional
    public List<HobbyHistoryResponseDto> findByMemberIdAndFavorite(Long memberId) {
        return hobbyRepository.findByMemberIdAndFavorite(memberId);
    }


}
