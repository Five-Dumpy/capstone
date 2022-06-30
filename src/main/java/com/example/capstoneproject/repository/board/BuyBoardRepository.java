package com.example.capstoneproject.repository.board;

import com.example.capstoneproject.entity.board.BuyBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyBoardRepository extends JpaRepository<BuyBoard, Long> {

    Page<BuyBoard> findByTitleContainingOrderByIdDesc(String searchText, Pageable pageable);

}