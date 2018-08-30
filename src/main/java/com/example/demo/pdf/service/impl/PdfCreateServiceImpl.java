package com.example.demo.pdf.service.impl;

import com.example.demo.pdf.service.PdfCreateService;
import com.example.demo.util.DateUtil;
import com.example.demo.util.StringUtil;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import com.lowagie.text.Image;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 16:20 2018/8/17 .
 */
@Service
public class PdfCreateServiceImpl implements PdfCreateService {
    private static final Logger logger = LogManager.getLogger(PdfCreateServiceImpl.class);
    //10号字体
    private final Font FONT_TEN = initFont(10);
    //7号字体
    private final Font FONT_SEVEN = initFont(7);
    //18号字体
    private final Font FONT_EIGHTEEN = initFont(18);
    //字体颜色
    private final Color COLOR = new Color(243, 243, 243);

    @SuppressWarnings("rawtypes")
    @Override
    public void createVoucher(Map map, String tempPath, String basePath) throws Exception {
        Document doc = new Document(PageSize.A4);
        String tempFileName = tempPath  + map.get("SEQUENCEID")+".pdf";
        PdfWriter.getInstance(doc, new FileOutputStream(tempFileName));
        doc.open();

        boolean isNewVoucher = (Boolean) map.get("isNewVoucher");
//		init(isNewVoucher);

        PdfPTable table = initTable(isNewVoucher);

        Paragraph titleP = new Paragraph("凭证\n\n", FONT_EIGHTEEN);
        titleP.setAlignment(Element.ALIGN_CENTER);
        doc.add(titleP);

        addFirstPart(map , FONT_TEN , COLOR , table);
        if(isNewVoucher){
            addOther(map , FONT_TEN , COLOR , table);
        }
        addSecondPart(map , FONT_TEN , COLOR , table);
        doc.add(table);
        doc.close();
        addPhoto(map,tempPath,basePath);

    }

    @Override
    public int createBatchDetailVoucher(List<Map> details, String tempPath, String basePath, String appCode, int nextPageIndex) throws Exception {
        //初始化列
        List<String> columns = initColumnNames();

        int startRow = 0;
        int endRow = 0 ;
        int pageSize = 25;
        String tempFileName ="";
        int rowindex = nextPageIndex * pageSize;

        int vouchersize = details.size();
        int page = vouchersize/25 + (vouchersize % 25 > 0?1:0);
        int count = 0;
        for(int i = 0;i < page ; i++){

            Document doc = null;
            try {
                doc = new Document(PageSize.A4.rotate());
                float[] widths = {3,4,5,9,5,7,8,5,3,5,5,4,3,4,4,3,4,9};
                PdfPTable detailTable = new PdfPTable(widths);
                detailTable.setWidthPercentage(100);

                tempFileName = "detail_"+ nextPageIndex + ".pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(tempPath + tempFileName));
                doc.open();
                endRow = startRow + pageSize ;

                List<Map> subList = new ArrayList<Map>();
                if(i == page - 1){
                    subList = details.subList(startRow, details.size());
                    endRow = details.size();
                }
                else{
                    subList = details.subList(startRow, endRow );
                }

                initSeqNum(subList , startRow , endRow,rowindex);
                startRow = endRow ;
                //初始化head
                initTableHead(detailTable , columns , FONT_SEVEN);

                // 填充数据
                initTableData(detailTable , subList , FONT_SEVEN);
                doc.add(detailTable);
                doc.close();
                //增加pic
                detailAddPhoto(tempFileName,tempPath,basePath);

                nextPageIndex++;
                count++;
            } catch (Exception e) {
                logger.error("create details voucher occurs error ["+tempFileName+"]", e);
            }finally{
                if(doc.isOpen()){
                    doc.close();
                }
            }

        }

        return count;
    }

