package com.aloha.todo.Service;

import com.aloha.todo.domain.Todos;
import com.github.pagehelper.PageInfo;

public interface TodoService extends BaseService<Todos> {

    public PageInfo<Todos> list(int page, int size);
    public boolean completeAll();
    public boolean deleteAll();
    
}
