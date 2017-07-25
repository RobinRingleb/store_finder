package de.mytoysgroup.multishop.storefinder.service;

import org.springframework.stereotype.Service;

import com.maxmind.geoip2.record.Location;

import de.mytoysgroup.multishop.storefinder.entity.Branches;
import de.mytoysgroup.multishop.storefinder.entity.JsonInfos;

@Service
public class NearestStoreFinder {	
	
	public String getNearestStore(Location location, JsonInfos jsonData) {
		
		Branches[] br 		= jsonData.getBranches();
		double[] entfernung = new double[br.length];
		int minIndex 		= 0;
		
		for (int i = 0; i < br.length; i++) {
			entfernung[i] = Math.sqrt(Math.pow(Math.abs(location.getLatitude() - br[i].getLatitude()), 2) + 
					(Math.pow(Math.abs(location.getLongitude() - br[i].getLongitude()), 2)));
			if (entfernung[i] < entfernung[minIndex]) {
				minIndex = i;
			}
		}
		
		return br[minIndex].getName();
	}
}