package mars.nomad.com.a0_common.Value;

import android.os.Environment;


/**
 * Created by SJH on 2017-03-02.
 */

public class CommonConstants {

    public static final String NOTI_CHANNEL_NAME = "LINNO";

    public static final boolean DEBUG = true;

    /**
     * 푸시 채널
     */
    public static String MARS_PUSH_CHANNEL = "mars.push.channel";


    //푸시 액션용 상수
    public static final String PUSH_RECEIVED = "com.nomad.mars.push.received";



    /**
     * 화면의 크기
     */
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;

    public static final String P0_BUNDLE_ALBUM_MAX_COUNT = "com.nomad.common.album.max.count";
    public static final String P0_BUNDLE_ALBUM_ACTION = "com.nomad.common.album.action";

    public static final String CAPTURE_PATH = Environment.getExternalStorageDirectory() + "/MarsData";
    public static final String CACHE_DIR = Environment.getExternalStorageDirectory() + "/SpepCache";

    public static long BLE_DELAY = 300;

    public static boolean isApplicationRunning = false;


}
