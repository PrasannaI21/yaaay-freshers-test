package com.zenken.freshers.user;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.user.EditCertificationsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Certifications extends BaseTest{

	ProfilePreviewPage profilePreview;
	EditCertificationsPage certification;
	Properties properties;
	private boolean certDelete = false;
	
	@BeforeMethod
	public void setUpTest() throws IOException
	{
		profilePreview = new ProfilePreviewPage(driver);
		certification = new EditCertificationsPage(driver);
		navigateTo("/");
		profilePreview.login("prasanna.inamdar+user2@zenken.co.jp", "Password_1");
		properties = getProperties();
		if(!certDelete)
		{
			int addedCert = profilePreview.getAddedCertCount();
			for(int i=0; i<addedCert; i++)
			{
				profilePreview.clickCertEdit(i*0);
				certification.deleteCert();
			}
			certDelete = true;
		}
	}
	
	@Test(priority=1, description="This test verifies that user is redirected to 'Certifications New' URL")
	public void verifyCertNewUrl()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Verify that URL and page headline are correct");
		Assert.assertEquals(profilePreview.getPageUrl(), properties.getProperty("url14"));
		Assert.assertEquals(certification.getCertHeadlineText(), "Certifications");
	}
	
	@Test(priority=2, description="This test verifies that the Certification page title is correct")
	public void verifyCertPageTitle()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Verify that title name is correct");
		Assert.assertEquals(profilePreview.getPageTitle(), properties.getProperty("title4"));
	}
	
	@Test(priority=3, description="This test verifies that expected placeholders are displayed")
	public void verifyCertPlaceholders()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Verify that placeholders displayed for 'Certification-Name, Details' are correct");
		List<String> attributes = certification.getCertPlaceholders();
		Assert.assertEquals(attributes.get(0), properties.get("certPH1"));
		Assert.assertEquals(attributes.get(1), properties.get("certPH2"));
	}
	
	@Test(priority=4, description="This test verifies that the certification is added with only required to save fields")
	public void verifyCertAddition()
	{
		String certName = "Certified Information Systems Security Professional (CISSP)";
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Enter 'Certification Name' :"+certName);
		certification.enterCertName(certName);
		log("Step 3: Click on 'Save' button");
		certification.clickSave();
		redirectionAssertions("#Certifications");
		log("Step 4: Verify that certification name is displayed on preview page");
		Assert.assertEquals(profilePreview.getCertNameValue(), certName);
	}
	
	@Test(priority=5, description="This test verifies that a certification is added with 'Certification Details'"
			)
	public void verifyCertAdditionWithDetails()
	{
		String certName = "Certified Ethical Hacker (CEH)";
		String certDetails = properties.getProperty("certDetails");
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Enter 'Certification Name'");
		certification.enterCertName(certName);
		log("Step 3: Enter 'Certification Details'");
		certification.enterCertDetails(certDetails);
		log("Step 4: Click on 'Save' button");;
		certification.clickSave();
		redirectionAssertions("#Certifications");
		log("Step 5: Verify that Certification- Name and Details are displayed on preview page");
		Assert.assertEquals(profilePreview.getCertNameValue(), certName);
		Assert.assertEquals(profilePreview.getCertDetailValue(), certDetails);
	}
	
	@Test(priority=6, description="This test verifies that a certification file can be uploaded and button to upload the files turns inactive")
	public void verifyCertFileUpload()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Upload a sample PDF file");
		certification.uploadFile(System.getProperty("user.dir")+"/test-data/Sample_Project.pdf");
		log("Step 3: Verify that the file is uploaded successfully");
		Assert.assertTrue(certification.isProgressBarDisplayed(), "Progress bar is not displayed");
		Assert.assertTrue(certification.isUploadComplete(), "File upload did not complete successfully");
		Assert.assertTrue(certification.getFileNameText().contains("Sample_Project.pdf"));
		log("Step 4: Verify that (file check in progress) is displayed");
		Assert.assertTrue(certification.getFileNameText().contains("(file check in progress)"));
		log("Step 5: Verify that 'Select From Files' button turns inactive");
		Assert.assertFalse(certification.isSelectFileActive(), "Select From Files button should be inactive");
	}
	
	@Test(priority=7, description="This test verifies that default certification name is correct")
	public void verifyDefaultCertName()
	{
		log("Step 1: Verify that the Certification Name is 'Untitled Certification'");
		Assert.assertEquals(profilePreview.getCertNameValue(), "Untitled Certification");
	}
	
	@Test(priority=11, description="This test verifies that the certification is added with file")
	public void verifyCertAdditionWithFile() throws InterruptedException
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Enter 'Certification Name'");
		certification.enterCertName("Microsoft Certified: Azure Fundamentals");
		log("Step 3: Upload a sample PDF file");
		certification.uploadFile(System.getProperty("user.dir")+"/test-data/Sample_Project-pdf.pdf");
		certification.getFileNameText();
		log("Step 4: Click on 'Save' button");
		certification.clickSave();
		redirectionAssertions("#Certifications");
		log("Step 5: Verify that expected file name with file check text are displayed on preview page");
		Assert.assertTrue(profilePreview.getCertFileValue().contains("Sample_Project-pdf.pdf"));
		Assert.assertTrue(profilePreview.getCertFileValue().contains("(file check in progress)"));
		log("Step 6: Verify that download link is displayed on preview page");
		Assert.assertEquals(profilePreview.getCertDlLink(10), "Sample_Project-pdf.pdf");
		int initialCount = profilePreview.getFileCount();
		log("Step 7: Click download link");
		profilePreview.clickCertFile();
		log("Step 8: Verify that file can be downloaded from preview page after refreshing");
		Assert.assertTrue(profilePreview.isFileDownloaded(initialCount), "File was not downloaded successfully");
		String downloadedFileName = profilePreview.getDownloadedFileName();
		Assert.assertEquals(downloadedFileName, "Sample_Project-pdf.pdf");
	}
	
	@Test(priority=9, description="This test verifies that expected 'Certification Edit' URL is displayed")
	public void verifyCertEditUrl()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Verify that user is redirected to certification edit URL");
		Assert.assertTrue(profilePreview.getPageUrl().startsWith(properties.getProperty("url15")));
		Assert.assertFalse(profilePreview.getPageUrl().contains("new"));
	}
	
	@Test(priority=10, description="This test verifies that the information saved for first certification is correct")
	public void verifyCertSavedData()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Verify that certification name is 'Certified Information Systems Security Professional (CISSP)'");
		Assert.assertEquals(certification.getCertNameText(), "Certified Information Systems Security Professional (CISSP)");
	}
	
	@Test(priority=8, description="This test verifies that the character count for Certification Details is dynamically updated")
	public void verifyCertCharCount()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		certification.clearProjectDetails();
		String details = properties.getProperty("certDetails");
		log("Step 2: Enter individual characters in Certification Details field");
		for(int i=0; i<details.length(); i++)
		{
			char currnetChar = details.charAt(i);
			certification.enterCertDetails2(Character.toString(currnetChar));
			String displayedCount = certification.getCertCharCount();
			Assert.assertEquals(displayedCount, String.valueOf(i + 1), "Character count mismatch at index "+i);
		}
		log("Step 3: Verify that character count is updated and the number displayed is correct");
		Assert.assertEquals(Integer.parseInt(certification.getCertCharCount()), 595);
	}
	
	@Test(priority=12, description="This test verifies that the certificate file is deleted")
	public void verifyCertFileDelete()
	{
		log("Step 1: Click on pen icon of third certification");
		profilePreview.clickCertEdit(2);//send index '2' during execution
		log("Step 2: Click on 'Delete' button for added file");
		certification.clickCertFileDelete();
		log("Step 3: Verify that certificate file is deleted");
		Assert.assertTrue(certification.isCertFileDeleted("Sample_Project.pdf"), "Certificate file should have been deleted");
		log("Step 4: Verify that 'Select From Files' button turns active");
		Assert.assertTrue(certification.isSelectFileActive(), "'Select From Files' button should turn active");
	}
	
	@Test(priority=13, description="This test verifies that 'Add Certification' button turns inactive once ten certifications are added")
	public void verifyCertCountExceed()
	{
		log("Step 1: Add ten certifications");
		int totalCertCount = 10;
		String[] data = {"Certification 1", "Certification 2", "Certification 3", "Certification 4", "Certification 5",
				"Certification 6", "Certification 7", "Certification 8", "Certification 9", "Certification 10"};
		int addedCerts = profilePreview.getAddedCertCount();
		int remainingCerts = totalCertCount - addedCerts;
		for(int i=0; i<remainingCerts; i++)
		{
			profilePreview.clickAddCertification();
			certification.enterCertName(data[i]);
			certification.clickSave();
		}
		log("Step 2: Verify that 'Add Certification' button turns inactive");
		Assert.assertTrue(profilePreview.isAddCertActive(), "'Add Certification' button should be inactive");
		log("Step 3: Verify that validation error for maximum certifications appears");
		Assert.assertTrue(profilePreview.getCertSectionText().contains(properties.getProperty("error28")), "Validation error for maximum projects did not occur.");
	}
	
	@Test(priority=14, description="This test verifies that a confirmation popup to delete the certification appears",
			retryAnalyzer=com.zenken.freshers.testcomponents.Retry.class)
	public void verifyCertDeletePopUp()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Click on 'Delete Certification' link");
		certification.clickCertDeleteLink();
		log("Step 3: Verify that popup is displayed and the text is correct");
		Assert.assertTrue(certification.getCertPopUpText().contains(properties.getProperty("popup3")), "Confirmation popup could not be displayed");
		Assert.assertTrue(certification.getCertPopUpText().contains(properties.getProperty("popup3.1")), "Confirmation popup could not be displayed");
	}
	
	@Test(priority=15, description="This test verifies that the certification is deleted successfully")
	public void verifyCertDelete()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Click on 'Delete Certification' link");
		certification.clickCertDeleteLink();
		log("Step 3: Click on 'Delete' on the confirmation popup");
		certification.clickDeleteCert();
		redirectionAssertions("#Certifications");
		log("Step 4: Verify that deleted certification name is not displayed on preview page");
		Assert.assertFalse(profilePreview.getProjectTitleValue(0).equals("Certified Information Systems Security Professional (CISSP)'"));//send "Certified Information Systems Security Professional (CISSP)'" during execution
		Assert.assertTrue(profilePreview.isAddCertButtonActive(), "Add Certification button should be active");
	}
	
	@Test(priority=16, description="This test verifies that validation error for maximum characters occurs: Certification Name")
	public void verifyCertNameCharExceed()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Enter 201 characters in Certification Name field");
		certification.enterCertName(properties.getProperty("exceed200"));
		log("Step 3: Click on 'Save' button");
		certification.clickSave();
		log("Step 4: Verify that validation error corresponds to exceeding the 200 character limit");
		Assert.assertEquals(certification.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(certification.getCertNameErrorText().contains(properties.getProperty("error17")));
	}
	
	@Test(priority=17, description="This test verifies that validation error for maximum characters occurs: Certification Details")
	public void verifyCertDetailsCharExceed()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Enter 5001 characters in Project Overview field");
		certification.enterCertDetails(properties.getProperty("exceed5k"));
		log("Step 3: Click on 'Save' button");
		certification.clickSave();
		log("Step 4: Verify that validation error corresponds to exceeding the 5000 character limit");
		Assert.assertEquals(certification.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(certification.getCertDetailsErrorText().contains(properties.getProperty("error23")));
	}
	
	@Test(priority=18, description="This test verifies that validation error for required to save field occurs: Certification Name")
	public void verifyCertNameRequired()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Click on 'Save' button");
		certification.clickSave();
		log("Step 3: Verify that validation error corresponds to 'Required to save' for Certification Name field");
		Assert.assertEquals(certification.getAlert(), properties.getProperty("alert3"));		
		Assert.assertTrue(certification.getCertNameErrorText().contains(properties.getProperty("error1")));
	}
	
	@Test(priority=19, description="This test verifies that validation error for invalid certificate file format occurs")
	public void verifyCertInvalidFileFormat()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Upload a file in doc format");
		certification.uploadFile(System.getProperty("user.dir")+"/test-data/file-sample-doc.doc");
		log("Step 3: Verify that validation error corresponds to file type: pdf");
		Assert.assertTrue(certification.getCertFileErrorText().contains(properties.getProperty("error29")));
	}
	
	@Test(priority=20, description="This test verifies that validation error for invalid size for certificate file occurs")
	public void verifyCertInvalidFileSize()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Upload a PDF file having size more than 2mb");
		certification.uploadFile(System.getProperty("user.dir")+"/test-data/samplepdf-above2mb.pdf");
		log("Step 3: Verify that validation error corresponds to file size less than 2048 kilobytes");
		Assert.assertTrue(certification.getCertFileErrorText().contains(properties.getProperty("error27")));
	}
	
	@Test(priority=21, description="This test verifies that 'Cancel' from New Certification redirectes user to preview page ")
	public void verifyCertCancelNewRed()
	{
		log("Step 1: Click on 'Add Certification' button");
		profilePreview.clickAddCertification();
		log("Step 2: Click on 'Cancel' button");
		certification.clickCancel();
		log("Step 3: Verify that user is redirected to 'Certification' section of profile page");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#Certifications"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.certificationsSection);
		Assert.assertTrue(state, "Certifications section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.certificationsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
	
	@Test(priority=22, description="This test verifies that 'Cancel' from Edit Certification redirectes user to preview page ")
	public void verifyCertCancelEditRed()
	{
		log("Step 1: Click on pen icon for first certification");
		profilePreview.clickCertEdit(0);
		log("Step 2: Click on 'Cancel' button");
		certification.clickCancel();
		log("Step 3: Verify that user is redirected to 'Certification' section of profile page");
		String url = profilePreview.getPageUrl();
		Assert.assertTrue(url.contains("#Certifications"), "The URL does not contain the expected anchor");
		boolean state = profilePreview.getSectionDisplay(profilePreview.certificationsSection);
		Assert.assertTrue(state, "Certifications section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.certificationsAnchor);
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
		boolean state = profilePreview.getSectionDisplay(profilePreview.certificationsSection);
		Assert.assertTrue(state, parameter+" section is not displayed on the page");
		String attribute = profilePreview.getAnchorLinkAttribute(profilePreview.certificationsAnchor);
		Assert.assertTrue(attribute.contains("current"), "The correct tab is not selected");
	}
}
