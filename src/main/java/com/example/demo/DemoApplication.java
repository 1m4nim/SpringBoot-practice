package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

@SpringBootApplication
@Controller
public class DemoApplication {

	// データの保存用リスト
	private static final List<FortuneResult> fortuneHistory = new ArrayList<>();
	private static final List<BaggageItem> baggageList = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// --- 占い機能 (hello.html) ---
	@GetMapping("/profile")
	public String profilePage(Model model) {
		List<FortuneResult> displayHistory = new ArrayList<>(fortuneHistory);
		Collections.reverse(displayHistory);
		model.addAttribute("history", displayHistory);
		return "hello";
	}

	@PostMapping("/profile")
	public String doFortune(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
		if (name != null && !name.isEmpty()) {
			String[] results = { "大吉", "中吉", "小吉", "凶" };
			String luck = results[new Random().nextInt(results.length)];
			String[] items = { "青いペン", "使い古した辞書", "あたたかいココア", "新しい靴下" };
			String luckyItem = items[new Random().nextInt(items.length)];

			fortuneHistory.add(new FortuneResult(name, luck, luckyItem));

			redirectAttributes.addFlashAttribute("name", name);
			redirectAttributes.addFlashAttribute("luck", luck);
			redirectAttributes.addFlashAttribute("item", luckyItem);
		}
		return "redirect:/profile";
	}

	// --- 持ち物リスト機能 (baggage.html) ---
	@GetMapping("/baggage")
	public String baggagePage(Model model) {
		model.addAttribute("items", baggageList);
		return "baggage";
	}

	@PostMapping("/baggage/add")
	public String addBaggage(@RequestParam String name, @RequestParam String category) {
		if (!name.isEmpty()) {
			baggageList.add(new BaggageItem(name, category));
		}
		return "redirect:/baggage";
	}

	@PostMapping("/baggage/check")
	public String checkBaggage(@RequestParam String id) {
		for (BaggageItem item : baggageList) {
			if (item.id.equals(id)) {
				item.checked = !item.checked;
				break;
			}
		}
		return "redirect:/baggage";
	}

	@PostMapping("/baggage/delete")
	public String deleteBaggage(@RequestParam String id) {
		baggageList.removeIf(item -> item.id.equals(id));
		return "redirect:/baggage";
	}

	// --- 自己紹介ページを表示する命令 ---
	@GetMapping("/about")
	public String about() {
		return "about"; // about.html を表示
	}

	// --- 占いの履歴ページを表示する命令 ---
	@GetMapping("/history")
	public String history(Model model) {
		// 占い結果のリストを逆順（新しい順）にして渡す
		List<FortuneResult> displayHistory = new ArrayList<>(fortuneHistory);
		Collections.reverse(displayHistory);
		model.addAttribute("historyList", displayHistory);
		return "history"; // history.html を表示
	}

	// --- データ構造クラス ---
	public static class FortuneResult {
		public String id = UUID.randomUUID().toString();
		public String name;
		public String luck;
		public String item;

		public FortuneResult(String n, String l, String i) {
			this.name = n;
			this.luck = l;
			this.item = i;
		}

		public String getMessage() {
			return name + "さんは「" + luck + "」でした";
		}
	}

	public static class BaggageItem {
		public String id = UUID.randomUUID().toString();
		public String name;
		public String category;
		public boolean checked = false;

		public BaggageItem(String n, String c) {
			this.name = n;
			this.category = c;
		}
	}
}