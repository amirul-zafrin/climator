# Climator

<br />
<div align="center">
  <a href="https://img.freepik.com/free-vector/two-thermometers-hot-cold-weather_1308-36010.jpg?t=st=1649314846~exp=1649315446~hmac=f41c26947b7a3fc87f83ff2687c4373bfefc9347d100711b4c8ab7128573bdbc&w=740">
    <img src="https://img.freepik.com/free-vector/two-thermometers-hot-cold-weather_1308-36010.jpg?t=st=1649314846~exp=1649315446~hmac=f41c26947b7a3fc87f83ff2687c4373bfefc9347d100711b4c8ab7128573bdbc&w=740" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Climator</h3>

  <p align="center">
    Temperature Dashboard and Prediction
  </p>
</div>

<br />

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Quarkus

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

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
