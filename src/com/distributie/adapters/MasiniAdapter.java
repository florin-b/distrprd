package com.distributie.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.distributie.view.R;

public class MasiniAdapter extends BaseAdapter {

	private List<String> listMasini;
	private Context context;

	public MasiniAdapter(Context context, List<String> listMasini) {
		this.context = context;
		this.listMasini = listMasini;
	}

	private static class ViewHolder {
		TextView textMasina;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_masina, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.textMasina = (TextView) convertView.findViewById(R.id.textMasina);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String masina = (String) getItem(position);

		viewHolder.textMasina.setText(masina);

		return convertView;
	}

	@Override
	public int getCount() {
		return listMasini.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listMasini.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

}
