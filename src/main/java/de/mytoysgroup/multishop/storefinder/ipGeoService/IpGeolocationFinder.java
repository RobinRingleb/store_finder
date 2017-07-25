package de.mytoysgroup.multishop.storefinder.ipGeoService;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.Location;

import de.mytoysgroup.multishop.storefinder.configuration.ApplicationConfiguration;
import de.mytoysgroup.multishop.storefinder.datalayer.LocationDatabase;
import de.mytoysgroup.multishop.storefinder.entity.JsonInfos;
import de.mytoysgroup.multishop.storefinder.service.NearestStoreFinder;

@SpringBootApplication
public class IpGeolocationFinder {
	
	private static final Logger LOG = LoggerFactory.getLogger(IpGeolocationFinder.class);

	ApplicationConfiguration applicationConfiguration;
		
	public IpGeolocationFinder(ApplicationConfiguration applicationConfiguration) {
		this.applicationConfiguration = applicationConfiguration;
	}

	public String getLocation(String IpAdress, JsonInfos jsonData) {
		
		try {
			String dbPath 		= applicationConfiguration.getGeoDataBasePath();
			Location location 	= LocationDatabase.getStoreLocation(IpAdress, dbPath);
			
			NearestStoreFinder storeFinder 	= new NearestStoreFinder();
	    	String nearestStore 			= storeFinder.getNearestStore(location, jsonData);
	    	
	    	return "Die n&aumlchste Filiale befindet sich in " + nearestStore + ".";
		} catch (IOException | GeoIp2Exception e) {			
			LOG.info(e.getMessage());
			return "0";
		}
	}
}