    /**
     * 将原先init进行拆分，为了解决成员变量高并发问题
     */
    private PdfPTable initTable(boolean isNewVoucher) throws DocumentException, IOException {
        float[] widths = {};
        float[] widthsForWith = { 20, 30, 15, 35 };
        float[] widthsForPay = { 15, 35, 15, 35 };
        if (isNewVoucher) {
            widths = widthsForWith;
        } else {
            widths = widthsForPay;
        }
        PdfPTable table = new PdfPTable(widths);
        table.setWidthPercentage(100);
        return table;
    }

    /**
     * 将原先init进行拆分，为了解决成员变量高并发问题
     * 用于初始化字体
     */
    private Font initFont(float size){
        BaseFont bfComic;
        Font font;
        try {
            bfComic = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            font = new Font(bfComic, size, Font.NORMAL);
            return font;
        }  catch (Exception e) {
            logger.error("--: BaseFont.createFont 异常 ", e);
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private void addFirstPart(Map map , Font font , Color color , PdfPTable table){

        // 添加第一行
        PdfPCell firstCell = new PdfPCell();
        firstCell.setColspan(4);
        firstCell.setPadding(5.0f);
        PdfPTable firstTable = new PdfPTable(4);
        Paragraph para = new Paragraph("商家订单号/批次号:" + StringUtil.null2String(map.get("ORDERID")), font);

        PdfPCell cell = new PdfPCell(para);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5.0f);
        cell.setColspan(3);
        cell.setBorder(0);
        cell.setLeft(0);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Paragraph("交易号:"
                + StringUtil.null2String(map.get("SEQUENCEID")), font));
        cell.setBorder(0);
        firstTable.addCell(cell);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        firstCell.addElement(firstTable);
        table.addCell(firstCell);

        // 添加第二行
        cell = new PdfPCell(new Paragraph("付      款     方", font));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("收      款     方", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);

        // 添加第三行
        cell = new PdfPCell(new Paragraph("名 称：", font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(color);
        table.addCell(cell);

        String payer = "";
        if (StringUtil.isNull(map.get("PAYER_NAME"))) {
            payer = StringUtil.null2String(map.get("PAYER_DISPLAYNAME"));
        } else {
            payer = StringUtil.null2String(map.get("PAYER_NAME"));
        }

        cell = new PdfPCell(new Paragraph(payer, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("名 称：", font));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        String payee = "";
        if (StringUtil.isNull(map.get("BANKACCTID"))) {

            if (StringUtil.isNull(map.get("PAYEE_NAME"))) {
                payee = StringUtil.null2String(map.get("PAYEE_NAME"));
            } else {
                payee = StringUtil.null2String(map.get("PAYEE_DISPLAYNAME"));
            }
        } else {
            payee = StringUtil.null2String(map.get("NAME"));
        }

        cell = new PdfPCell(new Paragraph(payee, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        // 添加第四行
        cell = new PdfPCell(new Paragraph("本地账户：", font));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(StringUtil.null2String(map
                .get("PAYER_IDC")), font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("银行账户：", font));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        String bankAcctId = "";
        if (StringUtil.isNull(map.get("BANKACCTID"))) {
            bankAcctId = StringUtil.null2String(map.get("PAYEE_IDC"));
        } else {
            bankAcctId = StringUtil.null2String(map.get("BANKACCTID"));
        }
        cell = new PdfPCell(new Paragraph(bankAcctId, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

    }

    @SuppressWarnings("rawtypes")
    public  void addSecondPart(Map map , Font font , Color color , PdfPTable table){

        DecimalFormat df = new DecimalFormat("#0.00");
        //添加第五行
        PdfPCell cell = new PdfPCell (new Paragraph ("订单金额：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setBackgroundColor(color );
        table.addCell (cell);
        cell = new PdfPCell (new Paragraph (StringUtil.null2String(map.get("strOrderAmount")),font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);

        cell = new PdfPCell (new Paragraph ("小写：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);
        String orderAmountNum ="0.00";
        if(map.get("ORDERAMOUNT") != null){
            orderAmountNum = StringUtil.null2String(map.get("ORDERAMOUNT"));
            orderAmountNum = df.format(new BigDecimal(orderAmountNum));
        }
        cell = new PdfPCell (new Paragraph("￥"+orderAmountNum,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);



        //添加第六行
        cell = new PdfPCell (new Paragraph ("费 用：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(color );
        table.addCell (cell);

        cell = new PdfPCell (new Paragraph (StringUtil.null2String(map.get("strPayerFee")),font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);

        cell = new PdfPCell (new Paragraph ("小写：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);
        String payFee=  StringUtil.null2String(map.get("payFee"));

        payFee = df.format(new BigDecimal(payFee));
        cell = new PdfPCell (new Paragraph ("￥"+ payFee,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);

        //添加第七行
        cell = new PdfPCell (new Paragraph ("出账金额：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(color );
        table.addCell (cell);
        cell = new PdfPCell (new Paragraph (StringUtil.null2String(map.get("strTotalAmount")),font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);
        cell = new PdfPCell (new Paragraph ("小写：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);
        String totalAmountNum ="0.00";
        if(map.get("totalAmount") != null){
            totalAmountNum = StringUtil.null2String(map.get("totalAmount"));
            totalAmountNum = df.format(new BigDecimal(totalAmountNum));
        }
        cell = new PdfPCell (new Paragraph ("￥"+ totalAmountNum,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);
        //添加第八行

        cell = new PdfPCell (new Paragraph ("账户类型：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(color );
        table.addCell (cell);

        PdfPCell accountTypeCell = new PdfPCell (new Paragraph (StringUtil.null2String(map.get("MEMBERACCTNAME")),font));
        accountTypeCell.setColspan(3);
        accountTypeCell.setPadding(5.0f);
        accountTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (accountTypeCell);
        //添加第九行
        cell = new PdfPCell (new Paragraph ("交易类型：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(color );
        table.addCell (cell);
        table.addCell (new Paragraph (StringUtil.null2String(map.get("ORDERTYPENAME")),font));

        cell = new PdfPCell (new Paragraph ("交易时间：",font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(color );
        table.addCell (cell);
        String orderDate =  DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", (Date) map.get("ORDERTIME"));
        table.addCell (new Paragraph (orderDate ,font));

        //添加第十行
        Paragraph memo =new Paragraph ("备注："+StringUtil.null2String(map.get("MEMO")),font);
        PdfPCell memoCell = new PdfPCell (memo);
        memoCell.setPadding(5.0f);
        memoCell.setColspan(3);
        table.addCell (memoCell);
        table.addCell (new Paragraph ("盖章：",font));
        //---------------------------------------
        String tip ="提示：本回单与我司原始记录不符的，以我司原始记录为准；由于系统原因或故障，而导致的回单或对账单与客户实际交易不符的，以客户实际交易为准。";
        Paragraph para = new Paragraph (tip,font);
        cell = new PdfPCell (para);
        cell.setColspan(4);
        table.addCell(cell);
    }

    @SuppressWarnings("rawtypes")
    public  void addOther(Map map , Font font , Color color , PdfPTable table){

        // 附加第一行
        PdfPCell  cell = new PdfPCell (new Paragraph ("协议代付方户名：",font));
        cell.setBackgroundColor(color );
        cell.setPadding(5.0f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);
        String accountName ="";
        if(StringUtil.isNull(map.get("ACCOUNTNAME"))){
            accountName= StringUtil.null2String(map.get("PAYER_DISPLAYNAME"));
        }else{
            accountName = StringUtil.null2String(map.get("ACCOUNTNAME"));
        }
        cell = new PdfPCell (new Paragraph(accountName,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);

        cell = new PdfPCell (new Paragraph ("开户行：",font));
        cell.setBackgroundColor(color );
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);
        String   destbranchbankname ="";
        if(StringUtil.isNull(map.get("DESTBRANCHBANKNAME"))){
            destbranchbankname= StringUtil.null2String(map.get("PAYEE_DISPLAYNAME"));
        }else{
            destbranchbankname = StringUtil.null2String(map.get("DESTBRANCHBANKNAME"));
        }
        cell = new PdfPCell (new Paragraph(destbranchbankname,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);
        // 附加第二行
        cell = new PdfPCell (new Paragraph ("协议代付方开户行：",font));
        cell.setBackgroundColor(color );
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);

        String    orignalBankName="";
        if(StringUtil.isNull(map.get("ORIGINALBANKNAME"))){
            orignalBankName= StringUtil.null2String(map.get("PAYER_DISPLAYNAME"));
        }else{
            orignalBankName = StringUtil.null2String(map.get("ORIGINALBANKNAME"));
        }
        cell = new PdfPCell (new Paragraph(orignalBankName,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);
        cell = new PdfPCell ();
        cell.setColspan(2);
        table.addCell (cell);
        // 附加第三行
        cell = new PdfPCell (new Paragraph ("协议代付方账号：",font));
        cell.setBackgroundColor(color );
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell (cell);

        String    oriBankAcct="";
        if(StringUtil.isNull(map.get("ORIBANKACCT"))){
            oriBankAcct= StringUtil.null2String(map.get("PAYER_DISPLAYNAME"));
        }else{
            oriBankAcct = StringUtil.null2String(map.get("ORIBANKACCT"));
        }
        cell = new PdfPCell (new Paragraph(oriBankAcct,font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell (cell);
        cell = new PdfPCell ();
        cell.setColspan(2);
        table.addCell (cell);
    }

    @SuppressWarnings("rawtypes")
    public static void addPhoto(Map map,String tempPath,String basePath) throws IOException, DocumentException {

        String tempFileName = tempPath+map.get("SEQUENCEID")+".pdf";
        PdfReader reader = new PdfReader(tempFileName);

        String orderDate =  DateUtil.formatDateTime("yyyy-MM-dd", (Date) map.get("ORDERTIME"));
        String voucherName = genatePdfName(map, orderDate);

        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(basePath+voucherName));
        Image photo = Image.getInstance(PdfCreateServiceImpl.class.getResource("/image/paige.jpg"));
        photo.scaleToFit(70, 70);
        boolean isNewVoucher = (Boolean) map.get("isNewVoucher");
        if (isNewVoucher) {
            photo.setAbsolutePosition(430, 480);
        } else {
            photo.setAbsolutePosition(430, 530);
        }

        photo.setAlignment(Image.TEXTWRAP);

        PdfContentByte over = stamp.getOverContent(1);
        over.addImage(photo);
        stamp.close();
    }

    @SuppressWarnings("rawtypes")
    private static String genatePdfName(Map map, String orderDate) throws UnsupportedEncodingException {

        //在现有的文件名规则下增加后缀
        String fileNameAppend = getFileNameAppend(0, "test", (String) map.get("MEMO"));

        String voucherName ="";
        String orderid =  StringUtil.null2String(map.get("ORDERID")) ;
        String amount = map.get("ORDERAMOUNT").toString();

        voucherName = orderDate+"_"+orderid+"_"+amount+"_"+map.get("SEQUENCEID");

        if(!StringUtil.isEmpty(fileNameAppend)){
            voucherName = voucherName + "_"+StringUtil.null2String(fileNameAppend);
        }

        //长度限制一下
        if(voucherName.length()>200){
            voucherName = voucherName.substring(0,200);
        }
        voucherName += ".pdf";
        voucherName = new String(voucherName.getBytes("UTF-8"),"UTF-8");
        logger.info(voucherName);
        return voucherName;
    }

    //文件名加后缀
    private static String getFileNameAppend(Integer isCustomize, String filenameAppend, String memo) {
        String rtnName = filenameAppend;
        if(isCustomize == null || isCustomize == 0 || StringUtil.isEmpty(filenameAppend) || StringUtil.isEmpty(memo)){
            return null;
        }

        //替换MEMO
        Pattern p = Pattern.compile(".*?((\\{.+?\\})|(｛.+?｝))");//找到{}的内容
        Matcher m = p.matcher(filenameAppend);
        while(m.find()) {
            String finder = m.group(1);//匹配的数据
            String pureStr = finder.replaceAll("[{,},｛,｝, ]", "");//括号中的数据
            logger.info("--:finder="+finder+";pureStr =" + pureStr);
            //商户号替换
            if("memo".equalsIgnoreCase(pureStr)){
                rtnName = filenameAppend.replace(finder, memo);
                continue;
            }
        }

        //只保留数字、字母和下划线
        p = Pattern.compile("\\W");
        rtnName = p.matcher(rtnName).replaceAll("");

        return rtnName;
    }

    private List<String> initColumnNames() {

        List<String> columns = new ArrayList<String>();

        columns.add("序号");
        columns.add("交易号");
        columns.add("付款账号");
        columns.add("姓名");
        columns.add("银行名称");
        columns.add("银行账号");
        columns.add("开户行名称");
        columns.add("交易金额");
        columns.add("手续费");
        columns.add("提交时间");
        columns.add("结束时间");
        columns.add("交易类型");
        columns.add("交易状态");
        columns.add("批次");
        columns.add("失败原因");
        columns.add("错误代码");
        columns.add("原始交易号");
        columns.add("备注");

        return columns;
    }

    private void initSeqNum(List<Map> subList , int startRow ,int  endRow,int rowindex) {
        int index = 0;
        for(int i = startRow ; i< endRow;i++ ){
            Map map = subList.get(index++);
            map.put("ROWNUM", rowindex + i + 1);
        }
    }

    private void initTableHead(PdfPTable detailTable, List<String> columns , Font font) {

        for(int i = 0 ; i < columns.size() ; i++){
            PdfPCell cell = new PdfPCell(new Paragraph(columns.get(i), font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            detailTable.addCell(cell);
        }

    }

    private void initTableData(PdfPTable detailTable , List<Map> details, Font font) {
        DecimalFormat df = new DecimalFormat("#0.00");
        for(int row = 0; row <details.size() ; row++){
            Map dataMap = details.get(row);
            if(dataMap == null){
                break;
            }
            for(Object value : dataMap.values()){
                PdfPCell cell = new PdfPCell(new Paragraph(StringUtil.null2String(value), font));
                //设置单元格行高
                cell.setFixedHeight(20);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                detailTable.addCell(cell);

            }
//            for(int i = 0; i < 20;i++){//加入memo，因此共20列,第十九列是PRI，第二十列是MEMO
//                if(dataMap == null){
//                    break;
//                }
//                Object data =  dataMap.entrySet().toArray()[i];
//                if(i == 7 || i == 8){
//                    data = df.format(new BigDecimal(data.toString()));
//                }else if(i == 17){
//
//                }else if(i == 18){
//                    continue;
//                }else if(i == 19){
//
//                }
//                PdfPCell cell = new PdfPCell(new Paragraph(StringUtil.null2String(data), font));
//                //设置单元格行高
//                cell.setFixedHeight(20);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setVerticalAlignment(Element.ALIGN_CENTER);
//                detailTable.addCell(cell);
//            }
        }
    }

    private void detailAddPhoto(String fileName, String tempPath, String basePath) throws Exception {
        //读取原文件

        String tempFileName = tempPath + fileName;
        PdfReader reader = new PdfReader(tempFileName);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(basePath+fileName));
        Image photo = Image.getInstance( PdfCreateServiceImpl.class.getResource("/image/paige.jpg"));
        photo.scaleToFit(100, 100);
        //是否设置印章位置
        //默认印章位置
        photo.setAbsolutePosition(730, 50);
        photo.setAlignment(Image.TEXTWRAP);
        PdfContentByte over = stamp.getOverContent(1);
        over.addImage(photo);
        stamp.close();
    }

    //是否设置印章位置
    private boolean isSetBadgeAbsPosition(Integer isCustomize, String badgeAbsposition) {
        if(isCustomize == null || isCustomize == 0){
            return false;
        }else if(StringUtil.isEmpty(badgeAbsposition)){
            return false;
        }
        return true;
    }






}
