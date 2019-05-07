package mars.nomad.com.l12_applicationutil.Date;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import mars.nomad.com.l0_base.Logger.ErrorController;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

public class NSDate {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /*
    public static SimpleDateFormat dateFormatList = new SimpleDateFormat("(yy.MM.dd HH:mm:ss)");
    public static SimpleDateFormat dateFormatMilsec = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static SimpleDateFormat dateFormatList2 = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
    public static SimpleDateFormat dateFormatMain = new SimpleDateFormat("yyyy-MM-dd a H:mm");
    */

    public static SimpleDateFormat dateFormatYearMonth = new SimpleDateFormat("yyyy년 MM월");

    public static SimpleDateFormat dateFormatMilsec = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static SimpleDateFormat dateFormatDefault = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormatCounsel = new SimpleDateFormat("a hh:mm");
    public static SimpleDateFormat dateFormatEvent = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat dateFormatKorea = new SimpleDateFormat("yyyy년 MM월 dd일");
    public static SimpleDateFormat dateFormatEcho = new SimpleDateFormat("MM월 dd일");
    public static SimpleDateFormat dateFormatEcho3 = new SimpleDateFormat("M월 d일");

    public static SimpleDateFormat dateFormatCalendar = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat dateFormatChatDate = new SimpleDateFormat("yyyy년 MM월 dd일 E요일");
    public static SimpleDateFormat dateFormatChatDialog = new SimpleDateFormat("a h:mm");
    public static SimpleDateFormat dateFormatChatDay = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat dateFormatTodayHour = new SimpleDateFormat("HH시간 전");
    public static SimpleDateFormat dateFormatTodayMinute = new SimpleDateFormat("mm분 전");
    public static SimpleDateFormat dateFormatBirthDay = new SimpleDateFormat("yyyy.MM.dd");
    public static SimpleDateFormat dateFormatEcho2 = new SimpleDateFormat("MM.dd");
    public static SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFormatRingTalkDate = new SimpleDateFormat("yyyy.MM.dd a hh:mm");

    public static SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm");

    public static SimpleDateFormat dateFormatTime2 = new SimpleDateFormat("HH:mm:ss");

    public static SimpleDateFormat dateFormatAuthTime = new SimpleDateFormat("mm:ss");

    public static SimpleDateFormat dateFormatEpQna = new SimpleDateFormat("MM/dd");


    public static SimpleDateFormat dateFormatVoteCalendar = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    ;


    public static String getLogDatestr() {
        return dateFormatChatDialog.format(new Date(System.currentTimeMillis()));
    }

