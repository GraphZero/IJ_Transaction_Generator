package generator.generators.rest;

import generator.commands.FileType;
import generator.commands.GenerateTransactionResponseCommand;
import generator.generators.Transaction;
import generator.generators.TransactionGenerator;
import generator.readers.items.HttpCsvFileReader;
import generator.utility.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseTransactionGenerator {
    private final HttpCsvFileReader httpCsvFileReader;
    private TransactionGenerator transactionGenerator;

    @Autowired
    public ResponseTransactionGenerator(HttpCsvFileReader httpCsvFileReader) {
        this.httpCsvFileReader = httpCsvFileReader;
        this.transactionGenerator = new TransactionGenerator();
    }

    private List<Tuple<String, Double>> getItemsThroughHttp(FileType fileType){
        switch (fileType){
            case YAML:
                return httpCsvFileReader.getJsonItems("");

            case XML:
                return httpCsvFileReader.getXmlItems("");

            case JSON:
                return httpCsvFileReader.getJsonItems("");

            default:
                return  httpCsvFileReader.getJsonItems("");
        }
    }

    public ArrayList<? extends Transaction> generateTransactions(GenerateTransactionResponseCommand command){
        switch (command.getFileType()){
            case YAML:
                return transactionGenerator.generateYaml(getItemsThroughHttp(command.getFileType()), command.toFileCommand());

            case XML:
                return transactionGenerator.generateXml(getItemsThroughHttp(command.getFileType()), command.toFileCommand());

            case JSON:
                return transactionGenerator.generateJsons(getItemsThroughHttp(command.getFileType()), command.toFileCommand());

            default:
                return transactionGenerator.generateJsons(getItemsThroughHttp(command.getFileType()), command.toFileCommand());
        }
    }

}
