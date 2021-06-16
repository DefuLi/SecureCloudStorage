package cn.xjtu.iotlab.utils.encdec;

/**
 * OPET V1.2
 * ���÷��鷽ʽ���ܣ�ÿ�����Ķ�Ӧ��ASCII�ַ�����С����blkLen��
 * ������Ķβ���һ�飬����䣻

 * OPET V1.1;
 * �յ����2�ַ����������ӹյ��������division_border(...);
 * ���������������Χ��[0, 1]��Ϊ[ran_fac, 1-ran_fac];

 * OPET V1.0��
 * VL���þ��ȷֲ���
 * GLĬ����4���յ㣬�յ�֮�䲿�ֲ����ۼӣ�
 */
import java.util.Random;

public class OPEART  {
   private String zeros; /**���ĵ���Сֵ��10��0���ַ���.*/
   private String nines; /**���ĵ����ֵ��10��9���ַ���.*/
   private int len; /**ÿ�μ��ܵ�λ��,2. */
   private int depth; /**ѭ������,6.*/
   private int expLen; /**һ�μ���ascii�ĳ���,12.*/
   private long minAll; /**�����������Сֵ.*/
   private long maxAll; /**������������ֵ.*/
   private long key;  /**�ܳ�.*/
   private int nFakeNums; /**С�����,22*/
   private  int divParts; /**�ִ�����,4.*/
   private float ran_fac; /**ֵ��ͼ���򻮷ֵı���ϵ��,��ʼ0.5.*/
   private int blkLen; /**ÿ����ܵ���ʵ����,22,ÿ���2�μ���.*/

     /**
      * No parameters constructor.
      */
    public OPEART() {
 		len = 2;
 		depth = 6;
 		expLen = len * depth;
        StringBuffer sb0 = new StringBuffer(expLen);
        StringBuffer sb1 = new StringBuffer(expLen);
        for (int i = 0; i < expLen; ++i) {
            sb0.append(0);
            sb1.append(9);
       }
        zeros = new String(sb0);
 	    nines = new String(sb1);
 		minAll = 0;
 		maxAll = minAll + (long) Math.pow(10, (expLen + 3));
 		key = 2568941234L;
 		nFakeNums = 22;
 		divParts = 4;
 		ran_fac = 0.5f;
 		blkLen = 22;
  }

    /**
     * compress a long value to a 4-byte string.
     * @param key long type value
     */
    public OPEART(long key) {
		len = 2;
		depth = 6;
		expLen = len * depth;
		StringBuffer sb0 = new StringBuffer(expLen);
	    StringBuffer sb1 = new StringBuffer(expLen);
	    for (int i = 0; i < expLen; ++i) {
	           sb0.append(0);
	           sb1.append(9);
	    }
	    zeros = new String(sb0);
	    nines = new String(sb1);
		minAll = 0;
		maxAll = minAll + (long) Math.pow(10, (expLen + 3));
		this.key = key;
		nFakeNums = 22;
		divParts = 4;
		ran_fac = 0.5f;
		blkLen = 22;
	}

    /**
	 * compress a long value to a 4-byte string.
	 * @param lvalue long type value
	 * @return 4-byte compressed string
	 */
    public String compress_expLen(long lvalue) {
		String res = "";
		for (int i = 0; i < 4; ++i) {
			res += (char) (lvalue >> ((3 - i) * 16));
	    /**16λ��Ϊһ��char����ѹ�����Ӹ�λ����λ����ѹ��*/
		}
		return res;
	}

	/**
	 * decompress 4-byte string to a long value.
	 * @param str 4-byte compressed string
	 * @return res long type value
	 */
    public long decompress_expLen(String str) {
		long res = 0;
		for (int i = 0; i < 4; ++i) {
			/**res*65536ʵ�����ƣ���λ��char��Ӧ�� int ��ÿ��ѭ��ʱ��������16λ*/
			res = res * 65536 + (int) str.charAt(i);
		}
		return res;
	}

	/**
	 * translate an unicode string to its ASCII format.
	 * @param str unicode string.
	 * @return ASCII string.
	 */
	public String string2ascii(String str) {
		String res = "";
		for (int i = 0; i < str.length(); ++i) {
			/**��õ����ַ���ASCII��*/
			res += char2ascii(str.charAt(i));
		}
		return res;
	}

