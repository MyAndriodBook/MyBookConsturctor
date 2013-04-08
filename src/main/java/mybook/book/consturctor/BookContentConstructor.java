/**
 * 
 */
package mybook.book.consturctor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Administrator
 * 
 */
public class BookContentConstructor implements IBookContentConstructor {

	private String path;
	private FileInputStream fileInputStream;
	private BufferedReader inputStringBuffer;
	private StringBuffer outputContent;

	private int threadsHole = 324;
	private int tempThreadsHole = threadsHole;
	private int lineThreadsHole = 18;
	private int pageCount = 1;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getLineThreadsHole() {
		return lineThreadsHole;
	}

	public void setLineThreadsHole(int lineThreadsHole) {
		this.lineThreadsHole = lineThreadsHole;
	}

	private final String newLine = "\n";
	private final String paragraBegin = "<P>";
	private final String paragraEnd = "</P>";
	private final String ignoreString_alignCenter = "align=\"center\"";
	private final String ignoreString_listBegin = "<LI>";
	private final String ignoreString_listEnd = "</LI>";
	private final String ignoreString_listTitleBegine = "<LI_Title>";
	private final String ignoreString_listTitleEnd = "</LI_Title>";

	private final String newPageIndicator = "<P align=\"center\">";

	private final String pageHeader = "<div class=\"item\">";
	private final String pageFooter = "</div>";

	public int getThreadshole() {
		return threadsHole;
	}

	public void setThreadshole(int threadshole) {
		this.threadsHole = threadshole;
		this.tempThreadsHole = this.threadsHole;
	}

	public BufferedReader getInputStringBuffer() {
		return inputStringBuffer;
	}

	public void setInputStringBuffer(BufferedReader inputStringBuffer) {
		this.inputStringBuffer = inputStringBuffer;
	}

	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mybook.book.consturctor.IBookContentConstructor#readFromFile(java.lang
	 * .String)
	 */
	public void setInputResource(String path) {
		this.path = path;
		try {
			fileInputStream = new FileInputStream(new File(getPath()));
			inputStringBuffer = new BufferedReader(new InputStreamReader(
					fileInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mybook.book.consturctor.IBookContentConstructor#getContentOfFile()
	 */
	public void parseInputResource() {

		outputContent = new StringBuffer();
		tempThreadsHole = threadsHole;
		String aLine = null;
		try {
			pageContentBuilder(outputContent, pageHeader);
			while ((aLine = inputStringBuffer.readLine()) != null) {
				if (aLine.trim().length() != 0) {
					if (isWithInPageContentThreadsHole(aLine)) {
						pageContentBuilder(outputContent, aLine);
					} else {
						pageContentBuilder(outputContent,
								"<p align=\"center\"> - " + pageCount + " <"
										+ tempThreadsHole + "> -</p>");
						pageContentBuilder(outputContent, pageFooter);
						pageContentBuilder(outputContent, pageHeader);
						pageContentBuilder(outputContent, aLine);
						if (aLine.contains(newPageIndicator)) {
							tempThreadsHole = threadsHole
									- (lineThreadsHole * 2);
						} else {
							tempThreadsHole = threadsHole;
						}

						pageCount++;
					}

				}
			}
			pageContentBuilder(outputContent, "<p align=\"center\"> - "
					+ pageCount + " -</p>");
			pageContentBuilder(outputContent, pageFooter);
			inputStringBuffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(outputContent.toString());
	}

	private void pageContentBuilder(StringBuffer outputContent, String aLine) {
		outputContent.append(aLine);
		outputContent.append(newLine);
	}

	public boolean isWithInPageContentThreadsHole(String aLine) {
		int lineCount = 0;
		if (aLine.contains(newPageIndicator)) {
			return false;
		}

		if (aLine.startsWith("<div") || aLine.startsWith("</div")) {
			return true;
		}

		lineCount = getCount(aLine);

		if (lineCount > tempThreadsHole) {
			return false;
		} else {
			if (aLine.endsWith(paragraEnd)) {
				tempThreadsHole = tempThreadsHole - lineThreadsHole - lineCount;
			} else {
				tempThreadsHole = tempThreadsHole - lineCount;
			}

			return tempThreadsHole > 0;
		}

	}

	public int getCount(String aLine) {
		String tempString = aLine;
		tempString = tempString.replaceAll(paragraBegin, "");
		tempString = tempString.replaceAll(paragraEnd, "");
		tempString = tempString.replaceAll(ignoreString_alignCenter, "");
		tempString = tempString.replaceAll(ignoreString_listBegin, "");
		tempString = tempString.replaceAll(ignoreString_listEnd, "");
		tempString = tempString.replaceAll(ignoreString_listTitleBegine, "");
		tempString = tempString.replaceAll(ignoreString_listTitleEnd, "");
		tempString.trim();

		int count = 0;
		int length = tempString.length();
		int lines = length / lineThreadsHole;
		if (length != 0) {
			if (length % lineThreadsHole == 0) {
				count = lines * lineThreadsHole;
			} else {
				count = lines * lineThreadsHole + lineThreadsHole;
			}
		}
		return count;
	}

}
