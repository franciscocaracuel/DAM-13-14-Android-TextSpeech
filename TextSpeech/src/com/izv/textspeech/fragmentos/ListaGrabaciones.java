package com.izv.textspeech.fragmentos;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.izv.textspeech.R;
import com.izv.textspeech.adaptadores.AdaptadorGrabaciones;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ListaGrabaciones extends Fragment{

	private View v;
	
	//Mis variables
	private ListView lvGrabaciones;
	private ArrayList<File> lista;
	private File carpeta;
	private TextView tvNoGrabacion;
	private SeekBar sbTono, sbVelocidad;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup contenedor, Bundle estado) {
		
		View v = inflater.inflate(R.layout.fragmento_listagrabaciones, contenedor,false);
		this.v=v;
		
		inicio();
		
		return v;
	}
	
	public View getContext(){
		return v;
	}
		
	public void inicio(){
	
		lvGrabaciones=(ListView)v.findViewById(R.id.lvGrabaciones);
		
		sbTono=(SeekBar)v.findViewById(R.id.sbTono);
		sbVelocidad=(SeekBar)v.findViewById(R.id.sbVelocidad);
		
		tvNoGrabacion=(TextView)v.findViewById(R.id.tvNoGrabacion);
		tvNoGrabacion.setVisibility(View.GONE);
		
		carpeta=new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/TextSpeech");
				
		compruebaCarpeta();
		
		crearLista();
		
		cargarLista();
		
	}
	
	public ArrayList<File> getLista(){
		return lista;
	}
	
	
	public void compruebaCarpeta(){
		
		if (!carpeta.exists()) {
			carpeta.mkdir();
		}
		
	}
	
	public void crearLista(){
		
		//Convierte el array de File en un arrayList
		lista=new ArrayList<File>(Arrays.asList(carpeta.listFiles()));
		
	}
	
	public void cargarLista(){
		
		if(lista.size()==0){
			tvNoGrabacion.setVisibility(View.VISIBLE);
		}else{
			tvNoGrabacion.setVisibility(View.GONE);
		}
		
		final AdaptadorGrabaciones ag=new AdaptadorGrabaciones(getActivity(), lista);
		registerForContextMenu(lvGrabaciones);
		lvGrabaciones.setAdapter(ag);
		
	}
	
	public float getTono(){
		if(sbTono.getProgress()==0){
			return 0.1f;
		}else{
			return (float)sbTono.getProgress()/10;
		}
	}
	
	public float getVelocidad(){
		if(sbVelocidad.getProgress()==0){
			return 0.1f;
		}else{
			return (float)sbVelocidad.getProgress()/10;
		}
	}
	
}