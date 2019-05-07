package mars.nomad.com.b3_commongallery.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.b3_commongallery.Callback.DoubleSelectionClickListener;
import mars.nomad.com.b3_commongallery.DataModel.AlbumDataModel;
import mars.nomad.com.b3_commongallery.R;
import mars.nomad.com.m0_imageloader.ImageLoader;


/**
 * Created by 김덕원 on 2017-03-08.
 */

public class PictureAlbumAdapter extends RecyclerView.Adapter<PictureAlbumAdapter.PictureAlbumViewHolder> {

    private List<AlbumDataModel> list;
    private Context context;
    private String type;
    private DoubleSelectionClickListener<Integer, AlbumDataModel> onListItemDetailClickLisner;

    public PictureAlbumAdapter(List<AlbumDataModel> list, Context context, String type, DoubleSelectionClickListener<Integer, AlbumDataModel> onListItemDetailClickLisner) {
        this.list = list;
        this.context = context;
        this.onListItemDetailClickLisner = onListItemDetailClickLisner;
        this.type = type;
    }

    @Override
    public PictureAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p0_cell_album, parent, false);
        return new PictureAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureAlbumViewHolder holder, int position) {
        try {
            if (list != null && list.get(position) != null) {
                if ("pfback".equals(type) || "pf".equals(type)) {
                    if (position == 0) {
                        holder.imageViewPicture.setImageResource(R.drawable.p0_img_camera_take_photo_android);
                        holder.imageViewCheckBox.setVisibility(View.GONE);
                    } else {
                        String path = list.get(position).getFullPath();
                        holder.imageViewCheckBox.setVisibility(View.VISIBLE);
//                        if (!path.contains("file://")) {
//                            path = "file://" + path;
//                        }

                        holder.imageViewCheckBox.setSelected(list.get(position).isSelected());

                        ImageLoader.loadLocalThumbImage(holder.imageViewPicture, context, path);

//                        Glide.with(context).clear(holder.imageViewPicture);
//                        Glide.with(context).asBitmap().load(path).placeholder(R.drawable.p0_img_camera_default_android).into(holder.imageViewPicture);
                    }
                } else {
                    String path = list.get(position).getFullPath();
                    holder.imageViewCheckBox.setVisibility(View.VISIBLE);
//                    if (!path.contains("file://")) {
//                        path = "file://" + path;
//                    }

                    holder.imageViewCheckBox.setSelected(list.get(position).isSelected());
                    ImageLoader.loadLocalThumbImage(holder.imageViewPicture, context, path);

//                    Glide.with(context).clear(holder.imageViewPicture);
//                    Glide.with(context).load(path).asBitmap().placeholder(R.drawable.p0_img_camera_default_android).into(holder.imageViewPicture);
                }

            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<AlbumDataModel> getSelectedData() {
        List<AlbumDataModel> result = new ArrayList<>();
        try {
            for (AlbumDataModel albumDataModel : list) {
                if (albumDataModel.isSelected()) {
                    result.add(albumDataModel);
                }
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 셀을 더한다.
     */
    public void addCell(AlbumDataModel newItem) {

        try {
            if(list == null){
                list = new ArrayList<>();
            }
            list.add(newItem);
            notifyItemInserted(list.indexOf(newItem));

        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    /**
     * ViewHolder
     */
    public class PictureAlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private ImageView imageViewCheckBox;

        public PictureAlbumViewHolder(View itemView) {
            super(itemView);
            imageViewPicture = (ImageView) itemView.findViewById(R.id.imageViewPicture);
            imageViewCheckBox = (ImageView) itemView.findViewById(R.id.imageViewCheckBox);
            imageViewPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("pfback".equals(type) || "pf".equals(type)) {
                        if (getAdapterPosition() == 0) {
                            onListItemDetailClickLisner.onClickItem(getAdapterPosition(), null);
                        } else {
                            onListItemDetailClickLisner.onClickItem(getAdapterPosition(), list.get(getAdapterPosition()));
                        }
                    } else {
                        onListItemDetailClickLisner.onClickItem(getAdapterPosition(), list.get(getAdapterPosition()));
                    }
                }
            });

        }
    }
}
