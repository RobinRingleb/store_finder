package de.mytoysgroup.multishop.storefinder.controller;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.mytoysgroup.multishop.storefinder.configuration.ApplicationConfiguration;
import de.mytoysgroup.multishop.storefinder.entity.JsonInfos;
import de.mytoysgroup.multishop.storefinder.ipGeoService.IpGeolocationFinder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A controller with RESTful endpoints mostly used by the frontend.
 *
 * @author Robin Ringleb (2017)
 */
@Controller
@Api(value = "storefinder")
@SpringBootApplication
public class StoreFinderController {
	
	@Autowired
	ApplicationConfiguration applicationConfiguration;
	
	private HashMap<String, String> cache = new HashMap<String, String>();
	
	/**
	 * Rest endpoint returning a freemarker template.
	 *
	 * @return a freemarker template
	 */
	@ApiOperation(value = "Returns a message including the specified text")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal server error, e.g. something went wrong with the DB query.") })
	
	@RequestMapping(method = RequestMethod.GET, value = "/", produces = TEXT_PLAIN_VALUE + ";charset=UTF-8")
	public String getStoreFinder(
			Model model, 
			@ApiParam(name = "ipAddress", value = "IP Address of the visitor") 
			@RequestParam(value = "ipAddress", required = false, defaultValue = "!") final String ipAddress) {
		
			IpGeolocationFinder finder 	= new IpGeolocationFinder(applicationConfiguration);
			JsonInfos jsonData 			= new JsonInfos();
			JsonFileReader reader 		= new JsonFileReader();
			jsonData 					= reader.readJson(applicationConfiguration.getJsonFilePath());
			
			// Add attributes to the view model
			model.addAttribute("headline", jsonData.getHeadline());
			model.addAttribute("info", jsonData.getInfo());
			model.addAttribute("branches", jsonData.getBranches());
			
			if (ipAddress != null && !ipAddress.isEmpty()) {
				if (cache.containsKey(ipAddress)) {
					model.addAttribute("location", cache.get(ipAddress));
				} else {
					cache.put(ipAddress, finder.getLocation(ipAddress, jsonData));
					model.addAttribute("location", cache.get(ipAddress));
				}
			} else {
				model.addAttribute("location", "0");
			}

		return "storefinder";
	}
}

// storefinder.ftl Zeile 15: "mytoys.de" entfernen
