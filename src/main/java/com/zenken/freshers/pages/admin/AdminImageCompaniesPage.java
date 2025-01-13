package com.zenken.freshers.pages.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.zenken.frehsers.abstractcomponents.WebDriverUtils;
import com.zenken.freshers.data.DataReader;

public class AdminImageCompaniesPage extends WebDriverUtils{

	WebDriver driver;
	
	public AdminImageCompaniesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[type=file]")
	List<WebElement> upload;
	
	@FindBy(css="[type=text]")
	List<WebElement> alt;
	
	@FindBy(css="[value=保存]")
	List<WebElement> save;
	
	@FindBy(css="[value=削除]")
	WebElement delete;
	
	@FindBy(xpath="//div/div/div/img")
	List<WebElement> images;
	
	public List<String> uploadComImages(String indexValue, String indexKey) throws IOException
	{
		JsonNode json = DataReader.getDataSet(System.getProperty("user.dir")+
				"/src/main/java/com/zenken/freshers/data/image.json", indexValue, indexKey);
		JsonNode values = json.get("values");
		for(int i=0; i<upload.size(); i++) {
			WebElement image = upload.get(i);
			String path = values.get(i).asText(null);
			if(path == null) {
				continue;
			}else {
				if(path.contains("companylogo")) {
					image.sendKeys(System.getProperty("user.dir")+path);
					clickByJavaScript(save.get(i));
				}else {
					image.sendKeys(System.getProperty("user.dir")+path);
					alt.get(i-1).sendKeys("alt");
					clickByJavaScript(save.get(i));
				}
			}
		}
		List<String> imagesSrc = new ArrayList<>();
		if(!images.isEmpty()) {
			for(WebElement image : images) {
				imagesSrc.add(image.getDomAttribute("src"));
			}
		}
		return imagesSrc;
	}
	
	public void deleteComImages()
	{
		while(isElementPresent(delete)) {
			clickByJavaScript(delete);
		}
	}
}
