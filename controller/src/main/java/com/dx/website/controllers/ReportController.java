package com.dx.website.controllers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/26.
 */
    /*
     HSSF[1]  － 提供读写Microsoft Excel XLS格式档案的功能。
     XSSF[1]  － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
     HWPF[1]  － 提供读写Microsoft Word DOC格式档案的功能。
     HSLF[1]  － 提供读写Microsoft PowerPoint格式档案的功能。
     HDGF[1]  － 提供读Microsoft Visio格式档案的功能。
     HPBF[1]  － 提供读Microsoft Publisher格式档案的功能。
     HSMF[1]  － 提供读Microsoft Outlook格式档案的功能。
     */
@RestController
@RequestMapping("/api")
public class ReportController {
    //    @Resource
//    ReportService reportService;
    @RequestMapping(value = "/report/generate", method = RequestMethod.GET)
    public String getReport() throws IOException {
        createReport();
        return "ok";
    }

    private static void createReport() throws IOException {
        try (Workbook wb = new HSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Sheet 1");
            final int numOfRows = 3;
            final int numOfColumns = 10;

            Row row;
            Cell cell;
            for (int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
                row = sheet.createRow((short) rowIndex);
                for (int colIndex = 0; colIndex < numOfColumns; colIndex++) {
                    cell = row.createCell((short) colIndex);
                    cell.setCellValue(colIndex + rowIndex);
                }
            }

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);

            Chart chart = drawing.createChart(anchor);
            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            ScatterChartData data = chart.getChartDataFactory().createScatterChartData();

            ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 0, numOfColumns - 1));
            ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, numOfColumns - 1));
            ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 0, numOfColumns - 1));

            data.addSerie(xs, ys1);
            data.addSerie(xs, ys2);

            chart.plot(data, bottomAxis, leftAxis);

            try (FileOutputStream fileOut = new FileOutputStream("my-scatter-chart")) {
                wb.write(fileOut);
            }
        }
    }
}