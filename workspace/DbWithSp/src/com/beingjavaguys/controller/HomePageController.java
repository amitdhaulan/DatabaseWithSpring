package com.beingjavaguys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.beingjavaguys.domain.User;
import com.beingjavaguys.services.UserService;
import com.google.gson.JsonObject;

@Controller
public class HomePageController {

	
	  private static final Logger logger =
	  LoggerFactory.getLogger(HomePageController.class);
	 
	Map<Integer, User> empData = new HashMap<Integer, User>();

	@Autowired
	UserService userService;

	@RequestMapping("/register")
	public ModelAndView registerUser(@ModelAttribute User user) {

		List<String> genderList = new ArrayList<String>();
		genderList.add("male");
		genderList.add("female");

		List<String> cityList = new ArrayList<String>();
		cityList.add("delhi");
		cityList.add("gurgaon");
		cityList.add("meerut");
		cityList.add("noida");

		@SuppressWarnings("rawtypes")
		Map<String, List> map = new HashMap<String, List>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		
		
		
		return new ModelAndView("register", "map", map);
	}

	@RequestMapping("/insert")
	public String inserData(@ModelAttribute User user) {
		if (user != null)
			userService.insertData(user);
		return "redirect:/getList";
	}

	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public ModelAndView getUserLIst() {
		List<User> userList = userService.getUserList();
		logger.debug("debugging start:");
		return new ModelAndView("userList", "userList", userList);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser(@RequestParam String id,
			@ModelAttribute User user) {

		user = userService.getUser(id);

		List<String> genderList = new ArrayList<String>();
		genderList.add("male");
		genderList.add("female");

		List<String> cityList = new ArrayList<String>();
		cityList.add("delhi");
		cityList.add("gurgaon");
		cityList.add("meerut");
		cityList.add("noida");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		map.put("user", user);

		return new ModelAndView("edit", "map", map);

	}

	@RequestMapping("/update")
	public String updateUser(@ModelAttribute User user) {

		userService.updateData(user);
		return "redirect:/getList";

	}

	@RequestMapping("/delete")
	public String deleteUser(@RequestParam String id) {
		System.out.println("id = " + id);
		userService.deleteData(id);
		return "redirect:/getList";
	}

	@RequestMapping("/")
	public ModelAndView getIndex() {
		return new ModelAndView("index");
	}

	@GET
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	@ResponseBody
	public String getServerTime() {
		JsonObject jsonObject = new JsonObject();
		System.out
				.println("RESTful Service 'MessageService' is running ==> ping");

		jsonObject.addProperty("Date",
				"  received ping on " + new Date().toString());
		return jsonObject.toString();

	}

	@RequestMapping(value = "/post", method = RequestMethod.POST, produces = { "application/json" }, consumes = { "application/json" })
	@ResponseBody
	public String getuserList() {
		System.out.println("get");
		JsonObject outerJsonObject = new JsonObject();

		List<User> userList = userService.getUserList();
		User user = new User();
		for (int i = 0; i < userList.size(); i++) {
			JsonObject jsonObject = new JsonObject();
			user = userList.get(i);
			jsonObject.addProperty("userId", user.getUserId());
			jsonObject.addProperty("firstName", user.getFirstName());
			jsonObject.addProperty("lastName", user.getLastName());
			jsonObject.addProperty("gender", user.getGender());
			jsonObject.addProperty("city", user.getCity());

			outerJsonObject.add("user" + i, jsonObject);
		}
		return outerJsonObject.toString();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = { "application/json" }, consumes = { "application/json" })
	@ResponseBody
	public String getuserListGet() {
		System.out.println("get");
		JsonObject outerJsonObject = new JsonObject();

		List<User> userList = userService.getUserList();
		User user = new User();
		for (int i = 0; i < userList.size(); i++) {
			JsonObject jsonObject = new JsonObject();
			user = userList.get(i);
			jsonObject.addProperty("userId", user.getUserId());
			jsonObject.addProperty("firstName", user.getFirstName());
			jsonObject.addProperty("lastName", user.getLastName());
			jsonObject.addProperty("gender", user.getGender());
			jsonObject.addProperty("city", user.getCity());

			outerJsonObject.add("user" + i, jsonObject);
		}
		return outerJsonObject.toString();
	}

	@RequestMapping(value = "/added", method = RequestMethod.POST, produces = { "application/json" }, /*consumes = { "application/json" }, */params = { "data" })
	@ResponseBody
	public String addUser(@FormParam("data") String data) {
		
		System.out.println(data);
		
		System.out.println("get");
		JsonObject outerJsonObject = new JsonObject();

		List<User> userList = userService.getUserList();
		User user = new User();
		for (int i = 0; i < userList.size(); i++) {
			JsonObject jsonObject = new JsonObject();
			user = userList.get(i);
			jsonObject.addProperty("userId", user.getUserId());
			jsonObject.addProperty("firstName", user.getFirstName());
			jsonObject.addProperty("lastName", user.getLastName());
			jsonObject.addProperty("gender", user.getGender());
			jsonObject.addProperty("city", user.getCity());

			outerJsonObject.add("user" + i, jsonObject);
		}
		return outerJsonObject.toString();
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json" }, 
			/*consumes = { "application/json" }, */params = { "data"})
	@ResponseBody
	public String addData(@FormParam(value = "data") String data) {
		
		System.out.println(data);
		JSONObject jsonObject = new JSONObject(data);
		
		User user = new User();
		user.setFirstName(jsonObject.getString("firstname"));
		user.setLastName(jsonObject.getString("lastname"));
		user.setGender(jsonObject.getString("gender"));
		user.setCity(jsonObject.getString("city"));
		
		if (user != null){
			userService.insertData(user);
			
		}
		return "message:added";
			
	}

}