package com.izv.textspeech.adaptadores;

import java.io.File;
import java.util.ArrayList;

import com.izv.textspeech.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AdaptadorGrabaciones extends ArrayAdapter<File>{
	
	private Context contexto;
	private ArrayList<File> lista;
	
	public AdaptadorGrabaciones(Context c, ArrayList<File> l){
		super(c, R.layout.listview_grabaciones, l);
		this.contexto=c;
		this.lista=l;
	}
	
	/*@Override
	public int getCount() {
	   return lista.size();
	}*/

	public View getView(int posicion, View vista, ViewGroup padre){
		
		if(vista==null){
			LayoutInflater i=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista=i.inflate(R.layout.listview_grabaciones, null);
		}
		
		TextView tvNombre=(TextView)vista.findViewById(R.id.tvNombre);
		Button btPlay=(Button)vista.findViewById(R.id.btPlay);
		Button btPause=(Button)vista.findViewById(R.id.btPause);
		
		tvNombre.setText(lista.get(posicion).getName());
		btPlay.setTag(posicion);
		btPause.setTag(posicion);
				
		return vista;
		
	}

}
