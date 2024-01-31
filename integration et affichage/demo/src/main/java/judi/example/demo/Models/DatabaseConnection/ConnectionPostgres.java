package judi.example.demo.Models.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConnectionPostgres {
    static String ip;
    static int port;
    static String databaseName;
    static String userName;
    static String password;
    public static void setDatabaseName(String databaseName) {
        ConnectionPostgres.databaseName = databaseName;
    }
    public static void setIp(String ip) {
        ConnectionPostgres.ip = ip;
    }
    public static void setPassword(String password) {
        ConnectionPostgres.password = password;
    }
    public static void setPort(int port) {
        ConnectionPostgres.port = port;
    }
    public static void setUserName(String userName) {
        ConnectionPostgres.userName = userName;
    }
    public static String getDatabaseName() {
        return databaseName;
    }
    public static String getIp() {
        return ip;
    }
    public static String getPassword() {
        return password;
    }
    public static int getPort() {
        return port;
    }
    public static String getUserName() {
        return userName;
    }

     public static void loadConfigFromXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("./connectionPostgres.xml");

            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    switch (element.getTagName()) {
                        case "ip":
                            ConnectionPostgres.setIp(element.getTextContent());
                            break;
                        case "port":
                            ConnectionPostgres.setPort(Integer.parseInt(element.getTextContent()));
                            break;
                        case "databaseName":
                            ConnectionPostgres.setDatabaseName(element.getTextContent());
                            break;
                        case "userName":
                            ConnectionPostgres.setUserName(element.getTextContent());
                            break;
                        case "password":
                            ConnectionPostgres.setPassword(element.getTextContent());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection connectDefault() throws Exception{
        ConnectionPostgres.loadConfigFromXML();
        return ConnectionPostgres.connect(ConnectionPostgres.getIp(), ConnectionPostgres.getPort(), ConnectionPostgres.getDatabaseName(), ConnectionPostgres.getUserName(), ConnectionPostgres.getPassword());    
    }
    public static Connection connect(String ip,int port,String databaseName,String userName,String password) throws Exception{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://"+ip+":"+port+"/"+databaseName+"", ""+userName+"", ""+password+"");
    }
}
