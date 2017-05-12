package cn.trinea.android.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pts80.framework.R;

/**
 * Created by Sai on 15/8/4. 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
	private ImageView imageView;

	@Override
	public View createView(Context context) {
		// 你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
		imageView = new ImageView(context);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imageView;
	}

	@Override
	public void UpdateUI(Context context, int position, String url) {
		if (TextUtils.isEmpty(url)) {
			url = "drawable://" + R.drawable.ic_default_adimage;
		}
		ImageLoader.getInstance().displayImage(url, imageView);
	}
}
