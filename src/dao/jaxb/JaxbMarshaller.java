package dao.jaxb;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.ProductList;

public class JaxbMarshaller {
    
    public void init(ProductList productList) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = now.format(formatter);

            marshaller.marshal(productList, new File("jaxb/inventory_" + formattedDateTime + ".xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    
}
