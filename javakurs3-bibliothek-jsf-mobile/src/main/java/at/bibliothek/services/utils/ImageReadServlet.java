package at.bibliothek.services.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import at.bibliothek.model.Constants;
import at.bibliothek.web.utils.MostRecentImagePerUserFinder;

@WebServlet("/imageRead")
@MultipartConfig
public class ImageReadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String custId = request.getParameter(Constants.REQUEST_PARAMETER_CUSTOMER_ID);

		if (StringUtils.isEmpty(custId)) {
			return;
		}
		
		try{
			MostRecentImagePerUserFinder mostRecentImagePerUser = new MostRecentImagePerUserFinder(
					ImageUtils.determineSelectedUserUploadDirectoryName(request, response,  Integer.valueOf(custId)));
			mostRecentImagePerUser.findMostRecentImagePerUser();
	
			Path selectedCustIdMostRecentImageSrc = mostRecentImagePerUser.getPathToImageItself();
			
			Path destinationPath = ImageUtils.copyMostRecentCustomerImageToUserApplicationDirectory(selectedCustIdMostRecentImageSrc, Integer.valueOf(custId), request, response);
			
			// https://stackoverflow.com/questions/8623709/output-an-image-file-from-a-servlet
			String mime = request.getServletContext().getMimeType(destinationPath.getFileName().toString());
			
			if (destinationPath == null || mime == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
	
			PrintWriter pw = response.getWriter();
			pw.write(custId + "/" + destinationPath.getFileName().toString());
			
			pw.flush();
			pw.close();
		
		} catch ( ServletException | IOException e ) {
			e.printStackTrace();
			Logger.getLogger(this.getClass()).error(e.getMessage(), e);
			throw e;
		}
		

	}

}
