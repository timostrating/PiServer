package tests;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;

public class Xml {
    public static void main(String args[]){

//        try{
//            DefaultHandler handler = new DefaultHandler() {
//
//                boolean firstName = false;
//
//                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//
//                    if (qName.equalsIgnoreCase("firstname")) {
//                        firstName = true;
//                    }
//                }
//
//                public void characters(char ch[], int start, int length) throws SAXException {
//
//                    if (firstName) {
//                        String name = new String(ch, start, length);
//                        System.out.println("First name is : " + name);
//                        firstName = false;
//                    }
//
//                }
//            };
//
//
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            factory.setValidating(true);
//            SAXParser saxParser = factory.newSAXParser();
//            saxParser.parse(new InputSource(new StringReader(xmlstring)), handler);
//
//
//
//
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + " " + rs.getString(2));
//            }
//
//            conn.close();
//        }catch(Exception e){ System.out.println(e);}

    }
}
