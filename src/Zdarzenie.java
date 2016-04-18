/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Klasa pojedynczego zdarzenia
 * @author kastel
 */
class Zdarzenie
{
	String Nazwa;

	public int start_time; //Czas poczatkowy
	public int end_time;	//Czas koncowy
	public int luz;			//koncowy-poczatkowy = luz

	/**
	 * Konstruktor przyjmujacy nazwe zdarzenia
	 * @param naz 
	 */
	Zdarzenie(String naz)
	{
		this.Nazwa = naz;
	}

}
