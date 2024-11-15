package com.zenken.freshers.user;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditConsetFormPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class JapanesePlacementConsentForm extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditConsetFormPage consentForm;
	Properties properties;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		profilePreview = new ProfilePreviewPage(driver);
		consentForm = new EditConsetFormPage(driver);
		navigateTo("/");
		profilePreview.login("applied_user_26@example.com", "Password_1");
		properties = getProperties();
	}
	
	@Test(priority=1, description="This test verifies that user is redirected to 'Japanese Placement Consent Form' URL")
	public void verifyConsentFormUrl()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url16"));
		Assert.assertEquals(consentForm.getFormHeadlineText(), "Japanese Placement Consent Form");
	}
	
	@Test(priority=2, description="This test verifies that the Japanese Placement Consent Form page title is correct")
	public void verifyConsentFormTitle()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that Consent Form is saved when checkbox is selected")
	public void verifyIsCFSaved()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Click placement agreement checkbox");
		consentForm.selectAgreeCheckbox();
		log("Step 3: Click on 'Save' button");
		consentForm.clickSave();
		log("Step 4: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#JapanesePlacementConsentForm");
		log("Step 5: Verify that 'Yes' for 'Parent's Agreement' is displayed on preview page");
		Assert.assertEquals(profilePreview.getAgreementValueText(), "Yes");
	}
	
	@Test(priority=4, description="This test verifies that consent form can be downloaded successfully"
			)
	public void verifyConsentFormDL()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		int initialCount = profilePreview.getFileCount();
		log("Step 2: Click on 'Consent Form Template' link");
		consentForm.clickConsentFormLink();
		log("Step 3: Verify that consent form can be downloaded");
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Consent form template.pdf");
	}
	
	@Test(priority=5, description="This test verifies that consent form can be uploaded and can be downloaded from preview page")
	public void verifyCFUploadAndDl() throws InterruptedException
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Upload consent form PDF file");
		consentForm.uploadFile("C:\\Users\\prasa\\Downloads\\Sample_Project.pdf");
		log("Step 3: Verify that the file is uploaded successfully");
		Assert.assertTrue(consentForm.isProgressBarDisplayed(), "Progress bar is not displayed");
		Assert.assertTrue(consentForm.isUploadComplete(), "File upload did not complete successfully");
		Assert.assertTrue(consentForm.getFileNameText().contains("Sample_Project.pdf"));
		log("Step 4: Verify that (file check in progress) is displayed");
		Assert.assertTrue(consentForm.getFileNameText().contains("(file check in progress)"));
		log("Step 5: Verify that 'Select From Files' button turns inactive");
		Assert.assertFalse(consentForm.isSelectFileActive(), "Select From Files button should be inactive");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		String date = LocalDate.now().format(formatter);
		log("Step 6: Verify that uploaded date is "+ date);
		Assert.assertTrue(consentForm.getFileNameText().contains("Uploaded on: "+date), "The displayed date does not match today's date");
		log("Step 7: Click on 'Save' button");
		consentForm.clickSave();
		log("Step 8: Verify: Snackbar, Section display, Tab selection");
		redirectionAssertions("#JapanesePlacementConsentForm");
		log("Step 9: Verify that expected file name with file check text and date are displayed on preview page");
		Assert.assertTrue(profilePreview.getConsentFormValueText().contains("Sample_Project.pdf"));
		Assert.assertTrue(profilePreview.getConsentFormValueText().contains("(file check in progress)"));
		Assert.assertTrue(profilePreview.getConsentFormValueText().contains("Uploaded on: "+date), "The displayed date does not match today's date on preview page");
		log("Step 10: Verify that download link is displayed and 'Required to apply' text is not displayed on preview page");
		Assert.assertEquals(profilePreview.getConsentFormDlLink(10), "Sample_Project.pdf");
		Assert.assertFalse(profilePreview.formTitle.getText().contains(properties.getProperty("error16")));
		int initialCount = profilePreview.getFileCount();
		log("Step 11: Click download link");
		profilePreview.clickConsentFormFile();
		log("Step 12: Verify that file can be downloaded from preview page after refreshing");
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Sample_Project.pdf");
	}
	
	@Test(priority=6, description="This test verifies that the consent form can be deleted")
	public void verifyConsentFormDelete()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Click on 'Delete' button for added file");
		consentForm.clickCFDelete();
		log("Step 3: Verify that consent form is deleted");
		Assert.assertTrue(consentForm.isCFDeleted("Sample_Project.pdf"), "Consent form file should have been deleted");
		log("Step 4: Verify that 'Select From Files' button turns active");
		Assert.assertTrue(consentForm.isSelectFileActive(), "'Select From Files' button should turn active");
	}
	
	@Test(priority=7, description="This test verifies that 'Required to apply' text is displayed for consent form after it is deleted")
	public void verifyCFReqToApply()
	{
		log("step 1: Verify that 'Required to apply' text is displayed on preview page");
		Assert.assertTrue(profilePreview.formTitle.getText().contains(properties.getProperty("error16")));
		log("Step 2: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 3: Verify that 'Required to apply' text is displayed on consent form edit page");
		Assert.assertTrue(consentForm.getCFReqText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=8, description="This test verifies that validation error occurs for required to save field: Do your parents/guardians agree with your participation to this Japanese Placement?")
	public void verifyAgreeCBReqToSave()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: De-select the checkbox");
		consentForm.deSelectAgreeCheckbox();
		log("Step 3: Click on 'Save' button");
		consentForm.clickSave();
		log("Step 4: Verify that validation error corresponds to 'Required to save' for checkbox's field");
		Assert.assertEquals(consentForm.getAlert(), properties.getProperty("alert3"));		
		Assert.assertEquals(consentForm.getReqToSaveText(), properties.getProperty("error1"));
	}
	
	@Test(priority=9, description="This test verifies that validation error for invalid consent form file format occurs")
	public void verifyCFInvalidFileFormat()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Upload a file in doc format");
		consentForm.uploadFile("C:\\Users\\prasa\\Downloads\\file-sample-doc.doc");
		log("Step 3: Verify that validation error corresponds to file type: pdf");
		Assert.assertTrue(consentForm.getCFErrorText().contains(properties.getProperty("error29")));
	}
	
	@Test(priority=10, description="This test verifies that validation error for invalid size for consent form file occurs")
	public void verifyCFInvalidFileSize()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Upload a PDF file having size more than 2mb");
		consentForm.uploadFile("C:\\Users\\prasa\\Downloads\\samplepdf-above2mb.pdf");
		log("Step 3: Verify that validation error corresponds to file size less than 2048 kilobytes");
		Assert.assertTrue(consentForm.getCFErrorText().contains(properties.getProperty("error27")));
	}
	
	@Test(priority=11, description="This test verifies that 'Cancel' from Japanese Placement Consent Form redirectes user to preview page ")
	public void verifyCFCancelRed()
	{
		log("Step 1: Click on Consent Form edit button");
		profilePreview.clickFormEdit();
		log("Step 2: Click on 'Cancel' button");
		consentForm.clickCancel();
		log("Step 3: Verify that user is redirected to 'Japanese Placement Consent Form' section of profile page");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#JapanesePlacementConsentForm"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.consentFormSection);
		Assert.assertTrue(state, "Certifications section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.consentFormAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.consentFormSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.consentFormAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
