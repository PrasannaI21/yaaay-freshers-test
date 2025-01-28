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
	public void setUpTest(ITestResult result) throws IOException
	{
		String testName = result.getMethod().getMethodName();
		if(!"verifyPasswordResetCompleteHeadline".equals(testName) && !"verifyLogInUrl".equals(testName))
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
	public void verifyTitle()
	{
		log("Step 1: Verify that title name is correct");
		String title = login.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title2"));
	}

	@Test(description="This test verifies that expected headline is displayed on Login page")
	public void verifyHeadLineText()
	{
		log("Step 1: Verify that \"Welcome Back\" text is displayed");
		String headline = login.getHeadLineText();
		Assert.assertEquals(headline, properties.getProperty("text3"), "Headline is not correct");
	}
	
	@Test(description="This test verifies that user is able to login successfully to"
			+ " his/her account")
	public void verifyUserLogIn()
	{
		log("Step 1: Enter email \"prasanna.inamdar@zenken.co.jp\"");
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		log("Step 2: Enter password \"Password_1\"");
		login.enterPassword("Password_1");
		log("Step 3: Click on 'Log In' button");
		String url = login.clickLogIn();
		log("Step 4: Verify that user is redirected to profile preview page");
		Assert.assertEquals(url, properties.getProperty("url2"));
	}
	
	@Test(description="This test verifies that user is redirected to Forgot Password"
			+ " page")
	public void verifyForgotPasswordUrl()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		String url = login.clickForgotPasswordLink();
		log("Step 2: Verify that the loaded URL corresponds to the Forgot Password page");
		Assert.assertEquals(url, properties.getProperty("url3"));
	}
	
	@Test(description="This test verifies that user is redirected to Registration page")
	public void verifyCreateAccountUrl()
	{
		log("Step 1: Click on 'Create an Account' link");
		String url = login.clickCreateAccountLink();
		log("Step 2: Verify that the loaded URL corresponds to the Registration page");
		Assert.assertEquals(url, properties.getProperty("url4"));
	}
	
	@Test(description="This test verifies that validation error occurs when invalid "
			+ "email is entered")
	public void verifyInvalidEmail()
	{
		log("Step 1: Enter email \"prasanna.inamdar@zenken.co.\"");
		login.enterEmailId("prasanna.inamdar@zenken.co.");
		log("Step 2: Enter password \"Password_1\"");
		login.enterPassword("Password_1");
		log("Step 3: Click on 'Log In' button");
//		login.clickLogIn();
		String text = login.getInvalidEmailText();
		log("Step 4: Verify that validation error message for an invalid email is displayed");
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test(description="This test verifies that validation error occurs when 'Email' "
			+ "field is left blank")
	public void verifyEmailRequired()
	{
		log("Step 1: Enter password \"Password_1\"");
		login.enterPassword("Password_1");
		log("Step 2: Click on 'Log In' button");
		login.clickLogIn();
		String text = login.getRequiredText();
		log("Step 3: Verify that a validation error message stating 'Email is required' is displayed");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs when 'Password' "
			+ "field is left blank")
	public void verifyPasswordRequired()
	{
		log("Step 1: Enter email \"prasanna.inamdar@zenken.co.jp\"");
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		log("Step 2: Click on 'Log In' button");
		login.clickLogIn();
		String text = login.getRequiredText();
		log("Step 3: Verify that a validation error message stating 'Password is required' is displayed");
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test(description="This test verifies that validation error occurs when password "
			+ "entered is incorrect")
	public void verifyCredentialsMismatch()
	{
		log("Step 1: Enter email \"prasanna.inamdar@zenken.co.jp\"");
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		log("Step 2: Enter password \"password_1\"");
		login.enterPassword("password_1#");
		log("Step 3: Click on 'Log In' button");
		login.clickLogIn();
		String text = login.getNotMatchText();
		log("Step 4: Verify that expected validation error message is displayed");
		Assert.assertEquals(text, properties.getProperty("error11"));
	}
	
	@Test(description="This test verifies that Forgot Password page title is correct")
	public void verifyForgotPasswordPageTitle()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		String title = forgotPassword.getTitleName();
		log("Step 2: Verify that expected title name is displayed");
		Assert.assertEquals(title, properties.getProperty("title3"));
	}
	
	@Test(description="This test verifies that expected text for headline is displayed "
			+ "on Forgot Password page")
	public void verifyForgotPasswordPageHeadline()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		String text = login.getHeadLineText();
		log("Step 2: Verify that headline text is \"Reset Password\"");
		Assert.assertEquals(text, properties.getProperty("text4"));
	}
	
	@Test(priority=1, description="This test verifies that alert is displayed upon "
			+ "clicking on 'Send' button on Forgot Password page")
	public void verifyPasswordResetMailSend()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		log("Step 2: Enter email "+"\""+emailAddress+"\"");
		forgotPassword.enterEmail(emailAddress);
		log("Step 3: Click on 'Send' button");
		forgotPassword.clickSend();
		String alert = forgotPassword.getAlertText();
		log("Step 4: Verify that expected alert is displayed");
		Assert.assertEquals(alert, properties.getProperty("alert2"));
	}
	
	@Test(priority=2, description="This test verifies that validation error occurs when "
			+ "user tries to send email again within one minute")
	public void verifySameMailSendWithinMinute()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		log("Step 2: Enter email "+"\""+emailAddress+"\"");
		forgotPassword.enterEmail(emailAddress);
		log("Step 3: Click on 'Send' button");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed");
		Assert.assertEquals(text, properties.getProperty("error12"));
	}
	
	@Test(priority=3, description="This test verifies that user receives the email "
			+ "for resetting the password")
	public void verifyPasswordResetEmail() throws ApiException
	{
		log("Step 1: Wait for the email to be received");
		apiClient = new ApiClient();
		apiClient.setApiKey(properties.getProperty("API_KEY"));
		waitForControllerApi = new WaitForControllerApi(apiClient);
		uuid = UUID.fromString("23e51ed7-f1d2-405c-8380-67b86ca0956b");
		email = waitForControllerApi.waitForLatestEmail(uuid, 30000L, true, null, null, null, null);
		String subject = email.getSubject();
		log("Step 2: Verify that subject of the email received is \"Reset Password\"");
		Assert.assertEquals(subject, properties.getProperty("subject1"), "Subject is not correct");
	}
	
	@Test(priority=4, description="This test verifies that the link contained in the email"
			+ " redirects user to Reset Password page")
	public void verifyPasswordResetLink() throws ApiException
	{
		String emailBody = email.getBody();
		passwordResetLink = forgotPassword.extractPasswordResetLink(emailBody);
		log("Step 1: Click on the link for resetting the password in the mail");
		forgotPassword.openInNewTab(passwordResetLink);
		forgotPassword.switchTabs(1);
		String headline = resetPassword.getHeadlineText();
		log("Step 2: Verify that headline of the page is \"Create a New Password\"");
		Assert.assertEquals(headline, properties.getProperty("text5"), "Did not open reset password page");
	}
	
	@Test(priority=5, description="This test verifies that the password can be reset "
			+ "successfully")
	public void verifyPasswordReset()
	{
		forgotPassword.openInNewTab(passwordResetLink);
		log("Step 1: Click on the link for resetting the password in the mail");
		forgotPassword.switchTabs(1);
		log("Step 2: Enter password "+"\""+resetPass+"\"");
		resetPassword.enterPassword(resetPass);
		log("Step 3: Click on 'Change Password' button");
		String url = resetPassword.clickChangePassword();
		log("Step 4: Verify that user is redirected to Reset Password Complete page");
		Assert.assertEquals(url, properties.getProperty("url5"), "Did not open reset password complete page");
	}
	
	@Test(priority=6, description="This test verifies that expected headline text is "
			+ "displayed on Reset Password Complete page")
	public void verifyPasswordResetCompleteHeadline() throws InterruptedException
	{
		String headline = resetPasswordComplete.getHeadlineText();
		log("Step 1: Verify that headline text is \"Your password has been reset\"");
		Assert.assertEquals(headline, properties.getProperty("text6"), "Headline is not correct");
	}
	
	@Test(priority=7, description="This test verifies that user is redirected to Login "
			+ "page upon clicking on 'Log In' button on Reset Password Complete page")
	public void verifyLogInUrl()
	{
		log("Step 1: Click on 'Log In' button");
		String url = resetPasswordComplete.clickLogIn();
		log("Step 2: Verify that the loaded URL corresponds to the Login page");
		Assert.assertTrue(url.equals(properties.getProperty("url")), "Did not open login page");
	}
	
	@Test(priority=8, description="This test verifies that user is able to login into "
			+ "his/her account with the password that has been reset")
	public void verifyLogInAfterPasswordChange() throws InterruptedException
	{
		log("Step 1: Enter email "+"\""+emailAddress+"\"");
		login.enterEmailId(emailAddress);
		log("Step 2: Enter password "+"\""+resetPass+"\"");
		login.enterPassword(resetPass);
		log("Step 3: Click on 'Log In' button");
		String url = login.clickLogIn();
		log("Step 4: Verify that user is redirected to profile preview page");
		Assert.assertEquals(url, properties.getProperty("url2"), "Could not login with the new password");
	}
	
	@Test(description="This test verifies that validation error occurs when 'Email' field"
			+ " is left blank on Forgot Password page")
	public void verifyEmailRequiredOnForgotPasswordPage()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		log("Step 2: Click on 'Send' button");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		log("Step 3: Verify that expected validation error message is displayed for 'Email' field");
		Assert.assertTrue(text.equals(properties.getProperty("error1")));
	}
	
	@Test(description="This test verifies that validation error occurs when email entered "
			+ "in 'Email' field does not exist on Forgot Password page")
	public void verifyNonExistentEmail()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		log("Step 2: Enter email \"sample123@orkut.com\"");
		forgotPassword.enterEmail("sample123@orkut.com");
		log("Step 3: Click on 'Send' button");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Email' field");
		Assert.assertEquals(text, properties.getProperty("error14"));
	}
	
	@Test(description="This test verifies that validation error occurs when an invalid "
			+ "email is entered in 'Email' field on Forgot Password page")
	public void verifyInvalidEmailOnForgotPasswordPage()
	{
		log("Step 1: Click on 'Forgot your password?' link");
		login.clickForgotPasswordLink();
		log("Step 2: Enter email \"prasanna.inamdar@zenken.co.\"");
		forgotPassword.enterEmail("prasanna.inamdar@zenken.co.");
		log("Step 3: Click on 'Send' button");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Email' field");
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field when fewer than 8 characters are entered on Reset Password page")
	public void verifyInvalidPassword1()//8文字なし
	{
		log("Step 1: Open Reset Password page");
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		log("Step 2: Enter \"Passw_1\" in 'Password' field's text box");
		resetPassword.enterPassword("Passw_1");
		log("Step 3: Click on 'Change Password' button");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertTrue(text.equals(properties.getProperty("error7")));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field  on Reset Password page when password entered does "
			+ "not contain a number")
	public void verifyInvalidPassword2()//数字なし
	{
		log("Step 1: Open Reset Password page");
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		log("Step 2: Enter \"Password_#\" in 'Password' field's text box");
		resetPassword.enterPassword("Password_#");
		log("Step 3: Click on 'Change Password' button");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertTrue(text.equals(properties.getProperty("error8")));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field on Reset Password page when password entered does not "
			+ "contain any uppercase character")
	public void verifyInvalidPassword3()//大文字なし
	{
		log("Step 1: Open Reset Password page");
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		log("Step 2: Enter \"password_1\" in 'Password' field's text box");
		resetPassword.enterPassword("password_1");
		log("Step 3: Click on 'Change Password' button");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertTrue(text.equals(properties.getProperty("error9")));
	}
	
	@Test(description="This test verifies that a validation error occurs for the "
			+ "'Password' field on Reset Password page when password entered does not "
			+ "any special characters")
	public void verifyInvalidPassword4()//特殊文字なし
	{
		log("Step 1: Open Reset Password page");
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		log("Step 2: Enter \"Password12\" in 'Password' field's text box");
		resetPassword.enterPassword("Password12");
		log("Step 3: Click on 'Change Password' button");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertTrue(text.equals(properties.getProperty("error10")));
	}
	
	@Test(description="This test verifies that a validation error occurs on Reset "
			+ "Password page when the token in the URL has expired")
	public void verifyInvalidToken()
	{
		log("Step 1: Open Reset Password page");
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		log("Step 2: Enter \"Password_189\" in 'Password' field's text box");
		resetPassword.enterPassword("Password_189");
		log("Step 3: Click on 'Change Password' button");
		resetPassword.clickChangePassword();
		String text = resetPassword.getinvalidTokenText();
		log("Step 4: Verify that expected validation error message is displayed for invalid token");
		Assert.assertTrue(text.equals(properties.getProperty("error15")));
	}
	
	@Test(description="This test verifies that a validation error occurs on Reset "
			+ "Password page when the same password as previous is entered")
	public void verifySamePassword()
	{
		log("Step 1: Open Reset Password page");
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		log("Step 2: Enter \"Password_1\" in 'Password' field's text box");
		resetPassword.enterPassword("Password_1");
		log("Step 3: Click on 'Change Password' button");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		log("Step 4: Verify that expected validation error message is displayed for 'Password' field");
		Assert.assertTrue(text.equals(properties.getProperty("error13")));
	}
}
