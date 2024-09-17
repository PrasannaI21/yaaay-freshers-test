package com.zenken.freshers.pages.user;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class EditSkillsPage extends WebDriverUtils{

	WebDriver driver;
	
	public EditSkillsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*=black]")
	WebElement headline;
	
	@FindBy(id="skillsLabel")
	WebElement label;
	
	@FindBy(id="skillInput")
	WebElement skillsTb;
	
	@FindBy(css="[class*='position']")
	WebElement skillsTbBox;
	
	@FindBy(xpath="//ul[contains(@class,'list')]/li")
	List<WebElement> skillOptions;
	
	@FindBy(css="[class*='15']")
	List<WebElement> skillLabels;
	
	@FindBy(css="select[x-model='skill[1]']")
	WebElement skillLevelDd;
	
	@FindBy(css="[alt*='Delete']")
	List<WebElement> skillDelete;
	
	@FindBy(css="div[class*='u-mt-30']")
	WebElement skillBox;
	
	@FindBy(css="[type=submit]")
	WebElement save;
	
	@FindBy(id="updateCancel")
	WebElement cancel;
	
	public String getHeadlineText()
	{
		return headline.getText();
	}
	
	public String getLabelText()
	{
		return label.getText();
	}
	
	public String getSkillsPlaceholder()
	{
		return skillsTb.getAttribute("placeholder");
	}
	
	public void enterSkills(String skill)
	{
		skillsTb.clear();
		skillsTb.click();
		skillsTb.sendKeys(skill);
	}
	
	public List<WebElement> getOptionsDisplayed()
	{
		return skillOptions;
	}
	
	public List<WebElement> getSkillLabels()
	{
		return skillLabels;
	}
	
	public void selectSkillLevel(int index)
	{
		selectDropdownByIndex(skillLevelDd, index);
	}
	
	public void addSkill(String skill, int index, int level)
	{
		enterSkills(skill);
		getOptionsDisplayed().get(index).click();
		selectSkillLevel(level);
	}
	
	public void clickSave()
	{
		clickByJavaScript(save);
	}
	
	public void deleteSkill(int index)
	{
		clickByJavaScript(skillDelete.get(index));
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancel);
	}
	
	public String getSkillBoxText()
	{
		return skillBox.getText();
	}
	
	public boolean isSkillTextBoxActive()
	{
		return skillsTb.isEnabled();
	}
	
	public String getSkillBoxErrorText()
	{
		return skillsTbBox.getText();
	}
	
	public List<WebElement> getDeleteIcons()
	{
		return skillDelete;
	}
}
