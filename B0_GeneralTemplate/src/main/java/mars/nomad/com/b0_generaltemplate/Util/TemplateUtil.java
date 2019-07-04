package mars.nomad.com.b0_generaltemplate.Util;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.RawRes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-04
 */
public class TemplateUtil {

    /**
     * mars.nomad.com.xxx를 /mars/nomad/com/xxx로 바꿔준다.
     *
     * @param input
     * @return
     */
    public static String getDirectoryNameFromPackageName(String input) {

        String result = "";

        try {

            String[] split = input.split("\\.");

            for (String s : split) {

                result += "/" + s;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * raw에 있는 id의 파일 내용을 싹 긁어온다.
     *
     * @param context
     * @param id
     * @return
     */
    public static String readContentsFromFile(final Context context, @RawRes int id) {

        String result = "";

        try {

            InputStream inputStream = context.getResources().openRawResource(id);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int size;

            try {

                while ((size = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, size);
                }

            } catch (IOException e) {
                ErrorController.showError(e);
            } finally {
                outputStream.close();
                inputStream.close();
            }

            result = outputStream.toString();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 파일로 저장
     *
     * @param originalString
     * @param fileName
     * @param dirName
     */
    public static void saveAsFile(String originalString, String fileName, String dirName) {

        try {

            File dir = new File(dirName);

            if (!dir.exists()) {

                dir.mkdirs();
            }

            File file = new File(dirName, fileName);

            if (!file.exists()) {

                file.createNewFile();
            }

            FileWriter out = new FileWriter(file);
            out.write(originalString);
            out.close();

            ErrorController.showMessage("Created file : " + file.getAbsolutePath());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void deleteAllFiles(String path) {

        try {

            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {

                String deleteCmd = "rm -r " + path;
                Runtime runtime = Runtime.getRuntime();

                try {

                    runtime.exec(deleteCmd);

                } catch (IOException e) {
                    ErrorController.showError(e);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 문자열을 받아서 resString으로 변환한다
     * @param targetName
     * @return
     */
    public static String getResText(String targetName) {
        StringBuilder result = new StringBuilder();

        try {

            int index = 0;
            for (char c : targetName.toCharArray()) {

                if (Character.isUpperCase(c) && index == 0) {
                    result.append(Character.toLowerCase(c));
                } else if (Character.isUpperCase(c)) {
                    result.append("_").append(Character.toLowerCase(c));
                } else {
                    result.append(c);
                }
                index++;
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result.toString();

    }


}
