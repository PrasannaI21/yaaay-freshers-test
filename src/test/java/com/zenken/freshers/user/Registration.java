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
	public void verifyTitle()
	{
		log("Step 1: Verify that title name is correct");
		String title = register.getTitle();
		Assert.assertEquals(title, properties.getProperty("title1"), "Title is incorrect");
	}
	
	@Test(description="This test verifies that expected texts are displayed at the top of the page")
	public void verifyTexts()
	{
		List<String> texts = register.getTexts();
		log("Step 1: Verify that texts displayed are correct");
		Assert.assertEquals(texts.get(0), properties.getProperty("Text1"));
		Assert.assertEquals(texts.get(1), properties.getProperty("Text2"));
	}
	
	@Test(description="This test verifies that expected placeholders are displayed")
	public void verifyPlaceholders()
	{
		List<String> pTexts = register.getPlaceholders();
		log("Step 1: Verify that placeholders for all text boxes are correct");
		for(int i=0; i<pTexts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeholderText"+(i+1));
			Assert.assertEquals(pTexts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test(description="This test verifies that the text box for 'Last Name' field turns "
			+ "inactive after clicking on checkbox")
	public void verifyTextBoxDisable()
	{
		log("Step 1: Click on checkbox for 'Last Name' field");
		log("Step 2: Verify that corresponding text box turns inactive");
		boolean state = register.getTextBoxState();
		Assert.assertFalse(state, "Text Box remained active after clicking");
	}
	
	@Test(description="This test verifies that college can be selected from the dropdown")
	public void verifyCollegeSelect()
	{
		log("Step 1: Select option \"NHCE (New Horizon College of Engineering)\" from the 'College' dropdown");
		String actualCollege = properties.getProperty("college");
		String selectedCollege = register.selectCollege(actualCollege);
		log("Step 2: Verify that the selected college name is displayed accordingly");
		Assert.assertEquals(selectedCollege, actualCollege, "College name displayed is incorrect");
	}
	
	@Test(description="This test verifies that year can be selected from the dropdown")
	public void verifyYearSelect()
	{
		log("Step 1: Select option \"2029\" from the 'Expected Graduation Year' dropdown");
		String selectedYear = register.selectYear("2029");
		log("Step 2: Verify that the selected year is displayed accordingly");
		Assert.assertEquals(selectedYear, "2029", "Year displayed is incorrect");
	}
	
	@Test(description="This test verifies that radio button can be selected")
	public void verifyRadioSelect()
	{
		log("Step 1: Click on 'Yes' option for 'Are you interested in studying Japanese language?' field");
		boolean state = register.getRadioState();
		log("Step 2: Verify that the radio button is selected");
		Assert.assertTrue(state, "Radio button is not selected");
	}
	
	@Test(description="This test verifies that Terms PDF is opened in a separate tab")
	public void verifyTermsPDF()
	{
		log("Step 1: Click on 'Terms' link");
		register.clickTerms();
		ArrayList<String> tabs = register.switchTab(1);
		log("Step 2: Verify that new tab is opened");
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedTerms = properties.getProperty("terms");
		String actualTerms = driver.getCurrentUrl();
		log("Step 3: Verify that PDF url is correct");
		Assert.assertEquals(actualTerms, expectedTerms, "The opened URL is not the expected PDF URL.");
	}
	
	@Test(description="This test verifies that the link for Privacy Policy is opened "
			+ "in a separate tab")
	public void verifyPolicy()
	{
		log("Step 1: Click on 'Privacy Policy' link");
		register.clickPolicy();
		ArrayList<String> tabs = register.switchTab(1);
		log("Step 2: Verify that new tab is opened");
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPolicy = properties.getProperty("policy");
		String actualPolicy = driver.getCurrentUrl();
		log("Step 3: Verify that Privacy Policy url is correct");
		Assert.assertEquals(actualPolicy, expectedPolicy, "The opened URL is not the expected policy site");
	}
	
	@Test(description="This test verifies that the checkboxes 'Yaaay Midcareer' and "
			+ "'Zenken Newsletter' are selected by default")
	public void verifyCheckBoxState()
	{
		boolean state1 = register.getCheckBox3State();
		boolean state2 = register.getCheckBox4State();
		log("Step 1: Verify that the checkbox for 'Yaaay Midcareer' is selected");
		Assert.assertTrue(state1, "Midcareer checkbox is not selected");
		log("Step 1: Verify that the checkbox for 'Zenken Newsletter' is selected");
		Assert.assertTrue(state2, "Newsletter checkbox is not selected");
	}
	
	@Test(description="This test verifies that user is redirected to Login page after "
			+ "clicking on the login link")
	public void verifyLogInUrl()
	{
		log("Step 1: Click on 'Log in' link");
		String url = register.getLogInUrl();
		log("Step 2: Verify that URL loaded is for the login page");
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
		log("Step 1: Enter \"Sherlock\" in the text box for 'First Name' field");
		register.enterName("Sherlock");
		log("Step 2: Enter \"Holmes\" in the text box for 'Last Name' field");
		register.enterLastName("Holmes");
		log("Step 3: Select \"NMIT (Nitte Meenakshi Institute of Technology)\" from the 'College' dropdown");
		register.selectCollege(properties.getProperty("college2"));
		log("Step 4: Enter \"12345\" in the text box for 'USN / Registration Number' field");
		register.enterUsn("12345");
		log("Step 5: Select \"2030\" from the 'Expected Graduation Year' dropdown");
		register.selectYear("2030");
		log("Step 6: Click 'Maybe' option for 'Are you interested in studying Japanese language?' field");
		register.selectInterest();
		log("Step 7: Enter "+"\""+emailAddress+"\""+" in the the text box for 'Email' field");
		register.enterEmail(emailAddress);
		log("Step 8: Enter \"Password_1\" in the text box for 'Password' field");
		register.enterPassword("Password_1");
		log("Step 9: Select checkbox for terms agreement");
		register.clickTermsCheckbox();
		log("Step 10: Click on 'Create Account' button");
		register.clickSubmit();
		//メール認証ページへの遷移を確認
		String currentUrl = driver.getCurrentUrl();
		log("Step 11: Verify that user is redirected to Email Verification page");
		Assert.assertTrue(currentUrl.contains("/verify-email/"));
	}
	
	@Test(priority=2, description="This test verifies that user receives a verification "
			+ "email to the user's email address")
	public void verifyEmail() throws ApiException
	{
		log("Step 1: Wait for the email to be received");
		email = waitForControllerApi.waitForLatestEmail(inboxId, TIMEOUT_MILLIS, true, null, null, null, null);
		String subject = email.getSubject();
		log("Step 2: Verify that mail's subject equals \"Please verify your email\"");
		Assert.assertEquals(subject, "Please verify your email", "Subject is not correct");
	}
	
	@Test(priority=3, description="This test verifies that expected text for headline "
			+ "is displayed on the Email Verification page")
	public void verifyEmailPageTitle()
	{
		String text = verifyEmail.getTitle();
		log("Step 1: Verify that the headline text is \"Verify your Email\"");
		Assert.assertEquals(text, properties.getProperty("headline"));
	}
	
	@Test(priority=4, description="This test verifies that the email address displayed "
			+ "on the Email Verification page is correct")
	public void verifyEmailAddress()
	{
		String mainText = verifyEmail.getMainText();
		log("Step 1: Verify that email address displayed is "+ "\""+emailAddress+"\"");
		Assert.assertTrue(mainText.contains(emailAddress), "Email Address displayed is incorrect");
	}
	
	@Test(priority=5, description="This test verifies that the email is resent after "
			+ "clicking on 'Resend' link")
	public void verifyEmailResend() throws ApiException
	{
		log("Step 1: Click on 'Resend' link");
		verifyEmail.clickResend();
		String alert= verifyEmail.getAlert();
		log("Step 2: Verify that alert for new verification link is displayed");
		Assert.assertEquals(alert, properties.getProperty("alert"));
	}
	
	@Test(priority=6, description="This test verifies that the link mentioned in the "
			+ "mail redirects user to the profile preview page of his/her account")
	public void verifyEmailVerificationLink() throws ApiException
	{
		log("Step 1: Wait for the email to be received");
		email = waitForControllerApi.waitForLatestEmail(inboxId, TIMEOUT_MILLIS, true, null, null, null, null);
		String emailBody = email.getBody();
		String verificationLink = register.extractVerificationLink(emailBody);
		log("Step 2: Click on the verification link in the mail");
		register.openInNewTab(verificationLink);
		register.switchTab(1);
		String currentUrl = driver.getCurrentUrl();
		log("Step 3: Verify that the page opened is profile preview by the URL");
		Assert.assertEquals(currentUrl, properties.getProperty("url2"), "Link did not open profile page");
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'First Name'")
	public void verifyFirstNameRequired()
	{
		log("Step 1: Leave 'First Name' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'First Name' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Last Name'")
	public void verifyLastNameRequired()
	{
		log("Step 1: Leave 'Last Name' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'Last Name' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'College'")
	public void verifyCollegeRequired()
	{
		log("Step 1: Leave 'College' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'College' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'USN / Registration Number'")
	public void verifyUSNRequired()
	{
		log("Step 1: Leave 'USN / Registration Number' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'USN / Registration Number' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Expected Graduation Year'")
	public void verifyYearRequired()
	{
		log("Step 1: Leave 'Expected Graduation Year' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'Expected Graduation Year' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Are you interested in studying Japanese language?'")
	public void verifyInterestRequired()
	{
		log("Step 1: Leave 'Are you interested in studying Japanese language?' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'Are you interested in studying Japanese language?' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Email'")
	public void verifyEmailRequired()
	{
		log("Step 1: Leave 'Email' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'Email' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'Password'")
	public void verifyPasswordRequired()
	{
		log("Step 1: Leave 'Password' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getRequiredText();
		log("Step 4: Verify that validation error occurs for 'Password' field");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs for required "
			+ "field: 'I agree to the Terms and Privacy Policy'")
	public void verifyTermsAgreementRequired()
	{
		log("Step 1: Leave 'I agree to the Terms and Privacy Policy ' field blank");
		log("Step 2: Give input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getTermsRequiredText();
		log("Step 4: Verify that validation error occurs for 'I agree to the Terms and Privacy Policy' field");
		Assert.assertEquals(text, properties.getProperty("error2"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'First Name'")
	public void verifyCharExceedFirstName()
	{
		log("Step 1: Enter 31 characters in 'First Name' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName(properties.getProperty("exceed30"));
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getCharExceedText();
		log("Step 4: Verify that validation error for maximum characters occurs for 'First Name' field");
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'Middle Name'")
	public void verifyCharExceedMiddleName()
	{
		log("Step 1: Enter 31 characters in 'Middle Name' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
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
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getCharExceedText();
		log("Step 4: Verify that validation error for maximum characters occurs for 'Middle Name' field");
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'Last Name'")
	public void verifyCharExceedLastName()
	{
		log("Step 1: Enter 31 characters in 'Last Name' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName(properties.getProperty("exceed30"));
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getCharExceedText();
		log("Step 4: Verify that validation error for maximum characters occurs for 'Last Name' field");
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for maximum characters "
			+ "occurs for field: 'USN / Registration Number'")
	public void verifyCharExceedUsn()
	{
		log("Step 1: Enter 31 characters in 'USN / Registration Number' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn(properties.getProperty("exceed30"));
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getCharExceedText();
		log("Step 4: Verify that validation error for maximum characters occurs for 'USN / Registration Number' field");
		Assert.assertEquals(text, properties.getProperty("error3"));
	}
	
	@Test(description="This test verifies that validation error for invalid input "
			+ "occurs for field: 'USN / Registration Number'")
	public void verifyInvalidUsn()
	{
		log("Step 1: Enter \"12345 12345\" in 'USN / Registration Number' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345 12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getInvalidFormatText();
		log("Step 4: Verify that validation error for invalid input occurs for 'USN / Registration Number' field");
		Assert.assertEquals(text, properties.getProperty("error4"));
	}
	
	@Test(description="This test verifies that validation error for invalid input "
			+ "occurs for field: 'Email'")
	public void verifyInvalidEmail()
	{
		log("Step 1: Enter \"admin@example..com\" in 'Email' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("admin@example..com");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getInvalidEmailText();
		log("Step 4: Verify that validation error for invalid input occurs for 'Email' field");
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test(description="This test verifies that validation error for same email occurs"
			+ "for 'Email' field")
	public void verifyUsedEmail()
	{
		log("Step 1: Enter \"prasanna.inamdar+user5@zenken.co.jp\" in 'Email' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user5@zenken.co.jp");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getUsedEmailText();
		log("Step 4: Verify that validation error for same email occurs for 'Email' field");
		Assert.assertEquals(text, properties.getProperty("error6"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when fewer than 8 characters are entered")
	public void verifyIncorrectPassword1()//8文字なし
	{
		log("Step 1: Enter \"Passw_1\" in 'Password' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Passw_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getInvalidPass1Text();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertEquals(text, properties.getProperty("error7"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when password entered does not contain a number")
	public void verifyIncorrectPassword2()//数字なし
	{
		log("Step 1: Enter \"Password_#\" in 'Password' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password_#");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getInvalidPass2Text();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertEquals(text, properties.getProperty("error8"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when password entered does not contain any uppercase character")
	public void verifyIncorrectPassword3()//大文字なし
	{
		log("Step 1: Enter \"password_1\" in 'Password' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("password_1");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getInvalidPass3Text();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertEquals(text, properties.getProperty("error9"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when password entered does not any special characters")
	public void verifyIncorrectPassword4()//特殊文字なし
	{
		log("Step 1: Enter \"Password12\" in 'Password' field's text box");
		log("Step 2: Give valid input for the rest of the mandatory fields");
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("prasanna.inamdar+user6@zenken.co.jp");
		register.enterPassword("Password12");
		register.clickTermsCheckbox();
		log("Step 3: Click on 'Create Account' button");
		register.clickSubmit();
		String text = register.getInvalidPass4Text();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertEquals(text, properties.getProperty("error10"));
	}
}
