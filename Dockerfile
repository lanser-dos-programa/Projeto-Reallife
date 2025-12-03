# Estágio de Build (Para criar o JAR)
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# ** CORREÇÃO ESSENCIAL: Copiando do diretório pai (../) **
# O contexto de build duplicado exige que subamos um nível para achar o pom.xml e src.
# Presume que o pom.xml e src estão em um diretório acima do contexto de build atual.
COPY ../pom.xml .
COPY ../src ./src

# Executa o build do Maven. O JAR será gerado em /app/target/
RUN mvn clean package -DskipTests

# --- Estágio Final (Runtime) ---
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copia o JAR do local correto (/app/target/) com o nome específico.
COPY --from=build /app/target/tcc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]