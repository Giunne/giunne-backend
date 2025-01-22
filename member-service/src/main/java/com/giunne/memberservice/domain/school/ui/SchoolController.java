package com.giunne.memberservice.domain.school.ui;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.school.api.request.GetSchoolPageRequestDto;
import com.giunne.memberservice.domain.school.api.response.GetSchoolPageResponseDto;
import com.giunne.memberservice.domain.school.application.CSVService;
import com.giunne.memberservice.domain.school.application.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/api/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;
    private final CSVService csvService;

    @PostMapping
    public Response<PaginationModel<GetSchoolPageResponseDto>> findByNameLike(@RequestBody GetSchoolPageRequestDto dto) {
        PaginationModel<GetSchoolPageResponseDto> schools = schoolService.findByNameLike(dto);
        return Response.ok(schools);
    }

    @PostMapping("/upload-csv-file")
    private Response<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        csvService.schoolCsvToEntity(file);
        return Response.ok("저장완료");
    }

}
