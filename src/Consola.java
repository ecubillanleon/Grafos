import java.util.LinkedList;

class Consola
{
	private Grafo nuevo;
	private boolean continuar;
	
	public void analizador(String cadena)
	{
		String[] comando = cadena.split(" ");
		if(comando == null || comando.length < 1)
		{
			mensajeError(cadena);				
		}
		else if(cadena.compareToIgnoreCase("exit") == 0)
		{
			mostrarAdios();
		}
		else if(cadena.compareToIgnoreCase("menu") == 0)
		{
			mostrarMenu();
		}
		else if(comando[0].compareToIgnoreCase("cargar") == 0)
		{
			if(comando.length > 1)
			{
				String[] archivo = cadena.split("\"");
				String leerLinea;
				if(archivo.length > 1)
				{
					leerLinea = archivo[1];
					if(archivo.length > 2)
					{
						comando[2] = archivo[2].trim();
					}
					else if(comando.length > 2)
					{
						comando[2] = "1";
					}
				}
				else
				{
					leerLinea = comando[1];
				}
				Archivo arch = new Archivo(leerLinea);
				if(arch.abrirArchivo())
				{
					continuar = true;
					if(comando.length > 2)
					{
						if(comando[2].compareToIgnoreCase("0") == 0)
						{
							nuevo = new Grafo();
							nuevo.setDirigido(false);
						}
						else if(comando[2].compareToIgnoreCase("1") == 0)
						{
							nuevo = new Grafo();
							nuevo.setDirigido(true);
						}
						else
						{
							mensajeError(comando[2]);
							continuar = false;
						}
					}
					else
					{
						nuevo = new Grafo();
						nuevo.setDirigido(true);
					}
					if(continuar)
					{
						while((leerLinea = arch.leerLinea()) != null)
						{
							String [] vertice = leerLinea.split("\t");
							for (int i = 0; i < vertice.length; i++)
							{
								if(vertice[i].compareToIgnoreCase(" ") == 0 || vertice[i].compareToIgnoreCase("") == 0)
								{
									mostrarFormatoInvalido();
									continuar = false;
									break;
								}
							}
							if(vertice.length >= 2)
							{
								nuevo.agregar(vertice[0]);
								nuevo.agregar(vertice[1]);
							}
							else
							{
								mostrarFormatoInvalido();
								continuar = false;
								break;
							}
						}
					}
					arch.cerrarArchivo();
					if(continuar)
					{
						nuevo.crear(nuevo.getNombresVerticesNodos().size());
						arch.abrirArchivo();
						leerLinea = arch.leerLinea();
						String [] vertice = leerLinea.split("\t");
						arch.cerrarArchivo();
						arch.abrirArchivo();
						if(nuevo.isDirigido())
						{
							if(vertice.length == 2)
							{
								while((leerLinea = arch.leerLinea()) != null)
								{
									vertice = leerLinea.split("\t");
									nuevo.getMady().encenderBit(nuevo.getNombresVerticesNodos().indexOf(vertice[0]), nuevo.getNombresVerticesNodos().indexOf(vertice[1]), (double) 1);
									nuevo.getLady().agregarNodoPPAL(vertice[0], vertice[1], (double) 1);
								}
								mostrarGrafoDirigidoCargado();
							}
							else if(vertice.length == 3)
							{
								while((leerLinea = arch.leerLinea()) != null)
								{
									vertice = leerLinea.split("\t");
									nuevo.getMady().encenderBit(nuevo.getNombresVerticesNodos().indexOf(vertice[0]), nuevo.getNombresVerticesNodos().indexOf(vertice[1]), Double.parseDouble(vertice[2]));
									nuevo.getLady().agregarNodoPPAL(vertice[0], vertice[1], Double.parseDouble(vertice[2]));
								}
								mostrarGrafoDirigidoCargado();
							}
							else
							{
								mostrarFormatoInvalido();
							}
						}
						else
						{
							if(vertice.length == 2)
							{
								while ((leerLinea = arch.leerLinea()) != null)
								{
									vertice = leerLinea.split("\t");
									nuevo.getMadytii().encenderBit(nuevo.getNombresVerticesNodos().indexOf(vertice[0]), nuevo.getNombresVerticesNodos().indexOf(vertice[1]), (double) 1);
									nuevo.getLady().agregarNodoPPAL(vertice[0], vertice[1], (double) 1);
									nuevo.getLady().agregarNodoPPAL(vertice[1], vertice[0], (double) 1);									
								}
								mostrarGrafoNoDirigidoCargado();
							}
							else if(vertice.length == 3)
							{
								while ((leerLinea = arch.leerLinea()) != null)
								{
									vertice = leerLinea.split("\t");
									nuevo.getMadytii().encenderBit(nuevo.getNombresVerticesNodos().indexOf(vertice[0]), nuevo.getNombresVerticesNodos().indexOf(vertice[1]), Double.parseDouble(vertice[2]));
									nuevo.getLady().agregarNodoPPAL(vertice[0], vertice[1], Double.parseDouble(vertice[2]));
									nuevo.getLady().agregarNodoPPAL(vertice[1], vertice[0], Double.parseDouble(vertice[2]));	
								}
								mostrarGrafoNoDirigidoCargado();
							}
							else
							{
								mostrarFormatoInvalido();
							}
						}
						arch.cerrarArchivo();
					}
				}
				else
				{
					mostrarArchivoNoCargado();
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(nuevo == null)
		{
			mostrarGrafoNoCargado();
		}
		else if(comando[0].compareToIgnoreCase("exg") == 0)
		{
			if(nuevo.isDirigido())
			{
				if(comando.length == 1 )
				{
					System.out.println("Vector de exgrados: ");
					mostrarNombresVertices();
					mostrarVector(nuevo.vectorExgrado());
				}
				else
				{
					if(nuevo.getNombresVerticesNodos().indexOf(comando[1]) < nuevo.vectorExgrado().length && nuevo.getNombresVerticesNodos().indexOf(comando[1]) >= 0)
					{
						System.out.println("Exgrado de '" + comando[1] + "': " + nuevo.exgrado(nuevo.getNombresVerticesNodos().indexOf(comando[1])));
					}
					else
					{
						System.out.println("¡Vertice '" + comando[1] + "' no existe!");
					}
				}
			}
			else
			{
				mostrarGrafoDirigidoNoCargado();
			}
		}
		else if(comando[0].compareToIgnoreCase("ing") == 0)
		{
			if(nuevo.isDirigido())
			{
				if(comando.length == 1 )
				{
					System.out.println("Vector de ingrados: ");
					mostrarNombresVertices();
					mostrarVector(nuevo.vectorIngrado());
				}
				else
				{
					if(nuevo.getNombresVerticesNodos().indexOf(comando[1]) < nuevo.vectorIngrado().length && nuevo.getNombresVerticesNodos().indexOf(comando[1]) >= 0)
					{
						System.out.println("Ingrado de '" + comando[1] + "': " + nuevo.ingrado(nuevo.getNombresVerticesNodos().indexOf(comando[1])));
					}
					else
					{
						System.out.println("¡Vertice '" + comando[1] + "' no existe!");
					}
				}
			}
			else
			{
				mostrarGrafoDirigidoNoCargado();
			}
		}
		else if(comando[0].compareToIgnoreCase("gdo") == 0)
		{
			if(!nuevo.isDirigido())
			{
				if(comando.length == 1 )
				{
					System.out.println("Vector de grados: ");
					mostrarNombresVertices();
					mostrarVector(nuevo.vectorGrado());
				}
				else
				{
					if(nuevo.getNombresVerticesNodos().indexOf(comando[1]) < nuevo.vectorGrado().length && nuevo.getNombresVerticesNodos().indexOf(comando[1]) >= 0)
					{
						System.out.println("Grado de '" + comando[1] + "': " + nuevo.grado(nuevo.getNombresVerticesNodos().indexOf(comando[1])));
					}
					else
					{
						System.out.println("¡Nodo '" + comando[1] + "' no existe!");
					}
				}
			}
			else
			{
				mostrarGrafoNoDirigidoNoCargado();
			}
		}
		else if(comando[0].compareToIgnoreCase("mady") == 0)
		{
			if(nuevo.isDirigido())
			{
				nuevo.getMady().mostrarMAdy(nuevo.getNombresVerticesNodos());
			}
			else
			{
				nuevo.getMadytii().mostrarMAdyTII(nuevo.getNombresVerticesNodos());
			}
		}
		else if(comando[0].compareToIgnoreCase("lady") == 0)
		{
			nuevo.getLady().mostrar();
		}
		else if(comando[0].compareToIgnoreCase("stats") == 0)
		{
			if(nuevo.isDirigido())
			{
				System.out.println("Vertices: " + nuevo.getNombresVerticesNodos().size());
				System.out.println("Arcos: " + nuevo.getMady().getArcos());
				System.out.println("Bucles: " + nuevo.getMady().getBucles());
				analizador("exg");
				System.out.println("Vector distribucion de exgrados: ");
				for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
				{
					System.out.print(i + "\t");
				}
				System.out.println();
				mostrarVector(nuevo.vectorDistribucion(nuevo.vectorExgrado()));
				analizador("ing");
				System.out.println("Vector distribucion de ingrados: ");
				for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
				{
					System.out.print(i + "\t");
				}
				System.out.println();
				mostrarVector(nuevo.vectorDistribucion(nuevo.vectorIngrado()));
			}
			else
			{
				System.out.println("Nodos: " + nuevo.getNombresVerticesNodos().size());
				System.out.println("Aristas: " + nuevo.getMadytii().getAristas());
				System.out.println("Bucles: " + nuevo.getMadytii().getBucles());
				analizador("gdo");
				System.out.println("Vector distribucion de grados: ");
				for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
				{
					System.out.print(i + "\t");
				}
				System.out.println();
				mostrarVector(nuevo.vectorDistribucion(nuevo.vectorGrado()));
			}
			if(nuevo.densidad() <= (float) 0.5)
			{
				System.out.println("Diperso, Densidad: " + (float) nuevo.densidad());
			}
			else
			{
				System.out.println("Denso, Densidad: " + (float) nuevo.densidad());
			}
		}
		else if(comando[0].compareToIgnoreCase("warshall") == 0)
		{
			if(nuevo.isDirigido())
			{
				MAdy temporal = nuevo.warshallMAdy();
				temporal.mostrarMAdy(nuevo.getNombresVerticesNodos());
			}
			else
			{
				MAdyTII temporal = nuevo.warshallMAdyTII();
				temporal.mostrarMAdyTII(nuevo.getNombresVerticesNodos());
			}
		}
		else if(comando[0].compareToIgnoreCase("rprof") == 0)
		{
			nuevo.Rprof();
			mostrarNodos(nuevo.getPILAdy());
			mostrarVector(nuevo.getDistancia());
		}
		else if(comando[0].compareToIgnoreCase("path") == 0)
		{
			if(comando.length == 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) != -1)
				{
					nuevo.Rprof(nuevo.getLady().getNodoPPAL().get(aux1));
					mostrarNodos(nuevo.getPILAdy());
					mostrarVector(nuevo.getDistancia());
				}
				else
				{
					mostrarOrigenNoEncontrado();
				}
			}
			else if(comando.length > 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) == -1)
				{
					mostrarOrigenNoEncontrado();
				}
				int aux2 = -1;
				if((aux2 = nuevo.getLady().encontrar(comando[2])) == -1)
				{
					mostrarDestinoNoEncontrado();
				}
				if(aux1 != -1 && aux2 != -1)
				{
					if(aux1 != aux2)
					{
						nuevo.Rprof(nuevo.getLady().getNodoPPAL().get(aux1));
						String camino = mostrarCamino(nuevo.getPILAdy(), nuevo.getLady().getNodoPPAL().get(aux2));
						if((nuevo.getPILAdy()[nuevo.getLady().getNodoPPAL().get(aux2).getIndice()] == null))
						{
							System.out.println("No existe una ruta desde '" + nuevo.getLady().getNodoPPAL().get(aux1).getEtiqueta() + "' a '" + nuevo.getLady().getNodoPPAL().get(aux2).getEtiqueta() + "'");
						}
						else
						{
							System.out.println("< " + camino + " >" + " (" + nuevo.getDistancia()[aux2] + " )");
						}
					}
					else
					{
						System.out.println("< " + comando[1] +" > ( 0 )");
					}
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(comando[0].compareToIgnoreCase("dist") == 0)
		{
			if(comando.length == 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) != -1)
				{
					nuevo.RAncho(nuevo.getLady().getNodoPPAL().get(aux1));
					mostrarNodos(nuevo.getPILAdy());
					mostrarVector(nuevo.getDistancia());
				}
				else
				{
					mostrarOrigenNoEncontrado();
				}
			}
			else if(comando.length > 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) == -1)
				{
					mostrarOrigenNoEncontrado();
				}
				int aux2 = -1;
				if((aux2 = nuevo.getLady().encontrar(comando[2])) == -1)
				{
					mostrarDestinoNoEncontrado();
				}
				if(aux1 != -1 && aux2 != -1)
				{
					if(aux1 != aux2)
					{
						nuevo.RAncho(nuevo.getLady().getNodoPPAL().get(aux1));
						String camino = mostrarCamino(nuevo.getPILAdy(), nuevo.getLady().getNodoPPAL().get(aux2));
						if((nuevo.getPILAdy()[nuevo.getLady().getNodoPPAL().get(aux2).getIndice()] == null))
						{
							System.out.println("No existe una ruta desde '" + nuevo.getLady().getNodoPPAL().get(aux1).getEtiqueta() + "' a '" + nuevo.getLady().getNodoPPAL().get(aux2).getEtiqueta() + "'");
						}
						else
						{
							System.out.println("< " + camino + " >" + " (" + nuevo.getDistancia()[aux2] + " )");
						}
					}
					else
					{
						System.out.println("< " + comando[1] + " > ( 0 )");
					}
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(comando[0].compareToIgnoreCase("exc") == 0)
		{
			if(comando.length == 1 )
			{
				System.out.println("Vector de excentricidad: ");
				mostrarNombresVertices();
				nuevo.vectorExcentricidad();
				mostrarVector(nuevo.vectorExcentricidad());
			}
			else
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) == -1)
				{
					System.out.println("¡Vertice/Nodo '" + comando[1] + "' no existe!");
				}
				else
				{
					System.out.println("Excentricidad de '" + comando[1] + "': " + nuevo.excentricidad(nuevo.getLady().getNodoPPAL().get(aux1)));
				}
			}
		}
		else if(comando[0].compareToIgnoreCase("centro") == 0)
		{
			System.out.print("Centro:\t");
			mostrarCenPeri(nuevo.centro());
			System.out.println("(" + nuevo.getExcentricidadMinima() + ")");
		}
		else if(comando[0].compareToIgnoreCase("periferia") == 0)
		{
			System.out.print("Periferia:\t");
			mostrarCenPeri(nuevo.periferia());
			System.out.println("(" + nuevo.getExcentricidadMaxima() + ")");
		}
		else if(comando[0].compareToIgnoreCase("bellman") == 0)
		{
			if(comando.length == 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) != -1)
				{
					nuevo.Bellman(nuevo.getLady().getNodoPPAL().get(aux1));
					mostrarNodos(nuevo.getPILAdy());
					mostrarVector(nuevo.getDistancia());
					if(!nuevo.isCondicionado())
					{
						mostrarGrafoMalCondicionado();
					}
				}
				else
				{
					mostrarOrigenNoEncontrado();
				}
			}
			else if(comando.length == 3)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) == -1)
				{
					mostrarOrigenNoEncontrado();
				}
				int aux2 = -1;
				if((aux2 = nuevo.getLady().encontrar(comando[2])) == -1)
				{
					mostrarDestinoNoEncontrado();
				}
				if(aux1 != -1 && aux2 != -1)
				{
					if(aux1 != aux2)
					{
						nuevo.Bellman(nuevo.getLady().getNodoPPAL().get(aux1));
						if(nuevo.isCondicionado())
						{
							String camino = mostrarCamino(nuevo.getPILAdy(), nuevo.getLady().getNodoPPAL().get(aux2));
							if((nuevo.getPILAdy()[nuevo.getLady().getNodoPPAL().get(aux2).getIndice()] == null))
							{
								System.out.println("No existe una ruta desde '" + nuevo.getLady().getNodoPPAL().get(aux1).getEtiqueta() + "' a '" + nuevo.getLady().getNodoPPAL().get(aux2).getEtiqueta() + "'");
							}
							else
							{
								System.out.println("< " + camino + " >" + " (" + nuevo.getDistancia()[aux2] + ")");
							}
						}
						else
						{
							mostrarGrafoMalCondicionado();
						}
					}
					else
					{
						System.out.println("< " + comando[1] + " > ( 0 )");
					}
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(comando[0].compareToIgnoreCase("excw") == 0)
		{			
			double[] vectorExcentricidadPeso = nuevo.vectorExcentricidadPeso();
			if(nuevo.isCondicionado())
			{
				if(comando.length == 1 )
				{
					System.out.println("Vector de excentricidad: ");
					mostrarNombresVertices();
					mostrarVector(vectorExcentricidadPeso);
				}
				else
				{
					if(nuevo.getNombresVerticesNodos().indexOf(comando[1]) < nuevo.vectorExcentricidadPeso().length && nuevo.getNombresVerticesNodos().indexOf(comando[1]) >= 0)
					{
						System.out.println("Excentricidad de '" + comando[1] + "': " + nuevo.excentricidadPeso(nuevo.getNombresVerticesNodos().indexOf(comando[1])));
					}
					else
					{
						System.out.println("¡Vertice/Nodo '" + comando[1] + "' no existe!");
					}
				}
			}
			else
			{
				mostrarGrafoMalCondicionado();
			}
		}
		else if(comando[0].compareToIgnoreCase("centrow") == 0)
		{	
			LinkedList <String> centroPeso = nuevo.centroPeso();
			if(nuevo.isCondicionado())
			{
				System.out.print("Centro:\t");
				mostrarCenPeri(centroPeso);
				System.out.println("(" + nuevo.getExcentricidadMinima() + ")");
			}
			else
			{
				mostrarGrafoMalCondicionado();
			}
		}
		else if(comando[0].compareToIgnoreCase("periferiaw") == 0)
		{
			LinkedList <String> periferiaPeso = nuevo.periferiaPeso();
			if(nuevo.isCondicionado())
			{
				System.out.print("Periferia:\t");
				mostrarCenPeri(periferiaPeso);
				System.out.println("(" + nuevo.getExcentricidadMaxima() + ")");
			}
			else
			{
				mostrarGrafoMalCondicionado();
			}
		}
		else if(comando[0].compareToIgnoreCase("floyd") == 0)
		{
			if(nuevo.isDirigido())
			{
				mostrarMatriz(nuevo.FloydMAdy());
				System.out.println();
				mostrarMatriz(nuevo.getPIMAdy());
				if(!nuevo.isCondicionado())
				{
					mostrarGrafoMalCondicionado();
				}
			}
			else
			{
				mostrarMatriz(nuevo.FloydMAdyTII());
				System.out.println();
				mostrarMatriz(nuevo.getPIMAdy());
				if(!nuevo.isCondicionado())
				{
					mostrarGrafoMalCondicionado();
				}
			}
		}
		else if(comando[0].compareToIgnoreCase("pathf") == 0)
		{
			if(comando.length == 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getNombresVerticesNodos().indexOf(comando[1])) != -1)
				{
					if(nuevo.isDirigido())
					{
						double[][] delta  = nuevo.FloydMAdy();
						mostrarNombresVertices();
						mostrarPI(nuevo.getPIMAdy()[aux1]);
						mostrarVector(delta[aux1]);
						if(!nuevo.isCondicionado())
						{
							mostrarGrafoMalCondicionado();
						}
					}
					else
					{
						double[] delta = nuevo.FloydMAdyTII();
						mostrarNombresVertices();
						mostrarPI(nuevo.getPIMAdy()[aux1]);
						mostrarVector(delta, aux1);
						if(!nuevo.isCondicionado())
						{
							mostrarGrafoMalCondicionado();
						}
					}
				}
				else
				{
					mostrarOrigenNoEncontrado();
				}
			}
			else if(comando.length > 2)
			{
				if(nuevo.isDirigido())
				{
					nuevo.FloydMAdy();
				}
				else
				{
					nuevo.FloydMAdyTII();
				}
				int aux1 = -1;
				if((aux1 = nuevo.getNombresVerticesNodos().indexOf(comando[1])) == -1)
				{
					mostrarOrigenNoEncontrado();
				}
				int aux2 = -1;
				if((aux2 = nuevo.getNombresVerticesNodos().indexOf(comando[2])) == -1)
				{
					mostrarDestinoNoEncontrado();
				}
				if(aux1 != -1 && aux2 != -1)
				{
					double delta = Double.NEGATIVE_INFINITY;
					String camino;
					if(aux1 != aux2)
					{
						if(nuevo.isCondicionado())
						{
							if(nuevo.isDirigido())
							{
								delta = nuevo.FloydMAdy()[aux1][aux2];
								camino = mostrarCaminoFloydR(nuevo.getPIMAdy()[aux1], aux2);
							}
							else
							{
								delta = nuevo.FloydMAdyTII()[MAdyTII.mapear(aux1, aux2)];
								camino = mostrarCaminoFloydR(nuevo.getPIMAdy()[aux1], aux2);
							}
							if(nuevo.getPIMAdy()[aux1][aux2] == -1)
							{
								System.out.println("No existe una ruta desde '" + nuevo.getNombresVerticesNodos().get(aux1) + "' a '" + nuevo.getNombresVerticesNodos().get(aux2) + "'");
				
							}
							else
							{
								System.out.println("< " + camino + " > ( " + delta + " )");
							}
						}
						else
						{
							mostrarGrafoMalCondicionado();
						}
					}
					else
					{
						System.out.println("< " + comando[1] + " > ( 0 )");
					}
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(comando[0].compareToIgnoreCase("dijkstra") == 0)
		{
			if(comando.length == 2)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) != -1)
				{
					nuevo.Dijkstra(nuevo.getLady().getNodoPPAL().get(aux1));
					mostrarNodos(nuevo.getPILAdy());
					mostrarVector(nuevo.getDistancia());
					if(!nuevo.isCondicionado())
					{
						mostrarGrafoMalCondicionado();
					}
				}
				else
				{
					mostrarOrigenNoEncontrado();
				}
			}
			else if(comando.length == 3)
			{
				int aux1 = -1;
				if((aux1 = nuevo.getLady().encontrar(comando[1])) == -1)
				{
					mostrarOrigenNoEncontrado();
				}
				int aux2 = -1;
				if((aux2 = nuevo.getLady().encontrar(comando[2])) == -1)
				{
					mostrarDestinoNoEncontrado();
				}
				if(aux1 != -1 && aux2 != -1)
				{
					if(aux1 != aux2)
					{
						nuevo.Dijkstra(nuevo.getLady().getNodoPPAL().get(aux1));
						if(nuevo.isCondicionado())
						{
							String camino = mostrarCamino(nuevo.getPILAdy(), nuevo.getLady().getNodoPPAL().get(aux2));
							if((nuevo.getPILAdy()[nuevo.getLady().getNodoPPAL().get(aux2).getIndice()] == null))
							{
								System.out.println("No existe una ruta desde '" + nuevo.getLady().getNodoPPAL().get(aux1).getEtiqueta() + "' a '" + nuevo.getLady().getNodoPPAL().get(aux2).getEtiqueta() + "'");
							}
							else
							{
								System.out.println("< " + camino + " >" + " (" + nuevo.getDistancia()[aux2] + ")");
							}
						}
						else
						{
							mostrarGrafoMalCondicionado();
						}
					}
					else
					{
						System.out.println("< " + comando[1] + " > ( 0 )");
					}
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(comando[0].compareToIgnoreCase("prim") == 0)
		{
			if(comando.length > 1)
			{
				if(comando.length == 2)
				{
					int aux1 = -1;
					if((aux1 = nuevo.getLady().encontrar(comando[1])) != -1)
					{
						nuevo.Prim(nuevo.getLady().getNodoPPAL().get(aux1));
						mostrarNodos(nuevo.getPILAdy());
						mostrarVector(nuevo.getDistancia());
						double peso = 0;
						for (int i = 0; i < nuevo.getDistancia().length; i++)
						{
							peso = peso + nuevo.getDistancia()[i];
						}
						System.out.println("P(G(PI)): " + peso);
						if(!nuevo.isCondicionado())
						{
							mostrarGrafoMalCondicionado();
						}
					}
					else
					{
						mostrarOrigenNoEncontrado();
					}
				}
				else
				{
					mensajeError(comando[2]);
				}
			}
			else
			{
				mostrarFaltaComando();
			}
		}
		else if(comando[0].compareToIgnoreCase("cpm") == 0)
		{
			if(nuevo.isDirigido())
			{
				if(nuevo.caminoCritico())
				{
					int fin = nuevo.getLady().obtenerUltimoVertice() == -1 ? nuevo.getLady().encontrar("FIN") : nuevo.getLady().obtenerUltimoVertice();
					System.out.println("Duracion: " + nuevo.getDistancia()[fin]);
					
					System.out.println("Camino critico: < " + mostrarCamino(nuevo.getPILAdy(), nuevo.getLady().getNodoPPAL().get(fin)) + " >");
				}
				else
				{
					mostrarCicloDetectado();
					//mostrarNodos(nuevo.getPILAdy());
				}
			}
			else
			{
				mostrarGrafoDirigidoNoCargado();
			}
		}
		else
		{
			mensajeError(cadena);
		}
	}
	private void mostrarAdios()
	{
		System.out.println("¡Adiós!");
	}
	private void mostrarMenu()
	{
		System.out.println("Los comandos conocidos son:");
		System.out.println("menu" + "\t\t\t\tLista los comandos conocidos y breve descripción");
		System.out.println("exit" + "\t\t\t\tTermina la sesion de trabajo");
		System.out.println("cargar <archivo> [dir]" + "\t\tCarga desde el archivo un grafo");
		System.out.println("exg [vertice]" + "\t\t\tMuestra el exgrado del vertice del grafo dirigido");
		System.out.println("ing [vertice]" + "\t\t\tMuestra el ingrado del vertice del grafo dirigido");
		System.out.println("gdo [nodo]" + "\t\t\tMuestra el grado de un nodo del grafo no dirigido");
		System.out.println("stats" + "\t\t\t\tMuestra las propiedades del grafo cargado en memoria");
		System.out.println("mady" + "\t\t\t\tMuestra la matriz de adyacencia de un grafo");
		System.out.println("lady" + "\t\t\t\tMuestra la lista de adyacencia de un grafo");
		System.out.println("warshall" + "\t\t\tMuestra una caminata en una matriz de adyacencia de un grafo");
		System.out.println("rprof" + "\t\t\t\tMuestra el recorrido en profundidad de un grafo");
		System.out.println("path <origen> [destino]" + "\t\tMuestra la ruta mas larga desde un nodo a otro");
		System.out.println("dist <origen> [destino]" + "\t\tMuestra la ruta mas corta desde un nodo a otro");
		System.out.println("exc [nodo]" + "\t\t\tMuestra la excentricidad de un nodo");
		System.out.println("priferia" + "\t\t\tMuestra la periferia del grafo");
		System.out.println("centro" + "\t\t\t\tMuestra el centro del grafo");
		System.out.println("excw [nodo]" + "\t\t\tMuestra la excentricidad de un nodo pesado");
		System.out.println("priferiaw" + "\t\t\tMuestra la periferia del grafo pesado");
		System.out.println("centrow" + "\t\t\t\tMuestra el centro del grafo pesado");
		System.out.println("floyd" + "\t\t\t\tMuestra la matriz delta resultante de floyd");
		System.out.println("bellman <origen> [destino]" + "\tMuestra el camino mas corto desde un nodo a otro usando bellman");
		System.out.println("pathf <origen> [destino]" + "\tMuestra el camino de un nodo a otro usando floyd");
	} 
	private void mostrarArchivoNoCargado()
	{
		System.out.println("¡El archivo no pudo ser cargado!");
	}
	private void mostrarFaltaComando()
	{
		System.out.println("¡Falta un argumento en su comando!");
	}
	private void mostrarFormatoInvalido()
	{
		System.out.println("¡Formato de archivo invalido!");
	}
	private void mostrarGrafoDirigidoCargado()
	{
		System.out.println("¡El grafo dirigido ha sido cargado!");
	}
	private void mostrarGrafoNoDirigidoCargado()
	{
		System.out.println("¡El grafo no dirigido ha sido cargado!");
	}
	private void mostrarGrafoNoDirigidoNoCargado()
	{
		System.out.println("¡No se ha cargado el grafo no dirigido!");
	}
	private void mostrarGrafoDirigidoNoCargado()
	{
		System.out.println("¡No se ha cargado el grafo dirigido!");
	}
	private void mostrarGrafoNoCargado()
	{
		System.out.println("¡No se ha cargado el grafo!");
	}
	private void mostrarOrigenNoEncontrado()
	{
		System.out.println("¡Origen no se encontro!");
	}
	private void mostrarDestinoNoEncontrado()
	{
		System.out.println("¡Destino no se encontro!");
	}
	private void mostrarGrafoMalCondicionado()
	{
		System.out.println("¡Grafo Mal condicionado!");
	}
	private void mostrarCicloDetectado()
	{
		System.out.println("Se detectó un ciclo - No se pudo continuar");
	}
	private void mostrarVector(int[] vector)
	{
		for(int i = 0; i < vector.length; i++)
		{
			System.out.print(vector[i] + "\t");
		}
		System.out.println();
	}
	private void mostrarVector(double[] vector)
	{
		for(int i = 0; i < vector.length; i++)
		{
			System.out.printf("%.6s\t", String.format("" + vector[i]).toUpperCase());
		}
		System.out.println();
	}
	private void mostrarVector(double[] vector, int fila)
	{
		int indice = -1;
		for(int i = 0; i < vector.length; i++)
		{
			indice = MAdyTII.mapear(fila, i);
			System.out.printf("%.6s\t", String.format("" + ((indice == -1)? "0" : vector[indice])).toUpperCase());
		}
		System.out.println();
	}
	private void mostrarMatriz(int[][] matriz)
	{
		System.out.print("\t");
		mostrarNombresVertices();
		for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
		{
			System.out.printf("%.6s\t", nuevo.getNombresVerticesNodos().get(i));
			for (int j = 0; j < nuevo.getNombresVerticesNodos().size(); j++)
			{
				System.out.printf("%.6s\t", (matriz[i][j] == -1)? "NULL" : nuevo.getNombresVerticesNodos().get(matriz[i][j]));
			}
			System.out.println();
		}
	}
	public void mostrarMatriz(double[][] matriz)
	{
		System.out.print("\t");
		mostrarNombresVertices();
		for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
		{
			System.out.printf("%.6s\t", nuevo.getNombresVerticesNodos().get(i));
			for (int j = 0; j < nuevo.getNombresVerticesNodos().size(); j++)
			{
				System.out.printf("%.6s\t", String.format("" + matriz[i][j]).toUpperCase());
			}
			System.out.println();
		}
	}
	private void mostrarMatriz(double[] vector)
	{
		System.out.print("\t");
		mostrarNombresVertices();
		for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
		{
			System.out.printf("%.6s\t", nuevo.getNombresVerticesNodos().get(i));
			for (int j = 0; j < nuevo.getNombresVerticesNodos().size(); j++)
			{
				System.out.printf("%.6s\t", (i==j)?0: String.format("" + vector[MAdyTII.mapear(i, j)]).toUpperCase());
			}
			System.out.println();
		}	
	}
	private void mostrarCenPeri(LinkedList <String> pericen)
	{
		for (int i = 0; i < pericen.size(); i++)
		{
			System.out.print(pericen.get(i) + "\t");
		}
	}
	/*private void mostrarNombresVerticesLAdy()
	{
		for (NodoAdy u : nuevo.getLady().getNodoPPAL())
		{
			System.out.printf("%.6s\t", u.getEtiqueta());
		}
		System.out.println();
	}*/
	private void mostrarNombresVertices()
	{
		for (int i = 0; i < nuevo.getNombresVerticesNodos().size(); i++)
		{
			System.out.printf("%.6s\t", nuevo.getNombresVerticesNodos().get(i));
		}
		System.out.println();
	}
	private void mostrarPI(int[] vector)
	{
		for (int j = 0; j < vector.length; j++)
		{
			System.out.printf("%.6s\t", vector[j] == -1 ? "NULL" : nuevo.getNombresVerticesNodos().get(vector[j]));
		}
		System.out.println();
	}
	private void mostrarNodos(NodoAdy vector[])
	{
		mostrarNombresVertices();
		for(int i = 0; i < vector.length; i++)
		{
			System.out.printf("%.6s\t", vector[i] == null ? "NULL" : vector[i].getEtiqueta());
		}
		System.out.println();
	}
	public String mostrarCaminoFloydR(int[] PI, int destino)
	{
		if(PI[destino] != -1)
		{
			return mostrarCaminoFloydR(PI, PI[destino]) + ", " + nuevo.getNombresVerticesNodos().get(destino);
		}
		return nuevo.getNombresVerticesNodos().get(destino);
	}
	private String mostrarCamino(NodoAdy[] pi, NodoAdy destino)
	{
		if(pi[destino.getIndice()] != null)
		{
			return mostrarCamino(pi, pi[destino.getIndice()]) + ", " + destino.getEtiqueta();
		}
		return destino.getEtiqueta();
	}
	private void mensajeError(String cadena)
	{	
		System.out.println("\"" + cadena + "\"" + " no se reconoce como un comando o los parámetros son inválidos");
	}
}