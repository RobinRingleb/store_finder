package de.mytoysgroup.multishop.storefinder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.mytoysgroup.multishop.storefinder.configuration.ApplicationConfiguration;
import de.mytoysgroup.multishop.storefinder.controller.JsonFileReader;
import de.mytoysgroup.multishop.storefinder.entity.JsonInfos;
import de.mytoysgroup.multishop.storefinder.ipGeoService.IpGeolocationFinder;

/**
 * Integration tests for REST endpoints of {@link de.mytoysgroup.multishop.storefinder.ipGeoService.IpGeolocationFinder}
 * <p>
 * Hint: Good introduction into testing with Spring Boot 1.4
 * https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
 *
 * @author Robin Ringleb (2017)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IpGeolocationFinderTest {
	private JsonInfos jsonData = new JsonInfos();
	private HashMap<String, String> cache = new HashMap<String, String>();
	
	@Autowired
	ApplicationConfiguration applicationConfiguration;
	
	@Autowired
	IpGeolocationFinder ipGeolocationFinder;

	@Before
	public void setup() {
		JsonFileReader reader = new JsonFileReader();
		jsonData = reader.readJson(applicationConfiguration.getJsonFilePath().toString());
		cache.put("128.101.101.101", ipGeolocationFinder.getLocation("128.101.101.101", jsonData));
		cache.put("87.123.151.24", ipGeolocationFinder.getLocation("87.123.151.24", jsonData));
	}

	@Test
	public void testGetLocationWithInvalidIp() throws Exception {
		assertEquals("0", ipGeolocationFinder.getLocation("0", jsonData));
	}
	
	@Test
	public void testGetLocationWithEmptyIp() throws Exception {
		assertEquals("0", ipGeolocationFinder.getLocation("", jsonData));
	}
	
	@Test
	public void testGetLocationWithValidIpInGermany() throws Exception {
		assertEquals("Die n&aumlchste Filiale befindet sich in K&oumlln-Chorweiler.", 
				ipGeolocationFinder.getLocation("87.123.151.24", jsonData));
	}
	
	@Test
	public void testGetLocationWithValidIpInGermanyWithCache() throws Exception {
		assertEquals("Die n&aumlchste Filiale befindet sich in K&oumlln-Chorweiler.", 
				cache.get("87.123.151.24"));
	}
	
	@Test
	public void testGetLocationWithValidIpInUsa() throws Exception {
		assertEquals("Die n&aumlchste Filiale befindet sich in Aachen.", 
				ipGeolocationFinder.getLocation("128.101.101.101", jsonData));
	}
	
	@Test
	public void testGetLocationWithValidIpInUsaWithCache() throws Exception {
		assertEquals("Die n&aumlchste Filiale befindet sich in Aachen.", cache.get("128.101.101.101"));
	}
	
	@Test
	public void testGetLocationWithPrivateIp() throws Exception {
		assertEquals("0", ipGeolocationFinder.getLocation("192.168.2.1", jsonData));
	}
	
	@Test
	public void testGetLocationWithIncompleteIp() throws Exception {
		assertEquals("0", ipGeolocationFinder.getLocation("192.168.2.", jsonData));	
	}
		
	@Test
	public void testGetLocationWithText() throws Exception {
		assertEquals("0", ipGeolocationFinder.getLocation("hello", jsonData));	
	}	
	
}