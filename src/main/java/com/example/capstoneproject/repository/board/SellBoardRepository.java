package com.example.capstoneproject.repository.board;

import com.example.capstoneproject.entity.board.SellBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellBoardRepository extends JpaRepository<SellBoard, Long> {

    Page<SellBoard> findByTitleContainingOrderByIdDesc(String searchText, Pageable pageable);

}