
@hotels
Feature: Finding Cheapest hotels with best reviews
  I want to find the best deal hotels, for given destination and dates
Background:
Given I want to land in Agoda.com 

Scenario: Best hotel deals
    When I select location as "Bangkok"
    And click on date, select month as "August" and year "2025"
    And check for cheapest flight dates from makemytrip
    And apply required filters
    Then I want the list of hotels with cheapest price with good reviews to be displayed
    

