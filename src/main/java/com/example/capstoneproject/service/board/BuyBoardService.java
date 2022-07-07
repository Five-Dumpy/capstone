package com.example.capstoneproject.service.board;

import com.example.capstoneproject.entity.User;
import com.example.capstoneproject.entity.board.BuyBoard;
import com.example.capstoneproject.repository.board.BuyBoardRepository;
import com.example.capstoneproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyBoardService {

    @Autowired
    private BuyBoardRepository buyBoardRepository;

    @Autowired
    private UserRepository userRepository;

    public BuyBoard save(String username, BuyBoard buyBoard) {
        User user = userRepository.findByUsername(username);
        buyBoard.setUser(user);
        return buyBoardRepository.save(buyBoard);
    }
}
