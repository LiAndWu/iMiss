package edu.crabium.android;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.crabium.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	public ArrayList<Map<String, Object>> mydata;
	public static HashMap<Integer,Boolean> isSelected;
	private String Column1 = "name";
	private String Column2 = "phone";
	private int count = 0; 
	private String [][] arr;
	
	public MyListAdapter(Context context,int count,String [][] arr) {
		
		mInflater = LayoutInflater.from(context);
		this.count = count;
		this.arr = arr;
		init();
	}
	// ��ʼ�� 
	private void init() {
		mydata = new ArrayList<Map<String,Object>>();
		for(int i = 0 ; i < count;i++) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put(Column1, arr[i][0]);
			map.put(Column2, arr[i][1]);
			mydata.add(map);
		}
		// ����� isSelected ��� map �Ǽ�¼ÿ�� list items ��״̬����ʼ״̬ȫ��Ŷ false
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < count; i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// convertView Ϊ null ʱ ��ʼ�� convertView
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.dispatch_select_user_item, null);
			holder.LinkManName = (TextView) convertView.findViewById(R.id.dispatch_item_select_user_name);
			holder.LinkManPhone = (TextView) convertView.findViewById(R.id.dispatch_item_select_user_phone);
			holder.cBox = (CheckBox) convertView.findViewById(R.id.dispatch_item_select_user_checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cBox.setChecked(isSelected.get(position));
		holder.LinkManName.setText(mydata.get(position).get(Column1).toString());
		holder.LinkManPhone.setText(mydata.get(position).get(Column2).toString());
		return convertView;
	}
	
	public final class ViewHolder {
		public TextView LinkManName;
		public TextView LinkManPhone;
		public CheckBox cBox;
	}
}
