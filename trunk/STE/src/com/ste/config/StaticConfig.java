package com.ste.config;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticConfig {

	static public ArrayList<Config> permisos =
			new ArrayList<Config>(Arrays.asList(					
					new Config("2", "Consulta"),
					new Config("1", "Administrador")));
	
	static public ArrayList<Config> departamentos =
			new ArrayList<Config>(Arrays.asList(
					new Config("Promoción Local", "Promoción Local"),
					new Config("Global Customer Service", "Global Customer Service"),
					new Config("Local Customer Service", "Local Customer Service")
					));	
}
