package tests;


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

				{ "asaltest19@gmail.com", "password123456789s*", true },

				{ "asaltest1646449@gmail.com", "password123456789s*", false },

				{ "asaltest19@gmail.com", "password123456789sgg", false },

				{ "", "", false }

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
	public void LogInTest(String username, String password, boolean expectedResult) {
		driver.manage().window().maximize();
		driver.get("https://github.com/login");
		signInObj = new SignInPage(driver);
		signInObj.loginOperation(username, password);
		if (expectedResult) {
			Assert.assertTrue(signInObj.isSuccessfulSignIn());

		} else {

			Assert.assertTrue(signInObj.iisInvalidDataMessage());

		}

	}

	@AfterMethod
	public void close() {
		if (driver != null) {
			driver.quit();
		}
	}
}
