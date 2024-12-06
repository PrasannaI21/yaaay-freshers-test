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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public ExtentReports extent;
	
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
		if(method.getName().equals("verifyConsentFormDL")) {
			prefs.put("plugins.always_open_pdf_externally", true);
		}
		ChromeOptions options = new ChromeOptions();
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.setExperimentalOption("prefs", prefs);
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
		FirefoxOptions options2 = new FirefoxOptions();
//		options2.addArguments("-profile", "path_to_clean_profile_directory");
//		options2.setCapability("marionette", true);
//		options2.setLogLevel(FirefoxDriverLogLevel.TRACE);
//		options2.addArguments("--headless");
//		options2.addPreference("dom.max_script_run_time", 0);
//		options2.addPreference("layout.css.devPixelsPerPx", "1.0");
		options2.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		options2.addPreference("network.http.speculative-parallel-limit", 0);
//		options2.addPreference("network.http.max-persistent-connections-per-server", 6);
//		options2.addPreference("dom.webnotifications.enabled", false);
//		options2.addPreference("browser.tabs.remote.autostart", false);
		currentTestMethod.set(method.getName());
		initializeDriver(options, options2, edgeOptions);
		
	}
	
	public WebDriver setup() throws IOException
	{
		ChromeOptions options = new ChromeOptions();
		FirefoxOptions options2 = new FirefoxOptions();
		EdgeOptions edgeOptions = new EdgeOptions();
//		Map<String, Object> prefs = new HashMap<String, Object>();
		initializeDriver(options, options2, edgeOptions);
		return driver;
	}
	
	public void initializeDriver(ChromeOptions options, FirefoxOptions options2, EdgeOptions edgeOptions) throws IOException
	{
		if(!reuseBrowserSession)//セッションを再利用しない場合のみにWebDriverを初期化
		{
			String browser = System.getProperty("browser") != null ? System.getProperty("browser") : 
					getProperties().getProperty("browser");
			if(browser.contains("chrome")) {
				WebDriverManager.chromedriver().setup();
				if(browser.contains("headless")) {
					options.addArguments("--headless");
				}
				driver = new ChromeDriver(options);
				driver.manage().window().setSize(new Dimension(1440, 900));
			}else if(browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
//				System.setProperty("webdriver.gecko.driver", "C:\\Users\\prasa\\Downloads\\geckodriver-v0.35.0-win-aarch64\\geckodriver.exe");
				driver = new FirefoxDriver(options2);
			}else if(browser.contains("edge")) {
				WebDriverManager.edgedriver().setup();
				if(browser.contains("headless")) {
					edgeOptions.addArguments("--headless");
				}				
				driver = new EdgeDriver(edgeOptions);
			}
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
//		if(extent != null) {
//			extent.flush();
//		}
	}
	
	public Properties getProperties() throws IOException
	{
		properties = new Properties();
//		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\zenken\\freshers\\resources\\UserTexts.properties");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/zenken/freshers/resources/UserTexts.properties");
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
