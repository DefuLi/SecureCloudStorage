package cn.xjtu.iotlab.utils.encdec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class AspeEncDec {

		/**
		 * ���ܲ�����ز���
		 */	
		static String head0="000000000000";
		static int aspe_da=4;
		static int aspe_dm=2;
		static int aspe_k=1;
		static long opeart_k=2568941234L;
	public static int OP_AS_enc_dec(int type,int enc_property,String filePath) {
			
			try {		 
				  //ASPE�����㷨					
					    FileInputStream finput = new FileInputStream(filePath);
				        POIFSFileSystem fs = new POIFSFileSystem(finput);
				        HSSFWorkbook wb = new HSSFWorkbook(fs);
				        HSSFSheet sheet = wb.getSheetAt(0);				        
				        HSSFRow row = sheet.getRow(1);
				        HSSFCell cell  = row.getCell(enc_property-1); 
				   //     System.out.println(cell.getCellType()); //������0���ַ�����1
				        int rsRows = sheet.getLastRowNum();//��ȡSheet������������������ ,��0��ʼ
			        
					  if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING && type==0) { //�ַ���  ����					
					   finput.close();
					   return 3;//ASPE�㷨ֻ֧�����ּ���
					  }	
					  else  if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING && type==1) { //�ַ���  ����
							ASPE191 aspe=new ASPE191(aspe_da,aspe_dm,aspe_k);							        	
			        		
							/*
						        int dec_type=0;//0��ֱ�ӽ��ܣ�1 �Ǽӷ����ܣ�2�Ǽ������ܣ�3�ǳ˷����ܣ�4�ǳ�������
						        if(filePath.contains("____add"))
						        	dec_type=1;
						        else if(filePath.contains("____sub"))	
						        	dec_type=2;
						        else if(filePath.contains("____mul"))	
						        	dec_type=3;
						        else if(filePath.contains("____div"))	
						        	dec_type=4;
						*/
							
							 for(int j=1;j<=rsRows;j++) {
					        	
			        			 row = sheet.getRow(j);
					        	 cell   = row.getCell(enc_property-1);
					        	 String getexcle=cell.getStringCellValue();	
					        	 char c=getexcle.charAt(0);//��һλ�����ж�����ʲôʲô����������
					        	 getexcle=getexcle.substring(1);
								 String[] st=getexcle.split(",");
								double[][]en_sn=new double[1][st.length];
								for(int i=0;i<st.length;i++){
									en_sn[0][i]=Double.valueOf(st[i]);
								}
							//n,a,s,m,d�ֱ��ʾ�������ܣ��Ӽ��˳�����
								double de_sn=0;
								if(c=='n') 
									de_sn=aspe.decrypt(en_sn)[0];		//����������
								else if(c=='a') //�ӷ�����
									de_sn=aspe.after_add_sub(en_sn)[0];
								else if(c=='s') //��������
									de_sn=aspe.after_add_sub(en_sn)[0];	
								else if(c=='m') {//�˷�����
									int length=(int)Math.sqrt(en_sn[0].length);
									System.out.println(length);
									double [][][] de_mul=new double[en_sn.length][length][length];
									 for(int t=0;t<de_mul.length;t++)
										 for(int y=0;y<de_mul[0].length;y++)
											 for(int u=0;u<de_mul[0][0].length;u++)
												 de_mul[t][y][u]=en_sn[t][8*y+u];
									de_sn=aspe.after_mul(de_mul)[0];
							    }
							   else if(c=='d'){ //��������
								   int length=(int)Math.sqrt(en_sn[0].length);
									double [][][] de_mul=new double[en_sn.length][length][length];
									 for(int t=0;t<de_mul.length;t++)
										 for(int y=0;y<de_mul[0].length;y++)
											 for(int u=0;u<de_mul[0][0].length;u++)
												 de_mul[t][y][u]=en_sn[t][8*y+u];
									de_sn=aspe.after_div(de_mul)[0];   
							    }	
							 
							 
								row.createCell(enc_property-1).setCellValue(de_sn);				
							  }
			        		   OutputStream fos = new FileOutputStream(filePath);
						        wb.write(fos);			        					       
						        fos.close();
						        finput.close();
						        return 4;//ASPE�������					 
					  }
					  else if(cell.getCellType() ==  HSSFCell.CELL_TYPE_NUMERIC && type==0 ) {	//����	 ����				  
						 
						  ASPE191 aspe=new ASPE191(aspe_da,aspe_dm,aspe_k);	
							 for(int j=1;j<=rsRows;j++) {	    
								row = sheet.getRow(j);
					        	cell   = row.getCell(enc_property-1);						    			
								double[] sn={0};
								sn[0]=cell.getNumericCellValue();
								double[][]en_sn=aspe.encrypt(sn);			//������������
								String en_str="";
								for(int i=0;i<en_sn[0].length;i++){
									en_str=en_str+en_sn[0][i]+",";
								}
								en_str="n"+en_str;
								row.createCell(enc_property-1).setCellValue(en_str);								
				        }
						OutputStream fos = new FileOutputStream(filePath);
						 wb.write(fos);			        					       
						 fos.close();
						 finput.close();
						 return 5;//ASPE�������
					  }  
					  else if(cell.getCellType() ==  HSSFCell.CELL_TYPE_NUMERIC && type==1 ) { //���ֽ���				
						//  enc.enc_result.setText("���:\nASPE����ʧ��");
						  finput.close();
						  return 6;//ASPE����ʧ��
					  }
			 return 0;
					  }
					catch (Exception ex)
					{	
						 ex.printStackTrace();
						 return 7;//�ӽ���ʧ��
					}		
		}    


}
