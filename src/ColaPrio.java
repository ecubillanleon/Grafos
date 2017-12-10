import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


class ColaPrio extends AbstractList<NodoAdy> {

    private ArrayList<NodoAdy> internalList = new ArrayList<NodoAdy>();

    // Note that add(E e) in AbstractList is calling this one
    @Override 
    public void add(int position, NodoAdy e)
    {
        internalList.add(e);
    }
    @Override
    public NodoAdy get(int i) {
        return internalList.get(i);
    }
	public void sort(double[] distancia)
	{
		Collections.sort(internalList, new Comparator<NodoAdy>(){
		public int compare(NodoAdy n1, NodoAdy n2)
		{
			return (int) (distancia[((NodoAdy) n1).getIndice()] - distancia[n2.getIndice()]);
		}
		});
	}
	public NodoAdy remove()
	{
		return internalList.remove(0);
	}
    @Override
    public int size() {
        return internalList.size();
    }

}