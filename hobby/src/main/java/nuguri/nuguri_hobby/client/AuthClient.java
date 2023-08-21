package nuguri.nuguri_hobby.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="AUTH-SERVICE",path="/auth")
public interface AuthClient {
    @GetMapping("/security-util")
    ResponseEntity<Long> getMemberIdBySecurityUtil();

}
