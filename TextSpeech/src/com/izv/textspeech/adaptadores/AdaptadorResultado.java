package com.izv.textspeech.adaptadores;

import java.io.File;
import java.util.ArrayList;

import com.izv.textspeech.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorResultado extends ArrayAdapter<File>{
	
	private Context contexto;
	private ArrayList<String> lista;
	
	public AdaptadorResultado(Context c, ArrayList<String> l){
		super(c, R.layout.listview_resultado_hablado);
		this.contexto=c;
		this.lista=l;
	}
	
	@Override
	public int getCount() {
	   return lista.size();
	}

	public View getView(int posicion, View vista, ViewGroup padre){
		
		if(vista==null){
			LayoutInflater i=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista=i.inflate(R.layout.listview_resultado_hablado, null);
		}
		
		TextView tvTexto=(TextView)vista.findViewById(R.id.tvOpcion);
		
		tvTexto.setText(lista.get(posicion));
		
		return vista;
		
	}

}