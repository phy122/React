package com.aloha.todo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.todo.domain.Todos;
import com.aloha.todo.mapper.TodoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TodoServiceImpl implements TodoService {
    
    @Autowired
    private TodoMapper todoMapper;

    public TodoServiceImpl(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    public List<Todos> list() {
        return todoMapper.list();
    }

    @Override
    public Todos select(Long no) {
        return todoMapper.select(no);
    }

    @Override
    public Todos selectById(String id) {
        return todoMapper.selectById(id);
    }

    @Override
    public boolean insert(Todos entity) {
        return todoMapper.insert(entity) > 0;
    }

    @Override
    public boolean update(Todos entity) {
        return todoMapper.update(entity) > 0;
    }

    @Override
    public boolean updateById(Todos entity) {
        return todoMapper.updateById(entity) > 0;
    }

    @Override
    public boolean delete(Long no) {
        return todoMapper.delete(no) > 0;
    }

    @Override
    public boolean deleteById(String id) {
        return todoMapper.deleteById(id) > 0;
    }

    @Override
    public PageInfo<Todos> list(int page, int size) {
        // PageHelper.startPage(page, size);
        PageHelper.startPage(page, size);

        List<Todos> list = todoMapper.list();
        PageInfo<Todos> pageInfo = new PageInfo<>(list);

        // status 오름차순
        // seq 오름차순
        pageInfo.getList().sort((t1, t2) -> {
            int statusCompare = t1.getStatus().compareTo(t2.getStatus());
            if(statusCompare != 0) {
                return statusCompare;
            }
            return Integer.compare(t1.getSeq(), t2.getSeq());
        });
        
        return pageInfo;
    }

    @Override
    public boolean completeAll() {
         return todoMapper.completeAll() > 0;
    }

    @Override
    public boolean deleteAll() {
        return todoMapper.deleteAll() > 0;
    }
    
}
