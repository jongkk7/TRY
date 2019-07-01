package mars.nomad.com.B1_post.PostList.DataModel;

public class PostList {


    /**
     * post_seq : 7
     * board_seq : 2
     * status : 50
     * user_id : 45a3e3f72403eb5c7cfc2fa90c0a4068955146054a6c303a0
     * contents : [{"contents":"글12","isFirstLoaded":true,"sortNum":0,"type":"txt","baseObjId":0}]
     * contents_plain : 글12
     * is_secret : 0
     * like_cnt : 0
     * read_cnt : 0
     * reply_cnt : 11
     * report_cnt : 0
     * mod_date : 2019-05-14 11:29:19
     * reg_date : 2019-05-14 11:29:19
     * user_name : 익명
     * is_like : 0
     */

    private int post_seq;
    private int board_seq;
    private int status;
    private String user_id;
    private String contents;
    private String contents_plain;
    private int is_secret;
    private int like_cnt;
    private int read_cnt;
    private int reply_cnt;
    private int report_cnt;
    private String mod_date;
    private String reg_date;
    private String user_name;
    private int is_like;

    public int getPost_seq() {
        return post_seq;
    }

    public void setPost_seq(int post_seq) {
        this.post_seq = post_seq;
    }

    public int getBoard_seq() {
        return board_seq;
    }

    public void setBoard_seq(int board_seq) {
        this.board_seq = board_seq;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents_plain() {
        return contents_plain;
    }

    public void setContents_plain(String contents_plain) {
        this.contents_plain = contents_plain;
    }

    public int getIs_secret() {
        return is_secret;
    }

    public void setIs_secret(int is_secret) {
        this.is_secret = is_secret;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getRead_cnt() {
        return read_cnt;
    }

    public void setRead_cnt(int read_cnt) {
        this.read_cnt = read_cnt;
    }

    public int getReply_cnt() {
        return reply_cnt;
    }

    public void setReply_cnt(int reply_cnt) {
        this.reply_cnt = reply_cnt;
    }

    public int getReport_cnt() {
        return report_cnt;
    }

    public void setReport_cnt(int report_cnt) {
        this.report_cnt = report_cnt;
    }

    public String getMod_date() {
        return mod_date;
    }

    public void setMod_date(String mod_date) {
        this.mod_date = mod_date;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    @Override
    public String toString() {
        return "PostList{" +
                "post_seq=" + post_seq +
                ", board_seq=" + board_seq +
                ", status=" + status +
                ", user_id='" + user_id + '\'' +
                ", contents='" + contents + '\'' +
                ", contents_plain='" + contents_plain + '\'' +
                ", is_secret=" + is_secret +
                ", like_cnt=" + like_cnt +
                ", read_cnt=" + read_cnt +
                ", reply_cnt=" + reply_cnt +
                ", report_cnt=" + report_cnt +
                ", mod_date='" + mod_date + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", user_name='" + user_name + '\'' +
                ", is_like=" + is_like +
                '}';
    }
}
