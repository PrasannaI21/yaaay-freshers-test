package com.zenken.freshers.pages.user;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class ApplyFormPage extends WebDriverUtils{

	WebDriver driver;
	
	public ApplyFormPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='20 u-fw']")
	WebElement midashi;
	
	@FindBy(css="[class=u-fz-14]:first-child")
	WebElement fixedText;
	
	@FindBy(css="[class*='14 u']")
	WebElement freeText;
	
	@FindBy(css="[class*='cancel']")
	WebElement cancelBt;
	
	@FindBy(id="jobEntryQuestionAnswers0")
	WebElement answer1Tb;
	
	@FindBy(css="[value=No]")
	WebElement answer3radio;
	
	@FindBy(css="[value=Java]")
	WebElement answer2radio;
	
	@FindBy(id="jobEntryQuestionAnswers3")
	WebElement answer4Tb;
	
	@FindBy(xpath="(//div[contains(@class,'gap')])[4]")
	WebElement qaBox1;
	
	@FindBy(css="[class*='field']:first-of-type")
	WebElement qaBox2;
	
	@FindBy(css="[class*='field']:last-of-type")
	WebElement qaBox3;
	
	@FindBy(xpath="(//div[contains(@class,'gap')])[7]")
	WebElement qaBox4;
	
	public String getApplyFormHeadline()
	{
		return midashi.getText();
	}
	
	public String getApplyFormFixedText()
	{
		return fixedText.getText();
	}
	
	public String getApplyFormFreeText()
	{
		return freeText.getText();
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancelBt);
	}
	
	public void enterAnswer1(String text)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value=arguments[1]", answer1Tb, text);
	}
	
	public void enterAnswer2()
	{
		clickByJavaScript(answer2radio);
	}
	
	public void enterAnswer3()
	{
		clickByJavaScript(answer3radio);
	}
	
	public void enterAnswer4(String text)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value=arguments[1]", answer4Tb, text);
	}
	
	public String getQaBox1Text()
	{
		return qaBox1.getText();
	}
	
	public String getQaBox2Text()
	{
		return qaBox2.getText();
	}
	
	public String getQaBox3Text()
	{
		return qaBox3.getText();
	}
	
	public String getQaBox4Text()
	{
		return qaBox4.getText();
	}
	
	public void resetAnswers()
	{
		String script = "document.querySelectorAll('input, textarea').forEach(el =>"
				+ "{if(el.tagName === 'TEXTAREA'){el.value = '';}"
				+ "else if(el.type === 'radio'){el.checked = false;}})";
		((JavascriptExecutor)driver).executeScript(script);
	}
}
