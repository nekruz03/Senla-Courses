INSERT_FILE="/Users/nekruz/Downloads/Senla-Courses-Task-11/Task-11/Task-2/src/main/java/util/DML/insert.sql"
if [ -f "$INSERT_FILE" ]; then
    echo "Вставка данных"
    psql -h localhost -U postgres -d Dz-11_3 -f "$INSERT_FILE"
    echo "Вставка данных завершена!"
else
    echo "Ошибка! Файл insert.sql не найден!!"
    exit 1
fi