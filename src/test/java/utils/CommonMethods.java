package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class CommonMethods extends PageInitalizer{

    public static WebDriver driver; // it is interface not method >> mean i can implement webDriver in many faces web
    //Static var also known as a class.. Static means there is only one copy and Share among of all classes
    public void openBrowserAndLaunchApplication() {
        String browserName = System.getProperty("browser");
        openBrowser(browserName);
        System.out.println("the browser name is " + browserName);
        //
    }
    public void openBrowser(String browserName){
        switch (ConfigReader.read("browser")){
            case "Chrome":
                ChromeOptions options=new ChromeOptions();
                options.setHeadless(false);
                driver = new ChromeDriver(options);
                break;
            case "FireFox":
                driver=new FirefoxDriver();
                break;
            case "Edge":
                driver = new EdgeDriver();
                break;
            case "Safari":
                driver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("Invalid Browser Name");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get(ConfigReader.read("url"));
        //this, method will call all the objects
        initializePageObjects(); /*so that as soon as we open browser all the objects of classes were created
automatically*/
    }

    public void closeBrowser() {
        if(driver!= null) {
            driver.quit();
        }
    }

    public void sendText(String text, WebElement element){
        element.clear();
        element.sendKeys(text);
    }

    public void selectFromDropDown(WebElement dropDown, String visibleText){
        Select sel =new Select(dropDown);
        sel.selectByVisibleText(visibleText);
    }
    public void selectFromDropDown(String value, WebElement dropDown ){
        Select sel =new Select(dropDown);
        sel.selectByValue(value);
    }
    public void selectFromDropDown(WebElement dropDown, int index ){
        Select sel =new Select(dropDown);
        sel.selectByIndex(index);
    }

    public WebDriverWait getwait(){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        return  wait;
    }

    public void waitForElementToBeClickAble(WebElement element){
        getwait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void click(WebElement element){
        waitForElementToBeClickAble(element);
        element.click();
    }

    public JavascriptExecutor getJSExecutor(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public void jsClick(WebElement element){

        getJSExecutor().executeScript("arguments[0].click();", element);
    }

    public byte[] takeScreenshot(String fileName){  // give specific file name in screen shot package to not make my random name come for screen shot file
        //it accepts array of byte in cucumber for the screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] picByte = ts.getScreenshotAs(OutputType.BYTES); // the image what we are create in screenshot as a byte ,so array of byte to make cucumber understand this image and impact in the report
        File sourFile = ts.getScreenshotAs(OutputType.FILE);  //save as a file on computer

        try {
            FileUtils.copyFile(sourFile,
                    new File(Constants.SCREENSHOT_FILEPATH + fileName+ " "+
                            getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return picByte;
    }

    public String getTimeStamp(String pattern){
        //this method will return the timestamp which we will add in ss method
        Date date = new Date(); // modulo from Java .. this date responsible for date
        //12-01-1992-21-32-34
        //yyyy-mm-dd-hh-mm-ss
        SimpleDateFormat sdf = new SimpleDateFormat(pattern); // this sdf .. responsible format the date  as pattern im going to give you
        return sdf.format(date); // format date as pattern
    }
}
