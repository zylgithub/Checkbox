package com.awardresult.recyclerviewchoice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.awardresult.recyclerviewchoice.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/8/31 0031.]
 * 数据适配器
 */

public class ProviceChoseAdapter extends RecyclerView.Adapter<ProviceChoseAdapter.Holder> {
    private Context mContent;
    private List<String> list;

    //    //这个是checkbox的Hashmap集合
//    private Map<Integer, Boolean> map = new HashMap<>();
    //多选
    private HashMap<Integer, Boolean> Maps = new HashMap<Integer, Boolean>();

    public ProviceChoseAdapter(Context mContent, List<String> list) {
        this.mContent = mContent;
        this.list = list;
        initMap();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContent).inflate(R.layout.item_provice_chose, parent, false);
        return new Holder(itemView);
    }

    private void initMap() {
        for (int i = 0; i < list.size(); i++) {
            Maps.put(i, false);//记录状态
        }
    }

    //全选方法
    public void All() {
        Set<Map.Entry<Integer, Boolean>> entries = Maps.entrySet();
        boolean shouldall = false;
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();
            if (!value) {
                shouldall = true;
                break;
            }
        }
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldall);
        }
        notifyDataSetChanged();
    }

    //反选
    public void neverall() {
        Set<Map.Entry<Integer, Boolean>> entries = Maps.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(!entry.getValue());
        }
        notifyDataSetChanged();
    }

    //多选
    public void MultiSelection(int position) {
        //对当前状态取反
        if (Maps.get(position)) {
            Maps.put(position, false);
        } else {
            Maps.put(position, true);
        }
        notifyItemChanged(position);
    }

    //获取最终的map存储数据
    public Map<Integer, Boolean> getMap() {
        return Maps;
    }

   /* //后续扩展 - 获取最终的map存储数据
    public Map<Integer, Boolean> getAllMap() {
        return map;
    }*/

    public RecyclerViewOnItemClickListener onItemClickListener;

    //回调的接口
    public void setItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.province_name.setText(list.get(position).toString());
        holder.cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Maps.put(position, isChecked);
            }
        });

        if (Maps.get(position) == null) {
            Maps.put(position, false);
        }
        //没有设置tag之前会有item重复选框出现，设置tag之后，此问题解决
        holder.cbox.setChecked(Maps.get(position));


//        //之后扩展使用
//        map.put(position, true);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private RecyclerViewOnItemClickListener mListener;//接口
        private CheckBox cbox;
        private TextView province_name;

        public Holder(View itemView) {
            super(itemView);
            cbox = itemView.findViewById(R.id.cbox);
            province_name = itemView.findViewById(R.id.province_name);
        }
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);
    }
}
