package youandme.states;

import youandme.transitions.Transition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionState extends State {

	private Transition transition;
	private State previous;
	private State next;
	
	public TransitionState(GSM gsm, State previous, State next, Transition transition) {
		super(gsm);
		this.previous = previous;
		this.next = next;
		this.transition = transition;
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		transition.update(dt);
		if (transition.phase == 2) {
			gsm.set(next);
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		if (transition.phase == 0) {
			previous.render(sb);
		} else {
			next.render(sb);
		}
		
		transition.render(sb);
	}
}
