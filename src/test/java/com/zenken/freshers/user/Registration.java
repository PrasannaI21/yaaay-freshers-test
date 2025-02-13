package com.zenken.freshers.user;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.ApiException;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import com.zenken.freshers.pages.user.RegisterPage;
import com.zenken.freshers.pages.user.VerifyEmailPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Registration extends BaseTest{
	
	RegisterPage register;
	VerifyEmailPage verifyEmail;
	Properties properties;
	private String emailAddress;
	private UUID inboxId;
	ApiClient apiClient;
	Email email;
	
	final Long TIMEOUT_MILLIS = 30000L;
	InboxControllerApi inboxControllerApi;
	WaitForControllerApi waitForControllerApi;
	
	@BeforeMethod
	public void setUpTest(Method method) throws IOException
	{
		navigateTo("/register/");
		register = new RegisterPage(driver);
		verifyEmail = new VerifyEmailPage(driver);
		properties = getProperties();
		apiClient = new ApiClient();
		apiClient.setApiKey(properties.getProperty("API_KEY"));
		inboxControllerApi = new InboxControllerApi(apiClient);
		waitForControllerApi = new WaitForControllerApi(apiClient);
		setCurrentTestMethod(method.getName());
	}
	
	@Test(description="This test verifies that the Registration page title is correct")
	public void verifyRegTitle()
	{
		String title = register.getTitle();
		Assert.assertEquals(title, properties.getProperty("title1"), "Title is incorrect");
	}
	
	@Test(description="This test verifies that expected texts are displayed at the top of the page")
	public void verifyRegTexts()
	{
		List<String> texts = register.getTexts();
		Assert.assertEquals(texts.get(0), properties.getProperty("Text1"));
		Assert.assertEquals(texts.get(1), properties.getProperty("Text2"));
	}
	
	@Test(description="This test verifies that expected placeholders are displayed")
	public void verifyRegPlaceholders()
	{
		List<String> pTexts = register.getPlaceholders();
		for(int i=0; i<pTexts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeholderText"+(i+1));
			Assert.assertEquals(pTexts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test(description="This test verifies that the text box for 'Last Name' field turns "
			+ "inactive after clicking on checkbox")
	public void verifyRegTbDisable()
	{
		boolean state = register.getTextBoxState();
		Assert.assertFalse(state, "Text Box remained active after clicking");
	}
	
	@Test(description="This test verifies that college can be selected from the dropdown")
	public void verifyRegCollegeSelect()
	{
		String actualCollege = properties.getProperty("college");
		String selectedCollege = register.selectCollege(actualCollege);
		Assert.assertEquals(selectedCollege, actualCollege, "College name displayed is incorrect");
	}
	
	@Test(description="This test verifies that year can be selected from the dropdown")
	public void verifyRegYearSelect()
	{
		String selectedYear = register.selectYear("2029");
		Assert.assertEquals(selectedYear, "2029", "Year displayed is incorrect");
	}
	
	@Test(description="This test verifies that radio button can be selected")
	public void verifyRegRadioSelect()
	{
		boolean state = register.getRadioState();
		Assert.assertTrue(state, "Radio button is not selected");
	}
	
	@Test(description="This test verifies that Terms PDF is opened in a separate tab")
	public void verifyRegTermsPDF()
	{
		register.clickTerms();
		ArrayList<String> tabs = register.switchTab(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedTerms = properties.getProperty("terms");
		String actualTerms = driver.getCurrentUrl();
		Assert.assertEquals(actualTerms, expectedTerms, "The opened URL is not the expected PDF URL.");
	}
	
	@Test(description="This test verifies that the link for Privacy Policy is opened "
			+ "in a separate tab")
	public void verifyRegPolicy()
	{
		register.clickPolicy();
		ArrayList<String> tabs = register.switchTab(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPolicy = properties.getProperty("policy");
		String actualPolicy = driver.getCurrentUrl();
		Assert.assertEquals(actualPolicy, expectedPolicy, "The opened URL is not the expected policy site");
	}
	
	@Test(description="This test verifies that the checkboxes 'Yaaay Midcareer' and "
			+ "'Zenken Newsletter' are selected by default")
	public void verifyRegCheckBoxState()
	{
		boolean state1 = register.getCheckBox3State();
		boolean state2 = register.getCheckBox4State();
		Assert.assertTrue(state1, "Midcareer checkbox is not selected");
		Assert.assertTrue(state2, "Newsletter checkbox is not selected");
	}
	
	@Test(description="This test verifies that user is redirected to Login page after "
			+ "clicking on the login link")
	public void verifyRegLogInUrl()
	{
		String url = register.getLogInUrl();
		Assert.assertEquals(url, properties.getProperty("url"), "Did not redirect to login page");
	}
	
	@Test(priority=1, description="This test verifies that the user can create an account")
	public void verifyUserRegistration() throws ApiException
	{
		//MailSlurpの受信箱を作成 
		InboxDto inbox = inboxControllerApi.createInboxWithDefaults();
		emailAddress = inbox.getEmailAddress();
		inboxId = inbox.getId();
		//登録フォームを記入
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail(emailAddress);
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		//メール認証ページへの遷移を確認
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("/verify-email/"));
	}
	
	@Test(priority=2, description="This test verifies that user receives a verification "
			+ "email to the user's email address")
	public void verifyRegEmail() throws ApiException
	{
		email = waitForControllerApi.waitForLatestEmail(inboxId, TIMEOUT_MILLIS, true, null, null, null, null);
		String subject = email.getSubject();
		Assert.assertEquals(subject, properties.get("subject2"), "Subject is not correct");
	}
	
	@Test(priority=3, description="This test verifies that expected text for headline "
			+ "is displayed on the Email Verification page")
	public void verifyEmailVerPageTitle()
	{
		String text = verifyEmail.getTitle();
		Assert.assertEquals(text, properties.getProperty("headline"));
	}
	
	@Test(priority=4, description="This test verifies that the email address displayed "
			+ "on the Email Verification page is correct")
	public void verifyRegEmailAddress()
	{
		String mainText = verifyEmail.getMainText();
		log("Step 1: Verify that email address displayed is "+ "\""+emailAddress+"\"");
		Assert.assertTrue(mainText.contains(emailAddress), "Email Address displayed is incorrect");
	}
	
	@Test(priority=5, description="This test verifies that the email is resent after "
			+ "clicking on 'Resend' link")
	public void verifyRegEmailResend() throws ApiException
	{
		verifyEmail.clickResend();
		String alert= verifyEmail.getAlert();
		Assert.assertEquals(alert, properties.getProperty("alert"));
	}
	
	@Test(priority=6, description="This test verifies that the link mentioned in the "
			+ "mail redirects user to the profile preview page of his/her account")
	public void verifyEmailVerificationLink() throws ApiException
	{
		email = waitForControllerApi.waitForLatestEmail(inboxId, TIMEOUT_MILLIS, true, null, null, null, null);
		String emailBody = email.getBody();
		String verificationLink = register.extractVerificationLink(emailBody);
		register.openInNewTab(verificationLink);
		register.switchTab(1);
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, properties.getProperty("url2"), "Link did not open profile page");
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'First Name'")
	public void verifyRegFirstNameReq()
	{
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Last Name'")
	public void verifyRegLastNameReq()
	{
		register.enterName("Sherlock");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'College'")
	public void verifyRegCollegeReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'USN / Registration Number'")
	public void verifyRegUSNReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Expected Graduation Year'")
	public void verifyRegYearReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Are you interested in studying Japanese language?'")
	public void verifyRegInterestReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Email'")
	public void verifyRegEmailReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Password'")
	public void verifyRegPassReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'I agree to the Terms and Privacy Policy'")
	public void verifyRegTermsAgreementReq()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickSubmit();
		String text = register.getTermsRequiredText();
		Assert.assertEquals(text, properties.getProperty("error2"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'First Name'")
	public void verifyRegCharExceedFirstName()
	{
		register.enterName(properties.getProperty("exceed30"));
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getCharExceedText();
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'Middle Name'")
	public void verifyRegCharExceedMiddleName()
	{
		register.enterName("Sherlock");
		register.enterMiddleName(properties.getProperty("exceed30"));
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getCharExceedText();
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'Last Name'")
	public void verifyRegCharExceedLastName()
	{
		register.enterName("Sherlock");
		register.enterLastName(properties.getProperty("exceed30"));
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getCharExceedText();
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'USN / Registration Number'")
	public void verifyRegCharExceedUsn()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn(properties.getProperty("exceed30"));
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getCharExceedText();
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for invalid input "
			+ "occurs for field: 'USN / Registration Number'")
	public void verifyRegInvalidUsn()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345 12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidFormatText();
		Assert.assertEquals(text, properties.getProperty("error4"));
	}
	
	@Test(description="This test verifies that validation error for invalid input "
			+ "occurs for field: 'Email'")
	public void verifyRegInvalidEmail()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("admin@example..com");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidEmailText();
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test(description="This test verifies that validation error for same email occurs"
			+ "for 'Email' field")
	public void verifyRegUsedEmail()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user5@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getUsedEmailText();
		Assert.assertEquals(text, properties.getProperty("error6"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when fewer than 8 characters are entered")
	public void verifyRegInvalidPass1()//8文字なし
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Passw_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidPass1Text();
		Assert.assertEquals(text, properties.getProperty("error7"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when password entered does not contain a number")
	public void verifyRegInvalidPass2()//数字なし
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_#");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidPass2Text();
		Assert.assertEquals(text, properties.getProperty("error8"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when password entered does not contain any uppercase character")
	public void verifyRegInvalidPass3()//大文字なし
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidPass3Text();
		Assert.assertEquals(text, properties.getProperty("error9"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when password entered does not any special characters")
	public void verifyRegInvalidPass4()//特殊文字なし
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password12");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidPass4Text();
		Assert.assertEquals(text, properties.getProperty("error10"));
	}
}
