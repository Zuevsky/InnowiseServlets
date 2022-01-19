package services;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ParamsProvider {
    private static final String AGREEMENT_PARAM = "termsAgreement";
    private static final String USERNAME_PARAM = "username";
    private static final String PRODUCTS_PARAM = "products";
    private static final String PRODUCT_NAME_PARAM = "productName";

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
}
