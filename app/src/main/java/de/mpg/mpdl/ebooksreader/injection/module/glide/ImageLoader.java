package de.mpg.mpdl.ebooksreader.injection.module.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;

import de.mpg.mpdl.ebooksreader.activity.R;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageLoader {

    public static ImageLoadConfig defConfig = new ImageLoadConfig.Builder().
            setCropType(ImageLoadConfig.CENTER_CROP).
            setAsBitmap(true).
            setPlaceHolderResId(R.drawable.ic_favicon).
            setErrorResId(R.drawable.ic_favicon).
            setDiskCacheStrategy(ImageLoadConfig.DiskCache.SOURCE).
            setPrioriy(ImageLoadConfig.LoadPriority.HIGH).build();

    public static void loadStringRes(ImageView view, String imageUrl, ImageLoadConfig config, ImageLoadConfig.LoaderListener listener) {
        // can .addHeader("Authorization", authorization) before build
        GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder().build());
        load(view.getContext(), view, glideUrl, config, listener);
    }

    public static void loadFile(ImageView view, File file, ImageLoadConfig config, ImageLoadConfig.LoaderListener listener) {
        load(view.getContext(), view, file, config, listener);
    }

    public static void loadResId(ImageView view, Integer resourceId, ImageLoadConfig config, ImageLoadConfig.LoaderListener listener) {
        load(view.getContext(), view, resourceId, config, listener);
    }

    public static void loadUri(ImageView view, Uri uri, ImageLoadConfig config, ImageLoadConfig.LoaderListener listener) {
        load(view.getContext(), view, uri, config, listener);
    }

    public static void loadGif(ImageView view, String gifUrl, ImageLoadConfig config, ImageLoadConfig.LoaderListener listener) {
        load(view.getContext(), view, gifUrl, ImageLoadConfig.parseBuilder(config).setAsGif(true).build(), listener);
    }

    public static void loadTarget(Context context, Object objUrl, ImageLoadConfig config, final ImageLoadConfig.LoaderListener listener) {
        load(context, null, objUrl, config, listener);
    }

    private static void load(Context context, ImageView view, Object objUrl, ImageLoadConfig config, final ImageLoadConfig.LoaderListener listener) {
        if (null == objUrl) {
            throw new IllegalArgumentException("objUrl is null");
        } else if (null == config) {
            config = defConfig;
        }
        try {
            GenericRequestBuilder builder = null;
            if (config.isAsGif()) {//gif类型
                GifRequestBuilder request = Glide.with(context).load(objUrl).asGif();
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (config.isAsBitmap()) {  //bitmap
                BitmapRequestBuilder request = Glide.with(context).load(objUrl).asBitmap();
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                //transform bitmap
                if (config.isRoundedCorners()) {
                    request.transform(new RoundedCornersTransformation(context, 10, 10));
                } else if (config.isCropCircle()) {
                    request.transform(new CropCircleTransformation(context));
                } else if (config.isGrayscale()) {
                    request.transform(new GrayscaleTransformation(context));
                } else if (config.isBlur()) {
                    request.transform(new BlurTransformation(context, 8, 8));
                } else if (config.isRotate()) {
                    //request.transform(new RotateTransformation(context, config.getRotateDegree()));
                }
                builder = request;
            } else if (config.isCrossFade()) {
                DrawableRequestBuilder request = Glide.with(context).load(objUrl).crossFade();
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            }

            builder.diskCacheStrategy(config.getDiskCacheStrategy().getStrategy()).
                    skipMemoryCache(config.isSkipMemoryCache()).
                    priority(config.getPrioriy().getPriority());
            builder.dontAnimate();
            if (null != config.getTag()) {
                builder.signature(new StringSignature(config.getTag()));
            } else {
                builder.signature(new StringSignature(objUrl.toString()));
            }
            if (null != config.getAnimator()) {
                builder.animate(config.getAnimator());
            } else if (null != config.getAnimResId()) {
                builder.animate(config.getAnimResId());
            }
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }
            if (null != config.getErrorResId()) {
                builder.error(config.getErrorResId());
            }
            if (null != config.getPlaceHolderResId()) {
                builder.placeholder(config.getPlaceHolderResId());
            }
            if (null != config.getSize()) {
                builder.override(config.getSize().getWidth(), config.getSize().getHeight());
            }
            if (null != listener) {
                setListener(builder, listener);
            }
            if (null != config.getThumbnailUrl()) {
                BitmapRequestBuilder thumbnailRequest = Glide.with(context).load(config.getThumbnailUrl()).asBitmap();
                builder.thumbnail(thumbnailRequest).into(view);
            } else {
                setTargetView(builder, config, view);
            }
        } catch (Exception e) {
            //view.setImageResource(config.getErrorResId());
        }
    }

    private static void setListener(GenericRequestBuilder request, final ImageLoadConfig.LoaderListener listener) {
        request.listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                if (!e.getMessage().equals("divide by zero")) {
                    listener.onError();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                listener.onSuccess();
                return false;
            }
        });
    }

    private static void setTargetView(GenericRequestBuilder request, ImageLoadConfig config, ImageView view) {
        //set targetView
        if (null != config.getSimpleTarget()) {
            request.into(config.getSimpleTarget());
        } else if (null != config.getViewTarget()) {
            request.into(config.getViewTarget());
        } else if (null != config.getNotificationTarget()) {
            request.into(config.getNotificationTarget());
        } else if (null != config.getAppWidgetTarget()) {
            request.into(config.getAppWidgetTarget());
        } else {
            request.into(view);
        }
    }

    public static void loadBitmap(Context context, Object url, final ImageLoadConfig.BitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            Glide.with(context).
                    load(url).
                    asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    dontAnimate().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (listener != null) {
                                listener.onSuccess(resource);
                            }
                        }
                    });
        }
    }

    public static void loadImageWithHighPriority(Object url, ImageView imageView, final ImageLoadConfig.LoaderListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            Glide.with(imageView.getContext()).
                    load(url).
                    asBitmap().
                    priority(Priority.HIGH).
                    dontAnimate().
                    listener(new RequestListener<Object, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            if (null != listener) {
                                listener.onError();
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (null != listener) {
                                listener.onSuccess();
                            }
                            return false;
                        }
                    }).into(imageView);
        }
    }

    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    public static void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }
}
