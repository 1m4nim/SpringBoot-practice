package com.example.demo;

import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // 追加
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 追加

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@Controller
public class DemoApplication {

	private static final List<FortuneResult> history = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// 表示用のメソッド（リロードしてもここが呼ばれるだけなので安全）
	@GetMapping("/profile")
	public String profilePage(Model model) {
		List<FortuneResult> displayHistory = new ArrayList<>(history);
		Collections.reverse(displayHistory);
		model.addAttribute("history", displayHistory);
		return "hello";
	}

	// 占い実行用のメソッド（ボタンを押したときだけ呼ばれる）
	@PostMapping("/profile")
	public String doFortune(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
		if (name != null && !name.isEmpty()) {
			String[] results = { "大吉", "中吉", "小吉", "凶" };
			String luck = results[new java.util.Random().nextInt(results.length)];
			String[] items = { "青いペン", "使い古した辞書", "あたたかいココア", "新しい靴下" };
			String luckyItem = items[new java.util.Random().nextInt(items.length)];

			history.add(new FortuneResult(name, luck, luckyItem));

			// リダイレクト先にデータを引き継ぐ設定
			redirectAttributes.addFlashAttribute("name", name);
			redirectAttributes.addFlashAttribute("luck", luck);
			redirectAttributes.addFlashAttribute("item", luckyItem);
		}
		// 処理が終わったら GET の /profile へ転送
		return "redirect:/profile";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/history")
	public String history(Model model) {
		List<FortuneResult> displayHistory = new ArrayList<>(history);
		Collections.reverse(displayHistory);
		model.addAttribute("historyList", displayHistory);
		return "history";
	}

	public static class FortuneResult {
		public String id = UUID.randomUUID().toString();
		public String name;
		public String luck;
		public String item;

		public FortuneResult(String name, String luck, String item) {
			this.name = name;
			this.luck = luck;
			this.item = item;
		}

		public String getMessage() {
			return name + "さんは「" + luck + "」でした";
		}
	}

	// 履歴削除用のメソッドを追加
	@PostMapping("/history/delete")
	public String deleteHistory(@RequestParam("id") String id) {
		// idが一致するものをリストから取り除く
		history.removeIf(result -> result.id.equals(id));
		return "redirect:/history"; // 削除後は履歴ページを再表示
	}
}