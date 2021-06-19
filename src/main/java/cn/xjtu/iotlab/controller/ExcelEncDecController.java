package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.utils.ExcelEncDecUtil;
//import cn.xjtu.iotlab.utils.FileOpen;
import java.awt.FileDialog;
import java.awt.Frame;

import cn.xjtu.iotlab.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("excel")
public class ExcelEncDecController {
    //属性加密
    @ResponseBody
    @PostMapping(value = "propertyEncrypt")
    public int propertyEncrypt(@RequestParam("file") MultipartFile multipartFile,@RequestParam("arrSelect") String arrSelect , @RequestParam("type") int type,HttpSession session) throws Exception {
        List<Integer> results = new ArrayList<>();

        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        // 会在本地产生临时文件，用完后需要删除



//        String originalFilename = multipartFile.getOriginalFilename();
//        File file0 = new File(originalFilename);
//        multipartFile.transferTo(file0);
//        FileInputStream finput = new FileInputStream(file);
//        POIFSFileSystem fs = new POIFSFileSystem(finput);//Excel读入系统
//        HSSFWorkbook wb = new HSSFWorkbook(fs);//获取Excel文件
//        HSSFSheet sheet = wb.getSheetAt(0);//获取Excel文件的第一个工作页面
//        HSSFRow row = sheet.getRow(0);//第一行
//        HSSFRow row2 = sheet.getRow(1);//第二行

        int enc_property=0;//加密属性
        int enc_type=0;//加密算法
        String[] arrselect=arrSelect.split("#");
        int[] protype=new int[arrselect.length];//加密属性
        int[] enctype=new int[arrselect.length];//加密算法

        int count=0;
        int count1=0;

        for(int i=0;i<arrselect.length;i++)	{
            String[] str=arrselect[i].split(",");  // 1:工资,2:算术算法
            protype[i]=Integer.valueOf(str[0]);
            enctype[i]=Integer.valueOf(str[1]);
        }
        for(int i=0;i<protype.length;i++){
            enc_property=protype[i];
            enc_type=enctype[i];
//            ExcelEncDecUtil.user_name = (String) session.getAttribute("userName");
            int result= ExcelEncDecUtil.OP_CE_enc_dec(type,enc_property,enc_type,file,(String)session.getAttribute("userName"));
            results.add(result);
            switch (result) {
                case 0:
                    break;
                case 1:
                    count++;//box.setMessage("OPEART加密成功！");
                    break;
                case 3:
                    count1 = 1;//box.setMessage("ASPE算法只支持数字加密！");
                    break;
                case 5:
                    count++;//box.setMessage("ASPE加密成功！");
                    break;
                case 7:
                    count1 = 1;//box.setMessage("加解密失败！");
                    break;
                default:
            }
        }
        if(count>0){// 部分加密成功
            if(count1==1){ //失败
                System.out.println(("部分加密成功！"));
                return 0;
            } else {
                System.out.println(("加密成功！"));
                return 1;
            }
        }
        else {
            System.out.println(("加密失败，该文件可能被其他程序所打开！"));
            return -1;
        }

//        if (file.exists()) {
//            file.delete();
//        }

        //只针对Excel文件
//        FileInputStream finput = new FileInputStream((File) file);


//        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
//
//        ServletFileUpload upload = new ServletFileUpload();
//        upload.setHeaderEncoding("UTF-8");

//        while (iter.hasNext()) {
//            FileItem item = (FileItem) iter.next();
//
//            if (item.isFormField()) {
//                //如果是普通表单字段
//                String name = item.getFieldName();
//                String value = item.getString();
//
//            } else {
//                //如果是文件字段
//                String fieldName = item.getFieldName();
//                String fileName = item.getName();
//                String contentType = item.getContentType();
//                boolean isInMemory = item.isInMemory();
//                long sizeInBytes = item.getSize();
//
//                // Process a file upload
//                if (true) {
//                    File uploadedFile = new File("fileName");
//                    item.write(uploadedFile);
//                } else {
//                    InputStream uploadedStream = item.getInputStream();
//                    uploadedStream.close();
//                }
//            }
//        }
    }

    //属性解密
    @ResponseBody
    @RequestMapping(value = "propertyDecrypt", method = RequestMethod.POST)
    public int propertyDecrypt(@RequestParam("file") MultipartFile multipartFile,@RequestParam("arrSelect") String arrSelect , @RequestParam("type") int type) throws Exception {
        List<Integer> results = new ArrayList<>();

        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        // 会在本地产生临时文件，用完后需要删除

        int enc_property=0;//加密属性
        int enc_type=0;//加密算法
        String[] arrselect=arrSelect.split("#");
        int[] protype=new int[arrselect.length];//加密属性
        int[] enctype=new int[arrselect.length];//加密算法

        int count=0;
        int count1=0;

        for(int i=0;i<arrselect.length;i++)	{
            String[] str=arrselect[i].split(",");  // 1:工资,2:算术算法
            protype[i]=Integer.valueOf(str[0]);
            enctype[i]=Integer.valueOf(str[1]);
        }
        for(int i=0;i<protype.length;i++){
            enc_property=protype[i];
            enc_type=enctype[i];
            int result= ExcelEncDecUtil.OP_CE_enc_dec(type,enc_property,enc_type,file,"admin");
            results.add(result);
            switch(result){
                case 0:
                    break;
                case 2: count++;//box.setMessage("OPEART解密成功！");
                    break;
                case 4:count++;//box.setMessage("ASPE解密成功！");
                    break;
                case 6:count1=1;//box.setMessage("ASPE解密失败！");
                    break;
                case 7:count1=1;//box.setMessage("加解密失败！");
                    break;
                default:
            }
        }
        if(count>0){// 部分加密成功
            if(count1==1){ //失败
                System.out.println(("部分解密成功！"));
                return 0;
            } else {
                System.out.println(("解密成功！"));
                return 1;
            }
        }
        else {
            System.out.println(("解密失败！"));
            return -1;
        }

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
                List<String> list = new ArrayList<>();
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

//                for(int i=0;i<infoarr.length;i++){
//                    String tmp=infoarr[i];//1@#ID
//                    if(tmp.startsWith("1@#")){
//                        box44.addItem(""+i+":"+tmp.substring(3));
//                    }
//                    else if(tmp.startsWith("2@#")){
//                        box47.addItem(""+i+":"+tmp.substring(3));
//                    }
//                }

//                model.addAttribute("result",1);
//                model.addAttribute("tableHead",infoarr);
//                model.addAttribute("tableData",fileData);
//                return new Result(1,infoarr,fileData).toString();
                JSONObject jsonObject= (JSONObject) JSONObject.toJSON(new Result(1,infoarr,fileData));
                jsonObject.put("tableHead",infoarr);
                jsonObject.put("code",1);
                jsonObject.put("filename",name);
                jsonObject.put("tableData", JSON.toJSONString(lists));
//                jsonObject.put("sheet",sheet);

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
