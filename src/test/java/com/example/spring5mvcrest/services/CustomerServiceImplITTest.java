package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.bootstrap.Bootstrap;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CategoryRepository;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplITTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        System.out.println("loading customers data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstNameTest() {
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
    }

    @Test
    public void patchCustomerUpdateLastNameTest() {
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));

    }

    private Long getCustomerIdValue() {

        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers found: " + customers.size());

        return customers.get(0).getId();
    }
}