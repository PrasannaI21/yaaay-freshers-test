package com.zenken.freshers.pages.company;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class PriorityListPage extends WebDriverUtils{

	WebDriver driver;
	
	public PriorityListPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[id*='selectPriorities']")
	List<WebElement> jobDd;
	
	@FindBy(css="[role=dialog]")
	WebElement screenPopup;
	
	@FindBy(css="[form*='compl']")
	WebElement scrEndPopupBt;
	
	public void enterPriority(String pr1, String pr2)
	{
		jobDd.get(0).clear();
		jobDd.get(0).sendKeys(pr1);
		jobDd.get(1).clear();
		jobDd.get(1).sendKeys(pr2);
	}
	
	public String getScreenPopup()
	{
		waitUntilElementAppears(screenPopup);
		return screenPopup.getText();
	}
	
	public void clickScrEndBt()
	{
		clickByJavaScript(scrEndPopupBt);
	}
	
}
