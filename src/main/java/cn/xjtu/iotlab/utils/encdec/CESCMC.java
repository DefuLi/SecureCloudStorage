package cn.xjtu.iotlab.utils.encdec;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class CESCMC {   
	/**
	 * �Լ��ģ�ѭ������
	 */
	public double[][] dM;         //��Կ
	public double[][] Mi;    //M^-1	
	public int dsum;        //��ά��
	public long myseed ;        //�������������
	
	public CESCMC(int n,long seed){

		dsum=n;
		myseed=seed;
		genKey();
	}	
	
	public void genKey(){   //������ԿM������M^-1��M^T^-1����ʼ�����ݿ�db
		dM=new double[dsum][dsum];
		//Mi=new double[dsum][dsum];		
		Mi=null;	
		//long seed = (long)(Math.random()*100+10);
		Random random = new Random(myseed);		
		do{
			for(int i=0;i<dsum;i++)
				for(int j=0;j<dsum;j++){									
					dM[i][j]=random.nextInt(1000);						
				}
			Mi=reverse(dM);			
		}
		while(Mi==null);
		//System.out.println("dM");
		//printMatrix(dM);
	}	
	
	//������󣬸�˹��Ԫ��
    public  double[][] reverse(double[][] matrix)   
    {   
        double[][] temp;   
        double[][] back_temp;   
        //�õ�����Ľ���   
        int m_length=matrix.length;   
        int n_length=matrix[0].length; 
        //����n*��2n������ʽ�������������ԭ����͵�λ����   
        temp=new double[m_length][2*m_length];   
        back_temp=new double[m_length][n_length];
        //�������صľ���,��ʼ��   
        for(int x=0;x<m_length;x++) {
        	 for(int y=0;y<n_length;y++)   {    		 
        		 back_temp[x][y]=matrix[x][y];   
        	 }     		
        }
        
        
        //��ԭ�����ֵ���� temp���󣬲���ӵ�λ�����ֵ   
        for(int x=0;x<m_length;x++)   
        {   
            for(int y=0;y<temp[0].length;y++)   
            {   
                if(y>=m_length)   
                {   
                    if(x==(y-m_length))    
                        temp[x][y]=1;   
                    else
                        temp[x][y]=0;   
                }   
                else
                {   
                    temp[x][y]=matrix[x][y];   
                }   
            }   
        }   
   
        //��˹��Ԫ�������   
        for(int x=0;x<m_length;x++)   
        {   
            double var=temp[x][x];   
            //�ж϶Խ�����Ԫ���Ƿ�Ϊ0���ǵĻ��������н��н����У���û������������   
            //�����Ϊԭ����û�������Ȼ��ȡֵҪ��Ϊ0���е�ֵ   
            for(int w=x;w<temp[0].length;w++)   
            {   
                if(temp[x][x]==0) //����Խ��ߵ�ֵΪ0�Ļ����������Ľ����н���  
                {   int k;   
                    for(k=x+1;k<temp.length;k++)   
                    {      
                        if(temp[k][k]!=0)  //����ĶԽ����в�Ϊ0�� 
                        {   
                            for(int t=0;t<temp[0].length;t++)//�������е�ֵ   
                            {   //System.out.println(">>>"+k+"<<<");   
                                double tmp=temp[x][t];   
                                temp[x][t]=temp[k][t];   
                                temp[k][t]=tmp;   
                            }   
                        break;   
                        }   
                    }   
                    //System.out.println(""+k);   
                    //��������޷���temp�������߻�Ϊ��λ���󣬷���ԭ����   
                    if(k>=temp.length) {//û���ҵ�����˵��û������󣬷���ԭ�������һ��Ϊ0�����Ҳ�������
                    	System.out.println("û�п������");   
                    	return null;  
                    }
                    var=temp[x][x];   
               
                   
                }   
                temp[x][w] /=var;   //�Ȱѵ�ǰ�еĶԽ��߻�Ϊ1
            }   
            //����x�е�Ԫ�س��Խ����ϵ�Ԫ���ⶼ��Ϊ0����������λ����   
            for(int z=0;z<m_length;z++)   
            {   double var_tmp=0.0;   
                for(int w=x;w<temp[0].length;w++)   
                {   //System.out.println("-"+x+"-"+z+"-"+w+"+++" + temp[z][w]);    
                    if(w==x) var_tmp=temp[z][x];   
                    if(x!=z) temp[z][w] -=(var_tmp*temp[x][w]);   
                           
                }   
            }   
        }   
        //ȡ������ֵ   
        for(int x=0;x<m_length;x++)   
        {   
            for(int y=0;y<m_length;y++)   
            {   
                back_temp[x][y]=temp[x][y+m_length];   
            }      
        }   
        
        return back_temp;   
    } 
	
	
	
	
	public double[][] MulMatrix(double[][] M1,double[][] M2){ //��������ˣ�a[][]*b[][]
		int k1=M1.length;
		int k2=M1[0].length;   
		int k3=M2[0].length; // System.out.println(k1+" "+k2+"  "+k3);
		double[][] res=new double[k1][k3];
		for(int i=0;i<k1;i++){
			for(int j=0;j<k3;j++){
				res[i][j]=0;
				for(int z=0;z<k2;z++){
					 double   b1= M1[i][z]*M2[z][j];  
	                 res[i][j]=res[i][j]+b1;
				}
			}
			//System.out.println();
		}
		return res;
	}
	
	//����ѭ������
	public double[][] genCycleMatrix(double[] vector){
		int n=vector.length;
		double[][]aa=new double[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				//aa[j][i]=vector[(j-i+n)%n];
				aa[i][j]=vector[(j-i+n)%n];
			}
		}
		return aa;
	}
	
	//��ӡ����
	public void printMatrix(double[][] matrix){
		System.out.println("\n");	
		int n=matrix.length;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++){				
					System.out.print(matrix[i][j]+ " ");		
			}
			System.out.println("\n");	
		}
		System.out.println("\n");	
	}
	
	//����
	public double[][] encrypt(double d){  //�������ݵ�Ԫ����
		double[] res=new double[dsum];  
		double sum=0;
		for(int i=0;i<dsum-1;i++) {			
			double ta=(int)(Math.random()*100);
			res[i]=ta;
			sum=sum+ta;
		}
		res[dsum-1]=d-sum;
		double[][] cycleMatrix=genCycleMatrix(res);
		cycleMatrix=MulMatrix(dM,cycleMatrix);
		cycleMatrix=MulMatrix(cycleMatrix,Mi);	
		return cycleMatrix;
	}
	
	//����
	public double decrypt(double[][] en){
		int n=en.length;
		double[][] res=new double[n][n];	
		DecimalFormat to= new DecimalFormat("#.00000");	
		res=MulMatrix(Mi,en);
		res=MulMatrix(res,dM);
		double result=0;
		for(int i=0;i<n;i++)
			result=result+res[0][i];	
		return result;			
	} 

	
	
	
	//�ӡ���������
	public double[][] add_sub(double[][] sn,double[][] rn,int tag){ //�ӡ���������
		double[][] res=new double[sn.length][sn.length];
		if(tag==1){
			for(int i=0;i<sn.length;i++){
				for(int j=0;j<dsum;j++){
					res[i][j]=sn[i][j]+rn[i][j];
				}
			}
		}else{
			for(int i=0;i<sn.length;i++){
				for(int j=0;j<dsum;j++){
					res[i][j]=sn[i][j]-rn[i][j];
				}
			}
		}		
		//after_add_sub(res);
		return res;
	}
	
	
	
	//�������
	public double[][] mul(double[][] sn,double[][] rn){
		double[][]res=MulMatrix(sn,rn);		
		return res;
	}
	
	//�������
	public double[][] div(double[][] sn,double[][] rn){
		
				
		try{
			//Test2 t=new Test2();			
			//double[][] rn_i=t.exec(dsum,rn);//�������
			double[][] rn_i=reverse(rn);//�������
			//double[][] res=new double[sn.length][sn.length];
			double[][] res=MulMatrix(sn,rn_i);
			return res;		
		}catch (Exception e) { 
			e.printStackTrace(); 
			return null;
		}
	}

	
	public static void main(String[] args) {	
		int testtime=1000;
		for(int tt=0;tt<testtime;tt++){
			double sn=100;  //��������
			double rn=200;	//������						           
			double rn2=10;	//������	
			int n=10;
			long seed=47;
			//CESVMCIMP aspe=new CESVMCIMP(da,dm,k);
			CESCMC cescmc=new CESCMC(n,seed);
			
			double[][] en_sn=cescmc.encrypt(sn);			//������������	
			double[][] en_rn=cescmc.encrypt(rn);			//������������	
			double[][] en_rn2=cescmc.encrypt(rn2);			//������������	
			/*
			for(int i=0;i<aspe.dsum;i++){
				for(int j=0;j<aspe.dsum;j++) {							
					System.out.print(en_sn[i][j]+",");
				}
				System.out.println();
				
			}
			*/
			double de_sn=cescmc.decrypt(en_sn);		//����������		
			System.out.println("����1: "+sn);
			System.out.println("����1����: "+de_sn);
			de_sn=cescmc.decrypt(en_rn);		//����������			
			//System.out.println("����2: "+rn);		
			//System.out.println("����2����: "+de_sn);
			de_sn=cescmc.decrypt(en_rn2);		//����������			
			//System.out.println("����3: "+rn2);		
			//System.out.println("����3����: "+de_sn);
			
			
			
			double[][] res_add_e=cescmc.add_sub(en_sn,en_rn,1);  //�ӷ�����						
			double[][] res_sub_e=cescmc.add_sub(en_sn,en_rn,2);  
				
			//double res_add_d=aspe.after_add_sub(res_add_e);  //�ӷ�����������		
			//double res_sub_d=aspe.after_add_sub(res_sub_e);
			double res_add_d=cescmc.decrypt(res_add_e);  //�ӷ�����������		
			double res_sub_d=cescmc.decrypt(res_sub_e);
			
			double[][] res_sub_e2=cescmc.add_sub(res_sub_e,en_rn2,1);  
			//double res_add_d2=aspe.after_add_sub(res_sub_e2);  //�ӷ�����������
			double res_add_d2=cescmc.decrypt(res_sub_e2);  //�ӷ�����������
			
			
			//System.out.println("�ӷ�����: "+res_add_d);
			//System.out.println("��������: "+res_sub_d);
			//System.out.println("��������Ӽ�����: "+res_add_d2);
			
			
			double[][] res_mul_e=cescmc.mul(en_sn,en_rn);//�˷�
			//double res_mul_d=aspe.after_mul(res_mul_e);
			double res_mul_d=cescmc.decrypt(res_mul_e);
			//System.out.println("�˷�����: "+res_mul_d);
			
			
			
			
			double[][] res_mul_e2=cescmc.mul(res_mul_e,en_rn2);
			//double res_mul_d2=aspe.after_mul(res_mul_e2);
			double res_mul_d2=cescmc.decrypt(res_mul_e2);
			//System.out.println("�����˷�����: "+res_mul_d2);
			
			
			
			double[][] res_div_e=cescmc.div(en_sn,en_rn);//����
			//double res_div_d=aspe.after_div(res_div_e);
			double res_div_d=cescmc.decrypt(res_div_e);
			//System.out.println("��������: "+res_div_d);
			
			
			
			
			
			double[][] res_div_e2=cescmc.div(res_div_e,en_rn2);//����
			//double res_div_d2=aspe.after_div(res_div_e2);
			double res_div_d2=cescmc.decrypt(res_div_e2);
			//System.out.println("������������: "+res_div_d2);

			
			double[][] res_mul_add=cescmc.add_sub(res_add_e,res_mul_e2,1);
			//double res_mul_d2=aspe.after_mul(res_mul_e2);
			double res_mul_add_dec=cescmc.decrypt(res_mul_add);
			//System.out.println("�˷�����ӽ���: "+res_mul_add_dec);
			
			
			double[][] res_div_add=cescmc.add_sub(res_add_e,res_div_e,1);
			//double res_mul_d2=aspe.after_mul(res_mul_e2);
			double res_div_add_dec=cescmc.decrypt(res_div_add);
			//System.out.println("��������ӽ���: "+res_div_add_dec);
			
			
			double[][] res_div_add_mul=cescmc.mul(res_div_add,en_rn2);
			//double res_mul_d2=aspe.after_mul(res_mul_e2);
			double res_div_add_mul_dec=cescmc.decrypt(res_div_add_mul);
			//System.out.println("���������,����˽���: "+res_div_add_mul_dec);
		}
			/*
			String en_str="";
			for(int i=0;i<en_sn[0].length;i++){
				en_str=en_str+en_sn[0][i]+",";
			}
//			System.out.println(en_str);
			
			double de_sn=aspe.decrypt(en_sn)[0];		//����������		
				
//			System.out.println(de_sn);
			
			double[] en_rn=aspe.encrypt(rn)[0];			//����������
			
		
			double[][] res_add_e=aspe.add_sub(en_sn,en_rn,1);  //�ӷ�����
			
			
			double[][] res_sub_e=aspe.add_sub(en_sn,en_rn,2);  
			
			
			double[][][] res_mul_e=aspe.mul(en_sn,en_rn,3);
			
				
			double[][][] res_div_e=aspe.mul(en_sn,en_rn,3);
			
		
			double res_add_d=aspe.after_add_sub(res_add_e)[0];  //�ӷ�����������
			
		
			double res_sub_d=aspe.after_add_sub(res_sub_e)[0];
			
			
			double res_mul_d=aspe.after_mul(res_mul_e)[0];
			
			
			double res_div_d=aspe.after_div(res_div_e)[0];
			
			
			System.out.println(res_add_d+",  "+res_sub_d+",  "+res_mul_d+",  "+res_div_d);
			System.out.println(en_str);		
		*/
	}


}
