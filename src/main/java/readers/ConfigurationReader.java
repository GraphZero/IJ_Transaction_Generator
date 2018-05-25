package readers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private final static int numberOfConfigurationOptions = 16;

    public static String[] getCommands() throws IOException {

        String[] command = new String[numberOfConfigurationOptions];

        //to load application's properties, we use this class
        Properties mainProperties = new Properties();

        FileInputStream file;

        //the base folder is ./, the root of the main.properties file
        String path = "./storage/generator.properties";

        //load the file handle for main.properties
        file = new FileInputStream(path);

        //load all the properties from this file
        mainProperties.load(file);

        //we have loaded the properties, so close the file handle
        file.close();

        //retrieve the property we are intrested, the app.version
        command[0] = "-itemsFile";
        command[1] = mainProperties.getProperty("itemsFile");

        command[2] = "-customerIds";
        command[3] = mainProperties.getProperty("customerIds");

        command[4] = "-dateRange";
        command[5] = mainProperties.getProperty("dateRange");

        command[6] = "-itemsCount";
        command[7] = mainProperties.getProperty("itemsCount");

        command[8] = "-itemsQuantity";
        command[9] = mainProperties.getProperty("itemsQuantity");

        command[10] = "-outDir";
        command[11] = mainProperties.getProperty("outDir");

        command[12] = "-eventsCount";
        command[13] = mainProperties.getProperty("eventsCount");

        command[14] = "-format";
        command[15] = mainProperties.getProperty("format");

        return command;
    }

}
