package practiceTest.seleniumFrameWorkDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
//import org.testng.annotations.Test;
public class DesignStandalone {
	public static void main (String[] args) {
		
		String productname = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//add username and password and click on login 
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.id("userEmail")).sendKeys("NatsuDragon@guild.com");
		driver.findElement(By.id("userPassword")).sendKeys("Password");
		driver.findElement(By.id("login")).click();
		driver.manage().window().maximize();
		//add wait to display all the contents of a page
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
	// get list of products and iterate - use java streams 
		List <WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		// stream iterate through list of products and filter out the or iterate get the product name by css -b
		
	WebElement prod = 	products.stream().filter(product-> product.findElement(By.cssSelector("b")).getText().equals(productname)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//resolving synchronization issue using 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();//click on cart
		
		//logic to verify products added to cart 
	
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
  Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productname));

	Assert.assertTrue(match);	
	//click on checkout
	
	driver.findElement(By.xpath("//li[@class = 'totalRow']/button")).click();
	
	Actions a = new Actions(driver);
	a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	//(//button[contains(@class,'ta-item')])[2]
	driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	driver.findElement(By.cssSelector(".action__submit")).click();
	
	String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	
	Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	driver.close();
	
	
	}
	
}
