package com.zenken.freshers.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class DataReader {

//	private static final String FILE_PATH = System.getProperty("user.dir")+
//			"/src/test/java/com/zenken/freshers/data/jd.json";
	static String propFile = System.getProperty("user.dir") + 
			"/src/main/java/com/zenken/freshers/resources/UserTexts.properties";
	
	public static JsonNode getDataSet(String filePath) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(new File(filePath));
		JsonNode datasets = rootNode.get("datasets");
		int datasetIndex = readIndex();
		JsonNode dataset = datasets.get(datasetIndex % datasets.size());
		datasetIndex++;
		saveIndex(datasetIndex);
		return dataset;
	}
	
	public static int readIndex() throws IOException
	{
		InputStream inputStream = new FileInputStream(propFile);
		Properties properties = new Properties();
		properties.load(inputStream);
		return Integer.parseInt(properties.getProperty("datasetIndex"));
	}
	
	public static void saveIndex(int index) throws IOException
	{
		InputStream inputStream = new FileInputStream(propFile);
		Properties properties = new Properties();
		properties.load(inputStream);
		OutputStream outputStream = new FileOutputStream(propFile);
		properties.setProperty("datasetIndex", String.valueOf(index));
		properties.store(outputStream, null);
	}
}
