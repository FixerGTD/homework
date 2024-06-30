FROM amazoncorretto:18.0.2-alpine3.16
RUN apk update && apk add --no-cache libc6-compat && ln -s /lib/libc.musl-x86_64.so.1 /lib/ld-linux-x86-64.so.2
ENV LD_PRELOAD=/lib/libgcompat.so.0
RUN mkdir /homework
ADD target/homework-0.0.1-SNAPSHOT.jar /homework/homework-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/homework/homework-0.0.1-SNAPSHOT.jar"]