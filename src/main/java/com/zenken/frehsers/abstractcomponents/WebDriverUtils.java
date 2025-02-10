package com.zenken.frehsers.abstractcomponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zenken.freshers.pages.user.LoginPage;

public class WebDriverUtils {

	WebDriver driver;
	
	public WebDriverUtils(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//header//img[1]")
	WebElement headerImage;
	
	@FindBy(linkText="Terms")
	WebElement terms;
	
	@FindBy(linkText="Privacy Policy")
	WebElement policy;
	
	@FindBy(css="[class*='u-mb-30']")
	WebElement copyright;
	
	@FindBy(css="[alt='Account icon']")
	WebElement icon;
	
	@FindBy(css="[role='alert']")
	WebElement alert;
	
	@FindBy(xpath="//a[contains(.,'Profile')]")
	WebElement profile;
	
	@FindBy(xpath="//a[contains(.,'Log')]")
	WebElement logout;
	
	@FindBy(css="span [class=u-c-red]")
	List<WebElement> requiredToApplyMarks;
	
	@FindBy(css="[type=submit]")
	WebElement save;
	
	@FindBy(id="updateCancel")
	WebElement cancel;
	
	public void waitUntilElementAppears(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public WebElement getHeaderImage()
	{
		return headerImage;
	}
	
	public String getImageAttribute()
	{
		String imgSrc = headerImage.getDomProperty("src");
		return imgSrc;
	}
	
	public String getAltAttribute()
	{
		String imageAlt = headerImage.getDomProperty("alt");
		return imageAlt;
	}
	
	public void clickByJavaScript(WebElement ele)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", ele);
	}
	
	public void openInNewTab(String link)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.open(arguments[0])", link);
	}
	
	public void clickTerms()
	{
		clickByJavaScript(terms);
	}
	
	public void clickPolicy()
	{
		clickByJavaScript(policy);
	}
	
	public WebElement getCopyrightMark()
	{
		return copyright;
	}
	
	public void loginUser(String emailId, String pass)
	{
		LoginPage loginPage = new LoginPage(driver);
		WebElement id = loginPage.email;
		id.sendKeys(emailId);
		WebElement password = loginPage.password;
		password.sendKeys(pass);
		WebElement login = loginPage.logIn;
		login.click();
	}
	
