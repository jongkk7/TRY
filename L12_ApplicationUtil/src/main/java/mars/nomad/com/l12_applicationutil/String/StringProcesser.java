package mars.nomad.com.l12_applicationutil.String;


import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;

import mars.nomad.com.l0_base.Callback.CommonCallback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class StringProcesser {

    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
    //자음
    private static final char[] INITIAL_SOUND = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    private static String[] engFilter = {"a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y",
            "z"};

    public static String[] numFilter = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

    public static String[] espFilter = {"!", "@", "#", "$", "%"
            , "^", "&", "*", "(", ")"
            , ".", ",", "/", "?", "'"
            , "\"", "\\", "-", "_", "="
            , "+", "|", "`", "~", "\\s+", " "};

    public static boolean isExistNumber(String input) {

        boolean result = false;

        try {

            for (String s : StringProcesser.numFilter) {


                if (input.contains(s)) {

                    result = true;
                    break;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public static boolean isExistEsp(String input) {

        boolean result = false;

        try {

            for (String s : StringProcesser.espFilter) {


                if (input.contains(s)) {

                    result = true;
                    break;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 오직 한글만 들어있는지 체크한다.
     *
     * @param input
     * @return
     */
    public static boolean containsOnlyKorean(String input) {

        try {

//            if (StringChecker.isStringNotNull(input)) {
//
//                for (String s : engFilter) {
//
//                    if (input.contains(s)) {
//
//                        return false;
//                    }
//                }
//
//                for (String s : numFilter) {
//
//                    if (input.contains(s)) {
//
//                        return false;
//                    }
//                }
//
//                for (String s : espFilter) {
//
//                    if (input.contains(s)) {
//
//                        return false;
//                    }
//                }
//
//
//            }

            return Pattern.matches("^[가-힣]*$", input);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return true;
    }


    /**
     * 글의 Tag부분("#"로 시작하는 문자)에 Html 태그가 마크업된 string을 돌려준다.
     *
     * @return
     */
    public static String getTagHtmlString(String original) {

        List<Integer[]> tagPoints = new ArrayList<>();

        setTagPoint(original, tagPoints);

        String htmlText = original;

        try {
            String startTag = "<span style= 'color: #002F6A'><b>";
            String endTag = "</b></span>";

            Collections.reverse(tagPoints);

            for (Integer[] tagPoint : tagPoints) {

                ErrorController.showMessage("tagPoint : " + tagPoint[0] + ", " + tagPoint[1]);

                String tempBack = htmlText.substring(tagPoint[1]);
                String tempStart = htmlText.substring(0, tagPoint[1]);

                htmlText = tempStart + endTag + tempBack;

                String tempBack2 = htmlText.substring(tagPoint[0]);
                String tempStart2 = htmlText.substring(0, tagPoint[0]);

                htmlText = tempStart2 + startTag + tempBack2;

                ErrorController.showMessage("Reduced String = " + htmlText);
            }


            htmlText = htmlText.replace("\n", "<br/>");
        } catch (Exception e) {
            ErrorController.showError(e);
        }


        return htmlText;
    }

    /**
     * 특정 텍스트의 위치를 알려준다.
     *
     * @param original
     */
    public static void setTextPoint(String original, String targetString, List<Integer[]> list) {

        int max = original.length();

        boolean isBoldData = true;

        int savedBoldStartIndex = -1;

        while (isBoldData) {

            if (savedBoldStartIndex == -1) {

                savedBoldStartIndex = original.indexOf(targetString);


            } else {
                savedBoldStartIndex = original.indexOf(targetString, savedBoldStartIndex);

            }

            if (savedBoldStartIndex != -1) {
                list.add(new Integer[]{savedBoldStartIndex, savedBoldStartIndex + targetString.length()});
                savedBoldStartIndex = savedBoldStartIndex + 1;
            } else {
                isBoldData = false;
            }

        }//end of for
    }


    /**
     * 태그 좌표를 해쉬 태그에 삽입
     *
     * @param original
     */
    public static void setTagPoint(String original, List<Integer[]> list) {

        int max = original.length();

        boolean isTagStarted = false;

        int savedTagStartIndex = -1;

        for (int i = 0; i < max; i++) {

            if (!isTagStarted) {

                if (original.charAt(i) == '#') {//#으로 시작하는 단어 찾음.

                    isTagStarted = true;
                    savedTagStartIndex = i;
                }

            } else {

                if (original.charAt(i) == ' ' || original.charAt(i) == '\n' || original.charAt(i) == '#') {//공백 혹은 라인스킵 체크

                    if (original.charAt(i) != '#') {
                        Integer[] toSave = {savedTagStartIndex, i};
                        list.add(toSave);

                        savedTagStartIndex = 0;
                        isTagStarted = false; //다시 #부터 찾음.
                    } else {
                        Integer[] toSave = {savedTagStartIndex, i};
                        list.add(toSave);
                        savedTagStartIndex = i;
                    }

                } else if (i == max - 1) {//마지막 항목이 라인스킵도 뭐도 아닌 경우
                    if (savedTagStartIndex != -1) {
                        Integer[] toSave = {savedTagStartIndex, max};
                        list.add(toSave);
                    }
                }
            }

        }//end of for
    }


    /**
     * 해당 문자가 INITIAL_SOUND인지 검사.
     *
     * @param searchar
     * @return
     */
    private static boolean isInitialSound(char searchar) {
        for (char c : INITIAL_SOUND) {
            if (c == searchar) {
                return true;
            }
        }
        return false;
    }

    /**
     * 해당 문자의 자음을 얻는다.
     *
     * @param c 검사할 문자
     * @return
     */
    private static char getInitialSound(char c) {
        int hanBegin = (c - HANGUL_BEGIN_UNICODE);
        int index = hanBegin / HANGUL_BASE_UNIT;
        return INITIAL_SOUND[index];
    }

    /**
     * 해당 문자가 한글인지 검사
     *
     * @param c 문자 하나
     * @return
     */
    private static boolean isHangul(char c) {
        return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
    }


    /**
     * 검색을 한다. 초성 검색 완벽 지원함.
     *
     * @param value  : 검색 대상 ex> 초성검색합니다
     * @param search : 검색어 ex> ㅅ검ㅅ합ㄴ
     * @return 매칭 되는거 찾으면 true 못찾으면 false.
     */
    public static boolean matchString(String value, String search) {
        ErrorController.showMessage("StringProcessor : search string : " + search);
        int t = 0;
        int seof = value.length() - search.length();
        int slen = search.length();
        if (seof < 0)
            return false; //검색어가 더 길면 false를 리턴한다.
        for (int i = 0; i <= seof; i++) {
            t = 0;
            while (t < slen) {
                if (isInitialSound(search.charAt(t)) == true && isHangul(value.charAt(i + t))) {
                    //만약 현재 char이 초성이고 value가 한글이면
                    if (getInitialSound(value.charAt(i + t)) == search.charAt(t))
                        //각각의 초성끼리 같은지 비교한다
                        t++;
                    else
                        break;
                } else {
                    //char이 초성이 아니라면
                    if (value.charAt(i + t) == search.charAt(t))
                        //그냥 같은지 비교한다.
                        t++;
                    else
                        break;
                }
            }
            if (t == slen)
                return true; //모두 일치한 결과를 찾으면 true를 리턴한다.
        }
        return false; //일치하는 것을 찾지 못했으면 false를 리턴한다.
    }


    //                                              ㄱ      ㄲ      ㄴ      ㄷ      ㄸ      ㄹ      ㅁ      ㅂ       ㅃ       ㅅ      ㅆ      ㅇ      ㅈ       ㅉ      ㅊ      ㅋ       ㅌ      ㅍ      ㅎ
    private final static char[] ChoSung = {0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};
    //                                              ㅏ      ㅐ      ㅑ      ㅒ      ㅓ      ㅔ      ㅕ      ㅖ       ㅗ       ㅘ      ㅙ      ㅚ      ㅛ       ㅜ      ㅝ      ㅞ       ㅟ      ㅠ      ㅡ      ㅢ      ㅣ
    private final static char[] JungSung = {0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163};
    //                                                ㄱ      ㄲ      ㄳ      ㄴ      ㄵ      ㄶ      ㄷ       ㄹ       ㄺ      ㄻ      ㄼ      ㄽ       ㄾ      ㄿ      ㅀ       ㅁ       ㅂ      ㅄ      ㅅ      ㅆ      ㅇ      ㅈ       ㅊ      ㅋ      ㅌ       ㅍ      ㅎ
    private final static char[] JongSung = {0, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};


    public static String getJaso(String $str) {
        int a; // 자소
        String result = $str;
        char ch = $str.charAt(0);

        // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
        if (ch >= 0xAC00 && ch <= 0xD7A3) {
            a = (ch - 0xAC00) / (21 * 28);
            result = ChoSung[a] + "";
        }
        return result;
    }

    /**
     * 한글 자소 분리
     *
     * @param $str
     * @return 김태희 -> ㄱㅣㅁㅌㅐㅎㅢ
     */
    public static String hangulToJaso(String $str) {
        try {

            $str = $str.replace(" ", "");
            $str = $str.replace("0", "");
            $str = $str.replace("1", "");
            $str = $str.replace("2", "");
            $str = $str.replace("3", "");
            $str = $str.replace("4", "");
            $str = $str.replace("5", "");
            $str = $str.replace("6", "");
            $str = $str.replace("7", "");
            $str = $str.replace("8", "");
            $str = $str.replace("9", "");

            // 유니코드 한글 문자열을 입력 받음

            int a, b, c; // 자소 버퍼: 초성/중성/종성 순
            String result = "";

            for (int i = 0; i < $str.length(); i++) {
                char ch = $str.charAt(0);

                if (ch >= 0xAC00 && ch <= 0xD7A3) { // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
                    c = ch - 0xAC00;
                    a = c / (21 * 28);
                    c = c % (21 * 28);
                    b = c / 28;
                    c = c % 28;
                    result = result + ChoSung[a] + JungSung[b];
                    if (c != 0)
                        result = result + JongSung[c]; // c가 0이 아니면, 즉 받침이 있으면
                } else {
                    result = result + ch;
                }
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }


    public static int hangulToJasoInt(String $str) {
        try {
            //이름에서 숫자 등 제거
            $str = $str.replace(" ", "");
            $str = $str.replace("0", "");
            $str = $str.replace("1", "");
            $str = $str.replace("2", "");
            $str = $str.replace("3", "");
            $str = $str.replace("4", "");
            $str = $str.replace("5", "");
            $str = $str.replace("6", "");
            $str = $str.replace("7", "");
            $str = $str.replace("8", "");
            $str = $str.replace("9", "");


            // 유니코드 한글 문자열을 입력 받음
            int a, b, c; // 자소 버퍼: 초성/중성/종성 순
            int result = 0;

            for (int i = 0; i < $str.length(); i++) {
                char ch = $str.charAt(0);

                if (ch >= 0xAC00 && ch <= 0xD7A3) { // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
                    c = ch - 0xAC00;
                    a = c / (21 * 28);
                    c = c % (21 * 28);
                    b = c / 28;
                    c = c % 28;
                    result = result + a + b;
                    if (c != 0)
                        result = c; // c가 0이 아니면, 즉 받침이 있으면
                } else {
                    result = result + ch;
                }
            }
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getMbSize(String source) {

        try {

            long fileSize = Long.parseLong(source);

            double fileSizeDouble = fileSize;

            double result = (fileSizeDouble / 1024.0) / 1024.0;

            Double truncatedDouble = BigDecimal.valueOf(result)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();

            return truncatedDouble + "MB";

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
        return "0MB";
    }

    public static String removeFirstComma(String likeString) {
        try {

            if (likeString != null && !likeString.equals("") && likeString.length() > 0) {
                return likeString.substring(1);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return "";
    }

    /**
     * original에서 target을 제거한다.
     *
     * @param original 원본 스트링
     * @param target   제거할 부분 문자열
     * @return
     */
    public static String removeString(String original, String target) {

        String result = original;

        try {

            result = original.replace(target, "");

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


    /**
     * 약관관련 위치 확인
     *
     * @param originalText
     */
    private static Integer[] setTagPoint(String originalText, String tag) {

        Integer[] list = new Integer[2];


        list[0] = originalText.indexOf(tag);
        list[1] = list[0] + tag.length();

        return list;

    }

    /**
     * 특정 문자를 Bold 처리하여 SpannableString을 리턴한다.
     *
     * @param contentTxt
     * @return
     */
    public static SpannableString getBoldString(String contentTxt, String boldTxt) {


        SpannableString ss = new SpannableString(contentTxt);

        try {

            List<Integer[]> tagPoints = new ArrayList<>();

            setTextPoint(contentTxt, boldTxt, tagPoints);

            for (Integer[] tagPoint : tagPoints) {

                ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), tagPoint[0], tagPoint[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return ss;
    }


    /**
     * 특정 문자를 컬러처리하여 SpannableString을 리턴한다.
     *
     * @param contentTxt
     * @return
     */
    public static SpannableString getColorString(Context context, String contentTxt, String targetTxt, int colorId) {


        SpannableString ss = new SpannableString(contentTxt);

        try {

            List<Integer[]> tagPoints = new ArrayList<>();

            setTextPoint(contentTxt, targetTxt, tagPoints);

            for (Integer[] tagPoint : tagPoints) {
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorId)), tagPoint[0], tagPoint[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return ss;
    }

    /**
     * 태그를 셋팅하여 SpannableString을 리턴한다.
     *
     * @param contentTxt
     * @return
     */
    public static SpannableString getTagString(Context context, int colorId, String contentTxt, final CommonCallback.SingleObjectCallback<String> mCallback) {


        SpannableString ss = new SpannableString(contentTxt);

        try {

            List<Integer[]> tagPoints = new ArrayList<>();

            setTagPoint(contentTxt, tagPoints);

            for (Integer[] tagPoint : tagPoints) {

                final String savedTag = contentTxt.substring(tagPoint[0], tagPoint[1]);

                ClickableSpan span1 = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        ErrorController.showMessage("Click TAG [0]: " + savedTag);
                        mCallback.onSuccess(savedTag);
                    }
                };
                ss.setSpan(span1, tagPoint[0], tagPoint[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorId)), tagPoint[0], tagPoint[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), tagPoint[0], tagPoint[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return ss;
    }

    public static String getFormattedNumber(int number) {

        String result = "0";

        try {

            result = NumberFormat.getNumberInstance(Locale.getDefault()).format(number);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


    public static String getFormattedNumber(long number) {

        String result = "0";

        try {

            result = NumberFormat.getNumberInstance(Locale.getDefault()).format(number);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 스트링을 받아서 그 스트링이 1자리일 경우 앞에 0을 붙이는 유틸
     *
     * @param date
     * @return
     */
    public static String datePadding(String date) {

        try {

            if (date.length() == 1) {
                return "0" + date;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return date;
    }

    /**
     * 스트링을 받아서 그 스트링이 1자리일 경우 앞에 0을 붙이는 유틸
     *
     * @param date
     * @return
     */
    public static String datePadding(int date) {

        try {

            if (date < 10) {
                return "0" + date;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return date + "";
    }

    /**
     * 2로 나눈 후, 소수점이 있으면 한자리까지만, 없다면 정수형으로 리턴한다.
     *
     * @param original
     * @return
     */
    public static String getFormattedDouble(int original) {

        String result = "0";

        try {

            double originalDouble = (double) original;

            double originalResult = originalDouble / 2.0;

            int resultInt = (int) originalResult;

            if (originalResult - (double) resultInt > 0) {//소수점이 있다.

                result = resultInt + ".5";

            } else {//소수점이 없다.

                result = resultInt + "";
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public static String convertToPyung(double meterSquare) {

        String result = "";

        try {

            double resultDouble = meterSquare * 0.3025;

            result = Math.round(resultDouble) + "";


        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public static boolean checkValueNotNull(String value, String fault, final CommonCallback.SingleObjectCallback callback) {

        try {

            if (!StringChecker.isStringNotNull(value)) {

                callback.onFailed(fault);
                return false;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return true;
    }

    /**
     * 약관을 셋팅하여 SpannableString을 리턴한다.
     * Span에는 mCallback에 Url을 넣어서 돌려주고
     * 받은 후 한번에 웹뷰로 연결한다.
     *
     * @param contentTxt
     * @return
     */
    public static SpannableString getAgreementString(String contentTxt, final CommonCallback.DoubleObjectCallback<String, String> mCallback) {


        SpannableString ss = new SpannableString(contentTxt);

        try {

            // 약관
            Integer[] termPoint = setTagPoint(contentTxt, "Terms of Use");

            ClickableSpan termSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {

                    mCallback.onSuccess("Terms of Use", "/m/terms/terms_rule");
                }
            };


            ss.setSpan(termSpan, termPoint[0], termPoint[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), termPoint[0], termPoint[1], 0);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), termPoint[0], termPoint[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // 개인정보취급방침
            Integer[] dataPolicyPoint = setTagPoint(contentTxt, "Privacy Policy");

            ClickableSpan dataPolicySpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    // 데이터 정책
                    mCallback.onSuccess("Privacy Policy", "/m/terms/terms_private");

                }
            };

            ss.setSpan(dataPolicySpan, dataPolicyPoint[0], dataPolicyPoint[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), dataPolicyPoint[0], dataPolicyPoint[1], 0);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), dataPolicyPoint[0], dataPolicyPoint[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return ss;
    }

//    public static void main(String[] args) {
//        String xmlstring = "Здравей' хора";
//        String utf8string = StringHelper.convertToUTF8(xmlstring);
//        for (int i = 0; c < utf8string.length(); ++i) {
//            System.out.printf("%x ", (int) utf8string.charAt(c));
//        }
//    }

    // convert from UTF-8 -> internal Java String format
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

}
