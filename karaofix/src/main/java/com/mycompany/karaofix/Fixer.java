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
    	
    	Path documentos = Paths.get("C:\\Users\\TIC\\git\\KaraoFix\\karaofix\\src\\main\\resources\\temazos");
    	Path copia = Paths.get("C:\\Users\\TIC\\git\\KaraoFix\\karaofix\\src\\main\\resources\\grandesexitos");
    	
    	List<Path> carpetas = new ArrayList<Path>();
    	
		try {
			carpetas = Files.list(documentos).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	for(Path carpeta:carpetas) {
    		String path = carpeta.toString();
    		path = path.replace("temazos", "grandesexitos");
    		try {
				Files.createDirectories(Paths.get(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		try {
				for(Path archivo:(Files.walk(carpeta)).collect(Collectors.toList())) {
					Files.copy(archivo, out)
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
}
