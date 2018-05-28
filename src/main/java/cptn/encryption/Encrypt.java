package cptn.encryption;

/**
 * 加密解密接口
 * @author cptn
 *
 */
public interface Encrypt extends Cipher {
    byte[] encode(final byte[] src) throws Exception;
}
