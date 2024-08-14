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
	
	@Test
	public void verifyTitle()
	{
		String title = register.getTitle();
		Assert.assertEquals(title, properties.getProperty("title1"), "Title is incorrect");
	}
	
	@Test
	public void verifyTexts()
	{
		List<String> texts = register.getTexts();
		Assert.assertEquals(texts.get(0), properties.getProperty("Text1"));
		Assert.assertEquals(texts.get(1), properties.getProperty("Text2"));
	}
	
	@Test
	public void verifyPlaceholders()
	{
		List<String> pTexts = register.getPlaceholders();
		for(int i=0; i<pTexts.size(); i++)
		{
			String expectedPlaceholder = properties.getProperty("placeholderText"+(i+1));
			Assert.assertEquals(pTexts.get(i), expectedPlaceholder, "Placeholder text does not match for text box "+(i+1));
		}
	}
	
	@Test
	public void verifyTextBoxDisable()
	{
		boolean state = register.getTextBoxState();
		Assert.assertFalse(state, "Text Box remained active after clicking");
	}
	
	@Test
	public void verifyCollegeSelect()
	{
		String actualCollege = properties.getProperty("college");
		String selectedCollege = register.selectCollege(actualCollege);
		Assert.assertEquals(selectedCollege, actualCollege, "College name displayed is incorrect");
	}
	
	@Test
	public void verifyYearSelect()
	{
		String selectedYear = register.selectYear("2029");
		Assert.assertEquals(selectedYear, "2029", "Year displayed is incorrect");
	}
	
	@Test
	public void verifyRadioSelect()
	{
		boolean state = register.getRadioState();
		Assert.assertTrue(state, "Radio button is not selected");
	}
	
	@Test
	public void verifyTermsPDF()
	{
		register.clickTerms();
		ArrayList<String> tabs = register.switchTab(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedTerms = properties.getProperty("terms");
		String actualTerms = driver.getCurrentUrl();
		Assert.assertEquals(actualTerms, expectedTerms, "The opened URL is not the expected PDF URL.");
	}
	
	@Test
	public void verifyPolicy()
	{
		register.clickPolicy();
		ArrayList<String> tabs = register.switchTab(1);
		Assert.assertEquals(tabs.size(), 2, "A new tab did not open");
		String expectedPolicy = properties.getProperty("policy");
		String actualPolicy = driver.getCurrentUrl();
		Assert.assertEquals(actualPolicy, expectedPolicy, "The opened URL is not the expected policy site");
	}
	
	@Test
	public void verifyCheckBoxState()
	{
		boolean state1 = register.getCheckBox3State();
		boolean state2 = register.getCheckBox4State();
		Assert.assertTrue(state1, "Midcareer checkbox is not selected");
		Assert.assertTrue(state2, "Newsletter checkbox is not selected");
	}
	
	@Test
	public void verifyLogInUrl()
	{
		String url = register.getLogInUrl();
		Assert.assertEquals(url, properties.getProperty("url"), "Did not redirect to login page");
	}
	
	@Test(priority=1)
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
	
	@Test(priority=2)
	public void verifyEmail() throws ApiException
	{
		email = waitForControllerApi.waitForLatestEmail(inboxId, TIMEOUT_MILLIS, true, null, null, null, null);
		String subject = email.getSubject();
		Assert.assertEquals(subject, "Please verify your email", "Subject is not correct");
	}
	
	@Test(priority=3)
	public void verifyEmailPageTitle()
	{
		String text = verifyEmail.getTitle();
		Assert.assertEquals(text, properties.getProperty("headline"));
	}
	
	@Test(priority=4)
	public void verifyEmailAddress()
	{
		String mainText = verifyEmail.getMainText();
		Assert.assertTrue(mainText.contains(emailAddress), "Email Address displayed is incorrect");
	}
	
	@Test(priority=5)
	public void verifyEmailResend() throws ApiException
	{
		verifyEmail.clickResend();
		String alert= verifyEmail.getAlert();
		Assert.assertEquals(alert, properties.getProperty("alert"));
	}
	
	@Test(priority=6)
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
	
	@Test
	public void verifyFirstNameRequired()
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
	
	@Test
	public void verifyLastNameRequired()
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
	
	@Test
	public void verifyCollegeRequired()
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
	
	@Test
	public void verifyUSNRequired()
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
	
	@Test
	public void verifyYearRequired()
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
	
	@Test
	public void verifyInterestRequired()
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
	
	@Test
	public void verifyEmailRequired()
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
	
	@Test
	public void verifyPasswordRequired()
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
	
	@Test
	public void verifyTermsAgreementRequired()
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
	
	@Test
	public void verifyCharExceedFirstName()
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
	
	@Test
	public void verifyCharExceedMiddleName()
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
	
	@Test
	public void verifyCharExceedLastName()
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
	
	@Test
	public void verifyCharExceedUsn()
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
	
	@Test
	public void verifyInvalidUsn()
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
	
	@Test
	public void verifyInvalidEmail()
	{
		register.enterName("Sherlock");
		register.enterLastName("Holmes");
		register.selectCollege(properties.getProperty("college2"));
		register.enterUsn("12345");
		register.selectYear("2030");
		register.selectInterest();
		register.enterEmail("hey");
		register.enterPassword("Password_1");
		register.clickTermsCheckbox();
		register.clickSubmit();
		String text = register.getInvalidEmailText();
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test
	public void verifyUsedEmail()
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
	
	@Test
	public void verifyIncorrectPassword1()//8文字なし
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
	
	@Test
	public void verifyIncorrectPassword2()//数字なし
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
	
	@Test
	public void verifyIncorrectPassword3()//大文字なし
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
	
	@Test
	public void verifyIncorrectPassword4()//特殊文字なし
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
