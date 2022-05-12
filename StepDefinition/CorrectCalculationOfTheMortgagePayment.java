package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.IMortgageCalculation;
import src.MortgageCalculation;

import java.text.DecimalFormat;
import java.util.Random;

public class CorrectCalculationOfTheMortgagePayment {
    private String driverPath = "D:\\Chvikova_qa\\chromedriver.exe";
    private String url = "https://calcus.ru/kalkulyator-ipoteki";
    private WebDriver browser;
    private long sum = 9600000;
    private int age = 20;
    private Random random = new Random();
    private int randomPercent;
    private IMortgageCalculation mortgageCalculation = new MortgageCalculation();

    public CorrectCalculationOfTheMortgagePayment() //конструктор
    {
        System.setProperty("webdriver.chrome.driver", driverPath);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.get(url);
    }

    @Given("^Open the application in Google Chrome$")
    public void given() {
        browser.findElement(By.xpath("//h1[text()='Ипотечный калькулятор']"));
        browser.findElement(By.xpath("//a[text()='По стоимости недвижимости']"));
        browser.findElement(By.xpath("//a[text()='По сумме кредита']"));
        browser.findElement(By.xpath("//div[text()='Стоимость недвижимости']"));
        browser.findElement(By.xpath("//div[text()='Первоначальный взнос']"));
        browser.findElement(By.xpath("//div[text()='Сумма кредита']"));
        browser.findElement(By.xpath("//div[text()='Срок кредита']"));
        browser.findElement(By.xpath("//div[normalize-space(text())='Процентная ставка']"));
        browser.findElement(By.xpath("//div[normalize-space(text())='Тип ежемесячных платежей']"));
    }

    @When("^Filling the form with values$")
    public void when() {
        //Стоимость недвижимости = 12000000
        browser.findElement(By.xpath("//input[@name='cost']")).sendKeys("12000000");

        //Первоначальный взнос в процентах %
        WebElement selectElement = browser.findElement(By.xpath("//select[@name='start_sum_type']"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("%");

        //Первоначальный взнос = 20
        browser.findElement(By.xpath("//input[@name='start_sum']")).sendKeys("20");

        //Первоначальный взнос = (2 400 000руб.) --в скобочках?
        browser.findElement(By.xpath("//div[text()='(2 400 000 руб.)']"));

        //Сумма кредита = 9 600 000руб.
        browser.findElement(By.xpath(String.format("//span[translate(text(), ' ', '')=%d]", sum)));
        browser.findElement(By.xpath("//span[text()='руб.']"));

        //Срок кредита = 20
        browser.findElement(By.xpath("//input[@name='period']")).sendKeys(String.valueOf(age));

        //Генерация числа от 5 до 12 и вставка его в Процентную ставку
        randomPercent = random.nextInt(8) + 5;
        browser.findElement(By.xpath("//input[@name='percent']")).sendKeys(String.valueOf(randomPercent));
        //browser.findElement(By.xpath("//input[@name='percent']")).sendKeys("3");

        //Радиобаттон Аннуитетные
        if (!browser.findElement(By.xpath("//input[@id='payment-type-1']")).isSelected()
        && browser.findElement(By.xpath("//input[@id='payment-type-2']")).isSelected()) {
            throw new ElementNotSelectableException("Пожалуйста, выберите аннуитетные!");
        }

        //Кнопка Рассчитать
        browser.findElement(By.xpath("//input[@value='Рассчитать']")).click();
    }

    @Then("^Getting a mortgage calculation$")
    public void then() {
        browser.findElement(By.xpath("//div[@class='row no-gutters split']"));

        double expectedResult = mortgageCalculation.calculate(sum, randomPercent / 100. / 12, age * 12);

        DecimalFormat df = new DecimalFormat("0.00");
        String formatExpectedResult = df.format(expectedResult);
        String expectedXPath = String.format("//div[@class='calc-result-value result-placeholder-monthlyPayment' and translate(text(), ' ', '')='%s']", formatExpectedResult);

        WebDriverWait waitBrowser = new WebDriverWait(browser, 3);
        waitBrowser.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(expectedXPath)));

        browser.close();
    }
}