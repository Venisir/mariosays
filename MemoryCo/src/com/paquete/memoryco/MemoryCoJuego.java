package com.paquete.memoryco;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MemoryCoJuego extends Activity {

	private Timer t;
	private int TimeCounter = 0;
	private int nivel = 1;
	private int vecesReproducidas = 0;
	private boolean estaEnPausa = false;
	private int[ ] historial = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private int contador = 0;

	private Button buttonMoneda;
	private Button buttonSalto;
	private Button buttonVida;
	private Button buttonSeta;
	private Button buttonRestart;

	MediaPlayer mp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_juego);

		//text = (TextView) findViewById(R.id.tvTimer);
		
        buttonMoneda = (Button) findViewById(R.id.buttonMoneda);
   		buttonSeta= (Button) findViewById(R.id.buttonSeta);
   		buttonVida = (Button) findViewById(R.id.buttonVida);
   		buttonSalto = (Button) findViewById(R.id.buttonSalto);
   		buttonRestart = (Button) findViewById(R.id.buttonRestart);
   		 
   		buttonMoneda.setVisibility(View.INVISIBLE);
		buttonSeta.setVisibility(View.INVISIBLE);
		buttonSalto.setVisibility(View.INVISIBLE);
		buttonVida.setVisibility(View.INVISIBLE);
		buttonRestart.setVisibility(View.INVISIBLE);
		
		//////////////////////////////////////////////////////////////////////
		 
		 t = new Timer();
		 t.scheduleAtFixedRate(new TimerTask() {
		    @Override
		    public void run() {
		      
		        runOnUiThread(new Runnable() {
		            public void run() {
		            	
		            	TextView cuenta = (TextView)findViewById(R.id.tvTimer);
		               	
		               	if(estaEnPausa == false && TimeCounter >= 1){
		               		if(vecesReproducidas<nivel){
		               			if (mp!=null && mp.isPlaying()){                     
		               				mp.stop();
		                        	mp.reset();
		                        }
		               			cuenta.setText("");
		               			
		               			reproducir(historial[vecesReproducidas],vecesReproducidas);
		               			vecesReproducidas++;
		               			
		               		}else{
		               			estaEnPausa = true;
		               			
		               			buttonMoneda.setVisibility(View.VISIBLE);
		               			buttonSeta.setVisibility(View.VISIBLE);
		               			buttonSalto.setVisibility(View.VISIBLE);
		               			buttonVida.setVisibility(View.VISIBLE);
		               		}
		               		
		               	}
		            	
		               	TimeCounter++;
		            }
		        });
	        }
		 }, 1000, 1000); // 1000 means start from 1 sec, and the second 1000 is do the loop each 1 sec.
	}
	
	public void reproducir(int azar,int posicion) {
		 
		 if(azar==-1){
			 azar = (int) Math.floor(Math.random()*4);
		 }
		 switch(azar){
			 case 0:
				 mp = MediaPlayer.create(this, R.raw.moneda);
				 
				 mp.start();
				 buttonSeta.setVisibility(View.INVISIBLE);
				 buttonMoneda.setVisibility(View.VISIBLE);
				 buttonVida.setVisibility(View.INVISIBLE);
				 buttonSalto.setVisibility(View.INVISIBLE);

				 break;
			 case 1:
				 mp = MediaPlayer.create(this, R.raw.powerup);

				 mp.start();				 
				 buttonSeta.setVisibility(View.VISIBLE);
				 buttonMoneda.setVisibility(View.INVISIBLE);
				 buttonVida.setVisibility(View.INVISIBLE);
				 buttonSalto.setVisibility(View.INVISIBLE);

				 break;
			 case 2:
				 mp = MediaPlayer.create(this, R.raw.salto);

				 mp.start();
				 buttonSeta.setVisibility(View.INVISIBLE);
				 buttonMoneda.setVisibility(View.INVISIBLE);
				 buttonVida.setVisibility(View.INVISIBLE);
				 buttonSalto.setVisibility(View.VISIBLE);
				 break;
			 case 3:
				 mp = MediaPlayer.create(this, R.raw.vida);

				 mp.start();				 
				 buttonSeta.setVisibility(View.INVISIBLE);
				 buttonMoneda.setVisibility(View.INVISIBLE);
				 buttonVida.setVisibility(View.VISIBLE);
				 buttonSalto.setVisibility(View.INVISIBLE);

				 break;
		 }
		 
		 historial[posicion] = azar;
	 }
	
	public void comprobar(int n){
		
		if(n==historial[contador]){
			
			contador++;
			
			if(contador!=9){
				if(historial[contador] == -1){
					
					contador = 0;
					nivel++;
					vecesReproducidas = 0;
					
					TextView cuenta = (TextView)findViewById(R.id.tvTimer);
					cuenta.setText(String.valueOf("¡Correcto!"));
					Toast.makeText(MemoryCoJuego.this,"¡Correcto!", 
		        	Toast.LENGTH_SHORT).show();
					try{
						Thread.sleep(1500);
					}catch(Exception e){e.printStackTrace();}
					
					buttonSeta.setVisibility(View.INVISIBLE);
					buttonMoneda.setVisibility(View.INVISIBLE);
					buttonVida.setVisibility(View.INVISIBLE);
					buttonSalto.setVisibility(View.INVISIBLE);
					 
					TimeCounter = 0;
					estaEnPausa = false;
					
				}else{
					
				}
			}else{
				estaEnPausa = true;
				t.cancel();
				TextView cuenta = (TextView)findViewById(R.id.tvTimer);
				cuenta.setText(String.valueOf("¡GANASTE!"));
				Toast.makeText(MemoryCoJuego.this,"¡Has ganado!", 
			    Toast.LENGTH_SHORT).show();
				 buttonSeta.setVisibility(View.INVISIBLE);
				 buttonMoneda.setVisibility(View.INVISIBLE);
				 buttonVida.setVisibility(View.INVISIBLE);
				 buttonSalto.setVisibility(View.INVISIBLE);
					buttonRestart.setVisibility(View.VISIBLE);
			}
		}else{
			estaEnPausa = true;
			t.cancel();
			contador = 0;
			TextView cuenta = (TextView)findViewById(R.id.tvTimer);
			cuenta.setText(String.valueOf("¡FALLASTE!"));
			Toast.makeText(MemoryCoJuego.this,"¡Has perdido! Tu puntuación: " + (nivel-1), 
		    Toast.LENGTH_SHORT).show();
			 buttonSeta.setVisibility(View.INVISIBLE);
			 buttonMoneda.setVisibility(View.INVISIBLE);
			 buttonVida.setVisibility(View.INVISIBLE);
			 buttonSalto.setVisibility(View.INVISIBLE);
				buttonRestart.setVisibility(View.VISIBLE);
		}
	}
	////////////////////////////Botones del usuario////////////////////
	
	public void presionMoneda(View v) {
		mp = MediaPlayer.create(this, R.raw.moneda);
		mp.start();
		comprobar(0);
	}
	public void presionCrecer(View v) {
		mp = MediaPlayer.create(this, R.raw.powerup);
		mp.start();
		comprobar(1);
	}
	public void presionSalto(View v) {
		mp = MediaPlayer.create(this, R.raw.salto);
		mp.start();
		comprobar(2);
	}
	public void presionVida(View v) {
		mp = MediaPlayer.create(this, R.raw.vida);
		mp.start();
		comprobar(3);
	}
	public void reintentar(View v) {
		this.finish();
	}
	
	
}
