package at.bibliothek.services.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import at.bibliothek.web.NewCustomerBean;;

@WebServlet("/fileUpload")
@MultipartConfig
public class ImageUploadServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		Long savedKundeId = (Long)request.getSession(false).getAttribute(NewCustomerBean.SESSION_ATTRIBUTE_CUST_ID);

		// Retrieves <input type="text" name="description">
		String description = request.getParameter("description");

		// Retrieves <input type="file" name="file">
		Part filePart = null;
		
		// @see https://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
		try {
			filePart = request.getPart("file");
//			// MSIE fix.
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
//			InputStream fileContent = filePart.getInputStream();
			// ... (do your job here)
			
			response.sendRedirect( request.getServletContext().getContextPath().toString() + "/pages/customers.jsf");
			
		} catch (IOException ioe) {

		}
		

	}
}
