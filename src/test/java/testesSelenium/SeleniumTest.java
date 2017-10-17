package testesSelenium;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumTest {

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
		
		driver.get("http://www.canoas.ifrs.edu.br/site/");
		
	}
	
	@Test
	public void testeValidAnchorLinks(){
		
		List<WebElement> links = getAllLinks(driver, "a");
		List<WebElement> brokenLinks = new ArrayList<WebElement>();
		
		for(WebElement link: links){
			
			try{
				
				int msg = checkLink(new URL(link.getAttribute("href")));
				if(msg > 400){
					brokenLinks.add(link);
				}
			}catch(MalformedURLException e){
				
				fail(e.getMessage());
			}catch (ClassCastException e){
				
				brokenLinks.add(link);
			}
		}
		System.out.println("");
		System.out.println("****** LINKS QUEBRADOS ****** ");
		System.out.println("");
		
		for(WebElement link: brokenLinks){
			
			try{
				
				checkLink(new URL(link.getAttribute("href")));
				
			}catch(MalformedURLException e){
				
				fail(e.getMessage());
			}
			catch (ClassCastException e){
				
			    System.out.println("Invalid format: "+ link.getAttribute("href"));
			}
		}
	}
	
	public List<WebElement> getAllLinks(WebDriver driver, String tag){
		
		List <WebElement> elementList = new ArrayList<WebElement>();
		
		elementList = driver.findElements(By.tagName(tag));
		
		for(int i = 0; i < elementList.size(); i++){
			
			WebElement element = elementList.get(i);
			
			if(element.getAttribute("href") == null){
				
				elementList.remove(i);
				i--;
			}
		}
		
		return elementList;
	}
	
	
	public int checkLink(URL url){
		
		String response = "";
		int code = 0;
		try{
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			code = connection.getResponseCode();
			response = connection.getResponseMessage();
			connection.disconnect();
			
		}catch(ProtocolException e){
			
			System.out.printf("Server redirected too many times (20) Link: ");
			code = 404;
			
		} catch(IOException e){
		    System.out.println(e.getMessage());
		}
		
		
		
		System.out.println(url + " " + response);
		
		return code;
	}

}
