package com.zenken.freshers.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {
	
	public static ExtentReports getReportObject()
	{
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("テスト結果");
		reporter.config().setDocumentTitle("Freshers Automation Test Results");
		reporter.config().setTheme(Theme.STANDARD);
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);	
		extent.setSystemInfo("Tester", "プラサナ");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		return extent;
	}
}
