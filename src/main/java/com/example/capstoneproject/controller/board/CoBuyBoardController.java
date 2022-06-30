package com.example.capstoneproject.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class CoBuyBoardController {

    @GetMapping("/cobuy/list")
    public String coBuyList() {
        return "board/cobuy";
    }

    @GetMapping("/cobuy/form")
    public String coBuyForm() {
        return "board/cobuyform";
    }

}