	public void hoverOver(WebElement ele)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).perform();
	}
	
	public WebElement getIcon()
	{
		return icon;
	}
	
	public String getAlert()
	{
		waitUntilElementAppears(alert);
		return alert.getText();
	}
	
	public List<WebElement> getDropdownOptions()
	{
		List<WebElement> elements = new ArrayList<>();
		elements.add(profile);
		elements.add(logout);
		return elements;
	}
	
	public void clickProfile()
	{
		profile.click();
	}
	
	public void clickLogOut()
	{
		logout.click();
	}
	
	public void selectDropdown(WebElement ele, String text)
	{
		Select select = new Select(ele);
		select.selectByVisibleText(text);
	}
	
	public void selectDropdownByIndex(WebElement ele, int index)
	{
		Select select = new Select(ele);
		select.selectByIndex(index);
	}
	
	public String getDropdownText(WebElement ele)
	{
		Select select = new Select(ele);
		String selectedText = select.getFirstSelectedOption().getText();
		return selectedText;
	}
	
	public ArrayList<String> switchTabs(int i)
	{
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(i));
		return tabs;
	}
	
	public String extractLinkFromMail(String url, String emailBody)
	{
		String urlStart = url;
		int startIndex = emailBody.indexOf(urlStart);
		int endIndex = emailBody.indexOf("\n", startIndex);
		String link = emailBody.substring(startIndex, endIndex);
		return link;
	}
	
	public String getPageTitle()
	{
		String title = driver.getTitle();
		return title;
	}
	
	public boolean getState(WebElement ele, WebElement ele2)
	{
		ele.click();
		boolean state = ele2.isEnabled();
		return state;
	}
	
	public Map<String, Boolean> getDropdownTextBoxStates(WebElement dropdown, WebElement textBox)
	{
		Map<String, Boolean> optionsStateMap = new LinkedHashMap<>();
		Select select = new Select(dropdown);
		int numberOfOptions = select.getOptions().size();
		for(int i=0; i<numberOfOptions; i++)
		{
			select.selectByIndex(i);
			String optionText = select.getOptions().get(i).getText();
			boolean isTextBoxActive = textBox.isEnabled();
			optionsStateMap.put(optionText, isTextBoxActive);
		}
		return optionsStateMap;
	}
	
	public String monitorDownloadLink(int maxRetries, List<WebElement> links) throws InterruptedException
	{
		int linkCount = links.size();
		int retryCount = 0;
		while(retryCount < maxRetries)
		{
			driver.navigate().refresh();
			int currentLinkCount = links.size();
			if(currentLinkCount > linkCount)
			{	
				return links.get(currentLinkCount -1).getText();
			}else {
				retryCount++;
				Thread.sleep(3000);
			}
		}
		throw new RuntimeException("New download link did not appear after "+maxRetries+" attempts.");
	}
	
	public void clearText(WebElement ele)
	{
		ele.clear();
	}
	
	public void clickSave()
	{
		clickByJavaScript(save);
	}
	
	public void clickCancel()
	{
		clickByJavaScript(cancel);
	}
	
	public boolean isSectionComplete()
	{
		return !requiredToApplyMarks.isEmpty() && requiredToApplyMarks.get(0).isDisplayed();
	}
	
	public int getFileCount()
	{
		File downloadFolder = new File(System.getProperty("user.dir")+File.separator+"downloads");
		int initialFileCount = downloadFolder.listFiles().length;
		return initialFileCount;
	}
	
	public boolean isFileDownloaded(int initialFileCount, String extension)
	{
		File downloadFolder = new File(System.getProperty("user.dir")+File.separator+"downloads");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		boolean isDownloaded = wait.until((ExpectedCondition<Boolean>) driver -> 
		{int currentFileCount = downloadFolder.listFiles().length;
		if(currentFileCount > initialFileCount) {
			File[] files = downloadFolder.listFiles();
			if(files != null && files.length > 0) {
				Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
				return files[0].getName().endsWith(extension);
			}
		}
		return false;
		});
		return isDownloaded;
	}
	
	public String getDownloadedFileName()
	{
		File downloadFolder = new File(System.getProperty("user.dir")+File.separator+"downloads");
		File[] files = downloadFolder.listFiles();
		if(files != null && files.length > 0)
		{
			Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
			return files[0].getName();
		}else {
			return null;
		}
	}
	
	public String convertJSTtoIST(String inputDateTime)
	{
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
		String cleanedDateTime = inputDateTime.replace("\t", " ");
		cleanedDateTime = cleanedDateTime.substring(0, cleanedDateTime.length()-1)
				+ (inputDateTime.endsWith("1") ? " AM" : " PM");
		LocalDateTime jstDateTime = LocalDateTime.parse(cleanedDateTime, inputFormatter);
		ZonedDateTime jstZoned = jstDateTime.atZone(ZoneId.of("Asia/Tokyo"));
		ZonedDateTime istZoned = jstZoned.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy, hh:mma z", Locale.ENGLISH);
		return istZoned.format(outputFormatter);
	}
	
	public void unzip(File zipFile) throws FileNotFoundException, IOException
	{
		File destDir = new File(System.getProperty("user.dir")+"/downloads/extracted");
		destDir.mkdir();
		try(ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))){
			ZipEntry entry;
			while((entry = zis.getNextEntry()) != null){
				File extractedFile = new File(destDir, entry.getName());
				try(FileOutputStream fos = new FileOutputStream(extractedFile)){
					byte[] buffer = new byte[1024];
					int lenght;
					while((lenght = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, lenght);
					}
				}
				zis.closeEntry();
			}
		}
	}
	
	public String getCsvHeader(String filePath) throws FileNotFoundException, IOException
	{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Shift_JIS"))){
			String firstLine = reader.readLine();
			return firstLine;
		}
	}
	
	public String getCsvFirstLine(String filePath) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Shift_JIS"))){
			reader.readLine();
			String firstLine = reader.readLine();
			return firstLine;
		}
	}
	
	public String[] santizeArray(String[] array)
	{
		return Arrays.stream(array).map(value -> value.replaceAll("^\"|\"$", ""))
				.toArray(String[]::new);
	}
	
	public String[] getCsvRowById(File csvFile, String targetId) throws IOException
	{
		try (BufferedReader reader = new BufferedReader(new InputStreamReader
				(new FileInputStream(String.valueOf(csvFile)), "Shift_JIS"))) {
			String header = reader.readLine();
			String[] headers = header.split(",");
			int idColumnIndex = -1;
			for(int i=0; i<headers.length; i++) {
				if(headers[i].equals("求職者ID")) {
					idColumnIndex = i;
					break;
				}
			}
			String line;
			while((line = reader.readLine()) != null) {
				String [] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				if(values[idColumnIndex].equals(targetId)) {
					return values;
				}
			}
		}
		return null;
	}
	
	public boolean isElementPresent(WebElement element)
	{
		try {
			return element.isDisplayed();
		}catch(Exception e) {
			return false;
		}
	}
	
}
