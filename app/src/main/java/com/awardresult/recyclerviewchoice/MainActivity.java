package com.awardresult.recyclerviewchoice;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.awardresult.recyclerviewchoice.adapter.ProviceChoseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private Toolbar toolbar;

    private TextView tv_Totalselection;
    private RecyclerView rv_provice;
    private Button bt_confirm;

    private List<String> list;

    private boolean choose = false;
    private List<String> listDatas = new ArrayList<>();//记录选择的数据

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        tv_Totalselection = findViewById(R.id.tv_Totalselection);
        tv_Totalselection.setOnClickListener(this);
        rv_provice = findViewById(R.id.rv_provice);
        bt_confirm = findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);
    }

    private ProviceChoseAdapter adapter;

    private void initData() {
        list = new ArrayList<>();//创建数据
        for (int i = 0; i < 30; i++) {
            list.add("数据" + (i+1));
        }
        adapter = new ProviceChoseAdapter(this, list);
        rv_provice.setLayoutManager(new LinearLayoutManager(this));
        rv_provice.setAdapter(adapter);//设置数据
        adapter.setItemClickListener(new ProviceChoseAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Log.d("ExtensionProvinceChoice", "If you are happy - " + position);
                //设置选中的项
                adapter.MultiSelection(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Totalselection:
//                Log.d("MainActivity", "choose:" + choose);
                if (!choose) {
                    choose = true;
                    adapter.All();//全选
//                    Toast.makeText(this, "全选", Toast.LENGTH_SHORT).show();
                    tv_Totalselection.setText("取消全选");
                } else {
                    choose = false;
                    adapter.neverall();
//                    Toast.makeText(this, "取消全选", Toast.LENGTH_SHORT).show();
                    tv_Totalselection.setText("全选");
                }
                break;
            case R.id.bt_confirm:
                String content = "";
                listDatas.clear();

//                Toast.makeText(this, "获取我们选取的数据", Toast.LENGTH_SHORT).show();
//                Log.e("TAG",mGetData.getText().toString());

                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < list.size(); i++) {
                    if (map.get(i)) {
                        listDatas.add(list.get(i));
                    }
                }

                //这里是为了测试我们的结果 ，可忽略！
                for (int j = 0; j < listDatas.size(); j++) {
                    Log.e("TAG", listDatas.get(j));
                    content += listDatas.get(j) + ",";
                }
//                mGetData.setText(content);
//                Log.d("ExtensionProvinceChoice", "选择的数据：" + content);//将数据回传
//                Log.d("ExtensionProvinceChoice", "选择的数据：" + content.substring(0,content.length()-1));//将数据回传
                Intent intent = this.getIntent();
                Bundle bundle = new Bundle();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (content.length() == 0) {
                    builder.setMessage("请选择数据");
                } else {
                    builder.setMessage(content.substring(0, content.length() - 1));
                }
                builder.create().show();
                break;
        }
    }
}
