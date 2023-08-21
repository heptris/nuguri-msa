package nuguri.nuguri_member.client;

import nuguri.nuguri_member.dto.deal.DealListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "deal-service")
public interface DealServiceClient {

    @GetMapping("/app/deal/{memberId}/list/true")
    List<DealListDto> findDealByMemberIdAndIsDealTrue(@PathVariable(value = "memberId") Long memberId);

    @GetMapping("/app/deal/{memberId}/list/false")
    List<DealListDto> findDealByMemberIdAndIsDealFalse(@PathVariable(value = "memberId") Long memberId);

    @GetMapping("/app/deal/{memberId}/list/favorite")
    List<DealListDto> findDealByMemberIdAndIsFavorite(@PathVariable(value = "memberId") Long memberId);

    @GetMapping("/app/deal-history/{memberId}/list/buyer")
    List<DealListDto> findDealByMemberIdAndBuyer(@PathVariable(value = "memberId") Long memberId);
}
