Feature: Search and Filter Products

  Background:
    Given the user is logged in and on the Amazon product catalog page
    When the user enters the product name "gaming laptop" in the search bar
    And the user clicks the search button or presses Enter


  Scenario: Verify the search bar allows the user to enter a product name
    When the user locates the search bar
    And the user enters a valid product name "gaming laptop"
    Then the search bar should display the entered product name "gaming laptop"
    And the search results related to "gaming laptop" should be displayed.

  @test1a
  Scenario: Verify that relevant products matching the exact search term "gaming laptop" are displayed
    Then products with the exact name "gaming laptop" should be displayed
    And each product should display the correct product name, image, and price.


  @test1
  Scenario: Verify that search results are paginated when there are many matching products
    Given more than 20 products with the name "gaming laptop" exist in the catalog
    Then the search results should be paginated
    Then the first page should display a limited number of products 16 or 64
    And navigation options for other pages should be available.

  @test2
  Scenario: Verify the product details (name, image, price) for each search result
    Then each product result should display the correct product name corresponding to the search term
    And each product result should display the correct product image
    And each product result should display the correct price
    And any discrepancies between displayed details and catalog data should be logged.

  @test2a
  Scenario: Filters are available after a product search
    Then filters such as price range, brand, and other attributes should be visible
    And the filters should be available for use.

  @test2b
  Scenario: Apply a price range filter and verify that only products within the selected range are displayed
    When the user selects a price range filter $500 to $900
    Then all displayed products should have prices within the selected price range $500 to $900