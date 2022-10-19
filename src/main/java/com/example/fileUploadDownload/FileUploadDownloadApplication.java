package com.example.fileUploadDownload;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FileUploadDownloadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadDownloadApplication.class, args);
        writeCSV();
    }

    public static void writeCSV() {
        String[] columns = {"name", "email", "countryCode", "no"};
        List<User> users = new ArrayList<>();
        users.add(new User("Adewale Joseph", "adewale.joseph@example.com", "UK", Arrays.asList("1", "2", "3")));
        users.add(new User("Adam Shaw", "adam.shaw@example.com", "DE", Arrays.asList("5", "6", "7")));


        try {

            Writer w = new FileWriter("/home/mahendra/workspace/IntelliJ/test.csv");
            CSVWriter writer = new CSVWriter(w, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(User.class);
            mappingStrategy.setColumnMapping(columns);
            writer.writeNext(columns);
//

            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            beanToCsv.write(users);
            writer.close();
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
