FROM openjdk:11-jre-slim
VOLUME /tmp
COPY build/libs/nuguri_member-0.0.1-SNAPSHOT.jar MemberService.jar
ENTRYPOINT ["java", "-jar", "MemberService.jar"]