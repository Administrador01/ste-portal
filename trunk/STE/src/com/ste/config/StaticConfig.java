package com.ste.config;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticConfig {

	static public ArrayList<Config> permisos =
			new ArrayList<Config>(Arrays.asList(
					new Config("3", "Consulta"),
					new Config("2", "Edición"),
					new Config("1", "Propietario")));
	
	static public ArrayList<Config> departamentos =
			new ArrayList<Config>(Arrays.asList(
					new Config("Promoción Local", "Promoción Local"),
					new Config("Global Customer Service", "Global Customer Service"),
					new Config("Local Customer Service", "Local Customer Service")
					));	
}
