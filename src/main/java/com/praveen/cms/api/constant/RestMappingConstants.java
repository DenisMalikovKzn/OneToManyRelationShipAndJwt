package com.praveen.cms.api.constant;

public interface RestMappingConstants {
    String APP_BASE="/cms";

    public interface CustomerRequestUri{
        String Customer_BASE_URL = APP_BASE + "/customer";
        String GET_CUSTOMER_LIST = "/getCustomers";
        String GET_CUSTOMER_BY_ID = "/getCustomer";
        String SIGNUP_CUSTOMER = "/signup";
        String UPDATE_CUSTOMER = "/updateCustomer";
        String DELETE_CUSTOMER = "/deleteCustomer";
        String PURCHASE_PRODUCTS = "/placeOrder";
        String VIEW_ALL_PURCHASED_PRODUCTS ="/viewPurchasedProducts" ;
        String SIGNIN_CUSTOMER ="/signin" ;
        String SEARCH_CUSTOMER_OR_PRODUCT ="/search" ;
    }
    public interface MessageRequestUri {
        String MESSAGE_BASE_URL = APP_BASE + "/message";
        String GET_MESSAGE_LIST = "/getMessages";
        String ADD_MESSAGE = "/addMessage";
    }
}
