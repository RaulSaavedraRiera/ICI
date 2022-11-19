package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.io.File;
import java.util.Iterator;

import jess.Fact;
import jess.JessException;
import jess.Rete;

public class Retetest {

	public static void main(String args[]) {
		String RULES_PATH = "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator
				+ "ici" + File.separator + "c2223" + File.separator + "practica3" + File.separator + "grupo08"
				+ File.separator;
		String RULES_FILE = "RulesGhost.clp";
		String rulesFile = String.format("%s%s", RULES_PATH, RULES_FILE);
		Rete jess = new Rete();
		try {
			jess.batch(rulesFile);
			jess.reset();
			jess.run();
			Iterator<?> it = jess.listFacts();
			while(it.hasNext()){ 
			    Fact dd = (Fact)it.next();
			    System.out.println(dd.toString());
			}
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}
