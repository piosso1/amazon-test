package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class SearchandFilterProducts extends CommonMethods {

    @FindBy(id="twotabsearchtextbox")
    public WebElement searchbar;

    @FindBy(id="nav-search-submit-button")
    public WebElement searchButton;

    @FindBy(xpath="//span[text()='Price']")
    public WebElement priceFilterSection;

    @FindBy(xpath="//input[@aria-label='Go - Submit price range']")
    public WebElement goButton;


    public void clickSearchButton() {click(SearchandFilterProducts.searchButton);}

    public SearchandFilterProducts(){
        PageFactory.initElements(driver,this);
    }


}
