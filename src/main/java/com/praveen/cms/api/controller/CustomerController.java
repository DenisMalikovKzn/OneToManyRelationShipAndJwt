package com.praveen.cms.api.controller;

import com.praveen.cms.api.bo.AddCustomerBo;
import com.praveen.cms.api.constant.AppConstants;
import com.praveen.cms.api.constant.RestMappingConstants;
import com.praveen.cms.api.constant.SecurityConstants;
import com.praveen.cms.api.convertor.CustomerConvertor;
import com.praveen.cms.api.entity.Customer;
import com.praveen.cms.api.request.CustomerAddRequest;
import com.praveen.cms.api.request.CustomerSignInRequest;
import com.praveen.cms.api.response.BaseApiResponse;
import com.praveen.cms.api.response.CustomerAddResponse;
import com.praveen.cms.api.response.CustomerDeleteResponse;
import com.praveen.cms.api.response.CustomerDetailResponse;
import com.praveen.cms.api.response.CustomerListResponse;
import com.praveen.cms.api.response.ResponseBuilder;
import com.praveen.cms.api.security.JwtTokenProvider;
import com.praveen.cms.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(RestMappingConstants.CustomerRequestUri.Customer_BASE_URL)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping(path = RestMappingConstants.CustomerRequestUri.GET_CUSTOMER_LIST)
    public ResponseEntity<BaseApiResponse> getCustomers(
            @RequestHeader(value = AppConstants.Common.TOKEN_HEADER, required = true) String token,
            @RequestParam(value = AppConstants.Common.PAGE_NUMBER,defaultValue = "0") int page,
            @RequestParam(value = AppConstants.Common.PAGE_LIMIT,defaultValue = AppConstants.Common.PAGE_LIMIT_VALUE) int limit,
            HttpServletRequest request
    )
    {
        CustomerListResponse customerListResponses = customerService.getCustomers(page,limit);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(customerListResponses);
        return new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
    }

    @GetMapping(path= RestMappingConstants.CustomerRequestUri.GET_CUSTOMER_BY_ID)
    public ResponseEntity<BaseApiResponse> getCustomerById(
            @RequestHeader(value = AppConstants.Common.TOKEN_HEADER, required = true) String token,
            @RequestParam("customerId") Long customerId,HttpServletRequest request)
    {
        CustomerDetailResponse customerDetailResponse = customerService.getCustomerById(customerId);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(customerDetailResponse);
        return new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
    }

    @PostMapping(path = RestMappingConstants.CustomerRequestUri.SIGNUP_CUSTOMER)
    public ResponseEntity<BaseApiResponse> signUpCustomer(
            @Valid @RequestBody(required = true) CustomerAddRequest customerAddRequest,HttpServletRequest request)
    {
        AddCustomerBo addCustomerBo = CustomerConvertor.convertAddCustomerRequestToAddCustomerBo(customerAddRequest);
        CustomerAddResponse customerAddResponse = customerService.addCustomer(addCustomerBo);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(customerAddResponse);
        return new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
    }

    @PostMapping(path = RestMappingConstants.CustomerRequestUri.SIGNIN_CUSTOMER)
    public @ResponseBody
    ResponseEntity<BaseApiResponse> signInCustomer(
            @RequestBody CustomerSignInRequest customerSignInRequest,
            HttpServletResponse signinResponse, HttpServletRequest request){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customerSignInRequest.getEmail(),customerSignInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(true);
        signinResponse.setHeader("Access-Control-Allow-Headers",
                "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
                        + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        signinResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        signinResponse.setHeader(AppConstants.Common.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwt);

        return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

    }

    @PutMapping(path=RestMappingConstants.CustomerRequestUri.UPDATE_CUSTOMER)
    public ResponseEntity<Customer> updateCustomer(
            @RequestHeader(value = AppConstants.Common.TOKEN_HEADER, required = true) String token,
            @RequestBody Customer customer,HttpServletRequest request)
    {
         Customer theCustomer = customerService.updateCustomer(customer);
         return new ResponseEntity<>(theCustomer, HttpStatus.OK);
    }

    @DeleteMapping(path = RestMappingConstants.CustomerRequestUri.DELETE_CUSTOMER)
    public ResponseEntity<BaseApiResponse> deleteCustomer(
            @RequestHeader(value = AppConstants.Common.TOKEN_HEADER, required = true) String token,
            @RequestParam("customerId") Long customerId,HttpServletRequest request)
    {
        CustomerDeleteResponse customerDeleteResponse = customerService.deleteCustomer(customerId);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(customerDeleteResponse);
        return new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
    }
}
