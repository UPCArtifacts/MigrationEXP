package fr.uphf.AndroidAnalyzer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.junit.Test;

import spoon.reflect.declaration.CtClass;

import static org.junit.Assert.*;

public class AndroidParserTest {

	@Test
	public void testFragment() throws ParseException, IOException {
		File file = new File("./examples/ArticleFragment.java");


		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("isFragment"), "1");
		assertEquals(result.get("isView"), "1");
		assertEquals(result.get("hasAndroidCoupling"), "1");
		assertEquals(result.get("methodsCoupled"), "2");
		assertEquals(result.get("parametersCoupled"), "4");
		assertEquals(result.get("returnsCoupled"), "1");

	}

	@Test
	public void testAndroidMethod() throws ParseException, IOException {
		File file = new File("./examples/PaginatedRecyclerViewAdapter.java");

		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("hasAndroidCoupling"), "1");
		assertEquals(result.get("methodsCoupled"), "3");
		assertEquals(result.get("parametersCoupled"), "3");
		assertEquals(result.get("returnsCoupled"), "0");
	}

	@Test
	public void testBroadcastReceiver() throws ParseException, IOException {
		File file = new File("./examples/ConnectionReceiver.java");

		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "1");
		assertEquals(result.get("isBroadcastReceiver"), "1");
		assertEquals(result.get("methodsCoupled"), "1");
		assertEquals(result.get("parametersCoupled"), "2");
		assertEquals(result.get("returnsCoupled"), "0");
	}

	@Test
	public void testContentProvider() throws ParseException, IOException {
		File file = new File("./examples/CountryProvider.java");

		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "1");
		assertEquals(result.get("isContentProvider"), "1");
		assertEquals(result.get("methodsCoupled"), "6");
		assertEquals(result.get("parametersCoupled"), "7");
		assertEquals(result.get("returnsCoupled"), "3");
	}

	@Test
	public void testService() throws ParseException, IOException {
		File file = new File("./examples/ClassicService.java");

		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "1");
		assertEquals(result.get("isService"), "1");
		assertEquals(result.get("methodsCoupled"), "3");
		assertEquals(result.get("parametersCoupled"), "3");
		assertEquals(result.get("returnsCoupled"), "1");
	}


	@Test
	public void testActivity() throws ParseException, IOException {
		File file = new File("./examples/MainActivity.java");

		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "1");
		assertEquals(result.get("isActivity"), "1");
		assertEquals(result.get("methodsCoupled"), "1");
		assertEquals(result.get("parametersCoupled"), "1");
		assertEquals(result.get("returnsCoupled"), "0");
	}

	@Test
	public void testEnum() throws ParseException, IOException {
		File file = new File("./examples/Day.java");
		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "0");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("isActivity"), "0");
		assertEquals(result.get("methodsCoupled"), "0");
		assertEquals(result.get("parametersCoupled"), "0");
		assertEquals(result.get("returnsCoupled"), "0");
	}

	@Test
	public void testInterface() throws ParseException, IOException {
		File file = new File("./examples/Store.java");
		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "0");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("isActivity"), "0");
		assertEquals(result.get("methodsCoupled"), "0");
		assertEquals(result.get("parametersCoupled"), "0");
		assertEquals(result.get("returnsCoupled"), "0");
	}

	@Test
	public void testHasMethodCausingNullPointer() throws IOException, ParseException {

		AndroidParser parser = new AndroidParser();
		parser.parse(new File("./examples/VectorRoomSettingsFragment.java"));

		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("isActivity"), "0");
		assertEquals(result.get("methodsCoupled"), "0"); //There are 4 methods, but Spoon fails
		assertEquals(result.get("parametersCoupled"), "0");
		assertEquals(result.get("returnsCoupled"), "0");


	}

	@Test
	public void testComplexActivity() throws ParseException, IOException {
		File file = new File("./examples/BrowserActivity.java");


		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "0");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("isFragment"), "0");
		assertEquals(result.get("isView"), "0");
		assertEquals(result.get("hasAndroidCoupling"), "1");
		assertEquals(result.get("returnsCoupled"), "2");
		assertEquals(result.get("parametersCoupled"), "27");
		assertEquals(result.get("methodsCoupled"), "26");

	}

	@Test
	public void testMultipleFileOneInstance() throws ParseException, IOException {
		File file = new File("./examples/BrowserActivity.java");

		AndroidParser parser = new AndroidParser();
		parser.parse(file);
		HashMap<String, String> result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "0");
		assertEquals(result.get("isBuildingBlock"), "0");
		assertEquals(result.get("isFragment"), "0");
		assertEquals(result.get("isView"), "0");
		assertEquals(result.get("hasAndroidCoupling"), "1");
		assertEquals(result.get("returnsCoupled"), "2");
		assertEquals(result.get("parametersCoupled"), "27");
		assertEquals(result.get("methodsCoupled"), "26");

		file = new File("./examples/ConnectionReceiver.java");

		parser.parse(file);
		result = parser.getResult();

		assertEquals(result.get("isInAndroidHierarchy"), "1");
		assertEquals(result.get("isBuildingBlock"), "1");
		assertEquals(result.get("isBroadcastReceiver"), "1");
		assertEquals(result.get("methodsCoupled"), "1");
		assertEquals(result.get("parametersCoupled"), "2");
		assertEquals(result.get("returnsCoupled"), "0");

	}

	@Test(expected = ParseException.class)
	public void testParseFail() throws ParseException, IOException {

		AndroidParser p = new AndroidParser();
		p.parse(new File("./examples/ListActivity.java"));

	}

	@Test(expected = ParseException.class)
	public void testParseFailNoClassDeclared() throws ParseException, IOException {

		AndroidParser p = new AndroidParser();
		p.parse(new File("./examples/package-info.java"));

	}


	@Test(expected = ParseException.class)
	public void testParseIndexOutOfBounds() throws ParseException, IOException {

		AndroidParser p = new AndroidParser();
		p.parse(new File("./examples/NetworkBoundResource.java"));

	}

	@Test(expected = ParseException.class)
	public void testPackageInvalidChar$() throws ParseException, IOException {

		AndroidParser p = new AndroidParser();
		p.parse(new File("./examples/AppHttlMethods.java"));

	}

	@Test(expected = ParseException.class)
	public void testPackageInvalidChar$2() throws ParseException, IOException {

		AndroidParser p = new AndroidParser();
		p.parse(new File("./examples/AppModule.java"));
	}

	@Test(expected = ParseException.class)
	public void testModuleFile() throws ParseException, IOException {

		AndroidParser p = new AndroidParser();
		p.parse(new File("./examples/module-info.java"));
	}


}
