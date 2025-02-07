package com.corruptprotoss.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.corruptprotoss.dto.ReportImportExcelAnalysisDto;
import com.corruptprotoss.listener.ReportImportAnalysisListener;
import com.corruptprotoss.utils.R;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RoyDowney
 * @date 2025/2/7
 */

@Api(value = "execl操作控制器", tags = "execl操作控制器", description = "execl操作控制器")
@RestController
@RequestMapping("/execl/operate")
public class ExeclOperateColtroller {


    /**
     * <p> 组织财务报表-报表导入财报明细解析 </p>
     *
     * @author: RoyDowney
     * @Date: 2025-02-06 14:04:04
     */
    @ApiOperation(value = "组织财务报表-报表导入财报明细解析", notes = "组织财务报表-报表导入财报明细解析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/reportImportExcelAnalysis")
    public R reportImportExcelAnalysis(@ApiParam(name = "file", value = "文件信息", required = true) @RequestParam("file") MultipartFile file) {

        // 解析
        List<ReportImportExcelAnalysisDto> analysisList = new ArrayList<>();
        try {
            ReportImportAnalysisListener listener = new ReportImportAnalysisListener();
            EasyExcel.read(file.getInputStream(), ReportImportExcelAnalysisDto.class, listener)
                    .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
            analysisList = listener.getList();
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("导入文件解析异常，请检查文件内容格式！异常信息："+e.getMessage());
        }
        if (analysisList.isEmpty()) {
            return R.ok("未解析到任何有效数据，请检查文件内容！");
        }

        return R.ok();
    }



}
