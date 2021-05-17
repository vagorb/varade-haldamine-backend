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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ExcelAssetExporter {

    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    private final HashMap<Integer,List<Object>> assetData;

    private void sortAssetData(List<AssetInfo> assets){
        List<Object> data;
        for (int index = 0; index < assets.size(); index++){
            data = new LinkedList<>();
            data.add(assets.get(index).getId());
            data.add(assets.get(index).getName());
            data.add(assets.get(index).getCreatedAt());
            data.add(assets.get(index).getPrice());
            data.add(assets.get(index).getResidualPrice());
            data.add(assets.get(index).getDescriptionText());
            data.add(assets.get(index).getMajorAssetId());
            data.add(assets.get(index).getMainClass());
            data.add(assets.get(index).getSubclass());
            data.add(assets.get(index).getActive());
            data.add(assets.get(index).getBuildingAbbreviation());
            data.add(assets.get(index).getRoom());
            data.add(assets.get(index).getUserId());
            data.add(assets.get(index).getUsername());
            if (assets.get(index).getPurchaseDate()!= null){
                data.add(true);
            } else {
                data.add(false);
            }
            data.add(assets.get(index).getKitPartName());
            data.add(assets.get(index).getStructuralUnit());
            data.add(assets.get(index).getSubdivision());
            data.add(assets.get(index).getLifeMonthsLeft());
            data.add(assets.get(index).getDelicateCondition());
            assetData.put(index, data);
        }
    }

    public ExcelAssetExporter(List<AssetInfo> assets) {
        assetData = new HashMap<>();
        sortAssetData(assets);
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Assets");
    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell assetID = row.createCell(0);
        assetID.setCellValue("Nr.");
        Cell name = row.createCell(1);
        name.setCellValue("Kirjeldus");
        Cell createdAt = row.createCell(2);
        createdAt.setCellValue("PV konteerimise kuupäev");
        Cell price = row.createCell(3);
        price.setCellValue("Soetusmaksumus");
        Cell residualPrice = row.createCell(4);
        residualPrice.setCellValue("Jääkväärtus");
        Cell description = row.createCell(5);
        description.setCellValue("Kirjeldus 2");
        Cell majorAsset = row.createCell(6);
        majorAsset.setCellValue("Peavara komponent");
        Cell mainClass = row.createCell(7);
        mainClass.setCellValue("PV klassi tähis");
        Cell subclass = row.createCell(8);
        subclass.setCellValue("PV alamklassi tähis");
        row.createCell(9).setCellValue("Aktiivne");
        row.createCell(10).setCellValue("Töökohtade aadressi tähis");
        row.createCell(11).setCellValue("Tööruumi nr.");
        row.createCell(12).setCellValue("PV kasutaja nr.");
        row.createCell(13).setCellValue("PV kasutaja nimi");
        row.createCell(14).setCellValue("Soetatud");
        row.createCell(15).setCellValue("Peavara/osis");
        row.createCell(16).setCellValue("Instituut/Osakond nr.");
        row.createCell(17).setCellValue("Allüksus nr.");
        row.createCell(18).setCellValue("Eluea Jääk");
        row.createCell(19).setCellValue("Delikaatse sisuga");
    }

    private String getDataByIndex(int assetIndex,int index){
        Object data = assetData.get(assetIndex-1).get(index);
        if (data == null){
            return "";
        } else if (data instanceof Boolean){
            if ((boolean) data){
                return "Jah";
            }
            return "Ei";
        }
        return data.toString();
    }


    private void writeDataRows() {
        for (int index = 1; index <= assetData.size(); index++){
            Row row = sheet.createRow(index);
            for (int column = 0; column < 20; column++){
                String data = getDataByIndex(index, column);
                row.createCell(column).setCellValue(data);
            }
        }
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
