package nuguri.nuguri_member.client;

import nuguri.nuguri_member.dto.baseaddress.BaseAddressIdRequestDto;
import nuguri.nuguri_member.dto.baseaddress.BaseAddressSidoGugunDongDto;
import nuguri.nuguri_member.dto.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "basic-service")
public interface BaseAddressServiceClient {

    @PostMapping("/app/base-address/local/search")
    Long findBySidoGugunDong(@RequestBody BaseAddressSidoGugunDongDto requestDto);

    @PostMapping("/app/base-address/local/search/local-id")
    BaseAddressSidoGugunDongDto findByLocalId(@RequestBody BaseAddressIdRequestDto requestDto);

}
