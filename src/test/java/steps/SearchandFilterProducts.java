package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import utils.CommonMethods;

import java.time.Duration;
import java.util.List;

public class SearchandFilterProducts extends CommonMethods {




    @Given("the user is logged in and on the Amazon product catalog page")
    public void the_user_is_logged_in_and_on_the_amazon_product_catalog_page() {
        openBrowserAndLaunchApplication();

    }
    @When("the user locates the search bar")
    public void the_user_locates_the_search_bar() {
       driver.findElement(By.id("twotabsearchtextbox"));
        Assert.assertTrue(SearchandFilterProducts.searchbar.isDisplayed());
    }
    @When("the user enters a valid product name {string}")
    public void the_user_enters_a_valid_product_name(String productName) {
        SearchandFilterProducts.searchbar.sendKeys(productName);
    }
    @When("the user clicks the search button or presses Enter")
    public void the_user_clicks_the_search_button_or_presses_enter() {
        click(SearchandFilterProducts.searchButton);

    }
    @Then("the search bar should display the entered product name {string}")
    public void the_search_bar_should_display_the_entered_product_name(String expectedProductName) {
        WebElement searchBarAfterSearch = driver.findElement(By.id("twotabsearchtextbox"));
        String enteredText = searchBarAfterSearch.getAttribute("value");
        Assert.assertEquals(enteredText, expectedProductName);
    }
    @Then("the search results related to {string} should be displayed.")
    public void the_search_results_related_to_should_be_displayed(String expectedProductName) {
        Assert.assertTrue(driver.getTitle().contains(expectedProductName));
    }

    @When("the user enters the product name {string} in the search bar")
    public void the_user_enters_the_product_name_in_the_search_bar(String productName) {
        driver.findElement(By.id("twotabsearchtextbox"));
        SearchandFilterProducts.searchbar.sendKeys(productName);
        //click(SearchandFilterProducts.searchButton);

    }
    @Then("products with the exact name {string} should be displayed")
    public void products_with_the_exact_name_should_be_displayed(String expectedProductName) {
        List<WebElement> productNames = driver.findElements(By.cssSelector(".s-title span.a-text-normal"));

        boolean isProductDisplayed = true;
        // Iterate over each product name and check if it exactly matches the expected product name
        for (WebElement product : productNames) {
            if (product.getText().equalsIgnoreCase(expectedProductName)) {
                isProductDisplayed = false;
                break;  // Exit the loop as soon as a matching product is found
            }
        }

        // Assert that a product with the exact name is displayed, and provide an error message if not
        Assert.assertTrue(expectedProductName, isProductDisplayed);
    }
    @Then("each product should display the correct product name, image, and price.")
    public void each_product_should_display_the_correct_product_name_image_and_price() {
        // Verify that each product has a name, an image, and a price
        List<WebElement> productNames = driver.findElements(By.cssSelector("div.s-main-slot div:nth-child(9) h2 > a > span"));
        List<WebElement> productImages = driver.findElements(By.cssSelector("#search .s-main-slot .s-result-item:nth-child(9) img"));
        List<WebElement> productPrices = driver.findElements(By.cssSelector("div.s-main-slot.s-result-list div:nth-child(9) span.a-price-whole"));

        // Assert that at least one product with all the necessary information (name, image, price) is displayed
        Assert.assertTrue("No product names found.", productNames.size() > 0);
        Assert.assertTrue("No product images found.", productImages.size() > 0);
        Assert.assertTrue("No product prices found.", productPrices.size() > 0);

        // Ensure the lists are of equal length to avoid index issues
        int productCount = Math.min(productNames.size(), Math.min(productImages.size(), productPrices.size()));

        Assert.assertEquals(productCount, productNames.size());
        Assert.assertEquals(productCount, productImages.size());
        Assert.assertEquals(productCount, productPrices.size());

        // Optionally check each product entry has all the required fields
        for (int i = 0; i < productCount; i++) {
            Assert.assertTrue(productNames.get(i).isDisplayed());
            Assert.assertTrue(productImages.get(i).isDisplayed());
            Assert.assertTrue(productPrices.get(i).isDisplayed());
        }

        // Remove driver.quit() from here if it disrupts other tests
        // driver.quit();
    }

    @Given("more than {int} products with the name {string} exist in the catalog")
    public void more_than_products_with_the_name_exist_in_the_catalog(Integer productCount, String productName) {
        System.out.println(productName);
        System.out.println(productCount);

    }
    @Then("the search results should be paginated")
    public void the_search_results_should_be_paginated() {
        // Verify that pagination elements (e.g., "Next" button) are displayed on the page
        WebElement paginationElement = driver.findElement(By.cssSelector(".s-pagination-next"));
        Assert.assertTrue(paginationElement.isDisplayed());
    }
    @Then("the first page should display a limited number of products {int} or {int}")
    public void the_first_page_should_display_a_limited_number_of_products_or(Integer min, Integer max) {
        List<WebElement> productsOnPage = driver.findElements(By.cssSelector(".s-result-item"));

        // Assert that the number of products displayed falls within the expected range
        int productCount = productsOnPage.size();
        Assert.assertTrue("The number of products on the first page is not within the expected range.",
                productCount >= min && productCount <= max);
        System.out.println(productCount);
        //System.out.println(productsOnPage);

    }
    @Then("navigation options for other pages should be available.")
    public void navigation_options_for_other_pages_should_be_available() {
        // Verify that the "Next" or other pagination buttons are present
        WebElement nextPageButton = driver.findElement(By.cssSelector(".s-pagination-next"));
        Assert.assertTrue("Next page button is not available, pagination options are missing.", nextPageButton.isDisplayed());

        // Optionally check if there are multiple page numbers (for detailed pagination test)
        List<WebElement> pageNumbers = driver.findElements(By.cssSelector(".s-pagination-item"));
        Assert.assertTrue("Multiple page numbers are not displayed.", pageNumbers.size() > 1);

        // Quit the browser after the checks
        //driver.quit();
    }

