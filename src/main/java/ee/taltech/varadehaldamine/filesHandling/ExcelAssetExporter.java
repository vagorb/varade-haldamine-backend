package ee.taltech.varadehaldamine.filesHandling;

import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ExcelAssetExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<AssetInfo> assets;

    public ExcelAssetExporter(List<AssetInfo> assets) {
        this.assets = assets;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Assets");
    }

    private void writeHeaderRow(){
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Asset name");
    }

    private void writeDataRows(){

    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
