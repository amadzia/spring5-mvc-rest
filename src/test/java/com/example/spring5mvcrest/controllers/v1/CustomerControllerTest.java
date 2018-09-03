package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.example.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getListOfCustomersTest() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Mike");
        customer1.setLastName("West");
        customer1.setCustomerUrl("/api/v1/customers/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        customer2.setCustomerUrl("/api/v1/customers/2");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Mike");
        customer1.setLastName("West");
        customer1.setCustomerUrl("/api/v1/customers/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //when
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Mike")));
    }


    @Test
    public void createCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Fred");
        customerDTO.setLastName("Flin");


        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }
}