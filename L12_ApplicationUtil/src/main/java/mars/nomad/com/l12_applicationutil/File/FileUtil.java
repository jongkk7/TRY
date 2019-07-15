package mars.nomad.com.l12_applicationutil.File;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;



import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJ Han, NomadSoft.INC on 2016-11-06. <br/>
 */

public class FileUtil {
    public static String getRealPathFromURI(Uri contentUri, Activity context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }//end of getRealPathFromURI.


    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    final String auth = uri.getAuthority();

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    } else {
                        return "/storage/" + type + "/" + split[1];
                    }
                }
                //내장 스토리지인 경우...
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            //킷캣 미만인 경우
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean fileExist(String dir, String fileRealName) {
        try {
            File file = new File(dir, fileRealName);
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public static void openFile(String dir,Context context, String url) {
        // Create URI
        File file = new File(dir, url);
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {

            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


            ErrorController.showMessage("Orientation: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    /**
     * 썸네일을 비동기적으로 만든다.
     *
     * @param fileName
     * @param context+
     * @param callback
     */
    public static void createThumbnail(final int max, final String fileName, final Context context, final ThumbCallback callback) {
        new AsyncTask<Void, Void, ThumbContainer>() {

            @Override
            protected ThumbContainer doInBackground(Void... voids) {

                ThumbContainer result = null;

                String thumbFileName = System.currentTimeMillis() + ".jpg";
                File resultThumbnail = new File(context.getCacheDir(), thumbFileName);


                byte[] imageData = null;
                try {

//                    int rotate = getCameraPhotoOrientation(context, Uri.fromFile(new File(fileName)), fileName);
                    int rotate = processOrientation(fileName);
                    ErrorController.showMessage("ROTATE : " + rotate);

                    int width = 0;
                    int height = 0;

                    InputStream in = context.getContentResolver().openInputStream(Uri.fromFile(new File(fileName)));
                    BitmapFactory.Options o = new BitmapFactory.Options();


                    o = new BitmapFactory.Options();
                    //o.inSampleSize = 2;
                    Bitmap image_bitmap = BitmapFactory.decodeStream(in, null, o);


                    int[] scaledSize = getScaledWidthAndHeight(max, image_bitmap.getWidth(), image_bitmap.getHeight());

                    width = scaledSize[0];
                    height = scaledSize[1];


                    Bitmap imageBitmapScaled = Bitmap.createScaledBitmap(image_bitmap, width, height, false);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    Bitmap rotated = Bitmap.createBitmap(imageBitmapScaled, 0, 0, imageBitmapScaled.getWidth(), imageBitmapScaled.getHeight(), matrix, true);

                    ErrorController.showMessage("[FileUtil] createThumbnail\nOriginal :: width - " + image_bitmap.getWidth() + ", height - " + image_bitmap.getHeight() + "\nthumbnail :: width - " + imageBitmapScaled.getWidth() + ", height - " + imageBitmapScaled.getHeight());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    rotated.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    imageData = baos.toByteArray();

                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(resultThumbnail));
                    bos.write(imageData);
                    bos.flush();
                    bos.close();

                    result = new ThumbContainer(resultThumbnail, width, height);

                    return result;

                } catch (Exception ex) {
                    ErrorController.showError(ex);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ThumbContainer file) {
                if (file != null) {//생성 성공
                    callback.onThumbnailCreated(file);
                } else {//생성 실패
                    callback.onFailure("썸네일 생성에 실패함.");
                }
            }
        }.execute();
    }


    /**
     * 가로, 세로 둘 중 가장 큰 부분이 max인 새로운 w와 h를 배열로 돌려준다.
     *
     * @param max           최대 길이(높이 or 너비 중 큰 부분)
     * @param currentWidth  현재 그림의 너비
     * @param currentHeight 현재 그림의 높이
     * @return {newWidth, newHeight}
     */
    private static int[] getScaledWidthAndHeight(int max, int currentWidth, int currentHeight) {
        int[] result = new int[2];
        double newWidth = 0;
        double newHeight = 0;
        try {
            if (currentWidth <= max && currentHeight <= max) {//이미 크기보다 작다면 스케일할 필요가 없다.
                result[0] = currentWidth;
                result[1] = currentHeight;
            } else {//크기를 조절해야함.
                if (currentWidth > currentHeight) { //width > height
                    double ratio = (double) currentHeight / (double) currentWidth;
                    newWidth = max;
                    newHeight = max * ratio;
                    result[0] = (int) newWidth;
                    result[1] = (int) newHeight;
                } else if (currentWidth < currentHeight) {//width < height
                    double ratio = (double) currentWidth / (double) currentHeight;
                    newHeight = max;
                    newWidth = max * ratio;
                    result[0] = (int) newWidth;
                    result[1] = (int) newHeight;
                } else { //width == height
                    result[0] = max;
                    result[1] = max;
                }
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    /**
     * 비디오의 썸네일을 비동기적으로 생성해준다.
     *
     * @param max
     * @param filePath
     * @param context
     * @param callback
     */
    public static void createVideoThumbnail(final int max, final String filePath, final Context context, final ThumbCallback callback) {
        try {
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);
            File fileCacheItem = new File(context.getCacheDir(), System.currentTimeMillis() + ".jpg");

            fileCacheItem.createNewFile();
            OutputStream out = new FileOutputStream(fileCacheItem);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);

            callback.onThumbnailCreated(new ThumbContainer(fileCacheItem, thumbnail.getWidth(), thumbnail.getHeight()));
        } catch (Exception e) {
            ErrorController.showError(e);
            callback.onFailure("비디오 썸네일 생성에 실패했습니다.");
        }
    }

    public static String getFileName(Uri selectedImageUri) {
        try {
            String result = "";
            result = selectedImageUri.getPath();
            String[] temp = result.split(":");
            return temp[1];
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }

    public static File cpFile(String filePath, Context context) {

        String newFileName = System.currentTimeMillis() + "." + getExtension(filePath);

        File resultFile = new File(context.getExternalCacheDir(), newFileName);

        try {

            copy(new File(filePath), resultFile);

            return resultFile;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;

    }

    public static String getExtension(String filePath) {

        String ext = "mp4";

        try {

            String[] temp = filePath.split("\\.");

            if (temp != null && temp.length > 0) {
                ext = temp[temp.length - 1];
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        ErrorController.showMessage("[FileUtil] ext : " + ext);
        return ext;
    }

    public static void copy(File src, File dst) throws IOException {

        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }

    public static boolean fileExist2(String contents) {
        try {
            File file = new File(contents);
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public static void openFile2(Context context, String url) {
        // Create URI
        File file = new File(url);
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {

            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 파일의 width, height를 돌려준다.
     * 실패 시 1080, 1080을 리턴함.
     *
     * @param path
     * @return
     */
    public static int[] getFileWidthAndHeight(String path) {


        int[] result = new int[2];
        result[0] = 1080;
        result[1] = 1080;
        try {
/*            int width = exif.getAttributeInt( ExifInterface.TAG_IMAGE_WIDTH, defaultValue );
            int height = exif.getAttributeInt( ExifInterface.TAG_IMAGE_LENGTH, defaultValue );*/
            int exifOrientation = processOrientation(path);

            ExifInterface exif = new ExifInterface(path);
            int width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 1080);
            int height = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 1080);

            if (exifOrientation == 90 || exifOrientation == 270) {

                result[0] = height;
                result[1] = width;

            } else {
                result[0] = width;
                result[1] = height;
            }



/*            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            result[0] = options.outWidth;
            result[1] = options.outHeight;
            */
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 파일 크기를 검사함. 50mb를 넘으면 차단함.
     *
     * @param selectedImagePath
     * @return
     */
    public static boolean sizeCheck(String selectedImagePath) {
        try {
            File file = new File(selectedImagePath);

            ErrorController.showMessage("File Length : " + file.length());

            if (file.length() <= 52428800) {
                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public static String getFileName(String filePath, String splitter) {

        String result = System.currentTimeMillis() + ".mp4";

        try {

            String[] temp = filePath.split(splitter);

            if (temp != null && temp.length > 0) {

                result = System.currentTimeMillis() + "_" + temp[temp.length - 1];
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * Exif orientation에 따라 각도를 돌려준다.
     *
     * @param path 파일의 풀 경로
     * @return 0 : 0, 180도의 사진(회전 불필요), 90 | 270 : 회전이 필요한 사진.(width/height 를 반전해야한다.)
     */
    public static int processOrientation(String path) {

        int rotate = 0;

        try {
            ExifInterface exif = new ExifInterface(path);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return rotate;

    }


    public interface ThumbCallback {

        /**
         * 썸네일이 잘 만들어짐.
         *
         * @param file 썸네일 파일 객체
         */
        void onThumbnailCreated(ThumbContainer file);

        /**
         * 썸네일 생성 실패
         *
         * @param fault 실패 원인
         */
        void onFailure(String fault);
    }
}
