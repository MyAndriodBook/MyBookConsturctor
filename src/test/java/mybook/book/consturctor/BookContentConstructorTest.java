/**
 * 
 */
package mybook.book.consturctor;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class BookContentConstructorTest {

	private BookContentConstructor bookContentConstructor;
	String bookPath = "D:\\dev\\eclipseWorkSpace2\\book.consturctor-0.0.1-SNAPSHOT\\src\\test\\resources\\Alpha_Attraction.xml";
	String testBookPath = "D:\\dev\\eclipseWorkSpace2\\book.consturctor-0.0.1-SNAPSHOT\\src\\test\\resources\\Alpha_Attraction_test.xml";
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bookContentConstructor = new BookContentConstructor();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		bookContentConstructor = null;
	}

	@Test
	public void testSetFilePath() {
		//given
		bookContentConstructor.setInputResource(bookPath);
		String expectedPath = "D:\\dev\\eclipseWorkSpace2\\book.consturctor-0.0.1-SNAPSHOT\\src\\test\\resources\\Alpha_Attraction.xml";

		assertEquals("File path not match", expectedPath, bookContentConstructor.getPath());
		assertNotNull(bookContentConstructor.getFileInputStream());
		assertNotNull(bookContentConstructor.getInputStringBuffer());
	}
	
	@Test
	public void testGetContentOfFile(){
		bookContentConstructor.setInputResource(bookPath);
		bookContentConstructor.setLineThreadsHole(18);
		bookContentConstructor.setThreadshole(324);
		bookContentConstructor.parseInputResource();
	}
	
	@Test
	public void testIsExceedPageContentThreadsHole()
	{
		String aLine ="<P>通过对身边的女人展示较高的地位，你就会迅速变得魅力十足。事实上，如果你不论到什么地方都积极培养这种态度，女孩子们自然就会注意到你的存在。正如一句古谚所说的：“我思故我在”。</P>";
		bookContentConstructor.setThreadshole(10);
		assertFalse(bookContentConstructor.isWithInPageContentThreadsHole(aLine));
	}
	
	@Test
	public void testGetCountWithinOneLine()
	{
		String aLine ="<P>通过对身边的女人。“</P>";
		
		int expect = 18;
		int actual = bookContentConstructor.getCount(aLine);
		assertEquals(expect, actual);
		
		
	}
	@Test
	public void testGetCountExceedOneLine()
	{
		String aLine ="<P>通过对身边的女人。通过对身边的女人。通过对身边的女人。“通过对身边的女人。“</P>";
		
		int expect = 54;
		int actual = bookContentConstructor.getCount(aLine);
		assertEquals(expect, actual);
		
		
	}
	
	@Test
	public void testGetCountExceptionCases()
	{
		String aLine ="<P  align=\"center\">把妹制胜的33条规则</P>";
		
		int expect = 54;
		int actual = bookContentConstructor.getCount(aLine);
		assertEquals(expect, actual);
		
		
	}
	

}
