FROM gradle:7-jdk17-alpine
WORKDIR /home/gradle/code
COPY . .
EXPOSE 8080
CMD ["gradle", "--stacktrace", "bootRun"]