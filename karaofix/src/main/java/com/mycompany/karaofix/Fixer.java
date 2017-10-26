/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.karaofix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author japar
 */
public class Fixer {
    public static void main(String[] args) {
    	
    	//Seleccionamos la ruta donde se encuentra la carpeta temazos, esta realizado de forma que se pueda obtener
    	//desde cualquier equipo, siempre que el acceso a los archivos dentro del propio proyecto no cambie.
    	Path documentos = Paths.get(System.getProperty("user.dir"), "karaofix", "src", "main", "resources", "temazos");
    	
    	List<Path> carpetas = new ArrayList<Path>();
    	
		try {
			carpetas = Files.list(documentos).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	for(Path carpeta:carpetas) {
    		String pathCarpeta = carpeta.toString();
    		pathCarpeta = pathCarpeta.replace("temazos", "grandesexitos");
    		
    		//Comprobamos si ya existe un directorio con el mismo nombre en la ruta destino y, en caso afirmativo,
    		//eliminamos los archivos que se almacenan dentro.
    		try {
    			Path pathCarp = Paths.get(pathCarpeta);
    			if(Files.isDirectory(pathCarp)) {
    				List<Path> archivos = (Files.walk(pathCarp)).collect(Collectors.toList());
    				
    				for(int i=1;i<archivos.size();i++) {
    					Files.delete(archivos.get(i));
    				}
    			}
				Files.createDirectories(Paths.get(pathCarpeta));
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		try {
    			List<Path> archivos = (Files.walk(carpeta)).collect(Collectors.toList());
				for(int i=1; i<archivos.size();i++) {
					String pathArchivo = archivos.get(i).toString();
					pathArchivo = pathArchivo.replace("temazos", "grandesexitos");
					Path pathDestino = Paths.get(pathArchivo);
					Files.copy(archivos.get(i), pathDestino);
					
					//Solo leemos el archivo con extensión .txt, ya que es el que nos concierne para este problema.
					if(pathArchivo.contains(".txt")) {
						String line = null;
						Charset charset = Charset.forName("ISO-8859-1");
						
						try (BufferedReader reader = Files.newBufferedReader(archivos.get(i), charset);
								PrintWriter writer = new PrintWriter(Files.newBufferedWriter(pathDestino, charset))){
							
							line = reader.readLine();
							while(line != null) {

								if(line.startsWith("#BPM")) {
									String[] attrValue = line.split(":");
									attrValue[1] = attrValue[1].replaceAll(",", ".");
									String lineaCorregida = attrValue[0] + ":" + Math.round(Float.parseFloat(attrValue[1]));
									writer.println(lineaCorregida);
								}
								else if(line.startsWith("#GAP")) {
									String[] attrValue = line.split(":");
									attrValue[1] = attrValue[1].replaceAll(",", ".");
									String lineaCorregida = attrValue[0] + ":" + Math.round(Float.parseFloat(attrValue[1]));
									writer.println(lineaCorregida);
								}
								else {
									writer.println(line);
								}
								
								line = reader.readLine();
							}
							
						} catch (IOException x) {
							System.err.format("IOException: %s%n", x);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    }
    
}
