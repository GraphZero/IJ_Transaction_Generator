package generator.writers.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import generator.writers.IFileWriter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class XmlFileWriter implements IFileWriter<XmlTransaction> {
    private static final Logger logger = LogManager.getLogger(XmlFileWriter.class);
    private final Marshaller jaxbMarshaller;

    public XmlFileWriter(Marshaller jaxbMarshaller) {
        this.jaxbMarshaller = jaxbMarshaller;
    }

    @Override
    public void writeValue(String filePath, ArrayList<XmlTransaction> transactionsToSave) {
        try {
            if ( !Files.exists(Paths.get(filePath))){
                Files.createDirectories(Paths.get(filePath));
            }
            File file = new File(filePath + "/transactionsXml.xml");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            transactionsToSave.forEach( transactionsToSave1 -> {
                try {
                    jaxbMarshaller.marshal(transactionsToSave1, file);
                } catch (JAXBException e) {
                    e.printStackTrace();
                    logger.error("JAXB went nuts! " + e.getMessage());
                }
            });
            logger.info("Successfully parsed items into XML file.");
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            logger.error("JAXB went nuts! " + e.getMessage());
        }
    }
}

