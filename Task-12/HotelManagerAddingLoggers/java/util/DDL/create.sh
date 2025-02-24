CREATE_FILE="/Users/nekruz/Downloads/Senla-Courses-Task-11/Task-11/Task-2/src/main/java/util/DDL/create.sh"

if [ -f "$CREATE_FILE" ]; then
    echo "Создание БД"
    psql -h localhost -U postgres -d Dz-11_3 -f "$CREATE_FILE"
    echo "Создание БД завершено!"
else
    echo "Файл create.sql не найден!!"
    exit 1
fi