
public class Czynnosc
{
	int Czas;	//Czas czynnosci

	Zdarzenie poprzednie;	//Poprzednie zdarzenie
	Zdarzenie nastepne;		//Nastepujace zdarzenie po czynnosci

	/**
	 * Konstruktor czynnosci
	 * @param cz czas trwania
	 * @param last	popczenie zdarzenie
	 * @param next nastepujace zdarzenie
	 */
	Czynnosc(int cz, Zdarzenie last, Zdarzenie next)
	{
		Czas = cz;
		poprzednie = last;
		nastepne = next;
	}

	/**
	 * Konstruktor dla picu
	 */
	Czynnosc()
	{
		
	}
}
