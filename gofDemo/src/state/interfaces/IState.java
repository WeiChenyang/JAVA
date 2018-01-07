package state.interfaces;

/**
 * @author Administrator
 * 状态接口
 */
public interface IState {

	//状态对应的处理
	public void handle(String sampleParameter);
}