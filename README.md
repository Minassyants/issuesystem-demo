# Система управления обращениями и жалобами клиентов

## Описание
Бэкенд для системы работы с обращениями клиентов. Позволяет регистрировать обращения, подключать сотрудников для внутреннего обсуждения, выявлять виновников и формировать заключение. Предусмотрена печать завершённых обращений.

## Основной функционал
- Регистрация обращений оператором.
- Подключение сотрудников к обсуждению.
- Управление статусами обращений (ожидание, в работе, закрыто).
- Формирование заключений.
- Печать и архивирование обращений.
- Хранение прикреплённых файлов.
- Поддержка уведомлений и чатов.
- Поиск обращений с использованием Elasticsearch.
- **Планируется**: интеграция с почтой, 2ГИС, формой обратной связи и другими внешними сервисами.

## Технологии
- **Язык**: Java
- **База данных**: PostgreSQL
- **Поиск**: Elasticsearch
- **Хранение файлов**: MinIO
- **Фронтенд**: Flutter (в отдельном проекте)
- **Документация API**: OpenAPI (Swagger)
  
## Установка и запуск

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Minassyants/issuesystem.git
2. Упакуйте в JAR:
   ```bash
   mvn clean package -Pprod
3. Разверните в Docker:
   ```bash
   sh pullup.sh
