package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import factory.Base;

public class HotelsPageObejcts extends BaseClass {

	public HotelsPageObejcts(WebDriver driver) {
		super(driver);
	}
	JavascriptExecutor js=(JavascriptExecutor)Base.getDriver();
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 //hotel
	@FindBy(xpath="//*[text()=\"Hotels\"]")	WebElement hotelTab;
	
	//logo
	@FindBy(xpath="//*[contains(@data-element-test,\"popover\")]")	WebElement popup;
	@FindBy(xpath="//*[@alt=\"Primary Logo\"]") WebElement agodaLogo;
	
	//destination
	@FindBy(id="textInput") WebElement destination;
	@FindBy(xpath="//*[@data-selenium=\"autocompletePanel\"]//child::li[@data-selenium=\"suggestion-category-name\"]")  List<WebElement> autosuggestedMenu;
	
	//calender
	@FindBy(xpath="//*[@data-selenium=\"checkInText\"]")	WebElement checkIn;
	@FindBy(xpath="//*[contains(@class,\"DayPicker-Months\")]")  WebElement calenderWidget;
	@FindBy(xpath="//*[contains(@class,\"DayPicker-Caption\")]") List<WebElement> months;
	
	//nextmonthArrow
	@FindBy(xpath="//*[@aria-label=\"Next Month\"]")	WebElement nextArrow;
	
	//adultcount
	@FindBy(xpath="//*[@data-selenium=\"occupancyBox\"]") WebElement roomConfig;
	@FindBy(xpath="//*[@data-element-name=\"occupancy-selector-panel-adult\" and @data-selenium=\"minus\"]") WebElement adultsCount;
	@FindBy(xpath="//*[@data-selenium=\"searchButton\"]")	WebElement searchButton;
	
	//hotels page
	@FindBy(xpath="//*[@data-selenium=\"hotel-item\"]")	List<WebElement> hotelContent;
	
	//sort
	@FindBy(xpath="//*[@data-element-name=\"search-sort-price\"]")	WebElement lowestPriceFilter;
	@FindBy(xpath="//*[contains(@data-element-name,\"guest-rating\")]")	WebElement sortDropdown;
	
	//hoteldetails
	@FindBy(xpath="//*[@data-selenium=\"hotel-name\"]")	List<WebElement> hotelNames;
	@FindBy(xpath="//*[@data-selenium=\"display-price\"]")	List<WebElement> hotelPrices;
	
	public void clickHotelTab()
	{
		hotelTab.click();
	}
	public boolean isLogoPresent()
	{
		boolean isLogoVisible=agodaLogo.isDisplayed();
		return isLogoVisible;
	}
	public void closePopup()
	{
		boolean res=popup.isDisplayed();
		if(res) {
			popup.click();
		}
	}
	public void selectDestination(String loc)
	{
		destination.click();
		destination.sendKeys(loc);
	}
	
	public void getAndSelectLocation(String loc) {
		System.out.println("Entering autosuggested");
		//wait.until(ExpectedConditions.visibilityOfAllElements(autosuggestedMenu));

	    for(WebElement city:autosuggestedMenu)
	    {
	    	String cityname=city.getText();
	    	System.out.println(cityname);
	    	if(cityname.contains(loc))
	    	{
	    		city.click();
	    		break;
	    	}
	    }
	    System.out.println("Leaving auto suggetsed");
	}
	
	public String verifyLocationFromDestination() {
		String destinationValue=destination.getText();
		return destinationValue;
	}
	public void clickCheckIn()
	{
		wait.until(ExpectedConditions.elementToBeClickable((checkIn)));
		js.executeScript("arguments[0].click();", checkIn);
	}
	public boolean iscalenderVisible() {
		wait.until(ExpectedConditions.visibilityOf(calenderWidget));
		boolean isVisible=calenderWidget.isDisplayed();
		return isVisible;
	}
	
	public void selectMonthForBooking(String expMonth, String expYear) throws InterruptedException {
	    int maxTries = 12; // prevent infinite loop (1 year ahead max)
	    int tries = 0;
	    int expectedYear=Integer.parseInt(expYear);
	    
	    while (tries < maxTries) {
	    	
	        for (WebElement ele : months) {
	            String actualText = ele.getText();
	            String[] parts = actualText.split(" ");
	            String actualMonth = parts[0];
	            String actualYear = parts[1];

	            int actualYearInt = Integer.parseInt(actualYear);
	            
	            // 1. If actual year is less than expected, click next
	            if (actualYearInt < expectedYear) {
	                continue; // continue the for loop, check next visible month
	            }

	            // 2. If year matches, check month
	            if (actualYearInt == expectedYear) {
	                if (actualMonth.equalsIgnoreCase(expMonth)) {
	                    return; // Expected year & month found
	                }
	            }
	        }
	        // 3. If not found in visible months, click next
	        Thread.sleep(3000);
	        nextArrow.click();
	        tries++;
	    }

	    throw new RuntimeException("Expected month/year not found within limit");
	}
	
	public void selectDate(String month, String day) {
	    String xpath = String.format("//div[contains(text(),'%s')]//following::span[text()='%s']", month, day);
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	    WebElement dateElement = driver.findElement(By.xpath(xpath));
	    dateElement.click();
	}

	public void clickSearchButton()
	{
		searchButton.click();
	}
	public void clickRooms()
	{
		roomConfig.click();
	}
	public void clickAdultsCount()
	{
		adultsCount.click();
	}
	public void checkForHotelContent()
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(hotelContent));
	}
	public void clickLowestPriceFilter()
	{
		wait.until(ExpectedConditions.visibilityOf(lowestPriceFilter));
		js.executeScript("arguments[0].click();", lowestPriceFilter);
	}
	 public void getWindowHandles()
	 {
		 Set<String> wh=driver.getWindowHandles();
		 ArrayList<String> al=new ArrayList<>(wh);
		 System.out.println(al.size());
		 driver.switchTo().window(al.get(1));
	 }
	 public void selectSortFilter() throws InterruptedException
	 {
		 sortDropdown.click();
		 int attempts = 0;
		 while (attempts < 3) {
		     try {
		         List<WebElement> options = driver.findElements(By.xpath("//*[contains(@aria-labelledby,\"guest-rating-0\")]//following-sibling::div"));
		         for (WebElement option : options) {
		             if (option.getText().equalsIgnoreCase("Solo travelers")) {
		                 option.click();
		                 return; // exit if clicked
		             }
		         }
		         break; // exit if done
		     } catch (Exception e) {
		         attempts++;
		         Thread.sleep(500); // small wait before retry
		     }
		 }

	 }
	 
	 public Set<String> getUniqueHotelNamePricePairs() {
		    Set<String> hotelDetails = new HashSet<>();
		 
		    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    
		    int count = Math.min(hotelNames.size(), hotelPrices.size()); // to avoid IndexOutOfBounds
		    for (int i = 0; i < count; i++) {
		        try {
		            String name = hotelNames.get(i).getText().trim();
		            String price = hotelPrices.get(i).getText().trim();
		            String combined = name + " with price " + price;
		            hotelDetails.add(combined); // Set handles duplicates automatically
		        } catch (StaleElementReferenceException e) {
		            System.out.println("Stale element at index " + i);
		        }
		    }
		    return hotelDetails;
		}
	 
}
