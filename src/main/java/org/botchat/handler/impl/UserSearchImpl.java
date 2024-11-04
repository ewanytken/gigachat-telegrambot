package org.botchat.handler.impl;

import org.botchat.handler.UserSearch;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public class UserSearchImpl implements UserSearch {
    private User user;
    public UserSearchImpl(User user){
        this.user = user;
    }

    @Override
    public User findUserById(String id){
        Optional<User> user = Optional.empty();
        if(this.user.getId().equals(id)){
            user = Optional.of(this.user);
        }
        return user.orElseThrow();
    }
    @Override
    public User findUserByName(String name){
        Optional<User> user = Optional.empty();
        if(this.user.getFirstName().equals(name)){
            user = Optional.of(this.user);
        }
        return user.orElseThrow();
    }
}
