package com.zenken.freshers.pages.user;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditFieldOfStudyPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditFieldOfStudyPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*=black]")
	WebElement headline;
	
	@FindBy(xpath="(//div)[10]")
	WebElement researchBox;
	
	@FindBy(id="mainAreaOfResearchAndStudy")
	WebElement fieldDd;
	
	@FindBy(xpath="(//div[(@class='form-check form-check-inline')])[1]")
	WebElement coreBox;
	
	@FindBy(xpath="(//div[(@class='form-check form-check-inline')])[2]")
	WebElement itBox;
	
	@FindBy(xpath="(//div[@class='u-mt-30'])[2]")
	WebElement coreOtherBox;
	
	@FindBy(xpath="(//div[@class='u-mt-30'])[4]")
	WebElement itOtherBox;
	
	@FindBy(id="coreFieldOther")
	WebElement coreOtherTb;
	
	@FindBy(id="itFieldOther")
	WebElement itOtherTb;
	
	@FindBy(css="[name=coreField]")
	List<WebElement> coreOptions;
	
	@FindBy(css="[name=itField]")
	List<WebElement> itOptions;
	
	@FindBy(css="[type=submit]")
	WebElement save;
	
	@FindBy(id="updateCancel")
	WebElement cancel;
	
	public String getHeadlineText()
	{
		String text = headline.getText();
		return text;
	}
	
	public void selectFieldDropdown(int index)
	{
		selectDropdownByIndex(fieldDd, index);
	}
	
	public String getCoreOptionsTexts()
	{
		return coreBox.getText();
	}
	
	public String getCoreOtherPlaceholder()
	{
		return coreOtherTb.getDomProperty("placeholder");
	}
	
	public String getITOptionsTexts()
	{
		return itBox.getText();
	}
	
	public String getITOtherPlaceholder()
	{
		return itOtherTb.getDomProperty("placeholder");
	}
	
	public List<Boolean> getCoreRadioTextBoxStates()
	{
		List<Boolean> states = new ArrayList<>();
		for(int i=0; i<coreOptions.size(); i++)
		{
			clickByJavaScript(coreOptions.get(i));
			boolean state = coreOtherTb.isEnabled();
			states.add(state);
		}
		return states;
	}
	
	public List<Boolean> getITRadioTextBoxStates()
	{
		List<Boolean> states = new ArrayList<>();
		for(int i=0; i<itOptions.size(); i++)
		{
			clickByJavaScript(itOptions.get(i));
			boolean state = itOtherTb.isEnabled();
			states.add(state);
		}
		return states;
	}
	
	public void selectCoreOption(int i)
	{
		clickByJavaScript(coreOptions.get(i));
	}
	
	public void selectITOption(int i)
	{
		clickByJavaScript(itOptions.get(i));
	}
	
	public void clickSave()
	{
		clickByJavaScript(save);
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancel);
	}
	
	public void enterCoreOther(String text)
	{
		selectFieldDropdown(1);
		clickByJavaScript(coreOptions.get(7));
		coreOtherTb.clear();
		coreOtherTb.sendKeys(text);
	}
	
	public void enterITOther(String text)
	{
		selectFieldDropdown(2);
		clickByJavaScript(itOptions.get(12));
		itOtherTb.clear();
		itOtherTb.sendKeys(text);
	}
	
	public String getResearchBoxText()
	{
		return researchBox.getText();
	}
	
	public String getCoreOtherBoxText()
	{
		return coreOtherBox.getText();
	}
	
	public String getITOtherBoxText()
	{
		return itOtherBox.getText();
	}
	
}
