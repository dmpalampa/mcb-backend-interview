
package com.mcb.mcshares.services;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mcb.mcshares.Core.GenericResponse;
import com.mcb.mcshares.entities.Entity_CustomerContactDetails;
import com.mcb.mcshares.entities.Entity_Customers;
import com.mcb.mcshares.entities.Entity_ErrorLogs;
import com.mcb.mcshares.entities.Entity_MailingAddresses;
import com.mcb.mcshares.entities.Entity_SharesDetails;
import com.mcb.mcshares.entities.Entity_XmlFiles;
import com.opencsv.CSVWriter;


public class McShareRS extends LocalCoreObject {

	private Entity_Customers saveCustomerDetails(Entity_Customers customer) {
		try {
			if (saveEntity(customer))
				return customer;
		} catch (Exception e) {

		}
		return null;
	}

	private Entity_SharesDetails saveShareDetails(Entity_SharesDetails shareDetails) {
		try {
			if (saveEntity(shareDetails))
				return shareDetails;
		} catch (Exception e) {

		}
		return null;

	}

	private Entity_CustomerContactDetails saveContactDetails(Entity_CustomerContactDetails contacts) {
		if (saveEntity(contacts))
			return contacts;
		return null;
	}

	private Entity_MailingAddresses saveMailingAddress(Entity_MailingAddresses mailingAddress) {
		try {
			if (saveEntity(mailingAddress))
				return mailingAddress;
		} catch (Exception e) {

		}

		return null;
	}

	private Entity_ErrorLogs saveErrorToDb(Entity_ErrorLogs error) {
		try {
			if (saveEntity(error))
				return error;
		} catch (Exception e) {

		}
		return null;
	}

	// download csv files

	// No 9 ----downloadDataInCSV
	public GenericResponse downloadDataInCSV() {
		
		try {
			return createCsvData();

		} catch (

		Exception e) {
			return new GenericResponse(0, "Error ......", e);
		}
	}

	// create CSV data
	private GenericResponse createCsvData() {
		String[] header = { "customer_id", "Customer_Type", "Date_Of_Birth", "Date_Incorp", "Registration_No",
				"Address_Line1", "Address_Line2", "Town_City", "Country", "Contact_Name", "Contact_Number",
				"Num_Shares", "Share_Price", "balance" };

		List<String[]> customerCSVList = new ArrayList<>();
		customerCSVList.add(header);
		
		// Entity_Customers customer = new Entity_Customers();
		List<Entity_Customers> customers = new ArrayList<Entity_Customers>();
		List<Entity_Customers> customersListToReturn = new ArrayList<Entity_Customers>();
		List<Entity_CustomerContactDetails> contacts = new ArrayList<Entity_CustomerContactDetails>();
		List<Entity_SharesDetails> shares = new ArrayList<Entity_SharesDetails>();
		List<Entity_MailingAddresses> addresses = new ArrayList<Entity_MailingAddresses>();

		Entity_CustomerContactDetails contact = null;
		Entity_SharesDetails share = null;
		Entity_MailingAddresses address = null;
		try {
			customers = (List<Entity_Customers>) getEntityList("From Entity_Customers").getReturnObject();
			
			for (Entity_Customers c : customers) {
				System.out.println("customer--"+ c.getId());
				contacts = (List<Entity_CustomerContactDetails>) getEntityList(
						"From Entity_CustomerContactDetails where customerId=" + c.getId()).getReturnObject();
				shares = (List<Entity_SharesDetails>) getEntityList(
						"From Entity_SharesDetails where customerId=" + c.getId()).getReturnObject();
				addresses = (List<Entity_MailingAddresses>) getEntityList(
						"From Entity_MailingAddresses where customerId=" + c.getId()).getReturnObject();

				if (contacts.size() > 0)
					contact = contacts.get(0);

				if (shares.size() > 0)
					share = shares.get(0);

				if (addresses.size() > 0)
					address = addresses.get(0);

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String dateOfBirth = "";
				String balance = "";
				if(share.getBalance()!=null) {
					balance = String.valueOf(share.getBalance());
				}
				if (c.getDateOfBirth() != null)
					dateOfBirth = dateFormat.format(c.getDateOfBirth());

				String dateOfIncorp = "";
				if (c.getDateIncorp() != null)
					dateOfIncorp = dateFormat.format(c.getDateIncorp());

				String[] customer = { c.getCustomerId(), c.getCustomerType(), dateOfBirth, dateOfIncorp,
						c.getRegistrationNo(), address.getAddressLine1(), address.getAddressLine2(),
						address.getTownCity(), address.getCountry(), contact.getContactName(),
						contact.getContactNumber(), String.valueOf(share.getSharesPrice()),
						String.valueOf(share.getNumShares()), String.valueOf(share.getBalance()) };
				
				System.out.println("customer-----------------");
				customerCSVList.add(customer);
			} 
            
            try (CSVWriter writer = new CSVWriter(new FileWriter("c:/uploadedFiles/mcshare.csv"))){
                 writer.writeAll(customerCSVList);;
            }
			
			return new GenericResponse(0, "Records", customerCSVList);

		} catch (Exception e) {
			return new GenericResponse(0, "Error ......", e);
		}
	}

