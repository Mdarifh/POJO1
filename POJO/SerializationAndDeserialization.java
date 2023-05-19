package POJO;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SerializationAndDeserialization {
	
	@Test
	public void CreateJSONObjectFromEmployee() throws JsonProcessingException {
		Employee emp1 = new Employee();
		emp1.setFirstname("Md Arif");
		emp1.setLastname("Hussain");
		emp1.setGender("M");
		emp1.setAge(24);
		emp1.setSalary(80000.50);
		
		//convert employee class to json payload as string
		
		ObjectMapper objMapper = new ObjectMapper();
		String employeeJSON	 = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp1); 
		System.out.println(employeeJSON);
		
		//create Request Specification
		RequestSpecification reqSpec = RestAssured.given();
		reqSpec.baseUri("http://httpbin.org/post");
		reqSpec.contentType(ContentType.JSON);
		reqSpec.body(employeeJSON);
		
		//perform post Request
		Response response = reqSpec.post();
		response.prettyPrint();
		
		//Validate Status Code
		Assert.assertEquals(response.statusCode(), 200,"Check for status code");
		
		
		//convert Json string to class object
		
		Employee emp2 = objMapper.readValue(employeeJSON,Employee.class);
		System.out.println("-----------Print After JSON Object to Class object---------");
		System.out.println("FirstName "+emp2.getFirstname());
		System.out.println("LastName "+emp2.getLastname());
		System.out.println("Gender "+emp2.getGender());
		System.out.println("Age "+emp2.getAge());
		System.out.println("Salary "+emp2.getSalary());
		
		
	}
}
