package system.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XmlPersister {
    private static XmlPersister theInstance;
    private final String path;

    public static XmlPersister instance() {
        if (theInstance == null) {
            theInstance = new XmlPersister("data.xml");
        }
        return theInstance;
    }

    private XmlPersister(String p) {
        path = p;
    }

    public Data load() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        try (FileInputStream is = new FileInputStream(path)) {
            return (Data) unmarshaller.unmarshal(is);
        } catch (IOException e) {
            throw new Exception("Error reading XML file: " + e.getMessage(), e);
        }
    }

    public void store(Data d) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        try (FileOutputStream os = new FileOutputStream(path)) {
            marshaller.marshal(d, os);
            os.flush();
        } catch (IOException e) {
            throw new Exception("Error writing to XML file: " + e.getMessage(), e);
        }
    }
}
