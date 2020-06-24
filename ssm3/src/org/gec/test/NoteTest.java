package org.gec.test;

import org.gec.pojo.Note;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class NoteTest {
    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Note.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputStream is = NoteTest.class.getResourceAsStream("/note.xml");
        Note note = (Note) unmarshaller.unmarshal(is);
        System.out.println("note:" + note);
    }
}
