package de.mytoysgroup.multishop.storefinder.datalayer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

public class LocationDatabase {
	
	public static Location getStoreLocation(String IpAdress, String dbPath)
			throws IOException, UnknownHostException, GeoIp2Exception {
		
		Path path 				= Paths.get(dbPath);
		File database 			= new File(path.toString());
		DatabaseReader reader 	= new DatabaseReader.Builder(database).build();
		InetAddress ipAddress 	= InetAddress.getByName(IpAdress);
		CityResponse response 	= reader.city(ipAddress);
		Location location 		= response.getLocation();
		
		return location;
	}
}
