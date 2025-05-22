package pageObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FlightsPageObjects extends BaseClass {

	public FlightsPageObjects(WebDriver driver) {
		super(driver);
	}
	List<Integer> priceList=new ArrayList<>();
	//popup
	@FindBy(className="commonModal__close") WebElement popup;
	
	//logo
	@FindBy(xpath="//img[@alt=\"Make My Trip\"]") public WebElement logo;

	//from and to selection
	@FindBy(id="fromCity") WebElement fromLocation;
	@FindBy(xpath="//input[@placeholder=\"From\"]")	WebElement fromInput;
	
	@FindBy(id="toCity") WebElement toLocation;
	@FindBy(xpath="//input[@placeholder=\"To\"]")	WebElement toInput;
	 
	//departure date
	@FindBy(xpath="//div[contains(@class,\"date\")]")	WebElement departureDate;
	@FindBy(xpath="")	WebElement	dd;
	
	//month
	@FindBy(xpath="//div[@class=\"DayPicker-Caption\"]/div")	List<WebElement> months;
	@FindBy(xpath="//span[@aria-label=\"Next Month\"]")	WebElement nextArrow;
	
	//action Methods
	public void closePopup()
	{
		boolean isDisplayed=popup.isDisplayed();
		if(isDisplayed)
		{
			popup.click();
		} 
	}
	public void checkLogo()
	{
		boolean isDisplayed=logo.isDisplayed();
		if(isDisplayed)
		{
			System.out.println("In MakeMyTrip Homepage");
		}  
	}
	public void enterFromLocation(String loc)
	{
		fromLocation.click();
		//System.out.print(loc);
	    fromInput.sendKeys(loc);
	}

	public void enterToLocation(String loc)
	{
		toLocation.click();
		//System.out.print(loc);
		toInput.sendKeys(loc);
	}	
	public void clickDeparture()
	{
	   departureDate.click();
	}
	
	public void selectMonth(String expMonth, Integer expYear) {
	    int maxTries = 12; // prevent infinite loop (1 year ahead max)
	    int tries = 0;

	    while (tries < maxTries) {
	        for (WebElement ele : months) {
	            String actualText = ele.getText();
	            String[] parts = actualText.split(" ");
	            String actualMonth = parts[0];
	            String actualYear = parts[1];

	            int actualYearInt = Integer.parseInt(actualYear);

	            // 1. If actual year is less than expected, click next
	            if (actualYearInt < expYear) {
	                continue; // continue the for loop, check next visible month
	            }

	            // 2. If year matches, check month
	            if (actualYearInt == expYear) {
	                if (actualMonth.equalsIgnoreCase(expMonth)) {
	                    return; // Expected year & month found
	                }
	            }
	        }
	        // 3. If not found in visible months, click next
	        nextArrow.click();
	        tries++;
	    }

	    throw new RuntimeException("Expected month/year not found within limit");
	}
	
	public List<Integer> sortPrice(List<WebElement> prices)
	{
		System.out.println("Available prices for the selected month are :");
		for(WebElement ele:prices)
		{
			String price=ele.getText();
			if(!price.isEmpty())
			{
			String actualPrice=price.replace(",","");
			int pri=Integer.parseInt(actualPrice);
			System.out.println(pri);
			priceList.add(pri);
			Collections.sort(priceList);
			}
		}
		return priceList;
	}

	//dynamic xpath 
	public WebElement getSuggestedLocation(String cityName) {
	    String xpath = String.format("//div[contains(@id,'react-autowhatever-1')]//span[contains(text(),'%s')]", cityName);
	    return driver.findElement(By.xpath(xpath));
	}

	public void clickSuggestedLocation(String cityName) {
		getSuggestedLocation(cityName).click();
	}
	public List<WebElement> getPriceOfSelectedMonth(String month) {
	    String xpath = String.format("//div[contains(text(),'%s')]//following::*[contains(@class,\" todayPrice\")]", month);
	    return driver.findElements(By.xpath(xpath));
	}
		
	public List<WebElement> getDateOfMinPrice(String month,String minPrice) {
	    String xpath = String.format("//div[contains(text(),'%s')]//following::*[contains(text(),'%s')]//preceding-sibling::*", month,minPrice);
	    return driver.findElements(By.xpath(xpath));
	}

}
