package mars.nomad.com.b5_fileupload.Upload;

import mars.nomad.com.m0_http.BaseResponseModel;

/**
 * Created by SJH on 2017-06-30.
 */

public class UploadPictureResponseModel extends BaseResponseModel {

    private String user_id;
    private String file_name;
    private String file_path;
    private String file_extension;

    private int file_width;
    private int file_height;
    private String temp_key;
    private String target_type;
    private String target_key;

    private String file_type;
    private int file_size;
    private String thumb_name;
    private int thumb_width;
    private int thumb_height;

    private int file_seq;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public int getFile_width() {
        return file_width;
    }

    public void setFile_width(int file_width) {
        this.file_width = file_width;
    }

    public int getFile_height() {
        return file_height;
    }

    public void setFile_height(int file_height) {
        this.file_height = file_height;
    }

    public String getTemp_key() {
        return temp_key;
    }

    public void setTemp_key(String temp_key) {
        this.temp_key = temp_key;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getTarget_key() {
        return target_key;
    }

    public void setTarget_key(String target_key) {
        this.target_key = target_key;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public String getThumb_name() {
        return thumb_name;
    }

    public void setThumb_name(String thumb_name) {
        this.thumb_name = thumb_name;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }

    public int getFile_seq() {
        return file_seq;
    }

    public void setFile_seq(int file_seq) {
        this.file_seq = file_seq;
    }

    @Override
    public String toString() {
        return "UploadPictureResponseModel{" +
                "result=" + result +
                ", user_id='" + user_id + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_extension='" + file_extension + '\'' +
                ", file_width=" + file_width +
                ", file_height=" + file_height +
                ", temp_key='" + temp_key + '\'' +
                ", target_type='" + target_type + '\'' +
                ", target_key='" + target_key + '\'' +
                ", file_type='" + file_type + '\'' +
                ", file_size=" + file_size +
                ", thumb_name='" + thumb_name + '\'' +
                ", thumb_width=" + thumb_width +
                ", thumb_height=" + thumb_height +
                ", code='" + code + '\'' +
                ", file_seq=" + file_seq +
                '}';
    }
}
