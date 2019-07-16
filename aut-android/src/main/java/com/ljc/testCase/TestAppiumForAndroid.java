package com.ljc.testCase;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


/**
 * @Author Lijc
 * @Description
 * @Date 2019/4/1-15:11
 **/
public class TestAppiumForAndroid {

    private AndroidDriver driver;

    @BeforeClass
    public void initDriver() throws MalformedURLException {
        File clasPathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(clasPathRoot,"apps");
        File app = new File(appDir,"Kf-1.0-20181121-test.apk");

        DesiredCapabilities caps=new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,"7.1.1");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,"RC7HDEQG7SHEMZSK");
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"10");
        caps.setCapability(MobileCapabilityType.NO_RESET,true);
        caps.setCapability("noSign",true);

        //support Chinese支持中文输入
//        caps.setCapability("unicodeKeyboard",true);
//        caps.setCapability("resetKeyboard",true);


        //x5内核
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setExperimentalOption("androidProcess","com.tencent.mm:appbrand0");

        //caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
//        caps.setCapability(MobileCapabilityType.APP,"");
        caps.setCapability(MobileCapabilityType.BROWSER_NAME,"");
//        caps.setCapability(MobileCapabilityType.FORCE_MJSONWP,true);
        caps.setCapability("autoGrantPermissions",true);
//        caps.setCapability("appPackage", "com.tencent.mm");
//        caps.setCapability("appActivity", "ui.LauncherUI");
//        caps.setCapability("chromedriverExecutableDir","C:\\Users\\Administrator\\Desktop\\chromedriver_win32 (1)\\");
        caps.setCapability("recreateChromeDriverSessions",true);
        caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        driver =new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
//        driver =new AndroidDriver<>(new URL("http://192.168.5.94:4723/wd/hub"), caps);

        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);


    }

    @Test
    public void setUp() throws InterruptedException {
        driver.unlockDevice();
        int width=driver.manage().window().getSize().getWidth();
        int height=driver.manage().window().getSize().getHeight();
        System.out.println(width+"*"+height);

        sleep(6000);

//        driver.findElement(By.id("word")).sendKeys("world");
//        driver.findElement(By.className("bn")).click();
//        driver.findElement(By.id("com.android.chrome:id/button_primary")).click();
//        driver.findElement(By.id("com.huak.hhp5a:id/et_userName")).sendKeys("gongre02");
//        driver.findElement(By.id("com.huak.hhp5a:id/et_userPwd")).sendKeys("Aa123456");
//        driver.findElement(By.id("com.huak.hhp5a:id/btn_login")).click();

        TouchAction ac=new TouchAction(driver);
        PointOption a=new PointOption();
        a.withCoordinates(width/2,height/4);
        PointOption a2=new PointOption();
        a2.withCoordinates(width/2,height/2);
        ac.press(a).waitAction().moveTo(a2).release().perform();
        sleep(5000);
        driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@resource-id='com.tencent.mm:id/pj']/android.widget.RelativeLayout[1]/android.widget.FrameLayout[1]/android.widget.ImageView[1]")).click();
        sleep(3000);

        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
        }

        driver.context(contextNames.toArray()[1].toString()); // set context to WEBVIEW_1
        System.out.println(contextNames.toArray()[1].toString());
        sleep(6000);
//        System.out.println("context==="+driver.getContext());

        System.out.println(driver.getPageSource());

        driver.findElement(By.xpath("/html/body/wx-scroll-view/div/div/div/wx-view[2]/wx-view[3]/wx-view[1]/wx-view[3]/wx-view[1]/wx-view[3]")).click();
        sleep(3000);



    }

    @AfterClass
    public void end(){
//        driver.closeApp();
        driver.quit();
    }
}
