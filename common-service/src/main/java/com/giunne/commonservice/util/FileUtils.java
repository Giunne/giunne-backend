package com.giunne.commonservice.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileUtils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String CATEGORY_PREFIX = "_";
    private static final String NAME_SEPARATOR = "_";
    private static final String TIME_SEPARATOR = "_";
    private static final String SEPARATOR = "_";

    public static String buildFileName(String category, String memberId, String originalFileName, LocalDateTime now) {
        // 파일 확장자
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        // 파일 이름
        String fileName = originalFileName.substring(0, fileExtensionIndex);

        return category +  CATEGORY_PREFIX + memberId + CATEGORY_PREFIX  + fileName + TIME_SEPARATOR + now + fileExtension;
    }

    public static String buildFileName(String category, Long memberId, Long postId,String originalFileName) {
        // 파일 확장자
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        // 파일 이름
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        UUID uuid = UUID.randomUUID();

        return category + CATEGORY_PREFIX + memberId + SEPARATOR + postId + SEPARATOR + uuid + fileExtension;
    }

    /**
     * 파일 이름만 가져오는 메소드
     * 파일 객체는 그대로 사용합니다.
     */
    public static String getGeneratedFileName(MultipartFile file, String category, Long memberId, Long postId) {
        String originalFileName = file.getOriginalFilename();
        return buildFileName(category, memberId, postId, originalFileName);
    }

    public static MultipartFile renameMultipartFile(MultipartFile file, String newFileName) throws IOException {
        return new MockMultipartFile(
                newFileName,            // 새로운 파일명
                newFileName,            // 새로운 원본 파일명
                file.getContentType(),  // 기존 파일의 Content-Type 유지
                file.getBytes()         // 기존 파일의 데이터 유지
        );
    }

}