    public static String getDatestr() {
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static Long getConiDateTime(String date) {
        try {

            return NSDate.dateFormatDefault.parse(date).getTime();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return 0L;
    }


    public static String getDateBirthDay(String date) {
        String dateStr = "";
        String valueDate = date;
        valueDate = valueDate.substring(0, 19);
        valueDate = valueDate.replaceAll("-", "");
        valueDate = valueDate.replaceAll(":", "");
        valueDate = valueDate.replaceAll(" ", "");
        Date date1 = new Date();
        try {
            date1 = NSDate.dateFormat.parse(valueDate);
        } catch (Exception e) {

        }
        return dateFormatBirthDay.format(date1);
    }

    public static String getToday() {
        String result = "";
        try {
            Date date = new Date(System.currentTimeMillis());
            result = dateFormatEvent.format(date);
        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static String getStrBirthDay(String birth) {
        String result = "";
        try {
            Date date = new Date(birth);
            result = dateFormatKorea.format(date);
        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


    public static String getPeriodStr(String value) {
        String dateStr = "";
        String valueDate = value;
        valueDate = valueDate.substring(0, 19);
        valueDate = valueDate.replaceAll("-", "");
        valueDate = valueDate.replaceAll(":", "");
        valueDate = valueDate.replaceAll(" ", "");


        try {
            Date date = NSDate.dateFormat.parse(valueDate);

            Long regDate = date.getTime();
            Long currentDate = System.currentTimeMillis();
            long bt = currentDate - regDate;

            if (bt > 0) {

                if (bt < 86400000L && bt > 3600000L) {
                    dateStr = dateFormatTodayHour.format(bt);
                    if (dateStr.startsWith("0")) {
                        dateStr = dateStr.substring(1, dateStr.length());
                    }
                } else if (bt < 3600000L) {
                    dateStr = dateFormatTodayMinute.format(bt);
                    if (dateStr.startsWith("0")) {
                        dateStr = dateStr.substring(1, dateStr.length());
                    }
                } else {
                    dateStr = dateFormatEcho.format(regDate);
                    if (dateStr.substring(4, 5).equals("0")) {
                        dateStr = dateStr.substring(0, 4) + dateStr.substring(5, dateStr.length());
                    }
                    if (dateStr.startsWith("0")) {
                        dateStr = dateStr.substring(1, dateStr.length());
                    }
                }
            } else {
                dateStr = dateFormatCounsel.format(regDate);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return dateStr;
    }


    public static String getPeriod(long value) {
        String dateStr = "";


        try {
            Long regDate = value;
            Long currentDate = System.currentTimeMillis();
            long bt = currentDate - regDate;

            if (bt > 0) {
                long xt;

                xt = (long) bt / 31536000000L;
                if (xt > 0) {
                    dateStr = String.valueOf(xt) + "년 전";

                    if (xt == 1) {
                        dateStr = String.valueOf(xt) + "년 전";
                    }

                } else {
                    xt = (long) bt / 2592000000L;
                    if (xt > 0) {
                        // 월 이상으로 표시함.
                        dateStr = String.valueOf(xt) + "달 전";

                        if (xt == 1) {
                            dateStr = String.valueOf(xt) + "달 전";
                        }

                    } else {
                        xt = (long) bt / 86400000L;
                        if (xt > 0) {
                            dateStr = String.valueOf(xt) + "일 전";

                            if (xt == 1) {
                                dateStr = String.valueOf(xt) + "일 전";
                            }

                        } else {
                            xt = (long) bt / 3600000L;
                            if (xt > 0) {
                                dateStr = String.valueOf(xt) + "시간 전";

                                if (xt == 1) {
                                    dateStr = String.valueOf(xt) + "시간 전";
                                }

                            } else {
                                xt = (long) bt / 60000L;
                                if (xt > 0) {
                                    dateStr = String.valueOf(xt) + "분 전";

                                    if (xt == 1) {
                                        dateStr = String.valueOf(xt) + "분 전";
                                    }

                                } else {
                                    xt = (long) bt / 1000L;
                                    if (xt > 0) {
                                        dateStr = String.valueOf(xt) + "초 전";

                                        if (xt == 1) {
                                            dateStr = String.valueOf(xt) + "초 전";
                                        }

                                    } else {
                                        dateStr = "";
                                    }
                                }
                            }
                        }
                    }
                }
            } else {//미래라면 날짜를 돌려준다.
                dateStr = NSDate.dateFormatCalendar.format(new Date(value));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return dateStr;
    }


    public static String getDDdayPeriod(long value) {

        // 0 이하는 값을 입력하지 않은 경우다.
        if (value < 0) {
            return "";
        }

        String dateStr = "종료 ";


        try {
            Long regDate = value;
            Long currentDate = System.currentTimeMillis();
            long bt = regDate - currentDate;

            if (bt > 0) {
                long xt;

                xt = (long) bt / 31536000000L;
                if (xt > 0) {
                    dateStr = String.valueOf(xt) + "년 전";

                    if (xt == 1) {
                        dateStr = String.valueOf(xt) + "년 전";
                    }

                } else {
                    xt = (long) bt / 2592000000L;
                    if (xt > 0) {
                        // 월 이상으로 표시함.
                        dateStr = String.valueOf(xt) + "달 전";

                        if (xt == 1) {
                            dateStr = String.valueOf(xt) + "달 전";
                        }

                    } else {
                        xt = (long) bt / 86400000L;
                        if (xt > 0) {
                            dateStr = String.valueOf(xt) + "일 전";

                            if (xt == 1) {
                                dateStr = String.valueOf(xt) + "일 전";
                            }

                        } else {
                            xt = (long) bt / 3600000L;
                            if (xt > 0) {
                                dateStr = String.valueOf(xt) + "시간 전";

                                if (xt == 1) {
                                    dateStr = String.valueOf(xt) + "시간 전";
                                }

                            } else {
                                xt = (long) bt / 60000L;
                                if (xt > 0) {
                                    dateStr = String.valueOf(xt) + "분 전";

                                    if (xt == 1) {
                                        dateStr = String.valueOf(xt) + "분 전";
                                    }

                                } else {
                                    xt = (long) bt / 1000L;
                                    if (xt > 0) {
                                        dateStr = String.valueOf(xt) + "초 전";

                                        if (xt == 1) {
                                            dateStr = String.valueOf(xt) + "초 전";
                                        }

                                    } else {
                                        dateStr = "";
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                dateStr = "";
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return dateStr;
    }

    /**
     * Calender 타입을 받아서 Vote 스트링으로 파싱하여 돌려준다.
     *
     * @return
     */
    public static String getCalendarToString(Calendar calendar) {

        try {

            return dateFormatVoteCalendar.format(calendar.getTimeInMillis());

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return null;
    }


    /**
     * Date 형태의 스트링을 받아서 Calendar로 돌려줌
     *
     * @param closeDate
     * @return
     */
    public static Calendar getDateStringToCalendar(String closeDate) {

        String date = closeDate;

        Calendar calendar = Calendar.getInstance();

        try {

            date = date.replaceAll("-", "");
            date = date.replaceAll(" ", "");
            date = date.replaceAll("/", "");

            if (closeDate.length() > 10) {    // 연월일시분초가 모두 있는 형태일 경우 (yyyy-MM-dd hh-mm-ss)


                calendar.set(YEAR, Integer.valueOf(date.substring(0, 4)));    // 연
                calendar.set(Calendar.MONTH, Integer.valueOf(date.substring(4, 6)));   // 월
                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(6, 8)));    // 일
                calendar.set(HOUR_OF_DAY, Integer.valueOf(date.substring(8, 10)));   // 시
                calendar.set(MINUTE, Integer.valueOf(date.substring(10, 12)));   // 분
                calendar.set(SECOND, Integer.valueOf(date.substring(12, 14)));   // 초
            } else {  // 연월일 만 존재하는 경우 (yyyy-MM-dd)
                calendar.set(YEAR, Integer.valueOf(date.substring(0, 4)));    // 연
                calendar.set(Calendar.MONTH, Integer.valueOf(date.substring(4, 6)));   // 월
                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(6, 8)));    // 일
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }


        return calendar;
    }

    public static String getTodayDate() {

        try {


            Calendar calendar = Calendar.getInstance();

            return dateFormatChatDate.format(calendar.getTimeInMillis());

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return null;
    }

    public static String getTodayTime() {

        try {

            Calendar calendar = Calendar.getInstance();


            return dateFormatChatDialog.format(calendar.getTimeInMillis());

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return null;
    }

    public static String getPeriod(String param) {

        long value = 0;

        String valueDate = param;
/*        valueDate = valueDate.substring(0, 19);
        valueDate = valueDate.replaceAll("-", "");
        valueDate = valueDate.replaceAll(":", "");
        valueDate = valueDate.replaceAll(" ", "");*/

        try {

            Date date = NSDate.dateFormatMilsec.parse(valueDate);
            value = date.getTime();

        } catch (Exception e) {
            //ErrorController.showError(e);

            try {

                Date date = NSDate.dateFormatDefault.parse(valueDate);
                value = date.getTime();

            } catch (Exception ee) {
                // ErrorController.showError(e);
            }
        }
        String dateStr = "";


        try {
            Long regDate = value;
            Long currentDate = System.currentTimeMillis();
            long bt = currentDate - regDate;

            if (bt > 0) {
                long xt;

                xt = (long) bt / 31536000000L;
                if (xt > 0) {
                    dateStr = String.valueOf(xt) + "년 전";

                    if (xt == 1) {
                        dateStr = String.valueOf(xt) + "년 전";
                    }

                } else {
                    xt = (long) bt / 2592000000L;
                    if (xt > 0) {
                        // 월 이상으로 표시함.
                        dateStr = String.valueOf(xt) + "달 전";

                        if (xt == 1) {
                            dateStr = String.valueOf(xt) + "달 전";
                        }

                    } else {
                        xt = (long) bt / 86400000L;
                        if (xt > 0) {
                            dateStr = String.valueOf(xt) + "일 전";

                            if (xt == 1) {
                                dateStr = String.valueOf(xt) + "일 전";
                            }

                        } else {
                            xt = (long) bt / 3600000L;
                            if (xt > 0) {
                                dateStr = String.valueOf(xt) + "시간 전";

                                if (xt == 1) {
                                    dateStr = String.valueOf(xt) + "시간 전";
                                }

                            } else {
                                xt = (long) bt / 60000L;
                                if (xt > 0) {
                                    dateStr = String.valueOf(xt) + "분 전";

                                    if (xt == 1) {
                                        dateStr = String.valueOf(xt) + "분 전";
                                    }

                                } else {
                                    xt = (long) bt / 1000L;
                                    if (xt > 0) {
                                        dateStr = String.valueOf(xt) + "초 전";

                                        if (xt == 1) {
                                            dateStr = String.valueOf(xt) + "초 전";
                                        }

                                    } else {
                                        dateStr = "지금 막";
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                dateStr = "지금 막";
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return dateStr;
    }

    /**
     * 입력받은 날짜가 현재날짜보다 뒤인지를 구분한다.
     *
     * @param vote_close_date
     * @return
     */
    public static boolean isCloseDate(String vote_close_date) {

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date result = df.parse(vote_close_date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(result);

            if (calendar.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                return true;
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return false;
    }


    /**
     * 시간을 밀리세컨드로 받아서 yyyy-MM-dd 타입으로 돌려준다.
     *
     * @return
     */
    public static String transStringToDateTime(long milliTime) {

        try {

            String formatDate = dateFormatDate.format(milliTime);

            return formatDate;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return "";
    }

    public static String getAuthTime(int timerTime) {

        try {

            Calendar todayCalendar = Calendar.getInstance();

            Calendar authTime = Calendar.getInstance();

            authTime.add(SECOND, timerTime);

            String formatTime = dateFormatAuthTime.format(authTime.getTimeInMillis() - todayCalendar.getTimeInMillis());

            return formatTime;
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return "";
    }

    /**
     * 생일로부터 나이를 반환한다.
     *
     * @param date
     * @return
     */
    public static String getAge(Date date) {

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int age = today.get(YEAR) - dob.get(YEAR) + 1;

/*        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }*/

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static int countDaysBetween(Date date1, Date date2) {

        Calendar c1 = removeTime(from(date1));
        Calendar c2 = removeTime(from(date2));

        if (c1.get(YEAR) == c2.get(YEAR)) {

            return Math.abs(c1.get(DAY_OF_YEAR) - c2.get(DAY_OF_YEAR)) + 1;
        }
        // ensure c1 <= c2
        if (c1.get(YEAR) > c2.get(YEAR)) {
            Calendar c = c1;
            c1 = c2;
            c2 = c;
        }
        int y1 = c1.get(YEAR);
        int y2 = c2.get(YEAR);
        int d1 = c1.get(DAY_OF_YEAR);
        int d2 = c2.get(DAY_OF_YEAR);

        return d2 + ((y2 - y1) * 365) - d1 + countLeapYearsBetween(y1, y2) + 1;
    }

    private static int countLeapYearsBetween(int y1, int y2) {

        if (y1 < 1 || y2 < 1) {
            throw new IllegalArgumentException("Year must be > 0.");
        }
        // ensure y1 <= y2
        if (y1 > y2) {
            int i = y1;
            y1 = y2;
            y2 = i;
        }

        int diff = 0;

        int firstDivisibleBy4 = y1;
        if (firstDivisibleBy4 % 4 != 0) {
            firstDivisibleBy4 += 4 - (y1 % 4);
        }
        diff = y2 - firstDivisibleBy4 - 1;
        int divisibleBy4 = diff < 0 ? 0 : diff / 4 + 1;

        int firstDivisibleBy100 = y1;
        if (firstDivisibleBy100 % 100 != 0) {
            firstDivisibleBy100 += 100 - (firstDivisibleBy100 % 100);
        }
        diff = y2 - firstDivisibleBy100 - 1;
        int divisibleBy100 = diff < 0 ? 0 : diff / 100 + 1;

        int firstDivisibleBy400 = y1;
        if (firstDivisibleBy400 % 400 != 0) {
            firstDivisibleBy400 += 400 - (y1 % 400);
        }
        diff = y2 - firstDivisibleBy400 - 1;
        int divisibleBy400 = diff < 0 ? 0 : diff / 400 + 1;

        return divisibleBy4 - divisibleBy100 + divisibleBy400;
    }


    public static Calendar from(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }


    public static Calendar removeTime(Calendar c) {

        c.set(HOUR_OF_DAY, 0);
        c.set(MINUTE, 0);
        c.set(SECOND, 0);
        c.set(MILLISECOND, 0);

        return c;
    }

    /**
     * 두 날짜의 차이만큼의 Month를 돌려준다
     * @param one
     * @param two
     * @return
     */
    public static int subMonth(Calendar one, Calendar two) {
        try {
            Calendar startCalendar = one;

            Calendar endCalendar = two;


            int subMonth = startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH);

            int startYear = startCalendar.get(Calendar.YEAR);

            int endYear = endCalendar.get(Calendar.YEAR);


            int subYear = (startYear - endYear) * 12;

            return (subMonth + subYear);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return 0;
    }
}