	// No 7
	public GenericResponse updateRecords(String customerId, Double sharePrice, Long numShares) {
		Entity_Customers customer = new Entity_Customers();
		List<Entity_Customers> customers = new ArrayList<Entity_Customers>();
		List<Entity_Customers> customersListToReturn = new ArrayList<Entity_Customers>();
		List<Entity_CustomerContactDetails> contacts = new ArrayList<Entity_CustomerContactDetails>();
		List<Entity_SharesDetails> shares = new ArrayList<Entity_SharesDetails>();
		List<Entity_MailingAddresses> addresses = new ArrayList<Entity_MailingAddresses>();

		Entity_CustomerContactDetails contact = null;
		Entity_SharesDetails share = null;
		Entity_MailingAddresses address = null;

		try {
			customer = (Entity_Customers) getOneEntityByField(Entity_Customers.class, "customerId", customerId);

			if (customer == null)
				return new GenericResponse(0, "No customer found such ID ", null);

			shares = (List<Entity_SharesDetails>) getEntityList(
					"From Entity_SharesDetails where customerId=" + customer.getId()).getReturnObject();

			if (customer.getCustomerType() != "Corporate") {
				if (shares.size() > 0)
					share = shares.get(0);

				if (share != null) {
					share.setSharesPrice(sharePrice);
					share.setNumShares(numShares);
					Double balance = sharePrice * numShares;
					share.setBalance(balance);
					saveShareDetails(share);
				}
			}
			 customersListToReturn.add(customer);

			return new GenericResponse(0,"Updated Succefully ", customersListToReturn);

		} catch (

		Exception e) {
			return new GenericResponse(0, "Error ......", e);
		}
	}

	// No 8 -- search records
	public GenericResponse searchRecords(String searchValue) {

		// Entity_Customers customer = new Entity_Customers();
		List<Entity_Customers> customers = new ArrayList<Entity_Customers>();
		List<Entity_Customers> customersListToReturn = new ArrayList<Entity_Customers>();
		List<Entity_CustomerContactDetails> contacts = new ArrayList<Entity_CustomerContactDetails>();
		List<Entity_SharesDetails> shares = new ArrayList<Entity_SharesDetails>();
		List<Entity_MailingAddresses> addresses = new ArrayList<Entity_MailingAddresses>();

		Entity_Customers customer = new Entity_Customers();
		;
		Entity_SharesDetails share = null;
		Entity_MailingAddresses address = null;
		try {

			contacts = (List<Entity_CustomerContactDetails>) getEntityList(
					"From Entity_CustomerContactDetails where contactName like '%" + searchValue + "%'")
							.getReturnObject();

			for (Entity_CustomerContactDetails c : contacts) {
				customers = (List<Entity_Customers>) getEntityList("From Entity_Customers where  id=" + c.getId())
						.getReturnObject();

				shares = (List<Entity_SharesDetails>) getEntityList(
						"From Entity_SharesDetails where customerId=" + c.getId()).getReturnObject();
				addresses = (List<Entity_MailingAddresses>) getEntityList(
						"From Entity_MailingAddresses where customerId=" + c.getId()).getReturnObject();

				if (customers.size() > 0)
					customer = customers.get(0);

				if (shares.size() > 0)
					share = shares.get(0);

				if (addresses.size() > 0)
					address = addresses.get(0);

				System.out.println("id --- " + c.getId());

				customer.setContactDetails(c);
				customer.setSharesDetails(share);
				customer.setMailingAddress(address);
				customersListToReturn.add(customer);
			}
			return new GenericResponse(0, "Records", customersListToReturn);

		} catch (Exception e) {
			return new GenericResponse(0, "Error ......", e);
		}

	}

