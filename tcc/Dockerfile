# --- ESTÁGIO 1: BUILD (Maven) ---
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copia arquivos do contexto de build (pasta tcc)
COPY pom.xml .
COPY src ./src

# Executa o build, OBRIGANDO O SKIP DOS TESTES:
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: EXECUÇÃO (Runtime) ---
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/tcc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]