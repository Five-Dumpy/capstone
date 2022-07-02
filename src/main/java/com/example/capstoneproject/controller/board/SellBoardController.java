package com.example.capstoneproject.controller.board;

import com.example.capstoneproject.entity.board.SellBoard;
import com.example.capstoneproject.repository.board.SellBoardRepository;
import com.example.capstoneproject.service.board.SellBoardService;
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
public class SellBoardController {

    @Autowired
    private SellBoardService sellBoardService;

    @Autowired
    private TimeCalService timeCalService;

    @Autowired
    private SellBoardRepository sellBoardRepository;

    // 판매
    @GetMapping("/sell")  // 상세 페이지
    public String sellView(Model model, @RequestParam(required = false) Long id, Principal principal) {
        SellBoard sellBoard = sellBoardRepository.findById(id).orElse(null);
        model.addAttribute("sellBoard", sellBoard);
        if(principal != null) {
            model.addAttribute("user", principal.getName());
        }
        return "board/sellview";
    }

    @GetMapping("/sell/list")  // 판매 페이지 리스트x
    public String sellList(Model model, @PageableDefault(size = 16) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<SellBoard> sellBoards = sellBoardRepository.findByTitleContainingOrderByIdDesc(searchText, pageable);
        for (SellBoard board : sellBoards) {
            timeCalService.setElapsedTimeSell(board);
        }
        int startPage = Math.max(1, sellBoards.getPageable().getPageNumber() - 5);
        int endPage = Math.min(sellBoards.getTotalPages(), sellBoards.getPageable().getPageNumber() + 5);
        if(endPage == 0) {
            endPage = 1;
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sellBoards", sellBoards);
        return "board/sell";
    }

    @GetMapping("/sell/form")
    public String sellForm(Model model, @RequestParam(required = false) Long id) {
        if(id == null) {
            model.addAttribute("sellBoard", new SellBoard());
        } else {
            SellBoard sellBoard = sellBoardRepository.findById(id).orElse(null);
            model.addAttribute("sellBoard", sellBoard);
        }

        return "board/sellform";
    }

    @PostMapping("/sell/form")  // 판매 폼 저장
    public String sellForm(@Valid SellBoard sellBoard, BindingResult bindingResult, MultipartFile file, Authentication authentication) throws Exception {
        if(bindingResult.hasErrors()) {
            return "board/sellform";
        }
        if(file.isEmpty()) {
            sellBoard.setFilename("noImage.png");
            sellBoard.setFilepath("/files/noImage.png");
        } else {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            sellBoard.setFilename(fileName);
            sellBoard.setFilepath("/files/" + fileName);
        }
        String username = authentication.getName();
        sellBoardService.save(username, sellBoard);
        return "redirect:/board/sell/list";
    }

    @GetMapping("/sell/delete")  // 판매 게시글 삭제
    public String sellFormDelete(@RequestParam Long id) {
        SellBoard sellBoard = sellBoardRepository.findById(id).orElse(null);
        sellBoardRepository.delete(sellBoard);
        return "redirect:/board/sell/list";
    }

    @GetMapping("/sell/details/{detailNum}")
    public String details(@PathVariable int detailNum, @RequestParam Long id) {
        SellBoard sellBoard = sellBoardRepository.findById(id).orElse(null);
        if (detailNum == 2) {
            sellBoard.setDetails("예약 중");
        } else if (detailNum == 3) {
            sellBoard.setDetails("구매 완료");
        } else {
            sellBoard.setDetails("진행 중");
        }
        sellBoardRepository.save(sellBoard);
        return "redirect:/board/sell?id=" + id;
    }
}
