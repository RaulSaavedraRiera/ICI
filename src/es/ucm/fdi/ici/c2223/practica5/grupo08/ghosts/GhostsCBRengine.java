package es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts;

import java.io.File;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.ici.c2223.practica5.grupo08.CBRengine.Average;
import es.ucm.fdi.ici.c2223.practica5.grupo08.CBRengine.CachedLinearCaseBase;
import es.ucm.fdi.ici.c2223.practica5.grupo08.CBRengine.CustomPlainTextConnector;
import pacman.game.Constants.MOVE;

public class GhostsCBRengine implements StandardCBRApplication {

	private String opponent;
	private MOVE action;
	private GhostsStorageManager edibleStorageManager;
	private GhostsStorageManager notEdibleStorageManager;

	CustomPlainTextConnector connectorGeneralEdible;
	CustomPlainTextConnector connectorGeneralNotEdible;
	CustomPlainTextConnector connectorTeamEdible;
	CustomPlainTextConnector connectorTeamNotEdible;

	CBRCaseBase generalEdibleCaseBase;
	CBRCaseBase generalNotEdibleCaseBase;
	NNConfig simConfig;

	final static String TEAM = "grupo08"; // Cuidado!! poner el grupo aquí

	final static String CONNECTOR_FILE_PATH_GENERAL_EDIBLE = "es/ucm/fdi/ici/c2223/practica5/" + TEAM
			+ "/ghosts/GeneralEdibleplaintextconfig.xml";
	final static String CONNECTOR_FILE_PATH_GENERAL_NOTEDIBLE = "es/ucm/fdi/ici/c2223/practica5/" + TEAM
			+ "/ghosts/GeneralNotEdibleplaintextconfig.xml";
	final static String EDIBLE_CASE_BASE_PATH = "cbrdata" + File.separator + TEAM + File.separator + "ghosts"
			+ File.separator;
	final static String NOTEDIBLE_CASE_BASE_PATH = "cbrdata" + File.separator + TEAM + File.separator + "ghosts"
			+ File.separator;

	public GhostsCBRengine(GhostsStorageManager edibleStorageManager, GhostsStorageManager notEdibleStorageManager) {
		this.edibleStorageManager = edibleStorageManager;
		this.notEdibleStorageManager = notEdibleStorageManager;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	@Override
	public void configure() throws ExecutionException {

		// conectores para cada base de casos
		connectorGeneralEdible = new CustomPlainTextConnector();
		generalEdibleCaseBase = new CachedLinearCaseBase();

		connectorGeneralNotEdible = new CustomPlainTextConnector();
		generalNotEdibleCaseBase = new CachedLinearCaseBase();

//		connectorTeamEdible = new CustomPlainTextConnector();
//		caseBase = new CachedLinearCaseBase();
//		
//		connectorTeamNotEdible = new CustomPlainTextConnector();
//		caseBase = new CachedLinearCaseBase();

		connectorGeneralEdible.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH_GENERAL_EDIBLE));
		connectorGeneralNotEdible.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH_GENERAL_NOTEDIBLE));
