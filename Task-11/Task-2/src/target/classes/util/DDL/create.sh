CREATE_FILE="/Users/nekruz/Downloads/Senla-Courses-main 4/Task-8/src/main/java/util/DDL/create.sql"

if [ -f "$CREATE_FILE" ]; then
    echo "Создание БД"
    psql -h localhost -U postgres -d Dz-11_2 -f "$CREATE_FILE"
    echo "Создание БД завершено!"
else
    echo "Файл create.sql не найден!!"
    exit 1
fi