/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kastel
 */
public class ComboItem
{

	public String key;
	public Zdarzenie value;
	public Czynnosc val;

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

	public ComboItem()
	{
	}

	@Override
	public String toString()
	{
		return key;
	}

	public String getNazwe()
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
