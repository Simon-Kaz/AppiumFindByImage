import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

public class OCRNekoAtsumeTestSuite {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private OCR OCR;
    private File imgDir;

    @Before
    public void setUp() throws Exception {

        //Appium setup for the app
        //needs to be installed on target device before the test
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appPackage", "jp.co.hit_point.nekoatsume");
        capabilities.setCapability("appActivity", "jp.co.hit_point.nekoatsume.GActivity");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformVersion", "5.0.1");

        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);

        //Sikuli settings
        OCR = new OCR(driver);

        //location of screenshots
        File classpathRoot = new File(System.getProperty("user.dir"));
        imgDir = new File(classpathRoot, "src/main/resources");

        //switch to native app + portrait mode
        driver.context("NATIVE_APP");
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void gatherAllGiftsTest() {
        String giftsAwaitImgLoc = imgDir + "/giftsAwait.png";
        String acceptAllImgLoc = imgDir + "/acceptAll.png";

        OCR.waitUntilImageExists(giftsAwaitImgLoc, 30);
        OCR.clickByImage(giftsAwaitImgLoc);
        OCR.waitUntilImageExists(acceptAllImgLoc, 10);
        OCR.clickByImage(acceptAllImgLoc);
    }

    @Test
    public void refillTest() {
        String foodBowlImgLoc = imgDir + "/foodBowl.png";
        String yesBowlImgLoc = imgDir + "/yesBowl.png";

        OCR.waitUntilImageExists(foodBowlImgLoc, 30);
        OCR.clickByImage(foodBowlImgLoc);
        OCR.clickByImage(yesBowlImgLoc);
    }

    @Test
    public void refillWithLongPressTest() {
        String foodBowlImgLoc = imgDir + "/foodBowl.png";
        String yesBowlImgLoc = imgDir + "/yesBowl.png";

        OCR.waitUntilImageExists(foodBowlImgLoc, 30);
        OCR.longPressByImage(foodBowlImgLoc);
        OCR.longPressByImage(yesBowlImgLoc);
    }
}
