import java.util.ArrayList;

class NodoAdy
{
	private String etiqueta;
	private int indice;
	private ArrayList <Nodo> nodoAdy;

	public NodoAdy(String etiqueta, int indice)
	{
		this.etiqueta = etiqueta;
		this.indice = indice;
		nodoAdy = new ArrayList <Nodo>();
	}
	
	public String getEtiqueta()
	{
		return etiqueta;
	}
	public int getIndice()
	{
		return indice;
	}
	public ArrayList<Nodo> getNodoAdy()
	{
		return nodoAdy;
	}

	public void agregarNodoAdy(Nodo nodoAdy)
	{
		this.nodoAdy.add((nodoAdy));
	}

}