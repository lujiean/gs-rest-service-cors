package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.User;
import hello.UserRepository;
import utils.StoreProcedureExt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import javax.sql.DataSource;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@Autowired
	private LjjtestRepository ljjtestRepository;

	@Autowired
	private DataSource ds;

	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody Response addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		// return "Saved";
		return new Response("Saved");
	}

	@GetMapping(path="/update") // Map ONLY GET Requests
	// public @ResponseBody String updateUser (@RequestParam Integer id, @RequestParam String name
	public @ResponseBody Response updateUser (@RequestParam Integer id, @RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		if (userRepository.existsById(id)) {
			User n = new User();
			n.setId(id);
			n.setName(name);
			n.setEmail(email);
			userRepository.save(n);
			// return "Updated";
			return new Response("Updated");
		} else {
			// return "ID not exists";
			return new Response("ID not exists");
		}
	}

	@GetMapping(path="/delete") // Map ONLY GET Requests
	// public @ResponseBody String delUser (@RequestParam Integer id) {
	public @ResponseBody Response delUser (@RequestParam Integer id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return new Response("Deleted");
		} else {
			return new Response("ID not exists");
		}
	}

	@GetMapping(path="/findByID") // Map ONLY GET Requests
	// public @ResponseBody Optional<User> finduserbyid (@RequestParam Integer id) {
	// Optional not for Response.
	public @ResponseBody Iterable<User> finduserbyid (@RequestParam Integer id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		// return userRepository.findById(id);

		ArrayList<User> foit = new ArrayList<User>();
		Optional<User> ou = userRepository.findById(id);

		if(ou.isPresent()){
			foit.add(ou.get());
		}
		return foit;
		
		// for (User u : userRepository.findById(id)) {
			
		// }
		// userRepository.findById(id)

	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
	// public @ResponseBody Userout getAllUsers() {
		// This returns a JSON or XML with the users
		// System.out.println("==== in demo/all ====");
		return userRepository.findAll();
		// for (User u : userRepository.findAll()) {
		// 	return new Userout(u.getId(), u.getName(), u.getEmail());
		// }
		// return null;
	}

	@GetMapping(path="/findByName")
	public @ResponseBody Iterable<User> findByName(@RequestParam String name){
		return userRepository.findByName(name);
	}

	@GetMapping(path="/findBy2Param")
	public @ResponseBody Iterable<User> findBy2Param(@RequestParam String name, @RequestParam String name2){
		return userRepository.findBy2Param(name, name2);
	}

	@GetMapping(path="/updateBySql")
	// public @ResponseBody String updateBySql(@RequestParam Integer id, @RequestParam String name2){
	public @ResponseBody Response updateBySql(@RequestParam Integer id, @RequestParam String name2){
		// userRepository.updateBySql(id, name2);
		userRepository.updateBySql(name2, id);
		// return "success";
		return new Response("success");
	}

	@GetMapping(path="/file")
	@ResponseBody
	public String FileProcess(@RequestParam String type, @RequestParam String filename){
		String s = null, outstr = "";
		// File file;
		File file = new File(filename);
		switch (type) {
			case "r":
				// file = new File("C:\\Users\\jiean.a.lu\\Documents\\test\\r.txt");
				// file = new File(filename);
				try {
					BufferedReader bf = new BufferedReader(new FileReader(file));
					while ((s = bf.readLine()) != null) {
						// System.out.println(s);
						outstr = outstr + s + System.getProperty("line.separator");
					}
					bf.close();
				} catch (Exception e) {
					//TODO: handle exception
					return e.getMessage();
				}
				// break;
				return outstr;
			case "w":
				// file = new File("C:\\Users\\jiean.a.lu\\Documents\\test\\w.txt");
				// file = new File(filename);
				try {
					if (!file.exists()) {
						file.createNewFile();
					}
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.newLine();
					bw.write("testString");
					bw.close();
				} catch (Exception e) {
					//TODO: handle exception
					return e.getMessage();
				}
				// break;
				outstr="success";
				return outstr;
			default:
				return type + " not support";
		}
		// return "success";
	}

	@GetMapping(path="norb")
	public String norb(){
		return "index";
	}

	@GetMapping(path="/ljjall")
	@ResponseBody
	public Iterable<Ljjtest> ljjAll() {
		return ljjtestRepository.findAll();
	}

	@GetMapping(path="/findBySP")
	@ResponseBody
	public LjjSPOutMap findBySP(@RequestParam Integer id){

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("ljjtest_sp1");
		SqlParameterSource in = new MapSqlParameterSource().
								addValue("fi_id", id);
		Map<String, Object> out = jdbcCall.execute(in);
		// set ouput
		LjjSPOutMap spOut = new LjjSPOutMap();
		spOut.setName((String) out.get("fo_name"));
		spOut.setEmail((String) out.get("fo_email"));
		// set output end
		return spOut;
		// https://www.cnblogs.com/youcong/p/9460861.html
		// https://www.cnblogs.com/AloneSword/p/3591702.html
		// return "found";
	}

	@GetMapping(path="/testjsp")
	public String testJsp(){
		System.out.println("in testJsp");
		return "jsp_page";
	}

	//test sp class output
	@GetMapping(path="/spjson")
	@ResponseBody
	public SP testSPjson() {
	// public @ResponseBody Userout getAllUsers() {
		// This returns a JSON or XML with the users
		// System.out.println("==== in demo/all ====");

		List<SPColumn> spc = new ArrayList<SPColumn>();
		spc.add(new SPColumn(1, "pol_no", 1, false));
		spc.add(new SPColumn(2, "mvmt_no", 2, false));
		spc.add(new SPColumn(3, "is_bb_define", 1, true));

		SP sp = new SP("sp_test", spc);
		return sp;
		// for (User u : userRepository.findAll()) {
		// 	return new Userout(u.getId(), u.getName(), u.getEmail());
		// }
		// return null;
	}
}