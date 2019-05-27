package wj.com.myplayer.Config;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import wj.com.myplayer.R;

public class MultipleStatusView extends RelativeLayout{

    private static final String TAG = "MultipleStatusView";

    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS =
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;

    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;

    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;

    private int mViewStatus = -1;
    private LayoutInflater mInflater;


    public MultipleStatusView(Context context) {
        super(context);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStateViewAttrs, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStateViewAttrs_emptyView,0);
        mErrorViewResId = a.getResourceId(R.styleable.MultipleStateViewAttrs_errorView, 0);
        mLoadingViewResId = a.getResourceId(R.styleable.MultipleStateViewAttrs_loadingView,0);
        mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStateViewAttrs_noNetworkView, 0);
        mContentViewResId = a.getResourceId(R.styleable.MultipleStateViewAttrs_contentView, NULL_RESOURCE_ID);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
    }
}
