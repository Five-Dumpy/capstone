package com.example.capstoneproject.controller.board;

import com.example.capstoneproject.entity.board.BuyBoard;
import com.example.capstoneproject.entity.board.SellBoard;
import com.example.capstoneproject.repository.board.BuyBoardRepository;
import com.example.capstoneproject.repository.board.SellBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class ListController {

    @Autowired
    private BuyBoardRepository buyBoardRepository;

    @Autowired
    private SellBoardRepository sellBoardRepository;

    @GetMapping("/list")
    public String listBoard(Model model, @PageableDefault Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchList) {
        Page<BuyBoard> buyBoards = buyBoardRepository.findByTitleContainingOrderByIdDesc(searchList, pageable);
        model.addAttribute("buyBoards", buyBoards);

        Page<SellBoard> sellBoards = sellBoardRepository.findByTitleContainingOrderByIdDesc(searchList, pageable);
        model.addAttribute("sellBoards", sellBoards);

        return "board/list";
    }
}