	// list all records
	public GenericResponse GetAllCustomerRecords() {

		// Entity_Customers customer = new Entity_Customers();
		List<Entity_Customers> customers = new ArrayList<Entity_Customers>();
		List<Entity_Customers> customersListToReturn = new ArrayList<Entity_Customers>();
		List<Entity_CustomerContactDetails> contacts = new ArrayList<Entity_CustomerContactDetails>();
		List<Entity_SharesDetails> shares = new ArrayList<Entity_SharesDetails>();
		List<Entity_MailingAddresses> addresses = new ArrayList<Entity_MailingAddresses>();

		Entity_CustomerContactDetails contact = null;
		Entity_SharesDetails share = null;
		Entity_MailingAddresses address = null;
		try {
			customers = (List<Entity_Customers>) getEntityList("From Entity_Customers").getReturnObject();

			for (Entity_Customers c : customers) {
				contacts = (List<Entity_CustomerContactDetails>) getEntityList(
						"From Entity_Customers where customerId=" + c.getId()).getReturnObject();
				shares = (List<Entity_SharesDetails>) getEntityList(
						"From Entity_SharesDetails where customerId=" + c.getId()).getReturnObject();
				addresses = (List<Entity_MailingAddresses>) getEntityList(
						"From Entity_MailingAddresses where customerId=" + c.getId()).getReturnObject();

				if (contacts.size() > 0)
					contact = contacts.get(0);

				if (shares.size() > 0)
					share = shares.get(0);

				if (addresses.size() > 0)
					address = addresses.get(0);

				c.setContactDetails(contact);
				c.setSharesDetails(share);
				c.setMailingAddress(address);
				customersListToReturn.add(c);
			}
			return new GenericResponse(0, "Records", customersListToReturn);

		} catch (Exception e) {
			return new GenericResponse(0, "Error ......", e);
		}
	}

	// REQUIREMENT 6
	public GenericResponse getCustomerBalance() {

		// Entity_Customers customer = new Entity_Customers();
		List<Entity_Customers> customers = new ArrayList<Entity_Customers>();
		List<Entity_Customers> customersListToReturn = new ArrayList<Entity_Customers>();
		List<Entity_CustomerContactDetails> contacts = new ArrayList<Entity_CustomerContactDetails>();
		List<Entity_SharesDetails> shares = new ArrayList<Entity_SharesDetails>();
		List<Entity_MailingAddresses> addresses = new ArrayList<Entity_MailingAddresses>();

		List<String> contactNames = new ArrayList<String>();
		List<Double> shareBalances = new ArrayList<Double>();
		;
		Entity_MailingAddresses address = null;
		try {
			customers = (List<Entity_Customers>) getEntityList("From Entity_Customers").getReturnObject();

			for (Entity_Customers c : customers) {
				contactNames = (List<String>) getEntityList(
						"Select contactName From Entity_CustomerContactDetails where customerId=" + c.getId())
								.getReturnObject();
				shareBalances = (List<Double>) getEntityList(
						"Select (sharesPrice * numShares) as balance From Entity_SharesDetails where customerId="
								+ c.getId()).getReturnObject();
				shares = (List<Entity_SharesDetails>) getEntityList(
						"From Entity_SharesDetails where customerId=" + c.getId()).getReturnObject();

				if (shareBalances.size() > 0)
					c.setBalance(shareBalances.get(0));

				if (contactNames.size() > 0)
					c.setContactName(contactNames.get(0));

				if (shares.size() > 0) {
					c.setSharesPrice(shares.get(0).getSharesPrice());
					c.setNumShares(shares.get(0).getNumShares());
				}

				customersListToReturn.add(c);
			}
			return new GenericResponse(0, "Records", customersListToReturn);

		} catch (Exception e) {
			return new GenericResponse(0, "Error ......", e);
		}

	}

