package com.giunne.itemservice.domain.itemImage.ui;

import com.giunne.commonservice.ui.Response;
import com.giunne.itemservice.domain.itemImage.application.ItemImageCsvService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "아이템 이미지 관리", description = "아이템 조회 및 저장")
@RestController
@RequestMapping("/v1/api/items/item-image")
@RequiredArgsConstructor
public class ItemImageController {

    private final ItemImageCsvService itemImageCsvService;

    @PostMapping("/upload-csv-file")
    private Response<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        itemImageCsvService.itemImageCsvToEntity(file);
        return Response.ok("저장완료");
    }
}
