package nuguri.nuguri_member.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @GetMapping("/auth/security-util")
    Long getMemberIdBySecurityUtil();

}
