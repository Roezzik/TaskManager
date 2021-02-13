package shared;


import shared.model.Journal;

import javax.xml.bind.*;
import java.io.File;

public class XmlMarshaller {

    public void XmlWriter(Journal journal) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Journal.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(journal, new File("C:/Users/User/IdeaProjects/task-manager/task.xml"));
    }

    public Journal XmlReader() throws JAXBException {
        JAXBContext j = JAXBContext.newInstance(Journal.class);
        Unmarshaller unmarshaller = j.createUnmarshaller();
        Journal journal = (Journal)unmarshaller.unmarshal(new File("C:/Users/User/IdeaProjects/task-manager/task.xml"));
        System.out.println(journal.toString());
        return journal;
    }

}
