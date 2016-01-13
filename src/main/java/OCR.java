import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.basics.Debug;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OCR {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public OCR(AppiumDriver driver) {
        this.driver = driver;
    }

    public void clickByImage(String targetImgPath) {
        Point2D coords = getCoords(takeScreenshot(), targetImgPath);
        if ((coords.getX() >= 0) && (coords.getY() >= 0)) {
            driver.tap(1, (int) coords.getX(), (int) coords.getY(), 100);
        } else {
            throw new ElementNotVisibleException("Element not found - " + targetImgPath);
        }
    }

    public Point2D getCoords(BufferedImage baseImg, String targetImgPath) {
        Match m;
        Finder f = new Finder(baseImg);
        Point2D coords = new Point2D.Double(-1, -1);

        f.find(targetImgPath);
        if (f.hasNext()) {
            m = f.next();
            coords.setLocation(m.getTarget().getX(), m.getTarget().getY());
        }
        return coords;
    }

    public List<Point2D> getCoordsForAllMatchingElements(String targetImgPath) {
        Finder f = new Finder(takeScreenshot());
        List<Point2D> coordsList = new ArrayList<>();
        Match m;
        f.findAll(targetImgPath);

        while (f.hasNext()) {
            m = f.next();
            coordsList.add(new Point2D.Double(m.getTarget().getX(), m.getTarget().getY()));
        }
        return coordsList;
    }

    public List<Point2D> getCoordsForAllMatchingElements(BufferedImage baseImg, String targetImgPath) {
        Finder f = new Finder(baseImg);
        List<Point2D> coordsList = new ArrayList<>();
        Match m;
        f.findAll(targetImgPath);

        while (f.hasNext()) {
            m = f.next();
            coordsList.add(new Point2D.Double(m.getTarget().getX(), m.getTarget().getY()));
        }
        return coordsList;
    }

    public BufferedImage takeScreenshot() {
        Debug.setDebugLevel(3);
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(scrFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public Boolean elementExists(String targetImgPath) {
        Point2D coords = getCoords(takeScreenshot(), targetImgPath);
        return (coords.getX() >= 0) && (coords.getY() >= 0);
    }

    public void waitUntilImageExists(final String targetImgPath, long timeoutDuration) {
        new WebDriverWait(driver, timeoutDuration).until((WebDriver driver) -> elementExists(targetImgPath));
    }
}


