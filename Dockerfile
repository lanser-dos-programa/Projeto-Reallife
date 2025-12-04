# ---------------------------------------------
# ESTÁGIO 1: BUILD (Ignorando Testes)
# ---------------------------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# COMANDO CRÍTICO: Executa o build (install) e IGNORA OS TESTES (-DskipTests)
# Isso é essencial para ambientes de CI/CD onde variáveis de ambiente do DB não estão prontas no momento do build.
RUN mvn clean install -DskipTests

# ---------------------------------------------
# ESTÁGIO 2: EXECUÇÃO (Runtime)
# ---------------------------------------------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Nome exato do JAR (Baseado no seu pom.xml: tcc-0.0.1-SNAPSHOT.jar)
ENV JAR_NAME tcc-0.0.1-SNAPSHOT.jar

# 3. Copia o JAR criado
COPY --from=build /app/target/${JAR_NAME} app.jar

# 4. Comando de execução
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]