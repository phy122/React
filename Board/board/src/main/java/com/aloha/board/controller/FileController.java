package com.aloha.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.board.domain.Files;
import com.aloha.board.service.FileService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired ResourceLoader resourceLoader;

    
    @GetMapping()
    public ResponseEntity<?> getAllFile() {
        try {
            List<Files> list = fileService.list();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneFile(@PathVariable("id") String id) {
        try {
            Files files = fileService.selectById(id);
            return new ResponseEntity<>(files, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> createFile(@RequestBody Files files) {
        try {
            boolean result = fileService.insert(files);
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
    public ResponseEntity<?> updateFile(@RequestBody Files files) {
        try {
            boolean result = fileService.updateById(files);
            if(result){
                return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
            } else{
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyFile(@PathVariable("id") String id) {
        try {
            boolean result = fileService.deleteById(id);
            if(result){
                return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
            } else{
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * 파일 선택 삭제
     */
    @DeleteMapping("")
    public ResponseEntity<?> deletFiles(
        @RequestParam(value = "noList", required = false) List<Long> noList,
        @RequestParam(value = "idList", required = false) List<String> idList
    ){
        log.info("noList[] : " + noList);
        log.info("idList[] : " + idList);
        boolean result = false;
        if(noList != null){
            result = fileService.deleteFiles(noList);
        }
        if(idList != null){
            result = fileService.deleteFilesById(idList);
        }
        if(result)
            return new ResponseEntity<>(HttpStatus.OK);
            
        return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    
    }

    /**
     * 파일 다운로드
     * @param id
     * @param response
     * @throws Exception
     */
    @GetMapping("/download/{id}")
    public void fileDownload(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
        fileService.download(id, response);
    }

    /**
     * 썸네일 이미지
     * @param id
     * @throws IOException 
     */
    @GetMapping("/img/{id}")
    public void thumbnailImg(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        // 파일 정보 조회
        Files files = fileService.selectById(id);
        String filePath = files != null ? files.getFilePath() : null; 

        // 기본 이미지 파일 로드
        Resource resource = resourceLoader.getResource("classpath:static/img/no-image.png");
        File imgFile;

        // 파일 경로가 null이거나 파일이 존재하지 않을 경우 no-image.png를 사용
        if (filePath == null || !(new File(filePath)).exists()) {
            imgFile = resource.getFile();
        } else {
            imgFile = new File(filePath);
        }

        // MIME 타입 설정
        String ext = (filePath != null && filePath.contains(".")) 
                        ? filePath.substring(filePath.lastIndexOf(".") + 1) 
                        : "png"; // 기본 확장자는 png로 설정
        MediaType mType;

        try {
            mType = MediaType.parseMediaType("image/" + ext);
            response.setContentType(mType.toString());
        } catch (IllegalArgumentException e) {
            // 잘못된 MIME 타입에 대한 기본 처리
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            imgFile = resource.getFile();
        }

        // 파일을 출력 스트림에 복사
        try (FileInputStream fis = new FileInputStream(imgFile);
            ServletOutputStream sos = response.getOutputStream()) {
            FileCopyUtils.copy(fis, sos);
        }
    }

    @GetMapping("/{pTable}/{pNo}")
    public ResponseEntity<?> getAllFile(
        @PathVariable("pTable") String pTable, 
        @PathVariable("pNo") Long pNo, 
        @RequestParam(value = "type", required = false) String type
        ) {
        try {
            Files files = new Files();
            files.setPTable(pTable);
            files.setPNo(pNo);
            files.setType(type);
            // type 없을 때 -> 부모 기준 모든 파일 
            log.info( "넘겨받은 파일 정보 : " + files.toString());
            if(type == null){
                List<Files> list = fileService.listByParent(files);
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            // type : "MAIN" -> 메인파일 1개  
            if(type.equals("MAIN")){
                Files mainFile = fileService.selectByType(files);
                log.info("메인 파일 : " + mainFile);
                return new ResponseEntity<>(mainFile, HttpStatus.OK);
            }
            // type : "?" -> 타입 별 여러 파일 
            else{
                List<Files> list = fileService.listByType(files);
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    
}
