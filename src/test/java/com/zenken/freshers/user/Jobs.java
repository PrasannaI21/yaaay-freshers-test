package com.zenken.freshers.user;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.zenken.freshers.pages.admin.AdminEditJobsPage;
import com.zenken.freshers.pages.admin.AdminLoginPage;
import com.zenken.freshers.pages.user.JobsPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Jobs extends BaseTest{
	
	JobsPage jobs;
	Properties properties;
	AdminEditJobsPage adminEditJob;
	AdminLoginPage adminLogin;
	private boolean isJdUpdated = false;
	JsonNode dataset;

	@BeforeMethod
	public void setupJobs() throws IOException
	{
		adminEditJob = new AdminEditJobsPage(driver);
		adminLogin = new AdminLoginPage(driver);
		if(!isJdUpdated) {
			navigateTo("/admin/");
			adminLogin.loginAdmin();
			driver.get("https://freshers.dspf-dev.com/admin/events/116/jobs/295/edit/");
			adminEditJob.restoreJob();
			dataset = adminEditJob.updateJob();
			isJdUpdated = true;
		}
		properties = getProperties();
		navigateTo("/jobs/9df004f4/");
		jobs = new JobsPage(driver);
		
	}
	
	@Test(description="This test verifies that expected Jobs page title is displayed")
	public void verifyJobsPageTitle()
	{
		Assert.assertEquals(driver.getTitle(), properties.getProperty("title5"));
	}
	
	@Test(description="This test verifies that user is redirected to Jobs page")
	public void verifyJobsUrl()
	{
		String url = properties.getProperty("url19");
		driver.get(url);
		Assert.assertEquals(driver.getCurrentUrl(), url);
	}
	
	@Test(description="This test verifies that user is navigated to Job Details section upon clicking on \"Job Detail\" anchor link")
	public void verifyJobDetailAnchor()
	{
		String attribute = jobs.clickAnchor1();
		Assert.assertTrue(driver.getCurrentUrl().contains("#JobDetail"), 
				"The URL does not contain the expected anchor");
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="This test verifies that user is navigated to Condtions section upon clicking on \"Condtions\" anchor link")
	public void verifyCondtionsAnchor()
	{
		String attribute = jobs.clickAnchor2();
		Assert.assertTrue(driver.getCurrentUrl().contains("#Conditions"), 
				"The URL does not contain the expected anchor");
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="This test verifies that user is navigated to Company Overview section upon clicking on \"Company Overview\" anchor link")
	public void verifyCompanyOverviewAnchor()
	{
		String attribute = jobs.clickAnchor3();
		Assert.assertTrue(driver.getCurrentUrl().contains("#CompanyOverview"), 
				"The URL does not contain the expected anchor");
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(description="This test verifies that the warning text displayed below the Jobs page is correct")
	public void verifyJobsWarningText()
	{
		Assert.assertEquals(jobs.getBottomText(), properties.getProperty("jdtext"));
	}
	
	@Test(description="This test verifies that user is redirected to Companies page")
	public void verifyJobsCompaniesUrl()
	{
		jobs.clickCompanyLink();
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url20"));
	}
	
	@Test(description="This test verifies that the Jobs page is updated and expected "
			+ "values are displayed as they had been set in Admin account")
	public void verifyJobsValues() throws IOException
	{
		jobs.expandFields();
		List<String> jsonValues = new ArrayList<>();
		dataset.forEach(value -> jsonValues.add(value.isNull() ? null : value.asText()));
		Map<WebElement, List<Integer>> groupRules = jobs.getGroups();
		for(Map.Entry<WebElement, List<Integer>> entry : groupRules.entrySet()) {
			WebElement ele = entry.getKey();
			List<Integer> indices = entry.getValue();
			List<String> groupedValues = indices.stream().map(index -> 
			jsonValues.get(index)).collect(Collectors.toList());
			int indicesNu = indices.size();
			switch(indicesNu) {
			case 2:
				if(indices.contains(111)) {
					if("true".equals(groupedValues.get(0))) {
						Assert.assertEquals(ele.getText(), "Available");
					}else {
						Assert.assertEquals(ele.getText(), "No");
					}
				}else {
					if(groupedValues.get(1) != null) {
						String expectedValue = groupedValues.get(0) + " " + groupedValues.get(1);
						Assert.assertEquals(ele.getText(), expectedValue);
					}else {
						String expectedValue = groupedValues.get(0);
						Assert.assertEquals(ele.getText(), expectedValue);
					}
				}
				break;
			case 1:
				Set<Integer> validIndices = new HashSet<>(Arrays.asList(5, 6, 7, 8, 9, 10));
				if(jobs.isNumber(groupedValues.get(0))) {
					int number = Integer.parseInt(groupedValues.get(0));
					NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
					String expValue = numberFormat.format(number);
					if(indices.contains(2)) {
						Assert.assertEquals(ele.getText(), expValue);
					}else {
						String expectedValue = expValue + " JPY";
						Assert.assertEquals(ele.getText(), expectedValue);
					}
				}else if(!Collections.disjoint(indices, validIndices)){
						List<String> skills = Arrays.asList(groupedValues.get(0).split(",\\s*"));
						for(String skill : skills) {
							Assert.assertTrue(ele.getText().contains(skill), "Skill not found: " + skill);
						}
				}else {
					String expectedValue = groupedValues.get(0);
					Assert.assertEquals(ele.getText(), expectedValue);
				}
				break;
			case 49:
				List<String> locations = Arrays.asList("Hokkaido", "Aomori", "Iwate", "Miyagi", "Akita", "Yamagata", "Fukushima", "Ibaraki",
						"Tochigi", "Gunma", "Saitama", "Chiba", "Tokyo", "Kanagawa", "Niigata", "Toyama", "Ishikawa",
						"Fukui", "Yamanashi", "Nagano", "Gifu", "Shizuoka", "Aichi", "Mie", "Shiga", "Kyoto", "Osaka",
						"Hyogo", "Nara", "Wakayama", "Tottori", "Shimane", "Okayama", "Hiroshima", "Yamaguchi", "Tokushima",
						"Kagawa", "Ehime", "Kochi", "Fukuoka", "Saga", "Nagasaki", "Kumamoto", "Oita", "Miyazaki",
						"Kagoshima", "Okinawa", "Remote");
				List<String> displayedLocations = new ArrayList<>();
				for(int i=0; i<groupedValues.size(); i++) {
					if("true".equals(groupedValues.get(i))) {
						displayedLocations.add(locations.get(i));
					}
				}
				String expectedValue1 = String.join(", ", displayedLocations);
				if(groupedValues.get(48) != null) {
					String expectedValue = expectedValue1 + ", " + groupedValues.get(48);
					Assert.assertEquals(ele.getText(), expectedValue);
				}else {
					Assert.assertEquals(ele.getText(), expectedValue1);
				}
				break;
			case 3:
				if(indices.contains(108) || indices.contains(113) || indices.contains(119)) {
					String expectedValue3 = null;
					if("true".equals(groupedValues.get(0))) {
						expectedValue3 = groupedValues.get(2) != null ? "Available / " + groupedValues.get(2) : "Available";
						Assert.assertEquals(ele.getText(), expectedValue3);
					}else if("true".equals(groupedValues.get(1))) {
						expectedValue3 = groupedValues.get(2) != null ? "No / " + groupedValues.get(2) : "No";
					}
				}else if(indices.contains(116)) {
					if("true".equals(groupedValues.get(0))) {
						String expectedValue3 = groupedValues.get(2) !=null ? "Provided / " + groupedValues.get(2) : "Provided";
						Assert.assertEquals(ele.getText(), expectedValue3);
					}else if("true".equals(groupedValues.get(1))) {
						String expectedValue3 = groupedValues.get(2) != null ? "No / " + groupedValues.get(2) : "No";
						Assert.assertEquals(ele.getText(), expectedValue3);
					}
				}else if(indices.contains(97)){
					List<String> holidays = Arrays.asList("Summer holidays", "End of the year holidays", "Japanese national holidays");
					List<String> displayedHolidays = new ArrayList<>();
					for(int i=0; i<groupedValues.size(); i++) {
						if("true".equals(groupedValues.get(i))) {
							displayedHolidays.add(holidays.get(i));
						}
					}
					for(String expValue : displayedHolidays) {
						Assert.assertTrue(ele.getText().contains(expValue), "Holiday not found :" + expValue);
					}
				}else {
					if("true".equals(groupedValues.get(0))) {
						String expectedValue3 = groupedValues.get(2) !=null ? "Yes / " + groupedValues.get(2) : "Yes";
						Assert.assertEquals(ele.getText(), expectedValue3);
					}else if("true".equals(groupedValues.get(1))) {
						String expectedValue3 = groupedValues.get(2) != null ? "No / " + groupedValues.get(2) : "No";
						Assert.assertEquals(ele.getText(), expectedValue3);
					}
				}
				break;
			}
		}
	}
}
