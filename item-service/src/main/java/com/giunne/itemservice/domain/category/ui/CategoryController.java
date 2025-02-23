package com.giunne.itemservice.domain.category.ui;

import com.giunne.commonservice.ui.Response;
import com.giunne.itemservice.domain.category.application.CategoryService;
import com.giunne.itemservice.domain.category.application.dto.request.CreateCategoryRequestDto;
import com.giunne.itemservice.domain.category.application.dto.request.MoveWithSubCategoryRequestDto;
import com.giunne.itemservice.domain.category.application.dto.request.UpdateCategoryRequestDto;
import com.giunne.itemservice.domain.category.application.dto.response.CategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "카테고리 관리", description = "카테고리 조회 및 저장")
@RestController
@RequestMapping("/v1/api/items/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
//
//    @Operation(summary = "모든 카테고리 검색", description = """
//            ## 기능설명
//            * 모든 카테고리 검색를 검색합니다.
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @PostMapping("/1")
//    public void test(CreateCategoryRequestDto dto){
//        categoryService.insertCategory(dto);
//    }
//
//    @Operation(summary = "카테고리 생성", description = """
//            ## 기능설명
//            * 카테고리를 생성합니다.
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @PostMapping("/2")
//    public void test2(CreateCategoryRequestDto dto){
//    }
//
//    @Operation(summary = "카테고리 수정", description = """
//            ## 기능설명
//            * 카테고리를 수정합니다.
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @PostMapping("/3")
//    public void insertBetween(UpdateCategoryRequestDto dto){
//        categoryService.insertBetween(dto);
//    }
//
//    @Operation(summary = "카테고리 수정", description = """
//            ## 기능설명
//            * 카테고리를 수정합니다.
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @PostMapping("/4")
//    public void moveWithSubTree(MoveWithSubCategoryRequestDto dto){
//        categoryService.moveWithSubTree(dto);
//    }

    @Operation(summary = "카테고리 전체 조회", description = """
            ## 기능설명
            * 카테고리 전체 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/root")
    public Response<Map<Long, List<CategoryResponseDto>>> getCategories(){
        return Response.ok(categoryService.getCategories());
    }

}
