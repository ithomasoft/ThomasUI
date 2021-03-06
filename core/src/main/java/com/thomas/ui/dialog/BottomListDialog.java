package com.thomas.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.ui.R;
import com.thomas.ui.entity.AbsKV;
import com.thomas.ui.helper.ClickHelper;
import com.thomas.ui.helper.RecyclerViewHelper;
import com.thomas.ui.helper.ScreenHelper;
import com.thomas.ui.listener.OnSingleClickListener;

import java.util.List;

import razerdp.basepopup.BaseLazyPopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

public class BottomListDialog extends BaseLazyPopupWindow {

    private AppCompatTextView tvDialogTitle;
    private RecyclerView rvDialogContent;
    private View viewDialogDividerTitle;
    private Builder builder;

    private BottomListDialog(Context context) {
        super(context);
    }

    private BottomListDialog(Context context, Builder builder) {
        this(context);
        this.builder = builder;
        setAlignBackground(false);
        setClipChildren(false);
        setOutSideTouchable(false);
        setPopupGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        if (ScreenHelper.isLandscape(context)) {
            //横屏
            setMaxHeight((ScreenHelper.getScreenHeight(context) / 3) * 2);
            setMaxWidth(ScreenHelper.getScreenHeight(context));
            setMinWidth(ScreenHelper.getScreenHeight(context));
        } else {
            //竖屏
            setMaxHeight((ScreenHelper.getScreenHeight(context) / 3) * 2);
            setMaxWidth(ScreenHelper.getScreenWidth(context));
            setMinWidth(ScreenHelper.getScreenWidth(context));
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_bottom_list_dialog);
    }

    @Override
    public void onViewCreated(View contentView) {
        tvDialogTitle = findViewById(R.id.thomas_tv_title);
        rvDialogContent = findViewById(R.id.thomas_rv_content);
        viewDialogDividerTitle = findViewById(R.id.thomas_divider_title);
        if (TextUtils.isEmpty(builder.title)) {
            tvDialogTitle.setVisibility(View.GONE);
            viewDialogDividerTitle.setVisibility(View.GONE);
        } else {
            tvDialogTitle.setVisibility(View.VISIBLE);
            viewDialogDividerTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText(builder.title);
        }

        DialogMenuAdapter adapter = new DialogMenuAdapter(builder.gravity);

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
        ClickHelper.applyGlobalDebouncing(contentView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM).toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM).toDismiss();
    }


    public static class Builder<T extends AbsKV> {
        private Context context;
        private String title;
        private int gravity = Gravity.CENTER;
        private List<T> items;
        private OnSingleClickListener onSingleClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }


        public Builder setItems(List<T> datas) {
            this.items = datas;
            return this;
        }


        public Builder setOnItemClickListener(OnSingleClickListener onSingleClickListener) {
            this.onSingleClickListener = onSingleClickListener;
            return this;
        }


        public BottomListDialog build() {
            return new BottomListDialog(context, this);
        }
    }


    private class DialogMenuAdapter<T extends AbsKV> extends BaseQuickAdapter<T, BaseViewHolder> {
        private int gravity;

        public DialogMenuAdapter(int gravity) {
            super(R.layout.item_view_menu_bottom);
            this.gravity = gravity;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, T item) {
            ((AppCompatTextView) helper.findView(R.id.thomas_tv_item_name)).setGravity(gravity);
            helper.setText(R.id.thomas_tv_item_name, item.getKey());
            if (gravity == Gravity.CENTER || item.getResId() == 0) {
                helper.findView(R.id.thomas_iv_item_res).setVisibility(View.GONE);
            } else {
                helper.findView(R.id.thomas_iv_item_res).setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.thomas_iv_item_res, item.getResId());
            }
        }
    }
}
