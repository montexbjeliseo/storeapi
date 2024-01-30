package com.mtxbjls.storeapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;
import com.mtxbjls.storeapi.exceptions.MandatoryFieldException;
import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.services.IProductService;
import com.mtxbjls.storeapi.utils.Constants;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Create a new product")
    @WithMockUser(authorities = Constants.Roles.ADMIN)
    void createProduct() throws Exception {
        when(productService.createProduct(any()))
                .thenReturn(getSavedProductDTO());

        mockMvc.perform(post(Constants.Endpoints.PRODUCTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestProductDTO())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Create a new product with missing mandatory fields")
    @WithMockUser(authorities = Constants.Roles.ADMIN)
    void createProductWithMandatoryFields() throws Exception {
        when(productService.createProduct(any())).thenThrow(MandatoryFieldException.class);

        mockMvc.perform(post(Constants.Endpoints.PRODUCTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestProductDTO())))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Create a new product without admin role")
    @WithMockUser(authorities = Constants.Roles.CUSTOMER)
    void createProductWithoutAdminRole() throws Exception {
        mockMvc.perform(post(Constants.Endpoints.PRODUCTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestProductDTO())))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("Create a new product without authentication")
    void createProductWithoutAuthentication() throws Exception {
        mockMvc.perform(post(Constants.Endpoints.PRODUCTS))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    public ResultActions performGetAllProducts() throws Exception {
        return mockMvc.perform(get(Constants.Endpoints.PRODUCTS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }

    @Test
    @DisplayName("Get all products")
    void getAllProducts() throws Exception {

        List<ResponseProductDTO> expectedProducts = getAllExpectedProducts();

        when(productService.getAllProducts(0, 100)).thenReturn(expectedProducts);

        ResultActions resultActions = performGetAllProducts();

        for (int i = 0; i < expectedProducts.size(); i++) {
            resultActions
                    .andExpect(jsonPath("$[" + i + "].id").value(expectedProducts.get(i).getId()))
                    .andExpect(jsonPath("$[" + i + "].title").value(expectedProducts.get(i).getTitle()))
                    .andExpect(jsonPath("$[" + i + "].description").value(expectedProducts.get(i).getDescription()))
                    .andExpectAll(jsonPath("$[" + i + "].images", containsInAnyOrder(expectedProducts.get(i).getImages())))
                    .andExpect(jsonPath("$[" + i + "].price").value(expectedProducts.get(i).getPrice()))
                    .andExpect(jsonPath("$[" + i + "].category.id").value(expectedProducts.get(i).getCategory().getId()))
                    .andExpect(jsonPath("$[" + i + "].category.name").value(expectedProducts.get(i).getCategory().getName()))
                    .andExpect(jsonPath("$[" + i + "].category.image").value(expectedProducts.get(i).getCategory().getImage()));
        }

        resultActions
                .andExpect(jsonPath("$", hasSize(expectedProducts.size())))
                .andDo(print());
    }

    @Test
    @DisplayName("Get product by id")
    void getProductById() throws Exception {

        ResponseProductDTO expectedProduct = getSavedProductDTO();

        when(productService.getProductById(1L)).thenReturn(getSavedProductDTO());

        mockMvc.perform(get(Constants.Endpoints.PRODUCTS + "/" + 1L))
                .andExpect(jsonPath("$.id").value(expectedProduct.getId()))
                .andExpect(jsonPath("$.title").value(expectedProduct.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedProduct.getDescription()))
                .andExpect(jsonPath("$.images", containsInAnyOrder(expectedProduct.getImages())))
                .andExpect(jsonPath("$.price").value(expectedProduct.getPrice()))
                .andExpect(jsonPath("$.category.id").value(expectedProduct.getCategory().getId()))
                .andExpect(jsonPath("$.category.name").value(expectedProduct.getCategory().getName()))
                .andExpect(jsonPath("$.category.image").value(expectedProduct.getCategory().getImage()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Get product by id not found: should return status code 404")
    void getProductByIdNotFound() throws Exception {
        when(productService.getProductById(anyLong())).thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get(Constants.Endpoints.PRODUCTS + "/" + 1L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Disabled
    @Test
    @DisplayName("Update product: should return status code 200")
    @WithMockUser(authorities = Constants.Roles.ADMIN)
    void updateProduct() throws Exception {
        ResponseProductDTO expected = getSavedProductDTO();
        when(productService.updateProduct(anyLong(), any(RequestProductDTO.class))).thenReturn(expected);

        mockMvc.perform(patch(Constants.Endpoints.PRODUCTS.concat("/1"))
                        .content(objectMapper.writeValueAsString(getRequestProductDTO())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    public static RequestProductDTO getRequestProductDTO() {
        return RequestProductDTO.builder()
                .title("testProduct")
                .description("testDescription")
                .images(new String[]{"testImage.png"})
                .price(10.0)
                .category_id(1L)
                .build();
    }

    public static ResponseProductDTO getSavedProductDTO() {
        return ResponseProductDTO.builder()
                .id(1L)
                .title("testProduct")
                .description("testDescription")
                .images(new String[]{"testImage.png"})
                .price(10.0)
                .category(getSavedCategoryDTO())
                .build();
    }

    public static ResponseCategoryDTO getSavedCategoryDTO() {
        return ResponseCategoryDTO.builder()
                .id(1L)
                .name("testCategory")
                .image("testImage.png")
                .build();
    }

    public static List<ResponseProductDTO> getAllExpectedProducts() {
        return List.of(
                ResponseProductDTO.builder()
                        .id(1L)
                        .title("testProduct")
                        .description("testDescription")
                        .images(new String[]{"testImage.png"})
                        .price(10.0)
                        .category(getSavedCategoryDTO())
                        .build(),
                ResponseProductDTO.builder()
                        .id(2L)
                        .title("testProduct2")
                        .description("testDescription2")
                        .images(new String[]{"testImage2.png"})
                        .price(20.0)
                        .category(getSavedCategoryDTO())
                        .build(),
                ResponseProductDTO.builder()
                        .id(3L)
                        .title("testProduct3")
                        .description("testDescription3")
                        .images(new String[]{"testImage3.png"})
                        .price(30.0)
                        .category(getSavedCategoryDTO())
                        .build()
        );
    }
}
