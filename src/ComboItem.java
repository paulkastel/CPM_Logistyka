/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Klasa potrzebna do wpisywania elementow do jcomboboxow, jlistow i innych
 * Pobiera string wyswietlany w elemencie i obiekt sam w sobie.
 * @author kastel
 */
public class ComboItem
{

	public String key;			//Wyswietlana nazwa w GUI
	public Zdarzenie value;		//Obiekt klasy zdarzenie
	public Czynnosc val;		//Obiekt klasy Czynnosc

	public ComboItem(String key, Zdarzenie value)
	{
		this.key = key;
		this.value = value;
	}

	public ComboItem(String key, Czynnosc value)
	{
		this.key = key;
		this.val = value;
	}

	@Override
	public String toString()
	{
		return key;
	}

	public Zdarzenie getZdarzenie()
	{
		return value;
	}

	public Czynnosc getCzynnosc()
	{
		return val;
	}
}
