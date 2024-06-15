package sg.nus.iss.service.workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.service.workshop.model.Course;
import sg.nus.iss.service.workshop.service.CourseService;

@Controller
	@RequestMapping("course")
	public class CourseController
	{
		@Autowired
		private CourseService courseService;
		
		@GetMapping("/list")
		public String listCourses(Model model) {
		    List<Course> courses = courseService.findAllCourses();
		    model.addAttribute("courses", courses);
		    return "course-list";
		}

		
		
	}


