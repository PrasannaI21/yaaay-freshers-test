package features;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/features", glue={"stepDefinitions"}, tags="@run",
plugin= {"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}, monochrome=true)
public class TestRunner extends AbstractTestNGCucumberTests{

}//html:target/cucumber.html
