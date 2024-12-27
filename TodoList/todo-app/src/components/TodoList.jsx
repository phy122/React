import React, { useEffect, useState } from 'react';
import TodoItem from './TodoItem';

const TodoList = ({ todoList, onToggle, onRemove }) => {
    // state 선언
    const [page, setPage] = useState(1);
    const [newList, setNewList] = useState([]);

    // 데이터 목록 추가
    const addList = (page) => {
        // 할 일 목록 요청
        fetch(`http://localhost:8080/todos?page=${page}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);

                // 마지막 페이지 여부 체크
                // - data.pagination.last : 마지막 페이지
                if (page > data.pagination.last) {
                    return;
                }
                const newTodoList = [...newList, ...data.list];
                setNewList(newTodoList);
                setPage(page);
            })
            .catch(error => {
                console.log(error);
            });
    };

    // 스크롤 이벤트 핸들러
    const handleScroll = () => {
        const todoListElement = document.querySelector('.todoList');
        const scrollHeight = todoListElement.scrollHeight; // 스크롤 높이
        const scrollTop = todoListElement.scrollTop; // 스크롤 위치
        const clientHeight = todoListElement.clientHeight; // 컨텐츠 높이

        // 스크롤 맨 마지막
        if (scrollHeight - scrollTop === clientHeight) {
            alert('마지막 페이지 입니다.');
            // 다음 페이지의 데이터를 추가
            addList(page + 1);
        }
    };

    useEffect(() => {
        // 스크롤 이벤트 등록
        const todoListElement = document.querySelector('.todoList');
        if (todoListElement) {
            todoListElement.addEventListener('scroll', handleScroll);
        }
        // 스크롤 이벤트 제거
        return () => {
            if (todoListElement) {
                todoListElement.removeEventListener('scroll', handleScroll);
            }
        };
    }, [page, newList]); // 의존성 배열에 page와 newList 추가

    return (
        <div className='todoList'>
            <ul>
                {todoList.map((todo) => (
                    <TodoItem key={todo.id} todo={todo} onToggle={onToggle} onRemove={onRemove} />
                ))}
                {newList.map((todo) => (
                    <TodoItem key={todo.id} todo={todo} onToggle={onToggle} onRemove={onRemove} />
                ))}
            </ul>
        </div>
    );
};

export default TodoList;