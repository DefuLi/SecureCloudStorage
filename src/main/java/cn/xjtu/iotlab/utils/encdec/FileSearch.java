package cn.xjtu.iotlab.utils.encdec;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题1：获取所有的文件名
 * 问题2：定义一个随机数
 * 需求：搜索关键字也要进行分割，最后多个List寻找重复文件
 */
public class FileSearch {
    private String basePath = "src/main/resources/iotlab";
    public static unpadded_RSA rsa =new unpadded_RSA() ;
    public String random;
    String BF;
    String keyword;
    public List<List<Map<BloomFilter, String>>> allFiles;//保存所有文件，第一个List保存所有文件，第二个List保存每个文件的各个关键词，以Map形式保存，Map的key是BF值，value是文件名
    private Map<String, String> allFiles2;//保存所有文件关键字，key是BF值，value是文件名
    private Map<String, List<String>> allFiles3;//保存所有文件关键字，key是BF值，value是BF值相同的文件名集合

    public FileSearch(){}

    public FileSearch(String userName){
        basePath += "/" + userName + "/atest/encrypted_file";
    }

    public static void main(String[] args) {
        FileSearch fileSearch = new FileSearch("admin");
        System.out.println(fileSearch.basePath);

        String [] fileNames = {"我要上清华.txt", "hello_World","今天星期天.docx", "清华NO1.txt", "清华yyds.mp3"};
        int rand=(int)(Math.random()*10000);
        fileSearch.random = Integer.toString(rand);
//        String name = "D:\\qingyunclient\\收到.txt";
//        FileSearch fileSearch = new FileSearch();
//        String name = "src/main/resources/iotlab/admin//西安交通大学物联网实验室.txt";
//        fileSearch.fileEncrypt(name);
//        fileSearch.keyword = "收到";
//        System.out.println(fileSearch.check(fileSearch.BF, Integer.parseInt(fileSearch.random), fileSearch.keyword));
//
//
//
        System.out.println("-----------------1--------------------");
        fileSearch.allFiles3 = fileSearch.keywordSplit2(fileNames, Integer.parseInt(fileSearch.random));
        for(Map.Entry<String, List<String>> entry: fileSearch.allFiles3.entrySet()){
            System.out.println("key = " + entry.getKey());
            for(String filename:entry.getValue()) System.out.println(filename);
        }
        System.out.println("search result:");
        List<String> fileName2 = fileSearch.searchFile2(fileSearch.allFiles3, Integer.parseInt(fileSearch.random), "清华");
        System.out.println(fileName2.toString());



        System.out.println("-----------------2--------------------");
        List<String> searchKwBF = fileSearch.searchKeywordSplit("上清华",Integer.parseInt(fileSearch.random));
        List<List<String>> filenameList = fileSearch.searchFile2(fileSearch.allFiles3, Integer.parseInt(fileSearch.random), searchKwBF);
        int i = 0;
        for(List<String> filenames:filenameList){
            System.out.println("---------------" + i++ + "------------------");
            for(String filename:filenames){
                System.out.println(filename);
            }
        }
    }


//    public List<String> fileNameOrder(List<List<String>> filenameList){
//
//    }


    /**
     * 根据关键字进行模糊搜索
     * @param allFiles 所有文件
     * @param ran 随机数
     * @param keyword 搜索关键字
     * @return 模糊搜索的文件名列表
     */
    public List<String> searchFile2(Map<String, List<String>> allFiles,int ran, String keyword){
        //对文件名加密
        List<String> fileName;
        String kw[] = kwAbstract(keyword);
        String ss = kwBF(kw,0.001,ran);

        fileName = allFiles.getOrDefault(ss,null);//返回文件名List，文件不存在返回null
//        if(allFiles.containsKey(ss)) fileName = allFiles.get(ss);
        return fileName;
    }


    /**
     * 根据分割后的搜索关键字，返回文件名
     * @param allFiles 所有文件
     * @param ran 随机数
     * @param keywords 分割后的搜索关键字，已经加密为BF值
     * @return 各个关键字对应的文件名List
     */
    public List<List<String>> searchFile2(Map<String, List<String>> allFiles,int ran, List<String> keywords){
        List<List<String>> fileNameList = new ArrayList<>();
        //遍历检索关键字
        for(String keyword: keywords){
            fileNameList.add(allFiles.getOrDefault(keyword, null));//添加文件名List
        }
        return fileNameList;
    }


