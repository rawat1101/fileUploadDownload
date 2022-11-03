package com.example.fileUploadDownload;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;
import csv.ProductSellerCsv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FileUploadDownloadApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(FileUploadDownloadApplication.class, args);
//        writeCSV();
        writeCSV2();
        List<ProductSellerCsv> productClientCsvs = read2("/home/mahendra/workspace/IntelliJ/test000.csv");
        System.out.println(productClientCsvs);
    }

    public static void writeCSV() {
        String[] columns = {"name", "email", "countryCode", "no"};
        List<User> users = new ArrayList<>();
        users.add(new User("Adewale Joseph", "adewale.joseph@example.com", "UK", Arrays.asList("1", "2", "3")));
        users.add(new User("Adam Shaw", "adam.shaw@example.com", "DE", Arrays.asList("5", "6", "7", "8")));


        try {

            Writer w = new FileWriter("/home/mahendra/workspace/IntelliJ/test123.csv");
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

    public static void ReadCSV() {
        String[] columns = {"name", "email", "countryCode", "no"};
        List<User> users = new ArrayList<>();
        users.add(new User("Adewale Joseph", "adewale.joseph@example.com", "UK", Arrays.asList("1", "2", "3")));
        users.add(new User("Adam Shaw", "adam.shaw@example.com", "DE", Arrays.asList("5", "6", "7")));


        try {
            Writer w = new FileWriter("/home/mahendra/workspace/IntelliJ/test123.csv");
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

    private static List<User> read1(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(Paths.get(path)))) {
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(User.class);
            reader.readLine();
            CsvToBeanBuilder<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withMappingStrategy(mappingStrategy)
                    .withIgnoreLeadingWhiteSpace(true);
            return csvToBean.build().parse();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public static void writeCSV2() {
        List<ProductSellerCsv> users = new ArrayList<>();
        users.add(new ProductSellerCsv("Adewale Joseph", Arrays.asList("1", "2", "3")));
        users.add(new ProductSellerCsv("Adam Shaw", Arrays.asList("5", "6", "7", "8")));
        try {
            Writer w = new FileWriter("/home/mahendra/workspace/IntelliJ/test000.csv");
            CSVWriter writer = new CSVWriter(w, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            StatefulBeanToCsv<ProductSellerCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductSellerCsv>(writer)
                    .build();
            beanToCsv.write(users);
            writer.close();
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static List<ProductSellerCsv> read2(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(Paths.get(path)))) {
            HeaderColumnNameMappingStrategy<ProductSellerCsv> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductSellerCsv.class);

            CsvToBeanBuilder<ProductSellerCsv> csvToBean = new CsvToBeanBuilder<ProductSellerCsv>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true);
            return csvToBean.build().parse();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
