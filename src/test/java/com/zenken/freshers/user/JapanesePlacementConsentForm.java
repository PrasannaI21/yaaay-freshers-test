package com.zenken.freshers.user;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
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
		profilePreview.clickFormEdit();
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url16"));
		Assert.assertEquals(consentForm.getFormHeadlineText(), "Japanese Placement Consent Form");
	}
	
	@Test(priority=2, description="This test verifies that the Japanese Placement Consent Form page title is correct")
	public void verifyConsentFormTitle()
	{
		profilePreview.clickFormEdit();
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that Consent Form is saved when checkbox is selected")
	public void verifyIsCFSaved()
	{
		profilePreview.clickFormEdit();
		consentForm.selectAgreeCheckbox();
		consentForm.clickSave();
		redirectionAssertions("#JapanesePlacementConsentForm");
		Assert.assertEquals(profilePreview.getAgreementValueText(), "Yes");
	}
	
	@Test(priority=4, description="This test verifies that consent form can be downloaded successfully"
			)
	public void verifyConsentFormDL() throws IOException
	{
		String browser = System.getProperty("browser") != null ? System.getProperty("browser") : 
			getProperties().getProperty("browser");		
		profilePreview.clickFormEdit();
		int initialCount = profilePreview.getFileCount();
		consentForm.clickConsentFormLink();
        if(browser.contains("edge")) {
        	consentForm.switchToPdf();
    		String pdfurl = driver.getCurrentUrl();
    		JavascriptExecutor js = (JavascriptExecutor) driver;
    		String jsScript = "var link = document.createElement('a');" +
                    "link.href = arguments[0];" +
                    "link.download = 'Consent form template.pdf';" +
                    "document.body.appendChild(link);" +
                    "link.click();" +
                    "document.body.removeChild(link);";
    		js.executeScript(jsScript, pdfurl);
		}
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Consent form template.pdf");
	}
	
	@Test(priority=5, description="This test verifies that consent form can be uploaded and can be downloaded from preview page")
	public void verifyCFUploadAndDl() throws InterruptedException
	{
		profilePreview.clickFormEdit();
		consentForm.uploadFile(System.getProperty("user.dir")+"/test-data/sample-cform.pdf");
		Assert.assertTrue(consentForm.isProgressBarDisplayed(), "Progress bar is not displayed");
		Assert.assertTrue(consentForm.isUploadComplete(), "File upload did not complete successfully");
		Assert.assertTrue(consentForm.getFileNameText().contains("sample-cform.pdf"));
		Assert.assertTrue(consentForm.getFileNameText().contains("(file check in progress)"));
		Assert.assertFalse(consentForm.isSelectFileActive(), "Select From Files button should be inactive");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		String date = LocalDate.now().format(formatter);
		Assert.assertTrue(consentForm.getFileNameText().contains("Uploaded on: "+date), "The displayed date does not match today's date");
		consentForm.clickSave();
		redirectionAssertions("#JapanesePlacementConsentForm");
		Assert.assertTrue(profilePreview.getConsentFormValueText().contains("sample-cform.pdf"));
		Assert.assertTrue(profilePreview.getConsentFormValueText().contains("(file check in progress)"));
		Assert.assertTrue(profilePreview.getConsentFormValueText().contains("Uploaded on: "+date), "The displayed date does not match today's date on preview page");
		Assert.assertEquals(profilePreview.getConsentFormDlLink(10), "sample-cform.pdf");
		Assert.assertFalse(profilePreview.formTitle.getText().contains(properties.getProperty("error16")));
		int initialCount = profilePreview.getFileCount();
		profilePreview.clickConsentFormFile();
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "sample-cform.pdf");
	}
	
	@Test(priority=6, description="This test verifies that the consent form can be deleted")
	public void verifyConsentFormDelete()
	{
		profilePreview.clickFormEdit();
		consentForm.clickCFDelete();
		Assert.assertTrue(consentForm.isCFDeleted("sample-cform.pdf"), "Consent form file should have been deleted");
		Assert.assertTrue(consentForm.isSelectFileActive(), "'Select From Files' button should turn active");
	}
	
	@Test(priority=7, description="This test verifies that 'Required to apply' text is displayed for consent form after it is deleted")
	public void verifyCFReqToApply()
	{
		Assert.assertTrue(profilePreview.formTitle.getText().contains(properties.getProperty("error16")));
		profilePreview.clickFormEdit();
		Assert.assertTrue(consentForm.getCFReqText().contains(properties.getProperty("error16")));
	}
	
	@Test(priority=8, description="This test verifies that validation error occurs for required to save field: Do your parents/guardians agree with your participation to this Japanese Placement?")
	public void verifyAgreeCBReqToSave()
	{
		profilePreview.clickFormEdit();
		consentForm.deSelectAgreeCheckbox();
		consentForm.clickSave();
		Assert.assertEquals(consentForm.getAlert(), properties.getProperty("alert3"));		
		Assert.assertEquals(consentForm.getReqToSaveText(), properties.getProperty("error1"));
	}
	
	@Test(priority=9, description="This test verifies that validation error for invalid consent form file format occurs")
	public void verifyCFInvalidFileFormat()
	{
		profilePreview.clickFormEdit();
		consentForm.uploadFile(System.getProperty("user.dir")+"/test-data/file-sample-doc.doc");
		Assert.assertTrue(consentForm.getCFErrorText().contains(properties.getProperty("error29")));
	}
	
	@Test(priority=10, description="This test verifies that validation error for invalid size for consent form file occurs")
	public void verifyCFInvalidFileSize()
	{
		profilePreview.clickFormEdit();
		consentForm.uploadFile(System.getProperty("user.dir")+"/test-data/samplepdf-above2mb.pdf");
		Assert.assertTrue(consentForm.getCFErrorText().contains(properties.getProperty("error27")));
	}
	
	@Test(priority=11, description="This test verifies that 'Cancel' from Japanese Placement Consent Form redirectes user to preview page ")
	public void verifyCFCancelRed()
	{
		profilePreview.clickFormEdit();
		consentForm.clickCancel();
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
