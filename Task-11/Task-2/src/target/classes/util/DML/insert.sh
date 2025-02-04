INSERT_FILE="/Users/nekruz/Downloads/Senla-Courses-main 4/Task-8/src/main/java/util/DML/insert.sql"

if [ -f "$INSERT_FILE" ]; then
    echo "Вставка данных"
    psql -h localhost -U postgres -d Dz-11_2 -f "$INSERT_FILE"
    echo "Вставка данных завершена!"
else
    echo "Ошибка! Файл insert.sql не найден!!"
    exit 1
fi