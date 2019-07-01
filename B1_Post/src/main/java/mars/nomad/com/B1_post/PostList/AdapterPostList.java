package mars.nomad.com.B1_post.PostList;

import android.content.Context;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.DataModel.PostImageDataModel;
import mars.nomad.com.B1_post.DataModel.PostTextDataModel;
import mars.nomad.com.B1_post.DataModel.PostVideoDataModel;
import mars.nomad.com.B1_post.PostList.ClickListener.PostListClickListener;
import mars.nomad.com.B1_post.PostList.DataModel.PostList;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Date.NSDate;
import mars.nomad.com.l12_applicationutil.String.StringChecker;
import mars.nomad.com.l12_applicationutil.Video.VideoControlUtil;
import mars.nomad.com.m0_http.RetrofitSender2;
import mars.nomad.com.m0_imageloader.ImageLoader;


public class AdapterPostList extends NsGeneralView<PostList> {


    private TextView textViewTopInfo;
    private RelativeLayout relativeLayoutTopInfo;
    private TextView textViewUserName;
    private TextView textViewRegDate;
    private TextView textViewDistance;
    private ImageView imageViewOption;
    private RelativeLayout relativeLayoutOption;
    private LinearLayout linearLayoutTopTitle;
    private TextView textViewContents;
    private TextView textViewMore;
    private ImageView imageViewMedia;
    private TextureView surfaceView;
    private ImageView imageViewPlayButton;
    private RelativeLayout relativeLayoutVideo;
    private RecyclerView recyclerViewMedia;
    private RelativeLayout relativeLayoutMedia;
    private LinearLayout linearLayoutContentCell;
    private ImageView imageViewLike;
    private TextView textViewLikeText;
    private LinearLayout linearLayoutLike;
    private LinearLayout linearLayoutComment;
    private TextView textViewCommentCount;
    private LinearLayout linearLayoutCell;

    public AdapterPostList(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_post_list;
    }

    @Override
    public void initView(View view) {


        textViewTopInfo = view.findViewById(R.id.textViewTopInfo);
        relativeLayoutTopInfo = view.findViewById(R.id.relativeLayoutTopInfo);
        textViewUserName = view.findViewById(R.id.textViewUserName);
        textViewRegDate = view.findViewById(R.id.textViewRegDate);
        textViewDistance = view.findViewById(R.id.textViewDistance);
        imageViewOption = view.findViewById(R.id.imageViewOption);
        relativeLayoutOption = view.findViewById(R.id.relativeLayoutOption);
        linearLayoutTopTitle = view.findViewById(R.id.linearLayoutTopTitle);
        textViewContents = view.findViewById(R.id.textViewContents);
        textViewMore = view.findViewById(R.id.textViewMore);
        imageViewMedia = view.findViewById(R.id.imageViewMedia);
        surfaceView = view.findViewById(R.id.surfaceView);
        imageViewPlayButton = view.findViewById(R.id.imageViewPlayButton);
        relativeLayoutVideo = view.findViewById(R.id.relativeLayoutVideo);
        recyclerViewMedia = view.findViewById(R.id.recyclerViewMedia);
        relativeLayoutMedia = view.findViewById(R.id.relativeLayoutMedia);
        linearLayoutContentCell = view.findViewById(R.id.linearLayoutContentCell);
        imageViewLike = view.findViewById(R.id.imageViewLike);
        textViewLikeText = view.findViewById(R.id.textViewLikeText);
        linearLayoutLike = view.findViewById(R.id.linearLayoutLike);
        linearLayoutComment = view.findViewById(R.id.linearLayoutComment);
        textViewCommentCount = view.findViewById(R.id.textViewCommentCount);
        linearLayoutCell = view.findViewById(R.id.linearLayoutCell);
    }

    @Override
    public void setEvent(List<PostList> data, PostList item, NsGeneralClickListener<PostList> mClickListener) {
        try {

            PostListClickListener clickListener = (PostListClickListener) mClickListener;


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public void onBindData(List<PostList> data, PostList item) {
        try {

            // 이름 (무조건 익명임)
            textViewUserName.setText("익명");

            // 날짜
            textViewRegDate.setText(NSDate.getPeriod(item.getMod_date()));

            // 좋아요 갯수
            textViewLikeText.setText(item.getLike_cnt() + "");
            // 코멘트 갯수
            textViewCommentCount.setText(item.getReply_cnt() + "");

            // 콘텐츠
            textViewContents.setVisibility(GONE);
            textViewMore.setVisibility(GONE);
            relativeLayoutMedia.setVisibility(GONE);

            imageViewMedia.setVisibility(GONE);
            relativeLayoutVideo.setVisibility(GONE);



            Gson gson = new Gson();
            try {

                List<PostDataModel> mediaList = new ArrayList<>();
                List<PostDataModel> postDataModels = gson.fromJson(item.getContents(), new TypeToken<List<PostDataModel>>() {
                }.getType());

                String textContents = "";

                for (PostDataModel postDataModel : postDataModels) {

                    switch (postDataModel.getType()) {
                        case "text":
                            PostTextDataModel text = gson.fromJson(postDataModel.getContents(), PostTextDataModel.class);
                            textContents = textContents + text.getContents();
                            break;
                        case "image":
                        case "video":
                            mediaList.add(postDataModel);
                            break;
                    }
                }


                // 텍스트
                if (StringChecker.isStringNotNull(textContents)) {

                    textViewContents.setVisibility(VISIBLE);

                    if (textContents.length() > 300) {

                        textContents = textContents.substring(0, 300);
                        textViewMore.setVisibility(VISIBLE);
                    }

                    textViewContents.setText(textContents);

                }


                if (mediaList.size() > 0) {
                    relativeLayoutMedia.setVisibility(VISIBLE);
                    // 이미지 하나
                    if (mediaList.size() == 1) {

                        PostDataModel oneMedia = mediaList.get(0);

                        if (oneMedia.getType().equalsIgnoreCase("image")) {
                            PostImageDataModel image = gson.fromJson(oneMedia.getContents(), PostImageDataModel.class);
                            imageViewMedia.setVisibility(VISIBLE);

                            ImageLoader.loadImageWithDefault(getContext(), imageViewMedia, RetrofitSender2.URL_IMG_BASE, image.getThumbPath());


                        } else {
                            PostVideoDataModel video = gson.fromJson(oneMedia.getContents(), PostVideoDataModel.class);
                            surfaceView.setVisibility(VISIBLE);

                            startMedia(video);
                        }


                    } else {
                        // TODO 멀티로 받는거
                    }
                }

            } catch (Exception e) {
                ErrorController.showError(e);
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void startMedia(PostVideoDataModel video) {
        try {

            VideoControlUtil.getInstance().startMediaPlayerNoAuto(RetrofitSender2.BASE_URL, video.getFilePath(), (imageViewPlayButton.getVisibility() == View.VISIBLE), new Surface(surfaceView.getSurfaceTexture()), new VideoControlUtil.VideoControlCallback() {
                @Override
                public void onStart() {
                    imageViewPlayButton.setVisibility(View.GONE);
                }

                @Override
                public void onPrePared() {
                    imageViewPlayButton.setVisibility(View.GONE);
                }

                @Override
                public void onComplete() {
                    imageViewPlayButton.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPause() {
                    imageViewPlayButton.setVisibility(View.VISIBLE);

                }

                @Override
                public void onStop() {
                    imageViewPlayButton.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

}
