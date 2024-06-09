package com.denys.dw.sax.read;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.denys.dw.user.User;
import com.denys.dw.user.addition.Address;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.ContactInfo;
import com.denys.dw.user.addition.Sex;
import com.denys.dw.user.addition.SocialMedia;

public class SAXAdministratorsHandler extends DefaultHandler {
	
	private List<User> administrators;
	private String currentElement = "";
	private User admin;
	private ContactInfo contactInfo;
	private Address address;
	private Birth birth;
	private SocialMedia socialMedia;
	private StringBuilder currentText;
	
	public List<User> getAllAdministrators(String filename) throws IOException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			parser.parse(new File(filename), this);
		} catch (SAXException e) {
			System.err.println(e.getMessage());
		}
		return administrators;
	}

	@Override
	public void startDocument() throws SAXException {
		//System.out.println("Start Document");
		administrators = new ArrayList<>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//System.out.println("start element " + qName);
		currentElement = qName;
		switch(currentElement) {
		case "administrators":
			break;
		case "administrator":
			admin = new User();
			contactInfo = new ContactInfo();
			address = new Address();
			birth = new Birth();
			socialMedia = new SocialMedia();
			String idAsString = attributes.getValue(Element.ID.getElement());
			Long id = Long.parseLong(idAsString);
			admin.setId(id);
			administrators.add(admin);
			break;
		default:
			currentText = new StringBuilder();
			break;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// System.out.println("CHARACTER");
		if(currentText != null) {
			currentText.append(ch, start, length);
		}
	}
	
	public static Element getByUpperCaseName(String element) {
	    if (element == null || element.isEmpty()) {
	        return null;
	    }

	    return Element.valueOf(element.toUpperCase());
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//System.out.println("end element " + qName);
		if(currentElement.equals("administrators") || currentElement.equals("administrator") 
				|| currentElement.equals(Element.CONTACTINFO.getElement())
				|| currentElement.equals(Element.ADDRESS.getElement())
				|| currentElement.equals(Element.BIRTH.getElement())
				|| currentElement.equals(Element.SOCIAL_MEDIA.getElement())) {
			return ;
		}
		String content = currentText.toString();
		Element element = getByUpperCaseName(currentElement);
		if(element != null) {
			switch(element) {
			case USERNAME:
				admin.setUsername(content);
				break;
			case FIRSTNAME:
				admin.setFirstName(content);
				break;
			case LASTNAME:
				admin.setLastName(content);
				break;
			case PASSWORD:
				admin.setPassword(content);
				break;
			case ADMIN:
				admin.setAdmin(Boolean.parseBoolean(content));
				break;
			case EMAIL:
				contactInfo.setEmail(content);
				break;
			case TELEPHONE:
				contactInfo.setTelephone(content);
				break;
			case POSITION:
				contactInfo.setPosition(content);
				break;
			case DEPARTMENT:
				contactInfo.setDepartment(content);
				admin.setContactInfo(contactInfo);
				break;
			case COUNTRY:
				address.setCountry(content);
				break;
			case CITY:
				address.setCity(content);
				break;
			case STREET:
				address.setStreet(content);
				admin.setAddress(address);
				break;
			case SEX:
				birth.setSex(Sex.valueOf(content.toUpperCase()));
				break;
			case DATE:
				birth.setDate(LocalDate.parse(content));
				admin.setBirth(birth);
				break;
			case LINKEDINURL:
				socialMedia.setLinkedInURL(content);
				break;
			case INSTAGRAMURL:
				socialMedia.setInstagramURL(content);
				break;
			case FACEBOOKURL:
				socialMedia.setFacebookURL(content);
				break;
			case TELEGRAMURL:
				socialMedia.setTelegramURL(content);
				admin.setSocialMedia(socialMedia);
				break;
			default:
				break;
			}
		}
		currentElement = "";
	}

	@Override
	public void endDocument() throws SAXException {
		// System.out.println("End Document");
	}
	
	@Override
	public void warning(SAXParseException e) throws SAXException {
		System.err.println("Warning!");
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.err.println("Error!");
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		System.err.println("Fatal Error!");
	}
}
