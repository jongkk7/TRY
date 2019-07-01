package mars.nomad.com.l12_applicationutil.Mime;


import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc on 2018-02-05.
 */

public class ExtMimeUtil {

    public static String getMimeType(String fileName) {

        String result = "";

        try {

            if (fileName.endsWith(".doc")) {

                result = "application/msword";

            } else if (fileName.endsWith(".dot")) {

                result = "application/msword";

            } else if (fileName.endsWith(".docx")) {

                result = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

            } else if (fileName.endsWith(".dotx")) {

                result = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";

            } else if (fileName.endsWith(".docm")) {

                result = "application/vnd.ms-word.document.macroEnabled.12";

            } else if (fileName.endsWith(".dotm")) {

                result = "application/vnd.ms-word.template.macroEnabled.12";

            } else if (fileName.endsWith(".xls")) {

                result = "application/vnd.ms-excel";

            } else if (fileName.endsWith(".xlt")) {

                result = "application/vnd.ms-excel";

            } else if (fileName.endsWith(".xla")) {

                result = "application/vnd.ms-excel";

            } else if (fileName.endsWith(".xlsx")) {

                result = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

            } else if (fileName.endsWith(".xltx")) {

                result = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";

            } else if (fileName.endsWith(".xlsm")) {

                result = "application/vnd.ms-excel.sheet.macroEnabled.12";

            } else if (fileName.endsWith(".xltm")) {

                result = "application/vnd.ms-excel.template.macroEnabled.12";

            } else if (fileName.endsWith(".xlam")) {

                result = "application/vnd.ms-excel.addin.macroEnabled.12";

            } else if (fileName.endsWith(".xlsb")) {

                result = "application/vnd.ms-excel.sheet.binary.macroEnabled.12";

            } else if (fileName.endsWith(".ppt")) {

                result = "application/vnd.ms-powerpoint";

            } else if (fileName.endsWith(".pot")) {

                result = "application/vnd.ms-powerpoint";

            } else if (fileName.endsWith(".pps")) {

                result = "application/vnd.ms-powerpoint";

            } else if (fileName.endsWith(".ppa")) {

                result = "application/vnd.ms-powerpoint";

            } else if (fileName.endsWith(".pptx")) {

                result = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

            } else if (fileName.endsWith(".potx")) {

                result = "application/vnd.openxmlformats-officedocument.presentationml.template";

            } else if (fileName.endsWith(".ppsx")) {

                result = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";

            } else if (fileName.endsWith(".ppam")) {

                result = "application/vnd.ms-powerpoint.addin.macroEnabled.12";

            } else if (fileName.endsWith(".pptm")) {

                result = "application/vnd.ms-powerpoint.presentation.macroEnabled.12";

            } else if (fileName.endsWith(".potm")) {

                result = "application/vnd.ms-powerpoint.template.macroEnabled.12";

            } else if (fileName.endsWith(".ppsm")) {

                result = "application/vnd.ms-powerpoint.slideshow.macroEnabled.12";

            } else if (fileName.endsWith(".mdb")) {

                result = "application/vnd.ms-access";

            } else {//그림

                result = "image/*";

            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static String TYPE_WORD = "word";
    public static String TYPE_EXCEL = "excel";
    public static String TYPE_PPT = "ppt";
    public static String TYPE_ACCESS = "access";
    public static String TYPE_IMAGE = "image";
    public static String TYPE_VIDEO = "video";
    public static String TYPE_FILE = "file";

    public static String getExtType(String fileName) {

        String result = "";

        try {

            if (fileName.endsWith(".doc") || fileName.endsWith(".dot") || fileName.endsWith(".docx") || fileName.endsWith(".dotx") || fileName.endsWith(".docm") || fileName.endsWith(".dotm")) {

                result = TYPE_WORD;

            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlt") || fileName.endsWith(".xla") || fileName.endsWith(".xlsx") || fileName.endsWith(".xltx") || fileName.endsWith(".xlsm")
                    || fileName.endsWith(".xltm") || fileName.endsWith(".xlam") || fileName.endsWith(".xlsb")) {

                result = TYPE_EXCEL;

            } else if (fileName.endsWith(".ppt") || fileName.endsWith(".pot") || fileName.endsWith(".pps") || fileName.endsWith(".ppa") || fileName.endsWith(".pptx") || fileName.endsWith(".potx")
                    || fileName.endsWith(".ppsx") || fileName.endsWith(".ppam") || fileName.endsWith(".pptm") || fileName.endsWith(".potm") || fileName.endsWith(".ppsm")) {

                result = TYPE_PPT;

            } else if (fileName.endsWith(".mdb")) {

                result = TYPE_ACCESS;

            } else if (fileName.endsWith(".bmp") || fileName.endsWith(".rle") || fileName.endsWith(".dib") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                    fileName.endsWith(".png") || fileName.endsWith(".tif")) { // 일반 그림

                result = TYPE_IMAGE;

            } else if (fileName.endsWith(".mkv") || fileName.endsWith("mp4") || fileName.endsWith(".mpg") || fileName.endsWith(".mpeg") || fileName.endsWith(".flv") || fileName.endsWith(".wmv")
                    || fileName.endsWith(".asf") || fileName.endsWith(".asx") || fileName.endsWith(".ogm") || fileName.endsWith(".ogv") || fileName.endsWith(".mov")) {
                result = TYPE_VIDEO;
            } else {
                result = TYPE_FILE;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

}
