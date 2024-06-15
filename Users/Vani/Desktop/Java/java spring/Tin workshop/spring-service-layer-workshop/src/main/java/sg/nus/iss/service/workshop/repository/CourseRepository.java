package sg.nus.iss.service.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.workshop.model.Course;

public interface CourseRepository extends JpaRepository <Course, Integer>
{

}
