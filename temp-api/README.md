# Climator

<br />
<div align="center">
  <a href="https://github.com/amirul-zafrin/climator">
    <img src="https://github.com/amirul-zafrin/climator/blob/main/public/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Climator</h3>

  <p align="center">
    Web application to analyse and predict temperature for business. 
    <br />
    <a href="https://github.com/amirul-zafrin/climator"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/amirul-zafrin/climator">View Demo</a>
    ·
    <a href="https://github.com/amirul-zafrin/climator/issues">Report Bug</a>
    ·
    <a href="https://github.com/amirul-zafrin/climator/issues">Request Feature</a>
  </p>
</div>

This project uses Quarkus (the Supersonic Subatomic Java Framework), Postgresql and MongoDB.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Quarkus

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

> **_NOTE:_** By default, Quarkus run at http://localhost:8080. For this project, I use http://localhost:8081.

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8081/q/dev/.

### Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Databases

### Postgresql

Postgres database store user information including the file objectID that can be use to retrieve data from mongoDB.

![Postgresql](https://github.com/amirul-zafrin/climator/blob/main/public/postgres_er.png)

### MongoDB

MongoDB limits only 16MB of data, therefore, I use mongoDB [GridFS](https://www.mongodb.com/docs/manual/core/gridfs/#:~:text=GridFS%20is%20a%20specification%20for,chunk%20as%20a%20separate%20document) to chunk the files.
