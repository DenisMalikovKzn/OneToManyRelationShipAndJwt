package com.praveen.cms.api.service;

import com.praveen.cms.api.bo.AddCustomerBo;
import com.praveen.cms.api.entity.Customer;

import com.praveen.cms.api.response.CustomerAddResponse;
import com.praveen.cms.api.response.CustomerDeleteResponse;
import com.praveen.cms.api.response.CustomerDetailResponse;
import com.praveen.cms.api.response.CustomerListResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    public CustomerListResponse getCustomers(int page, int limit);

    public CustomerDetailResponse getCustomerById(Long customerId);

    public Customer getCustomerByEmail(String email);

    public CustomerAddResponse addCustomer(AddCustomerBo customerBo);

    public Customer updateCustomer(Customer customer);

    public CustomerDeleteResponse deleteCustomer(Long customerId);

    // public List<Customer> searchCustomer(String name);
}
