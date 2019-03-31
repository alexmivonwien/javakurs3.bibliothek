package at.bibliothek.web.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * @see https://docs.oracle.com/javase/7/docs/api/java/nio/file/FileVisitor.html
 *
 */
public class MostRecentImagePerUserFinder {

	private Path pathToImageFolder;
	private Path pathToImageItself;
	
	private FileTime lastModifiedfileTime;
	
	public MostRecentImagePerUserFinder(Path pathToImageFolder) {
		this.pathToImageFolder = pathToImageFolder;
	}
	
	public Path getPathToImageItself () throws IOException {
		
		return pathToImageItself;
	}
	
	
	public void findMostRecentImagePerUser () throws IOException {
		
	    Files.walkFileTree(this.pathToImageFolder, new SimpleFileVisitor<Path>() {
	         
	         public FileVisitResult visitFile(Path pathToCurrentFile, BasicFileAttributes attrs)
	             throws IOException
	         {
	             if ( attrs.isRegularFile()) {
	            	 if (lastModifiedfileTime == null) {
	            		 lastModifiedfileTime = attrs.lastModifiedTime();
	            	 } else if ( attrs.lastModifiedTime().compareTo(lastModifiedfileTime) > 0) {
	            		 lastModifiedfileTime = attrs.lastModifiedTime();
	            	 }
	            	 
	            	 pathToImageItself = pathToCurrentFile;
	             }
	             return FileVisitResult.CONTINUE;
	         }
	     });
	}
	
}
