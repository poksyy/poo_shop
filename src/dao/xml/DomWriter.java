package dao.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Product;

public class DomWriter {

	private Document document;

	// Constructor: Initializes the XML document
	public DomWriter() {
		initializeDocument();
	}

	/**
	 * Initializes the Document object using DocumentBuilderFactory.
	 */
	private void initializeDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document.");
		}
	}

	/**
	 * Generates the XML document from a list of products.
	 * 
	 * @param products List of products to include in the XML.
	 */
	public void generateDocument(ArrayList<Product> products) {
		Element rootElement = document.createElement("products");
		rootElement.setAttribute("total", Integer.toString(products.size()));
		document.appendChild(rootElement);

		for (Product product : products) {

			// Product element
			Element productElement = document.createElement("product");
			productElement.setAttribute("id", String.valueOf(product.getId()));

			// Name element with currency attribute
			Element nameElement = document.createElement("name");
			nameElement.setTextContent(product.getName());
			productElement.appendChild(nameElement);

			// Price element
			Element priceElement = document.createElement("price");
			priceElement.setAttribute("currency", "€");
			priceElement.setTextContent(String.valueOf(product.getWholesalerPrice().getValue()));
			productElement.appendChild(priceElement);

			// Stock element
			Element stockElement = document.createElement("stock");
			stockElement.setTextContent(Integer.toString(product.getStock()));
			productElement.appendChild(stockElement);

			rootElement.appendChild(productElement);
		}

		generateXml();
	}

	/**
	 * Generates the XML file from the document and saves it in the "xml" folder.
	 */
	private void generateXml() {
		try {

			// Generate file name with current date
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDateTime = now.format(formatter);

			// Create transformer and save the document to the file
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();

			Source source = new DOMSource(document);
			File file = new File("xml/inventory_" + formattedDateTime + ".xml");

			try (FileWriter fw = new FileWriter(file); PrintWriter pw = new PrintWriter(fw)) {
				Result result = new StreamResult(pw);
				transformer.transform(source, result);
			}

		} catch (IOException e) {
			System.out.println("Error when creating writer file");
		} catch (TransformerException e) {
			System.out.println("Error transforming the document");
		}
	}
}
