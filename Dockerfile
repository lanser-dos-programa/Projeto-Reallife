# --- ESTÁGIO 1: BUILD (Maven) ---
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e o código-fonte (src) da raiz do contexto (sua pasta tcc) para /app
COPY pom.xml .
COPY src ./src

# Executa o build do Maven. Ele criará o JAR executável em /app/target/
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: EXECUÇÃO (Runtime) ---
# Usamos apenas o JRE (ambiente de execução Java) para uma imagem final menor e mais segura
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copia o JAR executável (com o nome exato do pom.xml) do estágio de build para o estágio de execução
COPY --from=build /app/target/tcc-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]