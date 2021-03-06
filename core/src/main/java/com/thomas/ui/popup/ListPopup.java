package com.thomas.ui.popup;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.ui.R;
import com.thomas.ui.entity.AbsKV;
import com.thomas.ui.helper.RecyclerViewHelper;
import com.thomas.ui.helper.ScreenHelper;
import com.thomas.ui.listener.OnSingleClickListener;

import java.util.List;

import razerdp.basepopup.BaseLazyPopupWindow;
import razerdp.util.animation.AlphaConfig;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.ScaleConfig;

/**
 * 菜单popupWindow
 */
public class ListPopup extends BaseLazyPopupWindow {
    private Builder builder;
    private RecyclerView rvDialogContent;

    public ListPopup(Context context) {
        super(context);
    }

    private ListPopup(Context context, Builder builder) {
        this(context);
        this.builder = builder;

        if (ScreenHelper.isLandscape(context)) {
            //横屏
            setMaxHeight((ScreenHelper.getScreenHeight(context) / 3) * 2);
            setMaxWidth(ScreenHelper.getScreenWidth(context) / 4);
            setMinWidth(ScreenHelper.getScreenWidth(context) / 4);
            setMinHeight(ScreenHelper.getScreenHeight(context) / 4);
        } else {
            //竖屏
            setMaxHeight((ScreenHelper.getScreenHeight(context) / 3) * 2);
            setMaxWidth(ScreenHelper.getScreenWidth(context) / 2);
            setMinWidth((ScreenHelper.getScreenWidth(context) / 3) * 2);
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_menu_popup);
    }


    @Override
    public void showPopupWindow(View anchorView) {
        setOffsetX(anchorView.getWidth());
        setAlignBackground(false);
        super.showPopupWindow(anchorView);

    }


    @Override
    public void onViewCreated(View contentView) {
        rvDialogContent = findViewById(R.id.thomas_rv_content);

        DialogMenuAdapter adapter = new DialogMenuAdapter();

        rvDialogContent.setAdapter(adapter);
        rvDialogContent.setLayoutManager(RecyclerViewHelper.getDefaultLayoutManager(getContext()));
        rvDialogContent.addItemDecoration(RecyclerViewHelper.getDefaultItemDecoration(getContext()));
        adapter.setNewInstance(builder.items);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AbsKV clickBean = (AbsKV) adapter.getData().get(position);
                if (builder.onSingleClickListener != null) {
                    builder.onSingleClickListener.onClick(position, clickBean.getKey(), clickBean.getValue());
                }
                dismiss();
            }
        });
    }


    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation().withScale(ScaleConfig.RIGHT_TO_LEFT)
                .withScale(ScaleConfig.TOP_TO_BOTTOM)
                .withAlpha(AlphaConfig.IN).toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation().withScale(ScaleConfig.LEFT_TO_RIGHT)
                .withScale(ScaleConfig.BOTTOM_TO_TOP)
                .withAlpha(AlphaConfig.OUT).toDismiss();
    }

    public static class Builder<T extends AbsKV> {
        private Context context;
        private List<T> items;
        private OnSingleClickListener onSingleClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setItems(List<T> datas) {
            this.items = datas;
            return this;
        }

        public Builder setOnItemClickListener(OnSingleClickListener onSingleClickListener) {
            this.onSingleClickListener = onSingleClickListener;
            return this;
        }

        public ListPopup build() {
            return new ListPopup(context, this);
        }
    }

    private class DialogMenuAdapter<T extends AbsKV> extends BaseQuickAdapter<T, BaseViewHolder> {

        public DialogMenuAdapter() {
            super(R.layout.item_view_menu_popup);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, T item) {
            helper.setText(R.id.thomas_tv_item_name, item.getKey());
            if (item.getResId() == 0) {
                helper.findView(R.id.thomas_iv_item_res).setVisibility(View.GONE);
            } else {
                helper.findView(R.id.thomas_iv_item_res).setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.thomas_iv_item_res, item.getResId());
            }
        }
    }
}
