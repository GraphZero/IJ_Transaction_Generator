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
public class ResponseTransactionManager {
    private final HttpCsvFileReader httpCsvFileReader;
    private TransactionGenerator transactionGenerator;

    @Autowired
    public ResponseTransactionManager(HttpCsvFileReader httpCsvFileReader) {
        this.httpCsvFileReader = httpCsvFileReader;
        this.transactionGenerator = new TransactionGenerator();
    }

    private List<Tuple<String, Double>> getItemsThroughHttp(){
        return httpCsvFileReader.getJsonItems("");
    }

    public ArrayList<? extends Transaction> generateTransactions(GenerateTransactionResponseCommand command){
        switch (command.getFileType()){
            case YAML:
                return transactionGenerator.generateYaml(getItemsThroughHttp(), command.toFileCommand());

            case XML:
                return transactionGenerator.generateXml(getItemsThroughHttp(), command.toFileCommand());

            case JSON:
                return transactionGenerator.generateJsons(getItemsThroughHttp(), command.toFileCommand());

            default:
                return transactionGenerator.generateJsons(getItemsThroughHttp(), command.toFileCommand());
        }
    }

}
