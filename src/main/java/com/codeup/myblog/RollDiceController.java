package com.codeup.myblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String showDice(Model model) {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{guess}")
    public String showGuessResult(@PathVariable int guess, Model model) {
        int dice = (int) (Math.random() * (6 - 1 + 1) + 1);
        model.addAttribute("dice", dice);
        model.addAttribute("guess", guess);
        return "show-dice-result";
    }

}
