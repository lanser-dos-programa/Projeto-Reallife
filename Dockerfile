FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
# O WORKDIR é /app

# 1. Copia o pom.xml e a pasta src da raiz do contexto (tcc/) para /app
COPY pom.xml .
COPY src ./src

# 2. Executa o build. O JAR será gerado em /app/target/
RUN mvn clean package -DskipTests

# --- Estágio Final (Runtime) ---
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 3. Copia o JAR do local onde o Maven o gerou
# Usamos o nome específico do artefato para evitar o erro de 'ClassNotFoundException'
COPY --from=build /app/target/tcc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]