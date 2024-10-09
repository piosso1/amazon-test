package utils;

import pages.SearchandFilterProducts;

public class PageInitalizer {

    public static SearchandFilterProducts SearchandFilterProducts;



    public static void initializePageObjects(){
        SearchandFilterProducts = new SearchandFilterProducts();
    }
}