   /**
	 * translate character to ASCII string.
	 * @param ch character.
	 * @return ASCII string of ch.
	 */
	public String char2ascii(char ch) {
		int val = (int) ch;
		String str = "" + val;
		if (val < 256) {
			/**���ֺ�Ӣ��ʱ��λ������3λ��ǰ�油0*/
			return "000".substring(str.length()) + str;
			 }
		else {
			/**����ʱ��ǰ���3����С��5λ��������Ҫ����ǰ���0*/
			return "3" + "00000".substring(str.length()) + str;
		}
	}

	/**
	 * translate ASCII string to character.
	 * @param as ASCII string of a character.
	 * @return character.
	 */
	public char ascii2char(String as) {
		int val = Integer.valueOf(as);
		if (val > 300000) {
			 /**���ģ�ǰ���в�3����Ҫȥ��*/
			return (char) (val - 300000);
		}
		else {
			return (char) val;
		}
	}

	/**
	 * translate an ASCII string to an unicode string.
	 * @param asc ASCII string.
	 * @return unicode string.
	 */
	public String ascii2string(String asc) {
		String res = "";
		int i = 0;
		while (i < asc.length()) {
			char ch = asc.charAt(i);
			if (ch == '3') {
                /**����ʱ��6λһ��*/
				res += ascii2char(asc.substring(i, i + 6));
				i += 6;
			}
			else if (ch < '3') {
				/**Ӣ��ʱ��3λһ��*/
				res += ascii2char(asc.substring(i, i + 3));
				i += 3;
				}
			else {
				break;
			}
		}
		return res;
	}

	/**
	 * encrypt plaintext string to ciphertext string.
	 * @param str plaintext string
	 * @return ciphertext string
	 */
	public String encrypt(String str) {
		String res = "";
		String asc = string2ascii(str);
		/**ÿ��22��ASCIIΪһ��*/
		for (int i = 0; i < asc.length(); i += blkLen) {
			String curBlock = "";
        if (asc.length() < i + blkLen) {  /**��ascʣ��ĳ���С��22ʱ*/	
				curBlock = asc.substring(i, asc.length()); /**ȡ��ʣ�µ�����ֵ*/
				Random random = new Random(curBlock.hashCode()); /**�ַ����Ĺ�ϣֵ���*/
				String len = "" + curBlock.length(); /**len��ֵ��2λ���ڣ�0-21 */
				len = "00".substring(len.length()) + len; /**ʹ��len��ֵΪ2λ,����2λ��ǰ�油0*/
         		if (Integer.valueOf(len) == blkLen - 1) { /**��0��Ϊ�˱���00<�κ�һ���ַ���ASCII��ǰ2λ*/
         			curBlock += "0";
                }
         		else {
				curBlock += "00"; 
         		}
	     	while (curBlock.length() < blkLen) { /**����С��22*/
					/**������6�ı���*/
					if (curBlock.length() % 6 == 0) {
						curBlock += random.nextInt(4); /**����0-3*/
					}
					else {
						curBlock += random.nextInt(10); /**����0-9*/
					}
				}
				curBlock += len; /**������ʵ�ĳ���*/
			}
			else { /**��ascʣ��ĳ��ȴ��ڵ���22ʱ��ȡ22��ASCII�����ں�����볤��22*/
				curBlock = asc.substring(i, i + blkLen) + blkLen;
			}
			for (int j = 0; j < blkLen; j += expLen) { /**24λ��curBlockÿ�μ���12λ*/
				long lRes = encrypt_expLen(curBlock.substring(j, j + expLen));
				res += compress_expLen(lRes);
			}
		}
		return res;
	}

