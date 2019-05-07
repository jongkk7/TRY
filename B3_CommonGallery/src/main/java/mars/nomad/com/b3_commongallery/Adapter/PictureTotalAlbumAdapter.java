package mars.nomad.com.b3_commongallery.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mars.nomad.com.l12_applicationutil.String.StringProcesser;
import mars.nomad.com.m0_imageloader.ImageLoader;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.b3_commongallery.Callback.DoubleSelectionClickListener;
import mars.nomad.com.b3_commongallery.DataModel.AlbumDataModel;
import mars.nomad.com.b3_commongallery.R;


/**
 * Created by 김덕원 on 2017-03-08.
 */
public class PictureTotalAlbumAdapter extends RecyclerView.Adapter<PictureTotalAlbumAdapter.PictureTotalAlbumHolder> {

    private List<AlbumDataModel> list;
    private Context context;
    private DoubleSelectionClickListener<String, String> onListItemClickLisner;

    public PictureTotalAlbumAdapter(List<AlbumDataModel> list, Context context, DoubleSelectionClickListener<String, String> onListItemClickLisner) {
        this.list = list;
        this.context = context;
        this.onListItemClickLisner = onListItemClickLisner;
    }

    @Override
    public PictureTotalAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p0_cell_total_album, parent, false);
        return new PictureTotalAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureTotalAlbumHolder holder, int position) {

        AlbumDataModel item = list.get(position);
        holder.textViewTitle.setText(item.getDirectoryName());

        try {
            if (list != null && list.get(position) != null) {
                String path = list.get(position).getFullPath();
//                if (!path.contains("file://")) {
//                    path = "file://" + path;
//                }
//                Glide.with(context).load(path).asBitmap().into(holder.imageViewPicture);
                ImageLoader.loadLocalThumbImage(holder.imageViewPicture, context, path);

            }

            if (item.getChildCnt() > -1) {//다른 뷰

                holder.textViewCount.setText(StringProcesser.getFormattedNumber(item.getChildCnt()));

            } else {//전체 보기

                int cnt = 0;

                for (AlbumDataModel albumDataModel : list) {

                    cnt += albumDataModel.getChildCnt();
                }

                cnt += 1;//자기 자신이 -1이므로, 이를 더함.

                holder.textViewCount.setText(StringProcesser.getFormattedNumber(cnt));

            }
            ErrorController.showMessage("[AlbumList] " + item.toString());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder
     */
    public class PictureTotalAlbumHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPicture;
        private TextView textViewTitle;
        private TextView textViewCount;
        private LinearLayout linearLayoutClick;

        public PictureTotalAlbumHolder(View itemView) {
            super(itemView);

            imageViewPicture = (ImageView) itemView.findViewById(R.id.imageViewPicture);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewCount = (TextView) itemView.findViewById(R.id.textViewCount);
            linearLayoutClick = (LinearLayout) itemView.findViewById(R.id.linearLayoutClick);

            linearLayoutClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlbumDataModel item = list.get(getAdapterPosition());
                    onListItemClickLisner.onClickItem(item.getDirectoryPath(), item.getDirectoryName());
                }
            });
        }
    }//end of ViewHolder


}
