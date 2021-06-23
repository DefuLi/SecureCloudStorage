package cn.xjtu.iotlab.utils;

import cn.xjtu.iotlab.utils.encdec.CESCMC;
import cn.xjtu.iotlab.utils.encdec.Cert1;
import cn.xjtu.iotlab.utils.encdec.OPEART;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExcelEncDecUtil {
    public static long opeart_k=2568941234L;
    public static int cescmc_k=47;
    public static int cescmc_n=8;
    public static String head0="000000000000";
    public static String user_name;


    public static int OP_CE_enc_dec(int type, int enc_property, int enc_type, File filePath,String user) {
        String path = filePath.getAbsolutePath();
        user_name = user;
        String outputPath = "D:\\"+user_name+"\\" + filePath.getName();

        try {
            // OPEART
            if(enc_type==1) { //OPEART加密算法
                System.out.println("opeart_k  "+opeart_k);
                OPEART op=new OPEART(opeart_k);
                FileInputStream finput = new FileInputStream(filePath);
                POIFSFileSystem fs = new POIFSFileSystem(finput);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                HSSFSheet sheet = wb.getSheetAt(0);

                HSSFRow row0 = sheet.getRow(0);//处理第一行，加密的时候添加加密信息，解密的时候消除解密信息
                HSSFCell cell0  = row0.getCell(enc_property-1);
                //前端？？？
                try{
                    String firstrow="";
                    if (cell0.getCellType() == HSSFCell.CELL_TYPE_STRING) { //单元格是字符串
                        firstrow=cell0.getStringCellValue();
                    }
                    else if(cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) { //单元格是数字
                        firstrow=""+cell0.getNumericCellValue();
                    }
                    if(type==0){//加密

                        // firstrow=enc_type+"@#"+firstrow;//加前缀

                        if((firstrow.charAt(1)=='@') && (firstrow.charAt(2)=='#')){//有经过加密的
                            System.out.println(("该属性已经经过保序加密！"));
                            return 7;
                            // hasenc=true;
                            //enctype[i]=Integer.parseInt(firstrow.substring(0,1));

                        }
                        firstrow=enc_type+"@#"+firstrow;//加前缀
                    }//解密
                    else{
                        if(!((firstrow.charAt(1)=='@') && (firstrow.charAt(2)=='#'))){//没有经过加密的
                            System.out.println(("该属性还没经过保序加密！"));
                            return 7;

                        }
                        firstrow=firstrow.substring(3);//去掉前缀
                    }
                    row0.createCell(enc_property-1).setCellValue(firstrow);

                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

                HSSFRow row = sheet.getRow(1);
                HSSFCell cell  = row.getCell(enc_property-1);
                //  System.out.println(cell.getCellType()); //数字是0，字符串是1
                int rsRows = sheet.getLastRowNum();//获取Sheet表中所包含的总行数 ,从0开始
                //    System.out.println(rsRows); //数字是0，字符串是1
                int str_num=0;//判断解密后的excel是字符串还是数字,0是数字，1是字符串
                for(int j=1;j<=rsRows;j++) {
                    row = sheet.getRow(j);
                    cell   = row.getCell(enc_property-1);
                    String getexcle="";
                    String result="";
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) { //单元格是字符串
                        //String result= op.encrypt(cell.getStringCellValue());
                        getexcle=cell.getStringCellValue();
                    }
                    else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) { //单元格是数字
                        getexcle=""+cell.getNumericCellValue();
                        if(getexcle.length()<head0.length()) //如果原来的长度小于head0，才在前面添加0，否则不用添加0
                            getexcle=head0.substring(getexcle.length())+getexcle;
                        // System.out.println(getexcle);
                    }
                    if(type==0) { //加密
                        result=op.encrypt(getexcle);
                        row.createCell(enc_property-1).setCellValue(result);
                    }
                    else //解密
                    {
                        // op=null;
                        if(path.endsWith("__.xls") || path.endsWith("__.xlsx")){//其他用户的文件

                            String othername=path;
                            othername=othername.substring(0,othername.lastIndexOf("__"));
                            othername=othername.substring(othername.lastIndexOf("__")+2);
                            Cert1 cert1 =new Cert1();
                            String res= cert1.getCert1(othername,user_name);
                            if(res.equals("")){
                                System.out.println(("你没有获得 " + othername + "的证书"));
                                return 0;
                            }
                            String keyarr[]=res.split("&");
                            String opeartkey=keyarr[5];
                            long value=Long.parseLong(opeartkey);
                            op=new OPEART(value);
                        }
                        // else {
                        //	op=new OPEART(opeart_k);
                        // }

                        result=op.decrypt(getexcle);
                        try {
                            if(str_num==0) { //数字,明文是数字的，转化为数字
                                double double_result=Double.valueOf(result);
                                row.createCell(enc_property-1).setCellValue(double_result);
                            }
                            else { //字符串
                                row.createCell(enc_property-1).setCellValue(result);
                            }
                        }
                        catch(Exception ex) {
                            str_num=1; //改为字符串
                            row.createCell(enc_property-1).setCellValue(result); //修改第一个字符串
                            ex.printStackTrace();
                        }
                    }
                }

                OutputStream fos = new FileOutputStream(path);
                wb.write(fos);
                fos.close();
                finput.close();
                if(type==0)
                    return 1;//OPEART加密完成
                else
                    return 2;//OPEART解密完成
            }
            // CESCMC
            else if(enc_type==2){ //CESCMC加密算法


                FileInputStream finput = new FileInputStream(filePath);
                POIFSFileSystem fs = new POIFSFileSystem(finput);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                HSSFSheet sheet = wb.getSheetAt(0);

                HSSFRow row0 = sheet.getRow(0);//处理第一行，加密的时候添加加密信息，解密的时候消除解密信息
                HSSFCell cell0  = row0.getCell(enc_property-1);
                // 前端部分
                try{
                    String firstrow="";
                    if (cell0.getCellType() == HSSFCell.CELL_TYPE_STRING) { //单元格是字符串
                        firstrow=cell0.getStringCellValue();
                    }
                    else if(cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) { //单元格是数字
                        firstrow=""+cell0.getNumericCellValue();
                    }
                    if(type==0){//加密
                        if((firstrow.charAt(1)=='@') && (firstrow.charAt(2)=='#')){//有经过加密的
                            System.out.println(("该属性已经经过算术加密！"));
                            return 7;
                            // hasenc=true;
                            //enctype[i]=Integer.parseInt(firstrow.substring(0,1));

                        }
                        firstrow=enc_type+"@#"+firstrow;//加前缀
                        // firstrow=enc_type+"@#"+firstrow;//加前缀
                    }
                    else{
                        if(!((firstrow.charAt(1)=='@') && (firstrow.charAt(2)=='#'))){//没有经过加密的
                            System.out.println(("该属性还没经过算术加密！"));
                            return 7;

                        }
                        firstrow=firstrow.substring(3);//去掉前缀
                        //firstrow=firstrow.substring(3);//去掉前缀
                    }
                    row0.createCell(enc_property-1).setCellValue(firstrow);

                }
                catch(Exception ex){
                    ex.printStackTrace();
                }


                HSSFRow row = sheet.getRow(1);
                HSSFCell cell  = row.getCell(enc_property-1);
                //     System.out.println(cell.getCellType()); //数字是0，字符串是1
                int rsRows = sheet.getLastRowNum();//获取Sheet表中所包含的总行数 ,从0开始

                if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING && type==0) { //字符串  加密
                    finput.close();
                    return 3;//CESCMC算法只支持数字加密
                }
                else  if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING && type==1) { //字符串  解密

                    long  ts= System.nanoTime();//开始时间

                    //CESCMC cescmc=new CESCMC(cescmc_n,cescmc_k);
                    CESCMC cescmc=null;
                    if(path.endsWith("__.xls") || path.endsWith("__.xlsx")){//其他用户的文件

                        String othername=path;
                        othername=othername.substring(0,othername.lastIndexOf("__"));
                        othername=othername.substring(othername.lastIndexOf("__")+2);
                        Cert1 cert1 =new Cert1();
                        String res= cert1.getCert1(othername,user_name);
                        if(res.equals("")){

                            System.out.println(("你没有获得 " + othername + "的证书"));
                            return 0;
                        }
                        String keyarr[]=res.split("&");
                        String cescmc_k_str=keyarr[6];
                        String cescmc_n_str=keyarr[7];

                        int cescmc_k_int=Integer.parseInt(cescmc_k_str);
                        int cescmc_n_int=Integer.parseInt(cescmc_n_str);

                        cescmc=new CESCMC(cescmc_n_int,cescmc_k_int);
                    }
                    else {
                        cescmc=new CESCMC(cescmc_n,cescmc_k);
                    }



                    for(int j=1;j<=rsRows;j++) {

                        row = sheet.getRow(j);
                        cell   = row.getCell(enc_property-1);
                        String getexcle=cell.getStringCellValue();
                        String[] st=getexcle.split(";");

                        //System.out.println("st.length"+st.length);
                        double[][]en_sn=new double[st.length][st.length];
                        for(int i=0;i<st.length;i++){
                            String[] st2=st[i].split(",");
                            try{
                                for(int k=0;k<st2.length;k++){
                                    en_sn[i][k]=Double.valueOf(st2[k]);
                                }
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                        double de_sn=cescmc.decrypt(en_sn);
                        row.createCell(enc_property-1).setCellValue(de_sn);
                    }
                    OutputStream fos = new FileOutputStream(path);
                    wb.write(fos);
                    fos.close();
                    finput.close();

                    long  te= System.nanoTime();//开始时间
                    System.out.println("******************************************************");
                    long dectime=te-ts;

                    double 	ddet=dectime;
                    ddet =ddet/Math.pow(10, 6);
                    System.out.println("解密时间(ms)  "+ddet);
                    System.out.println("******************************************************");
                    return 4;//CESCMC解密完成
                }
                else if(cell.getCellType() ==  HSSFCell.CELL_TYPE_NUMERIC && type==0 ) {	//数字	 加密

                    long  ts= System.nanoTime();//开始时间

                    CESCMC cescmc=new CESCMC(cescmc_n,cescmc_k);

                    for(int j=1;j<=rsRows;j++) {
                        row = sheet.getRow(j);
                        cell   = row.getCell(enc_property-1);
                        double sn=cell.getNumericCellValue();
                        double[][]en_sn=cescmc.encrypt(sn);			//被操作数加密
                        String en_str="";

                        for(int i=0;i<cescmc_n;i++){
                            for(int k=0;k<cescmc_n;k++){
                                en_str=en_str+en_sn[i][k]+",";
                            }
                            en_str=en_str+";";
                        }
                        row.createCell(enc_property-1).setCellValue(en_str);
                    }
                    OutputStream fos = new FileOutputStream(path);
                    wb.write(fos);
                    fos.close();
                    finput.close();

                    long  te= System.nanoTime();//結束时间
                    System.out.println("******************************************************");
                    long dectime=te-ts;

                    double 	ddet=dectime;
                    ddet =ddet/Math.pow(10, 6);
                    System.out.println("加密时间(ms)  "+ddet);
                    System.out.println("******************************************************");

                    return 5;//CESCMC加密完成
                }
                else if(cell.getCellType() ==  HSSFCell.CELL_TYPE_NUMERIC && type==1 ) { //数字解密
                    finput.close();
                    return 6;//CESCMC解密失败
                }
            }

            return 0;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 7;//加解密失败
        }
    }

    public static double[][] getMatrixFrom(String value){
        String[] st=value.split(";");
        //System.out.println("st.length"+st.length);
        double[][]en_sn=new double[st.length][st.length];
        for(int i=0;i<st.length;i++){
            String[] st2=st[i].split(",");
            try{
                for(int k=0;k<st2.length;k++){
                    en_sn[i][k]=Double.valueOf(st2[k]);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return en_sn;
    }
}
