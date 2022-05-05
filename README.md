# springboot-example

My basic rest API example app built using Java [Springboot](https://spring.io/projects/spring-boot). You can consume the api at [http://localhost:8080/api/](http://localhost:8080/api/). See [Consuming the API](#consuming-the-api) for more info.


#### To build and run:

You will need to install java 17 JDK, gradle, and [postgres](https://www.postgresql.org/). See [How to configure postgres](#configuring-postgres) for more info. Otherwise with docker installed you can do `docker-compose up api`.

Probably best to use an IDE like VSCode with the springboot extensions, Eclipse, or IntelliJ, but you can also use gradle:
```bash
./gradlew bootRun
```

Or, you may build the jar so you may run on any computer that has java 17. (write once, run anywhere*):
```bash
./gradlew build
java -jar build/libs/restservice-0.0.1-SNAPSHOT.jar
```

#### Running the tests

Ideally using a rich IDE, but also in the commandline using gradle:

```bash
./gradlew test
```

#### Consuming the API

To use these examples run the api then navigate to [http://localhost:8080/api/](http://localhost:8080/api/). Once there you can do GET operations by changing the URL, but to do any other HTTP method you may find these examples helpful. Open the developer console and paste them and hit enter to execute.

To create a rental:
```javascript
await fetch('http://127.0.0.1:8080/api/rentals/',
    {
        'method': 'POST',
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({owner: 'dan', description: 'A really nice house with 2 bedrooms and 1 bathroom'})
}).then(response => response.json());
```

To delete a rental:
```javascript
let rentalToDelete = 1;
await fetch(`http://127.0.0.1:8080/api/rentals/${rentalToDelte}`,
    {
        'method': 'DELETE',
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
});
```

To filter rentals by description:
```javascript
await fetch('http://127.0.0.1:8080/api/rentals?description=3+bed',
    {
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
}).then(response => response.json());
```

#### Configuring Postgres

*WARNING: Do not use these credentials in production. Or maybe don't run this app in production at all because I have no idea if it's secure.*

Also, I don't think this will be easy to set up on Windows, but you can use [WSL](https://docs.microsoft.com/en-us/windows/wsl) with ubuntu installed.

With [postgres](https://www.postgresql.org/) installed and running:
```bash
sudo -u postgres psql -f setup-postgres.psql # must run psql as the postgres user
```

Now, make sure you have [these environment variables set](./.example-env) else the application won't know how to connect:

NOTE: There is probably a better way to do this, see https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config

```bash
export $(cat .example-env | xargs)
```

Now you can run the api server:

```bash
./gradlew bootRun
```