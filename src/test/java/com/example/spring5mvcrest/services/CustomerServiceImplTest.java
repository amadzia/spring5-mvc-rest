package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }

    @Test
    public void getAllCustomersTest() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Mike");
        customer1.setLastName("West");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        List<CustomerDTO> customers = customerService.getAllCustomers();

        //then
        assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerByIdTest() {

        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Mike");
        customer1.setLastName("West");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));
        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        assertEquals("Mike", customerDTO.getFirstName());
    }

    @Test
    public void createNewCustomerTest() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }
}