package com.giunne.memberservice.domain.school.ui;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.school.api.request.GetSchoolPageRequestDto;
import com.giunne.memberservice.domain.school.api.response.GetSchoolPageResponseDto;
import com.giunne.memberservice.domain.school.application.CSVService;
import com.giunne.memberservice.domain.school.application.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "학교정보 관리", description = "학교정보 조회 및 저장")
@RestController
@RequestMapping("/v1/api/member/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;
    private final CSVService csvService;

    @Operation(summary = "학교 검색", description = """
            ## 기능설명
            * 전국 학교정보를 검색합니다.
            ---
            ## 상세설명
            * 학교명(schoolName)을 이용하여 전국 학교정보를 검색합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public Response<PaginationModel<GetSchoolPageResponseDto>> findByNameLike(@ParameterObject GetSchoolPageRequestDto dto) {
        PaginationModel<GetSchoolPageResponseDto> schools = schoolService.findByNameLike(dto);
        return Response.ok(schools);
    }

    @PostMapping("/upload-csv-file")
    private Response<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        csvService.schoolCsvToEntity(file);
        return Response.ok("저장완료");
    }

}
