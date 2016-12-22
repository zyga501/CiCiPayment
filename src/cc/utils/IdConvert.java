package cc.utils;

public class IdConvert {
    public static void main(String[] args) throws Exception {
        long encryptionId = DecryptionId(196656751516138077L);
        System.out.println(encryptionId);
    }

    private final static long twepoch = 1361753741828L;

    public static long EncryptionId(long id) {
        return (id * 100 + System.currentTimeMillis() % 100) ^ 1361753741828L;
    }

    public static long DecryptionId(long id) {
        return (id ^ 1361753741828L) / 100;
    }
}
