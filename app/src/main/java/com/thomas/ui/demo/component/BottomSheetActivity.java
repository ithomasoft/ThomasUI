package com.thomas.ui.demo.component;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.ScreenUtils;
import com.thomas.core.utils.TimeUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.ui.ThomasTitleBar;
import com.thomas.ui.demo.R;
import com.thomas.ui.demo.adapter.ItemAdapter;
import com.thomas.ui.demo.base.DemoActivity;
import com.thomas.ui.demo.entity.MenuBean;
import com.thomas.ui.listener.OnDateClickListener;
import com.thomas.ui.listener.OnSingleClickListener;
import com.thomas.ui.quick.ThomasBottomSheet;
import com.thomas.ui.quick.ThomasDateDialog;
import com.thomas.ui.quick.ThomasMessageDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class BottomSheetActivity extends DemoActivity {

    @BindView(R.id.thomasTitleBar)
    ThomasTitleBar thomasTitleBar;
    @BindView(R.id.rv_bottom_sheet)
    RecyclerView rvBottomSheet;

    private ItemAdapter adapter;
    private List<String> datas = new ArrayList<>();
    private List<MenuBean> lessDatas = new ArrayList<>();
    private List<MenuBean> manyDatas = new ArrayList<>();
    private List<MenuBean> gridDatas = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        thomasTitleBar.setListener((v, action, extra) -> {
            if (action == ThomasTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(mActivity);
            }

            if (action == ThomasTitleBar.ACTION_RIGHT_TEXT) {
                ThomasMessageDialog.showSimpleMessage(mActivity,
                        "切换屏幕方向，来看一下弹窗的展示效果",
                        "切换", () -> {

                            if (ScreenUtils.isPortrait()) {
                                ScreenUtils.setLandscape(mActivity);
                            } else {
                                ScreenUtils.setPortrait(mActivity);
                            }
                        });

//                ThomasWindow.showMenu(thomasTitleBar.getRightTextView(), gridDatas, new OnSingleClickListener() {
//                    @Override
//                    public void onClick(int position, String key, String value) {
//                        ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);
//                    }
//                });
            }
        });

        datas.add("弹出底部列表\n(居左显示)");
        datas.add("弹出底部列表\n(居中显示)");
        datas.add("弹出底部列表\n(带标题)");
        datas.add("弹出底部列表\n(多数据)");
        datas.add("弹出底部列表\n(带标题多数据)");
        datas.add("弹出底部时间选择框\n(带日选项和标题)");
        datas.add("弹出底部时间选择框\n(不带日选项和标题)");
        datas.add("弹出底部宫格\n(默认每行4个)");
        datas.add("弹出底部宫格\n(自定义行条目数)");

        adapter = new ItemAdapter(datas);
        rvBottomSheet.setLayoutManager(new GridLayoutManager(mActivity, 2));
        rvBottomSheet.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (position == 0) {
                    ThomasBottomSheet.showBottomSheetNormal(mActivity, Gravity.START, gridDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);

                        }
                    });
                    return;
                }
                if (position == 1) {
                    ThomasBottomSheet.showBottomSheetNormal(mActivity, gridDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);

                        }
                    });
                    return;
                }
                if (position == 2) {
                    ThomasBottomSheet.showBottomSheetWithTitle(mActivity, "测试标题", gridDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);

                        }
                    });
                    return;
                }
                if (position == 3) {
                    ThomasBottomSheet.showBottomSheetNormal(mActivity, manyDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);

                        }
                    });
                    return;
                }
                if (position == 4) {
                    ThomasBottomSheet.showBottomSheetWithTitle(mActivity, "测试标题", manyDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);

                        }
                    });
                    return;
                }
                if (position == 5) {
                    ThomasDateDialog.showDateBottom(mActivity, "测试标题", 2000, 2100, TimeUtils.string2Date("2020-02-02", "yyyy-MM-dd"), true, new OnDateClickListener() {

                        @Override
                        public void onClick(Date selectDate) {
                            ToastUtils.showLong("选择了" + TimeUtils.date2String(selectDate, "yyyy-MM-dd"));
                        }
                    });
                    return;
                }
                if (position == 6) {
                    ThomasDateDialog.showDateBottom(mActivity, 2000, 2100, TimeUtils.string2Date("2020-02-02", "yyyy-MM-dd"), false, new OnDateClickListener() {

                        @Override
                        public void onClick(Date selectDate) {
                            ToastUtils.showLong("选择了" + TimeUtils.date2String(selectDate, "yyyy-MM"));
                        }
                    });
                    return;
                }
                if (position == 7) {
                    ThomasBottomSheet.showBottomGrid(mActivity, gridDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);
                        }
                    });
                    return;
                }

                if (position == 8) {
                    ThomasBottomSheet.showBottomGrid(mActivity,3, gridDatas, new OnSingleClickListener() {
                        @Override
                        public void onClick(int position, String key, String value) {
                            ToastUtils.showLong("点击了第" + position + "条数据，key=" + key + ",value=" + value);
                        }
                    });
                    return;
                }
            }
        });
    }

    @Override
    public void doBusiness() {
        for (int i = 0; i < 8; i++) {
            lessDatas.add(new MenuBean("thomas_" + i, "00" + i));
        }

        for (int i = 0; i < 5; i++) {
            gridDatas.add(new MenuBean("thomas_" + i, "00" + i, R.mipmap.ic_launcher));
        }


        for (int i = 0; i < 80; i++) {
            manyDatas.add(new MenuBean("thomas_" + i, "00" + i));
        }
    }
}
