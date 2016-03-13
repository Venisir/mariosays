package com.paquete.memoryco;

import android.support.v7.app.ActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Modo fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		
		//BOTON PLAY
		TextView play = (TextView)findViewById(R.id.play_button);
		play.setOnClickListener(new OnClickListener(){
			//@Override
			public void onClick(View v) {
				empiezaJuego();
			}
		});
		
		//BOTON OPCIONES
		TextView options = (TextView)findViewById(R.id.options_button);
		options.setOnClickListener(new OnClickListener() {
		   //@Override
		   public void onClick(View v) {
			   Intent i = new Intent(getApplicationContext(), Opciones.class);
			   startActivity(i);
		   }
		  });

		//BOTON SALIR
		TextView exit = (TextView)findViewById(R.id.exit_button);
		exit.setOnClickListener(new OnClickListener() {
		   //@Override
		   public void onClick(View v) {
			   finish();
		   }
		});
		  
	}
	
	private void empiezaJuego() {
		 Intent juego = new Intent(this, MemoryCoJuego.class);
		 this.startActivity(juego);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
