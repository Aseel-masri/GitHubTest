package tests;


import static org.testng.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.SignInPage;

public class browserTest {
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

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) {
		// driver = WebDriverManager.chromedriver().create();
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = WebDriverManager.chromedriver().create();
			break;
		case "firefox":
			driver = WebDriverManager.firefoxdriver().create();
			break;
		case "edge":
			driver = WebDriverManager.edgedriver().create();
			break;
		default:
			throw new IllegalArgumentException("Browser \"" + browser + "\" not supported.");
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "LoginDataProvider")
	public void LogInTest(String username, String password, String status) {
		driver.manage().window().maximize();
		driver.get("https://github.com/login");
		signInObj = new SignInPage(driver);
		signInObj.loginOperation(username, password);

		switch (status) {
		case "Valid Sign-In": //Successful sign-in; redirected to home page
			Assert.assertTrue(driver.getTitle().contains("GitHub"), "Valid Sign-In test failed."); 
			break;
		case "Invalid Username": //Sign-in fails; appropriate error message
		case "Invalid Password":
			Assert.assertTrue(signInObj.getErrorMessage().contains("Incorrect username or password."),
					"Invalid username or password test failed.");
			break;
		case "Empty Fields": //Sign-in fails; 
			assertTrue(signInObj.checkEmptyfield(), "should display (Please fill out this field)"); // Check that the "Please fill out this field" alert is displayed
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
