FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# 💡 Assumindo que o Render copia a pasta 'tcc' inteira:
# 1. Copie todo o conteúdo para a raiz do WORKDIR
COPY tcc ./tcc

# 2. Mude o diretório de trabalho para onde o pom.xml realmente está
WORKDIR /app/tcc

# 3. Execute o Maven a partir do diretório do pom.xml
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# O JAR foi gerado dentro de /app/tcc/target
COPY --from=build /app/tcc/target/tcc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]