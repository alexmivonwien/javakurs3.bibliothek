package at.bibliothek.services.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import at.bibliothek.web.NewCustomerBean;

/**
 * 
 * @author alex-mi
 *
 * An image is uploaded in a dedicated directory for the particular user that uploads it.
 * The process is as follows:
 * 
 * 1.) The image is uploaded with an AJAX call / HTTP Post method via the js function ajaxUploadFile() ;
 * 2.) As the customerId is stored as a session attribute, it is trivial to determine whose user the uploaded file belongs to;
 * 3.) A new directory on the file system having the same name as the user the uploaded file belongs to is created, if no such directory still exists
 * 4.) The uploaded image is stored on the directory of step (3)
 * 5.) (Optional) the image description provided by the user upon upload is stored in the DB
 */

@WebServlet("/fileUpload")
@MultipartConfig
public class ImageUploadServlet extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		doPost(req, resp);
	}

	/**
	 * @see http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
	 * @see https://stackoverflow.com/questions/29307771/java-how-to-get-file-type-in-servlet-3-0
	 * @see https://gist.github.com/cengizIO/2067999
	 * 
     *
	 */
//	private String getFileName(Part part) {
//		for (String cd : part.getHeader("content-disposition").split(";")) {
//			if (cd.trim().startsWith("filename")) {
//				return cd.substring(cd.indexOf('=') + 1).trim()
//						.replace("\"", "");
//			}
//		}
//		return null;
//	}
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		Part filePart = null;
		PrintWriter pw = null;
		String fileNamer = null;
		
		// @see https://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
		// @see https://gist.github.com/cengizIO/2067999
		// @see https://stackoverflow.com/questions/38018632/use-dropzone-with-jsf
		
		try {
			for (Part fPart : request.getParts()){
				if (fPart.getName()!=null && fPart.getName().equals("file") && StringUtils.isNotEmpty(fPart.getSubmittedFileName())){
					fileNamer =  fPart.getSubmittedFileName();
					filePart = fPart;
					break;
				}
			}
			
			Path imageDirectoryPath = ImageUtils.determineUploadDirectoryName(request, response);
			
			if (!Files.exists(imageDirectoryPath)) {
				Files.createDirectories(imageDirectoryPath);
			}
		
			Path completeFilePath = Paths.get(imageDirectoryPath.toString() + File.separatorChar + fileNamer);
			OutputStream outStream = Files.newOutputStream(completeFilePath);
			
			DataInputStream inStreamReader = new DataInputStream(filePart.getInputStream());
			
			byte[] buf = new byte[ImageUtils.BUFFUER_SIZE];
			int count = 0;

			while ((count = inStreamReader.read(buf)) >= 0) {
				outStream.write(buf, 0, count);
			}
			
			outStream.close();
			inStreamReader.close();
			
			pw = response.getWriter();
			pw.write(ImageUtils.UPLOAD_OK_STATUS);
			
			pw.flush();
			pw.close();
			
		} catch (IOException ioe) {
			Logger.getLogger(this.getClass()).error(ioe.getMessage(), ioe);
			
			try{
				pw = response.getWriter();
				pw.write(ImageUtils.UPLOAD_ERROR_STATUS);
				
				pw.flush();
				pw.close();
			}
			catch (IOException ie) {
				Logger.getLogger(this.getClass()).error(ioe.getMessage(), ie);
				
			}
		}
		

	}
}
