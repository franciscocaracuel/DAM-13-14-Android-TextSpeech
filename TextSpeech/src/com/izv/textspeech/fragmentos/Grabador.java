package com.izv.textspeech.fragmentos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import MiFecha.MiFecha;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.izv.textspeech.R;
import com.izv.textspeech.adaptadores.AdaptadorResultado;

public class Grabador extends Fragment{

	private View v;
	
	private Button btGrabar;
	private SpeechRecognizer sr;
	private ReconocimientoListener rl;
	private RelativeLayout rlGrabador;
	private ArrayList<String> listaResultados;
		
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup contenedor, Bundle estado) {
		
		View v = inflater.inflate(R.layout.fragmento_grabador, contenedor,false);
		this.v=v;
		
		inicio();
		
		return v;
		
	}
	
	public View getContext(){
		return v;
	}
		
	public void inicio(){
		
		listaResultados=new ArrayList<String>();
		
		btGrabar=(Button)v.findViewById(R.id.btGrabar);
		btGrabar.setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		            comenzarGrabar();
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	terminarGrabar();
		        }
		        return true;
		    }
		});
		
		rlGrabador=(RelativeLayout)v.findViewById(R.id.rlGrabador);
		
		inicializarGrabador();
		
	}
	
	public void inicializarGrabador(){
		
		sr=SpeechRecognizer.createSpeechRecognizer(getActivity());
		rl=new ReconocimientoListener();
		sr.setRecognitionListener(rl);
		sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getActivity()));
		sr.cancel();
		
	}
	
	public void comenzarGrabar(){
		
		//La opcion mas facil es lanzar el intent de Google
		/*Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES");
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.grabador_ahora));
		intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
		startActivityForResult(intent, STT);*/
		
		sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getActivity()));
		
	}
	
	public void terminarGrabar(){
		
		sr.stopListening();
		
	}
	
	public void abrirDialogoResultado(){
		
		final Dialog d=new Dialog(getActivity());
		d.setContentView(R.layout.fragmento_resultado_hablado);
		d.setTitle(getResources().getString(R.string.resultado));
		d.setCanceledOnTouchOutside(true);
		
		final ListView lvResultado=(ListView)d.findViewById(R.id.lvResultado);
		
		lvResultado.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				
				MiFecha mf=new MiFecha();
				
				File f=new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/TextSpeech/", mf.getFechaFormateadaForFile()+".txt");
	
				
				//De esta manera no sobreescribimos, si no que añadimos al final
				FileWriter fw;
				try {
					fw = new FileWriter(f, true);
					fw.write(listaResultados.get(pos));
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Toast.makeText(getActivity(), getResources().getString(R.string.grabacion_guardada), 
						Toast.LENGTH_SHORT).show();
								
				d.cancel();
				
			}
		});
				
		final AdaptadorResultado ag=new AdaptadorResultado(getActivity(), listaResultados);
		registerForContextMenu(lvResultado);
		lvResultado.setAdapter(ag);
		
		d.show();
		
	}

	private class ReconocimientoListener implements RecognitionListener {
			
	    @Override
	    public void onBeginningOfSpeech() {
	            Log.d("Speech", "onBeginningOfSpeech");
	    }

	    @Override
	    public void onBufferReceived(byte[] buffer) {
	            Log.d("Speech", "onBufferReceived");
	    }

	    @Override
	    public void onEndOfSpeech() {
	            Log.d("Speech", "onEndOfSpeech");
	            rlGrabador.setBackgroundColor(Color.TRANSPARENT);      
	    }

	    @Override
	    public void onError(int error) {
	            Log.d("Speech", "onError");
	            rlGrabador.setBackgroundColor(Color.TRANSPARENT);
	            sr.destroy();
	            inicializarGrabador();
	    }

	    @Override
	    public void onEvent(int eventType, Bundle params) {
	            Log.d("Speech", "onEvent");
	    }

	    @Override
	    public void onPartialResults(Bundle partialResults) {
	            Log.d("Speech", "onPartialResults");
	    }

	    @Override
	    public void onReadyForSpeech(Bundle params) {
	            Log.d("Speech", "onReadyForSpeech");
	            rlGrabador.setBackgroundColor(Color.parseColor("#88DD0000"));
	    }
	   

	    @Override
		public void onResults(Bundle results) {
	    	
			Log.d("Speech", "onResults");

			listaResultados = results
					.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

			for (int i = 0; i < listaResultados.size(); i++) {
				Log.d("Speech", "result=" + listaResultados.get(i));
			}

			rlGrabador.setBackgroundColor(Color.TRANSPARENT);

			if (listaResultados.size() != 0) {
				abrirDialogoResultado();
			} else {
				Toast.makeText(getActivity(), R.string.error_guarda,
						Toast.LENGTH_LONG).show();
			}

		}

		@Override
		public void onRmsChanged(float rmsdB) {
			// TODO Auto-generated method stub
			
		}

	}
	
}
