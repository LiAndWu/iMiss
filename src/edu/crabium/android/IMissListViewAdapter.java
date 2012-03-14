package edu.crabium.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.crabium.android.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class IMissListViewAdapter extends BaseAdapter {
	//private Context context;
	private ArrayList<String[]> array;
	public Map<Integer, Boolean>  isSelected;
	private LayoutInflater inflater = null;
	private final static int NAME = 0;
	private final static int PHONE = 1;
	
	public IMissListViewAdapter(ArrayList<String[]> array, Context context) {
		//this.context = context;
		this.array = new ArrayList<String[]>(array);
		
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		for(int i =0; i< array.size(); i++) {
			isSelected.put(i, false);
		}
    }
	 
	@Override
	public int getCount() {
		return array.size();
	}

	public boolean getCheck(int position){
		return isSelected.get(position);
	}
	@Override
	public Object getItem(int position) {
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.dispatch_select_user_item, null);
			holder.name  = (TextView) convertView.findViewById(R.id.dispatch_item_select_user_name);
			holder.phone = (TextView) convertView.findViewById(R.id.dispatch_item_select_user_phone);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.dispatch_item_select_user_checkbox);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(array.get(position)[NAME]);
		holder.phone.setText(array.get(position)[PHONE]);
		holder.checkBox.setChecked(isSelected.get(position));
		
		return convertView;
	}
	
	public final class ViewHolder {
		 public CheckBox checkBox;
		 public TextView name;
		 public TextView phone;
	 }
	
}

