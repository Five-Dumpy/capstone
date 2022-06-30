package com.example.capstoneproject.entity.board;

import com.example.capstoneproject.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
public class SellBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=1, max=30, message = "상품명은 1자이상 30자 이하입니다.")
    private String title;

    @NotNull
    @Size(min=2, max=1000, message = "상품 설명은 2자 이상 1000자 이하입니다.")
    private String content;

    @NotNull(message = "가격을 입력해주세요")
    private Long price;

    private String details = "진행 중";

    private String filename;
    private String filepath;

    @CreatedDate
    private LocalDateTime datetime = LocalDateTime.now();

    private String elapsedtime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
