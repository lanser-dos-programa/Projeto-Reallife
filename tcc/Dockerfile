# ---------------------------------------------
# ESTÁGIO 1: BUILD
# ---------------------------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copia o pom.xml e o código-fonte
COPY pom.xml .
COPY src ./src

# 2. Executa o build (limpa o cache e IGNORA OS TESTES)
RUN mvn clean package -DskipTests

# ---------------------------------------------
# ESTÁGIO 2: EXECUÇÃO (Runtime)
# ---------------------------------------------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

ENV JAR_NAME tcc-0.0.1-SNAPSHOT.jar

# 3. Copia o JAR criado no estágio de build
COPY --from=build /app/target/${JAR_NAME} app.jar

# 4. Comando de execução
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]