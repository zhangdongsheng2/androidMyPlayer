package com.example.myplayer.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.db.DaoMaster;
import com.example.myplayer.db.DaoSession;
import com.example.myplayer.db.Memo;
import com.example.myplayer.db.MemoDao;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.util.DateUtils;
import com.example.myplayer.util.GsonTools;
import com.example.myplayer.util.SaveDataHelper;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.aigestudio.datepicker.views.MonthView;

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

//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "jiji.db", null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        MemoDao userDao = daoSession.getMemoDao();
//        final List<Memo_extra> list1 = daoSession.getMemo_extraDao().queryBuilder().build().list();
//        List<Memo> list = userDao.queryBuilder().build().list();
//        Observable.from(list)
//                .subscribe(new Action1<Memo>() {
//                    @Override
//                    public void call(Memo memo) {
//
//                        for (Memo_extra extra :
//                                list1) {
//                            if (extra.getMemoid().equals(memo.id)) {
//                                memo.setContent(memo.content + "  -:-Title" + extra.getWextratitle());
//                            }
//                        }
//
//
////                        daoHashMap.put(memo.addyear + "-" + memo.addmonth + "-" + memo.addday, memo);
//                    }
//                });
//        Bean bean = new Bean();
//        bean.memoArrayList = list;
//        SaveDataHelper.put("MeditationFragment", GsonHelper.toJson(bean));


        String meditationFragment = SaveDataHelper.get("MeditationFragment");
        Bean memos = GsonTools.changeGsonToBean(meditationFragment, Bean.class);

        DaoMaster.DevOpenHelper devOpenHelperTwo = new DaoMaster.DevOpenHelper(getContext(), "dateJi.db");
        DaoMaster daoMasterTwo = new DaoMaster(new DBHelper(getContext()).getWritableDatabase());
        DaoSession daoSessionTwo = daoMasterTwo.newSession();
        final MemoDao userDaoTwo = daoSessionTwo.getMemoDao();


        for (Memo memo : memos.memoArrayList) {
            userDaoTwo.insert(memo);

        }

//
//        devOpenHelperTwo.onCreate(devOpenHelperTwo.getWritableDatabase());


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

    }

    @Override
    protected void initData() {

    }

    public static class Bean {
        public String a = "aaa";
        public List<Memo> memoArrayList;
    }

    public class DBHelper extends DaoMaster.OpenHelper {
        public static final String DBNAME = "dateJi.db";

        public DBHelper(Context context) {
            super(context, DBNAME, null);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            KLog.e(oldVersion + "==============" + newVersion);
            super.onUpgrade(db, oldVersion, 2);
        }
    }
}