   /**
	 * encrypt 12 ASCII.
	 * @param asc ASCII String
	 * @return res one ciphertext string
	 */
	public long encrypt_expLen(String asc) {
		/**seed=�ܳ׺�����asc��Ϊ�����������������ֻ�����õ�����ֵʱʹ��*/
		long seed = key + Long.valueOf(asc);
		Random random = new Random(seed);
		float r = 0f; /**ֵ��ͼ����ķָ����*/
		long min = minAll; /**�������Сֵ��ֵ�����Сֵ��*/
		long max = maxAll; /**��������ֵ�����������ֵ��*/
		long mid = 0; /**ֵ������ֵ����������Сֵ��*/
		double vPart = 0, gLen = 0; /**vpart��ֵ���ÿ�ݵ�ֵ��glen�Ǽ����ĳ���*/
		int num = 0; /**ֵ��ķ���*/
        for (int dp = 0; dp < depth; ++dp) {
				num = (int) Math.pow(10, len); /**ֵ��ֳ�100��*/
			long divKey = key; /**�ֱ߽������min��max���ܳ�*/
			if (dp > 0) {
				divKey = key + Long.valueOf(asc.substring(0, dp * len));/**��Կ��asc�йأ�ÿ�ζ���ͬ*/
			}
			Random ran = new Random(divKey);
			/**ran_fac=0.5��ʹ��rΪ0.5��1*/
			r = ran_fac + ran.nextFloat() * (1 -  ran_fac);
			/**����ֵ������ֵ��Ϊ min+ԭ����������� r ��*/
			mid = min + (long) ((max - min) * r);
			vPart = ((double) (mid - min)) / num; /**�õ�ÿ��ֵ���ֵ*/
			gLen = max - mid; /**�����ĳ���*/
			int n = Integer.valueOf(asc.substring(dp * len, (dp + 1) * len)); /**ÿ��ȡ2λ*/

			/**[0][0]��С�� n �ı߽�ֵ��[0][1]�Ƕ�Ӧ�ļ����İٷֱ� ��
			  *[1][0]�Ǵ��ڵ���n�ı߽�ֵ��[1][1]�Ƕ�Ӧ�ļ����İٷֱ�*/
			float[][] div = division(n, num, divKey); /**���ֱ߽�*/
			if (n != (int) div[1][0]) { /**n���Ǳ߽�ֵ*/
				/**min=n��ֵ��+��ǰ��ļ����max=min+�Լ���һ��ֵ��*/
				min = min + (long) (n * vPart + div[0][1] * gLen);
				max = min + (long) vPart;
			}
			else { /**n�Ǳ߽�ֵ*/
				/**min=n��ֵ�������ǰ��ļ����max=min+�Լ���һ��ֵ��+�Լ��ļ����*/
				min = min + (long) (n * vPart + div[0][1] * gLen);
				max = min + (long) (vPart + (div[1][1] - div[0][1]) * gLen);
			}
		}
		long res = min + 1 + (long) ((max - min - 2) * random.nextDouble()); /**�õ�����ֵ*/
		return res;
	}

    /**
	 * division four big border and make sure the percent of n.
	 * @param n the 2 number
	 * @param num part of value
	 * @param seed the seed of key
	 * @return res border and percents
	 */
	public float[][] division(int n, int num, long seed) {
		float[][] res = new float[2][2];
		Random random = new Random(seed);
		random.nextFloat();

        /**��100ӳ�䵽0-25�����ǣ�����ٽ��л�ԭ*/
		int numPerPart = num / divParts; /**num/4,ÿ�������е�ĸ���,ֵΪ25*/
		int nPart = n / numPerPart; /** n/25��ȷ�� n ���ڵڼ���������0-24���ڵ�һ����25-49���ڵ�2��,�Դ�����*/
		int nInPart = n - nPart * numPerPart; /**n�ڸ������������е����λ�ã���24�ڵ�һ����24λ��25�ڵڶ�����0λ��26�ڵڶ�����1λ*/

		/**5���յ㣬��һ�������һ��ȷ������0��1,�յ��λ���� 0,25,50,75,100,�����Ƕ�Ӧ��ֵ�������*/
		float[] maxFs = new float[divParts + 1];
		maxFs[0] = 0;
		maxFs[maxFs.length - 1] = 1f;
		/**ȷ��3���м�յ�*/
		division_border(random, maxFs, 0, maxFs.length - 1);
		/**�Ͻ�-�½磬ȷ��n���ڵ�����ռ���ܵİٷֱ�*/
		float maxF = maxFs[nPart + 1] - maxFs[nPart];
		int sumN = 0; /**ֵ�ۼӺ�*/
		float sumF = 0.0f; /**�ٷֱ��ۼӺ�*/

		/**lower bound �±߽磬С��n*/
		res[0][0] = 0;
		res[0][1] = 0;
		/**res[0][0]�洢��ֵsumNС�� n,�� sumN���ڵ���nʱ������ѭ������ res[0][0]��û�б���*/
		while (sumN < nInPart && sumF < maxF) {
			res[0][0] = sumN; /**ֵ�ۼӺ�*/
			res[0][1] = sumF; /**�ٷֱ��ۼӺ�*/
			sumN += 1 + random.nextInt(3); /**�ۼ�  1-3*/
			sumF += random.nextFloat() / nFakeNums; /**r/22*/
		}
		/**upper bound, �ϱ߽磬���ڵ��� n*/
		res[1][0] = numPerPart - 1; /**24,�������ֵ��������*/
		res[1][1] = maxF; /**n��������ռ���ܵİٷֱ�*/
		if (sumN < res[1][0] && sumF < maxF) { /**�ϱ߽�С�ڴ���*/
			res[1][0] = sumN; /**�����ϱ߽��ֵ*/
			res[1][1] = sumF; /**��Ӧ�İٷֱ�*/
		}

		res[0][0] += nPart * numPerPart; /**��ԭ��100*/
		res[1][0] += nPart * numPerPart; /**��ԭ��100*/
		res[0][1] += maxFs[nPart]; /**����ǰ��İٷֱȲ���������Ӧ�İٷֱ�*/
		res[1][1] += maxFs[nPart]; /**����ǰ��İٷֱȲ���������Ӧ�İٷֱ�*/
		return res;
	}

