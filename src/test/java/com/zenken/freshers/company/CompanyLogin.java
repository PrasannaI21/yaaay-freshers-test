package com.zenken.freshers.company;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zenken.freshers.pages.admin.AdminLoginPage;
import com.zenken.freshers.pages.admin.CompanyDetailsPage;
import com.zenken.freshers.pages.admin.CompanyUserCreatePage;
import com.zenken.freshers.pages.company.CForgotPasswordPage;
import com.zenken.freshers.pages.company.CLoginPage;
import com.zenken.freshers.pages.company.EventsPage;
import com.zenken.freshers.pages.company.InitialPasswordPage;
import com.zenken.freshers.testcomponents.BaseTest;

public class CompanyLogin extends BaseTest{
	
	CLoginPage cLogin;
	InitialPasswordPage initialPass;
	EventsPage events;
	CForgotPasswordPage forgotPassword;
	AdminLoginPage adminLogin;
	CompanyDetailsPage companyDetails;
	Properties properties;
	String companyUserEmail;
	String password;
	private static boolean isCompanyCreated = false;
	
	@BeforeMethod
	public void setupCLogin(ITestResult result) throws IOException
	{
		CompanyUserCreatePage companyUserCreate = new CompanyUserCreatePage(driver);
		adminLogin = new AdminLoginPage(driver);
		companyDetails = new CompanyDetailsPage(driver);
		forgotPassword = new CForgotPasswordPage(driver);
		events = new EventsPage(driver);
		cLogin = new CLoginPage(driver);
		initialPass = new InitialPasswordPage(driver);
		properties = getProperties();	
		if(!isCompanyCreated) {
			navigateTo("/admin/");
			adminLogin.loginAdmin();
			navigateTo("/admin/companies/98/company-users/new/");
			companyUserCreate.enterComUserName("Prasanna");
			companyUserCreate.enterComUserEmail(companyUserEmail);
			companyUserCreate.clickSave();
			password = companyDetails.getComUserPassword();
			isCompanyCreated = true;
		}
		String testName = result.getMethod().getMethodName();
		if(!"verifyComBlankPassword".equals(testName) || !"verifyComInvalidPasswords".equals(testName)
				|| !"verifyComPasswordChange".equals(testName) || !"verifyComLogout".equals(testName)) {
			navigateTo("/company/");
		}
	}
	
	@BeforeClass
	public void generateComUser()
	{
		String timestamp = String.valueOf(System.currentTimeMillis());
		companyUserEmail = "prasanna.inamdar+" + timestamp + "@zenken.co.jp";
		System.out.println("Generated Company User: "+companyUserEmail);
	}

	@Test(description="This test verifies that company's Login page title is correct")
	public void verifyComTitle()
	{
		Assert.assertEquals(driver.getTitle(), properties.get("title6"));
		Assert.assertEquals(cLogin.getComHeadline(), "企業ログイン");
	}
	
	@Test(priority=1, description="This test verifies that Company user is redirected "
			+ "to Initial Password Set page")
	public void verifyComPasswordSet()
	{
		cLogin.enterComEmail(companyUserEmail);
		cLogin.enterComPassword(password);
		cLogin.clickSave();
		Assert.assertEquals(driver.getCurrentUrl(), properties.get("url22"));
		Assert.assertEquals(initialPass.getComHeadline(), "新しいパスワードを設定");
	}
	
	@Test(priority=2, description="This test verifies that validation error occurs when"
			+ " password field is left blank on Initial Password page")
	public void verifyComBlankPassword()
	{
		initialPass.enterComPassword("");
		initialPass.clickSave();
		Assert.assertTrue(initialPass.getPasswordBoxText().contains(properties.getProperty("error35"))
				, "Validation text is not correct");
	}
	
	@Test(priority=3, description="This test verifies that validation errors occur when invalid "
			+ "passwords are entered on Initial Password page")
	public void verifyComInvalidPasswords()
	{
		initialPass.enterComPassword("Passw_1");
		initialPass.clickSave();
		Assert.assertTrue(initialPass.getPasswordBoxText().contains(properties.getProperty("error31"))
				, "Validation text is not correct");
		initialPass.enterComPassword("Password_#");
		initialPass.clickSave();
		Assert.assertTrue(initialPass.getPasswordBoxText().contains(properties.getProperty("error32"))
				, "Validation text is not correct");
		initialPass.enterComPassword("password_1");
		initialPass.clickSave();
		Assert.assertTrue(initialPass.getPasswordBoxText().contains(properties.getProperty("error33"))
				, "Validation text is not correct");
		initialPass.enterComPassword("Password12");
		initialPass.clickSave();
		Assert.assertTrue(initialPass.getPasswordBoxText().contains(properties.getProperty("error34"))
				, "Validation text is not correct");
	}
	
	@Test(priority=4, description="This test verifies that company user is successfully able to "
			+ "change the initial password")
	public void verifyComPasswordChange()
	{
		initialPass.enterComPassword("Password_1");
		cLogin.clickSave();
		Assert.assertEquals(initialPass.getComHeadline2(), "パスワードの変更が完了しました。");
		initialPass.clickLogin();
		Assert.assertEquals(driver.getCurrentUrl(), properties.get("url23"));
	}
	
