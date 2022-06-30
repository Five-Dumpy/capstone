package com.example.capstoneproject.controller.board;

import com.example.capstoneproject.entity.board.BuyBoard;
import com.example.capstoneproject.entity.board.SellBoard;
import com.example.capstoneproject.repository.board.BuyBoardRepository;
import com.example.capstoneproject.service.board.BuyBoardService;
import com.example.capstoneproject.service.board.TimeCalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/board")
public class BuyBoardController {

    @Autowired
    private BuyBoardService buyBoardService;

    @Autowired
    private TimeCalService timeCalService;

    @Autowired
    private BuyBoardRepository buyBoardRepository;

    // 판매
    @GetMapping("/buy")  // 상세 페이지
    public String buyView(Model model, @RequestParam(required = false) Long id, Principal principal) {
        BuyBoard buyBoard = buyBoardRepository.findById(id).orElse(null);
        model.addAttribute("buyBoard", buyBoard);
        if(principal != null) {
            model.addAttribute("user", principal.getName());
        }
        return "board/buyview";
    }

    @GetMapping("/buy/list")  // 판매 페이지 리스트x
    public String buyList(Model model, @PageableDefault(size = 4) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<BuyBoard> buyBoards = buyBoardRepository.findByTitleContainingOrderByIdDesc(searchText, pageable);
        for (BuyBoard board : buyBoards) {
            timeCalService.setElapsedTimeBuy(board);
        }
        int startPage = Math.max(1, buyBoards.getPageable().getPageNumber() - 5);
        int endPage = Math.min(buyBoards.getTotalPages(), buyBoards.getPageable().getPageNumber() + 5);
        if(endPage == 0) {
            endPage = 1;
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("buyBoards", buyBoards);
        return "board/buy";
    }

    @GetMapping("/buy/form")
    public String buyForm(Model model, @RequestParam(required = false) Long id) {
        if(id == null) {
            model.addAttribute("buyBoard", new BuyBoard());
        } else {
            BuyBoard buyBoard = buyBoardRepository.findById(id).orElse(null);
            model.addAttribute("buyBoard", buyBoard);
        }

        return "board/buyform";
    }

    @PostMapping("/buy/form")  // 판매 폼 저장
    public String buyForm(@Valid BuyBoard buyBoard, BindingResult bindingResult, MultipartFile file, Authentication authentication) throws Exception {
        if(bindingResult.hasErrors()) {
            return "board/buyform";
        }
        if(file.isEmpty()) {
            buyBoard.setFilename("noImage.png");
            buyBoard.setFilepath("/files/noImage.png");
        } else {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            buyBoard.setFilename(fileName);
            buyBoard.setFilepath("/files/" + fileName);
        }
        String username = authentication.getName();
        buyBoardService.save(username, buyBoard);
        return "redirect:/board/buy/list";
    }

    @GetMapping("/buy/delete")  // 판매 게시글 삭제
    public String buyFormDelete(@RequestParam Long id) {
        BuyBoard buyBoard = buyBoardRepository.findById(id).orElse(null);
        buyBoardRepository.delete(buyBoard);
        return "redirect:/board/buy/list";
    }

    @GetMapping("/buy/details/{detailNum}")
    public String details(@PathVariable int detailNum, @RequestParam Long id) {
        BuyBoard buyBoard = buyBoardRepository.findById(id).orElse(null);
        if (detailNum == 2) {
            buyBoard.setDetails("예약 중");
        } else if (detailNum == 3) {
            buyBoard.setDetails("구매 완료");
        } else {
            buyBoard.setDetails("진행 중");
        }
        buyBoardRepository.save(buyBoard);
        return "redirect:/board/buy?id=" + id;
    }
}
