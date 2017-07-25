package de.mytoysgroup.multishop.storefinder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import de.mytoysgroup.multishop.storefinder.configuration.ApplicationConfiguration;
import de.mytoysgroup.multishop.storefinder.controller.JsonFileReader;
import de.mytoysgroup.multishop.storefinder.entity.JsonInfos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class JsonFileReaderTest {
	private JsonInfos jsonData = new JsonInfos();
	private JsonFileReader reader = new JsonFileReader();
	
	@Autowired
	ApplicationConfiguration applicationConfiguration;
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testReadJsonWithHeadline() throws Exception {
		jsonData = reader.readJson(applicationConfiguration.getJsonFilePath().toString());
		assertEquals("Unsere Filialen", jsonData.getHeadline());
	}
	
	@Test(expected=NullPointerException.class)
	public void testReadJsonWithEmptyJsonPath() throws Exception {
		jsonData = reader.readJson("");
		fail(jsonData.getHeadline());
	}
	
	@Test(expected=NullPointerException.class)
	public void testReadJsonWithInvalidJsonPath() throws Exception {
		jsonData = reader.readJson("json/foo.json");
		fail(jsonData.getHeadline());
	}
}
