package de.mpg.mpdl.ebooksreader.injection.module.glide;

import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;


public class ImageLoadConfig {
    public static final int CENTER_CROP = 0;
    public static final int FIT_CENTER = 1;
    private Integer placeHolderResId;
    private Integer errorResId;
    private boolean crossFade;
    private int crossDuration;
    private OverrideSize size;
    private int CropType = CENTER_CROP;
    private boolean asGif; //true
    private boolean asBitmap;//true
    private boolean skipMemoryCache;//true
    private DiskCache diskCacheStrategy;
    private LoadPriority prioriy;
    private float thumbnail;
    private String thumbnailUrl;
    private SimpleTarget<Bitmap> simpleTarget;
    private ViewTarget<? extends View, GlideDrawable> viewTarget;
    private NotificationTarget notificationTarget;
    private AppWidgetTarget appWidgetTarget;
    private Integer animResId;
    private ViewPropertyAnimation.Animator animator;
    private boolean cropCircle;
    private boolean roundedCorners;
    private boolean grayscale;
    private boolean blur;
    private boolean rotate;
    private int rotateDegree;
    private String tag;

    public enum DiskCache {
        NONE(DiskCacheStrategy.NONE),
        SOURCE(DiskCacheStrategy.SOURCE),
        RESULT(DiskCacheStrategy.RESULT),
        ALL(DiskCacheStrategy.ALL);
        private DiskCacheStrategy strategy;

        DiskCache(DiskCacheStrategy strategy) {
            this.strategy = strategy;
        }

        public DiskCacheStrategy getStrategy() {
            return strategy;
        }
    }

    public enum LoadPriority {
        LOW(Priority.LOW),
        NORMAL(Priority.NORMAL),
        HIGH(Priority.HIGH),
        IMMEDIATE(Priority.IMMEDIATE),;
        Priority priority;

        LoadPriority(Priority priority) {
            this.priority = priority;
        }

        public Priority getPriority() {
            return priority;
        }
    }

    private ImageLoadConfig(Builder builder) {
        this.placeHolderResId = builder.placeHolderResId;
        this.errorResId = builder.errorResId;
        this.crossFade = builder.crossFade;
        this.crossDuration = builder.crossDuration;
        this.size = builder.size;
        this.CropType = builder.CropType;
        this.asGif = builder.asGif;
        this.asBitmap = builder.asBitmap;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.diskCacheStrategy = builder.diskCacheStrategy;
        this.thumbnail = builder.thumbnail;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.simpleTarget = builder.simpleTarget;
        this.viewTarget = builder.viewTarget;
        this.notificationTarget = builder.notificationTarget;
        this.appWidgetTarget = builder.appWidgetTarget;
        this.animResId = builder.animResId;
        this.animator = builder.animator;
        this.prioriy = builder.prioriy;
        this.blur = builder.blur;
        this.cropCircle = builder.cropCircle;
        this.roundedCorners = builder.roundedCorners;
        this.grayscale = builder.grayscale;
        this.rotate = builder.rotate;
        this.rotateDegree = builder.rotateDegree;
        this.tag = tag;
    }

    public static class Builder {
        private Integer placeHolderResId;
        private Integer errorResId;
        private boolean crossFade;
        private int crossDuration;
        private OverrideSize size;
        private int CropType = CENTER_CROP;
        private boolean asGif;
        private boolean asBitmap;
        private boolean skipMemoryCache;
        private DiskCache diskCacheStrategy = DiskCache.ALL;
        private LoadPriority prioriy = LoadPriority.HIGH;
        private float thumbnail;
        private String thumbnailUrl;
        private SimpleTarget<Bitmap> simpleTarget;
        private ViewTarget<?extends View,GlideDrawable>viewTarget;
        private NotificationTarget notificationTarget;
        private AppWidgetTarget appWidgetTarget;
        private Integer animResId;
        private ViewPropertyAnimation.Animator animator;
        private boolean cropCircle;
        private boolean roundedCorners;
        private boolean grayscale;
        private boolean blur;
        private boolean rotate;
        private int rotateDegree = 90;
        private String tag;

