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

public class AdminEditJobsPage extends WebDriverUtils {

	WebDriver driver;
	
	public AdminEditJobsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[name=jobTitle]")
	WebElement jobTitleDd;
	
	@FindBy(id="jobTitleDetails")
	WebElement jobTitleDetailsTb;

	@FindBy(css="[type=radio]")
	WebElement radios;

	@FindBy(css="select, input, textarea")
	List<WebElement> jobUpdate;
	
	public JsonNode updateJob() throws IOException
	{
		JsonNode jsonObj = DataReader.getDataSet(System.getProperty("user.dir")+
				"/src/main/java/com/zenken/freshers/data/jd.json");
		JsonNode values = jsonObj.get("values");
		List<WebElement> elements = jobUpdate.stream().filter(e -> e.isDisplayed() && e.getDomAttribute("id") != null)
				.collect(Collectors.toList());
		for(int i=0; i<elements.size(); i++) {
			WebElement element = elements.get(i);
			String value = values.get(i).asText(null);
			if(value == null) {
				continue;
			}
			switch(element.getDomAttribute("class")) {
			case "uk-select " :
				selectDropdown(element, value);
				break;
			case "uk-input " :
			case "uk-textarea " :
			case "u-w-320 uk-input " :
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].value=arguments[1]", element, value);
				break;
			case "uk-radio" :
			case "uk-checkbox" :
				if(value.equals("true")) {
					clickByJavaScript(element);
				}
			}			
		}
		clickSave();
		return values;	
	}
	
	public void restoreJob()
	{
		String script = "document.querySelectorAll('input, textarea').forEach(el => {"
				+ "if(el.className === 'uk-input ' || el.tagName === 'TEXTAREA' || el.id === 'locationOther'){"
				+ "el.value = '';}"
				+ "else if(el.type === 'checkbox' || el.type === 'radio'){"
				+ "el.checked = false;}})";
		((JavascriptExecutor)driver).executeScript(script);
	}
	
}
