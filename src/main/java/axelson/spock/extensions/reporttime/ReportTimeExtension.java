package axelson.spock.extensions.reporttime;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.MethodInfo;
import org.spockframework.runtime.model.SpecInfo;

public class ReportTimeExtension extends AbstractAnnotationDrivenExtension<ReportTime> {
	@Override
	public void visitSpecAnnotation(ReportTime reportTime, SpecInfo spec) {
		for (FeatureInfo feature : spec.getFeatures()) {
			if (!feature.getFeatureMethod().getReflection().isAnnotationPresent(ReportTime.class)) {
				visitFeatureAnnotation(reportTime, feature);
			}
		}
		for (MethodInfo fixtureMethod: spec.getFixtureMethods()) {
			if (fixtureMethod.getReflection() != null && !fixtureMethod.getReflection().isAnnotationPresent(ReportTime.class)) {
				visitFixtureAnnotation(reportTime, fixtureMethod);
			}
		}
	}

	@Override
	public void visitFeatureAnnotation(ReportTime reportTime, FeatureInfo feature) {
		feature.getFeatureMethod().addInterceptor(new ReportTimeInterceptor(reportTime));
	}

	@Override
	public void visitFixtureAnnotation(ReportTime reportTime, MethodInfo fixtureMethod) {
		fixtureMethod.addInterceptor(new ReportTimeInterceptor(reportTime));
	}
}
