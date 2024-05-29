package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage {
	private WebDriver driver;
	By username = By.id("login_field");
	By password = By.id("password");
	By loginButton = By.name("commit");
	private WebElement flashAlert;
	WebElement errorMessage;

	public SignInPage(WebDriver driver) {
		this.driver = driver;
	}

	public void setUsername(String username) {
		driver.findElement(this.username).sendKeys(username);
	}

	public void setPassword(String password) {
		driver.findElement(this.password).sendKeys(password);
	}

	public void login() {

		driver.findElement(loginButton).click();

	}

	public void loginOperation(String username, String password) {

		this.setUsername(username);
		this.setPassword(password);
		this.login();
	}

	public boolean isSuccessfulSignIn() {
		return driver.getTitle().contains("GitHub");
	}

	public boolean iisInvalidDataMessage() {

		if (getErrorMessage().isEmpty() ) {
			
			return checkEmptyfield();
		} else {
			return getErrorMessage().contains("Incorrect username or password.");
		}

	}

	public boolean checkEmptyfield() {
		String validationMessage = driver.findElement(username).getAttribute("validationMessage");
		if ("Please fill out this field.".equals(validationMessage)) {
			return true;
		}

		return false;
	}

	public WebElement showFlashAlert() {
		this.flashAlert = driver.findElement(By.className("js-flash-alert"));
		return this.flashAlert;
	}

	public String getErrorMessage() {
		this.errorMessage = driver.findElement(By.cssSelector(".flash-error"));
		return errorMessage.getText();
	}

}
