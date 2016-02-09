import io.appium.java_client.AppiumDriver;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.basics.Settings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OCRTest {

    private AppiumDriver driver;
    private OCR ocr;

    private static String imgDir = System.getProperty("user.dir") + "/src/main/resources/";
    private static double DEFAULT_MIN_SIMILARITY = 0.8;

    @Before
    public void setUp() throws Exception {
        ocr = new OCR(driver);
    }

    @Test
    public void testGetCoordsWithSetMinSimilarity_revertsToDefaultMinSimilarity() throws Exception {
        String imageName = "acceptAll.png";
        String imageLocation = imgDir + imageName;
        BufferedImage testImg = convertImgFileToBufferedImage(imageLocation);

        assertThat("default minSimilarity should be used", Settings.MinSimilarity, is(DEFAULT_MIN_SIMILARITY));
        ocr.getCoords(testImg, imageLocation, 0.9);
        assertThat("minSimilarity should revert back to default", Settings.MinSimilarity, is(DEFAULT_MIN_SIMILARITY));
    }


    private BufferedImage convertImgFileToBufferedImage(String imagePath){
        BufferedImage in = null;
        try {
            in = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
}