    /**
     * 根据文件名给文件关键字加密
     * 问题1：随机数是一样的，如果随机数不一样BF就不一样，如果随机数一样加密效果不好
     * @param fileNames 文件名集合
     * @param ran 随机数
     * @return 所有文件关键字的map
     */
    public Map<String, List<String>> keywordSplit2(String [] fileNames, int ran){
        Map<String, List<String>> allFiles = new HashMap<>();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        //遍历所有文件名
        for(String fileName:fileNames){
            //删除后缀
            if(fileName.contains(".")){
                fileName=fileName.substring(0, fileName.indexOf("."));
            }
            String s=fileName;

            List<String> keywords;
            keywords = segmenter.sentenceProcess(s);//对文件名进行分词
            //遍历所有文件名的关键字
            for(String keyword:keywords){
                //BF加密
                String kw[] = kwAbstract(keyword);
                String ss = kwBF(kw,0.001,ran);

                //将文件名添加到对应BF值的文件名List中
                List<String> temp = new ArrayList<>();
                if(allFiles.containsKey(ss)){
                    temp = allFiles.get(ss);
                }
                temp.add(fileName);
                allFiles.put(ss, temp);
            }
        }
        return allFiles;
    }

    public List<String> keywordSplitByFileName(String fileName, int ran){
        List<String> result = new ArrayList<>();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        System.out.println();
        if(fileName.contains(".")){
            fileName=fileName.substring(0, fileName.indexOf("."));
        }
        String s=fileName;

        List<String> keywords;
        keywords = segmenter.sentenceProcess(s);//对文件名进行分词
        System.out.println("keywords: " + keywords);
        //遍历文件名所有分词
        for(String keyword:keywords){
            //BF加密
            String kw[] = kwAbstract(keyword);
            String ss = kwBF(kw,0.001,ran);
            result.add(ss);
        }
        return result;
    }

    /**
     * 根据搜索关键字，返回分割后关键字的BF值
     * @param keyword 搜索关键字
     * @param ran 随机数
     * @return 分割关键字的BF值
     */
    public List<String> searchKeywordSplit(String keyword, int ran){
        List<String> res = new ArrayList<>();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> kws = segmenter.sentenceProcess(keyword);
        //遍历搜索关键字的所有分词
        for(String kw:kws){
            //BF加密
            String [] temp = kwAbstract(kw);
            String ss = kwBF(temp,0.001,ran);

            res.add(ss);//添加到List
        }
        return res;
    }


