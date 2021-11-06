package com.mtr.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@CrossOrigin("http://localhost:4200/")
public class ProfileController {

	@Autowired
	private ProfileService ps;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Profile>> getAllProfile(){
		List<Profile> profiles = ps.getAllProfile();
		
		return new ResponseEntity<List<Profile>>(profiles, HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Profile> getById(@PathVariable("id") long id){
		Profile profile = ps.getById(id);
		
		return new ResponseEntity<Profile>(profile, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Profile> save(@RequestBody Profile profile){
		Profile p = ps.save(profile);
		
		return new ResponseEntity<Profile>(p, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Profile> update(@RequestBody Profile profile){
		Profile p = ps.save(profile);
		
		return new ResponseEntity<Profile>(p, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Profile> delete(@PathVariable("id") long id){
		ps.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/upload-image/{userCode}")
    public ResponseEntity<String> uploadFile(@PathVariable("userCode") String userCode, @RequestParam("file") MultipartFile file){
    	if(!file.isEmpty()) {
    		if(!ps.saveFileToFolder(userCode, file)) {
    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file not uploaded, something went wrong!!");
    		}
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file is EMPTY, try again!!");
    	}
    	return ResponseEntity.ok("file uploaded");
    }
	
	
}