//		connectorTeamEdible.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH_GENERAL_EDIBLE));
//		connectorTeamNotEdible.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH_GENERAL_NOTEDIBLE));

		// Do not use default case base path in the xml file. Instead use custom file
		// path for each opponent.
		// Note that you can create any subfolder of files to store the case base inside
		// your "cbrdata/grupoXX" folder.
		connectorGeneralEdible.setCaseBaseFile(EDIBLE_CASE_BASE_PATH, "General" + "Edible" + ".csv");
		connectorGeneralNotEdible.setCaseBaseFile(NOTEDIBLE_CASE_BASE_PATH, "General" + "NotEdible" + ".csv");

		this.edibleStorageManager.setCaseBase(generalEdibleCaseBase);
		this.notEdibleStorageManager.setCaseBase(generalNotEdibleCaseBase);

		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		simConfig.addMapping(new Attribute("score", GhostsDescription.class), new Interval(15000));
		simConfig.addMapping(new Attribute("time", GhostsDescription.class), new Interval(4000));
		simConfig.addMapping(new Attribute("actualDir", GhostsDescription.class), new MoveSim());
		simConfig.addMapping(new Attribute("lastDir", GhostsDescription.class), new MoveSim());
		simConfig.addMapping(new Attribute("distanceToPacman", GhostsDescription.class), new DistanceSim());
		simConfig.addMapping(new Attribute("nearestPill", GhostsDescription.class), new DistanceSim());
		simConfig.addMapping(new Attribute("nearestPPill", GhostsDescription.class), new DistanceSim());
		simConfig.addMapping(new Attribute("nearestGhost", GhostsDescription.class), new DistanceSim());
		simConfig.addMapping(new Attribute("pillsLeft", GhostsDescription.class), new PillsSim());
		simConfig.addMapping(new Attribute("edibleGhost", GhostsDescription.class), new Equal());
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		generalEdibleCaseBase.init(connectorGeneralEdible);
		generalNotEdibleCaseBase.init(connectorGeneralNotEdible);
		
		return generalEdibleCaseBase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		
		GhostsDescription desc = (GhostsDescription) query.getDescription();

		//hay que diferenciar por edible y not edible
		if (generalEdibleCaseBase.getCases().isEmpty()) {
			this.action = MOVE.NEUTRAL;
		} else if (desc.getEdibleGhost()) {
			// Compute retrieve
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(generalEdibleCaseBase.getCases(), query,
					simConfig);
			// Compute reuse
			this.action = reuse(eval);
		} else {
			// Compute retrieve
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(generalNotEdibleCaseBase.getCases(), query,
					simConfig);
			// Compute reuse
			this.action = reuse(eval);
		}

		// Compute revise & retain
		CBRCase newCase = createNewCase(query);
		
		if (desc.getEdibleGhost())
			this.edibleStorageManager.reviseAndRetain(newCase);
		else
			this.notEdibleStorageManager.reviseAndRetain(newCase);
	}

	private MOVE reuse(Collection<RetrievalResult> eval) {
		// This simple implementation only uses 1NN
		// Consider using kNNs with majority voting
		RetrievalResult first = SelectCases.selectTopKRR(eval, 1).iterator().next();
		CBRCase mostSimilarCase = first.get_case();
		double similarity = first.getEval();

		GhostsResult result = (GhostsResult) mostSimilarCase.getResult();
		GhostsSolution solution = (GhostsSolution) mostSimilarCase.getSolution();

		// Now compute a solution for the query

		// Here, it simply takes the action of the 1NN
		MOVE action = solution.getAction();

		// But if not enough similarity or bad case, choose another move randomly
		if ((similarity < 0.7) || (result.getScore() < 100)) {
			int index = (int) Math.floor(Math.random() * 4);
			if (MOVE.values()[index] == action)
				index = (index + 1) % 4;
			action = MOVE.values()[index];
		}
		return action;
	}

	/**
	 * Creates a new case using the query as description, storing the action into
	 * the solution and setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		GhostsDescription newDescription = (GhostsDescription) query.getDescription();
		GhostsResult newResult = new GhostsResult();
		GhostsSolution newSolution = new GhostsSolution();
		
		//sumamos todas las bases de casos
		int newId = this.generalEdibleCaseBase.getCases().size() + generalNotEdibleCaseBase.getCases().size();
		newId += edibleStorageManager.getPendingCases();
		newId += notEdibleStorageManager.getPendingCases();
		
		newDescription.setId(newId);
		newResult.setId(newId);
		newSolution.setId(newId);
		newSolution.setAction(this.action);
		newCase.setDescription(newDescription);
		newCase.setResult(newResult);
		newCase.setSolution(newSolution);
		return newCase;
	}

	public MOVE getSolution() {
		return this.action;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.edibleStorageManager.close();
		this.notEdibleStorageManager.close();
		this.generalEdibleCaseBase.close();
		this.generalNotEdibleCaseBase.close();
	}

}
