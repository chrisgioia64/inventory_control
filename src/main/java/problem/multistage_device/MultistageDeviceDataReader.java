package problem.multistage_device;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class MultistageDeviceDataReader {

    private final static String DEVICE_DATA_1_FILENAME = "multistage_device_1.xlsx";

    public static MultistageDeviceData readFromFile() {
        return readFromFile(DEVICE_DATA_1_FILENAME);
    }
    public static MultistageDeviceData readFromFile(String filename) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new File(filename));
            XSSFSheet userSheet = workbook.getSheetAt(0);
            List<MultistageDeviceData.SingleStageInformation> list = readData(userSheet);
            MultistageDeviceData data = MultistageDeviceData.builder()
                    .devices(list).build();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MultistageDeviceData.SingleStageInformation> readData(XSSFSheet sheet) {
        int rowNumber = 1;
        long getIdTime = 0;
        long insertEntryTime = 0;
        long startTime = System.currentTimeMillis();
        List<MultistageDeviceData.SingleStageInformation> list = new LinkedList<>();

        while (rowNumber < 100) {
            XSSFRow row = sheet.getRow(rowNumber);
            if (row == null) {
                break;
            }
            XSSFCell cell = row.getCell(1);
            int cost = getInteger(cell);
            if (cost == 0) {
                break;
            }

            int colNum = 2;
            List<Double> probs = new LinkedList<>();
            probs.add(0.0);

            while (row.getCell(colNum) != null) {
                cell = row.getCell(colNum);
                Double d = getDouble(cell);
                if (d == 0.0) {
                    break;
                }
                probs.add(getDouble(cell));
                colNum++;
            }

            double[] probsAry = new double[probs.size()];
            int idx = 0;
            for (Double prob : probs) {
                probsAry[idx] = prob;
                idx++;
            }

            MultistageDeviceData.SingleStageInformation data = MultistageDeviceData.SingleStageInformation
                    .builder().unitCost(cost).probabilities(probsAry).build();
            list.add(data);
            rowNumber++;
        }
        return list;
    }

    public static Double getDouble(XSSFCell cell) {
        Double number = 0.0;
        if (cell == null) {
            return 0.0;
        }
        try {
            number = cell.getNumericCellValue();
            return number.doubleValue();
        } catch (NumberFormatException ex) {
            log.warn("Could not translate the " + number + " into a number");
            return 0.0;
        }
    }
    public static Integer getInteger(XSSFCell cell) {
        Double number = 0.0;
        if (cell == null) {
            return 0;
        }
        try {
            number = cell.getNumericCellValue();
            return number.intValue();
        } catch (NumberFormatException ex) {
            log.warn("Could not translate the " + number + " into a number");
            return 0;
        }
    }

}
