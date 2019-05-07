package mars.nomad.com.l12_applicationutil.CreateKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import mars.nomad.com.l12_applicationutil.Crypt.NsCrypto;
import mars.nomad.com.l12_applicationutil.Hash.NsHash;


public class MsgKey {

	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	private static final char[] KEY_LIST = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z' };
	
	private static Random rnd = new Random();
	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

	private static String getRandomStr() {
		char[] rchar = { KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)],
				KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)],
				KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)]
				, KEY_LIST[rnd.nextInt(36)] };
		return String.copyValueOf(rchar);
	}
	
	public static String getMsgKey() {
	    return dateFormat.format(new Date(System.currentTimeMillis())) + getRandomStr();
	}

	/**
	 * 유저 id + 현재 시각 + 랜덤 스트링을 기반으로 해시된 키를 생성한다 만든다.
	 * @param userId
	 * @return
	 */
	public static String getMsgKey(String userId) {
		//return dateFormat.format(new Date(System.currentTimeMillis())) + getRandomStr();
		String original = userId + System.currentTimeMillis() + getRandomStr() + atomicInteger.getAndIncrement();
		String encryptResult = NsHash.md5(NsCrypto.AESEncrypt(original, userId));
		if(encryptResult != null){
			return encryptResult;
		}else{
			return original;
		}
	}
}