	@Test(priority=5, description="This test verifies that 初回パスワード text changes to "
			+ "\"初回パスワード変更済\" on 企業の詳細 page in CS after company user sets the password")
	public void verifyAdminPassTextChange()
	{
		navigateTo("/admin/");
		adminLogin.loginAdmin();
		navigateTo("/admin/companies/98/");
		Assert.assertEquals(companyDetails.getComUserPassword(), "初回パスワード変更済");
	}
	
	@Test(priority=6, description="This test verifies that company user is able to login"
			+ " with the updated password")
	public void verifyComLogin()
	{
		cLogin.enterComEmail(companyUserEmail);
		cLogin.enterComPassword("Password_1");
		cLogin.clickSave();
		Assert.assertEquals(driver.getCurrentUrl(), properties.get("url24"));
		Assert.assertEquals(events.getComEventsHeadline(), "イベント一覧");	
	}
	
	@Test(priority=7, description="This test verifies that company user is able to logout")
	public void verifyComLogout()
	{
		events.clickLogout();
		Assert.assertEquals(driver.getCurrentUrl(), properties.get("url23"));
	}
	
	@Test(priority=8, description="This test verifies that company user is redirected "
			+ "to Forgot Password page")
	public void verifyComForgotPasswordLink()
	{
		cLogin.clickResetLink();
		Assert.assertEquals(driver.getCurrentUrl(), properties.get("url25"));
		Assert.assertEquals(forgotPassword.getComHeadline(), "パスワードをお忘れの方");
	}
	
	@Test(description="This test verifies that validation error occurs "
			+ "when an invalid email is entered on Forgot Password page")
	public void verifyComInvalidEmail2()
	{
		cLogin.clickResetLink();
		forgotPassword.enterEmail("prasanna.inamdar+zenken.co.jp");
		cLogin.clickSave();
		Assert.assertTrue(forgotPassword.getComEmailBoxText().contains(properties.getProperty("error36"))
				, "Validation text is not correct");
	}
	
	@Test(description="This test verifies that validation error occurs "
			+ "when email field is left blank on Forgot Password page")
	public void verifyComBlankEmail()
	{
		cLogin.clickResetLink();
		forgotPassword.enterEmail("");
		cLogin.clickSave();
		Assert.assertTrue(forgotPassword.getComEmailBoxText().contains(properties.getProperty("error35"))
				, "Validation text is not correct");
	}
	
	@Test(description="This test verifies that validation error occurs "
			+ "when the email entered does not exist on Forgot Password page")
	public void verifyComInvalidEmail3()
	{
		cLogin.clickResetLink();
		forgotPassword.enterEmail("sample123@orkut.com");
		cLogin.clickSave();
		Assert.assertEquals(forgotPassword.getComAlertText(), properties.get("alert9"));
	}
	
	@Test(priority=9, description="This test verifies that company user is able to send the mail "
			+ "for resetting the password")
	public void verifyComMailSend()
	{
		cLogin.clickResetLink();
		forgotPassword.enterEmail(companyUserEmail);
		cLogin.clickSave();	
		Assert.assertEquals(forgotPassword.getComAlertText(), properties.get("alert10"));
	}
	
	@Test(priority=10, description="This test verifies that validation error occurs when"
			+ " company user tries to send email again within one minute")
	public void verifyComMailSendAgain()
	{
		cLogin.clickResetLink();
		forgotPassword.enterEmail(companyUserEmail);
		cLogin.clickSave();	
		Assert.assertEquals(forgotPassword.getComAlertText(), properties.get("alert11"));
	}
	
	@Test(description="This test verifies that validation error occurs when invalid "
			+ "email is entered on Company's Login page")
	public void verifyComInvalidEmail()
	{
		cLogin.enterComEmail("prasanna.inamdar@zenken.co.");
		cLogin.enterComPassword("Password_1");
		cLogin.clickSave();
		Assert.assertTrue(cLogin.getMailBoxText().contains(properties.getProperty("error36"))
				, "Validation error text is not correct");
	}
	
	@Test(description="This test verifies that validation errors occur when email and "
			+ "password fields are left blank on Company's Login page")
	public void verifyComBlankEmailAndPass()
	{
		cLogin.enterComEmail("");
		cLogin.enterComPassword("Password_1");
		cLogin.clickSave();
		Assert.assertTrue(cLogin.getMailBoxText().contains(properties.getProperty("error35"))
				, "Validation error text is not correct");
		cLogin.enterComEmail(companyUserEmail);
		cLogin.enterComPassword("");
		cLogin.clickSave();
		Assert.assertTrue(cLogin.getPasswordBoxText().contains(properties.getProperty("error35"))
				, "Validation error text is not correct");
	}
	
	@Test(description="This test verifies that validation error occurs when password "
			+ "entered is incorrect on Company's Login page")
	public void verifyComIncorrectPass()
	{
		cLogin.enterComEmail(companyUserEmail);
		cLogin.enterComPassword("password_1#");
		cLogin.clickSave();
		Assert.assertEquals(forgotPassword.getComAlertText(), properties.get("alert12"));
	}
	
}
