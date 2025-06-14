package hooks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import factory.Base;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;



public class Hooks {

	WebDriver driver;
	Properties p;
	
	@Before
	public void setup(Scenario scenario) throws IOException
	{
	driver=Base.initilizeBrowser();
	p=Base.getProperties();
	if (scenario.getSourceTagNames().contains("@flights")) {
		driver.get(p.getProperty("flights.appURL"));
    } else if (scenario.getSourceTagNames().contains("@hotels")) {
    	driver.get(p.getProperty("hotels.appURL"));
    }
	driver.manage().window().maximize();
	}
	
	@After
	public void teardown(Scenario scenario)
	{
	 driver.quit();
	}
}
