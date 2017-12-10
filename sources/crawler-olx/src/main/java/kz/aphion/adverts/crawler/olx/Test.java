package kz.aphion.adverts.crawler.olx;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "{\"jsonrpc\":\"2.0\",\"method\":\"ads_search\",\"params\":null,\"id\":1}";
		System.out.println("test:\n" + test);
		
		String testAx = ax(test);
		System.out.println("testAx:\n" + testAx);

		String res = m2914s(testAx, "none");
		System.out.println("hmac-sha1=" + res);
	}

    public static String m2914s(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(new SecretKeySpec(str2.getBytes(), "HmacSHA1"));
            return m2912i(instance.doFinal(str.getBytes()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    private static String m2912i(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >>> 4) & 15;
            int i3 = 0;
            while (true) {
                if (i2 < 0 || i2 > 9) {
                    stringBuffer.append((char) ((i2 - 10) + 97));
                } else {
                    stringBuffer.append((char) (i2 + 48));
                }
                byte b = bArr[i];
                if (i3 > 0) {
                    break;
                }
                i3++;
                i2 = b & 15;
            }
        }
        return stringBuffer.toString();
    }

	
    public static String ax(String str) {
        int i = 0;
        char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder stringBuilder = new StringBuilder();
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if ((charAt >> 7) > 0) {
                stringBuilder.append("\\u");
                stringBuilder.append(cArr[(charAt >> 12) & 15]);
                stringBuilder.append(cArr[(charAt >> 8) & 15]);
                stringBuilder.append(cArr[(charAt >> 4) & 15]);
                stringBuilder.append(cArr[charAt & 15]);
            } else {
                stringBuilder.append(charAt);
            }
            i++;
        }
        return stringBuilder.toString();
    }
}
