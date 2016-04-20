/**
 * Klasa czynnosci. Reprezentuje strzalki w grafie. Laczy ze soba dwa wydarzenia
 * poprzednie z nastepnym
 * @author kastel
 */
public class Czynnosc
{

	String nazwa_czynnosci;	//Nazwa zdarzenia
	int Czas;				//Czas czynnosci

	Zdarzenie poprzednie;	//Poprzednie zdarzenie
	Zdarzenie nastepne;		//Nastepujace zdarzenie po czynnosci

	String nazw_poprz;
	String nazw_next;


	/**
	 * Konstruktor czynnosci przymujacy nazwy zdarzen
	 *
	 * @param cz czas trwania
	 * @param _last	poprzednie zdarzenie
	 * @param _next nastepujace zdarzenie
	 */
	Czynnosc(String _nazwa, int cz, String _prev, String _next)
	{
		nazwa_czynnosci = _nazwa;
		Czas = cz;
		nazw_poprz = _prev;
		nazw_next = _next;
	}
}