    public void upload(String parentPath, String name){
//        String path = parentPath + "/" + name;
        String path = name;
        File file = new File(path);
        try{

            BufferedReader bf=new BufferedReader (new FileReader(path));//0.txt文件
            String readline;
            String random=null;
            String BF=null;
            while ((readline = bf.readLine()) != null) {
                if(readline.equals("filename:"+name)){
                    random=bf.readLine();
                    BF=bf.readLine();
                    break;
                }
            }

            this.random=random.substring(random.indexOf(":")+1);
            this.BF=BF.substring(BF.indexOf(":")+1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fileEncrypt(final String filedir){   //加密文件内容和名字，将加密后的文件存放在相应的位置


        int rand=(int)(Math.random()*10000);
        this.random = Integer.toString(rand);
        String BF;
        String en_name;
        AllFile allF=new AllFile();
        String content="";
//        File dirFile = new  File("D:\\qingyunclient\\atest\\encrypted_file");
        File dirFile = new  File(basePath);
        if (! (dirFile.exists())&& ! (dirFile.isDirectory())) dirFile.mkdirs();
        File f=new File(filedir);
        String name=f.getName();  //System.out.println(name);
//        System.out.println("file name: " + name);

        AES aes= new AES();
        en_name=aes.encrypt(name);	 //加密的文件名
        System.out.println("En_name: " + en_name);
        File test = new File(filedir);
//        System.out.println(test.exists());
        content=allF.readFile(filedir);
        String en_content=aes.encrypt(content); //加密的文件内容

        String[] kw=kwAbstract(name);      //文件关键字的BF
        //System.out.println("name encrypt");
        //for(String k : kw) System.out.println(k);
        BF=kwBF(kw,0.001,rand);
        this.BF = BF;

//        f = new File("D:\\qingyunclient\\atest\\encrypted_file\\"+en_name);
        f = new File(basePath + "/" + en_name);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        BufferedWriter output;
        try {
            output = new BufferedWriter(new FileWriter(f));
            output.write(en_content);
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
//            File f1=new File("D:\\qingyunclient\\atest\\encrypted_file\\0.txt");
            File f1=new File(basePath + "/0.txt");
            if (!f1.exists()) {
                try {
                    f1.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }}
            String en_info="";
            en_info=en_info+"filename:"+en_name+"\r\n";
            en_info=en_info+"random:"+rand+"\r\n";
            en_info=en_info+"BF:"+BF+"\r\n";
            BufferedWriter output1;
            output1 = new BufferedWriter(new FileWriter(f1));
            output1.write(en_info);
            output1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public String kwBF(String[] kw,double positive,int rand){   //根据加密后的kw生产BF
        byte[] mms=null;
        String result="";
        try{
            BloomFilter bf = new BloomFilter();
            bf.kwInsert(kw,rand);
            mms=bf.mm;
            //System.out.println();
            result=byteToStr(mms);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断目标文件内是否含有目标关键字
     * @param pkw 文件关键字,待查询文件提取的
     * @param ran random随机数，带查询文件提取的
     * @param qkw 查询关键字，手输
     * @return true/false
     */
    public boolean check(String pkw,int ran,String qkw){ //pkw----文件关键字，qkw----查询关键字
        System.out.println("file keyword: "+pkw);
        System.out.println("search keyword: "+qkw);
        //System.out.println(ran + "," + random);
        boolean b=false;
        int k=(int)(Math.log(0.001)/Math.log(0.5))+1;   //hash个数
        int n=50;                                         //kw个数
        int m=744; //(int)(Math.log(positive)/Math.log(0.6185))*n+n/2; //BF的位数
        byte[] mm=null;
        try{
//            String[] kw={qkw};//kw为查询关键字

            String[] kw=kwAbstract(qkw);      //文件关键字的BF
            String ss = kwBF(kw,0.001,ran);

//            BloomFilter bf=new BloomFilter(m,k,n); //查询关键字的BF映射与普通关键字的BF映射一样
//            //fileSearch.keyword = fileSearch.kwBF(kw,0.001,Integer.parseInt(fileSearch.random));
//            //有问题，kw必须为数字
//            bf.kwInsert(kw,ran);//生成mm数组
//            mm=bf.mm;
//            String ss=byteToStr(mm);   //转string
            b=check(ss,pkw);	//得到查询关键字的BF1，与普通关键字的BF2进行对比，BF只要有1位是1而BF2是0，就表名BF2不包含查询关键字
            //System.out.println((i+1)+":   "+result);
//            System.out.println(b);
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }



    /**
     * 判断文件是否含有含有关键字
     * @param bf1 查询关键字
     * @param bf2 文件关键字
     * @return true/false
     */
    public boolean check(String bf1,String bf2){//bf1是查询关键字的bf，bf2是文件关键字的bf
        boolean res=false;
        String t1=bf1;
        String t2=bf2;
        //System.out.println("----");
        //System.out.println(t1.substring(0, 1));
        //System.out.println("----");
        System.out.println("bf1 : " + bf1);
        System.out.println("bf2 : " + bf2);
        int a,b,c=1;
        int tag=0;
        while(t1.indexOf("&")!=-1){
            a=Integer.parseInt(t1.substring(0, t1.indexOf("&"))); //System.out.println(a+"---");
            b=Integer.parseInt(t2.substring(0, t2.indexOf("&"))); //System.out.println(a+"---"+b+"= "+(a&b));
            if(a!=0){
                c=a&b;  //System.out.println(a+": "+b+"="+c);
                if(c!=a){//当a的位上的数是1时(0时不考虑)，如果b也是1，则c一定等于a，如果a为1，而b为0，则c！＝a，表名关键字不在这个里面
                    tag=1;
                    break;
                }
            }
            t1=t1.substring(t1.indexOf("&")+1);
            t2=t2.substring(t2.indexOf("&")+1);
        }
        if(tag==0) res=true;

        return res;

    }

    /**
     * 将二进制数组转为string
     * @param mm 二进制数组
     * @return 数组的String
     */
    public String byteToStr(byte[] mm){
        String result="";
        try{
            int len=mm.length;
            String tmp="";
            int BF_m=744;                  //BloomFiler的位数m的信息
            int t=BF_m/31;
            int ts=0;
            for(int i=0;i<BF_m;i++){
                tmp=tmp+mm[i];
                ts++;
                if(ts==31){
                    int a=Integer.parseInt(tmp,2);
                    result=result+a+"&";
                    tmp="";
                    ts=0;
                }
            }
            //System.out.println(result.length()+",  "+result);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得文件名的关键字kw
     * @param filename
     * @return
     */
    public String[] kwAbstract(String filename){  //根据题目获得关键字kw
        int tag=0;
        if(filename.contains(".")){
            filename=filename.substring(0, filename.indexOf("."));
        }
        String s=filename;
        String[] result=new String[500];
        int slen=s.length();
        tag=-1;
        for(int i=0;i<slen;i++){  //tag：0--中文，1--英文，2--中英
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
        if(tag==0){  //处理中文
            String[] limit={"基于","研究","的","面向","设计"};
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
        }
        else if(tag==1){ //处理英文
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
        }
        else{ //处理中英
            String[] limit={"基于","研究","的","面向","设计"};
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

        BloomFilter bf=new BloomFilter();
        BigInteger rs;
//        System.out.println("sum = " + sum);
//        for(String k:result) System.out.println("result:" + k);
        for(int i=0;i<sum;i++){
            //System.out.println(result[i]+"-----------");
            rs=bf.ran_encrypt_para(result[i],rsa.n.toString());
            result[i]=rs.toString();
        }
        return result;
    }
    public String[] Chinese(String filename){  //处理中文词组
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

    public String[] English(String filename){  //处理英文词组
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
}
