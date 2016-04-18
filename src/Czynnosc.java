
public class Czynnosc
{
	int Czas;

	Zdarzenie poprzednie;
	Zdarzenie nastepne;

	Czynnosc(int cz, Zdarzenie last, Zdarzenie next)
	{
		Czas = cz;
		poprzednie = last;
		nastepne = next;
	}

	Czynnosc()
	{
		
	}
}
