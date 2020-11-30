package com.codeup.myblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String showDice(Model model) {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{guess}")
    public String showGuessResult(@PathVariable int guess, Model model) {
        ArrayList<Integer> dices = new ArrayList<>();
        dices.add((int) (Math.random() * (6 - 1 + 1) + 1));
        dices.add((int) (Math.random() * (6 - 1 + 1) + 1));
        dices.add((int) (Math.random() * (6 - 1 + 1) + 1));
        int counter = 0;
        for ( int dice: dices) {
            if (guess == dice)
                counter++;
        }
        model.addAttribute("dices", dices);
        model.addAttribute("guess", guess);
        model.addAttribute("counter", counter);
        return "show-dice-result";
    }

}
