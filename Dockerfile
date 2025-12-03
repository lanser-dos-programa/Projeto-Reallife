# ---------------------------------------------
# ESTÁGIO 1: BUILD
# Usa Java 17 para garantir compatibilidade total
# ---------------------------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copia o pom.xml e o código-fonte
# Assume que o Root Directory do Render é 'tcc'
COPY pom.xml .
COPY src ./src

# 2. Executa o build (limpa o cache e ignora os testes)
# O -DskipTests é vital para ignorar a falha de DB que vimos nos logs de teste.
RUN mvn clean package -DskipTests

# ---------------------------------------------
# ESTÁGIO 2: EXECUÇÃO (Runtime)
# Usa apenas o JRE para menor tamanho
# ---------------------------------------------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 3. Nome exato do JAR, baseado no pom.xml
ENV JAR_NAME tcc-0.0.1-SNAPSHOT.jar

# 4. Copia o JAR criado no estágio de build para o estágio de execução
# Se este caminho estiver incorreto, a Classe não será encontrada.
COPY --from=build /app/target/${JAR_NAME} app.jar

# 5. Comando de execução
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]