    /**
	 * creat three middle border.
	 * @param ran Random value
	 * @param bdr save three border
	 * @param left the first one of bdr
	 * @param right the last one of bdr
	 */
	public void division_border(Random ran, float[] bdr, int left, int right) {
		if (left + 1 >= right) { /**��ߵ����ұ�-1ʱ������3=4-1ʱ��������ȷ���м�Ĺյ㣬�����ݹ�*/
			return;
		}
		int mid = (left + right) / 2; /**ȷ���м�յ��λ��*/
		/**ԭ������� 0.3-0.7��ֵ��Ϊ�м�յ��ֵ*/
		bdr[mid] = bdr[left] + (0.3f + 0.4f * ran.nextFloat()) * (bdr[right] - bdr[left]);
		division_border(ran, bdr, left, mid); /**�ݹ���� left�� mid ���м�Ĺյ�*/
		division_border(ran, bdr, mid, right); /**�ݹ���� mid�� right �м�Ĺյ�*/
	}

	/**
	 * decrypt ciphertext string to plaintext string.
	 * @param cipher ciphertext string
	 * @return res plaintext string
	 */
	public String decrypt(String cipher) {
		String res = "";
		String asc = "";
		for (int i = 0; i < cipher.length(); i += 4) {
			/**ѹ��ʱ��4��char��ʾһ�����ġ���ѹ������ֵ*/
			long lval = decompress_expLen(cipher.substring(i, i + 4));
			asc += decrypt_expLen(lval); /**���ܵ�������*/
		}
		for (int i = 0; i < asc.length(); i += (blkLen + 2)) { /**ÿ��ȡ24��ASCII*/
			int len = Integer.valueOf(asc.substring(i + blkLen, i + blkLen + 2)); /**ʵ�ʳ���*/
			res += asc.substring(i, i + len); /**��24����ȡlen���ȵ�ֵ*/
		}
		res = ascii2string(res); /**ASCIIת��Ϊ��Ӧ���ַ���*/
		return res;
	}

   /**
	 * decrypt ciphertext.
	 * @param lvalue ciphertext value
	 * @return res one plaintext ASCII string
	 */
	public String decrypt_expLen(long lvalue) {
		String res = "";
		float r = 0f;
		long min = minAll;
		long max = maxAll;
		long mid = 0;
		double vPart = 0, gLen = 0;
		int num = 0;
		for (int dp = 0; dp < depth; ++dp) { /**6��ѭ��*/
				num = (int) Math.pow(10, len); /**ֵ���100��*/
			long divKey = key; /**���¼����min��max���ܳ�*/
			if (dp > 0) {
				divKey = key + Long.valueOf(res);
			}
			Random ran = new Random(divKey);
			r = ran_fac + ran.nextFloat() * (1 - ran_fac);
			mid = min + (long) ((max - min) * r);
			vPart = ((double) (mid - min)) / num;
			gLen = max - mid;
			/**res[0][0]��n,res[0][1]��С��n��ֵ��Ӧ�ļ����ٷֱ�
			 *res[1][0]����ڵ���n��ֵ��res[1][1]���Ӧ�ļ����İٷֱ� */
			float [][]ret = belongTo(lvalue, num, divKey, min, max, r); /**ȷ����������Ӧ��n�����ֵ*/
			int n = (int) ret[0][0]; /**���n*/
			String temp = n + "";
			res += "00".substring(temp.length()) + temp; /**ǰ�油0,ʹ��Ϊ2λ��*/
			if (n != (int) ret[1][0]) { /**n�Ǳ߽�ֵ*/
				/**min=n��ֵ�������ǰ��ļ����max=min+�Լ���һ��ֵ��ͬ����*/
				min = min + (long) (n * vPart + ret[0][1] * gLen);
				max = min + (long) vPart;
			}
			else { /**n�Ǳ߽�ֵ*/
				/**min=n��ֵ�������ǰ��ļ����max=min+�Լ���һ��ֵ��+�Լ��ļ����*/
				min = min + (long) (n * vPart + ret[0][1] * gLen);
				max = min + (long) (vPart + (ret[1][1] - ret[0][1]) * gLen);
			}
		}
		return res;
	}

