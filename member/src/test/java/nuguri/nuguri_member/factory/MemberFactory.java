package nuguri.nuguri_member.factory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import nuguri.nuguri_member.domain.Member;
import nuguri.nuguri_member.dto.member.MemberProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MemberFactory {

    public Key key;

    public MemberFactory(@Value("${token.secret}") String secretKey) {
        System.out.println("secretKey = " + secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(String memberId){
        return "Bearer " + Jwts.builder()
            .setSubject(memberId)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public static Member createUser(Long id){
        return MockMember.builder()
            .id(id)
            .build();
    }

    public static MemberProfileDto mockMemberProfileDto(){
        return MemberProfileDto.builder()
            .memberId(6l)
            .email("test@naver.com")
            .name("testName")
            .nickname("testNick")
            .description(null)
            .temperature(36.5)
            .profileImage(null)
            .baseAddress("인천광역시 서구 신현동")
            .localId(932l)
            .build();
    }
}
