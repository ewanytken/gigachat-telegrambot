package org.botchat.handler;

import org.telegram.telegrambots.meta.api.objects.User;

public interface UserSearch {
    User findUserById(String id);
    User findUserByName(String name);
}
