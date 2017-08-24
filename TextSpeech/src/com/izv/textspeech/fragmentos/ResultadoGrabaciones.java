package com.izv.textspeech.fragmentos;

import com.izv.textspeech.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResultadoGrabaciones extends Fragment{

	private View v;
		
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup contenedor, Bundle estado) {
		
		View v = inflater.inflate(R.layout.fragmento_resultado_hablado, contenedor,false);
		this.v=v;
				
		inicio();
		
		return v;
	}
	
	public View getContext(){
		return v;
	}
		
	public void inicio(){
	
		
		
	}

	
}