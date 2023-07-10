package com.sbs.service.helper;

import com.sbs.domain.Item;
import com.sbs.domain.Order;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfHelper {

    private static final Logger log = LoggerFactory.getLogger(PdfHelper.class);

    private static final int MAX_CHARACTERS_PER_LINE = 35;
    private static final int TABLE_TOP_Y = 520;
    private static final int TABLE_BOTTOM_Y = 50;
    private static final int TABLE_LEFT_X = 50;
    private static final int TABLE_RIGHT_X = 550;
    private static final int ROW_HEIGHT = 20;
    private static final int HEADER_HEIGHT = 30;
    private static final int COLUMN_WIDTH = (TABLE_RIGHT_X - TABLE_LEFT_X) / 4;

    public static byte[] createOrderFormPdf(Order order) {
        try {
            // Create a new PDF document and page
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Add text and other elements to the bill
            addBillHeading(contentStream, order);
            addSupplierBuyerInfo(contentStream, order);
            addItemsTable(contentStream, order);

            // Close the content stream
            contentStream.close();

            // Save and close the PDF document
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static void addBillHeading(PDPageContentStream contentStream, Order order) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset((TABLE_LEFT_X + TABLE_RIGHT_X) / 2, 700);
        contentStream.showText("Indent");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
        contentStream.beginText();
        contentStream.newLineAtOffset((TABLE_LEFT_X + TABLE_RIGHT_X) / 2, 750);
        contentStream.showText("M/s. Tribhovandas Mohanlal Gadhia & Sons");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset((TABLE_LEFT_X + TABLE_RIGHT_X) / 2 + 50, 750 + 24 + 10);
        contentStream.showText("TEXTILE SALES REPRESENTATIVES");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset((TABLE_LEFT_X + TABLE_RIGHT_X) / 2 + 50, 750 + 18 + 10);
        contentStream.showText("104, VASTRALATHA, VIJAYAWADA ");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(350, 760);
        contentStream.showText("Number: " + order.getSeller().getInvoiceCounter());
        contentStream.newLineAtOffset(0, -20);
        //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //        String currentDate = dateFormat.format();
        contentStream.showText("Date: " + order.getDate());
        contentStream.endText();
    }

    private static void addSupplierBuyerInfo(PDPageContentStream contentStream, Order order) throws IOException {
        contentStream.setLineWidth(1f);
        contentStream.addRect(50, 620, 500, 60); // Coordinates and dimensions of the box
        contentStream.stroke();

        String supplierInfo =
            order.getSeller().getBusineessName() + ", " + order.getSeller().getEmail() + ", " + order.getSeller().getPhoneNumber();
        String buyerInfo =
            order.getBuyer().getBusineessName() + ", " + order.getBuyer().getEmail() + ", " + order.getBuyer().getPhoneNumber();

        String[] supplierLines = splitTextIntoLines(supplierInfo, MAX_CHARACTERS_PER_LINE, 500);
        String[] buyerLines = splitTextIntoLines(buyerInfo, MAX_CHARACTERS_PER_LINE, 500);

        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(55, 645);
        for (String line : supplierLines) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -15);
        }
        contentStream.endText();

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(275, 620);
        contentStream.lineTo(275, 680);
        contentStream.stroke();

        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(280, 645);
        for (String line : buyerLines) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -15);
        }
        contentStream.endText();
    }

    private static void addItemsTable(PDPageContentStream contentStream, Order order) throws IOException {
        //        String[] items = {"Item 1", "Item 2", "A larger item name that needs to be split into multiple lines", "Item 4"};
        //        String[] quantities = {"2", "3", "1", "5"};
        String[] prices = { "$10", "$15", "$5", "$8" };
        //        String[] remarks = {"Remark 1", "Remark 2", "A larger remark that needs to be split into multiple lines", "Remark 4"};

        List<Item> itemsSet = order.getItems();

        int numberOfRows = itemsSet.size();

        int[] rowHeights = new int[numberOfRows];

        for (int i = 0; i < numberOfRows; i++) {
            String[] itemLines = splitTextIntoLines(itemsSet.get(i).getName(), MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);
            String[] quantityLines = splitTextIntoLines(itemsSet.get(i).getQuantityType().name(), MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);
            String[] priceLines = splitTextIntoLines(prices[i], MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);
            String[] remarkLines = splitTextIntoLines(itemsSet.get(i).getDescription(), MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);

            int maxItemHeight = itemLines.length * ROW_HEIGHT;
            int maxQuantityHeight = quantityLines.length * ROW_HEIGHT;
            int maxPriceHeight = priceLines.length * ROW_HEIGHT;
            int maxRemarkHeight = remarkLines.length * ROW_HEIGHT;

            rowHeights[i] = Math.max(Math.max(maxItemHeight, maxQuantityHeight), Math.max(maxPriceHeight, maxRemarkHeight));
        }

        int tableWidth = COLUMN_WIDTH + COLUMN_WIDTH + COLUMN_WIDTH + COLUMN_WIDTH;
        int[] columnWidths = { COLUMN_WIDTH, COLUMN_WIDTH, COLUMN_WIDTH, COLUMN_WIDTH };

        while (tableWidth > TABLE_RIGHT_X - TABLE_LEFT_X) {
            int maxRowIndex = getMaxValueIndex(rowHeights);

            columnWidths[maxRowIndex] += 1;
            rowHeights[maxRowIndex] -= ROW_HEIGHT;

            tableWidth = calculateTableWidth(columnWidths);
        }

        contentStream.setLineWidth(1f);
        contentStream.addRect(TABLE_LEFT_X, TABLE_BOTTOM_Y, TABLE_RIGHT_X - TABLE_LEFT_X, TABLE_TOP_Y - TABLE_BOTTOM_Y); // Coordinates and dimensions of the table
        contentStream.stroke();

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(TABLE_LEFT_X + 5, TABLE_TOP_Y - 15);

        contentStream.showText("Item");
        contentStream.newLineAtOffset(columnWidths[0], 0);
        contentStream.showText("Quantity");
        contentStream.newLineAtOffset(columnWidths[1], 0);
        contentStream.showText("Price");
        contentStream.newLineAtOffset(columnWidths[2], 0);
        contentStream.showText("Remark");
        contentStream.endText();

        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(TABLE_LEFT_X, TABLE_TOP_Y - HEADER_HEIGHT);
        contentStream.lineTo(TABLE_RIGHT_X, TABLE_TOP_Y - HEADER_HEIGHT);
        contentStream.stroke();

        contentStream.setFont(PDType1Font.HELVETICA, 12);

        int currentY = TABLE_TOP_Y - HEADER_HEIGHT - ROW_HEIGHT;
        for (int i = 0; i < numberOfRows; i++) {
            int currentX = TABLE_LEFT_X + 5;

            String[] itemLines = splitTextIntoLines(itemsSet.get(i).getName(), MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);
            String[] quantityLines = splitTextIntoLines(itemsSet.get(i).getQuantityType().name(), MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);
            String[] priceLines = splitTextIntoLines(prices[i], MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);
            String[] remarkLines = splitTextIntoLines(itemsSet.get(i).getDescription(), MAX_CHARACTERS_PER_LINE, COLUMN_WIDTH);

            int rowHeight = rowHeights[i];

            contentStream.setLineWidth(0.5f);
            contentStream.addRect(currentX, currentY - rowHeight, columnWidths[0], rowHeight);
            contentStream.stroke();
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, currentY - 12);
            for (String line : itemLines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -ROW_HEIGHT);
            }
            contentStream.endText();

            currentX += columnWidths[0];
            contentStream.setLineWidth(0.5f);
            contentStream.addRect(currentX, currentY - rowHeight, columnWidths[1], rowHeight);
            contentStream.stroke();
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, currentY - 12);
            for (String line : quantityLines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -ROW_HEIGHT);
            }
            contentStream.endText();

            currentX += columnWidths[1];
            contentStream.setLineWidth(0.5f);
            contentStream.addRect(currentX, currentY - rowHeight, columnWidths[2], rowHeight);
            contentStream.stroke();
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, currentY - 12);
            for (String line : priceLines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -ROW_HEIGHT);
            }
            contentStream.endText();

            currentX += columnWidths[2];
            contentStream.setLineWidth(0.5f);
            contentStream.addRect(currentX, currentY - rowHeight, columnWidths[3], rowHeight);
            contentStream.stroke();
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, currentY - 12);
            for (String line : remarkLines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -ROW_HEIGHT);
            }
            contentStream.endText();

            currentY -= rowHeight + ROW_HEIGHT;
        }
    }

    private static int calculateTableWidth(int[] columnWidths) {
        int tableWidth = 0;
        for (int width : columnWidths) {
            tableWidth += width;
        }
        return tableWidth;
    }

    private static int getMaxLineWidth(String[] lines) throws IOException {
        int maxWidth = 0;
        for (String line : lines) {
            int lineWidth = Math.round(PDType1Font.HELVETICA.getStringWidth(line) / 1000f * 12); // Font size 12
            maxWidth = Math.max(maxWidth, lineWidth);
        }
        return maxWidth;
    }

    private static int getMaxValue(int[] array) {
        int maxValue = Integer.MIN_VALUE;
        for (int value : array) {
            maxValue = Math.max(maxValue, value);
        }
        return maxValue;
    }

    private static int getMaxValueIndex(int[] array) {
        int maxValue = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static String[] splitTextIntoLines(String text, int maxCharactersPerLine, int cellWidth) {
        StringBuilder sb = new StringBuilder(text);
        int index = 0;
        int maxCharacters = Math.min(maxCharactersPerLine, cellWidth / 6); // Adjusted maximum characters based on cell width
        while (index + maxCharacters < sb.length() && (index = sb.lastIndexOf(" ", index + maxCharacters)) != -1) {
            sb.replace(index, index + 1, "\n");
        }
        return sb.toString().split("\n");
    }
}
