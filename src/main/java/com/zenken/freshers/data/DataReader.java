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

	static String propFile = System.getProperty("user.dir") + 
			"/src/main/java/com/zenken/freshers/resources/UserTexts.properties";
	
	public static JsonNode getDataSet(String filePath, String indexValue, String indexKey) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(new File(filePath));
		JsonNode datasets = rootNode.get("datasets");
		int datasetIndex = readIndex(indexValue);
		JsonNode dataset = datasets.get(datasetIndex % datasets.size());
		datasetIndex++;
		saveIndex(datasetIndex, indexKey);
		return dataset;
	}
	
	public static int readIndex(String indexValue) throws IOException
	{
		InputStream inputStream = new FileInputStream(propFile);
		Properties properties = new Properties();
		properties.load(inputStream);
		return Integer.parseInt(indexValue);
	}
	
	public static void saveIndex(int index, String indexKey) throws IOException
	{
		InputStream inputStream = new FileInputStream(propFile);
		Properties properties = new Properties();
		properties.load(inputStream);
		OutputStream outputStream = new FileOutputStream(propFile);
		properties.setProperty(indexKey, String.valueOf(index));
		properties.store(outputStream, null);
	}
}
