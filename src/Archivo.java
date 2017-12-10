import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Archivo
{
	private File archivo;
	private BufferedReader br;
  
	public Archivo(String direccion)
	{
		archivo = new File(direccion);
	}
	
	public boolean abrirArchivo()
	{
		try
		{
			br = new BufferedReader( new FileReader(archivo.getPath()) );
			return true;
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
	}
	
	public String leerLinea()
	{
		String linea = null;  
		if(archivo.canRead())
		{
			try
			{
				linea = br.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return linea;
	}
  
  public void cerrarArchivo()
  {
	  try
	  {
		  br.close();
	  }
	  catch (IOException e)
	  {
		e.printStackTrace();
	  }
  }
}