package nuguri.nuguri_member.factory;

import nuguri.nuguri_member.domain.Member;

public class MockMember {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email = "test@naver.com";
        private String name = "testName";
        private String password = "test1234!";
        private String nickname = "testNick";
        private String description = "test description";
        private Double temperature = 36.5;
        private Integer age = 27;
        private Character sex = 'm';
        private String profileImage = null;
        private Long localId = 1l;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Member build(){
            return new Member(
                id,
                email,
                name,
                password,
                nickname,
                description,
                temperature,
                age,
                sex,
                profileImage,
                localId
            );
        }

    }

}
