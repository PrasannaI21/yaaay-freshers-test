package com.zenken.freshers.pages.user;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zenken.frehsers.abstractcomponents.WebDriverUtils;

public class CompaniesPage extends WebDriverUtils{

	WebDriver driver;
	
	public CompaniesPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[class*='24']")
	WebElement companyName;
	
	@FindBy(css="div[class*='14']")
	WebElement industry;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[1]")
	WebElement whoWeAre;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[3]")
	WebElement hq;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[5]")
	WebElement established;
	
	@FindBy(xpath="((//h2[contains(.,'We')]/following-sibling::div)[7]/descendant::span)[1]")
	WebElement employees;
	
	@FindBy(xpath="((//h2[contains(.,'We')]/following-sibling::div)[7]/descendant::span)[2]")
	WebElement employeesDetails;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[9]")
	WebElement capital;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[11]")
	WebElement netSales;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[13]")
	WebElement officialLanguage;
	
	@FindBy(xpath="(//h2[contains(.,'We')]/following-sibling::div)[15]")
	WebElement website;
	
	@FindBy(xpath="//h2[contains(.,'Vision')]/following-sibling::div")
	WebElement ourVisionMission;
	
	@FindBy(xpath="//h2[contains(.,'Ser')]/following-sibling::div")
	WebElement ourServicesProducts;
	
	@FindBy(css="[class*='w u-mt']")
	WebElement skillsUsedInTheCompany;
	
	@FindBy(xpath="//h2[contains(.,'Path')]/following-sibling::div")
	WebElement careerPath;
	
	@FindBy(css="[class*='w u-mb']")
	WebElement ourCorporateCulture;
	
	@FindBy(xpath="(//h2[contains(.,'Work')]/following-sibling::div)[2]")
	WebElement ourCorporateCultureDetails;
	
	@FindBy(xpath="(//h2[contains(.,'Work')]/following-sibling::div)[4]")
	WebElement aboutForeignAndEnglishSpeakingEmployees;
	
	@FindBy(css="[class=link]")
	WebElement link;
	
	@FindBy(css="[viewBox='0 0 14 24']")
	List<WebElement> arrows;
	
	@FindBy(css="[class*='fit']")
	List<WebElement> comImages;
	
	@FindBy(css="[class*='y-logo']")
	WebElement comLogo;
	
	@FindBy(css="[href*='#com']")
	WebElement anchorLink1;
	
	@FindBy(css="[href*='#ourV']")
	WebElement anchorLink2;
	
	@FindBy(css="[href*='#ourS']")
	WebElement anchorLink3;
	
	@FindBy(css="[href*='#skills']")
	WebElement anchorLink4;
	
	@FindBy(css="[href*='#car']")
	WebElement anchorLink5;
	
	@FindBy(css="[href*='#ourC']")
	WebElement anchorLink6;
	
	public List<SimpleEntry<WebElement,List<Integer>>> getElementIndexMapping()
	{
		return Arrays.asList(
				new AbstractMap.SimpleEntry<>(companyName, Arrays.asList(0)),
				new AbstractMap.SimpleEntry<>(industry, Arrays.asList(1)),
				new AbstractMap.SimpleEntry<>(whoWeAre, Arrays.asList(2)),
				new AbstractMap.SimpleEntry<>(hq, Arrays.asList(3)),
				new AbstractMap.SimpleEntry<>(established, Arrays.asList(4)),
				new AbstractMap.SimpleEntry<>(employees, Arrays.asList(5, 6)),
				new AbstractMap.SimpleEntry<>(employeesDetails, Arrays.asList(7)),
				new AbstractMap.SimpleEntry<>(capital, Arrays.asList(8, 9)),
				new AbstractMap.SimpleEntry<>(netSales, Arrays.asList(10, 11)),
				new AbstractMap.SimpleEntry<>(officialLanguage, Arrays.asList(12, 13, 14)),
				new AbstractMap.SimpleEntry<>(website, Arrays.asList(15)),
				new AbstractMap.SimpleEntry<>(ourVisionMission, Arrays.asList(16)),
				new AbstractMap.SimpleEntry<>(ourServicesProducts, Arrays.asList(17)),
				new AbstractMap.SimpleEntry<>(skillsUsedInTheCompany, Arrays.asList(18)),
				new AbstractMap.SimpleEntry<>(careerPath, Arrays.asList(19)),
				new AbstractMap.SimpleEntry<>(ourCorporateCulture, Arrays.asList(20, 21, 22, 23, 24, 25, 26, 27)),
				new AbstractMap.SimpleEntry<>(ourCorporateCultureDetails, Arrays.asList(28)),
				new AbstractMap.SimpleEntry<>(aboutForeignAndEnglishSpeakingEmployees, Arrays.asList(29))
				);
	}
	
	public Map<WebElement, List<Integer>> getGroups()
	{
		Map<WebElement, List<Integer>> groupRules = new LinkedHashMap<>();
		for(AbstractMap.SimpleEntry<WebElement, List<Integer>> entry : getElementIndexMapping()) {
			WebElement ele = entry.getKey();
			List<Integer> indices = entry.getValue();
			if(isElementPresent(ele)) {
				groupRules.put(ele, indices);
			}
		}
		return groupRules;
	}
	
	public void clickWebsite()
	{
		clickByJavaScript(link);
	}
	
	public void clickArrow()
	{
		arrows.get(1).click();
	}
	
	public String getComLogoSrc()
	{
		return comLogo.getDomAttribute("src");
	}
	
	public List<String> getComImageSrc()
	{
		List<String> array = new ArrayList<>();
		for(WebElement ele : comImages) {
			array.add(ele.getDomAttribute("src"));
		}
		return array;
	}
	
	public List<WebElement> getAnchorLinks() {
		List<WebElement> elements = Arrays.asList(anchorLink1, anchorLink2, anchorLink3,
				anchorLink4, anchorLink5, anchorLink6);
		return elements;
	}
	
	public void clickAnchorLink(WebElement ele)
	{
		clickByJavaScript(ele);
	}
}
