package com.example.capstoneproject.controller;

import com.example.capstoneproject.entity.board.BuyBoard;
import com.example.capstoneproject.entity.board.SellBoard;
import com.example.capstoneproject.repository.board.BuyBoardRepository;
import com.example.capstoneproject.repository.board.SellBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private BuyBoardRepository buyBoardRepository;

    @Autowired
    private SellBoardRepository sellBoardRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<BuyBoard> buyBoards = buyBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        Iterator<BuyBoard> iter = buyBoards.iterator();
        while (iter.hasNext()) {
            String i = iter.next().getFilename();
            if (i.equals("noImage.png")) {
                iter.remove();
            }
        }
        model.addAttribute("buyBoards", buyBoards);

        List<SellBoard> sellBoards = sellBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("sellBoards", sellBoards);
        return "index";
    }
}