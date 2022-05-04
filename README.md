# springboot-example

My springboot basic rest API example app. Follows this tutorial https://spring.io/guides/gs/rest-service. Uses Java 17.

View the Greeting API at [http://localhost:8080/greeting](http://localhost:8080/greeting)


To build and run:

Probably best to use an IDE like VSCode with the springboot extensions, Eclipse, or IntelliJ.

Otherwise, you can use gradle to build the jar and run it like so:

```bash
./gradlew build
java -jar build/libs/restservice-0.0.1-SNAPSHOT.jar
```

Alternatively you can install docker and do `docker-compose up` which may be easier but also has more overhead.