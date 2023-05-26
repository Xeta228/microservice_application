package com.baron.productservice;

import com.baron.productservice.dto.ProductRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	//@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void shouldCreateProduct() {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = null;
		try {
			productRequestString = objectMapper.writeValueAsString(productRequest);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
					.contentType(MediaType.APPLICATION_JSON)
					.content(productRequestString)).andExpect(status().isCreated());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//	@Test
//	@SneakyThrows
//	void shouldGetListOfProducts(){
//		ProductRequest productRequest = getProductRequest();
//		String productAddString = objectMapper.writeValueAsString(productRequest);
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(productAddString)).andExpect(status().isCreated());
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
//				.andExpect(MockMvcResultMatchers.));
//	}



	private ProductRequest getProductRequest() {
		return ProductRequest.builder().name("iPhone 13").description("iPhone 13").description("iPhone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}


}
