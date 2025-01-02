package com.zenken.freshers.pages.user;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class JobsPage extends WebDriverUtils{

	WebDriver driver;
	
	public JobsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".u-ml-2")
	WebElement vacancies;
	
	@FindBy(css="h1[class*='fz-20']")
	WebElement jobTitle;
	
	@FindBy(css="p[class*='20 u-ws']")
	WebElement jobDesc;
	
	@FindBy(xpath="//h3[.='Details']/following-sibling::p")
	WebElement details;
	
	@FindBy(css="ul[class*='wrap']")
	List<WebElement> skills;
	
	@FindBy(xpath="//h3[contains(.,'d Skills')]/following-sibling::p")
	WebElement reqSkillDet;
	
	@FindBy(xpath="//h3[contains(.,'Soft')]/following-sibling::p")
	WebElement softSkills;
	
	@FindBy(css="[class*='card']:nth-child(5) [class='u-mt-10 u-ws-pw']")
	List<WebElement> conditions;
	
	@FindBy(xpath="//h3[contains(.,'Prob')]/following-sibling::p")
	WebElement probationPeriod;
	
	@FindBy(xpath="//h3[contains(.,'Loc')]/following-sibling::p")
	WebElement location;
	
	@FindBy(xpath="//h3[contains(.,'Reloc')]/following-sibling::p")
	WebElement relocation;
	
	@FindBy(xpath="//h3[contains(.,'Smok')]/following-sibling::p")
	WebElement measuresAgainstPassiveSmoking;
	
	@FindBy(xpath="//h3[contains(.,'after')]/following-sibling::p")
	WebElement remoteWorkAfterJoining;
	
	@FindBy(xpath="(//h3[contains(.,'before')]/following-sibling::p)[2]")
	WebElement remoteWorkBeforeJoining;
	
	@FindBy(xpath="//h3[contains(.,'Month')]/following-sibling::p")
	WebElement monthlySalary;
	
	@FindBy(xpath="(//h3[contains(.,'Annual')]/following-sibling::p)[1]")
	WebElement annualSalary;
	
	@FindBy(xpath="(//h3[contains(.,'Annual')]/following-sibling::p)[2]")
	WebElement annualSalaryDetails;
	
	@FindBy(xpath="(//h3[contains(.,'CTC')]/following-sibling::p)[1]")
	WebElement ctc;
	
	@FindBy(xpath="(//h3[contains(.,'CTC')]/following-sibling::p)[2]")
	WebElement ctcDetails;
	
	@FindBy(xpath="//h3[contains(.,'Bonus')]/following-sibling::p")
	WebElement bonus;
	
	@FindBy(xpath="//h3[contains(.,'Raise')]/following-sibling::p")
	WebElement raise;
	
	@FindBy(xpath="//h3[contains(.,'Pay')]/following-sibling::p")
	WebElement payroll;
	
	@FindBy(xpath="//h3[contains(.,'Days')]/following-sibling::p")
	WebElement workingDays;
	
	@FindBy(xpath="//h3[contains(.,'Hours')]/following-sibling::p")
	WebElement workHours;
	
	@FindBy(xpath="//h3[contains(.,'Break')]/following-sibling::p")
	WebElement breakTime;
	
	@FindBy(xpath="//h3[contains(.,'Over')]/following-sibling::p")
	WebElement overtimeWork;
	
	@FindBy(xpath="(//h3[contains(.,'Holiday')]/following-sibling::p)[1]")
	WebElement holidays;
	
	@FindBy(xpath="(//h3[contains(.,'Holiday')]/following-sibling::p)[2]")
	WebElement holidaysDetails;
	
	@FindBy(xpath="//h3[contains(.,'Insu')]/following-sibling::p")
	WebElement insurance;
	
	@FindBy(xpath="//h3[contains(.,'Reti')]/following-sibling::p")
	WebElement retirementSystem;
	
	@FindBy(xpath="//h3[contains(.,'Var')]/following-sibling::p")
	WebElement variousAllowances;
	
	@FindBy(xpath="//h3[contains(.,'Hous')]/following-sibling::p")
	WebElement housingAllowance;
	
	@FindBy(xpath="//h3[contains(.,'One')]/following-sibling::p")
	WebElement oneWayTravelExpenses;
	
	@FindBy(xpath="//h3[contains(.,'Train')]/following-sibling::p")
	WebElement trainingSupport;
	
	@FindBy(xpath="//h3[contains(.,'Jap')]/following-sibling::p")
	WebElement japaneseEducation;
	
	@FindBy(xpath="//h3[contains(.,'Cost')]/following-sibling::p")
	WebElement livingCostSupport;
	
	@FindBy(xpath="//h3[contains(.,'at ')]/following-sibling::p")
	WebElement leisureFacilitiesAtWorkActivitiesOutsideOfWork;
	
	@FindBy(xpath="//h3[contains(.,'Ben')]/following-sibling::p")
	WebElement otherBenefitPackage;
	
	@FindBy(xpath="//h3[.='Tests']/following-sibling::p")
	WebElement tests;
	
	@FindBy(xpath="(//h3[contains(.,'Rec')]/following-sibling::p)[3]")
	WebElement eventDetails;
	
	@FindBy(css=".u-ml-5.u-pl-2")
	List<WebElement> showMore;
	
	@FindBy(css="[href*='Job']")
	WebElement anchor1;
	
	@FindBy(css="[href*='#Con']")
	WebElement anchor2;
	
	@FindBy(css="[href*='#Com']")
	WebElement anchor3;
	
	@FindBy(css="[class*='card']:nth-child(9)")
	WebElement bottomText;
	
	@FindBy(css="[class='u-c-gray']")
	WebElement companyLink;
	
	public String getJobTitle()
	{
		return jobTitle.getText();
	}
	
	public Map<WebElement, List<Integer>> getGroups()
	{
		Map<WebElement, List<Integer>> groupRules = new LinkedHashMap<>();
		groupRules.put(jobTitle, Arrays.asList(0, 1));
		groupRules.put(vacancies, Arrays.asList(2));
		groupRules.put(jobDesc, Arrays.asList(3));
		groupRules.put(details, Arrays.asList(4));
		if(!skills.isEmpty()) {
			groupRules.put(skills.get(0), Arrays.asList(5));
			groupRules.put(skills.get(1), Arrays.asList(6));
			groupRules.put(skills.get(2), Arrays.asList(7));
			groupRules.put(skills.get(3), Arrays.asList(8));
			groupRules.put(skills.get(4), Arrays.asList(9));
			groupRules.put(skills.get(5), Arrays.asList(10));
		}		
		groupRules.put(reqSkillDet, Arrays.asList(11));
		groupRules.put(softSkills, Arrays.asList(12));
		groupRules.put(conditions.get(0), Arrays.asList(13));
		groupRules.put(conditions.get(1), Arrays.asList(14));
		for(AbstractMap.SimpleEntry<WebElement, List<Integer>> entry : getElementIndexMapping()) {
			WebElement ele = entry.getKey();
			List<Integer> indices = entry.getValue();
			if(isElementPresent(ele)) {
				groupRules.put(ele, indices);
			}
		}
		return groupRules;
	}
	
	private boolean isElementPresent(WebElement element)
	{
		try {
			return element.isDisplayed();
		}catch(Exception e) {
			return false;
		}
	}
	
	public List<SimpleEntry<WebElement,List<Integer>>> getElementIndexMapping()
	{
		return Arrays.asList(
		new AbstractMap.SimpleEntry<>(probationPeriod, Arrays.asList(15, 16, 17)),
		new AbstractMap.SimpleEntry<>(location, Arrays.asList(18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
				29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
				50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66)),
		new AbstractMap.SimpleEntry<>(relocation, Arrays.asList(67, 68, 69)),
		new AbstractMap.SimpleEntry<>(measuresAgainstPassiveSmoking, Arrays.asList(70, 71, 72)),
		new AbstractMap.SimpleEntry<>(remoteWorkAfterJoining, Arrays.asList(73, 74, 75)),
		new AbstractMap.SimpleEntry<>(remoteWorkBeforeJoining, Arrays.asList(76, 77, 78)),
		new AbstractMap.SimpleEntry<>(monthlySalary, Arrays.asList(79)),
		new AbstractMap.SimpleEntry<>(annualSalary, Arrays.asList(80)),
		new AbstractMap.SimpleEntry<>(annualSalaryDetails, Arrays.asList(81)),
		new AbstractMap.SimpleEntry<>(ctc, Arrays.asList(82)),
		new AbstractMap.SimpleEntry<>(ctcDetails, Arrays.asList(83)),
		new AbstractMap.SimpleEntry<>(bonus, Arrays.asList(84, 85, 86)),
		new AbstractMap.SimpleEntry<>(raise, Arrays.asList(87, 88, 89)),
		new AbstractMap.SimpleEntry<>(payroll, Arrays.asList(90)),
		new AbstractMap.SimpleEntry<>(workingDays, Arrays.asList(91)),
		new AbstractMap.SimpleEntry<>(workHours, Arrays.asList(92)),
		new AbstractMap.SimpleEntry<>(breakTime, Arrays.asList(93)),
		new AbstractMap.SimpleEntry<>(overtimeWork, Arrays.asList(94, 95, 96)),
		new AbstractMap.SimpleEntry<>(holidays, Arrays.asList(97, 98, 99)),
		new AbstractMap.SimpleEntry<>(holidaysDetails, Arrays.asList(100)),
		new AbstractMap.SimpleEntry<>(insurance, Arrays.asList(101, 102, 103)),
		new AbstractMap.SimpleEntry<>(retirementSystem, Arrays.asList(104, 105, 106)),
		new AbstractMap.SimpleEntry<>(variousAllowances, Arrays.asList(107)),
		new AbstractMap.SimpleEntry<>(housingAllowance, Arrays.asList(108, 109, 110)),
		new AbstractMap.SimpleEntry<>(oneWayTravelExpenses, Arrays.asList(111, 112)),
		new AbstractMap.SimpleEntry<>(trainingSupport, Arrays.asList(113, 114, 115)),
		new AbstractMap.SimpleEntry<>(japaneseEducation, Arrays.asList(116, 117, 118)),
		new AbstractMap.SimpleEntry<>(livingCostSupport, Arrays.asList(119, 120, 121)),
		new AbstractMap.SimpleEntry<>(leisureFacilitiesAtWorkActivitiesOutsideOfWork, Arrays.asList(122)),
		new AbstractMap.SimpleEntry<>(otherBenefitPackage, Arrays.asList(123)),
		new AbstractMap.SimpleEntry<>(tests, Arrays.asList(124)),
		new AbstractMap.SimpleEntry<>(eventDetails, Arrays.asList(125))
		);
	}
	
	public void expandFields()
	{
		clickByJavaScript(showMore.get(0));
		clickByJavaScript(showMore.get(2));
	}
	
	public boolean isNumber(String value)
	{
		try {
			Integer.parseInt(value);
			return true;
		}catch(NumberFormatException e) {
			return false;//Not a number
		}
	}
	
	public String clickAnchor1()
	{
		anchor1.click();
		return anchor1.getDomAttribute("class");
	}
	
	public String clickAnchor2()
	{
		anchor2.click();
		return anchor2.getDomAttribute("class");
	}
	
	public String clickAnchor3()
	{
		anchor3.click();
		return anchor3.getDomAttribute("class");
	}
	
	public String getBottomText() {
		return bottomText.getText();
	}
	
	public void clickCompanyLink() {
		companyLink.click();
	}

}
