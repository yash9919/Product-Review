package com.rakuten.training.web;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuten.training.domain.Product;
import com.rakuten.training.service.ProductService;
//we want juniut ton use spring so we use runner called spring runner
@RunWith(SpringRunner.class)
@WebMvcTest({ProductController.class})
class ProductControllerTest {
	@MockBean
	ProductService service;
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void getById_Returns_Ok_When_Product_Found() throws Exception {
		//Arrange
		int id=1;
		Product found=new Product("test",1234,100);
		found.setId(id);
		Mockito.when(service.findById(id)).thenReturn(found);
		
		//Act
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", id))
		.andExpect(MockMvcResultMatchers.status().is(200))
		.andExpect(MockMvcResultMatchers.jsonPath("$.id",CoreMatchers.is(id)));
		//Assert
	}
	@Test
	void getById_Returns_Ok_When_Product_Not_Found() throws Exception {
		//Arrange
		int id=1;
		//Product found=new Product("test",1234,600);
		//found.setId(id);
		Mockito.when(service.findById(id)).thenReturn(null);
		
		//Act
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", id))
		.andExpect(MockMvcResultMatchers.status().is(404));
		//Assert
	}
	@Test
	public void addProduct_Returns_Created_When_Creation_Succeeds() throws Exception {
		//Arrange
		Product toBeSentInPost = new Product("test", 1234, 1000);
		ObjectMapper jsonConverter = new ObjectMapper();
		byte[] json = jsonConverter.writeValueAsBytes(toBeSentInPost);
		
		Mockito.when(service.addnewProduct(ArgumentMatchers.any(Product.class))).thenReturn(1);
		
		//Act and Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	

}
