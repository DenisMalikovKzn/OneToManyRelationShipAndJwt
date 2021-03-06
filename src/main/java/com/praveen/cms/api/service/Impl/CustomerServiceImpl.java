package com.praveen.cms.api.service.Impl;

import com.praveen.cms.api.bo.AddCustomerBo;
import com.praveen.cms.api.constant.AppConstants;
import com.praveen.cms.api.convertor.CustomerConvertor;
import com.praveen.cms.api.dao.CustomerDao;
import com.praveen.cms.api.entity.Customer;
import com.praveen.cms.api.exception.CustomerAlreadyExistException;
import com.praveen.cms.api.exception.CustomerNotFoundException;
import com.praveen.cms.api.response.*;
import com.praveen.cms.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public CustomerListResponse getCustomers(int page,int limit)
    {
        if(page > 0)
            page = page - 1;
        Pageable pageable = PageRequest.of(page,limit);
        Page<Customer> customerPage = customerDao.getCustomers(pageable);
        List<Customer> customers = customerPage.getContent();
        List<CustomerDetailResponse> customerList = new ArrayList<>();
        for(Customer customer : customers){
            CustomerDetailResponse customerDetailResponse = new CustomerDetailResponse();
            customerDetailResponse.setId(customer.getId());
            customerDetailResponse.setFirstName(customer.getFirstName());
            customerDetailResponse.setLastName(customer.getLastName());
            customerDetailResponse.setEmail(customer.getEmail());
            customerList.add(customerDetailResponse);
        }
        CommonPaginationResponse commonPaginationResponse = new CommonPaginationResponse(customerPage.getTotalPages());
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.setCustomerDetailResponseList(customerList);
        customerListResponse.setCommonPaginationResponse(commonPaginationResponse);
        return customerListResponse;
    }

    @Override
    public CustomerDetailResponse getCustomerById(Long customerId)
    {
        Optional<Customer> customerOptional = customerDao.getCustomerById(customerId);
        customerOptional.orElseThrow(
                () -> new CustomerNotFoundException(AppConstants.ErrorTypes.NO_CUSTOMER_FOUND_ERROR, AppConstants.ErrorCodes.CUSTOMER_DOES_NOT_EXISTS_ERROR_CODE, AppConstants.ErrorMessages.CUSTOMER_NOT_EXISTS_ERROR_MESSAGE)
        );
        Customer customer = customerOptional.get();
        CustomerDetailResponse customerDetailResponse = new CustomerDetailResponse();
        customerDetailResponse.setId(customer.getId());
        customerDetailResponse.setFirstName(customer.getFirstName());
        customerDetailResponse.setLastName(customer.getLastName());
        customerDetailResponse.setEmail(customer.getEmail());
        return customerDetailResponse;
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerDao.getCustomerByEmailDao(email);
    }

    @Override
    public CustomerAddResponse addCustomer(AddCustomerBo customerBo)
    {
        Long isEmailExists = customerDao.checkIfEmailAlreadyExist(customerBo.getEmail());
        if(isEmailExists>0){
            throw new CustomerAlreadyExistException(AppConstants.ErrorTypes.EMAIL_ALREADY_REGISTERED_ERROR,
                    AppConstants.ErrorCodes.CUSTOMER_ALREADY_EXISTS_ERROR_CODE,
                    AppConstants.ErrorMessages.EMAIL_ALREADY_REGISTERED__MESSAGE);
        }
        Customer customer = CustomerConvertor.convertAddCustomerBoToAddCustomerEntity(customerBo);
        Customer theCustomer = customerDao.addCustomer(customer);
        Long customerId = theCustomer.getId();
        return new CustomerAddResponse(customerId);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    @Override
    public CustomerDeleteResponse deleteCustomer(Long customerId)
    {
        CustomerDeleteResponse customerDeleteResponse = new CustomerDeleteResponse();
        try{
            customerDao.deleteCustomer(customerId);
        }
        catch(EmptyResultDataAccessException exc){
            throw new CustomerNotFoundException(AppConstants.ErrorTypes.NO_CUSTOMER_FOUND_ERROR, AppConstants.ErrorCodes.CUSTOMER_DOES_NOT_EXISTS_ERROR_CODE, AppConstants.ErrorMessages.CUSTOMER_NOT_EXISTS_ERROR_MESSAGE);
        }
        customerDeleteResponse.setCustomerDeleted(true);

        return customerDeleteResponse;
    }
}
