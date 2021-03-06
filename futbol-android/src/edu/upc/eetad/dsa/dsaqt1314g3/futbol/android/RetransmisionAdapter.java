package edu.upc.eetad.dsa.dsaqt1314g3.futbol.android;

import java.util.ArrayList;

import edu.upc.eetac.dsa.dsaqt1314g3.futbol.android.api.Retransmision;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RetransmisionAdapter extends BaseAdapter{
	
	private ArrayList <Retransmision> data;
	private LayoutInflater inflater;
	
	public RetransmisionAdapter(Context context, ArrayList<Retransmision> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}
	 
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	 
	@Override
	public long getItemId(int position) {
		return Long.parseLong(((Retransmision)getItem(position)).getId());
	}
	
	private static class ViewHolder {
		TextView tvTiempo;
		TextView tvTexto;
		
	}

	@Override
	//mapeas los widgets
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_retransmision, null);
			viewHolder = new ViewHolder();
			viewHolder.tvTiempo = (TextView) convertView
					.findViewById(R.id.tvTiempo);
			viewHolder.tvTexto = (TextView) convertView
					.findViewById(R.id.tvTexto);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//obtener datos
		String tiempo = data.get(position).getTiempo();
		String titulo = data.get(position).getTexto();
		//poner datos
		viewHolder.tvTiempo.setText(tiempo);
		viewHolder.tvTexto.setText(titulo);
		return convertView;
	}

}