package proxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Administrator
 * 动态代理
 * 动态代理不需要实现接口，但是需要制定接口类型
 */
public class ProxyFactory {

	//维护一个目标对象
	private Object target;
	public ProxyFactory(Object target) {
		this.target = target;
	}
	
	//给目标对象生成代理对象
	public Object getProxyInstance(){
		ClassLoader loader = target.getClass().getClassLoader();
		Class<?>[] interfaces = target.getClass().getInterfaces();
		InvocationHandler h = new InvocationHandler(){
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				System.out.println("开始事物2");
				//执行目标对象方法
				Object returnValue = method.invoke(target, args);
				System.out.println("提交事物2");
				return returnValue;
			}
		};
		return Proxy.newProxyInstance(loader, interfaces, h);
	}
}
