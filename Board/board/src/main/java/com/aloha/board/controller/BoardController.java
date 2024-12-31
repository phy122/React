package com.aloha.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.board.domain.Boards;
import com.aloha.board.domain.Files;
import com.aloha.board.domain.Pagination;
import com.aloha.board.service.BoardService;
import com.aloha.board.service.FileService;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;
    
    @GetMapping()
    public ResponseEntity<?> getAllBoard(
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        try {
            PageInfo<Boards> pageInfo = boardService.list(page,size);
            Pagination pagination = new Pagination();
            pagination.setPage(page);
            pagination.setPage(size);
            pagination.setTotal(pageInfo.getTotal());
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("list", pageInfo.getList());
            response.put("pagination", pagination);
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneBoard(@PathVariable("id") String id) {
        try {
            Boards boards = boardService.selectById(id);
            // 파일 목록 조회
            Files files = new Files();
            files.setPTable("boards");
            files.setPNo(boards.getNo());
            List<Files> fileList = fileService.listByParent(files);
            Map<String, Object> response = new HashMap<>();
            response.put("board", boards);
            response.put("fileList", fileList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * - @RequestBody 붙일 때 안 붙일 때 차이
     * - @RequestBody O : application/json, application/xml
     * - @RequestBody X : multipart/form-data, application/x-www-form-urlencoded
     */
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBoardFormData( Boards boards) {
        log.info("게시글 등록 - multipart/form-data");
        try {
            boolean result = boardService.insert(boards);
            if(result){
                return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
            } else{
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBoardJSON(@RequestBody Boards boards) {
        log.info("게시글 등록 - application/json");
        try {
            boolean result = boardService.insert(boards);
            if(result){
                return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
            } else{
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping()
    public ResponseEntity<?> updateBoard(@RequestBody Boards boards) {
        try {
            boolean result = boardService.updateById(boards);
            if(result){
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            } else{
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyBoard(@PathVariable("id") String id) {
        try {
            boolean result = boardService.deleteById(id);
            if(result){
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            } else{
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
