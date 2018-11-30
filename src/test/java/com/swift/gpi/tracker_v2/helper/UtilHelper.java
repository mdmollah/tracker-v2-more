package com.swift.gpi.tracker_v2.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class UtilHelper {

	Properties properties;
	public HashMap<String, String> mymap;

	private static UtilHelper single_instance = null;

	private UtilHelper() {

	}

	public static UtilHelper getInstance() throws NoSuchAlgorithmException, IOException {
		if (single_instance == null) {
			single_instance = new UtilHelper();
			single_instance.loadProperties();
		}
		return single_instance;
	}

	public void loadProperties() throws IOException, NoSuchAlgorithmException {
		String filename = "main.properties";
		InputStream st = this.getClass().getResourceAsStream("/" + filename);
		properties = new Properties();
		properties.load(st);
		mymap = new HashMap<String, String>();
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			mymap.put(key, value);
			// System.out.println(key + " " + value);
		}
	}
}
