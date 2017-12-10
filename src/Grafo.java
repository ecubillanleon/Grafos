import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Grafo
{
	private LinkedList <String> nombresVerticesNodos;
	private Stack <NodoAdy> S;
	private MAdy mady;
	private MAdyTII madytii;
	private LAdy lady;
	private int cantidadVerticesNodos, tiempo;
	private boolean dirigido, condicionado;
	private static final int blanco = 0, gris = 1, negro = 2;
	private int[] color, fin;
	private double[] distancia;
	private double excentricidadMinima, excentricidadMaxima; 
	private int[][] PIMAdy;
	private NodoAdy[] PILAdy;
	
	public Grafo()
	{
		nombresVerticesNodos =  new LinkedList<String>();
		S = new Stack <NodoAdy>();
		condicionado = true;
		lady = new LAdy();
	}
	
	public void crear(int cantidadVerticesNodos)
	{
		setCantidadVerticesNodos(cantidadVerticesNodos);
		PIMAdy = new int[cantidadVerticesNodos][cantidadVerticesNodos];
		if(dirigido)
		{	
			mady = new MAdy(cantidadVerticesNodos);
		}
		else
		{
			madytii = new MAdyTII(cantidadVerticesNodos);
		}		
	}
	public boolean isCondicionado()
	{
		return condicionado;
	}
	public boolean isDirigido()
	{
		return dirigido;
	}
	
	public void setCantidadVerticesNodos(int cantidadVerticesNodos)
	{
		this.cantidadVerticesNodos = cantidadVerticesNodos;
		PILAdy = new NodoAdy[cantidadVerticesNodos];
		color = new int[cantidadVerticesNodos];
		distancia = new double[cantidadVerticesNodos];
		fin = new int[cantidadVerticesNodos];
	}

	public void setDirigido(boolean dirigido)
	{
		this.dirigido = dirigido;
	}
	public LinkedList<String> getNombresVerticesNodos()
	{
		return nombresVerticesNodos;
	}
	public MAdy getMady()
	{
		return mady;
	}
	public LAdy getLady()
	{
		return lady;
	}
	public MAdyTII getMadytii()
	{
		return madytii;
	}
	public NodoAdy[] getPILAdy()
	{
		return PILAdy;
	}
	public int[][] getPIMAdy()
	{
		return PIMAdy;
	}
	public double[] getDistancia()
	{
		return distancia;
	}
	public double getExcentricidadMinima()
	{
		return excentricidadMinima;
	}
	public double getExcentricidadMaxima()
	{
		return excentricidadMaxima;
	}
	
	public int[] getFin()
	{
		return fin;
	}

	public boolean agregar(String cadena)
	{
		if(nombresVerticesNodos.contains(cadena))
		{
			return false;
		}
		return nombresVerticesNodos.add(cadena);
	}
	public int exgrado(int u)
	{
		int semigrado = 0;
		for(int  i = 0; i < mady.getDimension(); i++)
		{
			semigrado = semigrado + mady.obtenerPosicion(u, i);	
		}
		return semigrado;
	}
	public int[] vectorExgrado()
	{
		int[] vectorSemigrado = new int[mady.getDimension()];
		for(int i = 0; i < vectorSemigrado.length; i++)
		{
			vectorSemigrado[i] = exgrado(i);
		}
		return vectorSemigrado;
	}
	public int ingrado(int u)
	{
		int semigrado = 0;
		for(int  i = 0; i < mady.getDimension(); i++)
		{
			semigrado = semigrado + mady.obtenerPosicion(i, u);	
		}
		return semigrado;
	}
	public int[] vectorIngrado()
	{
		int[] vectorSemigrado = new int[mady.getDimension()];
		for(int i = 0; i < vectorSemigrado.length; i++)
		{
			vectorSemigrado[i] = ingrado(i);
		}
		return vectorSemigrado;
	}
	public int grado(int u)
	{
		int grado = 0;
		for(int i = 0; i < madytii.getDimension(); i++)
		{
			grado = grado + madytii.obtenerPosicion(i, u);
		}
		return grado;
	}
	public int[] vectorGrado()
	{
		int[] vectorGrado = new int[madytii.getDimension()];
		for(int i = 0; i < vectorGrado.length; i++)
		{
			vectorGrado[i] = grado(i);
		}
		return vectorGrado;
	}
	public int[] vectorDistribucion(int[] vector)
	{
		int[] distribucion = new int[vector.length];
		int contador;
		for(int i = 0; i < distribucion.length; i++)
		{
			contador = 0;
			for(int j = 0; j < distribucion.length; j++)
			{
				if(i == vector[j])
				{
					contador++;
				}
			}
			distribucion[i] = contador;
		}
		return distribucion;
	}
	public float densidad()
	{
		if(dirigido)
		{
			return ((float) mady.getArcos()/((float) mady.getDimension() * (float) (mady.getDimension())));
		} 
		return (((float) madytii.getAristas())/(((float) madytii.getDimension() * ((float) madytii.getDimension() - (float) 1)) / (float) 2));
	}
	public double excentricidad(NodoAdy x)
	{
		RAncho(x);
		double excentricidad = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < distancia.length; i++)
		{
			if(excentricidad < distancia[i] && distancia[i] != Double.POSITIVE_INFINITY)
			{
				excentricidad = distancia[i];
			}
		}
		return excentricidad;
	}
	public double[] vectorExcentricidad()
	{
		double[] vectorExcentricidad = new double[cantidadVerticesNodos];
		for(int i = 0; i < vectorExcentricidad.length; i++)
		{
			vectorExcentricidad[i] = excentricidad(lady.getNodoPPAL().get(i));
		}
		return vectorExcentricidad;
	}
	public LinkedList <String> centro()
	{
		LinkedList <String> centro = new LinkedList <String>();
		double[] temporal = vectorExcentricidad();
		excentricidadMinima = temporal[0];
		for (int i = 1; i < temporal.length; i++)
		{
			if(excentricidadMinima > temporal[i])
			{
				excentricidadMinima = temporal[i];
			}
		}
		for (int i = 0; i < temporal.length; i++)
		{
			if(excentricidadMinima == excentricidad(lady.getNodoPPAL().get(i)))
			{
				centro.add(lady.getNodoPPAL().get(i).getEtiqueta());
			}
		}
		return centro;
	}
	public LinkedList <String> periferia()
	{
		LinkedList <String> periferia = new LinkedList <String>();
		double[] temporal = vectorExcentricidad();
		excentricidadMaxima = temporal[0];
		for (int i = 1; i < temporal.length; i++)
		{
			if(excentricidadMaxima < temporal[i])
			{
				excentricidadMaxima = temporal[i];
			}
		}
		for (int i = 0; i < vectorExcentricidad().length; i++)
		{
			if(excentricidadMaxima == excentricidad(lady.getNodoPPAL().get(i)))
			{
				periferia.add(lady.getNodoPPAL().get(i).getEtiqueta());
			}
		}
		return periferia;
	}
	public double excentricidadPeso(int u)
	{
		double excentricidad = Double.NEGATIVE_INFINITY;
		if(dirigido)
		{
			double[][] delta = FloydMAdy();
			for(int i = 0; i < cantidadVerticesNodos; i++)
			{
				if(excentricidad < delta[u][i] && delta[u][i] != Double.POSITIVE_INFINITY)
				{
					excentricidad = delta[u][i];
				}
			}
			return excentricidad;	
		}
		else
		{
			double[] delta = FloydMAdyTII();
			for(int i = 0; i < cantidadVerticesNodos; i++)
			{
				if(u != i)
				{
					if(excentricidad < delta[MAdyTII.mapear(u, i)] && delta[MAdyTII.mapear(u, i)] != Double.POSITIVE_INFINITY)
					{
						excentricidad = delta[MAdyTII.mapear(u, i)];
					}
				}
			}
			return excentricidad;	
		}
	}
	public double[] vectorExcentricidadPeso()
	{
		double[] vectorExcentricidadPeso = new double[cantidadVerticesNodos];
		for(int i = 0; i < cantidadVerticesNodos; i++)
		{
			vectorExcentricidadPeso[i] = excentricidadPeso(i);
		}
		return vectorExcentricidadPeso;
	}
	public LinkedList <String> centroPeso()
	{
		LinkedList <String> centroPeso = new LinkedList <String>();
		double[] temporal = vectorExcentricidadPeso();
		excentricidadMinima = temporal[0];
		for (int i = 1; i < temporal.length; i++)
		{
			if(excentricidadMinima > temporal[i])
			{
				excentricidadMinima = temporal[i];
			}
		}
		for (int i = 0; i < temporal.length; i++)
		{
			if(excentricidadMinima == excentricidadPeso(i))
			{
				centroPeso.add(lady.getNodoPPAL().get(i).getEtiqueta());
			}
		}
		return centroPeso;
	}
	public LinkedList <String> periferiaPeso()
	{
		LinkedList <String> periferiaPeso = new LinkedList <String>();
		double[] temporal = vectorExcentricidadPeso();
		excentricidadMaxima = temporal[0];
		for (int i = 1; i < temporal.length; i++)
		{
			if(excentricidadMaxima < temporal[i])
			{
				excentricidadMaxima = temporal[i];
			}
		}
		for (int i = 0; i < temporal.length; i++)
		{
			if(excentricidadMaxima == excentricidadPeso(i))
			{
				periferiaPeso.add(lady.getNodoPPAL().get(i).getEtiqueta());
			}
		}
		return periferiaPeso;
	}
	public MAdy warshallMAdy()
	{
		MAdy warshallMAdy = SerializationUtils.clone(mady);
		for(int k = 0; k < cantidadVerticesNodos; k++)
		{
			for(int i = 0; i < cantidadVerticesNodos; i++)
			{
				for(int j = 0; j < cantidadVerticesNodos; j++)
				{
					if(warshallMAdy.obtenerPosicion(i, k) == 1 && warshallMAdy.obtenerPosicion(k, j) == 1)
					{
						warshallMAdy.encenderBit(i, j, (double) 1, true);
					}
				}
			}
		}
		return warshallMAdy;
	}
	public MAdyTII warshallMAdyTII()
	{
		MAdyTII warshallMAdyTII = SerializationUtils.clone(madytii);
		for(int k = 0; k < cantidadVerticesNodos; k++)
		{
			for(int i = 0; i < cantidadVerticesNodos; i++)
			{
				for(int j = 0; j < cantidadVerticesNodos; j++)
				{
					if(warshallMAdyTII.obtenerPosicion(i, k) == 1 && warshallMAdyTII.obtenerPosicion(k, j) == 1)
					{
						
						warshallMAdyTII.encenderBit(i, j, (double) 1);
					}
				}
			}
		}
		return warshallMAdyTII;
	}
	public void Rprof()
	{
		tiempo = 1;
		for(int i = 0; i < cantidadVerticesNodos; i++)
		{
			color[i] = blanco;
			PILAdy[i] = null;
		}
		for(int i = 0;  i < cantidadVerticesNodos; i++)
		{
			if(color[i] == blanco)
			{
				RProf_visit(lady.getNodoPPAL().get(i));
			}
		}
	}
	public void Rprof(NodoAdy nodo)
	{
		tiempo = 1;
		for(int i = 0; i < cantidadVerticesNodos; i++)
		{
			color[i] = blanco;
			PILAdy[i] = null;
		}
		RProf_visit(nodo);
	}
	private void RProf_visit(NodoAdy u)
	{
		color[u.getIndice()] = gris;
		distancia[u.getIndice()] = tiempo++;
		for (int i = 0; i < u.getNodoAdy().size(); i++)
		{
			if(color[u.getNodoAdy().get(i).getIndice()] == blanco)
			{
				PILAdy[u.getNodoAdy().get(i).getIndice()] = u;
				RProf_visit(lady.getNodoPPAL().get(u.getNodoAdy().get(i).getIndice()));
			}
		}
		color[u.getIndice()] = negro;
		fin[u.getIndice()] = tiempo++;
		S.push(u);
	}
	public void RAncho(NodoAdy x)
	{
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			distancia[i] = Double.POSITIVE_INFINITY;
			PILAdy[i] = null;
			color[i] = blanco;
		}
		Queue <NodoAdy> Q = new LinkedList <NodoAdy>();
		Q.add(x);
		distancia[x.getIndice()] = 0;
		color[x.getIndice()] = gris;
		while(!Q.isEmpty())
		{
			NodoAdy u = Q.remove();
			for (int i = 0; i < u.getNodoAdy().size(); i++)
			{
				if(color[u.getNodoAdy().get(i).getIndice()]== blanco)
				{
					PILAdy[u.getNodoAdy().get(i).getIndice()] = u;
					distancia[u.getNodoAdy().get(i).getIndice()] = distancia[u.getIndice()] + 1;
					Q.add(lady.getNodoPPAL().get(u.getNodoAdy().get(i).getIndice()));
					color[u.getNodoAdy().get(i).getIndice()] = gris;
				}
			}
			color[u.getIndice()]= negro;
		}
	}
	private void Relax(NodoAdy u, Nodo v)
	{
		if(distancia[v.getIndice()] > distancia[u.getIndice()] + v.getPeso())
		{
			PILAdy[v.getIndice()] = u;
			distancia[v.getIndice()] = distancia[u.getIndice()] + v.getPeso();
		}
	}
	public void Bellman(NodoAdy raiz)
	{
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			distancia[i] = Double.POSITIVE_INFINITY;
			PILAdy[i] = null;
		}
		distancia[raiz.getIndice()] = 0;
		for(int i = 1; i < cantidadVerticesNodos - 1; i++)
		{
			for (int j = 0; j < cantidadVerticesNodos; j++)
			{
				for(int k = 0; k < lady.getNodoPPAL().get(j).getNodoAdy().size(); k++)
				{
					Relax(lady.getNodoPPAL().get(j), lady.getNodoPPAL().get(j).getNodoAdy().get(k));
				}
			}
		}
		for (int j = 0; j < cantidadVerticesNodos; j++)
		{
			for(int k = 0; k < lady.getNodoPPAL().get(j).getNodoAdy().size(); k++)
			{
				NodoAdy u = lady.getNodoPPAL().get(j);
				Nodo v = lady.getNodoPPAL().get(j).getNodoAdy().get(k);
				if(distancia[v.getIndice()] > distancia[u.getIndice()] + v.getPeso())
				{
					condicionado = false;
				}
			}
		}
	}
	public double[][] FloydMAdy()
	{
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			for (int j = 0; j < cantidadVerticesNodos; j++)
			{
				PIMAdy[i][j] = -1;
				if(mady.obtenerPosicion(i, j) == 1)
				{
					PIMAdy[i][j] = i;
				}
			}
		}
		double[][] delta = SerializationUtils.clone(mady.getPeso());
		for (int k = 0; k < cantidadVerticesNodos; k++)
		{
			for (int i = 0; i < cantidadVerticesNodos; i++)
			{
				for (int j = 0; j < cantidadVerticesNodos; j++)
				{
					if(delta[i][j] > delta[i][k] + delta[k][j])
					{
						delta[i][j] = delta[i][k] + delta[k][j];
						PIMAdy[i][j] = PIMAdy[k][j];
					}
					if(i == j && delta[i][j] != 0)
					{
						condicionado = false;
					}
				}
			}
		}
		return delta;
	}
	public double[] FloydMAdyTII()
	{
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			for (int j = 0; j < cantidadVerticesNodos; j++)
			{
				PIMAdy[i][j] = -1;
				if(madytii.obtenerPosicion(i, j) == 1)
				{
					PIMAdy[i][j] = i;
				}
			}
		}
		double[] delta = SerializationUtils.clone(madytii.getPeso());
		for (int k = 0; k < cantidadVerticesNodos; k++)
		{
			for (int i = 0; i < cantidadVerticesNodos; i++)
			{
				for (int j = 0; j < cantidadVerticesNodos; j++)
				{
					if(i != j && i != k && k != j)
					{												
						if(delta[MAdyTII.mapear(i, j)] > delta[MAdyTII.mapear(i, k)] + delta[MAdyTII.mapear(k, j)])
						{
							PIMAdy[i][j] = PIMAdy[k][j];
							delta[MAdyTII.mapear(i, j)] = delta[MAdyTII.mapear(i, k)] + delta[MAdyTII.mapear(k, j)];
						}
					}
				}
			}
		}
		for (int k = 0; k < cantidadVerticesNodos; k++)
		{
			for (int i = 0; i < cantidadVerticesNodos; i++)
			{
				for (int j = 0; j < cantidadVerticesNodos; j++)
				{
					if(i != j && i != k && k != j)
					{																
						if(delta[MAdyTII.mapear(i , j)] > delta[MAdyTII.mapear(i, k)] + delta[MAdyTII.mapear(k, j)])
						{
							condicionado = false;
						}
					}
				}
			}
		}
		return delta;
	}
	public void Dijkstra(NodoAdy raiz)
	{
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			distancia[i] = Double.POSITIVE_INFINITY;
			PILAdy[i] = null;
		}
		distancia[raiz.getIndice()] = 0;
		LinkedList < NodoAdy> Q = new LinkedList<NodoAdy>();
		for (NodoAdy nodo : lady.getNodoPPAL())
		{
			Q.add(nodo);
		}
		while(!Q.isEmpty())
		{
			Q.sort(compareTo);
			NodoAdy u = Q.remove();
			for (Nodo v : u.getNodoAdy())
			{
				Relax(u, v);
			}
		}
	}
	public void Prim(NodoAdy raiz)
	{
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			distancia[i] = Double.POSITIVE_INFINITY;
			PILAdy[i] = null;
		}
		distancia[raiz.getIndice()] = 0;
		LinkedList < NodoAdy> Q = new LinkedList<NodoAdy>();
		for (NodoAdy nodo : lady.getNodoPPAL())
		{
			Q.add(nodo);
		}
		while(!Q.isEmpty())
		{
			Q.sort(compareTo);
			NodoAdy u = Q.remove();
			for (Nodo v : u.getNodoAdy())
			{
				if(Q.contains(lady.getNodoPPAL().get(v.getIndice())))
				{
					if(distancia[v.getIndice()] > v.getPeso())
					{
						distancia[v.getIndice()] = v.getPeso();
						PILAdy[v.getIndice()] = u;
					}
				}
			}
		}
	}
	public Comparator<NodoAdy> compareTo = new Comparator<NodoAdy>()
	{
		public int compare(NodoAdy n1, NodoAdy n2)
		{
			return (int) (distancia[n1.getIndice()] - distancia[n2.getIndice()]);
		}
	};
	private void RelaxInverso(NodoAdy u, Nodo v)
	{
		if(distancia[v.getIndice()] < distancia[u.getIndice()] + v.getPeso())
		{
			PILAdy[v.getIndice()] = u;
			distancia[v.getIndice()] = distancia[u.getIndice()] + v.getPeso();
		}
	}
	public boolean caminoCritico()
	{
		añadirVerticesFicticios();
		//int inicio = lady.encontrar("INICIO") == -1 ?  0 : lady.encontrar("INICIO");
		int inicio = lady.obtenerPrimerVertice() == -1 ? 0 : lady.obtenerPrimerVertice();
		Rprof(lady.getNodoPPAL().get(inicio));
		
		for (int i = 0; i < cantidadVerticesNodos; i++)
		{
			distancia[i] = Double.NEGATIVE_INFINITY;
			PILAdy[i] = null;
		}
		distancia[inicio] = 0;
		while(!S.isEmpty())
		{
			NodoAdy u = S.pop();
			for (Nodo v : u.getNodoAdy())
			{
				RelaxInverso(u, v);
			}
		}
		if(PILAdy[inicio] != null)
			return false;
		return true;
	}
	private void añadirVerticesFicticios()
	{
		boolean ingrados[] = new boolean[cantidadVerticesNodos];
		boolean noExgrados[] = new boolean[cantidadVerticesNodos];
		int contIn = 0, contEx = 0, dimension = cantidadVerticesNodos;
		for (int k = 0; k < cantidadVerticesNodos; k++)
		{
			NodoAdy l =lady.getNodoPPAL().get(k); 
			if(l.getNodoAdy().size() == 0)
			{
				noExgrados[l.getIndice()] = true;
				contEx++;
			}
			
			for (int j = 0; j < cantidadVerticesNodos; j++)
			{
				NodoAdy u = lady.getNodoPPAL().get(j);
				for (int i = 0; i < u.getNodoAdy().size(); i++)
				{
					Nodo v = u.getNodoAdy().get(i);
					if(v.getIndice() == k)
					{
						ingrados[k] = true;
						contIn++;
						j = i =  cantidadVerticesNodos;
					}
				}
			}			
		}
		if(contIn < cantidadVerticesNodos-1)
		{
			for (int i = 0; i < ingrados.length; i++)
			{
				if(!ingrados[i])
				{
					if(lady.encontrar("INICIO") == -1)
					{
						dimension++;
					}
					lady.agregarNodoPPAL("INICIO", lady.getNodoPPAL().get(i).getEtiqueta(), 0);
					
				}
			}
		}
		if(contEx > 1)
		{
			for (int i = 0; i < noExgrados.length; i++)
			{
				if(noExgrados[i])
				{
					if(lady.encontrar("FIN") == -1)
					{
						dimension++;
					}
					lady.agregarNodoPPAL(lady.getNodoPPAL().get(i).getEtiqueta(), "FIN", 0);
				}
			}
		}
		setCantidadVerticesNodos(dimension);
	}
}