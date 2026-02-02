import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class FeedbackXMLDAO {
    private String filePath;
    private DocumentBuilderFactory documentBuilderFactory;
    private TransformerFactory transformerFactory;
    
    public FeedbackXMLDAO(String filePath) {
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
                
                Element rootElement = document.createElement("feedbacks");
                document.appendChild(rootElement);
                
                saveDocument(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveFeedback(Feedback feedback) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            
            // Remove existing feedback with same ID
            NodeList feedbackList = rootElement.getElementsByTagName("feedback");
            for (int i = 0; i < feedbackList.getLength(); i++) {
                Element element = (Element) feedbackList.item(i);
                if (element.getAttribute("id").equals(feedback.getFeedbackId())) {
                    rootElement.removeChild(element);
                    break;
                }
            }
            
            // Create new feedback element
            Element feedbackElement = document.createElement("feedback");
            feedbackElement.setAttribute("id", feedback.getFeedbackId());
            
            Element orderIdElement = document.createElement("orderId");
            orderIdElement.setTextContent(feedback.getOrder().getOrderId());
            feedbackElement.appendChild(orderIdElement);
            
            Element ratingElement = document.createElement("rating");
            ratingElement.setTextContent(String.valueOf(feedback.getRating()));
            feedbackElement.appendChild(ratingElement);
            
            Element commentsElement = document.createElement("comments");
            commentsElement.setTextContent(feedback.getComments());
            feedbackElement.appendChild(commentsElement);
            
            Element createdTimeElement = document.createElement("createdTime");
            createdTimeElement.setTextContent(String.valueOf(feedback.getCreatedTime()));
            feedbackElement.appendChild(createdTimeElement);
            
            rootElement.appendChild(feedbackElement);
            
            saveDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Feedback getFeedback(String feedbackId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList feedbackList = document.getElementsByTagName("feedback");
            for (int i = 0; i < feedbackList.getLength(); i++) {
                Element element = (Element) feedbackList.item(i);
                if (element.getAttribute("id").equals(feedbackId)) {
                    return parseFeedbackElement(element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList feedbackList = document.getElementsByTagName("feedback");
            for (int i = 0; i < feedbackList.getLength(); i++) {
                Element element = (Element) feedbackList.item(i);
                feedbacks.add(parseFeedbackElement(element));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbacks;
    }
    
    private Feedback parseFeedbackElement(Element element) {
        String feedbackId = element.getAttribute("id");
        String orderId = element.getElementsByTagName("orderId").item(0).getTextContent();
        int rating = Integer.parseInt(
            element.getElementsByTagName("rating").item(0).getTextContent());
        String comments = element.getElementsByTagName("comments").item(0).getTextContent();
        
        // Create temporary order (ID only)
        Customer tempCustomer = new Customer("", "", "", "", false);
        Order order = new Order(orderId, 0, tempCustomer);
        
        return new Feedback(feedbackId, order, rating, comments);
    }
    
    public List<Feedback> getFeedbackByRating(int rating) {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            NodeList feedbackList = document.getElementsByTagName("feedback");
            for (int i = 0; i < feedbackList.getLength(); i++) {
                Element element = (Element) feedbackList.item(i);
                int feedbackRating = Integer.parseInt(
                    element.getElementsByTagName("rating").item(0).getTextContent());
                
                if (feedbackRating == rating) {
                    feedbacks.add(parseFeedbackElement(element));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbacks;
    }
    
    public boolean deleteFeedback(String feedbackId) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));
            
            Element rootElement = document.getDocumentElement();
            NodeList feedbackList = rootElement.getElementsByTagName("feedback");
            
            for (int i = 0; i < feedbackList.getLength(); i++) {
                Element element = (Element) feedbackList.item(i);
                if (element.getAttribute("id").equals(feedbackId)) {
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