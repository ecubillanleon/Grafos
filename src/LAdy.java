import java.util.ArrayList;

class LAdy
{
	private ArrayList <NodoAdy> nodoPPAL;
	private int indice;

	public LAdy()
	{
		nodoPPAL = new ArrayList <NodoAdy> ();
		indice = 0;
	}
	
	public ArrayList<NodoAdy> getNodoPPAL()
	{
		return nodoPPAL;
	}

	public void agregarNodoPPAL(String etiqueta1, String etiqueta2, double peso)
	{
		int aux1 = -1, aux2 = -1;
		if(nodoPPAL.isEmpty() || (aux1 = encontrar(etiqueta1)) == -1)
		{
			nodoPPAL.add(new NodoAdy(etiqueta1, indice));
			aux1 = indice++;
		}
		if((aux2 = encontrar(etiqueta2)) == -1)
		{
			nodoPPAL.add(new NodoAdy(etiqueta2, indice));
			aux2 = indice++;
		}
		if(aux1 != -1 && aux2 != -1 && aux1 != aux2)
		{
			nodoPPAL.get(aux1).agregarNodoAdy(new Nodo(aux2,peso));
		}
	}
	public int obtenerPrimerVertice()
	{
		int cont;
		for (int k = 0; k < nodoPPAL.size(); k++)
		{
			cont = 0;
			for (int j = 0; j < nodoPPAL.size(); j++)
			{
				NodoAdy u = nodoPPAL.get(j);
				for (int i = 0; i < u.getNodoAdy().size(); i++)
				{
					Nodo v = u.getNodoAdy().get(i);
					if(v.getIndice() == k)
					{
						cont++;
						j = i =  nodoPPAL.size();
					}
				}
			}
			if(cont == 0)
			{
				return nodoPPAL.get(k).getIndice();
			}
		}
		return -1;
	}
	public int obtenerUltimoVertice()
	{
		for (int k = 0; k < nodoPPAL.size(); k++)
		{
			NodoAdy l = nodoPPAL.get(k); 
			if(l.getNodoAdy().size() == 0)
			{
				return l.getIndice();
			}
		}
		return -1;
	}
	public int encontrar(String etiqueta)
	{
		for (int i = 0; i < nodoPPAL.size(); i++)
		{
			if(nodoPPAL.get(i).getEtiqueta().compareToIgnoreCase(etiqueta) == 0)
			{
				return i;
			}
		}
		return -1;
	}
	public void mostrar()
	{
		for (int i = 0; i < nodoPPAL.size(); i++)
		{
			System.out.print(nodoPPAL.get(i).getEtiqueta() + " -> [ ");
			for (int j = 0; j < nodoPPAL.get(i).getNodoAdy().size(); j++)
			{
				System.out.print(nodoPPAL.get(nodoPPAL.get(i).getNodoAdy().get(j).getIndice()).getEtiqueta() + " (" + nodoPPAL.get(i).getNodoAdy().get(j).getPeso() + ") ");
			}
			System.out.println("]");
		}
	}
}
