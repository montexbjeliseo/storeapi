package com.mtxbjls.storeapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.exceptions.MandatoryFieldException;
import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.services.ICategoryService;
import com.mtxbjls.storeapi.utils.Constants;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService categoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("create a new category")
    @WithMockUser(roles = Constants.Roles.ADMIN)
    void createCategory() throws Exception {

        when(categoryService.createCategory(any(RequestCategoryDTO.class)))
                .thenReturn(getSavedCategoryDTO());

        mockMvc.perform(post(Constants.Endpoints.CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestCategoryDTO())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Create a new category missing field: should throw a mandatory field exception and return bad request")
    @WithMockUser(roles = Constants.Roles.ADMIN)
    void createCategoryMissingName() throws Exception {
        when(categoryService.createCategory(any(RequestCategoryDTO.class))).thenThrow(MandatoryFieldException.class);

        mockMvc.perform(post(Constants.Endpoints.CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestCategoryDTO())))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("create a new category without admin role")
    @WithMockUser(authorities = Constants.Roles.CUSTOMER)
    void createCategoryWithoutAdminRole() throws Exception {
        mockMvc.perform(post(Constants.Endpoints.CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestCategoryDTO())))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("Create a new category without authentication")
    void createCategoryWithoutAuthentication() throws Exception {
        mockMvc.perform(post(Constants.Endpoints.CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequestCategoryDTO())))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    private ResultActions performGetAllCategories() throws Exception {
        return mockMvc.perform(get(Constants.Endpoints.CATEGORIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Get all categories: should return categories and status code 200")
    void getAllCategories() throws Exception {

        List<ResponseCategoryDTO> expected = getAllExpectedCategories();

        when(categoryService.getAllCategories()).thenReturn(expected);

        ResultActions result = performGetAllCategories();

        for (int i = 0; i < expected.size(); i++){
            result
                    .andExpect(jsonPath("$[" + i + "].id").value(expected.get(i).getId()))
                    .andExpect(jsonPath("$[" + i + "].name").value(expected.get(i).getName()))
                    .andExpect(jsonPath("$[" + i + "].image").value(expected.get(i).getImage()));
        }

        result
                .andExpect(jsonPath("$", hasSize(expected.size())))
                .andDo(print());
    }

    @Test
    @DisplayName("Get category by id: should return category and status code 200")
    void getCategoryById() throws Exception {

        ResponseCategoryDTO expected = getSavedCategoryDTO();

        when(categoryService.getCategoryById(1L)).thenReturn(expected);

        mockMvc.perform(get(Constants.Endpoints.CATEGORIES + "/" + expected.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.image").value(expected.getImage()))
                .andDo(print());

    }

    @Test
    @DisplayName("Get category by id: should return status code 404")
    void getCategoryByIdNotFound() throws Exception {
        when(categoryService.getCategoryById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(get(Constants.Endpoints.CATEGORIES + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    public static ResponseCategoryDTO getSavedCategoryDTO() {
        return ResponseCategoryDTO.builder()
                .id(1L)
                .name("testCategory")
                .image("testImage.png")
                .build();
    }

    public static RequestCategoryDTO getRequestCategoryDTO() {
        return RequestCategoryDTO.builder()
                .name("testCategory")
                .image("testImage.png")
                .build();
    }

    public static List<ResponseCategoryDTO> getAllExpectedCategories() {
        return List.of(
                ResponseCategoryDTO.builder()
                        .id(1L)
                        .name("testCategory")
                        .image("testImage.png")
                        .build(),
                ResponseCategoryDTO.builder()
                        .id(2L)
                        .name("testCategory2")
                        .image("testImage2.png")
                        .build(),
                ResponseCategoryDTO.builder()
                        .id(3L)
                        .name("testCategory3")
                        .image("testImage3.png")
                        .build()
        );
    }
}
