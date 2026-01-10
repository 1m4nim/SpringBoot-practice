// package com.example.demo;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class DemoApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(DemoApplication.class, args);
// 	}

// }

// package com.example.demo;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// @SpringBootApplication
// @RestController
// public class DemoApplication {
// 	public static void main(String[] args) {
// 		SpringApplication.run(DemoApplication.class, args);
// 	}

// 	@GetMapping("/")
// 	public String hello() {
// 		return "Hello, World";
// 	}
// }

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@SpringBootApplication
@Controller
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// @GetMapping("/profile")
	// public String profile(@RequestParam(value = "name", defaultValue = "1m4nim")
	// String name, Model model) {
	// String[] results = { "大吉", "中吉", "小吉", "凶" };
	// String luck = results[new java.util.Random().nextInt(results.length)];

	// model.addAttribute("name", name);
	// model.addAttribute("luck", luck);

	// return "hello";
	// }

	@GetMapping("/profile")
	public String profile(@RequestParam(value = "name", required = false) String name, Model model) {
		if (name != null && !name.isEmpty()) {
			String[] results = { "大吉", "中吉", "小吉", "凶" };
			String luck = results[new java.util.Random().nextInt(results.length)];
			String[]items={"青いペン","使い古した辞書","あたたかいココア","新しい靴下"};
			String luckyItem=items[new java.util.Random().nextInt(items.length)];
			
			model.addAttribute("name", name);
			model.addAttribute("luck", luck);
			model.addAttribute("item",luckyItem);
		}
		return "hello";
	}

	@GetMapping("/about")
	public String about(){
		return "about";
	}
}

// @GetMapping("/")
// public String hello() {
// return "Hello, World";
// }

// @GetMapping("/luck")
// public String fortune() {
// String[] results = { "大吉", "中吉", "小吉", "凶" };
// int r = new java.util.Random().nextInt(results.length);
// return "今日の運勢は・・・" + results[r] + "です";
// }

// @GetMapping("/greet")
// public String greet(@RequestParam(value = "name", defaultValue = "ゲスト")
// String name) {
// return "Hello," + name + "さん！SpringBootの世界へようこそ";
// }

// }