	public GenericResponse saveXmlFileDataToDb(String fileName, InputStream uploadedInputStream)
			throws SAXException, IOException, ParserConfigurationException, DOMException, ParseException {

		Entity_Customers customer = null;
		Entity_Customers saveCustomer = null;
		Entity_CustomerContactDetails contacts = null;
		Entity_SharesDetails shareDetails = null;
		Entity_MailingAddresses mailingAddress = null;
		Entity_ErrorLogs error = null;

		List<Entity_ErrorLogs> errors = null;
		List<Entity_ErrorLogs> errorsListToReturn = new ArrayList<Entity_ErrorLogs>();

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(fileName));

			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());

			NodeList listOfCustomers = doc.getElementsByTagName("DataItem_Customer");
			String RegistrationNo = "";

			for (int s = 0; s < listOfCustomers.getLength(); s++) {

				errors = new ArrayList<Entity_ErrorLogs>();
				error = new Entity_ErrorLogs();
				Node firstPersonNode = listOfCustomers.item(s);

				customer = new Entity_Customers();
				contacts = new Entity_CustomerContactDetails();
				shareDetails = new Entity_SharesDetails();
				mailingAddress = new Entity_MailingAddresses();

				if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstPersonElement = (Element) firstPersonNode;

					customer.setCustomerId(
							firstPersonElement.getElementsByTagName("customer_id").item(0).getTextContent());
					// Customer_Type
					String CustomerType = firstPersonElement.getElementsByTagName("Customer_Type").item(0)
							.getTextContent();
					customer.setCustomerType(CustomerType);
					// date of birth
					String dateOfBirth = firstPersonElement.getElementsByTagName("Date_Of_Birth").item(0)
							.getTextContent();

					if (CustomerType.equals("Individual")) {
						if (dateOfBirth != "") {
							if (isCustomerOfRequiredAge(dateOfBirth)) {
								customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
							} else {
								errors.add(buildErrorObject("Customer Should at least 18 years", "99"));
							}
						} else {
							errors.add(buildErrorObject("Customer Date of Birth is required", "99"));
						}
					}

					// date of incorp
					if (firstPersonElement.getElementsByTagName("Date_Incorp").item(0).getTextContent() != "")
						customer.setDateIncorp(new SimpleDateFormat("dd/MM/yyyy").parse(
								firstPersonElement.getElementsByTagName("Date_Incorp").item(0).getTextContent()));

					// registration

					if (firstPersonElement.getElementsByTagName("Registration_No").getLength() > 0)
						RegistrationNo = firstPersonElement.getElementsByTagName("Registration_No").item(0)
								.getTextContent();

					// registration
					if (firstPersonElement.getElementsByTagName("REGISTRATION_NO").getLength() > 0)
						RegistrationNo = firstPersonElement.getElementsByTagName("REGISTRATION_NO").item(0)
								.getTextContent();

					customer.setRegistrationNo(RegistrationNo);
					System.out.println("reg no" + RegistrationNo);

					NodeList addressList = firstPersonElement.getElementsByTagName("Mailing_Address");
					Element addressElement = (Element) addressList.item(0);
					mailingAddress.setAddressLine1(
							addressElement.getElementsByTagName("Address_Line1").item(0).getTextContent());
					mailingAddress.setAddressLine2(
							addressElement.getElementsByTagName("Address_Line2").item(0).getTextContent());
					mailingAddress
							.setTownCity(addressElement.getElementsByTagName("Town_City").item(0).getTextContent());
					mailingAddress.setCountry(addressElement.getElementsByTagName("Country").item(0).getTextContent());

					NodeList contactDetails = firstPersonElement.getElementsByTagName("Contact_Details");
					Element contactElement = (Element) contactDetails.item(0);
					contacts.setContactName(
							contactElement.getElementsByTagName("Contact_Name").item(0).getTextContent());
					contacts.setContactNumber(
							contactElement.getElementsByTagName("Contact_Number").item(0).getTextContent());

					NodeList sharesDetails = firstPersonElement.getElementsByTagName("Shares_Details");
					Element sharesElement = (Element) sharesDetails.item(0);
					String shareNum = sharesElement.getElementsByTagName("Num_Shares").item(0).getTextContent();
					String sharesPrice = sharesElement.getElementsByTagName("Share_Price").item(0).getTextContent();

					if (isShareNumberValid(shareNum) != 0L) {// validate share number
						shareDetails.setNumShares(isShareNumberValid(shareNum));
					} else {
						errors.add(buildErrorObject("Num_Shares must an integer and greater 0", "99"));
					}

					if (isSharePriceValid(sharesPrice) != null) {// validate share price
						shareDetails.setSharesPrice(isSharePriceValid(sharesPrice));
					} else {
						errors.add(buildErrorObject(
								"Share_Price must a number of greater than 0 up to two decimal places", "99"));
					}

					if (errors.size() > 0) {
						// save list of errors
						for (Entity_ErrorLogs e : errors) {
							if (saveEntity(e))
								errorsListToReturn.add(e);
						}
						// errors.clear();
					} else {
						Entity_Customers savedCustomer = saveCustomerDetails(customer);

						Double balance = shareDetails.getNumShares() * shareDetails.getSharesPrice();

						shareDetails.setBalance(balance);
						if (savedCustomer != null) {
							shareDetails.setCustomerId(savedCustomer.getId());
							contacts.setCustomerId(savedCustomer.getId());
							mailingAddress.setCustomerId(savedCustomer.getId());
							saveContactDetails(contacts);
							saveMailingAddress(mailingAddress);
							saveShareDetails(shareDetails);
						}
					}
				}
			}
			saveXmlFile(fileName, uploadedInputStream);

			return new GenericResponse(99, "Customer list save succefully.", errorsListToReturn);

		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new GenericResponse(99, "Technical Error occured.", null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new GenericResponse(99, "Technical Error occured.", null);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new GenericResponse(99, "Technical Error occured.", null);
		}
		// return new GenericResponse(99, "Customer list save succefully.", null);
	}

	// storing xml file to database
	public Entity_XmlFiles saveXmlFile(String fileName, InputStream inStream) {
		Entity_XmlFiles file = new Entity_XmlFiles();
		try {
			if (isFileMimeTypeXml(fileName)) { // validating xml file
				// ====================
				File f = new File(fileName);
				byte[] fData = new byte[(int) f.length()];
				DataInputStream dis = new DataInputStream(new FileInputStream(f));
				dis.readFully(fData);
				dis.close();
				file.setfContent(fData);
				file.setfName(fileName);
				if (saveEntity(file))
					return file;
			}
		} catch (Exception e) {

		}
		return null;
	}

	// validate number of shares ( must be an integer and greater than 0)
	private Long isShareNumberValid(String shareNumber) {
		// Object shareNumber =shareNum;
		if (isStringInteger(shareNumber)) {
			Long validShareNumber = Long.parseLong(shareNumber);
			if (validShareNumber <= 0)
				return 0L;
			// System.out.println("Share number =" + validShareNumber);
			return validShareNumber;
		} else {
			return 0L;
		}

		// return false;
	}

	// validate share price ( must be a number greater than 0 up to two decimal
	// places)
	private Double isSharePriceValid(String sharePrice) {

		try {
			if (isStringDouble(sharePrice)) {
				Double validSharePrice = Double.parseDouble(sharePrice);
				if (validSharePrice <= 0)
					return null;

				double roundOff = Math.round(validSharePrice * 100) / 100D;
				String d = String.format("%.2f", roundOff);
				System.out.println("Round share price =" + d);
				return Double.parseDouble(d);

			} else {
				return null;
			}
		} catch (Exception e) {

		}
		return null;
	}

	// validate customer age
	private Boolean isCustomerOfRequiredAge(String dob) throws ParseException {

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date currentDate = new Date(ts.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String date = dateFormat.format(currentDate);

		java.util.Date currentDateFormatted = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		java.util.Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dob);

		long diff = currentDateFormatted.getTime() - dateOfBirth.getTime();

		System.out.println("Difference between  " + currentDateFormatted + " and " + dateOfBirth + " is "
				+ (diff / (1000 * 60 * 60 * 24)) + " days.");

		int numberOfDaysDiff = (int) (diff / (1000 * 60 * 60 * 24));
		System.out.println("number Of Days Diff  " + numberOfDaysDiff);

		int age = numberOfDaysDiff / 365;
		System.out.println("Age is  " + age);

		if (age >= 18) {
			return true;
		}
		return false;
	}

	// build error object
	private Entity_ErrorLogs buildErrorObject(String errorMessage, String errorCode) {
		Entity_ErrorLogs errorsToReturn = new Entity_ErrorLogs();
		try {
			errorsToReturn.setErrorMessage(errorMessage);
			errorsToReturn.setErrorCode(errorMessage);
			// if (saveEntity(mailingAddress))
			return errorsToReturn;
		} catch (Exception e) {

		}
		return null;
	}

	// check file is xml type
	private Boolean isFileMimeTypeXml(String fileName) {

		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String mimeType = fileNameMap.getContentTypeFor(fileName);
		// System.out.println("mime type is " + mimeType);

		if (mimeType.equals("application/xml")) {
			System.out.println(mimeType);
			return true;
		}
		return false;
	}

	private boolean isStringInteger(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	private boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

}