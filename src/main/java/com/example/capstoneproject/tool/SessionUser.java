package com.example.capstoneproject.tool;

import com.example.capstoneproject.entity.User;
import lombok.Getter;
import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String userid;

    public SessionUser(User user) {
        this.userid = user.getUserid();
    }
}