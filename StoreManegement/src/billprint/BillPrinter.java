package billprint;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;

import java.awt.Color;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import model.Order;
import model.OrderDetail;
import model.Product;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.FileOutputStream;

public class BillPrinter {
    public BillPrinter() {
    }

    public static void billPrint(Order order, String filePath) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            BaseFont bf = BaseFont.createFont("billprint/vuArial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 12);
            Font font1 = new Font(bf, 12, Font.BOLD);
            Font font2 = new Font(bf, 16, Font.BOLD, new BaseColor(new Color(255, 189, 68).getRGB()));
            Image logo = Image.getInstance("src/icon/Logo.png");
            logo.scaleAbsolute(100, 100);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
            Paragraph ten_quan = new Paragraph("BK SHOP", font2);
            ten_quan.setAlignment(Element.ALIGN_CENTER);
            document.add(ten_quan);
            Paragraph dia_chi = new Paragraph("D9 - Đại Học Bách Khoa Hà Nội", font2);
            dia_chi.setAlignment(Element.ALIGN_CENTER);
            document.add(dia_chi);
            Paragraph title = new Paragraph("---------------HÓA ĐƠN BÁN HÀNG---------------", font1);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(20);
            title.setSpacingAfter(5);
            document.add(title);
            Paragraph id = new Paragraph(order.getOrderID(), font1);
            id.setAlignment(Element.ALIGN_CENTER);
            id.setSpacingAfter(15);
            document.add(id);
            PdfPTable table = new PdfPTable(5);
            PdfPCell cus = new PdfPCell(new Phrase("Khách hàng: " + (order.isHaveCard() ? order.getCustomer().getCustomerName() : ""), font));
            cus.setColspan(5);
            cus.setBorder(Rectangle.TOP);
            cus.setBorderWidth(1);
            cus.setPaddingTop(10);
            table.addCell(cus);
            PdfPCell emp = new PdfPCell(new Phrase("Nhân viên xuất hóa đơn: " + order.getUser().getUserName(), font));
            emp.setColspan(5);
            emp.setBorder(Rectangle.NO_BORDER);
            table.addCell(emp);
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            Date dateO = sdf.parse(order.getDate());
            sdf = new SimpleDateFormat("dd-MM-yyyy");
            PdfPCell date = new PdfPCell(new Phrase("Ngày bán: " + sdf.format(dateO), font));
            date.setBorder(Rectangle.BOTTOM);
            date.setColspan(5);
            date.setPaddingBottom(10);
            table.addCell(date);
            PdfPCell cell1 = new PdfPCell(new Phrase("Tên hàng", font1));
            PdfPCell cell2 = new PdfPCell(new Phrase("Số lượng", font1));
            PdfPCell cell3 = new PdfPCell(new Phrase("Đơn giá", font1));
            PdfPCell cell4 = new PdfPCell(new Phrase("Thành tiền", font1));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setColspan(2);
            cell1.setPaddingTop(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            cell2.setPaddingTop(10);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell3.setPaddingTop(10);
            cell3.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            cell4.setBorder(Rectangle.NO_BORDER);
            cell4.setPaddingTop(10);
            cell4.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            //Add orderDetail
            List<OrderDetail> ods = order.getOrderDetails();
            NumberFormat nf = new DecimalFormat("###,###,###");
            for (OrderDetail od : ods) {
                Product p = od.getProduct();
                cell1 = new PdfPCell(new Phrase(p.getProductName(), font));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell2 = new PdfPCell(new Phrase(String.valueOf(od.getQuantity()), font));
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                cell3 = new PdfPCell(new Phrase(nf.format(p.getCost()), font));
                cell3.setBorder(Rectangle.NO_BORDER);
                cell3.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                cell4 = new PdfPCell(new Phrase(nf.format(od.getCost()), font));
                cell4.setBorder(Rectangle.NO_BORDER);
                cell4.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                cell1.setPadding(5);
                cell2.setPadding(5);
                cell3.setPadding(5);
                cell4.setPadding(5);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }
            PdfPCell line = new PdfPCell(new Phrase("-------------------------------------------------------------------------------------------------------", font));
            line.setColspan(5);
            line.setBorder(Rectangle.NO_BORDER);
            table.addCell(line);

            PdfPCell totalCost = new PdfPCell(new Phrase("Tổng tiền hàng: ", font1));
            totalCost.setColspan(3);
            totalCost.setPaddingTop(10);
            totalCost.setBorder(Rectangle.NO_BORDER);
            table.addCell(totalCost);
            PdfPCell totalCostV = new PdfPCell(new Phrase(nf.format(order.getTotalCost()), font));
            totalCostV.setColspan(2);
            totalCostV.setBorder(Rectangle.NO_BORDER);
            totalCostV.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            table.addCell(totalCostV);

            PdfPCell discount = new PdfPCell(new Phrase("Giảm giá: ", font1));
            discount.setColspan(3);
            discount.setBorder(Rectangle.NO_BORDER);
            table.addCell(discount);
            PdfPCell discountV = new PdfPCell(new Phrase(String.format("%d%c", order.getDiscount(), '%'), font));
            discountV.setColspan(2);
            discountV.setBorder(Rectangle.NO_BORDER);
            discountV.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            table.addCell(discountV);

            PdfPCell point = new PdfPCell(new Phrase("Điểm sử dụng: ", font1));
            point.setColspan(3);
            point.setBorder(Rectangle.NO_BORDER);
            table.addCell(point);
            PdfPCell pointV = new PdfPCell(new Phrase(String.valueOf(order.getUsedPoints()), font));
            pointV.setColspan(2);
            pointV.setBorder(Rectangle.NO_BORDER);
            pointV.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            table.addCell(pointV);

            PdfPCell total = new PdfPCell(new Phrase("Tổng tiền thanh toán: ", font1));
            total.setColspan(3);
            total.setBorder(Rectangle.NO_BORDER);
            table.addCell(total);
            PdfPCell totalV = new PdfPCell(new Phrase(nf.format(order.getTotal()), font));
            totalV.setColspan(2);
            totalV.setBorder(Rectangle.NO_BORDER);
            totalV.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            table.addCell(totalV);
            document.add(table);
            Paragraph thanks = new Paragraph("Cảm ơn quý khách, chúc quý khách một ngày tốt lành!", font);
            thanks.setAlignment(Element.ALIGN_CENTER);
            thanks.setSpacingBefore(20);
            document.add(thanks);
            document.close();
            writer.close();
        } catch (DocumentException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
