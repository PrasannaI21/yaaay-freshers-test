package com.zenken.freshers.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.zenken.freshers.pages.admin.AdminEditCompaniesPage;
import com.zenken.freshers.pages.admin.AdminImageCompaniesPage;
import com.zenken.freshers.pages.admin.AdminLoginPage;
import com.zenken.freshers.pages.user.CompaniesPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Companies extends BaseTest{

	CompaniesPage companies;
	AdminLoginPage adminLogin;
	AdminEditCompaniesPage adminEditCom;
	Properties properties;
	private boolean isComUpdated = false;
	JsonNode dataset;
	int index;
	List<String> imgSrc;
	
	@BeforeMethod
	public void setupCom() throws IOException
	{
		adminLogin = new AdminLoginPage(driver);
		adminEditCom = new AdminEditCompaniesPage(driver);
		companies = new CompaniesPage(driver);
		properties = getProperties();
		if(!isComUpdated) {
			navigateTo("/admin/");
			adminLogin.loginAdmin();
			driver.get("https://freshers.dspf-dev.com/admin/companies/57/manuscript/image/");
			AdminImageCompaniesPage adminImageCom = new AdminImageCompaniesPage(driver);
			index = Integer.parseInt(properties.getProperty("datasetIndex"));
			adminImageCom.deleteComImages();
			imgSrc = adminImageCom.uploadComImages(properties.getProperty("companyImageIndex"), "companyImageIndex");
			driver.get("https://freshers.dspf-dev.com/admin/companies/57/manuscript/edit/");
			adminEditCom.restoreCompany();
			dataset = adminEditCom.updateCompany(properties.getProperty("datasetIndex"), "datasetIndex");
			isComUpdated = true;
		}
		navigateTo("/companies/455f76c3/");
	}
	
	@Test(description="This test verifies that expected Companies page title is displayed")
	public void verifyComPageTitle()
	{
		Assert.assertEquals(driver.getTitle(), properties.getProperty("title5"));
	}
	
	@Test(description="This test verifies that user is redirected to Companies page")
	public void verifyComUrl()
	{
		String url = properties.getProperty("url20");
		driver.get(url);
		Assert.assertEquals(driver.getCurrentUrl(), url);
	}
	
	@Test(description="This test verifies that the Companies page is updated and expected "
			+ "values are displayed as they had been set in Admin account")
	public void verifyComValues() throws IOException
	{
		List<String> jsonValues = new ArrayList<>();
		dataset.forEach(value -> jsonValues.add(value.isNull() ? null : value.asText()));
		Map<WebElement, List<Integer>> groupRules = companies.getGroups();
		for(Map.Entry<WebElement, List<Integer>> entry : groupRules.entrySet()) {
			WebElement ele = entry.getKey();
			List<Integer> indices = entry.getValue();
			List<String> groupedValues = indices.stream().map(index -> jsonValues
					.get(index)).collect(Collectors.toList());
			int indicesNu = indices.size();
			switch(indicesNu) {
			case 1:
				if(indices.contains(18)) {
					String[] skills = groupedValues.get(0).split(",\\s*");
					for(String skill : skills) {
						Assert.assertTrue(ele.getText().contains(skill), "Skill not found: " + skill);
					}
				}else if(indices.contains(19) || indices.contains(29)){
					if(groupedValues.get(0) == null) {
						Assert.assertEquals(ele.getText(), "-");
					}else {
						Assert.assertEquals(ele.getText(), groupedValues.get(0));
					}					
				}else {
					Assert.assertEquals(ele.getText(), groupedValues.get(0));
				}
				break;
			case 2:
				if(groupedValues.get(1) != null) {
					Assert.assertEquals(ele.getText(), "Not Disclosed");
				}else if(indices.contains(5)){
					int number = Integer.parseInt(groupedValues.get(0));
					NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
					String expValue = numberFormat.format(number);
					Assert.assertEquals(ele.getText(), expValue + " members");
				}else {
					Long number = Long.parseLong(groupedValues.get(0));
					BigDecimal numberInMillions = new BigDecimal(number).divide(new BigDecimal(1_000_000));
					BigDecimal value = numberInMillions.setScale(1, RoundingMode.DOWN);
					DecimalFormat formatter = new DecimalFormat("#,###.0");
					String expValue = formatter.format(value);
					Assert.assertEquals(ele.getText(), expValue + " million JPY");
				}
				break;
			case 3:
				List<String> allLanguages = Arrays.asList("English", "Japanese");
				List<String> languages = new ArrayList<>();
				for(int i=0; i<groupedValues.size(); i++) {
					if("true".equals(groupedValues.get(i))) {
						languages.add(allLanguages.get(i));
					}
				}
				String expValue = String.join(", ", languages);
				if(groupedValues.get(2) != null) {
					String expValue1 = expValue + ", " + groupedValues.get(2);
					Assert.assertEquals(ele.getText(), expValue1);
				}else {
					Assert.assertEquals(ele.getText(), expValue);
				}
				break;
			case 8:
				List<String> allCultures = Arrays.asList("Cooperative", "Assertive",
						"Flexible", "Conservative", "Deliberate", "Logical", "Team Work", "Progressive");
				List<String> culture = new ArrayList<>();
				for(int i=0; i<groupedValues.size(); i++) {
					if("true".equals(groupedValues.get(i))) {
						culture.add(allCultures.get(i));
					}
				}
				for(String expValue1 : culture) {
					Assert.assertTrue(ele.getText().contains(expValue1), "Corporate culture not found: "+expValue1);
				}
				break;
			}		
		}
	}
	
	@Test(dependsOnMethods="verifyComValues", description="This test verifies that "
			+ "the company's website link is opened in a separate tab when clicked")
	public void verifyCompWebSite()
	{
		companies.clickWebsite();
		ArrayList<String> tabs = companies.switchTabs(1);
		Assert.assertEquals(tabs.size(), 2);
		if(index % 2 == 0) {
			Assert.assertEquals(driver.getCurrentUrl(), properties.get("site1"));
		}else {
			Assert.assertEquals(driver.getCurrentUrl(), properties.get("site2"));
		}		
	}
	
	@Test(description="This test verifies that the images uploaded for company logo and"
			+ " header images in Admin account are displayed accrodingly")
	public void verifyComImages() throws IOException
	{
		if(imgSrc.isEmpty()) {
			System.out.println("No images uploaded for this run!");
			Assert.assertEquals(companies.getComLogoSrc(), properties.get("comDefaultLogo"));
		}else {
			Assert.assertEquals(companies.getComLogoSrc(), imgSrc.get(0));
			int i = 1;
			do {
				List<String> array = companies.getComImageSrc();
				Assert.assertEquals(array.get(i-1), imgSrc.get(i));
				i++;
				if(i < imgSrc.size()) {
					companies.clickArrow();
				}
			}while(i < imgSrc.size());
		}
	}
	
	@Test(description="This test verifies that user is navigated to respective sections"
			+ "after clicking on their anchor links")
	public void verifyComAnchorLink() throws InterruptedException
	{
		List<WebElement> links = companies.getAnchorLinks();
		List<String> href = Arrays.asList("#companyOverview", "#ourVisionMission", "#ourServices",
				"#skillsUsedInTheCompany", "#careerPath", "#ourCorporateCulture");
		for(int i=0; i<links.size(); i++) {
			companies.clickAnchorLink(links.get(i));
			Assert.assertTrue(driver.getCurrentUrl().contains(href.get(i)), "The URL does not contain"
					+ "achor"+href.get(i));
		}
	}
}
