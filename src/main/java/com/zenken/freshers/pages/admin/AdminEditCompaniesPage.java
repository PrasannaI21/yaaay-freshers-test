package com.zenken.freshers.pages.admin;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.zenken.frehsers.abstractcomponents.WebDriverUtils;
import com.zenken.freshers.data.DataReader;

public class AdminEditCompaniesPage extends WebDriverUtils{

	WebDriver driver;
	
	public AdminEditCompaniesPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="select, input, textarea")
	List<WebElement> elements;
	
	public JsonNode updateCompany(String indexValue, String indexKey) throws IOException
	{
		JsonNode jsonObj = DataReader.getDataSet(System.getProperty("user.dir")+
				"/src/main/java/com/zenken/freshers/data/companies.json", indexValue, indexKey);
		JsonNode values = jsonObj.get("values");
		List<WebElement> eles = elements.stream().filter(e -> e.isDisplayed() && e.getDomAttribute("id") != null)
		.collect(Collectors.toList());
		for(int i=0; i<eles.size(); i++) {
			WebElement ele = eles.get(i);
			String value = values.get(i).asText(null);
			if(value == null) {
				continue;
			}
			switch(ele.getDomAttribute("class")) {
			case "uk-input " :
			case "uk-textarea " :
			case "u-w-320 uk-input " :
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].value=arguments[1]", ele, value);
				break;
			case "uk-select " :
				selectDropdown(ele, value);
				break;
			case "uk-checkbox" : 
				if(value.equals("true")) {
					clickByJavaScript(ele);
				}
			}
		}
		clickSave();
		return values;
	}
	
	public void restoreCompany()
	{
		String script = "document.querySelectorAll('input, textarea').forEach(el => {"
				+ "if(el.className === 'uk-input ' || el.tagName === 'TEXTAREA' || el.id === 'officialLanguageOther'){"
				+ "el.value = '';}"
				+ "else if(el.type === 'checkbox'){"
				+ "el.checked = false;}})";
		((JavascriptExecutor)driver).executeScript(script);
	}
}
