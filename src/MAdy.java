import java.io.Serializable;
import java.util.LinkedList;

class MAdy implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int dimension;
	private int arcos;
	private int bucles;
	private int[][] mady;
	private double[][] peso;
	
	public MAdy(int vertices)
	{
		dimension = vertices;
		arcos = 0;
		bucles = 0;
		mady = new int[dimension][dimension];
		peso = new double[dimension][dimension];
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{
				if(i != j)
				{
					peso[i][j] = Double.POSITIVE_INFINITY;
				}
			}
		}
	}
	
	public int getDimension()
	{
		return dimension;
	}

	public int getArcos()
	{
		return arcos;
	}
	public int getBucles()
	{
		return bucles;
	}
	public double[][] getPeso()
	{
		return peso;
	}
	
	public int obtenerPosicion(int fila, int columna)
	{
		return mady[fila][columna];
	}
	public void encenderBit(int fila, int columna, double peso)
	{
		encenderBit(fila, columna, peso, false);
	}
	public void encenderBit(int fila, int columna, double peso, boolean warshall)
	{
		if(fila == columna && !warshall)
		{
			bucles++;
		}
		else
		{
			mady[fila][columna] = 1;
			this.peso[fila][columna] = peso;
			arcos++;
		}
	}
	public void mostrarMAdy(LinkedList<String> array)
	{
		for (int i = 0; i < dimension; i++)
		{
			System.out.printf("\t%s", array.get(i));
		}
		System.out.println();
		for (int i = 0; i < dimension; i++)
		{
			System.out.printf("%s", array.get(i));
			for (int j = 0; j < dimension; j++)
			{
				System.out.printf("\t%d", mady[i][j]);
			}
			System.out.println();
		}
	}
}