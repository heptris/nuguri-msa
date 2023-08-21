package nuguri.nuguri_hobby.service.hobbyHistory;

import lombok.RequiredArgsConstructor;
import nuguri.nuguri_hobby.client.MemberClient;
import nuguri.nuguri_hobby.domain.ApproveStatus;
import nuguri.nuguri_hobby.domain.hobby.Hobby;
import nuguri.nuguri_hobby.domain.hobbyHistory.HobbyHistory;
import nuguri.nuguri_hobby.dto.*;
import nuguri.nuguri_hobby.dto.member.MemberIdRequestDto;
import nuguri.nuguri_hobby.dto.member.MemberInfoResponseDto;
import nuguri.nuguri_hobby.exception.ex.CustomException;
import nuguri.nuguri_hobby.repository.hobby.HobbyRepository;
import nuguri.nuguri_hobby.repository.hobbyHistory.HobbyHistoryRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static nuguri.nuguri_hobby.exception.ex.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HobbyHistoryService {
    private final HobbyHistoryRepository hobbyHistoryRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final HobbyRepository hobbyRepository;
    private final MemberClient memberClient;



    @Transactional
    public Long joinHobbyHistory(Long hobbyId, Long memberId) { // 취미방 참여
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(() -> new CustomException(HOBBY_NOT_FOUND));
        if(hobbyHistoryRepository.DuplicateCheck(memberId,hobbyId)) throw new CustomException(ALREADY_USED_HOBBY_HISTORY);
        MemberInfoResponseDto memberInfo = memberClient.getMemberInfoById(MemberIdRequestDto.builder().memberId(memberId).build()).getBody();


        // 조건 미달
        if(hobby.getCurNum() >= hobby.getMaxNum()) throw new CustomException(FULL_HOBBY_ERROR); // 정원초과
        else if(hobby.getRowAgeLimit() > memberInfo.getAge()) throw new CustomException(AGE_LIMIT_ERROR); // 나이제한
        else if(hobby.getHighAgeLimit() < memberInfo.getAge()) throw new CustomException(AGE_LIMIT_ERROR); // 나이제한
        else if(hobby.getSexLimit() ==  memberInfo.getSex()) throw new CustomException(SEX_LIMIT_ERROR); // 성별제한
        else if(hobby.getLocalId() != memberInfo.getLocalId()) throw new CustomException(DIFF_ADDRESS_ERROR); // 지역제한


        HobbyHistory hobbyHistoryEntity = HobbyHistory.builder()
                .memberId(memberId)
                .hobby(hobby)
                .isPromoter(false)
                .approveStatus(ApproveStatus.READY)
                .build();

//        /**
//         * 알람 보내기 시작
//         */
//        Long ownerId = hobbyHistoryRepository.findOwnerId(hobby);
//        Member alarmReceiver = new Member();
//        alarmReceiver.changeMemberId(ownerId);
//        HobbyAlarmEventDto hobbyAlarmEventDto = HobbyAlarmEventDto.builder().content(HOBBY_OWNER_ALARM.getContent()).title(HOBBY_OWNER_ALARM.getTitle())
//                .isRead(FALSE).member(alarmReceiver).participantId(member.getId()).
//                participantImage(member.getProfileImage()).hobbyId(hobbyId).build();
//        eventPublisher.publishEvent(hobbyAlarmEventDto);
//        /**
//         * 알람 보내기 끝
//         */

        return hobbyHistoryRepository.save(hobbyHistoryEntity).getId();
    }

    @Transactional
    public List<HobbyHistoryListDto> findWaitingMemberList(Long hobbyId) { // 해당 취미방 신청 대기자
        return hobbyHistoryRepository.userByStatus(hobbyId,ApproveStatus.READY);
    }

    @Transactional
    public List<HobbyHistoryListDto> findParticipantList(Long hobbyId) { // 해당 취미방 참여자
        return hobbyHistoryRepository.userByStatus(hobbyId,ApproveStatus.APPROVE);
    }

    @Transactional
    public ApproveStatus changeStatus(ChangeStatusRequestDto changeStatusRequestDto) { // 취미방 신청을 승인 또는 거절하기
        Long hobbyId = changeStatusRequestDto.getHobbyId();
        Long memberId = changeStatusRequestDto.getParticipantId();
        Long hobbyHistoryId = hobbyHistoryRepository.findByHobbyAndMemberIdDto(hobbyId, memberId)
                .getHobbyHistoryId();
//        /**
//         * 알람 보내기 시작
//         */
//        Hobby hobby = hobbyRepository.findById(changeStatusRequestDto.getHobbyId()).orElseThrow(
//                () -> new CustomException(HOBBY_NOT_FOUND)
//        );
//        Member alarmReceiver = new Member();
//        alarmReceiver.changeMemberId(changeStatusRequestDto.getParticipantId());
//        HobbyAlarmEventDto hobbyAlarmEventDto = HobbyAlarmEventDto.builder().member(alarmReceiver).isRead(FALSE).build();
//        if (changeStatusRequestDto.getApproveStatus().equals(ApproveStatus.APPROVE)) {
//            hobbyAlarmEventDto.setContent(HOBBY_PARTICIPANT_ALARM_APPROVE.getContent());
//            hobbyAlarmEventDto.setTitle(hobby.getTitle() + HOBBY_PARTICIPANT_ALARM_APPROVE.getTitle());
//        } else if (changeStatusRequestDto.getApproveStatus().equals(ApproveStatus.REJECT)) {
//            hobbyAlarmEventDto.setContent(HOBBY_PARTICIPANT_ALARM_REJECT.getContent());
//            hobbyAlarmEventDto.setTitle(hobby.getTitle() + HOBBY_PARTICIPANT_ALARM_REJECT.getTitle());
//        }
//        eventPublisher.publishEvent(hobbyAlarmEventDto);
//        /**
//         * 알람 보내기 끝
//         */
        return hobbyHistoryRepository.changeStatus(hobbyHistoryId, changeStatusRequestDto.getApproveStatus());
    }

    @Transactional
    public HobbyHistoryDto findByIdDto(Long hobbyHistoryId) {
        return hobbyHistoryRepository.findByIdDto(hobbyHistoryId);
    }

    @Transactional
    public HobbyHistoryDto createHobbyHistory(HobbyHistoryRequestDto hobbyHistoryRequestDto) {
        Hobby hobby = hobbyRepository.findById(hobbyHistoryRequestDto.getHobbyId()).orElseThrow(() -> new CustomException(HOBBY_NOT_FOUND));

        HobbyHistory hobbyHistoryEntity = HobbyHistory.builder()
                .memberId(hobbyHistoryRequestDto.getMemberId())
                .hobby(hobby)
                .isPromoter(true)
                .approveStatus(ApproveStatus.APPROVE)
                .build();

        hobbyHistoryRepository.save(hobbyHistoryEntity);


        return hobbyHistoryEntity.EntityToDto();
    }
    @Transactional
    public List<HobbyHistoryResponseDto> findHistoryList(Long memberId){
        return hobbyHistoryRepository.findHistoryList(memberId);
    }
}
