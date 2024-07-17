package com.zenken.freshers.testcomponents;


import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	
	public String username = "yaaayfreshersuser";
	public String password = "Kajibyw6.";
	public String domain = "freshers.dspf-dev.com";
	public String url;
	
	ThreadLocal<String> currentTestMethod = new ThreadLocal<>();
	boolean reuseBrowserSession = false;
	
	Properties properties;
	
	public void navigateTo(String uri)
	{
		url = "https://" + username + ":" + password + "@" + domain + uri;
		driver.get(url);
	}
	
	@BeforeMethod
	public void setup(Method method) throws IOException
	{
		currentTestMethod.set(method.getName());
		if(!reuseBrowserSession)
		{
			//セッションを再利用しない場合のみにWebDriverを初期化
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
	}
	
	@AfterMethod
	public void tearDown()
	{
		if(driver != null)
		{
			if(!shouldKeepBrowserOpen())
			{
				driver.quit();
				reuseBrowserSession = false;//ブラウザを閉じた後にフラグをリセット
			}
			else
			{
				reuseBrowserSession = true;//ブラウザを再利用するようにフラグを設定
			}
		}
	}
	
	public Properties getProperties() throws IOException
	{
		properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\zenken\\freshers\\resources\\UserTexts.properties");
		properties.load(fis);
		return properties;
	}
	
	public void setCurrentTestMethod(String methodName)
	{
		currentTestMethod.set(methodName);
	}
	
	public String getCurrentTestMethod()
	{
		return currentTestMethod.get();
	}
	
	public boolean shouldKeepBrowserOpen()
	{
		String testName = getCurrentTestMethod();
		return testName.equals("verifyUserRegistration") || testName.equals("verifyEmail") || testName.equals("verifyEmailPageTitle")
				|| testName.equals("verifyEmailAddress") || testName.equals("verifyEmailResend");
	}
	
}
