import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class TestNG_File {

    WebDriver driver = null ;

    // POM Design


    @DataProvider (name = "validData")
    public Object[][] validMethod()
    { return new Object[][] {
            {"https://demo.guru99.com/V4/","mngr419803","puzurus"},
    };}

    @DataProvider (name = "inValidData")
    public Object[][] inValidMethod()
    { return new Object[][] {
            {"https://demo.guru99.com/V4/","ASD412ASD3","puzurus"},
            {"https://demo.guru99.com/V4/","mngr419803","puzuruw"},
            {"https://demo.guru99.com/V4/","ASD412ASD3","puzuruw"}
    };}




    @BeforeTest
    public void openBrowser()
    {
        String Path =  System.getProperty("user.dir") + "\\src\\main\\resources\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver",Path);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();



    }




    @Test (dataProvider = "validData")
    public void Fun01_Valid_Login (String Url ,String Username , String Password) throws Exception {


        driver.navigate().to(Url);

        Thread.sleep(1000);

        driver.findElement(By.name("uid")).sendKeys(Username);
        driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys(Password);
        driver.findElement(By.name("btnLogin")).click();


        takeSnapShot(driver,"D:\\Testing Courses\\Automation Project - Guru99 [ TestNG ]\\ScreenShoots\\valid.png");
        Thread.sleep(2000);

        String expectedResult = driver.findElement(By.cssSelector("[behavior=\"alternate\"]")).getText();
        Assert.assertEquals("Welcome To Manager's Page of Guru99 Bank", expectedResult, "Login Functionality");


    }



@Test (dataProvider = "inValidData")
public void Fun02_inValid_Login (String Url ,String Username , String Password) throws Exception {

    driver.navigate().to(Url);

    Thread.sleep(1000);

    driver.findElement(By.name("uid")).sendKeys(Username);
    driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys(Password);
    driver.findElement(By.name("btnLogin")).click();


    Thread.sleep(2000);

    String actualResult = driver.switchTo().alert().getText();
    String expectedResult = "User or Password is not valid";

    Assert.assertTrue(actualResult.contains(expectedResult));

    driver.switchTo().alert().accept();


}





    @AfterTest
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();

    }


    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot) webdriver);

        //Call getScreenshotAs method to create image file

        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile = new File(fileWithPath);

        //Copy file at destination

        FileHandler.copy(SrcFile, DestFile);

    }




}
