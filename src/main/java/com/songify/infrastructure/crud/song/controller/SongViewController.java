package com.songify.infrastructure.crud.song.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    Map<Integer, String> database = new HashMap<>();

    @GetMapping("/")
    public String home(){
        return "home.html";
    }

    @GetMapping("/view/songs")
    public String songs(Model model){
        database.put(1, "shawn mendes song");
        database.put(2, "ariana grande song");
        database.put(3, "ariana grande songdvsv");
        database.put(4, "ariana grande songdvsvevve");

        model.addAttribute("songMap", database);
        return "songs";
    }
}
