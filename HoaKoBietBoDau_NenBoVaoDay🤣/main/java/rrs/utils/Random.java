package rrs.utils;

public class Random {

    private final static java.util.Random r = new java.util.Random();

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi chứa số" sau khi random
     */
    public static String Number(Object firstText, int length) {
        StringBuilder builder;
        try {
            builder = new StringBuilder(Integer.parseInt(firstText.toString()));
        } catch (NumberFormatException e) {
            builder = new StringBuilder();
        }
        while (builder.length() < length) {
            int i = r.nextInt(122);
            if (i > 47 && i < 58) builder.append((char) i);
        }
        return builder.toString();
    }

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi chứa kí tự in HOA" sau khi random
     */
    public static String UpperCase(String firstText, int length) {
    	 StringBuilder builder = new StringBuilder(firstText);
         while (builder.length() < length) {
             int i = r.nextInt(122);
             if (i > 64 && i < 91) builder.append((char) i);
         }
         return builder.toString();
    }

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi chứa kí tự in thường" sau khi random
     */
    public static String LowerCase(String firstText, int length) {
    	StringBuilder builder = new StringBuilder(firstText);
        while (builder.length() < length) {
            int i = r.nextInt(122);
            if (i > 96 && i < 123) builder.append((char) i);
        } return builder.toString();
    }

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi" sau khi random bao gồm "số", kí tự in "HOA".
     */
    public static String NumAndUpp(String firstText, int length) {
    	StringBuilder builder = new StringBuilder(firstText);
        while (builder.length() < length) {
            int i = r.nextInt(122);
            if (i > 47 && i < 58) builder.append((char) i);
            else if (i > 64 && i < 91) builder.append((char) i);
        } return builder.toString();
    }

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi" sau khi random bao gồm "số", kí tự in "thường".
     */
    public static String NumAndLow(String firstText, int length) {
    	 StringBuilder builder = new StringBuilder(firstText);
         while (builder.length() < length) {
             int i = r.nextInt(122);
             if (i > 47 && i < 58) builder.append((char) i);
             else if (i > 96 && i < 123) builder.append((char) i);
         } return builder.toString();
    }

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi" sau khi random bao gồm kí tự in "HOA" và kí tự in
     * "thường".
     */
    public static String UppAndLow(String firstText, int length) {
    	 StringBuilder builder = new StringBuilder(firstText);
         while (builder.length() < length) {
             int i = r.nextInt(122);
             if (i > 64 && i < 91) builder.append((char) i);
             else if (i > 96 && i < 123) builder.append((char) i);
         } return builder.toString();
    }

    /**
     * @param firstText chuỗi kí tự đầu tiên
     * @param length độ dài chuỗi kí tự
     * @return kết quả "chuỗi" sau khi random bao gồm "số", kí tự in "HOA", kí
     * tự in "thường".
     */
    public static String NumUppLow(String firstText, int length) {
        StringBuilder builder = new StringBuilder(firstText);
        while (builder.length() < length) {
            int i = r.nextInt(122);
            if (i > 47 && i < 58) builder.append((char) i);
            else if (i > 64 && i < 91) builder.append((char) i);
            else if (i > 96 && i < 123) builder.append((char) i);
        } return builder.toString();
    }
}