package at.bibliothek.services.utils;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.bibliothek.model.Constants;
import at.bibliothek.web.NewCustomerBean;

/**
 * a utility class for methods that are common to both ImageReadServlet and
 * ImageUploadServlet
 * 
 * @author User
 *
 */
public class ImageUtils {

	public static final int BUFFUER_SIZE = 1024;
	public static final String UPLOAD_OK_STATUS = "OK";
	public static final String UPLOAD_ERROR_STATUS = "Error uploading file !!!";

	/**
	 * 
	 */
	public static Path determineUploadDirectoryName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long savedKundeId = (Long) request.getSession(false).getAttribute(NewCustomerBean.SESSION_ATTRIBUTE_CUST_ID);
		Path imageDirectoryName = Paths.get(Constants.UPLOAD_FILE_DIRECTORY + File.separator
				+ request.getServletContext().getContextPath() + File.separator + savedKundeId);

		return imageDirectoryName;
	}

	public static Path determineSelectedUserUploadDirectoryName(HttpServletRequest request,
			HttpServletResponse response, int selectedUserId) throws ServletException, IOException {

		Path imageDirectoryName = Paths.get(Constants.UPLOAD_FILE_DIRECTORY + File.separator
				+ request.getServletContext().getContextPath() + File.separator + selectedUserId);

		return imageDirectoryName;
	}
	
	public static Path copyMostRecentCustomerImageToUserApplicationDirectory(Path pathToSelectedCustIdMostRecentImage, int selectedCustId, 
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sourceFileName = pathToSelectedCustIdMostRecentImage.getFileName().toString();
		
		Path destinationFolder = Paths.get(request.getServletContext().getRealPath("/") + File.separator + "resources/images"  + File.separator + selectedCustId);
		
		if (!destinationFolder.toFile().exists()){
			destinationFolder.toFile().mkdir();
		}
		
		Path destinationPath = Paths.get(request.getServletContext().getRealPath("/") + File.separator + "resources/images"  + File.separator + selectedCustId + File.separator + sourceFileName);
		
		if (!Files.exists(destinationPath)){
			Files.copy(Files.newInputStream(pathToSelectedCustIdMostRecentImage), destinationPath);
		}
		
		// https://stackoverflow.com/questions/8623709/output-an-image-file-from-a-servlet
		String mime = request.getServletContext().getMimeType(destinationPath.getFileName().toString());

		if (mime == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		
		return destinationPath;
	}
}
