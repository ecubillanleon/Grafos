import java.util.*;

public class Principal
{
	private static Scanner cursor;
	public static void main(String[] args)
	{
		cursor = new Scanner(System.in);
		String leerLinea = null;
		Consola consola = new Consola();
		boolean salir = false;
		do
		{
			System.out.print("Grafos:\\>");
			try
			{
				if(!salir)
				{
					leerLinea = cursor.nextLine();
					if(leerLinea.compareToIgnoreCase("") != 0)
					{
						consola.analizador(leerLinea);
					}
					System.out.println("");
				}
			}
			catch (NoSuchElementException e)
			{
				salir = true;
				return;
			}
		}
		while(leerLinea.compareToIgnoreCase("exit") != 0);
	}
}