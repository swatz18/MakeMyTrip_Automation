package stepDefinitions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import factory.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.FlightsPageObjects;
import utilities.TextContext;

public class FlightStepDefinition {
		FlightsPageObjects flight;
		WebDriverWait wait = new WebDriverWait(Base.getDriver(), Duration.ofSeconds(15));
		List<WebElement> prices;
		String expMonth;
		int expYear;
		@Given("I want to land in MakeMyTrip page")
		public void i_want_to_land_in_make_my_trip_page() {
			flight=new FlightsPageObjects(Base.getDriver());
			flight.closePopup();
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//img[@alt=\"Make My Trip\"]"))));
			flight.checkLogo();
		}

		@When("I select from location as {string}")
		public void i_select_from_location_as(String fromLocation) throws InterruptedException {
		    flight.enterFromLocation(fromLocation);
		    //Thread.sleep(3000);
		    flight.clickSuggestedLocation(fromLocation);
		}

		@When("select to location as {string}")
		public void select_to_location_as(String toLocation) {
			flight.enterToLocation(toLocation);
		    flight.clickSuggestedLocation(toLocation);
		}

		@When("click on date, select month as {string} and year {int}")
		public void click_on_date_select_month_as_and_year(String month, Integer year) throws InterruptedException {
			 flight.clickDeparture();
			 Thread.sleep(1000);
			 this.expMonth=month; // to use expMonth globally
			 this.expYear=year;
			 flight.selectMonth(month,year);
			 prices=flight.getPriceOfSelectedMonth(month);
			}
		
		   

		@Then("I want the date with cheapest price to be displayed")
		public void i_want_the_date_with_cheapest_price_to_be_displayed() {
			List<Integer> minPriceDates=new ArrayList<>();
			
		    List<Integer> sortedPrice=flight.sortPrice(prices);
		    int minPrice=sortedPrice.get(0);
		    String minPri=String.valueOf(minPrice);
		    minPri = minPri.charAt(0) + "," + minPri.substring(1);
		    System.out.println("The lowest price is: " +minPri);
	        //get Date ,Month and Year
		    List<WebElement> dates=flight.getDateOfMinPrice(expMonth,minPri);
    		for(WebElement date:dates)
    		{
    			int actualDate=Integer.parseInt(date.getText());
    			minPriceDates.add(actualDate);
    		}	
    		// to reuse the dates in hotel booking
    		String mindate=String.valueOf(minPriceDates.get(0));
    		TextContext.setTravelDate(mindate);
    		System.out.println("The Dates available for low price for the month of "+expMonth+ +expYear+ " are:"+minPriceDates);
		}
}
