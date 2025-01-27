package com.zenken.freshers.user;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.ApiException;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import com.zenken.freshers.data.DataReader;
import com.zenken.freshers.pages.admin.AdminLoginPage;
import com.zenken.freshers.pages.admin.EditEvents;
import com.zenken.freshers.pages.user.ApplyFormPage;
import com.zenken.freshers.pages.user.EditBasicInformationPage;
import com.zenken.freshers.pages.user.EditConsetFormPage;
import com.zenken.freshers.pages.user.EditFieldOfStudyPage;
import com.zenken.freshers.pages.user.EditJobPreferencesPage;
import com.zenken.freshers.pages.user.EditPhotoPage;
import com.zenken.freshers.pages.user.EditSkillsPage;
import com.zenken.freshers.pages.user.JobsPage;
import com.zenken.freshers.pages.user.ProfilePreviewPage;
import com.zenken.freshers.pages.user.RegisterPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class JobsApply extends BaseTest{

	JobsPage jobs;
	EditEvents editEvents;
	AdminLoginPage adminLogin;
	RegisterPage registerPage;
	ApplyFormPage applyForm;
	Properties properties;
	private static final String DATE_TIME_1 = "12/01/2028\t03:301";
	private static final String DATE_TIME_2 = "07/25/2027\t03:291";
	private static boolean isUserCreated = false;
	String emailAddress;
	WaitForControllerApi wait;
	InboxDto inbox;
	String companyName;
	String jobTitle;
	String jobTitle1;
	
	@BeforeMethod
	public void setupJobsApply() throws IOException, ApiException
	{
		jobs = new JobsPage(driver);
		editEvents = new EditEvents(driver);
		adminLogin = new AdminLoginPage(driver);
		registerPage = new RegisterPage(driver);
		applyForm = new ApplyFormPage(driver);
		properties = getProperties();
		navigateTo("/");
		if(!isUserCreated) {
			ApiClient apiClient = new ApiClient();
			apiClient.setApiKey(properties.getProperty("API_KEY"));
			InboxControllerApi inboxCon = new InboxControllerApi(apiClient);
			inbox = inboxCon.createInboxWithDefaults();
			emailAddress = inbox.getEmailAddress();
			System.out.println(emailAddress);
			navigateTo("/register/");
			registerPage.registerUser(emailAddress);
			wait = new WaitForControllerApi(apiClient);
			Email email = wait.waitForLatestEmail(inbox.getId(), 30000L, true, null, null, null, null);
			jobs.openInNewTab(registerPage.extractVerificationLink(email.getBody()));
			isUserCreated = true;
		}
	}
	
	@Test(description="This test verifies that apply area is not displayed when the user"
			+ " has not logged in")
	public void verifyApplyAreaDisplay()
	{
		navigateTo("/jobs/9df004f4/");
		Assert.assertFalse(jobs.isApplyAreaDisplayed(), "Apply area should not be "
				+ "displayed when user has not logged in");
	}
	
	@Test(description="This test verifies that expected text is displayed on apply frame"
			+ " when application period has not started/has ended")
	public void verifyApplyAreaText()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/9df004f4/");
		Assert.assertEquals(jobs.getApplyAreaText(), properties.get("applyframe"));		
	}
	
	@Test(description="This test verifies that the application deadline (date and time) "
			+ "displayed on Apply area is in expected format and in IST")
	public void verifyApplicationDeadline() throws IOException
	{
		boolean toggle = Boolean.parseBoolean(properties.getProperty("toggleValue"));
		navigateTo("/admin/");
		adminLogin.loginAdmin();
		navigateTo("/admin/events/108/edit/");
		String inputDateTime = toggle ? DATE_TIME_2 : DATE_TIME_1;
		editEvents.setApplicationDeadline(inputDateTime);
		editEvents.clickSave();
		String deadline = "Application Deadline: "+jobs.convertJSTtoIST(inputDateTime);	
		navigateTo("/");
		navigateTo("/jobs/a0ca77ac/");
		Assert.assertEquals(jobs.getAppDeadlineText(), deadline);
		DataReader.saveProperty("toggleValue", String.valueOf(!toggle));
	}
	
	@Test(priority=0, description="This test verifies that user is redirected to job details page "
			+ "when user tries to directly access the apply form URL when user's profile is incomplete")
	public void verifyApplyNotAllowed1()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"2a2c6d4e/");
	}
	
	@Test(priority=1, description="This test verifies that the popup prompting to complete the profile"
			+ " is displayed after clicking on 'Apply' when user's profile is incomplete")
	public void verifyProfileCompletePopup()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/b5dbad9e/");
		companyName = jobs.getCompanyName();
		jobTitle = jobs.getJobTitle();
		jobs.clickApply();
		Assert.assertTrue((jobs.getApplyPopupText().contains(properties.getProperty("applypopup1"))),
				"Incomplete profile popup is not displayed");
		Assert.assertTrue((jobs.getApplyPopupText().contains(properties.getProperty("applypopup2"))),
				"Incomplete profile popup is not displayed");
	}
	
	@Test(priority=2, description="This test verifies that user is redirected to profile preview page"
			+ " after clicking on 'Edit Profile' on the popup")
	public void verifyProfilePageURL()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/b5dbad9e/");
		jobs.clickApply();
		jobs.clickPopupButton();
		Assert.assertEquals(driver.getCurrentUrl(), properties.get("url2"));
	}
	
	@Test(priority=3, description="This test verifies that the final confirmation popup is displayed"
			+ " after clicking on 'Apply' when user's profile is complete")
	public void verifyFinalConfPopup1() throws InterruptedException
	{
		jobs.loginUser(emailAddress, "Password_1");
		fillProfile();
		navigateTo("/jobs/b5dbad9e/");
		jobs.clickApply();
		Assert.assertTrue((jobs.getApplyPopupText().contains(properties.getProperty("applypopup3"))),
				"Final confirmation popup is not displayed");
		Assert.assertTrue((jobs.getApplyPopupText().contains(properties.getProperty("applypopup4"))),
				"Final confirmation popup is not displayed");
	}
	
	@Test(priority=4, description="This test verifies that user is successfully able to apply to the job")
	public void verifyJobApplication1() throws ApiException, InterruptedException
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/b5dbad9e/");
		jobs.clickApply();
		jobs.clickPopupButton();
		Assert.assertEquals(driver.getCurrentUrl(), "https://freshers.dspf-dev.com/jobs/b5dbad9e/");
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert7"));
		Assert.assertEquals(jobs.getApplyAreaText(), "Applied");
	}
	
	@Test(priority=5, description="This test verifies that the user receives an expected email after"
			+ " applying to the job and that the mail content is correct")
	public void verifyApplyMailReception1() throws ApiException
	{
		Email email = wait.waitForLatestEmail(inbox.getId(), 30000L, true, null, null, null, null);
		Assert.assertEquals(email.getSubject(), properties.get("subject3"));
		Assert.assertTrue(email.getBody().contains("Company: "+companyName),
				"Company Name mentioned in the mail is not correct");
		Assert.assertTrue(email.getBody().contains("Position: "+jobTitle),
				"Job Title mentioned in the mail is not correct");
		jobs.openInNewTab(jobs.extractLinkFromMail(properties.getProperty("url21"), email.getBody()));
		jobs.switchTabs(1);
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"b5dbad9e/");
	}
	
	@Test(priority=6, description="This test verifies that user is redirected to job details page "
			+ "when user tries to directly access the apply form URL for 募集期間外 case")
	public void verifyApplyNotAllowed2()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/fc777807/apply-form/");
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"fc777807/");
	}
	
	@Test(priority=7, description="This test verifies that the user is redirected to Apply Form page"
			+ " upon clicking on 'Apply' when user's profile is complete")
	public void verifyApplyFormURL()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/");
		jobTitle1 = jobs.getJobTitle();
		jobs.clickApply();
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"2a2c6d4e/apply-form/");
		Assert.assertEquals(applyForm.getApplyFormHeadline(), "Application Form");
	}
	
	@Test(priority=8, description="This test verifies that the fixed text and the free text set by"
			+ " CS displayed on application form page are correct")
	public void verifyApplyFormText()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		Assert.assertEquals(applyForm.getApplyFormFixedText(), properties.get("formFixedText"));
		Assert.assertEquals(applyForm.getApplyFormFreeText(), properties.get("formFreeText"));
	}
	
	@Test(priority=9, description="This test verifies that user is redirected to job details "
			+ "page upon clicking on 'Cancel' on application form page")
	public void verifyApplyFormCancel()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		applyForm.clickCancel();
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"2a2c6d4e/");
	}
	
	@Test(priority=10, description="This test verifies that validation error occurs when user tries "
			+ "to apply without filling in mandatory fields")
	public void verifyApplyFormMandatoryFields()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		applyForm.enterAnswer2();
		applyForm.enterAnswer3();
		applyForm.enterAnswer4("I don't want to!");
		applyForm.clickSave();
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert8"));
		Assert.assertTrue(applyForm.getQaBox1Text().contains(properties.getProperty("error1"))
				, "Question 1 should be required to apply");
		applyForm.resetAnswers();
		applyForm.enterAnswer1("Because I was bored!");
		applyForm.enterAnswer3();
		applyForm.enterAnswer4("I don't want to!");
		applyForm.clickSave();
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert8"));
		Assert.assertTrue(applyForm.getQaBox2Text().contains(properties.getProperty("error1"))
				, "Question 2 should be required to apply");
		applyForm.resetAnswers();
		applyForm.enterAnswer1("Because I was bored!");
		applyForm.enterAnswer2();
		applyForm.enterAnswer4("I don't want to!");
		applyForm.clickSave();
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert8"));
		Assert.assertTrue(applyForm.getQaBox3Text().contains(properties.getProperty("error1"))
				, "Question 3 should be required to apply");
		applyForm.resetAnswers();
		applyForm.enterAnswer1("Because I was bored!");
		applyForm.enterAnswer2();
		applyForm.enterAnswer3();
		applyForm.clickSave();
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert8"));
		Assert.assertTrue(applyForm.getQaBox4Text().contains(properties.getProperty("error1"))
				, "Question 4 should be required to apply");
	}
	
	@Test(priority=11, description="This test verifies that validation error occurs when more than "
			+ "5000 characters are entered in the text box on application form page")
	public void verifyApplyFormCharExceed()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		applyForm.enterAnswer1(properties.getProperty("exceed5k"));
		applyForm.enterAnswer2();
		applyForm.enterAnswer3();
		applyForm.enterAnswer4("I don't want to!");
		applyForm.clickSave();
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert8"));
		Assert.assertTrue(applyForm.getQaBox1Text().contains(properties.getProperty("error23"))
				, "Question 1 character limit should be 5000");
		applyForm.resetAnswers();
		applyForm.enterAnswer1("Because I was bored!");
		applyForm.enterAnswer2();
		applyForm.enterAnswer3();
		applyForm.enterAnswer4(properties.getProperty("exceed5k"));
		applyForm.clickSave();
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert8"));
		Assert.assertTrue(applyForm.getQaBox4Text().contains(properties.getProperty("error23"))
				, "Question 4 character limit should be 5000");
	}
	
	@Test(priority=12, description="This test verifies that the final confirmation popup is displayed"
			+ " after clicking on 'Apply' when application form is filled with valid inputs")
	public void verifyFinalConfPopup2() throws InterruptedException
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		applyForm.enterAnswer1("Because I was bored!");
		applyForm.enterAnswer2();
		applyForm.enterAnswer3();
		applyForm.enterAnswer4("I don't want to!");
		applyForm.clickSave();
		Assert.assertTrue((jobs.getApplyPopupText().contains(properties.getProperty("applypopup3"))),
				"Final confirmation popup is not displayed");
		Assert.assertTrue((jobs.getApplyPopupText().contains(properties.getProperty("applypopup4"))),
				"Final confirmation popup is not displayed");
	}
	
	@Test(priority=13, description="This test verifies that user is successfully able to apply to the job"
			+ "from application form page")
	public void verifyJobApplication2() throws ApiException, InterruptedException
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		applyForm.enterAnswer1("Because I was bored!");
		applyForm.enterAnswer2();
		applyForm.enterAnswer3();
		applyForm.enterAnswer4("I don't want to!");
		applyForm.clickSave();
		jobs.clickPopupButton();
		Assert.assertEquals(driver.getCurrentUrl(), "https://freshers.dspf-dev.com/jobs/2a2c6d4e/");
		Assert.assertEquals(jobs.getApplySnackbarText(), properties.get("alert7"));
		Assert.assertEquals(jobs.getApplyAreaText(), "Applied");
	}
	
	@Test(priority=14, description="This test verifies that the user receives an expected email after"
			+ " applying to the job and that the mail content is correct")
	public void verifyApplyMailReception2() throws ApiException
	{
		Email email = wait.waitForLatestEmail(inbox.getId(), 30000L, true, null, null, null, null);
		Assert.assertEquals(email.getSubject(), properties.get("subject3"));
		Assert.assertTrue(email.getBody().contains("Company: "+companyName),
				"Company Name mentioned in the mail is not correct");
		Assert.assertTrue(email.getBody().contains("Position: "+jobTitle1),
				"Job Title mentioned in the mail is not correct");
		jobs.openInNewTab(jobs.extractLinkFromMail(properties.getProperty("url21"), email.getBody()));
		jobs.switchTabs(1);
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"2a2c6d4e/");	
	}
	
	@Test(priority=15, description="This test verifies that user is redirected to job details page "
			+ "when user tries to directly access the apply form URL for already applied job")
	public void verifyApplyNotAllowed3()
	{
		jobs.loginUser(emailAddress, "Password_1");
		navigateTo("/jobs/2a2c6d4e/apply-form/");
		Assert.assertEquals(driver.getCurrentUrl(), properties.getProperty("url21")+"2a2c6d4e/");
	}
	
	private void fillProfile() throws InterruptedException
	{
		ProfilePreviewPage profilePreview = new ProfilePreviewPage(driver);
		profilePreview.clickphotoEdit();
		EditPhotoPage photo = new EditPhotoPage(driver);
		photo.clickUploadPhoto();
		photo.uploadPhoto(System.getProperty("user.dir")+"/test-data/Sample Photo.png");
		photo.clickModalUpload();
		profilePreview.clickEdit(profilePreview.informationEdit);
		EditBasicInformationPage editBasicInfo = new EditBasicInformationPage(driver);
		editBasicInfo.enterBasicInfo();
		EditJobPreferencesPage editJobPref = new EditJobPreferencesPage(driver);
		profilePreview.clickEdit(profilePreview.preferenceEdit);
		editJobPref.selectPreferences(5, 12, 14);
		editJobPref.clickSave();
		EditFieldOfStudyPage editField = new EditFieldOfStudyPage(driver);
		profilePreview.clickEdit(profilePreview.fieldOfStudyEdit);
		editField.enterCoreOther("Core option");
		editField.clickSave();
		EditSkillsPage editSkills = new EditSkillsPage(driver);
		profilePreview.clickEdit(profilePreview.skillsEdit);
		editSkills.addSkill("p", 1, 1);
		editSkills.clickSave();
		EditConsetFormPage consentForm = new EditConsetFormPage(driver);
		profilePreview.clickFormEdit();
		consentForm.selectAgreeCheckbox();
		consentForm.uploadFile(System.getProperty("user.dir")+"/test-data/sample-cform.pdf");
		Assert.assertTrue(consentForm.isUploadComplete());
		consentForm.clickSave();
		profilePreview.monitorPhoto();
		Assert.assertFalse(profilePreview.isProfileComplete());
	}
}
