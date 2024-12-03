package QTripUITestwithTestNg;

import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserHandling {
   @Test
   public void BrowserWindowHandles() throws InterruptedException{
      WebDriverManager.chromedriver().setup();
      WebDriver driver = new ChromeDriver();
      driver.manage().window().maximize();
      driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.open('https://mvnrepository.com/','_blank')");
      js.executeScript("window.open('https://google.com', '_blank');");
      js.executeScript("window.open('https://yahoo.com', '_blank');");
      Set<String> allHandles = driver.getWindowHandles();
      String MainHandle = driver.getWindowHandle();
      for(String handle : allHandles){
            if(!handle.contentEquals(MainHandle)){
            driver.switchTo().window(handle);
            Thread.sleep(5000);
            driver.close();
         }driver.switchTo().window(MainHandle);
      }

      
      
   }
   

}
