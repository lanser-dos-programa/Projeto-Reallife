# --- ESTÁGIO 1: BUILD (Maven Robusto) ---
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copia o pom.xml e o código-fonte (src)
# Mantemos o COPY simples e correto, pois o problema de pathing deve estar resolvido
COPY pom.xml .
COPY src ./src

# 2. Executa o build com ajustes de memória e log detalhado:
# -e: Exibe logs de erro detalhados.
# -X: Exibe logs de debug completos (muito importante se falhar novamente).
# MAVEN_OPTS: Aumenta a memória Heap do Maven (solução comum para builds grandes).
ENV MAVEN_OPTS="-Xmx1024m"
RUN mvn clean package -DskipTests -e -X

# --- ESTÁGIO 2: EXECUÇÃO (Runtime) ---
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 3. Copia o JAR executável (com o nome exato)
COPY --from=build /app/target/tcc-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]