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
	String s="/rest/employees";
	   @Test
       public void getallEmployeeAPi() throws Exception
	   {
           mv.perform(get(s+"/all"))
				   .andDo(print())
				   .andExpect(jsonPath("$").isArray())
				   .andExpect(status().isOk());

	   }
	   @Test
	   public void getoneEmployeeRecordAPi() throws Exception
	   {
		 mv.perform(get(s+"/get/{id}",1))
				.andDo(print())
				.andExpect(jsonPath("$").isMap())
				.andExpect(status().isOk());


	   }
	  @Test
	  public void getOneEmployeeRecord2APi() throws Exception
	  {
		mv.perform(get(s+"/get/{id}",2))
				.andDo(print())
				.andExpect(jsonPath("$").isMap())
				.andExpect(status().isOk());


	  }
	  @Test
	  public void EmployeeNotfoundOrDoesNotExistApi() throws Exception
	  {
		mv.perform(get(s+"/get/{id}",15))
				.andDo(print())
				.andExpect(status().isNoContent());
	  }

	  @Test
	  public void EmployeeidInvalidApi() throws Exception
	  {
		mv.perform(get(s+"/get/{id}",0))
				.andDo(print())
				.andExpect(status().isBadRequest());
	  }

	  @Test
	  public void EmployeeIdNegativeApi() throws Exception
	  {
		  mv.perform(get(s+"/get/{id}",0))
				  .andDo(print())
				  .andExpect(status().isBadRequest());
	  }

	   @Test
	   public void deleteforNoExist() throws Exception
	      {
	 	    mv.perform((delete(s+"/del/{id}", 14)))
					.andDo(print())
					.andExpect(status().isNoContent());
	      }
	      @Test
	      public  void  deleteNoDirector() throws Exception
		   {
	   	     mv.perform(delete(s+"/del/{id}",1))
					.andDo(print())
					.andExpect(status().isBadRequest());
		   }

		   @Test
	       public void deleteSuccessFull() throws Exception
		   {
	   	     mv.perform(delete(s+"/del/{id}",3))
					 .andDo(print())
					 .andExpect(status().isOk());
		   }



 }
