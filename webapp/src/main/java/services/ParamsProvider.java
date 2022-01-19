package services;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ParamsProvider {
    private static final String AGREEMENT_PARAM = "termsAgreement";
    private static final String USERNAME_PARAM = "username";
    private static final String PRODUCTS_PARAM = "products";
    private static final String PRODUCT_NAME_PARAM = "productName";
    private static final String CHOSEN_PRODUCTS = "chosenProducts";
    private static final String TOTAL_PRICE = "totalPrice";
    private static final String SHOPPING_CART = "shoppingCart";
    private static final String SELECT_FOR_PRODUCT = "selectForProduct";
    private static final String SUBMIT_BUTTON = "submitButton";

    public static String getAgreementParam() {
        return AGREEMENT_PARAM;
    }

    public static String getUsernameParam() {
        return USERNAME_PARAM;
    }

    public static String getProductsParam() {
        return PRODUCTS_PARAM;
    }

    public static String getProductNameParam() {
        return PRODUCT_NAME_PARAM;
    }

    public static String getChosenProducts() {
        return CHOSEN_PRODUCTS;
    }

    public static String getTotalPrice() {
        return TOTAL_PRICE;
    }

    public static String getSelectForProduct() {
        return SELECT_FOR_PRODUCT;
    }

    public static String getSubmitButton() {
        return SUBMIT_BUTTON;
    }

    public static String getShoppingCart() {
        return SHOPPING_CART;
    }
}
