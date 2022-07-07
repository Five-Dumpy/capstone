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

import java.util.Iterator;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private BuyBoardRepository buyBoardRepository;

    @Autowired
    private SellBoardRepository sellBoardRepository;

    @GetMapping("/")
    public String index(Model model) {
        // 구매
        int buyCount = 0;
        List<BuyBoard> buyBoards = buyBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        Iterator<BuyBoard> buyIter = buyBoards.iterator();
        while (buyIter.hasNext()) {
            String i = buyIter.next().getFilename();
            if (i.equals("noImage.png") || buyCount >= 20) {
                buyIter.remove();
            } buyCount++;
        } model.addAttribute("buyBoards", buyBoards);

        // 판매
        int sellCount = 0;
        List<SellBoard> sellBoards = sellBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        Iterator<SellBoard> sellIter = sellBoards.iterator();
        while (sellIter.hasNext()) {
            String i = sellIter.next().getFilename();
            if (i.equals("noImage.png") || sellCount >= 20) {
                sellIter.remove();
            } sellCount++;
        } model.addAttribute("sellBoards", sellBoards);

        return "index";
    }
}
