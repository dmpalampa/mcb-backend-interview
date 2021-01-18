package com.mcb.mcshares.services;

import java.net.FileNameMap;
import java.net.URLConnection;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcb.mcshares.Core.GenericResponse;
import com.mcb.mcshares.entities.Entity_CustomerContactDetails;
import com.mcb.mcshares.entities.Entity_Customers;
import com.mcb.mcshares.entities.Entity_ErrorLogs;
import com.mcb.mcshares.entities.Entity_MailingAddresses;
import com.mcb.mcshares.entities.Entity_SharesDetails;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("user_service")
public class MainService {
	private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";

	// No 1
	@POST
	@Path("/UploadXmlFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("data") String data) {

		McShareRS mcshare = new McShareRS();

		GenericResponse response = new GenericResponse();

		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null) {
			response.setReturnMessage("Invalid form data");
			response.setReturnCode(202);
			return JSON.toJSONString(response, true);
		}
		// create our destination folder, if it not exists
		try {
			mcshare.createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			response.setReturnMessage("Can not create destination folder on server");
			response.setReturnCode(202);
			return JSON.toJSONString(response, true);
		}
		String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();

		File f = new File(uploadedFileLocation);
		if (f.exists() && !f.isDirectory()) {
			response.setReturnMessage("File already exists " + uploadedFileLocation);
			response.setReturnCode(202);
			return JSON.toJSONString(response, true);
		}

		try {
			saveToFile(uploadedInputStream, uploadedFileLocation);

			response.setReturnMessage("Saved successfully " + uploadedFileLocation);
			response.setReturnCode(202);
		} catch (IOException e) {
			response.setReturnMessage("Can not save file");
			response.setReturnCode(202);
			return JSON.toJSONString(response, true);
		}

		try {
			mcshare.saveXmlFileDataToDb(uploadedFileLocation, uploadedInputStream);
			System.out.println(uploadedFileLocation);
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DOMException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSON.toJSONString(response, true);
	}

	// No 5
	@GET
	@Path("/GetAllRecords")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt(InputStream incomingData) {
		GenericResponse response = new GenericResponse();
		McShareRS mcshare = new McShareRS();
		try {
			response = mcshare.GetAllCustomerRecords();

		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnMessage("Error Processing request " + e);
			response.setReturnCode(202);
		}
		return JSON.toJSONString(response, true);
	}

	// No 6
	@GET
	@Path("/GetCustomerBalance")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetCustomerBalance() {
		GenericResponse response = new GenericResponse();
		McShareRS mcshare = new McShareRS();
		try {
			response = mcshare.getCustomerBalance();

		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnMessage("Error Processing request " + e);
			response.setReturnCode(202);
		}
		return JSON.toJSONString(response, true);
	}

	// No 7 --update records
	@POST
	@Path("/updateRecords")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateSpecificRecords(InputStream incomingData) {

		String postData = readPostData(incomingData);

		JSONObject dataObject = JSON.parseObject(postData);

		String customerId = dataObject.getString("customerId");
		Double sharePrice = dataObject.getDouble("sharePrice");
		Long numShares = dataObject.getLong("numShares");
		
		GenericResponse response = new GenericResponse();
		McShareRS mcshare = new McShareRS();
		try {
			response = mcshare.updateRecords(customerId, sharePrice, numShares);

		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnMessage("Error Processing request " + e);
			response.setReturnCode(202);
		}
		return JSON.toJSONString(response, true);
	}

	// No 8 --search
	@POST
	@Path("/searchRecords")
	@Produces(MediaType.APPLICATION_JSON)
	public String searchRecords(InputStream incomingData) {
		GenericResponse response = new GenericResponse();

		String postData = readPostData(incomingData);
		JSONObject dataObject = JSON.parseObject(postData);
		String service = dataObject.getString("value");

		McShareRS mcshare = new McShareRS();
		try {
			response = mcshare.searchRecords(service);

		} catch (Exception e) {
			e.printStackTrace();
			response.setReturnMessage("Error Processing request " + e);
			response.setReturnCode(202);
		}
		return JSON.toJSONString(response, true);
	}

	// No 9 download csv
	@GET
	@Path("/downloadDataInCSV")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadDataInCSV() {
		McShareRS mcshare = new McShareRS();
		try {

			String path = "c:/uploadedFiles/";
			mcshare.downloadDataInCSV();

			File fileDownload = new File(path + File.separator + "mcshares.csv");
			ResponseBuilder response = Response.ok((Object) fileDownload);
			response.header("Content-Disposition", "attachment;filename=" + "mcshares.csv");
			return response.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	/**
	 *
	 * @param incomingData
	 * @return
	 */
	protected String readPostData(InputStream incomingData) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		return sBuilder.toString();
	}

}
