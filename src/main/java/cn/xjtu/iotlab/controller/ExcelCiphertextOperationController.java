package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.utils.ExcelEncDecUtil;
import cn.xjtu.iotlab.utils.encdec.OPEART;
import cn.xjtu.iotlab.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("excelOperation")
public class ExcelCiphertextOperationController {
    private String head0 = ExcelEncDecUtil.head0;
    private long opeart_k = ExcelEncDecUtil.opeart_k;

    // 保序关键字加密
    @ResponseBody
    @RequestMapping(value = "/opeKeyEncrypt", method = RequestMethod.POST)
    public void OpeKeyEncrypt(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String fileParentPath = req.getParameter("fileParentPath");
//        String[] outPath = filePath.split("/");
//        String path = "";
//        for(int i=0;i<outPath.length-1;i++){
//            path+=outPath[i];
//        }

        String opeEncInfo = req.getParameter("opeEncInfo");
        String opeProperty = req.getParameter("opeProperty");
        String opeKey = req.getParameter("opeKey");
        String type = req.getParameter("type");

        try { //数字，excel表格自动将int转化为double，所以这里也要转化
            double doubvalue=Double.valueOf(opeKey);
            opeKey=""+doubvalue;
            opeKey=ExcelEncDecUtil.head0.substring(opeKey.length())+opeKey;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            OPEART op=new OPEART(ExcelEncDecUtil.opeart_k);
            String encvalue=op.encrypt(opeKey);

            //写入excel表格中
            FileOutputStream fos = new FileOutputStream(fileParentPath+"\\OPEART_ENC.xls");
            HSSFWorkbook wb2 = new HSSFWorkbook();
            HSSFSheet s = wb2.createSheet(); //表
            wb2.setSheetName(0, "first sheet"); //设置第一个表名为 first sheet
            HSSFRow row2 = s.createRow(0); //第一行
            HSSFCell cell2 = row2.createCell(0);//第一列
            cell2.setCellValue(encvalue);
            wb2.write(fos);
            fos.close();
            //System.out.println(encvalue);
    //        String baoxushuxing=box44.getSelectedItem().toString();
            //System.out.println(baoxushuxing);
    //        texta46.append(baoxushuxing+";"+1+";"+encvalue+"\n");  //1 是保序关键字，2是双边界，3是下边界，4是上边界
            if(opeEncInfo.equals("null")){
                opeEncInfo = (opeProperty+";"+1+";"+encvalue+"#");
            } else {
                opeEncInfo = opeEncInfo + (opeProperty+";"+1+";"+encvalue+"#");
            }
            String result = ("保序关键字加密成功,加密关键字保存在 " + fileParentPath + "OPEART_ENC.xls中，可以继续选择保序属性进行加密");
            System.out.println(result);
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("code",1);
            jsonObject.put("result",result);
            jsonObject.put("opeEncInfo",opeEncInfo);

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    // 保序范围加密
    @ResponseBody
    @RequestMapping(value = "/opePropertyEncrypt", method = RequestMethod.POST)
    public void OpePropertyEncrypt(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String fileParentPath = req.getParameter("fileParentPath");

        String fileOutputPath = fileParentPath+"OPEART_ENC.xls";
        String opeProperty = req.getParameter("opeProperty");
        String low = req.getParameter("opeLowBound");
        String up = req.getParameter("opeUpBound");
        String type = req.getParameter("type");
        String opeEncInfo = req.getParameter("opeEncInfo");


        OPEART op=new OPEART(ExcelEncDecUtil.opeart_k);

        String result = "";

        int intboudn=0;
        if(!((low==null) || (low.equals("null"))) && !((up==null)||(up.equals("null"))))//双边界
            intboudn=3;
        else if(!((low==null) || (low.equals("null")))) //有下边界
            intboudn=2;
        else if(!((up==null)||(up.equals("null"))))  //上边界
            intboudn=1;

        if(intboudn==3){ //双边界
            try { //数字，excel表格自动将int转化为double，所以这里也要转化
                double doubvalue=Double.valueOf(low);
                low=""+doubvalue;
                low=ExcelEncDecUtil.head0.substring(low.length())+low;
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            try {
                double doubvalue=Double.valueOf(up);
                up=""+doubvalue;
                up=head0.substring(up.length())+up;
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            try {
                // OPEART op=new OPEART(opeart_k);

                String enclow=op.encrypt(low);
                String encup=op.encrypt(up);
                //写入excel表格中
                FileOutputStream fos = new FileOutputStream(fileOutputPath);
                HSSFWorkbook wb2 = new HSSFWorkbook();
                HSSFSheet s = wb2.createSheet(); //表
                wb2.setSheetName(0, "first sheet"); //设置第一个表名为 first sheet
                HSSFRow row2 = s.createRow(0); //第一行
                HSSFCell cell2 = row2.createCell(0);//第一列
                cell2.setCellValue(enclow);
                cell2 = row2.createCell(1);//第二列
                cell2.setCellValue(encup);
                wb2.write(fos);
                fos.close();

//                String baoxushuxing=box44.getSelectedItem().toString();
//                //System.out.println(baoxushuxing);
//                texta46.append(baoxushuxing+";"+2+";"+enclow+","+encup+"\n");
                if(opeEncInfo.equals("null")){
                    opeEncInfo = (opeProperty+";"+2+";"+enclow+","+encup+"#");
                } else {
                    opeEncInfo = opeEncInfo + (opeProperty+";"+2+";"+enclow+","+encup+"#");
                }
                result = "保序范围加密成功,加密范围保存在" + fileOutputPath +"中，可以继续选择保序属性进行加密";
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else if(intboudn==2){//下边界
            try { //数字，excel表格自动将int转化为double，所以这里也要转化
                double doubvalue=Double.valueOf(low);
                low=""+doubvalue;
                low=head0.substring(low.length())+low;
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            try {
                //OPEART op=new OPEART(opeart_k);
                String enclow=op.encrypt(low);
                //写入excel表格中
                FileOutputStream fos = new FileOutputStream(fileOutputPath);
                HSSFWorkbook wb2 = new HSSFWorkbook();
                HSSFSheet s = wb2.createSheet(); //表
                wb2.setSheetName(0, "first sheet"); //设置第一个表名为 first sheet
                HSSFRow row2 = s.createRow(0); //第一行
                HSSFCell cell2 = row2.createCell(0);//第一列
                cell2.setCellValue(enclow);
                wb2.write(fos);
                fos.close();
//                String baoxushuxing=box44.getSelectedItem().toString();
//                //System.out.println(baoxushuxing);
//                texta46.append(baoxushuxing+";"+3+";"+enclow+"\n");
                if(opeEncInfo.equals("null")){
                    opeEncInfo = (opeProperty+";"+3+";"+enclow+"#");
                } else {
                    opeEncInfo = opeEncInfo + (opeProperty+";"+3+";"+enclow+"#");
                }
                result = "保序范围加密成功,加密范围保存在" + fileOutputPath +"中，可以继续选择保序属性进行加密";
            }
            catch(Exception ex){
                ex.printStackTrace(); }
        }
        else if(intboudn==1){//上边界
            try {
                double doubvalue=Double.valueOf(up);
                up=""+doubvalue;
                up=head0.substring(up.length())+up;
            }
            catch(Exception ex){
                ex.printStackTrace(); }
            try {
                // OPEART op=new OPEART(opeart_k);
                String encup=op.encrypt(up);

                //写入excel表格中
                FileOutputStream fos = new FileOutputStream(fileOutputPath);
                HSSFWorkbook wb2 = new HSSFWorkbook();
                HSSFSheet s = wb2.createSheet(); //表
                wb2.setSheetName(0, "first sheet"); //设置第一个表名为 first sheet
                HSSFRow row2 = s.createRow(0); //第一行
                HSSFCell cell2 = row2.createCell(1);//第二列
                cell2.setCellValue(encup);
                wb2.write(fos);
                fos.close();
//                    String baoxushuxing=box44.getSelectedItem().toString();
                //System.out.println(baoxushuxing);
//                .append(baoxushuxing+";"+4+";"+encup+"\n");
                if(opeEncInfo.equals("null")){
                    opeEncInfo = (opeProperty+";"+4+";"+encup+"#");
                } else {
                    opeEncInfo = opeEncInfo + (opeProperty+";"+4+";"+encup+"#");
                }
                result = "保序范围加密成功,加密范围保存在" + fileOutputPath +"中，可以继续选择保序属性进行加密";
            }
            catch(Exception ex){
                ex.printStackTrace();  }
        }

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("result",result);
        jsonObject.put("opeEncInfo",opeEncInfo);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    // 关系运算
    @ResponseBody
    @RequestMapping(value = "/relationComputation", method = RequestMethod.POST)
    public void RelationComputation(HttpServletRequest req, HttpServletResponse response) throws IOException {

        String encinfo = req.getParameter("opeEncInfo");
        String result = "";

        String fileParentPath = req.getParameter("fileParentPath");
        String filename = req.getParameter("filePath");
        String file_enc_name = "Relation_search_result.xls";

        JSONObject jsonObject = new JSONObject();

        String infoarr[]=encinfo.split("#");//保密属性的个数
        int infoarrlength=infoarr.length;

        int shuxingarrno[]=new int[infoarr.length];//属性的在文件中的位置序号
        int yunsuantype[]=new int[infoarr.length];//保序运算的类型，关键字，范围等
        String enckey[]=new String[infoarr.length];//关键字
        System.out.println("infoarr.length  "+infoarr.length);

        for(int i=0;i<infoarr.length;i++){
            String tmp[]=infoarr[i].split(";");
            shuxingarrno[i]=Integer.parseInt(tmp[0].substring(0,tmp[0].indexOf(":")));
            yunsuantype[i]=Integer.parseInt(tmp[1]);
            enckey[i]=tmp[2];
        }
        int count=0;
        try {
            FileInputStream finput = new FileInputStream(filename);
            POIFSFileSystem fs = new POIFSFileSystem(finput);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row ;
            HSSFCell cell;
            int rsRows = sheet.getLastRowNum();
            row = sheet.getRow(0);
            int rsColumns =row.getLastCellNum();
            String[] title=new String[rsColumns];
            for(int i=0;i<rsColumns;i++){
                cell   = row.getCell(i);
                String getexcle="";
                if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
                    getexcle=cell.getStringCellValue();
                    title[i]=getexcle;
                }
                else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
                    getexcle=""+cell.getNumericCellValue();
                    title[i]=getexcle;
                }
            }
            File newfile=new File(fileParentPath+file_enc_name);
            if(newfile.exists()){
                newfile.delete();
                newfile.createNewFile();
            }
            else{
                newfile.createNewFile();
            }
            FileOutputStream fos2=new FileOutputStream(fileParentPath+file_enc_name);
            HSSFWorkbook wb2 = new HSSFWorkbook();
            HSSFSheet sheet2 = wb2.createSheet();
            wb2.setSheetName(0,"first sheet");
            HSSFRow row2 = sheet2.createRow(0);
            HSSFCell cell2 ;
            for(int i=0;i< title.length;i++){
                cell2=row2.createCell(i);
                cell2.setCellValue(title[i]);
            }
            for(int i=1;i<rsRows;i++){
                String[] tmp=new String[rsColumns];
                row=sheet.getRow(i);
                boolean isfound=true;
                for(int j=0;j<infoarrlength;j++){
                    cell=row.getCell(shuxingarrno[j]);
                    String getexcle="";
                    getexcle=cell.getStringCellValue();
                    if(yunsuantype[j]==1){//关键字
                        if(getexcle.equals(enckey[j])){
                            continue;
                        }else {
                            isfound=false;
                            break;
                        }
                    }
                    else if(yunsuantype[j]==2){//范围
                        String lowuparr[]=enckey[j].split(",");
                        String enc_low=lowuparr[0];
                        String enc_up=lowuparr[1];
                        int left=getexcle.compareTo(enc_low);
                        int right=enc_up.compareTo(getexcle);
                        if(left>=0 && right>=0){
                            continue;
                        }else {
                            isfound=false;
                            break;
                        }
                    }
                    else if(yunsuantype[j]==3){//下边界
                        int left=getexcle.compareTo(enckey[j]);
                        if(left>=0){
                            continue;
                        }else {
                            isfound=false;
                            break;
                        }
                    }
                    else if(yunsuantype[j]==4){//上边界
                        int right=enckey[j].compareTo(getexcle);
                        if(right>=0){
                            continue;
                        }else {
                            isfound=false;
                            break;
                        }
                    }
                }
                if(isfound){
                    count++;
                    row2=sheet2.createRow(count);
                    for(int j=0;j<rsColumns;j++){
                        cell=row.getCell(j);
                        cell2=row2.createCell(j);
                        if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
                            cell2.setCellValue(cell.getStringCellValue());
                        }
                        else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
                            cell2.setCellValue(cell.getNumericCellValue());
                        }
                    }
                }
                else{
                }
            }
            wb2.write(fos2);
            fos2.close();

            String num=""+count;
            jsonObject.put("code", 1);

            result = "关系运算成功，共查询到"+num+" 条记录.\n结果保存在根目录下的 Relation_search_result.xls 下，请解密查看";

        } catch (IOException ex) {
            jsonObject.put("code", -1);


            ex.printStackTrace();
        }
        System.out.println(result);

        jsonObject.put("result", result);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    // 算术关键字加密
    @ResponseBody
    @RequestMapping(value = "/arithKeyEncrypt", method = RequestMethod.POST)
    public Object arithKeyEncrypt(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //算术运算
    @ResponseBody
    @RequestMapping(value = "/arithEncrypt", method = RequestMethod.POST)
    public Object arithEncrypt(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //打开文件
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(HttpServletResponse response, HttpSession session) throws FileNotFoundException {
        Frame frame = new Frame();

        FileDialog dialog = new FileDialog(frame, "打开", FileDialog.LOAD);

        dialog.setVisible(true);

        System.out.println(dialog.getDirectory());
        String parentPath = dialog.getDirectory();
        String filename = dialog.getFile();
        String filePath = parentPath+"\\"+filename;
        String name = parentPath + filename;

        String fileinfo="";

        File selectedFile=new File(filePath);
        if (selectedFile.exists()) { //文件存在时
            try {
                FileInputStream finput = new FileInputStream(selectedFile);
                POIFSFileSystem fs = new POIFSFileSystem(finput);//Excel读入系统
                HSSFWorkbook wb = new HSSFWorkbook(fs);//获取Excel文件
                HSSFSheet sheet = wb.getSheetAt(0);//获取Excel文件的第一个工作页面
                HSSFRow row = sheet.getRow(0);//第一行

                int rsColumns =row.getLastCellNum();//获取Sheet表中所包含的总列数
                int rsRows = sheet.getLastRowNum();
                java.util.List<String> list = new ArrayList<>();
                // 获取表头
                for(int i=0;i<rsColumns;i++) {
                    HSSFCell cell = row.getCell(i);//得到第一行的各列单元格，即属性
                    String getexcle=cell.getStringCellValue();
                    fileinfo=fileinfo+getexcle+";";
                    list.add(getexcle);
                }
                finput.close();
                String filedata = "";
                String oldfileinfo=fileinfo+rsRows+"行记录";
                String[] infoarr=fileinfo.split(";");

                // 获取数据
                List<LinkedHashMap> lists = new ArrayList<>();
                for(int i=1;i<Math.min(rsRows,5);i++) {
                    LinkedHashMap<String,String> map = new LinkedHashMap<>();
                    for(int j=0;j<rsColumns;j++){
                        row = sheet.getRow(i);//第一行
                        HSSFCell cell = row.getCell(j);//得到第一行的各列单元格，即属性
                        if (cell.getCellTypeEnum()== CellType.NUMERIC) {
                            Double getexcle = cell.getNumericCellValue();
                            filedata=filedata+getexcle+";";
                            map.put(infoarr[j],getexcle.toString());
                        } else {
                            String getexcle=cell.getStringCellValue();
                            filedata=filedata+getexcle+";";
                            map.put(infoarr[j],getexcle);
                        }
                    }
                    lists.add(map);
                }

                String[] fileData=filedata.split(";");
                String[] oldinfoarr=oldfileinfo.split(";");
                int excelLine=Integer.parseInt(oldinfoarr[oldinfoarr.length-1].substring(0,oldinfoarr[oldinfoarr.length-1].indexOf("行")));//行数
                System.out.println("excelLine "+excelLine);

                JSONObject jsonObject= (JSONObject) JSONObject.toJSON(new Result(1,infoarr,fileData));
                jsonObject.put("tableHead",infoarr);
                jsonObject.put("code",1);
                jsonObject.put("filename",name);
//                jsonObject.put("tableData", JSON.toJSONString(lists));
//                jsonObject.put("sheet",sheet);
                ArrayList<String> opes = new ArrayList<>();
                ArrayList<String> arith = new ArrayList<>();

                for(int i=0;i<infoarr.length;i++){
                    String tmp=infoarr[i];//1@#ID
                    if(tmp.startsWith("1@#")){
                        opes.add(""+i+":"+tmp.substring(3));
                    }
                    else if(tmp.startsWith("2@#")){
                        arith.add(""+i+":"+tmp.substring(3));
                    }
                }

                jsonObject.put("opes",opes);
                jsonObject.put("arith",arith);
                jsonObject.put("parentPath",parentPath);

                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(jsonObject.toJSONString());
//                return jsonObject.toJSONString();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else{
            System.out.println(("该文件不存在"));
        }
//        return null;
    }

}
