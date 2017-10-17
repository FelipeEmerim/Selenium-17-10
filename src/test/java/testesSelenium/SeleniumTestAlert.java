package testesSelenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.*;

public class SeleniumTestAlert {

private static WebDriver driver;
	
	@BeforeClass
	public static void SetupClass() {
		
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+File.separator+"resources/geckodriver");
		
		driver = new FirefoxDriver();	
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}
	
	@AfterClass
	public static void tearDownClass() throws InterruptedException{
		
		Thread.sleep(3000);
		driver.close();
	}
	
	@Before
	public void setUp(){
		
		driver.get("http://toolsqa.com/handling-alerts-using-selenium-webdriver/");
		
	}
	
	@Test
	public void testSimpleAlert(){
		
		WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[2]/div/div/p[4]/button"));
		
		element.click();
		
		Alert simpleAlert = driver.switchTo().alert();
		assertThat(simpleAlert.getText()).as("A simple Alert");
		simpleAlert.accept();
			
	}
	
	

}
