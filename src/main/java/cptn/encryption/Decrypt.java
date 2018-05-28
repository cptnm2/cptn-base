package cptn.encryption;

/**
 * 加密解密接口
 * @author cptn
 *
 */
public interface Decrypt extends Cipher {
    byte[] decode(final byte[] src) throws Exception;
}
