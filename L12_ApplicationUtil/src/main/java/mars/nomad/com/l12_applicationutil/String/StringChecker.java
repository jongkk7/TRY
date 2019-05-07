package mars.nomad.com.l12_applicationutil.String;

import android.util.Patterns;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJH on 2017-02-28.
 * 간단한 스트링에 대한 스태틱 유틸리티 메소드를 모아둔 클래스.
 */
public class StringChecker {

    /**
     * 스트링의 널체킹을 해준다.
     * @param target 체크할 스트링
     * @return target이 null이 아니거나 비어있지 않으면 true, null이거나 비어있으면 false를 리턴한다.
     */
    public static boolean isStringNotNull(String target){
        if(target == null || target.equals("")){
            return false;
        }
        return true;
    }

    public static boolean containsNumber(String target){

        try{

            for(char c : target.toCharArray()){

                if(Character.isDigit(c)) {
                    return true;
                }
            }

        }catch(Exception e){
            ErrorController.showError(e);
        }
        return false;
    }

    /**
     * URL이 유효한지 검사함.
     * @param urlString
     * @return
     */
    public static String getValidUrlFromString(String urlString){
        String result = null;
        try {
            String [] parts = urlString.split("\\s+");
            for (String part : parts) {
                try {
                    if(part.startsWith("www.") || part.startsWith("naver.")){
                        part = "http://" + part;
                    }
                    URL url = new URL(part);
                    result = part;
                    break;
                } catch (Exception e) {
                    ErrorController.showMessage("[common] not url : " + part);
                }
            }
            return result;
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }

    /**
     * 이메일 체크 함수
     * @param email 체크할 이메일
     * @return 이메일 형식이 맞으면 true 아니면 fasle 반환
     */
    public static boolean isEmailChack(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * 전화번호 체크 함수
     * @param phoneNumber 체크할 전화번호
     * @return 전화번호 형식이 맞으면 true 아니면 false 반환
     */
    public static boolean isPhoneNumber(String phoneNumber){
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }


    public static String addPadding(int month) {
        String result = month + "";

        if(month < 10 && month >= 0){
            result = "0" + month;
        }
        return result;
    }

    public static int getOrderFromJaso(String firstChar) {
        if(firstChar.equals("ㄱ")){
            return 1;
        }else if(firstChar.equals("ㄴ")){
            return 2;
        }else if(firstChar.equals("ㄷ")){
            return 3;
        }else if(firstChar.equals("ㄹ")){
            return 4;
        }else if(firstChar.equals("ㅁ")){
            return 5;
        }else if(firstChar.equals("ㅂ")){
            return 6;
        }else if(firstChar.equals("ㅅ")){
            return 7;
        }else if(firstChar.equals("ㅇ")){
            return 8;
        }else if(firstChar.equals("ㅈ")){
            return 9;
        }else if(firstChar.equals("ㅊ")){
            return 10;
        }else if(firstChar.equals("ㅋ")){
            return 11;
        }else if(firstChar.equals("ㅌ")){
            return 12;
        }else if(firstChar.equals("ㅍ")){
            return 13;
        }else if(firstChar.equals("ㅎ")){
            return 14;
        }else if(firstChar.equals("a")){
            return 15;
        }else if(firstChar.equals("b")){
            return 16;
        }else if(firstChar.equals("c")){
            return 17;
        }else if(firstChar.equals("d")){
            return 18;
        }else if(firstChar.equals("e")){
            return 19;
        }else if(firstChar.equals("f")){
            return 20;
        }else if(firstChar.equals("g")){
            return 21;
        }else if(firstChar.equals("h")){
            return 22;
        }else if(firstChar.equals("i")){
            return 23;
        }else if(firstChar.equals("j")){
            return 24;
        }else if(firstChar.equals("k")){
            return 25;
        }else if(firstChar.equals("l")){
            return 26;
        }else if(firstChar.equals("m")){
            return 27;
        }else if(firstChar.equals("n")){
            return 28;
        }else if(firstChar.equals("o")){
            return 29;
        }else if(firstChar.equals("p")){
            return 30;
        }else if(firstChar.equals("q")){
            return 31;
        }else if(firstChar.equals("r")){
            return 32;
        }else if(firstChar.equals("s")){
            return 33;
        }else if(firstChar.equals("t")){
            return 34;
        }else if(firstChar.equals("u")){
            return 35;
        }else if(firstChar.equals("v")){
            return 36;
        }else if(firstChar.equals("w")){
            return 37;
        }else if(firstChar.equals("x")){
            return 38;
        }else if(firstChar.equals("y")){
            return 39;
        }else if(firstChar.equals("z")){
            return 40;
        }else if(firstChar.equals("1")){
            return 41;
        }else if(firstChar.equals("2")){
            return 42;
        }else if(firstChar.equals("3")){
            return 43;
        }else if(firstChar.equals("4")){
            return 44;
        }else if(firstChar.equals("5")){
            return 45;
        }else if(firstChar.equals("6")){
            return 46;
        }else if(firstChar.equals("7")){
            return 47;
        }else if(firstChar.equals("8")){
            return 48;
        }else if(firstChar.equals("9")){
            return 49;
        }else{
            return 1000;
        }
    }


    /**
     * 2017-04-21 SJH : 전화 번호, 및 예상과 다른 포멧으로 들어오는 변수에 대해서 isValidCellPhoneNumber 메소드가 더 이상 유효하지 못하므로, 신설하였다.
     *
     * @param cellphoneNumber
     * @return
     */
    public static boolean isValidCellPhoneNumber2(String cellphoneNumber) {

        try {

            cellphoneNumber = cellphoneNumber.replace("-", "");

            //없는 값이거나, 길이가 9보다 작거나, 11보다 큰 값은 미리 버린다.
            if (cellphoneNumber == null || cellphoneNumber.length() < 9 || cellphoneNumber.length() > 11) {
                return false;
            }


            if (cellphoneNumber.length() == 11) {//010 6347 8395, 070 6347 8395

                if (cellphoneNumber.startsWith("010") || cellphoneNumber.startsWith("070")) {
                    return true;
                }

            } else if (cellphoneNumber.length() == 10) {//016 347 8395, 032 547 8389

                //|011|016|017|018|019
                if (   cellphoneNumber.startsWith("011") || cellphoneNumber.startsWith("016") || cellphoneNumber.startsWith("017") ||
                        cellphoneNumber.startsWith("018") || cellphoneNumber.startsWith("019") || cellphoneNumber.startsWith("051") ||
                        cellphoneNumber.startsWith("053") || cellphoneNumber.startsWith("032") || cellphoneNumber.startsWith("062") ||
                        cellphoneNumber.startsWith("042") || cellphoneNumber.startsWith("052") || cellphoneNumber.startsWith("044") ||
                        cellphoneNumber.startsWith("031") || cellphoneNumber.startsWith("033") || cellphoneNumber.startsWith("043") ||
                        cellphoneNumber.startsWith("041") || cellphoneNumber.startsWith("063") || cellphoneNumber.startsWith("061") ||
                        cellphoneNumber.startsWith("054") || cellphoneNumber.startsWith("055") || cellphoneNumber.startsWith("064")) {
                    return true;
                }

            } else if (cellphoneNumber.length() == 9) {//02 547 8389

                if(cellphoneNumber.startsWith("02")){
                    return true;
                }

            } else {
                return false;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }


    /**
     * 받아온 문자열이 digit와 char가 둘다 있는지를 판별한다.
     *
     * @param s
     * @return
     */
    public static boolean isDigitCharMix(String s) {

        try {

            boolean isChar = false;
            boolean isDigit = false;

            Pattern charP = Pattern.compile("[a-zA-Z]");
            Pattern digitP = Pattern.compile("[\\d]");


            for (int i = 0; i < s.length(); i++) {

                Matcher charM = charP.matcher(s.charAt(i) + "");

                isChar = charM.matches();

                if (isChar) {
                    break;
                }
            }

            for (int i = 0; i < s.length(); i++) {
                Matcher digitM = digitP.matcher(s.charAt(i) + "");
                isDigit = digitM.matches();

                if (isDigit) {
                    break;
                }
            }

            return isDigit && isChar;

        } catch (Exception e) {
            ErrorController.showError(e);
        }


        return true;

    }

}
