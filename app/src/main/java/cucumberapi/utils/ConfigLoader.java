package cucumberapi.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static{
        try(InputStream input = new FileInputStream("C:\\Users\\karri pavan kalyan\\workspace\\bytes\\CucumberApi\\app\\src\\main\\java\\cucumberapi\\config\\config.properties")){
            properties.load(input);
               System.out.println("properties loaded succesfully");
        
        }
      catch (IOException ex) {
        ex.printStackTrace();
        }
    }
    public static String GetProperty(String key){
       return properties.getProperty(key);
    }
}
