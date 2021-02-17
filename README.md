**Установка на Linux**.
1. В домашней папке HOME создайте папку parser_items и
поместите туда файл для обработки.
2. Запустите проект командой вида
   
        mvn spring-boot:run -Dspring-boot.run.arguments="--source.name=FILENAME"

        Пример:
        mvn spring-boot:run -Dspring-boot.run.arguments="--source.name=parser_source.csv"
   
3. Результат будет расположен в папке parser_items