package com.zenken.freshers.testcomponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;

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
		String downloadFilePath = System.getProperty("user.dir") + File.separator + "downloads";
		File file = new File(downloadFilePath);
		if(!file.exists())
		{
			file.mkdir();
		}
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", downloadFilePath);
		prefs.put("download.prompt_for_download", false);
		prefs.put("plugins.always_open_pdf_externally", true);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		currentTestMethod.set(method.getName());
		initializeDriver(options);
	}
	
	public WebDriver setup()
	{
		ChromeOptions options = new ChromeOptions();
		initializeDriver(options);
		return driver;
	}
	
	public void initializeDriver(ChromeOptions options)
	{
		if(!reuseBrowserSession)
		{
			//セッションを再利用しない場合のみにWebDriverを初期化
//			WebDriverManager.edgedriver().setup();
			WebDriverManager.chromedriver().setup();
//			driver = new EdgeDriver();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		}
	}
	
	public String waitForScrollToComplete(WebDriver driver, String fileName) throws InterruptedException, IOException
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Number lastHeight = (Number)js.executeScript("return window.pageYOffset;");
		while(true)
		{
			Thread.sleep(500);
			Number newHeight = (Number)js.executeScript("return window.pageYOffset;");
			if(newHeight.equals(lastHeight))
			{
				break;
			}
			lastHeight = newHeight;
		}
		return takeScreenshot(driver, fileName);
	}
	
	public String takeScreenshot(WebDriver driver, String fileName) throws IOException
	{
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+"\\reports\\"+fileName+".png");
		FileUtils.copyFile(srcFile, destFile);
		return System.getProperty("user.dir")+"\\reports\\"+fileName+".png";// return file path in string
	}
	
	public void log(String message)
	{
		Listeners.extentTest.get().log(Status.INFO, message);
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
				|| testName.equals("verifyEmailAddress") || testName.equals("verifyEmailResend") || testName.equals("verifyPasswordReset")
				|| testName.equals("verifyPasswordResetCompleteHeadline");
	}
	
	public void assertTextBoxStates(Map<String, Boolean> states)
	{
		int numberOfOptions = states.size();
		int currentIndex = 1;
		for(Map.Entry<String, Boolean> entry : states.entrySet())
		{
			if(currentIndex < numberOfOptions)
			{
				Assert.assertFalse(entry.getValue(), "TextBox should be inactive for option: " + entry.getKey());
			}
			else
			{
				Assert.assertTrue(entry.getValue(), "TextBox should be active for option: " + entry.getKey());
			}
			currentIndex++;
		}
	}
	
	
}
