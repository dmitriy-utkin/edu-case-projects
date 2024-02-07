FROM openjdk:17-oracle

WORKDIR /app

COPY ../build/libs/currency-telegram-bot-0.0.1-SNAPSHOT.jar app.jar
COPY ../src/main/resources/application.yml application.yml


ENV BOT_NAME=default
ENV BOT_TOKEN=default
ENV NOTIFICATION_DELAY=10
ENV NOTIFICATION_DELAY_UNIT=MINUTES
ENV BINANCE_API_URL="https://api.binance.com/api/v3/avgPrice?symbol=BTCUSDT"
ENV POSTGRES_URL="jdbc:postgresql://localhost:5432/currency_db"
ENV POSTGRES_USERNAME=postgres
ENV POSTGRES_PASSWORD=postgres



CMD ["java", "-Dspring.config.location=file:/app/application.yml", "-jar", "app.jar"]