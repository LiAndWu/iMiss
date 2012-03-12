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
	public Map<String, String> myData;
	public static Map<Integer, Boolean> isSelected;
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
<<<<<<< HEAD
	// ��ʼ�� 
=======
	
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
	private void init() {
		myData = new HashMap<String, String>();
		for(int i = 0 ; i < count;i++) {
			myData.put(Column1, arr[i][0]);
			myData.put(Column2, arr[i][1]);
		}
<<<<<<< HEAD
		// ����� isSelected ��� map �Ǽ�¼ÿ�� list items ��״̬����ʼ״̬ȫ��Ŷ false
=======
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < count; i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getCount() {
		return count;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
<<<<<<< HEAD
		// convertView Ϊ null ʱ ��ʼ�� convertView
=======
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
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
		holder.LinkManName.setText(myData.get(Column1).toString());
		holder.LinkManPhone.setText(myData.get(Column2).toString());
		return convertView;
	}
	
	public final class ViewHolder {
		public TextView LinkManName;
		public TextView LinkManPhone;
		public CheckBox cBox;
	}
}
