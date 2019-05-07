package mars.nomad.com.l12_applicationutil.Compress;

import mars.nomad.com.l0_base.Callback.CommonCallback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by 김창혁, NomadSoft.Inc on 2017-09-19.
 */

public class Decompress {

    private final CommonCallback.DoubleObjectCallback<Integer, Integer> progressCallback;
    private CommonCallback.SingleObjectCallback<Boolean> mCallback;
    private String _zipFile;  //저장된 zip 파일 위치
    private String mLocation; //압축을 풀 위치

    /**
     * 압축파일을 해제한다.
     *
     * @param zipFile  압축파일
     * @param location 압축 해제할 위치
     * @param callback
     */
    public Decompress(String zipFile, String location, CommonCallback.SingleObjectCallback<Boolean> callback, CommonCallback.DoubleObjectCallback<Integer, Integer> progressCallback) {
        _zipFile = zipFile;
        mLocation = location;
        this.mCallback = callback;
        this.progressCallback = progressCallback;

        mLocation = _dirChecker(""); //폴더를 만들기 위한 함수로 아래에 정의

        ErrorController.showMessage(mLocation);

        unzip(mLocation);
    }

    // 압축 해제 메소드
    public void unzip(String location) {
        try {
            //create target location folder if not exist
//            _dirChecker(location);

            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);

            ErrorController.showMessage("[Decompress] : " + fin.available());

            int fileLength = fin.available();

            int total = 0;

            ZipEntry ze = null;

            while ((ze = zin.getNextEntry()) != null) {

                try {
                    ErrorController.showMessage(location + "/" + ze.getName());
                    //create dir if required while unzipping

                    if (ze.isDirectory()) {

                        _dirChecker(ze.getName());

                    } else {

                        FileOutputStream fout = null;

                        // 푼 파일중 덱스 파일과 같은 이름일 경우 = 덱스파일 일 경우
                       // if (ze.getName().equals(CommonConstants.DEX_LIB_NAME)) {

                        //    fout = new FileOutputStream(FileLocation.DEX_LOCATION + "/" + ze.getName());

                       // } else {

                            fout = new FileOutputStream(location + "/" + ze.getName());

                        //}


                       /* for (int c = zin.read(); c != -1; c = zin.read()) {
                            fout.write(c);
                        }

                        zin.closeEntry();
                        fout.close();*/

                        BufferedOutputStream bufout = new BufferedOutputStream(fout);

                        byte[] buffer = new byte[1024];
                        int read = 0;
                        while ((read = zin.read(buffer)) != -1) {
                            bufout.write(buffer, 0, read);
                            total += read;

                        }

                        progressCallback.onSuccess(fileLength, total);

                        zin.closeEntry();
                        bufout.close();
                        fout.close();
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }

            }
            mCallback.onSuccess(true);
            zin.close();

        } catch (Exception e) {
            ErrorController.showError(e);
            mCallback.onFailed(e.getMessage());
        }

    }


    //location에 매개변수로 받아온 dir 폴더를 만든다.
    private String _dirChecker(String dir) {
        File f = new File(mLocation + "/" + dir);

        if (!f.isDirectory()) {
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }

}
