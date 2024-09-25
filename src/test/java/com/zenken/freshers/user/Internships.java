package com.zenken.freshers.user;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditInternshipsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Internships extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditInternshipsPage internships;
	Properties properties;
	private boolean intsDeleted = false;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		navigateTo("/");
		profilePreview = new ProfilePreviewPage(driver);
		internships = new EditInternshipsPage(driver);
		profilePreview.login("prasanna.inamdar+user2@zenken.co.jp", "Password_1");
		properties = getProperties();
		if(!intsDeleted)
			{
				int addedInt = profilePreview.getAddedIntCount();
				for(int i=0; i<addedInt; i++)
				{
					profilePreview.clickIntEdit(i*0);
					internships.deleteInternship();
				}
				intsDeleted = true;
			}
	}
	
	@Test(priority=1)
	public void verifyIntNewUrl()
	{
		profilePreview.clickAddInternship();
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url10"));
		Assert.assertEquals(internships.getInternshipHeadline(), "Internships");
	}
	
	@Test(priority=2)
	public void verifyInternshipPageTitle()
	{
		profilePreview.clickAddInternship();
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3)
	public void verifyInternshipPlaceholder()
	{
		profilePreview.clickAddInternship();
		Assert.assertEquals(internships.getIntPlaceholder(), properties.getProperty("intPH"));
	}
	
	@Test(priority=4)
	public void verifyEndDateDdDisable()
	{
		profilePreview.clickAddInternship();
		internships.clickEndDateCheckbox();
		Assert.assertFalse(internships.isEndMonthDdActive(), "End Month dropdown should be inactive");
		Assert.assertFalse(internships.isEndYearDdActive(), "End Year dropdown should be inactive");
	}
	
	@Test(priority=5)
	public void verifyInternshipAddition()
	{
		String[] dates = {"January", "2024", "February"};
		profilePreview.clickAddInternship();
		internships.selectStartMonth(dates[0]);
		internships.selectStartYear(dates[1]);
		internships.selectEndMonth(dates[2]);
		internships.selectEndYear(dates[1]);
		internships.clickSave();
		redirectionAssertions("#Internships");
		Assert.assertEquals(profilePreview.getIntDateValue(), dates[0]+" / "+dates[1]+" - "+dates[2]+" / "+dates[1]);
	}
	
	@Test(priority=6)
	public void verifyIntAdditionWithDetails()
	{
		String[] dates = {"September", "2001"};
		profilePreview.clickAddInternship();
		internships.selectStartMonth(dates[0]);
		internships.selectStartYear(dates[1]);
		internships.clickEndDateCheckbox();
		internships.enterIntDetails(properties.getProperty("intDetails"));
		internships.clickSave();
		redirectionAssertions("#Internships");
		Assert.assertEquals(profilePreview.getIntDateValue(), dates[0]+" / "+dates[1]+" - "+"TBD");
		Assert.assertEquals(profilePreview.getIntDetailsValue(), properties.getProperty("intDetails"));
	}
	
	@Test(priority=7)
	public void verifyIntEditUrl()
	{
		profilePreview.clickIntEdit(0);
		Assert.assertTrue(profilePreview.getPageUrl().startsWith(properties.getProperty("url11")));
		Assert.assertFalse(profilePreview.getPageUrl().contains("new"));
	}
	
	@Test(priority=8)
	public void verifyIntSavedData()
	{
		profilePreview.clickIntEdit(0);
		Assert.assertEquals(internships.getStartMonthOption(), "January");
		Assert.assertEquals(internships.getStartYearOption(), "2024");
		Assert.assertEquals(internships.getEndMonthOption(), "February");
		Assert.assertEquals(internships.getEndYearOption(), "2024");
	}
	
	@Test(priority=9)
	public void verifyIntCharCount()
	{
		profilePreview.clickIntEdit(0);
		internships.clearIntDetails();
		String details = properties.getProperty("intDetails");
		for(int i=0; i<details.length(); i++)
		{
			char currentChar = details.charAt(i);
			internships.enterIntDetails2(Character.toString(currentChar));//send each character
			String displayedCount = internships.getCharCount();
			Assert.assertEquals(displayedCount, String.valueOf(i + 1), "Character count mismatch at index "+i);
		}
		Assert.assertEquals(Integer.parseInt(internships.getCharCount()), 410);
	}
	
	@Test(priority=10)
	public void verifyTenIntExceed()
	{
		int totalIntRequired = 10;
		String[][] dates = {{"January", "2021", "March", "2021"}, {"April", "2021", "June", "2021"},
				{"July", "2021", "September", "2021"}, {"October", "2021", "December", "2021"},
				{"February", "2022", "April", "2022"}, {"May", "2022", "July", "2022"}, 
				{"August", "2022", "October", "2022"}, {"November", "2022", "January", "2023"},
				{"February", "2023", "April", "2023"}, {"May", "2023", "July", "2023"}};
		int addedInt = profilePreview.getAddedIntCount();
		int remainingInts = totalIntRequired - addedInt;
		for(int i=0; i<remainingInts; i++)
		{
			profilePreview.clickAddInternship();
			internships.addInternship(dates[i][0], dates[i][1], dates[i][2], dates[i][3]);
		}
		Assert.assertTrue(profilePreview.isAddIntActive(), "Add Internship button should be inactive");
		Assert.assertTrue(profilePreview.getIntSectionText().contains(properties.getProperty("error22")), "Validation error for maximum internships did not occur.");
	}
	
	@Test(priority=11, retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class)
	public void verifyIntDeletePopUp()
	{
		profilePreview.clickIntEdit(0);
		internships.clickDeleteIntLink();
		Assert.assertTrue(internships.getPopUpText().contains(properties.getProperty("popup1")), "Confirmation popup could not be displayed");
		Assert.assertTrue(internships.getPopUpText().contains(properties.getProperty("popup1.1")), "Confirmation popup could not be displayed");
	}
	
	@Test(priority=12)
	public void verifyIntDelete()
	{
		profilePreview.clickIntEdit(0);
		internships.clickDeleteIntLink();
		internships.clickDeleteInt();
		redirectionAssertions("#Internships");
		Assert.assertFalse(profilePreview.getIntDateValue(0).equals("January / 2024 - February / 2024"));
		Assert.assertTrue(profilePreview.isAddIntButtonActive(), "Add Internship button should be active");
	}
	
	@Test(priority=13)
	public void verifyIntDetailsCharExceed()
	{
		profilePreview.clickIntEdit(0);
		internships.enterIntDetails(properties.getProperty("exceed5k"));
		internships.clickSave();
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getIntDetailsBoxText().contains(properties.getProperty("error23")));
	}
	
	@Test(priority=14)
	public void verifyEndDateMismatch()
	{
		profilePreview.clickIntEdit(0);
		internships.selectStartMonth("March");
		internships.selectStartYear("2023");
		if(internships.isIntCheckBoxSelected())
		{
			internships.clickEndDateCheckbox();
		}
		internships.selectEndMonth("February");
		internships.selectEndYear("2023");
		internships.clickSave();
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getEndDateBoxText().contains(properties.getProperty("error21")));
	}
	
	@Test(priority=15)
	public void verifyStartMonthRequired()
	{
		profilePreview.clickAddInternship();
		internships.selectStartYear("1991");
		internships.selectEndMonth("May");
		internships.selectEndYear("1990");
		internships.clickSave();
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getStartDateBoxText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=16)
	public void verifyStartYearRequired()
	{
		profilePreview.clickAddInternship();
		internships.selectStartMonth("October");
		internships.selectEndMonth("May");
		internships.selectEndYear("1990");
		internships.clickSave();
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getStartDateBoxText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=17)
	public void verifyEndMonthRequired()
	{
		profilePreview.clickAddInternship();
		internships.selectStartMonth("October");
		internships.selectStartYear("1991");
		internships.selectEndYear("1990");
		internships.clickSave();
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getEndDateBoxText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=18)
	public void verifyEndYearRequired()
	{
		profilePreview.clickAddInternship();
		internships.selectStartMonth("October");
		internships.selectStartYear("1991");
		internships.selectEndMonth("May");
		internships.clickSave();
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getEndDateBoxText().contains(properties.getProperty("error1")));
	}
	
	private void redirectionAssertions(String parameter)
	{
		String alert = profilePreview.getAlertText();
		if(profilePreview.isProfileComplete())
		{
			Assert.assertTrue(alert.equals(properties.getProperty("alert4")), "Alert text is not correct");
		}
		else{
			Assert.assertTrue(alert.equals(properties.getProperty("alert5")), "Alert text is not correct");
		}
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains(parameter), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.internshipsSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.internshipsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
