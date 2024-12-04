package QTripUITestwithTestNg;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import cucumberapi.utils.ConfigLoader;
import cucumberapi.utils.SingletonWebDriver;

public class LoginTest {
   @Test
   public void setup(){
      WebDriver driver = SingletonWebDriver.getInstance();
      driver.manage().window().maximize();
      String baseurl = ConfigLoader.GetProperty("baseUrl");
      driver.get(baseurl);
   
      
      }

      
      
   }
   


