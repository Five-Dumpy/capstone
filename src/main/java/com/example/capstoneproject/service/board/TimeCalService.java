package com.example.capstoneproject.service.board;

import com.example.capstoneproject.entity.board.BuyBoard;
import com.example.capstoneproject.entity.board.SellBoard;
import com.example.capstoneproject.repository.board.BuyBoardRepository;
import com.example.capstoneproject.repository.board.SellBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class TimeCalService {

    @Autowired
    private BuyBoardRepository buyBoardRepository;

    @Autowired
    private SellBoardRepository sellBoardRepository;

    public BuyBoard setElapsedTimeBuy(BuyBoard board) {
        LocalDateTime datetime = board.getDatetime();

        long diffTime = LocalDateTime.now().until(datetime, ChronoUnit.SECONDS) * -1; // now보다 이후면 +, 전이면 -

        if (diffTime < 60){
            board.setElapsedtime("방금전");
            return buyBoardRepository.save(board);
        }
        diffTime = diffTime / 60;
        if (diffTime < 60) {
            board.setElapsedtime(diffTime + "분 전");
            return buyBoardRepository.save(board);
        }
        diffTime = diffTime / 60;
        if (diffTime < 24) {
            board.setElapsedtime(diffTime + "시간 전");
            return buyBoardRepository.save(board);
        }
        diffTime = diffTime / 24;
        if (diffTime < 30) {
            board.setElapsedtime(diffTime + "일 전");
            return buyBoardRepository.save(board);
        }
        diffTime = diffTime / 30;
        if (diffTime < 12) {
            board.setElapsedtime(diffTime + "개월 전");
            return buyBoardRepository.save(board);
        }
        diffTime = diffTime / 12;
        board.setElapsedtime(diffTime + "년 전");
        return buyBoardRepository.save(board);
    }

    public SellBoard setElapsedTimeSell(SellBoard board) {
        LocalDateTime datetime = board.getDatetime();

        long diffTime = LocalDateTime.now().until(datetime, ChronoUnit.SECONDS) * -1; // now보다 이후면 +, 전이면 -

        if (diffTime < 60){
            board.setElapsedtime("방금전");
            return sellBoardRepository.save(board);
        }
        diffTime = diffTime / 60;
        if (diffTime < 60) {
            board.setElapsedtime(diffTime + "분 전");
            return sellBoardRepository.save(board);
        }
        diffTime = diffTime / 60;
        if (diffTime < 24) {
            board.setElapsedtime(diffTime + "시간 전");
            return sellBoardRepository.save(board);
        }
        diffTime = diffTime / 24;
        if (diffTime < 30) {
            board.setElapsedtime(diffTime + "일 전");
            return sellBoardRepository.save(board);
        }
        diffTime = diffTime / 30;
        if (diffTime < 12) {
            board.setElapsedtime(diffTime + "개월 전");
            return sellBoardRepository.save(board);
        }
        diffTime = diffTime / 12;
        board.setElapsedtime(diffTime + "년 전");
        return sellBoardRepository.save(board);
    }
}
