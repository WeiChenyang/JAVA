package template.abstracts;

/**
 * @author Administrator
 *
 */
public abstract class BenchMark {

	//在子类中实现   
	public abstract void benchMark();
	
	//重复至新年给benchMark()次数
	public final long repeat(int count){
		if (count <= 0) {
			return 0;
		} else {
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				//benchMark()方法的实现延迟到子类中
				benchMark();
			}
			long stopTime = System.currentTimeMillis();
			System.out.println("startTime : " + startTime + ",stopTime : " + stopTime);
			return stopTime - startTime;
		}
	}
}