package cucumberapi.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SingletonWebDriver{
    private static WebDriver driver;
    
    private SingletonWebDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    public static WebDriver getInstance(){
        if(driver==null){
            synchronized (SingletonWebDriver.class) {
                if(driver==null)
                {
                new SingletonWebDriver();
                }
            }
        }
        return driver;
    }
}