package net.noslexa.spock.extensions.reporttime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spockframework.runtime.extension.IMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import org.spockframework.runtime.model.MethodKind;

public class ReportTimeInterceptor implements IMethodInterceptor {
	private static final Logger log = LoggerFactory.getLogger(ReportTimeInterceptor.class);

	private final ReportTime reportTime;

	public ReportTimeInterceptor(ReportTime reportTime) {
		this.reportTime = reportTime;
	}

	public void intercept(final IMethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		invocation.proceed();
		log.info("() time: () seconds", getLabel(invocation), (System.currentTimeMillis() - start)/1000D);
	}

	private String getLabel(final IMethodInvocation invocation) {
		if (invocation.getIteration() != null) {
			if (invocation.getMethod().getKind() == MethodKind.FEATURE) {
				return String.format("(%s)", invocation.getIteration().getName());
			} else {
				return String.format("(%s) [%s]", invocation.getIteration().getName(), invocation.getMethod().getName());
			}
		} else {
			return String.format("[%s]", invocation.getMethod().getName());
		}
	}
}
