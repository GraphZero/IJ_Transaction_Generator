package generator.writers;

import generator.generators.Transaction;

import java.util.ArrayList;

public interface IFileWriter<T extends Transaction> {
    void writeValue(String filePath, ArrayList<T> transactionsToSave, int id);
}
