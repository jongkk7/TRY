package mars.nomad.com.l12_applicationutil.Animation.DataModel;

import android.view.View;

/**
 * Created by 김창혁, NomadSoft.Inc on 2018-01-18.
 */

public class AnimationDataModel {


    private View view;

    private int width;

    private int height;

    private float positionX;

    private float positionY;

    private int marginBottom;

    private int marginTop;

    private int marginRight;

    private int marginLeft;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    @Override
    public String toString() {
        return "NsAnimView{" +
                "view=" + view +
                ", width=" + width +
                ", height=" + height +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", marginBottom=" + marginBottom +
                ", marginTop=" + marginTop +
                ", marginRight=" + marginRight +
                ", marginLeft=" + marginLeft +
                '}';
    }
}
