package com.example.spring5mvcrest.api.v1.mapper;

import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    private static final String VENDOR_NAME = "First Vendor";

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(VENDOR_NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}