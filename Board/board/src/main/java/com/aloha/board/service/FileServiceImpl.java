package com.aloha.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.board.domain.Files;
import com.aloha.board.mapper.FileMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileMapper fileMapper;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<Files> list() {
        return fileMapper.list();
    }

    @Override
    public Files select(Long no) {
        return fileMapper.select(no);
    }

    @Override
    public Files selectById(String id) {
        return fileMapper.selectById(id);
    }

    @Override
    public boolean insert(Files entity) {
        return fileMapper.insert(entity) > 0;
    }

    @Override
    public boolean update(Files entity) {
        return fileMapper.update(entity) > 0;
    }

    @Override
    public boolean updateById(Files entity) {
        return fileMapper.updateById(entity) > 0;
    }

    @Override
    public boolean delete(Long no) {
        Files files = fileMapper.select(no);
        delete(files);

        return fileMapper.delete(no) > 0;
    }

    @Override
    public boolean deleteById(String id) {
        Files files = fileMapper.selectById(id);
        delete(files);
        return fileMapper.deleteById(id) > 0;
    }

    // 파일 시스템의 파일 삭제
    public boolean delete(Files files){

        if(files == null) return false;

        String filePath = files.getFilePath();
        File deleteFile = new File(filePath);

        if(!deleteFile.exists()){
            log.error("파일이 존재하지 않습니다.");
        }

        boolean deleted =  deleteFile.delete();
        if(deleted) {
            log.info("파일이 삭제되었습니다.");
            log.info("-" + filePath);
        }

        return false;

    }

    @Override
    public List<Files> listByParent(Files files) {
        return fileMapper.listByParent(files);
    }

    @Override
    public int deleteByParent(Files files) {
        List<Files> fileList = fileMapper.listByParent(files);

        for (Files deleteFile : fileList) {
            // 파일 삭제
            delete(deleteFile);
        }

        // DB 데이터 삭제
        return fileMapper.deleteByParent(files);
    }

    @Override
    public int upload(Files files) throws Exception{
        int result = 0;
        MultipartFile multipartFile = files.getData();
        // 파일이 없을 때
        if(multipartFile.isEmpty()){
            return result;
        }

        // 1. FS 에 등록 (파일 복사)
        // - 파일 정보 : 원본파일명, 파일 용량, 파일 데이터
        //              파일명, 파일경로
        String originName = multipartFile.getOriginalFilename();
        long fileSize = multipartFile.getSize();
        byte[] fileData = multipartFile.getBytes();
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = uploadPath + "/" + fileName;
        File uploadFile = new File(filePath);
        FileCopyUtils.copy(fileData, uploadFile);

        // 2. DB 에 등록 
        files.setOriginName(originName);
        files.setFileName(fileName);
        files.setFilePath(filePath);
        files.setFileSize(fileSize);

        result = fileMapper.insert(files);
        return result;
    }

    @Override
    public int upload(List<Files> fileList) throws Exception {
        int result = 0;
        if(fileList == null || fileList.isEmpty())
            return result;

        for (Files files : fileList) {
            result += upload(files);
        }
        return result;

    }

    @Override
    public int download(String id, HttpServletResponse response) throws Exception {
        Files files = fileMapper.selectById(id);

        // 파일이 없으면
        if(files == null){
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            return 0;
        }

        String fileName = files.getOriginName();    // 파일명 (다운로드시- 원본파일명)
        String filePath = files.getFilePath();      // 파일 경로
        File downloadFile = new File(filePath);
        FileInputStream fis = new FileInputStream(downloadFile);

        // 파일 다운로드 응답 헤더 세팅
        // - Content-Type : application/octet-stream
        // - Content-Disposition : attachment, filename="파일명.확장자"
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\" ");

        // 파일 출력
        ServletOutputStream sos = response.getOutputStream();


        // 다운로드
        int result = FileCopyUtils.copy(fis, sos);

        fis.close();
        sos.close();

        return result;
    }
    
}
