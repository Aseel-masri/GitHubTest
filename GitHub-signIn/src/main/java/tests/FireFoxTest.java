package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.SignInPage;

public class FireFoxTest {
	private WebDriver driver;
	SignInPage signInObj;

	@DataProvider(name = "LoginDataProvider")
	public Object[][] LoginData() {

		return new Object[][]

		{

				{ "asaltest19@gmail.com", "password123456789s*", "Valid Sign-In" },

				{ "asaltest1646449@gmail.com", "password123456789s*", "Invalid Username" },

				{ "asaltest19@gmail.com", "password123456789sgg", "Invalid Password" },

				{ "", "", "Empty Fields" }

		};
	}

	@BeforeMethod
	public void setUp() {
		driver = WebDriverManager.firefoxdriver().create();
	        
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "LoginDataProvider")
	public void LogInTest(String username, String password, String status) {
		driver.manage().window().maximize();
		driver.get("https://github.com/login");
		signInObj = new SignInPage(driver);
		signInObj.loginOperation(username, password);
		
		switch (status) {
		case "Valid Sign-In":

			 assertEquals(driver.getCurrentUrl(), "https://github.com");

			break;
		case "Invalid Username":
			assertNotNull(signInObj.showFlashAlert(), "The js-flash-alert element should exist on the page."); // <Incorrect username or password.> element
			break;
		case "Invalid Password":
			assertNotNull(signInObj.showFlashAlert(), "The js-flash-alert element should exist on the page."); // <Incorrect username or password.> element
			break;
		case "Empty Fields":

			assertTrue(signInObj.checkEmptyfield(), "should display (Please fill out this field)");
			break;
		default:

			break;
		}

	}

	@AfterMethod
	public void close() {
		if (driver != null) {
			driver.quit();
		}
	}
}
