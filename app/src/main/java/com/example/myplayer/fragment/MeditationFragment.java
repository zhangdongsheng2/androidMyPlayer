package com.example.myplayer.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import com.example.myplayer.MyApplication;
import com.example.myplayer.R;
import com.example.myplayer.db.DaoMaster;
import com.example.myplayer.db.DaoSession;
import com.example.myplayer.db.Memo;
import com.example.myplayer.db.MemoDao;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.util.DateUtils;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.aigestudio.datepicker.views.MonthView;
import rx.Observable;
import rx.functions.Action1;

import static com.example.myplayer.util.FileUtil.copyFile;
import static com.example.myplayer.util.FileUtil.getFileDirectory;

/**
 * Created by Administrator on 2016/3/31.
 * Three
 */
public class MeditationFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meditation;
    }

    @Override
    protected void initWidget() {
        final DatePicker picker = (DatePicker) mRootView.findViewById(R.id.main_dp);
        picker.setDate(DateUtils.getNowYear(), DateUtils.getNowMonth());
        // 自定义前景装饰物绘制示例 Example of custom date's foreground decor
        final HashMap<String, Memo> daoHashMap = new HashMap<>();

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "dateJi.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MemoDao userDao = daoSession.getMemoDao();
        List<Memo> list = userDao.queryBuilder().build().list();
        Observable.from(list)
                .subscribe(new Action1<Memo>() {
                    @Override
                    public void call(Memo memo) {
                        daoHashMap.put(memo.addyear + "-" + memo.addmonth + "-" + memo.addday, memo);
                    }
                });

        DPCManager.getInstance().setDecorTL(new ArrayList<String>(daoHashMap.keySet()));

        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);

        picker.setMode(DPMode.SINGLE);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTL(canvas, rect, paint, data);
                paint.setColor(Color.RED);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 4, paint);
            }
        });
        mRootView.findViewById(R.id.btn_copt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File oldFile = new File(getFileDirectory(), "dateJi.db");
                if (!oldFile.exists()) {
                    return;
                }
                File file = MyApplication.getContext().getDatabasePath("dateJi.db");
                copyFile(oldFile, file);
            }
        });
        mRootView.findViewById(R.id.btn_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(DateUtils.getNowYear(), DateUtils.getNowMonth());
                MonthView field = CommonUtil.getField(picker, "monthView", MonthView.class);
                try {
                    CommonUtil.getDeclaredMethod(field, "smoothScrollTo", int.class, int.class).invoke(field, 0, 0);
                    CommonUtil.getField(field, "lastMoveX").set(field, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final TextView tvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                KLog.e(date);
                Memo memo = daoHashMap.get(date);
                if (memo != null) {
                    tvContent.setText(memo.content);
                } else {
                    tvContent.setText("点击填写");
                }
            }
        });
        picker.setOnDateScrollChangeListener(new MonthView.OnDateScrollChangeListener() {
            @Override
            public void onDataCurrent(int year, int month) {
                KLog.e(year + "============" + month);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
