package com.cms.customer_management.util;

import com.cms.customer_management.dto.ExcelRowCustomer;
import com.cms.customer_management.entity.Customer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcelCustomerHelper {

    // Validate file type
    public static boolean isExcelFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null &&
                (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        || contentType.equals("application/vnd.ms-excel"));
    }

    // Main Excel reader
    public static List<ExcelRowCustomer> excelToCustomers(MultipartFile file) {

        if (!isExcelFile(file)) {
            throw new RuntimeException("Invalid file type. Upload Excel only.");
        }

        List<ExcelRowCustomer> results = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Start from row 1 (skip header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                int rowNum = i + 1; // actual Excel row number

                String name   = getCellValue(row.getCell(0));
                String nic    = getCellValue(row.getCell(1));
                String dobStr = getCellValue(row.getCell(2));

                // Mandatory field validation
                if (name == null || nic == null || dobStr == null) {
                    results.add(new ExcelRowCustomer(
                            rowNum, null, "Missing mandatory field"));
                    continue;
                }

                // NIC format validation
                if (!NicValidator.isValid(nic)) {
                    results.add(new ExcelRowCustomer(
                            rowNum, null, "Invalid NIC format → " + nic));
                    continue;
                }

                // DOB parsing
                LocalDate dob;
                try {
                    dob = LocalDate.parse(dobStr);
                } catch (Exception e) {
                    results.add(new ExcelRowCustomer(
                            rowNum, null,
                            "Invalid DOB format. Expected yyyy-MM-dd"));
                    continue;
                }

                // Valid row → build customer
                Customer customer = new Customer();
                customer.setName(name);
                customer.setNic(nic);
                customer.setDateOfBirth(dob);

                results.add(new ExcelRowCustomer(rowNum, customer, null));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage());
        }

        return results;
    }

    // Safe cell reader
    private static String getCellValue(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                // Excel date stored as number
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue()
                            .toLocalDate()
                            .toString(); // yyyy-MM-dd
                }
                // NIC or other numbers
                return String.valueOf((long) cell.getNumericCellValue());

            default:
                return null;
        }
    }
}
