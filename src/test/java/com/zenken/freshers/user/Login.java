package com.zenken.freshers.user;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.testng.Assert;
import org.testng.ITestResult;
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
	String resetPass = "Password_2";//実行する前に現在のパスワードを確認
	
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
	
	@Test
	public void verifyTitle()
	{
		String title = login.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title2"));
	}

	@Test
	public void verifyHeadLineText()
	{
		String headline = login.getHeadLineText();
		Assert.assertEquals(headline, properties.getProperty("text3"), "Headline is not correct");
	}
	
	@Test
	public void verifyUserLogIn()
	{
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		login.enterPassword("Password_1");
		String url = login.clickLogIn();
		Assert.assertEquals(url, properties.getProperty("url2"));
	}
	
	@Test
	public void verifyForgotPasswordUrl()
	{
		String url = login.clickForgotPasswordLink();
		Assert.assertEquals(url, properties.getProperty("url3"));
	}
	
	@Test
	public void verifyCreateAccountUrl()
	{
		String url = login.clickCreateAccountLink();
		Assert.assertEquals(url, properties.getProperty("url4"));
	}
	
	@Test
	public void verifyInvalidEmail()
	{
		login.enterEmailId("hey");
		login.enterPassword("Freshers123#");
		login.clickLogIn();
		String text = login.getInvalidEmailText();
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@Test
	public void verifyEmailRequired()
	{
		login.enterPassword("Freshers123#");
		login.clickLogIn();
		String text = login.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test
	public void verifyPasswordRequired()
	{
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		login.clickLogIn();
		String text = login.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@Test
	public void verifyCredentialsMismatch()
	{
		login.enterEmailId("prasanna.inamdar@zenken.co.jp");
		login.enterPassword("fReshers123#");
		login.clickLogIn();
		String text = login.getNotMatchText();
		Assert.assertEquals(text, properties.getProperty("error11"));
	}
	
	@Test
	public void verifyForgotPasswordPageTitle()
	{
		login.clickForgotPasswordLink();
		String title = forgotPassword.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title3"));
	}
	
	@Test
	public void verifyForgotPasswordPageHeadline()
	{
		login.clickForgotPasswordLink();
		String text = login.getHeadLineText();
		Assert.assertEquals(text, properties.getProperty("text4"));
	}
	
	@Test(priority=1)
	public void verifyPasswordResetMailSend()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail(emailAddress);
		forgotPassword.clickSend();
		String alert = forgotPassword.getAlertText();
		Assert.assertEquals(alert, properties.getProperty("alert2"));
	}
	
	@Test(priority=2)
	public void verifySameMailSendWithinMinute()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail(emailAddress);
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertEquals(text, properties.getProperty("error12"));
	}
	
	@Test(priority=3)
	public void verifyPasswordResetEmail() throws ApiException
	{
		apiClient = new ApiClient();
		apiClient.setApiKey(properties.getProperty("API_KEY"));
		waitForControllerApi = new WaitForControllerApi(apiClient);
		uuid = UUID.fromString("23e51ed7-f1d2-405c-8380-67b86ca0956b");
		email = waitForControllerApi.waitForLatestEmail(uuid, 30000L, true, null, null, null, null);
		String subject = email.getSubject();
		Assert.assertEquals(subject, properties.getProperty("subject"), "Subject is not correct");
	}
	
	@Test(priority=4)
	public void verifyPasswordResetLink() throws ApiException
	{
		String emailBody = email.getBody();
		passwordResetLink = forgotPassword.extractPasswordResetLink(emailBody);
		forgotPassword.openInNewTab(passwordResetLink);
		forgotPassword.switchTabs(1);
		String headline = resetPassword.getHeadlineText();
		Assert.assertEquals(headline, properties.getProperty("text5"), "Did not open reset password page");
	}
	
	@Test(priority=5)
	public void verifyPasswordReset()
	{
		forgotPassword.openInNewTab(passwordResetLink);
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword(resetPass);
		String url = resetPassword.clickChangePassword();
		Assert.assertTrue(url.equals(properties.getProperty("url5")), "Did not open reset password complete page");
	}
	
	@Test(priority=6)
	public void verifyPasswordResetCompleteHeadline() throws InterruptedException
	{
		String headline = resetPasswordComplete.getHeadlineText();
		Assert.assertTrue(headline.equals(properties.getProperty("text6")), "Headline is not correct");
	}
	
	@Test(priority=7)
	public void verifyLogInUrl()
	{
		String url = resetPasswordComplete.clickLogIn();
		Assert.assertTrue(url.equals(properties.getProperty("url")), "Did not open login page");
	}
	
	@Test(priority=8)
	public void verifyLogInAfterPasswordChange() throws InterruptedException
	{
		login.enterEmailId(emailAddress);
		login.enterPassword(resetPass);
		String url = login.clickLogIn();
		Assert.assertTrue(url.equals(properties.getProperty("url2")), "Could not login with the new password");
	}
	
	@Test
	public void verifyEmailRequiredOnForgotPasswordPage()
	{
		login.clickForgotPasswordLink();
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error1")));
	}
	
	@Test
	public void verifyNonExistentEmailOnForgotPasswordPage()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail("sample123@orkut.com");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error14")));
	}
	
	@Test
	public void verifyInvalidEmailOnForgotPasswordPage()
	{
		login.clickForgotPasswordLink();
		forgotPassword.enterEmail("hey");
		forgotPassword.clickSend();
		String text = forgotPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error5")));
	}
	
	@Test
	public void verifyInvalidPassword1OnResetPasswordPage()//8文字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Passw_1");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error7")));
	}
	
	@Test
	public void verifyInvalidPassword2OnResetPasswordPage()//数字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password_#");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error8")));
	}
	
	@Test
	public void verifyInvalidPassword3OnResetPasswordPage()//大文字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("password_1");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error9")));
	}
	
	@Test
	public void verifyInvalidPassword4OnResetPasswordPage()//特殊文字なし
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password12");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error10")));
	}
	
	@Test
	public void verifyInvalidTokenOnResetPasswordPage()
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password_189");
		resetPassword.clickChangePassword();
		String text = resetPassword.getinvalidTokenText();
		Assert.assertTrue(text.equals(properties.getProperty("error15")));
	}
	
	@Test
	public void verifySamePasswordOnResetPasswordPage()
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
		resetPassword.enterPassword("Password_1");
		resetPassword.clickChangePassword();
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error13")));
	}
}
