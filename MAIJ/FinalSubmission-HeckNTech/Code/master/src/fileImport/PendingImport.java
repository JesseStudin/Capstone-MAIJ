/**
 * 
 */
package fileImport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents a project version pending importation into the program.
 * Keeps track of the .java and .jar files and the root directory.
 * @author Jacob Botha
 *
 */
public class PendingImport {
	private List<Path> javaFiles;
	private Path selectedDirectory;
	private List<Path> jarFiles;

	/** Extracts and stores all .jar and .java files beneath the root directory.
	 * @param dir The root directory to search under
	 * @throws IOException if there was a problem traversing the directory
	 */
	public PendingImport(String dir) throws IOException {
		selectedDirectory = Paths.get(dir);
		extractFilesFromDirectory();
	}

	/**
	 * Gets all the .java and .jar files beneath a directory, ignoring other files
	 * Stores them in the s
	 * @throws IOException 
	 */
	private void extractFilesFromDirectory() throws IOException {
		FilesExtractorVisitor fileVisitor = new FilesExtractorVisitor();
		Files.walkFileTree(this.selectedDirectory, fileVisitor);
		this.jarFiles = fileVisitor.getJarFilePaths();
		this.javaFiles = fileVisitor.getJavaFilePaths();
	}

	/**
	 * @return list of .jar file paths
	 */
	public List<Path> getJarFiles() {
		return jarFiles;
	}

	/**
	 * @return list of .java file paths
	 */
	public List<Path> getJavaFiles() {
		return javaFiles;
	}

	/**
	 * @return path of the selected root directory
	 */
	public Path getSelectedDirectory() {
		return selectedDirectory;
	}
	
}