    /**
	 * division four big border and make sure the percent of n.
	 * @param lvalue ciphertext value
	 * @param num part of value
	 * @param seed the seed of key
	 * @param min min of border
	 * @param max max of border
	 * @param r value and interval percent
	 * @return res border and percents
	 */
	public float[][] belongTo(long lvalue, int num, long seed, long min, long max, float r) {
		float [][]res = new float[2][2];
		int n = 0;
		Random random = new Random(seed);
		random.nextFloat();
		long mid = min + (long) ((max - min) * r);
		double vPart = ((double) (mid - min)) / num;
		double gLen = max - mid; /**double�����Ӿ���*/

		int nPart = 0;
		int numPerPart = num / divParts; /**100/4=25*/
		float[] maxFs = new float[divParts + 1]; /**5���յ�*/
		maxFs[0] = 0;
		maxFs[maxFs.length - 1] = 1f;
		division_border(random, maxFs, 0, maxFs.length - 1); /**�м�3���յ�,ͬ����*/
		/**divParts=4��ȷ��lvalue�����ĸ�������npart=0ʱ��0-24*/
		for (; nPart < divParts; ++nPart) {
			/**lBdr��һ�����������ֵ*/
			long lBdr = min + (long) ((nPart + 1) * numPerPart * vPart + maxFs[nPart + 1] * gLen);
			if (lvalue <= lBdr) { /**lvalueС�ڴ��������ֵ���������������*/
				break;
			}
		}
		/**�����Ĵ�����ռ���ܵİٷֱ�*/
		float maxF = maxFs[nPart + 1] - maxFs[nPart];
		int sumN = 0;
		float sumF = 0;
		/**lower bound �±߽磬С��n
		 *res[0][0]��С��2λ��n��ֵ,res[0][1]�Ƕ�Ӧ�İٷֱ�*/
		res[0][0] = 0;
		res[0][1] = 0;
		long tempMax = min;
		while (sumN < numPerPart - 1 && sumF < maxF) {
			/**tempMax��ֵΪsumN��Ӧ����������ֵ����ֵ��ͼ����ĺͣ�
			 * ֵ�����1����Ϊ�����Լ���ռ����һ��ֵ��ȡ��n��Ӧ���ϱ߽硣*/
			tempMax = min + (long) ((sumN + nPart * numPerPart + 1) * vPart + (sumF + maxFs[nPart]) * gLen);
			if (lvalue <= tempMax) { /**С����������ֵʱ��ȷ������*/
				break;
			}
			res[0][0] = sumN; /**�ۼ�ֵ��*/
			res[0][1] = sumF; /**�ۼӰٷֱȺ�*/
			sumN += 1 + random.nextInt(3);
			sumF += random.nextFloat() / nFakeNums;
		}
		/**upper bound, �ϱ߽磬���ڵ��� n*/
		res[1][0] = numPerPart - 1; /**24,�������ֵ��������*/
		res[1][1] = maxF; /**n��������ռ���ܵİٷֱ�*/
		if (sumN < res[1][0] && sumF < maxF) { /**�ϱ߽�С�ڴ���*/
			res[1][0] = sumN; /**�����ϱ߽��ֵ*/
			res[1][1] = sumF; /**��Ӧ�İٷֱ�*/
		}

		/**ȷ��n��ֵ*/
		n = (int) res[0][0];
		/**С��n��ֵ���м���򣩶�Ӧ����������ֵ����ǰ���tempMax����*/
		tempMax = min + (long) ((n + nPart * numPerPart + 1) * vPart + (res[0][1] + maxFs[nPart]) * gLen);
		double vTemp = 0;
		while (n < res[1][0]) { /**ȷ��n��tempMaxÿ������һ��ֵ��ֵ��ȷ��n*/
			vTemp += vPart;
			++n;
			if (lvalue < tempMax + vTemp) { /**ȷ��n������ȡ<=,��Ϊ�п������ĸպ���min��max��*/
				break;
			}
		}
		res[0][0] = n; /**����n*/
		res[0][0] += nPart * numPerPart; /**��ԭ��100*/
		res[1][0] += nPart * numPerPart; /**��ԭ��100*/
		res[0][1] += maxFs[nPart]; /**����ǰ��İٷֱȲ��������İٷֱ�*/
		res[1][1] += maxFs[nPart]; /**����ǰ��İٷֱȲ��������İٷֱ�*/
		return res;
	}
}
