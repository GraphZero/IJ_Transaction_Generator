package writers.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import writers.IFileWriter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
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
            File file = new File(filePath + ".xml");
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
        } catch (JAXBException e) {
            e.printStackTrace();
            logger.error("JAXB went nuts! " + e.getMessage());
        }
    }
}

