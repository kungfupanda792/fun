package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import sun.reflect.annotation.ExceptionProxy;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FunApplicationTests
 {

	@Autowired
	private MockMvc mv;
	String s="/employees";

	//  Get Test Cases

	   @Test
       public void getallEmployeeAPi() throws Exception
	   {
           mv.perform(get(s))
				   .andDo(print())
				   .andExpect(jsonPath("$").isArray())
				   .andExpect(status().isOk());

	   }
	   @Test
	   public void getoneEmployeeRecordAPi() throws Exception
	   {
		 mv.perform(get(s+"/{id}",1))
				.andDo(print())
				.andExpect(jsonPath("$").isMap())
				.andExpect(status().isOk());


	   }
	  @Test
	  public void getOneEmployeeRecord2APi() throws Exception
	  {
		mv.perform(get(s+"/{id}",2))
				.andDo(print())
				.andExpect(jsonPath("$").isMap())
				.andExpect(status().isOk());


	  }
	  @Test
	  public void EmployeeNotfoundOrDoesNotExistApi() throws Exception
	  {
		mv.perform(get(s+"/{id}",15))
				.andDo(print())
				.andExpect(status().isNotFound());
	  }

	  @Test
	  public void EmployeeidInvalidApi() throws Exception
	  {
		mv.perform(get(s+"/{id}",0))
				.andDo(print())
				.andExpect(status().isBadRequest());
	  }

	  @Test
	  public void EmployeeIdNegativeApi() throws Exception
	  {
		  mv.perform(get(s+"/{id}",0))
				  .andDo(print())
				  .andExpect(status().isBadRequest());
	  }

	  // Delete Test Cases
	   @Test
	   public void deleteforNoExist() throws Exception
	      {
	 	    mv.perform((delete(s+"/{id}", 14)))
					.andDo(print())
					.andExpect(status().isNotFound());
	      }
	      @Test
	      public  void  deleteNoDirector() throws Exception
		   {
	   	     mv.perform(delete(s+"/{id}",1))
					.andDo(print())
					.andExpect(status().isBadRequest());
		   }

		   @Test
	       public void deleteSuccessFull() throws Exception
		   {
	   	     mv.perform(delete(s+"/{id}",3))
					 .andDo(print())
					 .andExpect(status().isNoContent());
		   }



 }
