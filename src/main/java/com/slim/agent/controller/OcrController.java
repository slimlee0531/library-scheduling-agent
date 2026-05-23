package com.slim.agent.controller;

import com.slim.agent.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @PostMapping("/upload")
    public ApiResponse<String> uploadScheduleImage() {
        // TODO: 实现OCR识别逻辑
        return ApiResponse.success("OCR功能开发中");
    }

}
