package com.kob.backend.service.user.bot;

import com.kob.backend.pojo.Bot;

import java.util.List;

public interface GetListService {
    // user's self userId is in the token, no parameter.
    List<Bot> getList();
    List<Bot> getDeletedList();
}
