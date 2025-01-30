package dao.jaxb;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import model.ProductList;

public class JaxbMarshaller {

	public boolean init(ProductList productList) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDateTime = now.format(formatter);

			marshaller.marshal(productList, new File("jaxb/inventory_" + formattedDateTime + ".xml"));
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
	}

}
