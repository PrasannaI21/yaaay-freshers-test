package com.zenken.freshers.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.zenken.freshers.resources.ExtentReporter;

public class Listeners extends BaseTest implements ITestListener, IConfigurationListener{

	ExtentReports extent = ExtentReporter.getReportObject();
	ExtentTest test;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		String description = result.getMethod().getDescription();
		String featureName = result.getTestClass().getRealClass().getSimpleName();
		test = extent.createTest(result.getMethod().getMethodName(), description);
		test.assignCategory(featureName);
		extentTest.set(test);
//		if(result.wasRetried()) {
//			extentTest.get().log(Status.WARNING, "Test was retried");
//		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
//		if(result.wasRetried()) {
//			extentTest.get().pass("Test passed after being retried");
//			extentTest.get().log(Status.WARNING, "Test was retried");
//		}else {
			extentTest.get().pass("Test Passed");
//		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
//		if(!result.wasRetried())
//		{
//			extentTest.get().fail(result.getThrowable());
//			try {
//				driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String filePath = null;
//			try {
//				filePath = waitForScrollToComplete(driver, result.getMethod().getMethodName());
//			} catch (IOException | InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
//		}else {
//			extentTest.get().log(Status.INFO, "Test failed but was retried");
//		}
		extentTest.get().fail(result.getThrowable());
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String filePath = null;
		try {
			filePath = waitForScrollToComplete(driver, result.getMethod().getMethodName());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
//		if(!result.wasRetried())
//		{
			extentTest.get().skip("Test Skipped");
//			extentTest.get().skip(result.getThrowable());
//		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}

	@Override
	public void onConfigurationSuccess(ITestResult tr) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.onConfigurationSuccess(tr);
	}

	@Override
	public void onConfigurationSuccess(ITestResult tr, ITestNGMethod tm) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.onConfigurationSuccess(tr, tm);
	}

	@Override
	public void onConfigurationFailure(ITestResult tr) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.onConfigurationFailure(tr);
		ExtentTest configFailTest = extent.createTest("Configuration Failure: "+ tr.getMethod().getMethodName());
		configFailTest.fail(tr.getThrowable());
//		ExtentTest test = extentTest.get();
//		if(test == null)
//		{
//			test = extent.createTest(tr.getMethod().getMethodName()+" (Configuration)");
//			extentTest.set(test);
//		}
//		extentTest.get().fail(tr.getThrowable());
	}

	@Override
	public void onConfigurationFailure(ITestResult tr, ITestNGMethod tm) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.onConfigurationFailure(tr, tm);
	}

	@Override
	public void onConfigurationSkip(ITestResult tr) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.onConfigurationSkip(tr);
//		ExtentTest configSkipTest = extent.createTest("Configuration Skipped: "+ tr.getMethod().getMethodName());
//		configSkipTest.skip("Configuration skipped");
//		extentTest.get().skip("Configuration skipped");
	}

	@Override
	public void onConfigurationSkip(ITestResult tr, ITestNGMethod tm) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.onConfigurationSkip(tr, tm);
	}

	@Override
	public void beforeConfiguration(ITestResult tr) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.beforeConfiguration(tr);
	}

	@Override
	public void beforeConfiguration(ITestResult tr, ITestNGMethod tm) {
		// TODO Auto-generated method stub
		IConfigurationListener.super.beforeConfiguration(tr, tm);
	}
	
}
