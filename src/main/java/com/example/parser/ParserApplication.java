package com.example.parser;

import com.opencsv.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

import static com.example.parser.config.ParserConfig.PROJECT_FOLDER;
import static com.opencsv.ICSVWriter.*;

@SpringBootApplication
@Slf4j
public class ParserApplication {

    @Value("${source.name}")
    private String sourceName;

    public static void main(String[] args) {
        SpringApplication.run(ParserApplication.class, args);
    }

    @Bean
    public CommandLineRunner processCodes(Parser parser) {
        return (args) -> {
            Path path = Paths.get(PROJECT_FOLDER);
            if (Files.notExists(path)) {
                log.error(String.format("Папка %s не найдена", PROJECT_FOLDER));
                return;
            }
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();

            try (CSVReader reader = new CSVReaderBuilder(new FileReader(PROJECT_FOLDER + sourceName))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)           // skip the first line, header info
                    .build()) {
                String outputName = String.format(PROJECT_FOLDER + "/parser_output_%s.csv", UUID.randomUUID());
                try (CSVWriter writer = new CSVWriter(new FileWriter(outputName), ';',
                        NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)) {
                    String[] header = "Code;Instruction".split(";");
                    writer.writeNext(header);

                    String[] lineInArray;
                    while ((lineInArray = reader.readNext()) != null) {
                        String found = parser.itemHasInstruction(lineInArray[0])
                                ? "Found"
                                : "Not_Found";
                        lineInArray[1] = found;
                        writer.writeNext(lineInArray);
                        log.info(Arrays.toString(lineInArray));
                    }
                }
                log.info("Успех. Обработка csv была успешна, вы можете остановить процесс.");
            }
        };
    }

}
