package mars.nomad.com.a0_common.Value;

/**
 * Created by SJH on 2017-07-10.
 */

public class FlagManager {


    public static final int p2_tutorial_screen_count = 5;

    //튜토리얼 관련 플래그
    public static final boolean p2_tutorial_enabled = true;

    public static final boolean p2_tutorial_skip_enabled = true;

    public static final boolean p2_tutorial_login_enabled = true;

    public static final boolean p2_tutorial_join_enabled = true;

    // 비밀번호 찾기에서 휴대폰 사용 여부
    public static final boolean p7_find_account_phone_enabled = false;

    // 인증 사용 여부
    public static boolean p4_auth_input_enabled = false;

    public static final boolean p4_auth_input_phone_enabled = true;

    public static final boolean p4_auth_input_email_enabled = true;

    public static final boolean p4_last_name_input_enabled = true;

    public static final boolean p4_birthday_input_enabled = true;

    public static final boolean p4_gender_input_enabled = true;


    public static boolean p9_profile_default_pfback_image = true;

    public static final boolean p9_profile_favorite_enabled = true;

    public static final boolean p9_profile_phone_call_enabled = false;

    public static final boolean p9_profile_enable_status_message = true;


    // 탭바 관련 플래그

    public static boolean p13_tabbar_enabled = false;    // true = 탭바 / false = nav

    public static boolean p13_tabbar_Top_enabled = false;    // true = 위쪽 / false = 아래쪽

    public static boolean p13_navigation_Right_enabled = false;   // true = 오른쪽 / false = 왼쪽

    public static boolean p13_navigation_push_enabled = false;   // true = 밀다 / false = 겹치다

    //2018-01-17 SJH 프리미엄 플래그 추가.

    /**
     * 로그인 프리미엄 플래그
     */
    public static boolean p6_login_is_premium = true;

    /**
     * 더보기 프리미엄 플래그
     */
    public static boolean p13_more_is_premium = true;

    //2018-01-18 SJH 탭 메뉴 플래그 정리.

    /**
     * true : 친구 목록을 사용함, false : 친구 목록을 사용하지 않음.
     */
    public static boolean p11_use_friend_list = true;

    /**
     * true : 채팅을 사용함, false : 채팅을 사용하지 않음.
     */
    public static boolean p12_use_chat = false;

    /**
     * true : 더보기를 사용함, false : 더보기를 사용하지 않음.
     */
    public static boolean p13_use_more = true;

    /**
     * true : 알림을 사용함, false : 알림을 사용하지 않음.
     */
    public static boolean p14_use_alarm = true;

    /**
     * true : 타임라인형 sns를 사용함, false : 타임라인형 sns를 사용하지 않음.
     */
    public static boolean p25_use_time_line = true;

    /**
     * true : 밴드형 sns를 사용함, false : 밴드형 sns를 사용하지 않음.
     */
    public static boolean p27_use_band = true;




    /**
     * 로그인 되어있는지 판단한다.
     */
    public static boolean isLogin = false;

    public static boolean userDataChange = false;
    public static boolean isRunning = false;
}
