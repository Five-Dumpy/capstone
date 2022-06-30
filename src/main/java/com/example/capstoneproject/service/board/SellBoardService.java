package com.example.capstoneproject.service.board;

import com.example.capstoneproject.entity.User;
import com.example.capstoneproject.entity.board.BuyBoard;
import com.example.capstoneproject.entity.board.SellBoard;
import com.example.capstoneproject.repository.board.BuyBoardRepository;
import com.example.capstoneproject.repository.UserRepository;
import com.example.capstoneproject.repository.board.SellBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SellBoardService {

    @Autowired
    private SellBoardRepository sellBoardRepository;

    @Autowired
    private UserRepository userRepository;

    public SellBoard save(String username, SellBoard sellBoard) {
        User user = userRepository.findByUsername(username);
        sellBoard.setUser(user);
        return sellBoardRepository.save(sellBoard);
    }
}
