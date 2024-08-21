package com.zenken.freshers.pages.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditJobPreferencesPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditJobPreferencesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="firstPreference")
	WebElement firstPreferenceDd;
	
	@FindBy(id="firstPreferenceOther")
	WebElement firstPreferenceOtherTb;
	
	@FindBy(id="secondPreference")
	WebElement secondPreferenceDd;
	
	@FindBy(id="secondPreferenceOther")
	WebElement secondPreferenceOtherTb;
	
	@FindBy(id="thirdPreference")
	WebElement thirdPreferenceDd;
	
	@FindBy(id="thirdPreferenceOther")
	WebElement thirdPreferenceOtherTb;
	
	@FindBy(id="firstPreferenceLabel")
	WebElement firstPrefLabel;
	
	@FindBy(id="secondPreferenceLabel")
	WebElement secondPrefLabel;
	
	@FindBy(id="thirdPreferenceLabel")
	WebElement thirdPrefLabel;
	
	@FindBy(xpath="(//div)[10]")
	WebElement firstPrefBox;
	
	@FindBy(xpath="(//div)[13]")
	WebElement secondPrefBox;
	
	@FindBy(xpath="(//div)[16]")
	WebElement thirdPrefBox;
	
	@FindBy(css="[role='alert']")
	WebElement alert;
	
	@FindBy(css="[type=submit]")
	WebElement save;
	
	@FindBy(id="updateCancel")
	WebElement cancel;
	
	public List<String> getPlaceholderTexts()
	{
		List<String> texts = new ArrayList<>();
		texts.add(firstPreferenceOtherTb.getAttribute("placeholder"));
		texts.add(secondPreferenceOtherTb.getAttribute("placeholder"));
		texts.add(thirdPreferenceOtherTb.getAttribute("placeholder"));
		return texts;
	}
	
	public Map<String, Boolean> getFirstPreferenceTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(firstPreferenceDd, firstPreferenceOtherTb);
		return states;
	}
	
	public Map<String, Boolean> getSecondPreferenceTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(secondPreferenceDd, secondPreferenceOtherTb);
		return states;
	}
	
	public Map<String, Boolean> getThirdPreferenceTextBoxStates()
	{
		Map<String, Boolean> states = getDropdownTextBoxStates(thirdPreferenceDd, thirdPreferenceOtherTb);
		return states;
	}
	
	public void selectPreferences(int a, int b, int c)
	{
		selectDropdownByIndex(firstPreferenceDd, a);
		selectDropdownByIndex(secondPreferenceDd, b);
		selectDropdownByIndex(thirdPreferenceDd, c);
	}
	
	public void clickSave()
	{
		clickByJavaScript(save);
	}
	
	public void selectPreferencesOther()
	{
		selectDropdown(firstPreferenceDd, "Other");
		selectDropdown(secondPreferenceDd, "Other");
		selectDropdown(thirdPreferenceDd, "Other");
	}
	
	public void enterFirstPrefOther(String text)
	{
		firstPreferenceOtherTb.clear();
		firstPreferenceOtherTb.sendKeys(text);
	}
	
	public void enterSecondPrefOther(String text)
	{
		secondPreferenceOtherTb.clear();
		secondPreferenceOtherTb.sendKeys(text);
	}
	
	public void enterThirdPrefOther(String text)
	{
		thirdPreferenceOtherTb.clear();
		thirdPreferenceOtherTb.sendKeys(text);
	}
	
	public void deSelectPref()
	{
		selectDropdownByIndex(firstPreferenceDd, 0);
		selectDropdownByIndex(secondPreferenceDd, 0);
		selectDropdownByIndex(thirdPreferenceDd, 0);
		clickSave();
	}
	
	public List<String> getRequiredToApplyTexts()
	{
		List<String> texts = new ArrayList<>();
		texts.add(firstPrefLabel.getText());
		texts.add(secondPrefLabel.getText());
		texts.add(thirdPrefLabel.getText());
		return texts;
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancel);
	}
	
	public String getAlert()
	{
		waitUntilElementAppears(alert);
		return alert.getText();
	}
	
	public String getFirstPrefErrorText()
	{
		return firstPrefBox.getText();
	}
	
	public String getSecondPrefErrorText()
	{
		return secondPrefBox.getText();
	}
	
	public String getThirdPrefErrorText()
	{
		return thirdPrefBox.getText();
	}
}
