package generator.readers.items;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import generator.utility.Tuple;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvFileReader implements ItemsReader{
    private static final Logger logger = LogManager.getLogger(CsvFileReader.class);

    public List<Tuple<String, Double>> getItems(String path){
        Iterable<CSVRecord> records;
        Reader in = getFileReader(path);
        if ( in != null ){
            try {
                records = CSVFormat
                        .newFormat(',')
                        .withHeader("name", "price")
                        .parse(in);
                logger.info("Successfully parsed items from csv file.");
                return returnItems(records);
            } catch (IOException e) {
                logger.error("Couldn't parse items!");
                throw new InputParseFileException();
            }
        } else{
            logger.error("Wrong item path");
            return new ArrayList<>();
        }
    }

    FileReader getFileReader(String path){
        try {
            return new FileReader(path);
        } catch (FileNotFoundException e) {
            logger.error("Couldn't find input file.");
            return null;
        }
    }

    protected List<Tuple<String, Double>> returnItems(Iterable<CSVRecord> records){
        records.iterator().next();
        ArrayList<Tuple<String, Double>> items = new ArrayList<>();

        for (CSVRecord record : records) {
            items.add( new Tuple<>( record.get("name"), Double.parseDouble( record.get("price"))));
        }
        return items;
    }

    public class InputItemFileNotFoundException extends RuntimeException{ }
    public class InputParseFileException extends RuntimeException{ }

}
