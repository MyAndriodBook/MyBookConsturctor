/**
 * 
 */
package mybook.book.consturctor;

import java.io.FileInputStream;

/**
 * @author Administrator
 *
 */
public interface IBookContentConstructor {

	public void setInputResource(String path);
	
	public void parseInputResource();
	
	public String getPath();
	
	public void setPath(String path);
	
	public FileInputStream  getFileInputStream();
	
	public void setFileInputStream(FileInputStream fw);
	
}
