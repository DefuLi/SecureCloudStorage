package cn.xjtu.iotlab.utils.encdec;

import   java.io.*; 

import   java.util.zip.*; 

public class unZip{
	
	public String[] readFloder(String user_name){
		String[] res=null;
		try{
			File file = new File("D:\\qingyunclient\\atest\\zip_"+user_name+"\\");
			res=file.list();
			for(int i=0;i<res.length;i++){
				res[i]="D:\\qingyunclient\\atest\\zip_"+user_name+"\\"+res[i];
				System.out.println(res[i]);
			} 
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
		
	public void extZipFileList(String zipFileName,String user_name){ 
		try{ 
			ZipInputStream in=new ZipInputStream(new FileInputStream(zipFileName)); 
			ZipEntry entry = null;
			File file2 = new File("D:\\qingyunclient\\atest\\zip_"+user_name);
			file2.mkdirs();
			while((entry =in.getNextEntry())!=null){
				String entryName = entry.getName();
				if(entry.isDirectory()){ 
					File file = new File("D:\\qingyunclient\\atest\\zip_"+user_name+"\\"+entryName);
					file.mkdirs(); 
					System.out.println( "�����ļ���: "   +   entryName); 
				}else{
					
					
					FileOutputStream os=new   FileOutputStream( "D:\\qingyunclient\\atest\\zip_"+user_name+"\\"+entryName);
					byte[]   buf   =   new   byte[1024]; 
					int   len;
					while((len=in.read(buf))> 0){ 
						os.write(buf,0, len);
					}
					os.close();
					in.closeEntry();
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		} 
		//System.out.println( "��ѹ�ļ��ɹ� ");
	}
	
	public   static   void   main(String[]   args){ 
		unZip zips=new unZip();
		//zips.extZipFileList( "C://Documents and Settings//Administrator//����//allqq.zip");
		//zips.readFloder();
	} 


} 
