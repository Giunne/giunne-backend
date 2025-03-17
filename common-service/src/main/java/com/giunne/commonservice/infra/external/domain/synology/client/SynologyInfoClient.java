package com.giunne.commonservice.infra.external.domain.synology.client;

import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.commonservice.infra.external.domain.synology.client.request.UploadFileRequestDto;
import com.giunne.commonservice.ui.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(url = "${app.apiUrl.synology-service}", name = "synologyInfoClient")
public interface SynologyInfoClient {

    @PostMapping(value = "/upload.php", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String uploadFile(@RequestPart(name = "file") MultipartFile file);

}
