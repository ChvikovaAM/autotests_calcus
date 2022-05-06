package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CorrectCalculationOfTheMortgagePayment {
    private String driverPath = "D:\\Chvikova_qa\\chromedriver.exe";
    private String url = "https://calcus.ru/kalkulyator-ipoteki";

    @Given("^Open the application in Google Chrome$")
    public void given() {
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.get(url);

        browser.findElement(By.xpath("//h1[text()='Ипотечный калькулятор']"));
        browser.findElement(By.xpath("//a[text()='По стоимости недвижимости']"));
        browser.findElement(By.xpath("//a[text()='По сумме кредита']"));
        browser.findElement(By.xpath("//div[text()='Стоимость недвижимости']"));
        browser.findElement(By.xpath("//div[text()='Первоначальный взнос']"));
        browser.findElement(By.xpath("//div[text()='Сумма кредита']"));
        browser.findElement(By.xpath("//div[text()='Срок кредита']"));
        browser.findElement(By.xpath("//div[contains(text(), 'Процентная ставка')]"));
        browser.findElement(By.xpath("//div[contains(text(), 'Тип ежемесячных платежей')]"));
    }

    @When("^Filling the form with values$")
    public void when() {
        System.out.println("kek when");
    }

    @Then("^Getting a mortgage calculation$")
    public void then() {
        System.out.println("kek then");
    }
}
