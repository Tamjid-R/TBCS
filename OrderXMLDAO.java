import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class OrderXMLDAO {
    private String filePath;
    private DocumentBuilderFactory documentBuilderFactory;
    private TransformerFactory transformerFactory;
    
    public OrderXMLDAO(String filePath) {
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
                
                Element rootElement = document.createElement("orders");
                document.appendChild(rootElement);
                
                saveDocument(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveOrder(Order order) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            
            // Remove existing order with same ID
            NodeList orderList = rootElement.getElementsByTagName("order");
            for (int i = 0; i < orderList.getLength(); i++) {
                Element element = (Element) orderList.item(i);
                if (element.getAttribute("id").equals(order.getOrderId())) {
                    rootElement.removeChild(element);
                    break;
                }
            }
            
            // Create new order element
            Element orderElement = document.createElement("order");
            orderElement.setAttribute("id", order.getOrderId());
            
            Element queueElement = document.createElement("queueNumber");
            queueElement.setTextContent(String.valueOf(order.getQueueNumber()));
            orderElement.appendChild(queueElement);
            
            Element customerElement = document.createElement("customerId");
            customerElement.setTextContent(order.getCustomer().getCustomerId());
            orderElement.appendChild(customerElement);
            
            Element statusElement = document.createElement("status");
            statusElement.setTextContent(order.getStatus().toString());
            orderElement.appendChild(statusElement);
            
            Element priorityElement = document.createElement("priority");
            priorityElement.setTextContent(order.getPriority().toString());
            orderElement.appendChild(priorityElement);
            
            Element totalElement = document.createElement("totalAmount");
            totalElement.setTextContent(String.valueOf(order.getTotalAmount()));
            orderElement.appendChild(totalElement);
            
            Element createdTimeElement = document.createElement("createdTime");
            createdTimeElement.setTextContent(String.valueOf(order.getCreatedTime()));
            orderElement.appendChild(createdTimeElement);
            
            Element estimatedPrepTimeElement = document.createElement("estimatedPrepTime");
            estimatedPrepTimeElement.setTextContent(String.valueOf(order.getEstimatedPrepTime()));
            orderElement.appendChild(estimatedPrepTimeElement);
            
            // Save items
            Element itemsElement = document.createElement("items");
            for (OrderItem item : order.getItems()) {
                Element itemElement = document.createElement("item");
                
                Element itemIdElement = document.createElement("itemId");
                itemIdElement.setTextContent(item.getMenuItem().getItemId());
                itemElement.appendChild(itemIdElement);
                
                Element itemNameElement = document.createElement("itemName");
                itemNameElement.setTextContent(item.getMenuItem().getName());
                itemElement.appendChild(itemNameElement);
                
                Element quantityElement = document.createElement("quantity");
                quantityElement.setTextContent(String.valueOf(item.getQuantity()));
                itemElement.appendChild(quantityElement);
                
                Element priceElement = document.createElement("price");
                priceElement.setTextContent(String.valueOf(item.getMenuItem().getPrice()));
                itemElement.appendChild(priceElement);
                
                itemsElement.appendChild(itemElement);
            }
            orderElement.appendChild(itemsElement);
            
            rootElement.appendChild(orderElement);
            
            saveDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Order getOrder(String orderId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList orderList = document.getElementsByTagName("order");
            for (int i = 0; i < orderList.getLength(); i++) {
                Element element = (Element) orderList.item(i);
                if (element.getAttribute("id").equals(orderId)) {
                    return parseOrderElement(element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList orderList = document.getElementsByTagName("order");
            for (int i = 0; i < orderList.getLength(); i++) {
                Element element = (Element) orderList.item(i);
                orders.add(parseOrderElement(element));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    private Order parseOrderElement(Element element) {
        String orderId = element.getAttribute("id");
        int queueNumber = Integer.parseInt(
            element.getElementsByTagName("queueNumber").item(0).getTextContent());
        String customerId = element.getElementsByTagName("customerId").item(0).getTextContent();
        
        // Create temporary customer (ID only)
        Customer customer = new Customer(customerId, "", "", "", false);
        Order order = new Order(orderId, queueNumber, customer);
        
        // Set status and priority
        String status = element.getElementsByTagName("status").item(0).getTextContent();
        order.setPriority(OrderPriority.valueOf(
            element.getElementsByTagName("priority").item(0).getTextContent()));
        
        // Parse items
        NodeList itemList = element.getElementsByTagName("item");
        for (int j = 0; j < itemList.getLength(); j++) {
            Element itemElement = (Element) itemList.item(j);
            String itemId = itemElement.getElementsByTagName("itemId").item(0).getTextContent();
            String itemName = itemElement.getElementsByTagName("itemName").item(0).getTextContent();
            double price = Double.parseDouble(
                itemElement.getElementsByTagName("price").item(0).getTextContent());
            int quantity = Integer.parseInt(
                itemElement.getElementsByTagName("quantity").item(0).getTextContent());
            
            MenuItem menuItem = new MenuItem(itemId, itemName, price, "");
            order.addItem(new OrderItem(menuItem, quantity));
        }
        
        return order;
    }
    
    public boolean deleteOrder(String orderId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            NodeList orderList = rootElement.getElementsByTagName("order");
            
            for (int i = 0; i < orderList.getLength(); i++) {
                Element element = (Element) orderList.item(i);
                if (element.getAttribute("id").equals(orderId)) {
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