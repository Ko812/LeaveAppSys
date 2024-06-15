package sg.nus.iss.service.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sg.nus.iss.service.workshop.model.Course;
import sg.nus.iss.service.workshop.repository.CourseRepository;

@Service
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService{
	@Autowired
	
	private CourseRepository courseRepository;
	
	@Override
	public List<Course>findAllCourses()
	{
		return courseRepository.findAll();
	}
	
	
	
	

}
