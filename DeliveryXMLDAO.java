import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class DeliveryXMLDAO {
    private String filePath;
    private DocumentBuilderFactory documentBuilderFactory;
    private TransformerFactory transformerFactory;
    
    public DeliveryXMLDAO(String filePath) {
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
                
                Element rootElement = document.createElement("deliveries");
                document.appendChild(rootElement);
                
                saveDocument(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveDelivery(Delivery delivery) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            
            // Remove existing delivery with same ID
            NodeList deliveryList = rootElement.getElementsByTagName("delivery");
            for (int i = 0; i < deliveryList.getLength(); i++) {
                Element element = (Element) deliveryList.item(i);
                if (element.getAttribute("id").equals(delivery.getDeliveryId())) {
                    rootElement.removeChild(element);
                    break;
                }
            }
            
            // Create new delivery element
            Element deliveryElement = document.createElement("delivery");
            deliveryElement.setAttribute("id", delivery.getDeliveryId());
            
            Element orderIdElement = document.createElement("orderId");
            orderIdElement.setTextContent(delivery.getOrder().getOrderId());
            deliveryElement.appendChild(orderIdElement);
            
            Element driverIdElement = document.createElement("driverId");
            driverIdElement.setTextContent(delivery.getDriver().getDriverId());
            deliveryElement.appendChild(driverIdElement);
            
            Element driverNameElement = document.createElement("driverName");
            driverNameElement.setTextContent(delivery.getDriver().getName());
            deliveryElement.appendChild(driverNameElement);
            
            Element drivingLicenseElement = document.createElement("drivingLicense");
            drivingLicenseElement.setTextContent(delivery.getDriver().getDrivingLicense());
            deliveryElement.appendChild(drivingLicenseElement);
            
            Element vehicleIdElement = document.createElement("vehicleId");
            vehicleIdElement.setTextContent(delivery.getVehicle().getVehicleId());
            deliveryElement.appendChild(vehicleIdElement);
            
            Element vehicleTypeElement = document.createElement("vehicleType");
            vehicleTypeElement.setTextContent(delivery.getVehicle().getType());
            deliveryElement.appendChild(vehicleTypeElement);
            
            Element registrationElement = document.createElement("registrationNumber");
            registrationElement.setTextContent(delivery.getVehicle().getRegistrationNumber());
            deliveryElement.appendChild(registrationElement);
            
            Element assignedTimeElement = document.createElement("assignedTime");
            assignedTimeElement.setTextContent(String.valueOf(delivery.getAssignedTime()));
            deliveryElement.appendChild(assignedTimeElement);
            
            Element deliveredElement = document.createElement("delivered");
            deliveredElement.setTextContent(String.valueOf(delivery.isDelivered()));
            deliveryElement.appendChild(deliveredElement);
            
            Element deliveredTimeElement = document.createElement("deliveredTime");
            deliveredTimeElement.setTextContent(String.valueOf(delivery.getDeliveredTime()));
            deliveryElement.appendChild(deliveredTimeElement);
            
            rootElement.appendChild(deliveryElement);
            
            saveDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Delivery getDelivery(String deliveryId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList deliveryList = document.getElementsByTagName("delivery");
            for (int i = 0; i < deliveryList.getLength(); i++) {
                Element element = (Element) deliveryList.item(i);
                if (element.getAttribute("id").equals(deliveryId)) {
                    return parseDeliveryElement(element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList deliveryList = document.getElementsByTagName("delivery");
            for (int i = 0; i < deliveryList.getLength(); i++) {
                Element element = (Element) deliveryList.item(i);
                deliveries.add(parseDeliveryElement(element));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }
    
    private Delivery parseDeliveryElement(Element element) {
        String deliveryId = element.getAttribute("id");
        String orderId = element.getElementsByTagName("orderId").item(0).getTextContent();
        String driverId = element.getElementsByTagName("driverId").item(0).getTextContent();
        String driverName = element.getElementsByTagName("driverName").item(0).getTextContent();
        String drivingLicense = element.getElementsByTagName("drivingLicense").item(0).getTextContent();
        String vehicleId = element.getElementsByTagName("vehicleId").item(0).getTextContent();
        String vehicleType = element.getElementsByTagName("vehicleType").item(0).getTextContent();
        String registrationNumber = element.getElementsByTagName("registrationNumber").item(0).getTextContent();
        
        // Create temporary objects
        Customer tempCustomer = new Customer("", "", "", "", false);
        Order order = new Order(orderId, 0, tempCustomer);
        Driver driver = new Driver(driverId, driverName, drivingLicense);
        Vehicle vehicle = new Vehicle(vehicleId, vehicleType, registrationNumber);
        
        return new Delivery(deliveryId, order, driver, vehicle);
    }
    
    public boolean deleteDelivery(String deliveryId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            NodeList deliveryList = rootElement.getElementsByTagName("delivery");
            
            for (int i = 0; i < deliveryList.getLength(); i++) {
                Element element = (Element) deliveryList.item(i);
                if (element.getAttribute("id").equals(deliveryId)) {
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