        public Builder setPlaceHolderResId(Integer placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public Builder setErrorResId(Integer errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder setCrossFade(boolean crossFade) {
            this.crossFade = crossFade;
            return this;
        }

        public Builder setCrossDuration(int crossDuration) {
            this.crossDuration = crossDuration;
            return this;
        }

        public Builder setSize(OverrideSize size) {
            this.size = size;
            return this;
        }

        public Builder setCropType(int cropType) {
            CropType = cropType;
            return this;
        }

        public Builder setAsGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder setAsBitmap(boolean asBitmap) {
            this.asBitmap = asBitmap;
            return this;
        }

        public Builder setSkipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder setDiskCacheStrategy(DiskCache diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public Builder setPrioriy(LoadPriority prioriy) {
            this.prioriy = prioriy;
            return this;
        }

        public Builder setThumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setSimpleTarget(SimpleTarget<Bitmap> simpleTarget) {
            this.simpleTarget = simpleTarget;
            return this;
        }

        public Builder setViewTarget(ViewTarget<? extends View, GlideDrawable> viewTarget) {
            this.viewTarget = viewTarget;
            return this;
        }

        public Builder setNotificationTarget(NotificationTarget notificationTarget) {
            this.notificationTarget = notificationTarget;
            return this;
        }

        public Builder setAppWidgetTarget(AppWidgetTarget appWidgetTarget) {
            this.appWidgetTarget = appWidgetTarget;
            return this;
        }

        public Builder setAnimResId(Integer animResId) {
            this.animResId = animResId;
            return this;
        }

        public Builder setAnimator(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        public Builder setCropCircle(boolean cropCircle) {
            this.cropCircle = cropCircle;
            return this;
        }

        public Builder setRoundedCorners(boolean roundedCorners) {
            this.roundedCorners = roundedCorners;
            return this;
        }

        public Builder setGrayscale(boolean grayscale) {
            this.grayscale = grayscale;
            return this;
        }

        public Builder setBlur(boolean blur) {
            this.blur = blur;
            return this;
        }

        public Builder setRotate(boolean rotate) {
            this.rotate = rotate;
            return this;
        }

        public Builder setRotateDegree(int rotateDegree) {
            this.rotateDegree = rotateDegree;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public ImageLoadConfig build() {
            return new ImageLoadConfig(this);
        }
    }

    public static Builder parseBuilder(ImageLoadConfig config) {
        Builder builder = new Builder();
        builder.placeHolderResId = config.placeHolderResId;
        builder.errorResId = config.errorResId;
        builder.crossFade = config.crossFade;
        builder.crossDuration = config.crossDuration;
        builder.size = config.size;
        builder.CropType = config.CropType;
        builder.asGif = config.asGif;
        builder.asBitmap = config.asBitmap;
        builder.skipMemoryCache = config.skipMemoryCache;
        builder.diskCacheStrategy = config.diskCacheStrategy;
        builder.thumbnail = config.thumbnail;
        builder.thumbnailUrl = config.thumbnailUrl;
        builder.simpleTarget = config.simpleTarget;
        builder.viewTarget = config.viewTarget;
        builder.notificationTarget = config.notificationTarget;
        builder.appWidgetTarget = config.appWidgetTarget;
        builder.animResId = config.animResId;
        builder.animator = config.animator;
        builder.prioriy = config.prioriy;
        builder.cropCircle = config.cropCircle;
        builder.roundedCorners = config.roundedCorners;
        builder.grayscale = config.grayscale;
        builder.blur = config.blur;
        builder.rotate = config.rotate;
        builder.rotateDegree = config.rotateDegree;
        builder.tag = config.tag;
        return builder;
    }

    public Integer getPlaceHolderResId() {
        return placeHolderResId;
    }

    public Integer getErrorResId() {
        return errorResId;
    }

    public boolean isCrossFade() {
        return crossFade;
    }

    public int getCrossDuration() {
        return crossDuration;
    }

    public OverrideSize getSize() {
        return size;
    }

    public int getCropType() {
        return CropType;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public DiskCache getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public LoadPriority getPrioriy() {
        return prioriy;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public SimpleTarget<Bitmap> getSimpleTarget() {
        return simpleTarget;
    }

    public ViewTarget<? extends View, GlideDrawable> getViewTarget() {
        return viewTarget;
    }

    public NotificationTarget getNotificationTarget() {
        return notificationTarget;
    }

    public AppWidgetTarget getAppWidgetTarget() {
        return appWidgetTarget;
    }

    public Integer getAnimResId() {
        return animResId;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public boolean isCropCircle() {
        return cropCircle;
    }

    public boolean isRoundedCorners() {
        return roundedCorners;
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public boolean isBlur() {
        return blur;
    }

    public boolean isRotate() {
        return rotate;
    }

    public int getRotateDegree() {
        return rotateDegree;
    }

    public String getTag() {
        return tag;
    }

    public static class OverrideSize {
        private final int width;
        private final int height;

        public OverrideSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public interface LoaderListener {

        void onSuccess();

        void onError();
    }

    public interface BitmapLoadingListener {

        void onSuccess(Bitmap b);

        void onError();
    }

}
