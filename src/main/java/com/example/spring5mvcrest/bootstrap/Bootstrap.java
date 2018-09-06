package com.example.spring5mvcrest.bootstrap;

import com.example.spring5mvcrest.domain.Category;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.domain.Vendor;
import com.example.spring5mvcrest.repositories.CategoryRepository;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();

        loadCustomers();

        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data Loaded = " + categoryRepository.count() );
    }

    private void loadCustomers() {

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Mike");
        customer1.setLastName("West");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        customerRepository.save(customer2);

        System.out.println("Customers loaded: " + customerRepository.count());
    }

    private void loadVendors() {

        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("First Vendor");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Second Vendor");
        vendorRepository.save(vendor2);

        System.out.println("Vendors loaded: " + vendorRepository.count());
    }
}
