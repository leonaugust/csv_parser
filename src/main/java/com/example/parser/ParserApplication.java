package com.example.parser;

import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

import static com.example.parser.config.ParserConfig.PROJECT_FOLDER;
import static com.opencsv.ICSVWriter.*;

@SpringBootApplication
public class ParserApplication {
    private final Logger log = LoggerFactory.getLogger(ParserApplication.class);

    public static void main(String[] args) {
//		for (String arg : args) {
//			System.out.println(arg);
//		}
        SpringApplication.run(ParserApplication.class, args);
    }

    @Bean
    public CommandLineRunner processCodes(Parser parser) {
        return (args) -> {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();

            String outputName = String.format(PROJECT_FOLDER + "/parser_output_%s.csv", UUID.randomUUID());
            try (CSVWriter writer = new CSVWriter(new FileWriter(outputName), ';',
                    NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)) {
                try (CSVReader reader = new CSVReaderBuilder(new FileReader(
                        PROJECT_FOLDER + "/parser_instruction.csv"))
                        .withCSVParser(csvParser)
                        .withSkipLines(1)           // skip the first line, header info
                        .build()) {

                    String[] header = "Code;Instruction".split(";");
                    writer.writeNext(header);

                    String[] lineInArray;
                    while ((lineInArray = reader.readNext()) != null) {
                        String isFound = parser.itemHasInstruction(lineInArray[0])
                                ? "Found"
                                : "Not_Found";
                        lineInArray[1] = isFound;
                        writer.writeNext(lineInArray);
                    }
                }
                log.info("Success. Csv handling was successful, you can stop the process.");
            }
        };
    }

}
