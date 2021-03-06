package com.lilosoft.xtcm.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.lilosoft.xtcm.R;
import com.lilosoft.xtcm.base.NormalBaseActivity;
import com.lilosoft.xtcm.constant.Config;
import com.lilosoft.xtcm.constant.LayoutStructure;
import com.lilosoft.xtcm.instantiation.FileBean;
import com.lilosoft.xtcm.instantiation.HistoryExamineBean;
import com.lilosoft.xtcm.utils.ImageTool;
import com.lilosoft.xtcm.views.MPProgressBar;
import com.lilosoft.xtcm.views.TitleBar;
import com.lilosoft.xtcm.views.TitleBar.STYLE;

public class QuestionHistoryExamineActivity extends NormalBaseActivity
        implements OnClickListener {

    private final static int MSG_INIT_SUCCESS = 0x0F1;
    private final static int MSG_INIT_LOSE = 0x0F2;
    /**
     * @category 主线程处理
     */
    @SuppressLint("HandlerLeak")
    private Handler myHandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case Config.SHOW_PROGRESS_DIALOG:
                    showProgressDialog("初始中…");
                    break;
                case Config.DISMISS_PROGRESS_DIALOG:
                    dismissProgressDialog();
                    break;
                case MSG_INIT_SUCCESS:
                    initViews();
                    break;
                case MSG_INIT_LOSE:
                    Toast.makeText(mContext, R.string.error_load_data,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        }

    };
    private HistoryExamineBean historyExamineBean = QuestionHistoryExamineListActivity.bean;
    private TitleBar mTitleBar;
    private ListView history_info_list;
    private List<Map<String, Object>> data;
    private List<FileBean> imageList;
    private View history_info_medio;
    private ImageView h_media_p1, h_media_p2, h_media_p3, h_img_preview;
    private boolean imgIsShow = false;
    private Message m;
    Thread initThread = new Thread(new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            m = new Message();
            m.what = Config.SHOW_PROGRESS_DIALOG;
            myHandle.sendMessage(m);
            try {
                readData();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                m = new Message();
                m.what = MSG_INIT_LOSE;
                myHandle.sendMessage(m);
            } finally {
                m = new Message();
                m.what = Config.DISMISS_PROGRESS_DIALOG;
                myHandle.sendMessage(m);
            }
        }
    });

    @Override
    protected void installViews() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_question_history_examine);
        initTitleBar();
        initValues();
        threadG = new Thread(initThread);
        threadG.start();

    }

    @Override
    protected void registerEvents() {
        // TODO Auto-generated method stub
        h_media_p1.setOnClickListener(this);
        h_media_p2.setOnClickListener(this);
        h_media_p3.setOnClickListener(this);
        h_img_preview.setOnClickListener(this);
    }

    /**
     * @category 初始化titleBar
     */
    protected void initTitleBar() {

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);

        mTitleBar.changeStyle(STYLE.NOT_BTN_AND_TITLE);

        mTitleBar.centerTextView.setText(R.string.function_history_hc_i);

    }

    protected void initValues() {

        mPProgressBar = (MPProgressBar) findViewById(R.id.mPProgressBar);

        history_info_list = (ListView) findViewById(R.id.history_info_list);
        history_info_medio = findViewById(R.id.history_info_medio);
        h_media_p1 = (ImageView) findViewById(R.id.h_media_p1);
        h_media_p2 = (ImageView) findViewById(R.id.h_media_p2);
        h_media_p3 = (ImageView) findViewById(R.id.h_media_p3);
        h_img_preview = (ImageView) findViewById(R.id.h_img_preview);

    }

    private void initViews() {
        // 初始化内容
        history_info_list.setAdapter(new SimpleAdapter(mContext, data,
                R.layout.view_history_value_item, LayoutStructure.from,
                new int[] { R.id.h_i_title, R.id.h_i_value }));
        // 初始化图片
        if (null != imageList) {
            switch (imageList.size()) {
                case 1:
                    h_media_p1.setVisibility(0);
                    h_media_p1.setImageDrawable(Drawable
                            .createFromPath(Config.FILES_NAME_URL
                                    + imageList.get(0).getfName()));
                    h_media_p1.setScaleType(ScaleType.CENTER_CROP);
                    break;
                case 2:
                    h_media_p1.setVisibility(0);
                    h_media_p2.setVisibility(0);
                    h_media_p1.setImageDrawable(Drawable
                            .createFromPath(Config.FILES_NAME_URL
                                    + imageList.get(0).getfName()));
                    h_media_p2.setImageDrawable(Drawable
                            .createFromPath(Config.FILES_NAME_URL
                                    + imageList.get(1).getfName()));
                    h_media_p1.setScaleType(ScaleType.CENTER_CROP);
                    h_media_p2.setScaleType(ScaleType.CENTER_CROP);
                    break;
                case 3:
                    h_media_p1.setVisibility(0);
                    h_media_p2.setVisibility(0);
                    h_media_p3.setVisibility(0);
                    h_media_p1.setImageDrawable(Drawable
                            .createFromPath(Config.FILES_NAME_URL
                                    + imageList.get(0).getfName()));
                    h_media_p2.setImageDrawable(Drawable
                            .createFromPath(Config.FILES_NAME_URL
                                    + imageList.get(1).getfName()));
                    h_media_p3.setImageDrawable(Drawable
                            .createFromPath(Config.FILES_NAME_URL
                                    + imageList.get(2).getfName()));
                    h_media_p1.setScaleType(ScaleType.CENTER_CROP);
                    h_media_p2.setScaleType(ScaleType.CENTER_CROP);
                    h_media_p3.setScaleType(ScaleType.CENTER_CROP);
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * @category 图片处理 值的顺序和是否需要显示 HistoryDisposeBean-getList()方法的值顺序和是否传入决定
     */
    private void readData() {

        // 值写入
        data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < historyExamineBean.getList().size() / 2; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(LayoutStructure.from[0],
                    historyExamineBean.getList().get(i * 2));
            map.put(LayoutStructure.from[1],
                    historyExamineBean.getList().get(i * 2 + 1));
            data.add(map);
        }

        // 图片写入
        if (null != (imageList = historyExamineBean.getApproveFileList())) {
            for (int i = 0; i < imageList.size(); i++) {
                ImageTool.writeFile(imageList.get(i));
            }
        }
        m = new Message();
        m.what = MSG_INIT_SUCCESS;
        myHandle.sendMessage(m);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.h_media_p1:
                h_img_preview.setImageDrawable(Drawable
                        .createFromPath(Config.FILES_NAME_URL
                                + imageList.get(0).getfName()));
                h_img_preview.setVisibility(0);
                imgIsShow = true;
                break;
            case R.id.h_media_p2:
                h_img_preview.setImageDrawable(Drawable
                        .createFromPath(Config.FILES_NAME_URL
                                + imageList.get(1).getfName()));
                h_img_preview.setVisibility(0);
                imgIsShow = true;
                break;
            case R.id.h_media_p3:
                h_img_preview.setImageDrawable(Drawable
                        .createFromPath(Config.FILES_NAME_URL
                                + imageList.get(2).getfName()));
                h_img_preview.setVisibility(0);
                imgIsShow = true;
                break;
            default:
                if (imgIsShow) {
                    h_img_preview.setVisibility(8);
                    imgIsShow = false;
                }
                break;
        }
    }
}
