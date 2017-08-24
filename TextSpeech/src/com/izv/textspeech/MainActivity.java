package com.izv.textspeech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import com.izv.textspeech.fragmentos.Grabador;
import com.izv.textspeech.fragmentos.ListaGrabaciones;

import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

public class MainActivity extends FragmentActivity implements TextToSpeech.OnInitListener{

	private int TTS=1;	
	
	//VIEW PAGER//
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	
	
	private ListaGrabaciones lg;
	private ArrayList<File> lista;
	private File archivo;
	private TextToSpeech tts;	
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inicio();

	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	}	
		
	@Override
	public void onDestroy() {		
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}		
		super.onDestroy();	
	}

	public void inicio(){
		
		lg = new ListaGrabaciones();
		
		tts=new TextToSpeech(this, this);
		tts.setLanguage(Locale.getDefault());
				
		//Se crea el adaptador que devolvera un fragmento por cada una de las 
		//secciones que tenga nuestra app
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Se indica donde va a estar el viewpager
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				lg.crearLista();
				lg.cargarLista();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
		
	//Metodo play del item del listview
	public void play(View v){

		lista=lg.getLista();
		
		archivo=lista.get(Integer.parseInt(v.getTag()+""));
				
		BufferedReader br;
		
		StringBuilder texto=new StringBuilder();
		
		try {
			
			br = new BufferedReader(new FileReader(archivo));
			
			String linea;
			
			while((linea=br.readLine()) != null){
				texto.append(linea);
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			
		} catch (IOException e){
			
		}
		
		reproducirTts(texto.toString());
		
	}
	
	public void stop(View v){
		tts.stop();
	}
	
	public void reproducirTts(String texto){
		
		tts.setPitch(lg.getTono());
		tts.setSpeechRate(lg.getVelocidad());
		tts.speak(texto, TextToSpeech.QUEUE_FLUSH,null);
		
	}	
	
	public void comprobarTts(){
		
		Intent intent = new Intent();
		intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(intent, TTS);
		
	}

	public void onInit(int estado) {
	    
		comprobarTts();
		
	}
	
	@Override
	public void onActivityResult(int request, int result, Intent data){

		if (request == TTS) {
			if (result == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				tts = new TextToSpeech(this, this);
			} else {
				Intent intent = new Intent();
				intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(intent);
			}
		}

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////CLASE DEL VIEW PAGER////////////////////////////////////////////
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		//Este metodo ira mostrando un fragmento u otro dependiendo de la pagina en la que este
		@Override
		public Fragment getItem(int position) {
			
			// getitem  es llamado al intanciar el fragment y devuelve la pagina			
				
			switch(position){
				case 0:
					return lg;
				case 1:
					return new Grabador();
				
				default:
					return new Fragment();
			}
						
		}

		@Override
		public int getCount() {
			// Muestra en total 2 paginas
			return 2;
		}

		//Devuelve el titulo de la pagina
		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.titulo_seccion1).toUpperCase(l);
			case 1:
				return getString(R.string.titulo_seccion2).toUpperCase(l);			
			}
			return null;
		}
	}

}
