package de.mytoysgroup.multishop.storefinder.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import de.mytoysgroup.multishop.storefinder.entity.JsonInfos;

/**
 * A controller with RESTful endpoints mostly used by the frontend.
 *
 * @author Robin Ringleb (2017)
 */
@Component
public class JsonFileReader {
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonFileReader.class);

	private JsonInfos jsonInfos;
	private JsonReader reader;
	
	public JsonInfos readJson(String jsonfilePath) {
		
		Path path 				= Paths.get(jsonfilePath);
		GsonBuilder gsonBuilder	= new GsonBuilder();
		Gson gson 				= gsonBuilder.create();
		
		try {
			reader = new JsonReader(new FileReader(path.toString()));
		} catch (FileNotFoundException e) {
			LOG.info(e.getMessage());
		}
		jsonInfos = gson.fromJson(reader , JsonInfos.class);
		
		return jsonInfos;
	}
}