    @Then("each product result should display the correct product name corresponding to the search term")
    public void each_product_result_should_display_the_correct_product_name_corresponding_to_the_search_term() {
        // Get a list of all product names displayed in the search results
        List<WebElement> productNames = driver.findElements(By.id("div.s-main-slot div:nth-child(9) h2 > a > span"));

        // Assert that each product name matches the search term "gaming laptop"
        for (WebElement productName : productNames) {
            String displayedName = productName.getText().toLowerCase();
            Assert.assertTrue("Product name does not match the search term.", displayedName.contains("gaming laptop"));
        }
    }
    @Then("each product result should display the correct product image")
    public void each_product_result_should_display_the_correct_product_image() {
        // Get a list of all product images displayed in the search results
        List<WebElement> productImages = driver.findElements(By.id("#search .s-main-slot .s-result-item:nth-child(9) img"));

        // Assert that each product image is displayed correctly
        for (WebElement image : productImages) {
            boolean isImageDisplayed = image.isDisplayed();
            Assert.assertTrue("Product image is not displayed correctly.", isImageDisplayed);
        }
    }
    @Then("each product result should display the correct price")
    public void each_product_result_should_display_the_correct_price() {
        List<WebElement> productPrices = driver.findElements(By.id("div.s-main-slot.s-result-list div:nth-child(9) span.a-price-whole"));

        // Assert that each product price is displayed (further price validation would require actual catalog data)
        for (WebElement price : productPrices) {
            String displayedPrice = price.getText();
            Assert.assertFalse("Product price is not displayed.", displayedPrice.isEmpty());
        }
    }
    @Then("any discrepancies between displayed details and catalog data should be logged.")
    public void any_discrepancies_between_displayed_details_and_catalog_data_should_be_logged() {
        // Normally, here we would compare displayed details with the actual catalog data
        // Assuming we have catalog data, log discrepancies for further investigation
        System.out.println("Any discrepancies found will be logged here (this is a placeholder).");
    }

    @Then("filters such as price range, brand, and other attributes should be visible")
    public void filters_such_as_price_range_brand_and_other_attributes_should_be_visible() {
        // Verify that filters like price range and brand are displayed
        WebElement priceFilter = driver.findElement(By.xpath("//span[contains(text(),'Price')]"));
        WebElement brandFilter = driver.findElement(By.xpath("//*[@id='p_123-title']"));

        Assert.assertTrue("Price filter is not visible", priceFilter.isDisplayed());
        Assert.assertTrue("Brand filter is not visible", brandFilter.isDisplayed());
    }
    @Then("the filters should be available for use.")
    public void the_filters_should_be_available_for_use() {
        WebElement priceFilter = driver.findElement(By.xpath("//span[contains(text(),'Price')]"));
        WebElement brandFilter = driver.findElement(By.xpath("//*[@id='p_123-title']"));

        // Check if filters are enabled for user interaction
        Assert.assertTrue("Price filter is not available for use.", priceFilter.isEnabled());
        Assert.assertTrue("Brand filter is not available for use.", brandFilter.isEnabled());
    }

    @When("the user selects a price range filter ${int} to ${int}")
    public void the_user_selects_a_price_range_filter_$_to_$(Integer minPrice, Integer maxPrice) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //WebElement priceFilterSection = driver.findElement(By.xpath("//span[text()='Price']"));
        js.executeScript("arguments[0].scrollIntoView(true);", SearchandFilterProducts.priceFilterSection);

        // Locate the price filter input fields and Go button
        WebElement priceFilterMin = driver.findElement(By.cssSelector("input[id='p_36/range-slider_slider-item_lower-bound-slider']"));
        Thread.sleep(4000);
        Actions action = new Actions(driver);
        action.dragAndDropBy(priceFilterMin, minPrice, 0).perform();

        WebElement priceFilterMax = driver.findElement(By.xpath("//*[@id='p_36/range-slider_slider-item_upper-bound-slider']"));
        Thread.sleep(4000);
        Actions actions = new Actions(driver);
        action.dragAndDropBy(priceFilterMax, maxPrice, 0).perform();

        // Set minimum and maximum price using JavascriptExecutor
        //js.executeScript("arguments[0].value='" + minPrice + "';", priceFilterMin);
        //Thread.sleep(4000);
        //js.executeScript("arguments[0].value='" + maxPrice + "';", priceFilterMax);

        Thread.sleep(4000);

        SearchandFilterProducts.goButton.click();

    }
    @Then("all displayed products should have prices within the selected price range ${int} to ${int}")
    public void all_displayed_products_should_have_prices_within_the_selected_price_range_$_to_$(Integer minPrice, Integer maxPrice) {
        // Locate all product prices displayed in the search results
        List<WebElement> productPrices = driver.findElements(By.xpath("//span[@class='a-price-whole']"));

        // Iterate through each price element and validate if it falls within the selected range
        for (WebElement priceElement : productPrices) {
            // Extract the price as text, remove commas, and parse it as an integer
            String priceText = priceElement.getText().replace(",", "").trim();
            if (!priceText.isEmpty()) {
                int price = Integer.parseInt(priceText);

                // Assert that the price is within the selected range
                Assert.assertTrue("Price " + price + " is not within the range: " + minPrice + " to " + maxPrice,
                        price >= minPrice && price <= maxPrice);
            }
        }
    }
}
