package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.example.spring5mvcrest.api.v1.model.VendorDTO;
import com.example.spring5mvcrest.api.v1.model.VendorListDTO;
import com.example.spring5mvcrest.domain.Vendor;
import com.example.spring5mvcrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class VendorServiceImplTest {

    public static final String FIRST_VENDOR_NAME = "First Vendor";
    public static final String SECOND_VENDOR_NAME = "Second Vendor";
    public static final long ID_1 = 1L;
    public static final long ID_2 = 2L;
    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
    }

    @Test
    public void getAllVendorsTest() {

        //given
        List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        VendorListDTO vendorListDTO = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorListDTO.getVendors().size(), is(equalTo(2)));
    }

    @Test
    public void getVendorByIdTest() {

        //given
        Vendor vendor = getVendor1();

        //mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID_1);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

        //JUnit assertThat with matchers
        assertThat(vendorDTO.getName(), is(equalTo(FIRST_VENDOR_NAME)));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFoundException() throws Exception {

        //given
        //mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID_1);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void createNewVendorTest() {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(FIRST_VENDOR_NAME);

        Vendor savedVendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    public void saveVendorByDTOTest() {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(FIRST_VENDOR_NAME);

        Vendor savedVendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        //when
        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID_1, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    public void patchVendor() throws Exception {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(FIRST_VENDOR_NAME);

        Vendor vendor = getVendor1();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.patchVendor((ID_1), vendorDTO);

        //then
        //'should' default times=1
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    public void deleteVendorByIdTest() {

        //when
        vendorRepository.deleteById(ID_1);

        //then
        then(vendorRepository).should().deleteById(anyLong());
    }

    private Vendor getVendor1() {

        Vendor vendor1 = new Vendor();
        vendor1.setName(FIRST_VENDOR_NAME);
        vendor1.setId(ID_1);
        return vendor1;
    }

    private Vendor getVendor2() {
        Vendor vendor2 = new Vendor();
        vendor2.setName(SECOND_VENDOR_NAME);
        vendor2.setId(ID_2);
        return vendor2;
    }
}