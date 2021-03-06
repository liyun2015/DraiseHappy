package com.or.draise_happy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.or.draise_happy.BuildConfig;
import com.or.draise_happy.R;

/**
 * ImageLoadUtils Imageloader统一封装配置Helper
 * Created by dawabos on 2016/7/18.
 * Email dawabo@163.com
 */
public class ImageLoadUtils {
    private static final int MAX_DISK_CACHE = 1024 * 1024 * 50;
    private static final int MAX_MEMORY_CACHE = 1024 * 1024 * 10;

    /**
     * 是否显示日志
     */
    private static boolean isShowLog = false;

    public static ImageLoader imageLoader;

    /**
     * 单例构建
     * @return
     */
    public static ImageLoader getImageLoader(){

        if(imageLoader==null){
            synchronized (ImageLoadUtils.class){
                imageLoader= ImageLoader.getInstance();
            }
        }

        return imageLoader;
    }

    /**
     * 在Application中初始化参数
     * @param context
     */
    public static void initImageLoader(Context context){
        ImageLoaderConfiguration.Builder build=new ImageLoaderConfiguration.Builder(context);
        build.tasksProcessingOrder(QueueProcessingType.LIFO);
        build.diskCacheSize(MAX_DISK_CACHE);
        build.memoryCacheSize(MAX_MEMORY_CACHE);
        build.memoryCache(new LruMemoryCache(MAX_MEMORY_CACHE));
        if (BuildConfig.DEBUG && isShowLog) {
            build.writeDebugLogs();
        }
        getImageLoader().init(build.build());
    }

    /**
     * 默认Option
     *
     * @param url
     * @param target
     */
    public static void displayImage(String url, ImageView target) {
        imageLoader.displayImage(url, target, getOptions4PictureList());
    }
    /**
     * 自定义Option
     *
     * @param url
     * @param target
     * @param options
     */
    public static void displayImage(String url, ImageView target, DisplayImageOptions options) {
        imageLoader.displayImage(url, target, options);
    }

    /**
     * 头像专用
     *
     * @param url
     * @param target
     */
    public static void displayHeadIcon(String url, ImageView target) {
        imageLoader.displayImage(url, target, getOptions4Header());

    }

    /**
     * 图片详情页专用
     *
     * @param url
     * @param target
     * @param loadingListener
     */
    public static void displayImage4Detail(String url, ImageView target, SimpleImageLoadingListener loadingListener) {
        imageLoader.displayImage(url, target, getOption4ExactlyType(), loadingListener);
    }

    /**
     * 图片列表页专用
     *
     * @param url
     * @param target
     * @param loadingResource
     * @param loadingListener
     * @param progressListener
     */
    public static void displayImageList(String url, ImageView target, int loadingResource, SimpleImageLoadingListener loadingListener, ImageLoadingProgressListener progressListener) {
        imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource), loadingListener, progressListener);
    }

    /**
     * 自定义加载中图片
     *
     * @param url
     * @param target
     * @param loadingResource
     */
    public static void displayImageWithLoadingPicture(String url, ImageView target, int loadingResource) {
        imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource));
    }

    /**
     * 当使用WebView加载大图的时候，使用本方法现下载到本地然后再加载
     *
     * @param url
     * @param loadingListener
     */
    public static void loadImageFromLocalCache(String url, SimpleImageLoadingListener loadingListener) {
        imageLoader.loadImage(url, getOption4ExactlyType(), loadingListener);
    }

    /**
     * 加载头像专用Options，默认加载中、失败和空url为 ic_loading_small
     *
     * @return
     */
    public static DisplayImageOptions getOptions4Header() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.drawable.header_default)
                .showImageOnFail(R.drawable.header_default)
                .showImageOnLoading(R.drawable.header_default)
                .displayer(new CircleBitmapDisplayer())
                .build();
    }

    /**
     * 设置图片放缩类型为模式EXACTLY，用于图片详情页的缩放
     *
     * @return
     */
    public static DisplayImageOptions getOption4ExactlyType() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    /**
     * 加载图片列表专用，加载前会重置View
     * {@link DisplayImageOptions.Builder#resetViewBeforeLoading} = true
     *
     * @param loadingResource
     * @return
     */
    public static DisplayImageOptions getOptions4PictureList(int loadingResource) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(loadingResource)
                .showImageForEmptyUri(loadingResource)
                .showImageOnFail(loadingResource)
                .build();
    }
    /**
     * 初始化加载默认图
     * {@link DisplayImageOptions.Builder#resetViewBeforeLoading} = true
     *
     * @return
     */
    public static DisplayImageOptions getOptions4PictureList() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.drawable.default_photoalbum_img)
                .showImageForEmptyUri(R.drawable.default_photoalbum_img)
                .showImageOnFail(R.drawable.default_photoalbum_img)
                .build();
    }

}
