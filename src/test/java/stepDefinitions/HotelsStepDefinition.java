package stepDefinitions;

import factory.Base;
import static org.junit.Assert.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;


import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HotelsPageObejcts;
import utilities.TextContext;

public class HotelsStepDefinition {
	HotelsPageObejcts hotels;
	JavascriptExecutor js=(JavascriptExecutor)Base.getDriver();
	WebDriverWait wait = new WebDriverWait(Base.getDriver(), Duration.ofSeconds(10));
	String date;
	String month;
	@Given("I want to land in Agoda.com")
	public void i_want_to_land_in_agoda_com() {
	    hotels=new HotelsPageObejcts(Base.getDriver());
	    hotels.closePopup();
	    boolean res=hotels.isLogoPresent();
	    assertTrue(res);
	}

	@When("I select location as {string}")
	public void i_select_location_as(String location) throws InterruptedException {
	    hotels.selectDestination(location);
	    hotels.getAndSelectLocation(location);
//	    String destinationValue=hotels.verifyLocationFromDestination();
//	    System.out.println(destinationValue);
//	    assertTrue(destinationValue.contains(location));    
	}
	
	@When("click on date, select month as {string} and year {string}")
	public void click_on_date_select_month_as_and_year(String month, String year) throws InterruptedException {

		//hotels.clickCheckIn();
		boolean visible=hotels.iscalenderVisible();
		assertTrue(visible);
		this.month=month;
		hotels.selectMonthForBooking(month, year);
		Thread.sleep(3000);
		

	}
	@When("check for cheapest flight dates from makemytrip")
	public void check_for_cheapest_flight_dates_from_makemytrip() throws InterruptedException {
		String CheckIndate = TextContext.getTravelDate();
		hotels.selectDate(month,CheckIndate);
		Thread.sleep(3000);
		int chkOutdate=Integer.parseInt(CheckIndate)+1;
		String checkOutdate=String.valueOf(chkOutdate);
		hotels.selectDate(month,checkOutdate);
		hotels.clickAdultsCount();
		hotels.clickSearchButton();
		Thread.sleep(5000);
	}
	@When("apply required filters")
	public void apply_required_filters() throws InterruptedException {
		System.out.println("Filters Section");
		//hotels.checkForHotelContent();
		hotels.getWindowHandles();
		hotels.clickLowestPriceFilter();
		hotels.checkForHotelContent();
		hotels.selectSortFilter();
	}

	@Then("I want the list of hotels with cheapest price with good reviews to be displayed")
	public void i_want_the_list_of_hotels_with_cheapest_price_with_good_reviews_to_be_displayed() {
		Set<String> hotel =hotels.getUniqueHotelNamePricePairs();
		for (String h : hotel) {
		
		    System.out.println(h);
		}

		
	}
}
