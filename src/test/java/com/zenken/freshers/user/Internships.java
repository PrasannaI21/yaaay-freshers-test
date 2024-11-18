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
	public void setupTest() throws IOException
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
	
	@Test(priority=1, description="This test verifies that user is redirected to Internships (New) page")
	public void verifyIntNewUrl()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url10"));
		Assert.assertEquals(internships.getInternshipHeadline(), "Internships");
	}
	
	@Test(priority=2, description="This test verifies that the Internships page title is correct")
	public void verifyInternshipPageTitle()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that expected placeholder for 'Internship Details' is displayed")
	public void verifyInternshipPlaceholder()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Verify that placeholder displayed for 'Internship Details' is correct");
		Assert.assertEquals(internships.getIntPlaceholder(), properties.getProperty("intPH"));
	}
	
	@Test(priority=4, description="This test verifies that end date dropdowns turn inactive after selecting the checkbox")
	public void verifyEndDateDdDisable()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select the checkbox for end date");
		internships.clickEndDateCheckbox();
		log("Step 3: Verify that the dropdowns for end-month and year turn inactive");
		Assert.assertFalse(internships.isEndMonthDdActive(), "End Month dropdown should be inactive");
		Assert.assertFalse(internships.isEndYearDdActive(), "End Year dropdown should be inactive");
	}
	
	@Test(priority=5, description="This test verifies that an internship is added successfully with required to save fields")
	public void verifyInternshipAddition()
	{
		String[] dates = {"January", "2024", "February"};
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select start month "+dates[0]);
		internships.selectStartMonth(dates[0]);
		log("Step 3: Select start year "+dates[1]);
		internships.selectStartYear(dates[1]);
		log("Step 4: Select end month "+dates[2]);
		internships.selectEndMonth(dates[2]);
		log("Step 5: Select end year "+dates[1]);
		internships.selectEndYear(dates[1]);
		log("Step 6: Click on 'Save' button");
		internships.clickSave();
		log("Step 7: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Internships");
		log("Step 8: Verify that internship record is added and dates are displayed on preview page");
		Assert.assertEquals(profilePreview.getIntDateValue(), dates[0]+" / "+dates[1]+" - "+dates[2]+" / "+dates[1]);
	}
	
	@Test(priority=6, description="This test verifies that an internship is added "
			+ "successfully with required to save fields along with internship details")
	public void verifyIntAdditionWithDetails()
	{
		String[] dates = {"September", "2001"};
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select start month "+dates[0]);
		internships.selectStartMonth(dates[0]);
		log("Step 3: Select start year "+dates[1]);
		internships.selectStartYear(dates[1]);
		log("Step 4: Select the checkbox for end date");
		internships.clickEndDateCheckbox();
		log("Step 5: Enter description in Internship Details field");
		internships.enterIntDetails(properties.getProperty("intDetails"));
		log("Step 6: Click on 'Save' button");
		internships.clickSave();
		log("Step 7: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Internships");
		log("Step 8: Verify that internship record is added and dates and details are displayed on preview page");
		Assert.assertEquals(profilePreview.getIntDateValue(), dates[0]+" / "+dates[1]+" - "+"TBD");
		Assert.assertEquals(profilePreview.getIntDetailsValue(), properties.getProperty("intDetails"));
	}
	
	@Test(priority=7, description="This test verifies that user is redirected to Internships (Edit) page")
	public void verifyIntEditUrl()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		log("Step 2: Verify that internship edit URL is correct");
		Assert.assertTrue(profilePreview.getPageUrl().startsWith(properties.getProperty("url11")));
		Assert.assertFalse(profilePreview.getPageUrl().contains("new"));
	}
	
	@Test(priority=8, description="This test verifies that information saved for internship is correct")
	public void verifyIntSavedData()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		log("Step 2: Verify that internship start and end dates saved are displayed as expected");
		Assert.assertEquals(internships.getStartMonthOption(), "January");
		Assert.assertEquals(internships.getStartYearOption(), "2024");
		Assert.assertEquals(internships.getEndMonthOption(), "February");
		Assert.assertEquals(internships.getEndYearOption(), "2024");
	}
	
	@Test(priority=9, description="This test verifies that character count for 'Internship Details' field is updated dynamically")
	public void verifyIntCharCount()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		internships.clearIntDetails();
		String details = properties.getProperty("intDetails");
		log("Step 2: Enter individual characters in the text box");
		for(int i=0; i<details.length(); i++)
		{
			char currentChar = details.charAt(i);
			internships.enterIntDetails2(Character.toString(currentChar));//send each character
			String displayedCount = internships.getCharCount();
			Assert.assertEquals(displayedCount, String.valueOf(i + 1), "Character count mismatch at index "+i);
		}
		log("Step 3: Verify that the number for chracter count is incremented as the characters are entered");
		Assert.assertEquals(Integer.parseInt(internships.getCharCount()), 410);
	}
	
	@Test(priority=10, description="This test verifies that 'Add Internship' button turns "
			+ "inactive after adding ten internships")
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
		log("Step 1: Add ten internships");
		for(int i=0; i<remainingInts; i++)
		{
			profilePreview.clickAddInternship();
			internships.addInternship(dates[i][0], dates[i][1], dates[i][2], dates[i][3]);
		}
		log("Step 2: Verify that 'Add Internship' button turns inactive and the text is displayed");
		Assert.assertTrue(profilePreview.isAddIntActive(), "Add Internship button should be inactive");
		Assert.assertTrue(profilePreview.getIntSectionText().contains(properties.getProperty("error22")), "Validation error for maximum internships did not occur.");
	}
	
	@Test(priority=11, description="This test verifies that confirmation popup is displayed while deleting the internship")
	public void verifyIntDeletePopUp()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		log("Step 2: Click on 'Delete Internship' link");
		internships.clickDeleteIntLink();
		log("Step 3: Verify that confirmation popup is displayed and the text displayed is correct");
		Assert.assertTrue(internships.getPopUpText().contains(properties.getProperty("popup1")), "Confirmation popup could not be displayed");
		Assert.assertTrue(internships.getPopUpText().contains(properties.getProperty("popup1.1")), "Confirmation popup could not be displayed");
	}
	
	@Test(priority=12, description="This test verifies that internship can be deleted")
	public void verifyIntDelete()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		log("Step 2: Click on 'Delete Internship' link");
		internships.clickDeleteIntLink();
		log("Step 3: Click on 'Delete' button on the popup");
		internships.clickDeleteInt();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#Internships");
		log("Step 5: Verify that the first internship is deleted and the 'Add Internship' button turns active");
		Assert.assertFalse(profilePreview.getIntDateValue(0).equals("January / 2024 - February / 2024"));
		Assert.assertTrue(profilePreview.isAddIntButtonActive(), "Add Internship button should be active");
	}
	
	@Test(priority=13, description="This test verifies that validation error occurs "
			+ "for maximum characters occurs: Internship Details")
	public void verifyIntDetailsCharExceed()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		log("Step 2: Enter 5001 characters in the text box");
		internships.enterIntDetails(properties.getProperty("exceed5k"));
		log("Step 3: Click on 'Save' button");
		internships.clickSave();
		log("Step 4: Verify that validation error corresponds to exceeding 5000 character limit");
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getIntDetailsBoxText().contains(properties.getProperty("error23")));
	}
	
	@Test(priority=14, description="This test verifies that validation error occurs when "
			+ "end date selected is before the start date")
	public void verifyEndDateMismatch()
	{
		log("Step 1: Click on first internship edit icon");
		profilePreview.clickIntEdit(0);
		log("Step 2: Select start month: March");
		internships.selectStartMonth("March");
		log("Step 3: Select start year: 2023");
		internships.selectStartYear("2023");
		if(internships.isIntCheckBoxSelected())
		{
			internships.clickEndDateCheckbox();
		}
		log("Step 4: Select end month: February");
		internships.selectEndMonth("February");
		log("Step 5: Select end year: 2023");
		internships.selectEndYear("2023");
		log("Step 6: Click on 'Save' button");
		internships.clickSave();
		log("Step 7: Verify that validation error corresponds to end date being earlier than start date");
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getEndDateBoxText().contains(properties.getProperty("error21")));
	}
	
	@Test(priority=15, description="This test verifies that validation error occurs when start month is not selected")
	public void verifyStartMonthRequired()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select start year: 1991");
		internships.selectStartYear("1991");
		log("Step 3: Select end month: May");
		internships.selectEndMonth("May");
		log("Step 4: Select end year: 1990");
		internships.selectEndYear("1990");
		log("Step 5: Click on 'Save' button");
		internships.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for start month dropdown");
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getStartDateBoxText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=16, description="This test verifies that validation error occurs when start year is not selected")
	public void verifyStartYearRequired()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select start month: October");
		internships.selectStartMonth("October");
		log("Step 3: Select end month: May");
		internships.selectEndMonth("May");
		log("Step 4: Select end year: 1990");
		internships.selectEndYear("1990");
		log("Step 5: Click on 'Save' button");
		internships.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for start year dropdown");
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getStartDateBoxText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=17, description="This test verifies that validation error occurs when end month is not selected")
	public void verifyEndMonthRequired()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select start month: October");
		internships.selectStartMonth("October");
		log("Step 3: Select start year: 1991");
		internships.selectStartYear("1991");
		log("Step 4: Select end year: 1990");
		internships.selectEndYear("1990");
		log("Step 5: Click on 'Save' button");
		internships.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for end month dropdown");
		Assert.assertEquals(internships.getAlert(), properties.getProperty("alert3"));
		Assert.assertTrue(internships.getEndDateBoxText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=18, description="This test verifies that validation error occurs when end year is not selected")
	public void verifyEndYearRequired()
	{
		log("Step 1: Click on 'Add Internship' button");
		profilePreview.clickAddInternship();
		log("Step 2: Select start month: October");
		internships.selectStartMonth("October");
		log("Step 3: Select start year: 1991");
		internships.selectStartYear("1991");
		log("Step 4: Select end month: May");
		internships.selectEndMonth("May");
		log("Step 5: Click on 'Save' button");
		internships.clickSave();
		log("Step 6: Verify that validation error corresponds to 'Required to save' for end year dropdown");
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
