package es.ucm.fdi.ici.c2223.practica5.grupo08.CBRengine;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import pacman.game.Constants.MOVE;

public class MoveSim implements LocalSimilarityFunction {
	
	private MOVE getOpposite(MOVE m) {
		MOVE opposite = null;
		switch(m) {
			case UP:
				opposite = MOVE.DOWN;
				break;
		case DOWN:
			opposite = MOVE.UP;
			break;
		case LEFT:
			opposite = MOVE.RIGHT;
			break;
		case NEUTRAL:
			opposite = MOVE.NEUTRAL;
			break;
		case RIGHT:
			opposite = MOVE.LEFT;
			break;
		default:
			break;
		}
		return opposite;
	}

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Number))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Number))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());
		
		MOVE i1 = (MOVE) o1;
		MOVE i2 = (MOVE) o2;
		if(i1==i2)return 1;
		if(getOpposite(i2) == i1)return 0;
		else return 0.25;
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof MOVE;
		else if(o2==null)
			return o1 instanceof MOVE;
		else
			return (o1 instanceof MOVE)&&(o2 instanceof MOVE);
	}
}
