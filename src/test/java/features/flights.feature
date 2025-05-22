
Feature: Find Chepest Date for given destination
  I want to find the cheapest date available to travel for the given destintation
Background:
	Given I want to land in MakeMyTrip page

  @flights
  Scenario: Check Flight Rate
    When I select from location as "Chennai"
    And select to location as "Bangkok"
    And click on date, select month as "October" and year 2025
    Then I want the date with cheapest price to be displayed
