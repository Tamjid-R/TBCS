import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class CustomerXMLDAO {
    private String filePath;
    private DocumentBuilderFactory documentBuilderFactory;
    private TransformerFactory transformerFactory;
    
    public CustomerXMLDAO(String filePath) {
        this.filePath = filePath;
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.transformerFactory = TransformerFactory.newInstance();
        initializeFile();
    }
    
    private void initializeFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                
                Element rootElement = document.createElement("customers");
                document.appendChild(rootElement);
                
                saveDocument(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveCustomer(Customer customer) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            
            // Remove existing customer with same ID
            NodeList customerList = rootElement.getElementsByTagName("customer");
            for (int i = 0; i < customerList.getLength(); i++) {
                Element element = (Element) customerList.item(i);
                if (element.getAttribute("id").equals(customer.getCustomerId())) {
                    rootElement.removeChild(element);
                    break;
                }
            }
            
            // Create new customer element
            Element customerElement = document.createElement("customer");
            customerElement.setAttribute("id", customer.getCustomerId());
            
            Element nameElement = document.createElement("name");
            nameElement.setTextContent(customer.getName());
            customerElement.appendChild(nameElement);
            
            Element emailElement = document.createElement("email");
            emailElement.setTextContent(customer.getEmail());
            customerElement.appendChild(emailElement);
            
            Element phoneElement = document.createElement("phone");
            phoneElement.setTextContent(customer.getPhone());
            customerElement.appendChild(phoneElement);
            
            Element registeredElement = document.createElement("registered");
            registeredElement.setTextContent(String.valueOf(customer.isRegistered()));
            customerElement.appendChild(registeredElement);
            
            Element orderCountElement = document.createElement("monthlyOrderCount");
            orderCountElement.setTextContent(String.valueOf(customer.getMonthlyOrderCount()));
            customerElement.appendChild(orderCountElement);
            
            rootElement.appendChild(customerElement);
            
            saveDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Customer getCustomer(String customerId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList customerList = document.getElementsByTagName("customer");
            for (int i = 0; i < customerList.getLength(); i++) {
                Element element = (Element) customerList.item(i);
                if (element.getAttribute("id").equals(customerId)) {
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String email = element.getElementsByTagName("email").item(0).getTextContent();
                    String phone = element.getElementsByTagName("phone").item(0).getTextContent();
                    boolean registered = Boolean.parseBoolean(
                        element.getElementsByTagName("registered").item(0).getTextContent());
                    
                    Customer customer = new Customer(customerId, name, email, phone, registered);
                    
                    int orderCount = Integer.parseInt(
                        element.getElementsByTagName("monthlyOrderCount").item(0).getTextContent());
                    for (int j = 0; j < orderCount; j++) {
                        customer.incrementOrderCount();
                    }
                    
                    return customer;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList customerList = document.getElementsByTagName("customer");
            for (int i = 0; i < customerList.getLength(); i++) {
                Element element = (Element) customerList.item(i);
                String customerId = element.getAttribute("id");
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String email = element.getElementsByTagName("email").item(0).getTextContent();
                String phone = element.getElementsByTagName("phone").item(0).getTextContent();
                boolean registered = Boolean.parseBoolean(
                    element.getElementsByTagName("registered").item(0).getTextContent());
                
                Customer customer = new Customer(customerId, name, email, phone, registered);
                
                int orderCount = Integer.parseInt(
                    element.getElementsByTagName("monthlyOrderCount").item(0).getTextContent());
                for (int j = 0; j < orderCount; j++) {
                    customer.incrementOrderCount();
                }
                
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public boolean deleteCustomer(String customerId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            NodeList customerList = rootElement.getElementsByTagName("customer");
            
            for (int i = 0; i < customerList.getLength(); i++) {
                Element element = (Element) customerList.item(i);
                if (element.getAttribute("id").equals(customerId)) {
                    rootElement.removeChild(element);
                    saveDocument(document);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void saveDocument(Document document) {
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}