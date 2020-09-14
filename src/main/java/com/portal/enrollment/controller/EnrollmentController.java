package com.portal.enrollment.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portal.enrollment.dao.EnrollmentRepository;
import com.portal.enrollment.model.Enrollment;

import io.swagger.annotations.ApiOperation;

@RestController
public class EnrollmentController {

	@Autowired
	private EnrollmentRepository repo;

	@GetMapping(value = "/")
	@ApiOperation(value = "This API is to get Swagger UI")
	public void swaggerHome(HttpServletResponse response) throws IOException {
		response.sendRedirect("swagger-ui.html");
	}

	@PostMapping(value = "/enroll", produces = "application/json")
	@ApiOperation(value = "API to insert enrollment")
	public ResponseEntity<String> insertEnrollment(@RequestBody Enrollment obj) {
		try {
			repo.save(obj);
			return new ResponseEntity<String>("enrollment added successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to add enrollment", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/all", produces = "application/json")
	@ApiOperation(value = "API to get all enrollments")
	public ResponseEntity<List<Enrollment>> get() {
		List<Enrollment> result = new ArrayList<>();
		try {
			Iterable<Enrollment> list = repo.findAll();
			list.forEach(x -> result.add(x));
			return new ResponseEntity<List<Enrollment>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Enrollment>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/one", produces = "application/json")
	@ApiOperation(value = "API to get one enrollment with given id")
	public ResponseEntity<Enrollment> get(@RequestParam Long id) {
		Enrollment enr = null;
		try {
			Optional<Enrollment> list = repo.findById(id);
			enr = list.get();
			return new ResponseEntity<Enrollment>(enr, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Enrollment>(enr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/one", produces = "application/json")
	@ApiOperation(value = "API to update enrollment and it's dependents")
	public ResponseEntity<Enrollment> update(@RequestBody Enrollment obj) {
		Enrollment enr = null;
		try {
			enr = repo.save(obj);
			return new ResponseEntity<Enrollment>(enr, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Enrollment>(enr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/one", produces = "application/json")
	@ApiOperation(value = "API to delete enrollment and it's dependencies")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			Optional<Enrollment> opt = repo.findById(id);
			if (opt.isPresent()) {
				Enrollment enr = opt.get();
				repo.delete(enr);
			}
			return new ResponseEntity<String>("deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("failed to delete enrollment", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
