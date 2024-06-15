package sg.nus.iss.service.workshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.service.workshop.model.Course;
import sg.nus.iss.service.workshop.repository.CourseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.service.workshop.model.Course;
import sg.nus.iss.service.workshop.repository.CourseRepository;

@SpringBootApplication
public class SpringServiceLayerWorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringServiceLayerWorkshopApplication.class, args);
			}
	
	
	@Bean
	CommandLineRunner loadData(CourseRepository courseRepository)
	{
		return (args) -> { 
	
		Course course1 = new Course();
		course1.setCode("FOPCS");
		course1.setName("Fundamentals of Programming in C#");
	    course1.setDescription("Introduction to programming concepts using the C# Language as the vehicle");
	    courseRepository.save(course1);
	    
	    Course course2 = new Course();
	    course2.setCode("OOPCS");
	    course2.setName("Object Oriented Programing");
	      course2.setDescription("Introduction to OOP concepts such as inheritance, polymorphism and interfaces");
	      courseRepository.save(course2);

	      Course course3 = new Course();
	      course3.setCode("ASP.NET");
	      course3.setName("Web Programming using ASP.NET");
	      course3.setDescription("Developing web application using ASP.NET MVC");
	      courseRepository.save(course3);

	    

		
		
	  };
   }
}



	
