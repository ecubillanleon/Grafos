import java.io.Serializable;
import java.util.LinkedList;

class MAdyTII implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int aristas;
	private int bucles;
	private int dimension;
	private int[] vector;
	private double[] peso;
	
	public MAdyTII(int nodos)
	{
		dimension = nodos;
		aristas = 0;
		bucles = 0;
		vector = new int[( nodos * (nodos - 1) / 2)];
		peso = new double[( nodos * (nodos - 1) / 2)];
		for (int i = 0; i < peso.length; i++)
		{
			peso[i] = Double.POSITIVE_INFINITY;
		}
	}
	
	public int getDimension()
	{
		return dimension;
	}
	public int getAristas()
	{
		return aristas;
	}
	public int getBucles()
	{
		return bucles;
	}
	public double[] getPeso()
	{
		return peso;
	}

	public static int mapear(int i, int j)
	{
		if(i == j)
		{
			return -1;
		}
		if(i > j)
		{
			return (j + i * (i - 1) / 2);
		}
		return ( i + j * (j - 1) / 2);
	}
	public void encenderBit(int i, int j, double peso)
	{
		int k = mapear(i, j);
		if(i == j)
		{
			bucles++;
		}
		else if(k > -1)
		{
			if(vector[k] == 0)
			{
				aristas++;
			}
			vector[k] = 1;
			this.peso[k] = peso;
		}		
	}
	public int obtenerPosicion(int i, int j)
	{
		int k = mapear(i,j);
		if(k == -1)
		{
			return 0;
		}
		else
		{
			return vector[k];
		}
	}
	public void mostrarMAdyTII(LinkedList<String> array)
	{
		int k = 0;
		while(array.size() != k)
		{
			System.out.printf("\t%s", array.get(k));
			k++;
		}
		System.out.println();
		int i = 0;
		while(array.size() != i)
		{
			System.out.printf("%s", array.get(i));
			int j = 0;
			while(array.size() != j)
			{
				System.out.printf("\t%d",(i==j)?0:vector[mapear(i,j)]);
				j++;
			}
			i++;
			System.out.println();
		}
	}
}