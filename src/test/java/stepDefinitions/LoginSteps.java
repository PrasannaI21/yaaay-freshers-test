package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.ApiException;
import com.mailslurp.models.Email;
import com.zenken.freshers.pages.user.ForgotPasswordPage;
import com.zenken.freshers.pages.user.LoginPage;
import com.zenken.freshers.pages.user.ResetPasswordCompletePage;
import com.zenken.freshers.pages.user.ResetPasswordPage;
import com.zenken.freshers.testcomponents.BaseTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends BaseTest{
	
	static WebDriver driver;
	LoginPage loginPage;
	ForgotPasswordPage forgotPassword;
	ResetPasswordPage resetPassword;
	ResetPasswordCompletePage resetPasswordComplete;
	Properties properties;
	String url;
	private static Email email;
	String subject;
	String url2;
	String url3;
	String url4;
	private boolean skipNavigation;
	private static ThreadLocal<String> string = new ThreadLocal<>();
	
	@Before
	public void before(Scenario scenario) throws IOException
	{
		if(!scenario.getSourceTagNames().contains("@SkipBeforeHook"))
		{
			driver = setup();
		}
		loginPage = new LoginPage(driver);
		properties = getProperties();
		skipNavigation = scenario.getSourceTagNames().contains("@SkipBeforeHook");
		forgotPassword = new ForgotPasswordPage(driver);
		resetPassword = new ResetPasswordPage(driver);
		resetPasswordComplete = new ResetPasswordCompletePage(driver);
	}

	@Given("I am on the login page")
	public void i_am_on_the_login_page() throws IOException
	{
		if(!skipNavigation)
		{
			navigateTo("/login/");
		}
	}
	
	@When("I enter email {string} and password {string}")
	public void i_enter_credentials(String email, String password)
	{
		loginPage.enterEmailId(email);
		loginPage.enterPassword(password);
		url = loginPage.clickLogIn();
	}
	
	@Then("I redirect to profile page")
	public void i_redirect_to_profile_page()
	{
		Assert.assertEquals(url, properties.getProperty("url2"), "Could not login");
	}
	
	@Then("The tab title should be correct")
	public void tab_title_isCorrect()
	{
		String title = loginPage.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title2"));
	}
	
	@Then("I find expected text for 見出し")
	public void i_find_expected_text()
	{
		String headline = loginPage.getHeadLineText();
		Assert.assertEquals(headline, properties.getProperty("text3"), "Headline is not correct");
	}
	
	@Then("I get redirected to forgot password page")
	public void i_redirect_to_forgot_password_page()
	{
		String url = string.get();
		Assert.assertEquals(url, properties.getProperty("url3"));
	}
	
	@When("I click on Create an Account link")
	public void i_click_on_create_account_link()
	{
		String url = loginPage.clickCreateAccountLink();
		string.set(url);
	}
	
	@Then("I get redirected to register page")
	public void i_redirect_to_register_page()
	{
		String url = string.get();
		Assert.assertEquals(url, properties.getProperty("url4"));
	}
	
	@When("I enter invalid email {string}")
	public void i_enter_invalid_email(String email)
	{
		loginPage.enterEmailId(email);
	}
	
	@But("I get error for invalid email")
	public void I_get_invalid_email_error()
	{
		String text = loginPage.getInvalidEmailText();
		Assert.assertEquals(text, properties.getProperty("error5"));
	}
	
	@But("I get error for blank email")
	public void I_get_blank_email_error()
	{
		String text = loginPage.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@But("I get error for blank password")
	public void I_get_blank_password_error()
	{
		String text = loginPage.getRequiredText();
		Assert.assertEquals(text, properties.getProperty("error1"));
	}
	
	@But("I get error for credentials mismatch")
	public void I_get_credentials_mismatch_error()
	{
		String text = loginPage.getNotMatchText();
		Assert.assertEquals(text, properties.getProperty("error11"));
	}
	
	@Then("I find expected title name")
	public void i_find_expected_title()
	{
		String title = forgotPassword.getTitleName();
		Assert.assertEquals(title, properties.getProperty("title3"));
	}
	
	@Then("I find expected headline text")
	public void i_find_expected_headline()
	{
		String title = forgotPassword.getHeadLineText();
		Assert.assertEquals(title, properties.getProperty("text4"));
	}
	
	@When("I click on Forgot your password? link")
	public void i_click_on_forgot_your_password_link()
	{
		String url = loginPage.clickForgotPasswordLink();
		string.set(url);
	}
	
	@And("I enter email {string}")
	public void i_enter_email(String email)
	{
		forgotPassword.enterEmail(email);
	}
	
	@And("I click on Send button")
	public void i_click_send()
	{
		forgotPassword.clickSend();
	}
	
	@Then("I see the success alert")
	public void i_see_success_alert()
	{
		String alert = forgotPassword.getAlertText();
		Assert.assertEquals(alert, properties.getProperty("alert2"), "Success alert is not displayed");
	}
	
	@But("I see the error alert")
	public void i_see_error_alert()
	{
		String text = forgotPassword.getErrorText();
		Assert.assertEquals(text, properties.getProperty("error12"), "Error alert is not displayed");
	}
	
	@When("I check inbox")
	public void i_check_inbox() throws ApiException
	{
		ApiClient apiClient = new ApiClient();
		apiClient.setApiKey(properties.getProperty("API_KEY"));
		WaitForControllerApi waitForControllerApi = new WaitForControllerApi(apiClient);
		UUID uuid = UUID.fromString("23e51ed7-f1d2-405c-8380-67b86ca0956b");
		email = waitForControllerApi.waitForLatestEmail(uuid, 30000L, true, null, null, null, null);
		subject = email.getSubject();
	}
	
	@Then("I find email with correct subject")
	public void i_find_email()
	{
		Assert.assertEquals(subject, properties.getProperty("subject"), "Subject is not correct");
	}
	
	@When("I open link from the mail")
	public void i_open_link()
	{
		String emailBody = email.getBody();
		String passwordResetLink = forgotPassword.extractPasswordResetLink(emailBody);
		forgotPassword.openInNewTab(passwordResetLink);
		forgotPassword.switchTabs(1);
	}
	
	@Then("I check 見出し of the page")
	public void i_check_title_of_the_page()
	{
		String headline = resetPassword.getHeadlineText();
		Assert.assertEquals(headline, properties.getProperty("text5"), "Did not open reset password page");
	}
	
	@And("I enter new password {string}")
	public void i_enter_new_password(String password)
	{
		resetPassword.enterPassword(password);
	}
	
	@And("I click on Change Password button")
	public void i_click_change_password()
	{
		url2 = resetPassword.clickChangePassword();
	}
	
	@Then("I redirect to reset password complete page")
	public void i_redirect_reset_password_complete()
	{
		Assert.assertTrue(url2.equals(properties.getProperty("url5")), "Did not open reset password complete page");
	}
	
	@Then("I check 見出し of password reset complete page")
	public void i_check_title_of_the_page2()
	{
		String headline = resetPasswordComplete.getHeadlineText();
		Assert.assertTrue(headline.equals(properties.getProperty("text6")), "Headline is not correct");
	}
	
	@When("I click on LogIn button")
	public void i_click_logIn()
	{
		url3 = resetPasswordComplete.clickLogIn();
	}
	
	@Then("I redirect to login page")
	public void i_redirect_to_login_page()
	{
		Assert.assertTrue(url3.equals(properties.getProperty("url")), "Did not open login page");
	}
	
	@When("I enter email {string} on login page")
	public void i_enter_Email(String email)
	{
		loginPage.enterEmailId(email);
	}
	
	@And("I enter password {string}")
	public void i_enter_password(String password)
	{
		loginPage.enterPassword(password);
	}
	
	@And("I click on LogIn button on login page")
	public void i_click_logIn_button()
	{
		url4 = loginPage.clickLogIn();
	}
	
	@Then("I redirect to profile page with new password")
	public void i_redirect_to_profile_page_with_new_password()
	{
		Assert.assertTrue(url4.equals(properties.getProperty("url2")), "Could not login with the new password");
	}
	
	@But("I get error for non-existent email")
	public void i_get_nonExistent_email_error()
	{
		String text = forgotPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error14")));
	}
	
	@When("I open reset password page")
	public void i_open_reset_password_page()
	{
		forgotPassword.openInNewTab(properties.getProperty("link"));
		forgotPassword.switchTabs(1);
	}
	
	@But("I get error for password: less than 8 characters")
	public void i_get_invalid_password1_error()
	{
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error7")));
	}
	
	@But("I get error for password: No numbers")
	public void i_get_invalid_password2_error()
	{
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error8")));
	}
	
	@But("I get error for password: No upper case characters")
	public void i_get_invalid_password3_error()
	{
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error9")));
	}
	
	@But("I get error for password: No special characters")
	public void i_get_invalid_password4_error()
	{
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error10")));
	}
	
	@But("I get error for invalid token")
	public void i_get_invalid_token()
	{
		String text = resetPassword.getinvalidTokenText();
		Assert.assertTrue(text.equals(properties.getProperty("error15")));
	}
	
	@But("I get error for same password as previous")
	public void i_get_same_password_error()
	{
		String text = resetPassword.getErrorText();
		Assert.assertTrue(text.equals(properties.getProperty("error13")));
	}
	
	@After
	public void tearDown(Scenario scenario) throws IOException
	{
		if(scenario.isFailed())
		{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = System.getProperty("user.dir")+"\\target\\screenshots\\"+scenario.getName()+".png";
			File destFile = new File(screenshotPath);
			FileUtils.copyFile(screenshot, destFile);
			scenario.attach(FileUtils.readFileToByteArray(destFile), "image/png", "Failed scenario screenshot");
			ExtentTest test = ExtentCucumberAdapter.getCurrentScenario();
			test.fail("シナリオ失敗").addScreenCaptureFromPath(screenshotPath);
		}
		if(!scenario.getSourceTagNames().contains("@SkipAfterHook"))
		{
			if(driver != null)
			{
				driver.quit();
			}
		}
	}
}
