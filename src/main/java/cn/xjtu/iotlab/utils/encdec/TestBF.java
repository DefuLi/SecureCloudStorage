package cn.xjtu.iotlab.utils.encdec;//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.swt.widgets.Shell;

import cn.xjtu.iotlab.vo.Files;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class TestBF {
    JTextArea text1;//BF�ӽ�����ʾ��Ϣ�Ľ��
    String BFencresult;//BF�����ļ����
    String BFdecresult;//BF�����ļ����
    boolean isallempty=true;//�����ļ��Ƿ�ȫ��Ϊ��
    public String username = "user1";
    public static String pwdd ="111111";
    public static String aes_key="RSA_key1:"+10000019+"\r\n";
    public static int kk=0;   //hash����
    public static int nn=0;   //kw����
    public static int mm=0;   //BFλ��
    public static unpadded_RSA rsa =new unpadded_RSA();
    public static String user_name="user1";
    public List<String> list =new ArrayList<String>();

//    public void choosefile(){//�ļ�ѡ��
//        //String[] tmm=readFile(user_name,pwdd);
//        //AES aes=new AES(tmm[0]);//AES����Կ�����ļ������ļ����ݽ��м��ܣ���ͨ��BF���Լ��ܺ���ļ����ؼ��ֽ���ӳ��
//        AES aes=new AES();
//        //String old_content=text1.getText();    //System.out.println(aes.password+"----------------5");
//        Shell shellb=new Shell(SWT.CLOSE|SWT.MAX);
//        FileDialog dialog=new FileDialog(shellb,SWT.MULTI);
//        //FileDialog dialog=new FileDialog(new JFrame(),SWT.MULTI);
//        dialog.setFileName("�ҵ��ĵ�");
//        dialog.setFilterExtensions(new String[] {"*.*","*.txt","*.mp3"});
//        dialog.setFilterNames(new String[] {"All Files(*.*)","Text Files(*.txt)","mp3(*.mp3)"});
//        dialog.open();
//        String[] filename=dialog.getFileNames();
//        String[] filedir=new String[filename.length];
//        String content3="";
//        for(int i=0;i<filename.length;i++){
//            filedir[i]=dialog.getFilterPath()+"\\"+filename[i];
//            content3=content3+filedir[i]+"\n";
//            ;
//        }
//        //content3=content3+old_content;
//        //System.out.println(content3);
//        text1.append(content3);
//    }

    public void bfEntrpted(){//����
        //String dir=text1.getText();
        String test_dir="D:\\����ƽ̨��ز��������\\qingyun\\user1\\test file";
        String dir="";
        File file= new File(test_dir);
        File[] fs=file.listFiles();
        for(File f : fs){
           if(f.isFile())  dir=dir+f+"\n";
        }
        System.out.println(dir);
        //String dir="D:\\����ƽ̨��ز��������\\qingyun\\user1\\test file\\1703.00420.pdf\n";
        if(dir==null || dir.equals("")){
            //JOptionPane.showMessageDialog( mainPanel,"����ѡ��������ļ�");
            return ;
        }
        String[] tmm=readFile(user_name,pwdd);
        AES aes=new AES(tmm[0]);//AES����Կ�����ļ������ļ����ݽ��м��ܣ���ͨ��BF���Լ��ܺ���ļ����ؼ��ֽ���ӳ��
        //AES aes=new AES();
        int i=0;
        String ss=dir;
        while(ss.indexOf("\n")!=-1){
            i++;
            ss=ss.substring(ss.indexOf("\n")+1);
        }
        System.out.println("***************"+i);
        String[] filedir=new String[i];//�Ȼ�ȡ�м��������ܵ��ļ�
        ss=dir;
        i=0;
        while(ss.indexOf("\n")!=-1){//��ȡ�����ܵ��ļ���
            filedir[i]=ss.substring(0,ss.indexOf("\n")).trim();
            i++;
            ss=ss.substring(ss.indexOf("\n")+1);
        }
        if(filedir.length!=0) {
            String encresult="";
            for(int j=0;j<filedir.length;j++){
                System.out.println(filedir[j]);
            }
            //System.out.println(aes.password+"----------------3");
            try{

                fileEncrypt(filedir);  //System.out.println(aes.password+"----------------4");
                if(isallempty){
                    //JOptionPane.showMessageDialog( mainPanel,"�ļ�ȫ��Ϊ�գ����ܽ���");
                }else{
                    //JOptionPane.showMessageDialog( mainPanel,"���ܳɹ� ,���ܺ���ļ������ "+myencdir+"�£�");
                    //text1.setText(BFencresult);
                }
                //text1.setText("\n\n"+"��ʾ�����ܺ���ļ������"+"D:\\qingyunclient\\atest\\encrypted_file\\�£�");


            }
            catch(Exception ex){
                //text1.setText("\n\n"+"��ʾ:����ʧ�ܣ������¼��ܣ�");
                ex.printStackTrace();
            }
        }
    }

    public void bftrpted(){//����
        //String dir=text1.getText();
        String dir="";
        System.out.println(list);
        for(String i: list){
            dir=dir+"D:\\qingyunclient\\atest\\encrypted_file\\user1\\"+i+"\n";
        }
        System.out.println(dir);
        //String dir="D:\\qingyunclient\\atest\\encrypted_file\\user1\\B67E8080F25B1A27BA05BD072FA43355\n";
        if(dir==null || dir.equals("")){
            //JOptionPane.showMessageDialog( mainPanel,"����ѡ��������ļ�");
            return ;
        }
        String[] tmm=readFile(user_name,pwdd);
        AES aes=new AES(tmm[0]);//AES����Կ�����ļ������ļ����ݽ��м��ܣ���ͨ��BF���Լ��ܺ���ļ����ؼ��ֽ���ӳ��
        //AES aes=new AES();
        int i=0;
        String ss=dir;
        while(ss.indexOf("\n")!=-1){
            i++;
            ss=ss.substring(ss.indexOf("\n")+1);
        }
        String[] filedir=new String[i];
        ss=dir;
        i=0;
        while(ss.indexOf("\n")!=-1){
            filedir[i]=ss.substring(0,ss.indexOf("\n")).trim();
            i++;
            ss=ss.substring(ss.indexOf("\n")+1);
        }
        if(filedir.length!=0){

            fileDecrypt(filedir);
            //JOptionPane.showMessageDialog( mainPanel,"���ܳɹ�,���ܺ���ļ������ "+mydecdir+"�£�");
            //text1.setText("\n\n"+"��ʾ�����ܺ���ļ������"+"D:\\atest\\decrypted_file\\�£�");
            //text1.setText("");
            System.out.println("BFdecresult "+BFdecresult);
            //text1.append(BFdecresult);
        }
    }

    public void entrptfile(){//����Ŀ¼
        String[] cmd = new String[5];
        String url = "D:\\qingyunclient\\atest\\encrypted_file";
        //String url = myencdir;
        cmd[0] = "cmd";
        cmd[1] = "/c";
        cmd[2] = "start";
        cmd[3] = " ";
        cmd[4] = url;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void detrptfile(){//����Ŀ¼
        String[] cmd = new String[5];
        String url = "D:\\qingyunclient\\atest\\decrypted_file";
        //String url = mydecdir;
        cmd[0] = "cmd";
        cmd[1] = "/c";
        cmd[2] = "start";
        cmd[3] = " ";
        cmd[4] = url;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public   String[] readFile(String name,String pwd){
        FileInputStream fin=null;

        String[] result=null;

        try{
            //System.out.println("D:\\test\\client\\key_"+name+".txt");
            int len=9;
            fin=new FileInputStream("D:\\qingyunclient\\test\\client\\key_"+name+".txt");
            result=new String[len];
            int bt=0;
            int j=0;
            String tmp="";
            AES aes=new AES(pwd);//���û���������Ϊ���ܱ����˻���Ϣ�ļ�AES����Կ

            while((bt=fin.read())!=-1)	tmp=tmp+(char)bt;//һ�ζ�һ���ַ���ȫ������洢��tmp��

            tmp=aes.decrypt(tmp);
            int i=0;
            if(tmp==null){
                return null;
            }
            else {
                while(tmp.indexOf("\r\n")!=-1){//���˵��س����У���ȡ��Կ��һ����Կռһ��
                    result[i]=tmp.substring(0,tmp.indexOf("\r\n"));
                    tmp=tmp.substring(tmp.indexOf("\r\n")+2);
                    //System.out.println(result[i]);
                    i++;

                }
                //System.out.println("IIIIII: "+i);
                for(j=0;j<len;j++){
                    //System.out.println(result[j]);
                    result[j]=result[j].substring(result[j].indexOf(":")+1);
                    //System.out.println(result[j]);
                }
                fin.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("--------------------");
        return result;
    }

    public  boolean fileEncrypt(final String[] filedir){   //�����ļ����ݺ����֣������ܺ���ļ��������Ӧ��λ��
        BFencresult="";
        isallempty=true;//ȫ��Ϊ��
        AES aes= new AES();
        deleteAllFile("D:\\qingyunclient\\atest\\encrypted_file\\"+username);
        int sfile=filedir.length;
        int[] rand=new int[sfile];
        String[] BF=new String[sfile];
        String[] en_name=new String[sfile];
        AllFile allF=new AllFile();
            String content="";
            for(int i=0;i<filedir.length;i++){
                rand[i]=(int)(Math.random()*10000);
                }
                    File dirFile = new  File("D:\\qingyunclient\\atest\\encrypted_file\\"+username);
                    if (! (dirFile.exists())&& ! (dirFile.isDirectory())) dirFile.mkdirs();


                    for(int i=0;i<filedir.length;i++) {


                        File f = new File(filedir[i]);//���ļ������м���
                        System.out.println("f.length()  " + f.length());
                        if (f.length() == 0) {
                            System.out.println("�ļ�����Ϊ�գ�������");
                            //JOptionPane.showMessageDialog( mainPanel,"�ļ�  "+filedir[i]+"����Ϊ�գ�������");
                            continue;
                        }
                        isallempty = false;
                        String name = f.getName();  //System.out.println(name);

                        en_name[i] = aes.encrypt(name);     //���ܵ��ļ���
                        list.add(en_name[i]);
                        content = allF.readFile(filedir[i]);
                        String en_content = aes.encrypt(content); //���ܵ��ļ�����

                        String[] kw = kwAbstract(name);      //�ļ��ؼ��ֵ�BF	 ���Ƕ����Ľ��м��ܣ����Ǿ���AES���ܹ���  ��������������棬�������Ǿ���RSA���ܵ� ��
                        //����������ļ�����һ���ģ�һ���ؼ��ִ���Ӧһ������ֵ
                        BF[i] = kwBF(kw, 0.001, rand[i]);    //ÿ��rand[i]����һ��������ģ����ڹؼ��ֺ�����л�����kw��һ���ļ��������ľ���RSA���ܹ��ģ�Ϊ�������񣬱�����0.txt�ļ���


                        f = new File("D:\\qingyunclient\\atest\\encrypted_file\\" + username + "\\" + en_name[i]); //���ܵ��ļ�����ֱ��д���ļ����ļ������ļ�����ͨ��AES���ܡ�
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        BufferedWriter output = null;
                        try {
                            output = new BufferedWriter(new FileWriter(f));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.write(en_content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        BFencresult = BFencresult + "D:\\qingyunclient\\atest\\encrypted_file\\" + username + "\\" + en_name[i] + "\n";

                    }


                    //System.arraycopy(en_name,0,tesyfilename,0,en_name.length);

                    if(isallempty)
                    {
                        //JOptionPane.showMessageDialog( mainPanel,"�ļ�ȫ��Ϊ�գ����ܽ���");
                        return false;

                    }


                        File f=new File("D:\\qingyunclient\\atest\\encrypted_file\\"+username+"\\0.txt");
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        String en_info="";
                        for(int i=0;i<sfile;i++){
                            en_info=en_info+"filename:"+en_name[i]+"\r\n"; //AES ���ܹ����ļ�������Ϊ��������ʹ��
                            en_info=en_info+"random:"+rand[i]+"\r\n";//���ֵ�����ļ����ؼ��ֽ������㣬���л�������
                            en_info=en_info+"BF:"+BF[i]+"\r\n";//BFӳ��Ľ��
                        }
        BufferedWriter output1 = null;
        try {
            output1 = new BufferedWriter(new FileWriter(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output1.write(en_info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BFencresult="D:\\qingyunclient\\atest\\encrypted_file\\"+username+"\\0.txt\n"+BFencresult;



        return isallempty;

    }

    public  void fileDecrypt(final String[] filedir){   //�����ļ����ݺ����֣������ܺ���ļ��������Ӧ��λ��
        BFdecresult="";
                deleteAllFile("D:\\qingyunclient\\atest\\decrypted_file\\"+username);
                unZip zips=new unZip();
                int sfile1=filedir.length;
                String[] de_name1=new String[sfile1];
                String[] content1=new String[sfile1];
                for(int i=0;i<sfile1;i++){
                    de_name1[i]="";
                    content1[i]="";
                }

                AllFile allF=new AllFile();
                File dirFile = new  File("D:\\qingyunclient\\atest\\decrypted_file\\"+username);
                if (! (dirFile.exists())&& ! (dirFile.isDirectory())) dirFile.mkdirs();
                for(int i=0;i<filedir.length;i++){


                    try{
                        String pt=filedir[i];
                        pt=pt.substring(pt.indexOf(".")+1);
                        System.out.println("pt="+pt.compareTo("zip"));
                        if(pt.compareTo("zip")==0){//�����ѹ���ļ����Ƚ�ѹ��

                            deleteAllFile("D:\\qingyunclient\\atest\\zip"+user_name);
                            zips.extZipFileList(filedir[i],user_name);	//��ѹ���ļ�
                            String[] unzipfiledir=zips.readFloder(user_name);//��ѹ��������ض�Ŀ¼�£���ȡ��Ŀ¼�µ������ļ�

                            int sfile=unzipfiledir.length;//��ѹ������ļ�����
                            String[] de_name=new String[sfile];
                            String[] content=new String[sfile];
                            for(int j=0;j<unzipfiledir.length;j++){
                                File f=new File(unzipfiledir[j]);
                                String name=f.getName();
                                String fromother="";
                                //String owner_name=name.substring(name.indexOf("__")+1);
                                if(name.indexOf("___")!=-1){
                                    fromother=name.substring(name.indexOf("___")+3);
                                    name=name.substring(0,name.indexOf("___"));
                                    System.out.println("fromother:"+fromother);
                                }
                                //System.out.println("---------------name------------"+name);
                                //System.out.println("---------------fromother------------"+fromother);
                                if(fromother.equals("") || fromother.equals(username)){//�Լ���

                                    //System.out.println("##############AES key "+aes_key);
                                     //AES aes=new AES(aes_key);
                                    AES aes=new AES();
                                    de_name1[i]=aes.decrypt(name);	 //���ܵ��ļ���
                                    System.out.println(de_name[i]);


                                    content1[i]="";
                                    InputStreamReader read = new InputStreamReader(new FileInputStream(f));
                                    BufferedReader reader=new BufferedReader(read);
                                    String line="";
                                    while ((line = reader.readLine()) != null) {
                                        content1[i]=content1[i]+line;
                                        }

                                    String de_content=aes.decrypt(content1[i]); //���ܵ��ļ�����
                                    String url="D:\\qingyunclient\\atest\\decrypted_file\\"+username+"\\"+de_name1[i];
                                    BFdecresult=BFdecresult+url+"\n";
                                    //BFdecresult=BFencresult+de_name[j]+"\n";
                                    allF.writeFile(de_content, url);


                                }else{//���˵�
                                    //System.out.println("others*******************");
                                    String decfiledir="D:\\qingyunclient\\atest\\decrypted_file\\"+username+"\\"+fromother;
                                    File newdir=new File(decfiledir);
                                    if(!newdir.exists())
                                        newdir.mkdir();


                                    Cert1 cert=new Cert1();
                                    String res=cert.getCert1(fromother,user_name);
                                    String keyarr[]=res.split("&");
                                    String aeskey=keyarr[2];
                                    AES aeshere=new AES(aeskey);
                                    //AES aeshere=new AES();
                                    de_name1[i]=aeshere.decrypt(name);	 //���ܵ��ļ���
                                    content1[i]="";
                                    InputStreamReader read = new InputStreamReader(new FileInputStream(f));
                                    BufferedReader reader=new BufferedReader(read);
                                    String line="";
                                    while ((line = reader.readLine()) != null) {
                                        content1[i]=content1[i]+line;
                                        }

                                    String de_content=aeshere.decrypt(content1[i]); //���ܵ��ļ�����
                                    String url="D:\\qingyunclient\\atest\\decrypted_file\\"+username+"\\"+fromother+"\\"+de_name1[i];
                                    BFdecresult=BFdecresult+url+"\n";
                                    //BFdecresult=BFencresult+de_name[j]+"\n";
                                    allF.writeFile(de_content, url);


                                }


                                System.out.println("һ��"+unzipfiledir.length+"���ļ������ڽ��ܵ�"+(j+1)+"���ļ�");

                            }
                        }else{//����ѹ���ļ���ֱ�ӽ���
                            System.out.println("------------------------------"+filedir[i]);
                            File f=new File(filedir[i]);
                            String name=f.getName();
                            String fromother="";
                            //String owner_name=name.substring(name.indexOf("__")+1);
                            if(name.indexOf("___")!=-1){
                                fromother=name.substring(name.indexOf("___")+3);
                                name=name.substring(0,name.indexOf("___"));

                            }
                            //System.out.println("---------------name------------"+name);
                            //System.out.println("---------------fromother------------"+fromother);
                            if(fromother.equals("") || fromother.equals(username)){//�Լ���

                                //System.out.println("##############AES key "+aes_key);
                                //AES aes=new AES(aes_key);
                                AES aes=new AES();
                                de_name1[i]=aes.decrypt(name);	 //���ܵ��ļ���

                                content1[i]="";
                                InputStreamReader read = new InputStreamReader(new FileInputStream(f));
                                BufferedReader reader=new BufferedReader(read);
                                String line="";
                                while ((line = reader.readLine()) != null) {
                                    content1[i]=content1[i]+line;
                                }

                                String de_content=aes.decrypt(content1[i]); //���ܵ��ļ�����
                                String url="D:\\qingyunclient\\atest\\decrypted_file\\"+username+"\\"+de_name1[i];
                                BFdecresult=BFdecresult+url+"\n";
                                //BFdecresult=BFencresult+de_name[j]+"\n";
                                allF.writeFile(de_content, url);


                            }else{//���˵�
                                //System.out.println("others*******************");
                                String decfiledir="D:\\qingyunclient\\atest\\decrypted_file\\"+username+"\\"+fromother;
                                File newdir=new File(decfiledir);
                                if(!newdir.exists())
                                    newdir.mkdir();


                                Cert1 cert=new Cert1();
                                String res=cert.getCert1(fromother,user_name);
                                String keyarr[]=res.split("&");
                                String aeskey=keyarr[2];
                                AES aeshere=new AES(aeskey);
                                //AES aeshere=new AES();
                                de_name1[i]=aeshere.decrypt(name);	 //���ܵ��ļ���

                                try{
                                    content1[i]="";
                                    InputStreamReader read = new InputStreamReader(new FileInputStream(f));
                                    BufferedReader reader=new BufferedReader(read);
                                    String line="";
                                    while ((line = reader.readLine()) != null) {
                                        content1[i]=content1[i]+line;
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                String de_content=aeshere.decrypt(content1[i]); //���ܵ��ļ�����
                                String url="D:\\qingyunclient\\atest\\decrypted_file\\"+username+"\\"+fromother+"\\"+de_name1[i];
                                BFdecresult=BFdecresult+url+"\n";
                                //BFdecresult=BFencresult+de_name[j]+"\n";
                                allF.writeFile(de_content, url);


                            }



                        }
                        if(pt.compareTo("zip")!=0){
                            System.out.println("һ��"+filedir.length+"���ļ������ڽ��ܵ�"+(i+1)+"���ļ�");
                    }
                    }catch(Throwable el){}
                }





    }

    public static boolean deleteAllFile(String folderFullPath){
        boolean ret = false;
        File file = new File(folderFullPath);
        if(file.exists()){
            if(file.isDirectory()){
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    String filePath = fileList[i].getPath();
                    deleteAllFile(filePath);
                }
            }
            if(file.isFile()){
                file.delete();
            }
        }
        return ret;
    }

    public static  String[] kwAbstract(String filename){  //������Ŀ��ùؼ���kw
        int tag=0;
        filename=filename.substring(0, filename.indexOf("."));
        String s=filename;
        String[] result=new String[500];
        int slen=s.length();
        tag=-1;
        for(int i=0;i<slen;i++){  //tag��0--���ģ�1--Ӣ�ģ�2--��Ӣ
            int c=(int)(s.charAt(i)); //System.out.println(c);
            if(c<125){
                if(tag==-1) tag=1;
                else if(tag==0) tag=2;
            }else{
                if(tag==-1) tag=0;
                else if(tag==1) tag=2;
            }
        }
        //System.out.println("tag="+tag);
        int sum=0;
        s=filename;
        if(tag==0){  //��������
            String[] limit={"����","�о�","��","����","���"};
            for(int i=0;i<limit.length;i++){
                while(s.indexOf(limit[i])!=-1){
                    int index=s.indexOf(limit[i]);
                    if(i!=2) s=s.substring(0,index)+" "+s.substring(index+2);
                    else s=s.substring(0,index)+" "+s.substring(index+1);
                }
            }
            //	int before=0;
            if(s.charAt(0)==' ') s=s.substring(1);
            for(int i=1;i<s.length();i++){
                if(s.charAt(i-1)==' '&& s.charAt(i)==' ') s=s.substring(0,i-1)+s.substring(i);
            }
            if(s.charAt(s.length()-1)==' ') s=s.substring(0,s.length()-2);
            String ss=s;
            while(ss.indexOf(" ")!=-1){
                sum++;
                ss=ss.substring(s.indexOf(" ")+1);
            }
            sum++;
            s=s+" ";
            result=new String[sum];
            int k=0;
            while(s.indexOf(" ")!=-1){
                result[k]=s.substring(0,s.indexOf(" "));
                k++;
                s=s.substring(s.indexOf(" ")+1);
            }
            String[][] sumResult=new String[sum][];
            int sumLen=0;
            for(int i=0;i<sum;i++){
                sumResult[i]=Chinese(result[i]);
                sumLen=sumLen+sumResult[i].length;
            }
            result=new String[sumLen+1];
            k=0;
            for(int i=0;i<sum;i++){
                for(int j=0;j<sumResult[i].length;j++){
                    result[k]=sumResult[i][j]; //System.out.println(result[k]);
                    k++;
                }
            }
            result[k]=filename;  sum=k+1;
        }else if(tag==1){ //����Ӣ��
            String[] limit={"of ","based on ","Research ","Supporting ","on ","An ","A ","for ","Study ","Scheme "};
            for(int i=0;i<limit.length;i++){
                while(s.indexOf(limit[i])!=-1){
                    int index=s.indexOf(limit[i]);
                    if(s.substring(0,index).compareTo("")!=0)
                        s=s.substring(0,index)+" "+s.substring(index+limit[i].length());
                    else s=s.substring(index+limit[i].length());
                }
            }  // System.out.println(s+",");
            String ss=s;
            while(ss.indexOf("  ")!=-1){
                sum++;
                ss=ss.substring(s.indexOf("  ")+1);
            }
            sum++;     //System.out.println(sum);
            s=s+"  ";
            result=new String[sum];
            int k=0;
            while(s.indexOf("  ")!=-1){
                result[k]=s.substring(0,s.indexOf("  "));
                int i=0;
                while(result[k].charAt(i)==' ') result[k]=result[k].substring(1);
                k++;
                s=s.substring(s.indexOf("  ")+2);
            }
            String[][] sumResult=new String[sum][];
            int sumLen=0;
            for(int i=0;i<sum;i++){
                sumResult[i]=English(result[i]);
                sumLen=sumLen+sumResult[i].length;
            }
            result=new String[sumLen+1];
            k=0;
            for(int i=0;i<sum;i++){
                for(int j=0;j<sumResult[i].length;j++){
                    result[k]=sumResult[i][j]; //System.out.println(result[k]);
                    k++;
                }
            }
            result[k]=filename; //System.out.println(result[k]);
            sum=k+1;
        }else{ //������Ӣ
            String[] limit={"����","�о�","��","����","���"};
            for(int i=0;i<limit.length;i++){
                while(s.indexOf(limit[i])!=-1){
                    int index=s.indexOf(limit[i]);
                    if(i!=2) s=s.substring(0,index)+" "+s.substring(index+2);
                    else s=s.substring(0,index)+" "+s.substring(index+1);
                }
            }

            int ssl=s.length();
            int nums=0;
            for(int i=0;i<ssl;i++){
                if(s.charAt(i)!=32 &&s.charAt(i)<125){
                    nums++;
                }else{
                    if(nums>0){
                        s=s.substring(0,i-nums)+" "+s.substring(i-nums,i)+" "+s.substring(i);
                        nums=0;
                    }
                }
            }
            //	int before=0;
            if(s.charAt(0)==' ') s=s.substring(1);
            for(int i=1;i<s.length();i++){
                if(s.charAt(i-1)==' '&& s.charAt(i)==' ') s=s.substring(0,i-1)+s.substring(i);
            }
            if(s.charAt(s.length()-1)==' ') s=s.substring(0,s.length()-2);

            //System.out.println(s+"----------");

            String ss=s;
            while(ss.indexOf(" ")!=-1){
                sum++;
                ss=ss.substring(s.indexOf(" ")+1);
            }
            sum++;
            s=s+" ";
            result=new String[sum];
            int k=0;
            while(s.indexOf(" ")!=-1){
                result[k]=s.substring(0,s.indexOf(" "));
                k++;
                s=s.substring(s.indexOf(" ")+1);
            }
            String[][] sumResult=new String[sum][];
            int sumLen=0;
            for(int i=0;i<sum;i++){
                if(result[i].charAt(0)<125){
                    sumResult[i]=English(result[i]);
                }else{
                    sumResult[i]=Chinese(result[i]);
                }
                sumLen=sumLen+sumResult[i].length;
            }
            result=new String[sumLen+1];
            k=0;
            for(int i=0;i<sum;i++){
                for(int j=0;j<sumResult[i].length;j++){
                    result[k]=sumResult[i][j]; //System.out.println(result[k]);
                    k++;
                }
            }
            result[k]=filename;  sum=k+1;

        }

        BloomFilter bf=new BloomFilter(mm,kk,nn);
        BigInteger rs;
        for(int i=0;i<sum;i++){
            //System.out.println(result[i]+"-----------");
            System.out.println(result[i]);
            rs=bf.ran_encrypt_para(result[i],rsa.n.toString());
            result[i]=rs.toString();
        }
        return result;
    }

    public static String kwBF(String[] kw,double positive,int rand){   //���ݼ��ܺ��kw����BF
        byte[] mms=null;
        String result="";
        try{
            BloomFilter bf;
            if(mm==0) bf=new BloomFilter();
            else bf=new BloomFilter(mm,kk,nn);
            bf.kwInsert(kw,rand);
            mms=bf.mm;
            //System.out.println();
            result=byteToStr(mms);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String[] Chinese(String filename){  //�������Ĵ���
        int len=filename.length();
        int sum=0;
        for(int i=0;i<len;i++){
            sum=sum+(len-i);
        }
        //System.out.println("sum="+sum);
        String[] result=new String[sum];
        int k=0;
        for(int i=0;i<len;i++){
            for(int j=i;j<len;j++){
                if(i==j) result[k]=filename.substring(i,i+1);
                else result[k]=filename.substring(i,j+1);
                k++;
            }
        }
        return result;
    }

    public static  String[] English(String filename){  //����Ӣ�Ĵ���
        String s=filename;
        int tag=0;
        while(s.indexOf(" ")!=-1){
            tag++;
            s=s.substring(s.indexOf(" ")+1);
        }
        String[] res=new String[tag+1];
        s=filename+" ";
        int k=0;
        while(s.indexOf(" ")!=-1){
            res[k]=s.substring(0,s.indexOf(" "));
            s=s.substring(s.indexOf(" ")+1);
            //System.out.println(res[k]);
            k++;
        }
        int len=res.length;
        int sum=0;
        for(int i=0;i<len;i++){
            sum=sum+(len-i);
        }
        String[] result=new String[sum];  //System.out.println("sum="+sum);
        k=0;
        for(int i=0;i<len;i++){
            String tmp="";
            for(int j=i;j<len;j++){
                if(i==j){
                    result[k]=res[i];
                    tmp=result[k];
                }
                else{
                    result[k]=tmp+" "+res[j];
                    tmp=result[k];
                }
                k++;
            }
        }
        return result;
    }

    public static String byteToStr(byte[] mmm){
        String result="";
        try{
            String tmp="";
            int BF_m=744;                  //BloomFiler��λ��m����Ϣ
            //	int t=BF_m/31;
            int ts=0;
            for(int i=0;i<BF_m;i++){
                tmp=tmp+mmm[i];
                ts++;
                if(ts==31){
                    int a=Integer.parseInt(tmp,2);
                    result=result+a+"&";
                    tmp="";
                    ts=0;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //�����ļ��Լ���ǰ��¼��ʹ���û������ļ����ܵ���Ҫ���ܺ���
    public String filebfencrpt(File file, String username){
        System.out.println("------"+username);
        File dirFile = new  File("src/main/resources/iotlab/"+username+"/BF����");
        if (! (dirFile.exists())&& !(dirFile.isDirectory())) dirFile.mkdirs();
        AES aes= new AES();
        AllFile allF=new AllFile();
        int rand = 10086;
        String BF;
        String filedir;
        File f = file;
        String en_name;
        String content="";
        if (f.length() == 0) {
            System.out.println("�ļ�����Ϊ�գ�������");
            //JOptionPane.showMessageDialog( mainPanel,"�ļ�  "+filedir[i]+"����Ϊ�գ�������");
            return null;
        }
        String name = f.getName();
        String type=name.substring(name.lastIndexOf("."));
        en_name = aes.encrypt(name);
        filedir = f.getAbsolutePath();
        content = allF.readFile(filedir);
        String en_content = aes.encrypt(content);
        System.out.println(en_content);
        String[] kw = kwAbstract(name);
        BF = kwBF(kw,0.001,rand);
        File f1 = new File("src/main/resources/iotlab/"+ username+"/"+"BF����/" +en_name+type);
        if (!f1.exists()) {
            try {
                f1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(f1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.write(en_content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f2=new File("src/main/resources/iotlab/"+username+"/0.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String en_info="";
        en_info=en_info+"filename:"+en_name+"\r\n"; //AES ���ܹ����ļ�������Ϊ��������ʹ��
        en_info=en_info+"random:"+rand+"\r\n";//���ֵ�����ļ����ؼ��ֽ������㣬���л�������
        en_info=en_info+"BF:"+BF+"\r\n";//BFӳ��Ľ��
        BufferedWriter output1 = null;
        try {
            output1 = new BufferedWriter(new FileWriter(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output1.write(en_info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f1.getPath();
    }
    //�����ļ��Լ���ǰ��¼��ʹ���û�����BF�ļ����ܵ���Ҫ���ܺ���
    public String filebfdecrpt(File file, String username) throws BadPaddingException, IllegalBlockSizeException, IOException {
        unZip zips=new unZip();
        String returnpath="";
        String de_name1="";
        String content1="";
        AllFile allF=new AllFile();
        File dirFile = new  File("src/main/resources/iotlab/"+ username+"/"+"BF����/");
        if (! (dirFile.exists())&& ! (dirFile.isDirectory())) dirFile.mkdirs();
        String pt = file.getName();
        String name = file.getName();
        pt=pt.substring(pt.indexOf(".")+1);
//      if(pt.compareTo("enc")!=0) return null;
        String fromother="";
        //String owner_name=name.substring(name.indexOf("__")+1);
        if(name.indexOf("___")!=-1){
            fromother=name.substring(name.indexOf("___")+3);
            name=name.substring(0,name.indexOf("___"));

        }
        //System.out.println("---------------name------------"+name);
        //System.out.println("---------------fromother------------"+fromother);
        if(fromother.equals("") || fromother.equals(username)){//�Լ���

            //System.out.println("##############AES key "+aes_key);
            //AES aes=new AES(aes_key);
            AES aes=new AES();
            name=name.substring(0,name.indexOf("."));
            de_name1=aes.decrypt(name);	 //���ܵ��ļ���

            content1="";
            InputStreamReader read = null;
            try {
                read = new InputStreamReader(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader reader=new BufferedReader(read);
            String line="";
            while ((line = reader.readLine()) != null) {
                content1=content1+line;
            }

            String de_content=aes.decrypt(content1); //���ܵ��ļ�����
            String url="src/main/resources/iotlab/"+username+"/"+"BF����/"+de_name1;
            allF.writeFile(de_content, url);
            returnpath =url;

        }else{//���˵�

        }
        return returnpath;
    }

    public static void main(String args[]) {
        TestBF test = new TestBF();
        test.entrptfile();
        test.detrptfile();
        //test.choosefile();
        test.bfEntrpted();
        test.bftrpted();


    }
}
