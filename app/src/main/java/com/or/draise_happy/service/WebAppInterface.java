package com.or.draise_happy.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.baidu.mobstat.StatService;
import com.or.draise_happy.ui.activity.CommentListActivity;
import com.or.draise_happy.ui.activity.other.WebViewActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class WebAppInterface {
	Context mContext;

	public WebAppInterface(Context c) {
		mContext = c;
	}
	
	/** Show a toast from the web page */
	// 如果target 大于等于API 17，则需要加上如下注解
	@JavascriptInterface
	public void finishPage() {
		//Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
		((Activity)mContext).finish();
	}

	@JavascriptInterface
	public void goToCommentList(String newsId,String tableName) {
		Intent intent = new Intent();
		intent.setClass(mContext, CommentListActivity.class);
		intent.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, newsId);
		intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, tableName);
		((Activity)mContext).startActivity(intent);
	}

	@JavascriptInterface
	public void shareAndroid(final String title,final String desc,final String shareurl,final String shareImageUrl){
		StatService.onEvent(mContext, "Social_sharing", "pass", 1);
		((Activity) mContext).runOnUiThread(new Runnable() {
			private UMImage umImage;
			public void run() {
				if (TextUtils.isEmpty(shareImageUrl)) {
					showImage(title, desc, shareurl);
				}else {
					umImage = new UMImage(mContext,shareImageUrl);
					final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] {
							SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
							SHARE_MEDIA.SINA };
					ShareBoardConfig config = new ShareBoardConfig();
					config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
					config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
					new ShareAction((Activity) mContext).setDisplayList(displaylist)
							.setShareboardclickCallback(new ShareBoardlistener() {
								
								@Override
								public void onclick(SnsPlatform arg0, SHARE_MEDIA share_media) {
									if (share_media.toString().equals("SMS")) {
										new ShareAction((Activity) mContext).setPlatform(share_media)
						                .withText(desc+shareurl).withTitle(title)
										.withTargetUrl(shareurl)
						                .share();
									}else {
										new ShareAction((Activity) mContext).setPlatform(share_media)
						                .withText(desc).withTitle(title)
										.withTargetUrl(shareurl)
										.withMedia(umImage)
						                .share();
									}
								}
							}).open(config);
				}
			}
		});
	}
	private void showImage(final String title, final String desc,
			final String shareurl) {
		final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] {
				SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
				SHARE_MEDIA.SINA  };
		ShareBoardConfig config = new ShareBoardConfig();
		config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
		config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆
		new ShareAction((Activity) mContext).setDisplayList(displaylist)
				.setShareboardclickCallback(new ShareBoardlistener() {
					
					@Override
					public void onclick(SnsPlatform arg0, SHARE_MEDIA share_media) {
						if (share_media.toString().equals("SMS")) {
							new ShareAction((Activity) mContext).setPlatform(share_media)
			                .withText(desc+shareurl).withTitle(title)
							.withTargetUrl(shareurl)
			                .share();
						}else {
							new ShareAction((Activity) mContext).setPlatform(share_media)
			                .withText(desc).withTitle(title)
							.withTargetUrl(shareurl)
			                .share();
						}
					}
				}).open(config);
	}
}
