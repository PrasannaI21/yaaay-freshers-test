package com.zenken.freshers.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.ApiException;
import com.mailslurp.models.Email;
import com.zenken.freshers.pages.user.ForgotPasswordPage;
import com.zenken.freshers.pages.user.LoginPage;
import com.zenken.freshers.pages.user.ResetPasswordCompletePage;
import com.zenken.freshers.pages.user.ResetPasswordPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class Login extends BaseTest{
	
	LoginPage login;
	Properties properties;
	ForgotPasswordPage forgotPassword;
	ResetPasswordPage resetPassword;
	ResetPasswordCompletePage resetPasswordComplete;
	Email email;
	ApiClient apiClient;
	WaitForControllerApi waitForControllerApi;
	UUID uuid;
	String passwordResetLink;
	String emailAddress = "23e51ed7-f1d2-405c-8380-67b86ca0956b@mailslurp.net";
	private String resetPass;
	
	@BeforeMethod
	public void setupLogin(ITestResult result) throws IOException
	{
		String testName = result.getMethod().getMethodName();
		if(!"verifyPassResetCompleteHeadline".equals(testName) && !"verifyLogInUrl".equals(testName))
		{
			navigateTo("/login/");
		}
		login = new LoginPage(driver);
		resetPassword = new ResetPasswordPage(driver);
		forgotPassword = new ForgotPasswordPage(driver);
		resetPasswordComplete = new ResetPasswordCompletePage(driver);
		properties = getProperties();
	}
	
	@BeforeClass
	public void generatePass()
	{
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		resetPass = "Password_" + timestamp;
		System.out.println("Generated password: " + resetPass);
	}
	
	@Test(description="This test verifies that Login page title is correct")
	public void verifyLoginTitle()
	{
		String title = login.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title2"));
	}

	@Test(description="This test verifies that expected headline is displayed on Login page")
	public void verifyLoginHeadLineText()
	{
		String headline = login.getHeadLineText();
		Assert.assertEquals(headline, properties.getProperty("text3"), "Headline is not correct");
	}
	
	@Test(description="This test verifies that user is able to login successfully to"
			+ " his/her account")
	public void verifyUserLogIn()
	{
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		login.enterPassword("Password_1");
		String url = login.clickLogIn();
		Assert.assertEquals(url, properties.getProperty("url2"));
	}
	
	@Test(description="This test verifies that user is redirected to Forgot Password"
			+ " page")
	public void verifyForgotPasswordUrl()
	{
		String url = login.clickForgotPasswordLink();
		Assert.assertEquals(url, properties.getProperty("url3"));
	}
	
	@Test(description="This test verifies that user is redirected to Registration page")
	public void verifyCreateAccountUrl()
	{
		String url = login.clickCreateAccountLink();
		Assert.assertEquals(url, properties.getProperty("url4"));
	}
	
	@Test(description="This test verifies that validation error occurs when invalid "
			+ "email is entered")
	public void verifyLoginInvalidEmail()
	{	
		login.enterEmailId("prasanna.inamdar@zenken.co.");
		login.enterPassword("Password_1");
		login.clickLogIn();
		String text = login.getInvalidEmailText();
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test(description="This test verifies that validation error occurs when 'Email' "
			+ "field is left blank")
	public void verifyLoginEmailReq()
	{
		login.enterPassword("Password_1");
		login.clickLogIn();
		String text = login.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs when 'Password' "
			+ "field is left blank")
	public void verifyLoginPassReq()
	{
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		login.clickLogIn();
		String text = login.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs when password "
			+ "entered is incorrect")
	public void verifyCredentialsMismatch()
	{
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		login.enterPassword("password_1#");
		login.clickLogIn();
		String text = login.getNotMatchText();
		Assert.assertEquals(text, properties.getProperty("error11"));
	}
	
	@Test(description="This test verifies that Forgot Password page title is correct")
	public void verifyForgotPasswordPageTitle()
	{
		login.clickForgotPasswordLink();
		String title = forgotPassword.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title3"));
	}
	
	@Test(description="This test verifies that expected text for headline is displayed "
			+ "on Forgot Password page")
	public void verifyForgotPasswordPageHeadline()
	{
		login.clickForgotPasswordLink();
		String text = login.getHeadLineText();
		Assert.assertEquals(text, properties.getProperty("text4"));
	}
	
	@Test(priority=1, description="This test verifies that alert is displayed upon "
			+ "clicking on 'Send' button on Forgot Password page")
	public void verifyPassResetMailSend()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail(emailAddress);
		forgotPassword.clickSend();
		String alert = forgotPassword.getAlertText();
		Assert.assertEquals(alert, properties.getProperty("alert2"));
	}
	
	@Test(priority=2, description="This test verifies that validation error occurs when "
			+ "user tries to send email again within one minute")
	public void verifyPassResetMailSend2()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail(emailAddress);
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertEquals(text, properties.getProperty("error12"));
	}
	
	@Test(priority=3, description="This test verifies that user receives the email "
			+ "for resetting the password")
	public void verifyPassResetEmail() throws ApiException
	{
		apiClient = new ApiClient();
		apiClient.setApiKey(properties.getProperty("API_KEY"));
		waitForControllerApi = new WaitForControllerApi(apiClient);
		uuid = UUID.fromString("23e51ed7-f1d2-405c-8380-67b86ca0956b");
		email = waitForControllerApi.waitForLatestEmail(uuid, 30000L, true, null, null, null, null);
		String subject = email.getSubject();
		Assert.assertEquals(subject, properties.getProperty("subject1"), "Subject is not correct");
	}
	
	@Test(priority=4, description="This test verifies that the link contained in the email"
			+ " redirects user to Reset Password page")
	public void verifyPassResetLink() throws ApiException
	{
		String emailBody = email.getBody();
		passwordResetLink = forgotPassword.extractPasswordResetLink(emailBody);
		forgotPassword.openInNewTab(passwordResetLink);
		forgotPassword.switchTabs(1);
		String headline = resetPassword.getHeadlineText();
		Assert.assertEquals(headline, properties.getProperty("text5"), "Did not open reset password page");
	}
	
	@Test(priority=5, description="This test verifies that the password can be reset "
			+ "successfully")
	public void verifyPassReset()
	{
		forgotPassword.openInNewTab(passwordResetLink);
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword(resetPass);
		String url = resetPassword.clickChangePassword();
		Assert.assertEquals(url, properties.getProperty("url5"), "Did not open reset password complete page");
	}
	
	@Test(priority=6, description="This test verifies that expected headline text is "
			+ "displayed on Reset Password Complete page")
	public void verifyPassResetCompleteHeadline() throws InterruptedException
	{
		String headline = resetPasswordComplete.getHeadlineText();
		Assert.assertEquals(headline, properties.getProperty("text6"), "Headline is not correct");
	}
	
	@Test(priority=7, description="This test verifies that user is redirected to Login "
			+ "page upon clicking on 'Log In' button on Reset Password Complete page")
	public void verifyLogInUrl()
	{
		String url = resetPasswordComplete.clickLogIn();
		Assert.assertTrue(url.equals(properties.getProperty("url")), "Did not open login page");
	}
	
	@Test(priority=8, description="This test verifies that user is able to login into "
			+ "his/her account with the password that has been reset")
	public void verifyLogInAfterPassChange() throws InterruptedException
	{
		login.enterEmailId(emailAddress);
		login.enterPassword(resetPass);
		String url = login.clickLogIn();
		Assert.assertEquals(url, properties.getProperty("url2"), "Could not login with the new password");
	}
	
	@Test(description="This test verifies that validation error occurs when 'Email' field"
			+ " is left blank on Forgot Password page")
	public void verifyEmailReqForgotPass()
	{
		login.clickForgotPasswordLink();
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error1")));
	}
	
	@Test(description="This test verifies that validation error occurs when email entered "
			+ "in 'Email' field does not exist on Forgot Password page")
	public void verifyNonExistentEmail()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail("sample123@orkut.com");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertEquals(text, properties.getProperty("error14"));
	}
	
	@Test(description="This test verifies that validation error occurs when an invalid "
			+ "email is entered in 'Email' field on Forgot Password page")
	public void verifyInvalidEmailForgotPassPage()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail("prasanna.inamdar@zenken.co.");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when fewer than 8 characters are entered on Reset Password page")
	public void verifyInvalidPassword1()//8文字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Passw_1");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error7")));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field  on Reset Password page when password entered does "
			+ "not contain a number")
	public void verifyInvalidPassword2()//数字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password_#");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error8")));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field on Reset Password page when password entered does not "
			+ "contain any uppercase character")
	public void verifyInvalidPassword3()//大文字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("password_1");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error9")));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field on Reset Password page when password entered does not "
			+ "any special characters")
	public void verifyInvalidPassword4()//特殊文字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password12");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error10")));
	}
	
	@Test(description="This test verifies that a validation error occurs on Reset "
			+ "Password page when the token in the URL has expired")
	public void verifyLoginInvalidToken()
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password_189");
		resetPassword.clickChangePassword();
		String text = resetPassword.getinvalidTokenText();
		Assert.assertTrue(text.equals(properties.getProperty("error15")));
	}
	
	@Test(description="This test verifies that a validation error occurs on Reset "
			+ "Password page when the same password as previous is entered")
	public void verifyLoginSamePass()
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password_1");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error13")));
	}
}
