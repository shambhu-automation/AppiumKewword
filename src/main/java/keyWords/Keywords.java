package keyWords;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import testBase.LoggerHelper;
import testBase.TestBase;

public class Keywords extends TestBase{
	public static String priceOnProductPage,priceOnCheckoutPage;
	
	private final static Logger logger = LoggerHelper.getLogger(Keywords.class);
	public static String login() throws Exception {
		logger.info("Application Started..");
		waitForElement(getElement("login.existingCustomer"),driver);
		getElement("login.existingCustomer").click();
		waitFor();
		waitForElement(getElement("login.email"),driver);
		getElement("login.email").sendKeys(Repository.getProperty("email"));
		getElement("login.continue").click();
		waitForElement(getElement("login.password"),driver);
		getElement("login.password").sendKeys(Repository.getProperty("password"));
		getElement("login.signin").click();
		return "Pass";
	}
	public static String searchForItemOnHomePage() throws Exception {
		getElement("homepage.logo").isDisplayed();
		getElement("homepage.searchbar").click();
		waitForElement(getElement("homepage.searchbar"),driver);
		getElement("homepage.searchbar").sendKeys("65 inch TV");
		List<WebElement> lstElements = getElements("homepage.searchbarList");
		lstElements.get(1).click();
		waitFor();

		return "Pass";
	}
	public static String selectItem() throws Exception {
		scrollUp(1);
		List<WebElement> lstElementsprod=getElements("itemlistpage.itemsList");
		lstElementsprod.get(2).click();
		waitFor();
		return "Pass";
	}
	public static String addItemToCart() throws Exception {
		priceOnProductPage= getElement("itempage.productprice").getText();
		logger.info("Price On Product Details Page is :"+priceOnProductPage);
		By addtocart=  By.xpath("//*[@resource-id='add-to-cart-button']");
		scrollToElement(driver,addtocart);
		getElement("itempage.addtocartbtn").click();
		getElement("itempage.carticon").click();
		waitFor();

		return "Pass";
	}
	public static String ValidateItemOnCheckoutPage() throws Exception {
//	 priceOnCheckoutPage= getElement("checkoutpage.productprice").getText();
//	 logger.info("Price On Checkout Page is :"+priceOnCheckoutPage);

		return "Pass";
	}
	public static boolean waitForElement(WebElement element, AppiumDriver driver){
		boolean elementPresence = false;
		try {

			WebDriverWait wait= new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
					elementPresence = true;
					return elementPresence;
				}
				
			} catch (Exception e) {
				System.out.println("element NOT found");	
				elementPresence = false;
				return elementPresence;				
			}	
			System.out.println("Element Presence is:" +elementPresence);
			return elementPresence;
		}
	
	
	public static void scrollToElement(AppiumDriver driver,By by)
	{
		int i=0;
		boolean found=false;
		while(!found)
		{
			if(i>5) {
				break;
			}
			try
			{
				driver.findElement(by);
				found=true;
			}
			catch(Exception e)
			{
				Dimension size = driver.manage().window().getSize();
				int startx=size.width/2;
				int starty=(int)(size.height*0.9);
				int endx=size.width/2;
				int endy=(int)(size.height*0.2);
				TouchAction action = new TouchAction(driver); 
//				PointOption tapOption = new PointOption(); 
				

				action.press(PointOption.point(startx,starty))
				.waitAction(new WaitOptions().withDuration(Duration.ofMillis(600)))
				.moveTo(PointOption.point(endx,endy))
				.release()
				.perform();
				
			}
		}

	}
	public static String scrollUp(int n) {
		for(int i=0;i<n;i++) {
			logger.info("Scrolling: "+i+1);
			Dimension size = driver.manage().window().getSize();
			int startx=size.width/2;
			int starty=(int)(size.height*0.8);
			int endx=size.width/2;
			int endy=(int)(size.height*0.2);
			TouchAction action = new TouchAction(driver);
			action.press(PointOption.point(startx,starty))
			.waitAction(new WaitOptions().withDuration(Duration.ofMillis(600)))
			.moveTo(PointOption.point(endx,endy))
			.release()
			.perform();
		}
		return "Pass";
	}	
	
	public static WebElement getElement(String locator) throws Exception{
		//logger.info("locator data:-"+locator+"is---"+Repository.getProperty(locator));
		String keywordValue = Repository.getProperty(locator);
		return getLocator(keywordValue);
	}
	
	public static List<WebElement> getElements(String locator) throws Exception{
		return getLocators(Repository.getProperty(locator));
	}
	
	public static WebElement getLocator(String locator) throws Exception {
		String[] split = locator.split(":::");
		String locatorType = split[0];
		String locatorValue = split[1];
		if (locatorType.toLowerCase().equals("id"))
			return driver.findElement(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElement(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return driver.findElement(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return driver.findElement(By.tagName(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return driver.findElement(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElement(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return driver.findElement(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}
	
	public static List<WebElement> getLocators(String locator) throws Exception {
		String[] split = locator.split(":::");
		String locatorType = split[0];
		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id"))
			return driver.findElements(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElements(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return driver.findElements(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElements(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return driver.findElements(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElements(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}


	public static void expliciteWait() throws Exception {
		try {
			logger.info("Waiting for webElement..."+webElement.toString());
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(Repository.getProperty("explicitWait")));
			wait.until(ExpectedConditions.visibilityOf(getElement(webElement)));
			logger.info("Element found..."+webElement.toString());
		} catch (Throwable e) {
			throw new TimeoutException(webElement, e);
			
		}
		
	}
	
	public static String clickWhenReady(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
		return "Pass";

	}


	
	public static String waitFor() throws InterruptedException {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			return "Failed - unable to load the page";
		}
		return "Pass";
	}
	
	/**
     * This function is used for vertical scroll
     * @param scrollView - class name for scrollView
     * @param className - class name for text view
     * @param text - text of the element
     */
    public static void verticalSwipe(String scrollView, String className, String text){

        try {
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)" +
                            ".className(\"" + scrollView + "\")).scrollIntoView(new UiSelector()" +
                            ".className(\"" + className + "\").text(\"" + text + "\"))"));

            logger.info("Vertically scrolling into the view.");
        }catch(Exception Ex){
            logger.error("Exception occurred while vertically scrolling into the view: " + Ex.getMessage());
        }

    }

    /**
     * This function is used for horizontal swipe
     * @param scrollView - class name for scrollView
     * @param className - class name for text view
     * @param text - text of the element
     */
    public static void horizontalSwipe(String scrollView, String className, String text){
        try{
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)" +
                            ".className(\"" + scrollView + "\")).setAsHorizontalList().scrollIntoView(new UiSelector()" +
                            ".className(\"" + className + "\").text(\"" + text + "\"))"));

            logger.info("Horizontally scrolling into the view.");

        }catch(Exception Ex){
            logger.error("Exception occurred while horizontally scrolling into the view: " + Ex.getMessage());
        }

    }
	
}
