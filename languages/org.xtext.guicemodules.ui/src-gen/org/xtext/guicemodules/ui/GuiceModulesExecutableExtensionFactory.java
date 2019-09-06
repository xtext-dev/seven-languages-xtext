/*
 * generated by Xtext 2.19.0
 */
package org.xtext.guicemodules.ui;

import com.google.inject.Injector;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.xtext.guicemodules.ui.internal.GuicemodulesActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class GuiceModulesExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return Platform.getBundle(GuicemodulesActivator.PLUGIN_ID);
	}
	
	@Override
	protected Injector getInjector() {
		GuicemodulesActivator activator = GuicemodulesActivator.getInstance();
		return activator != null ? activator.getInjector(GuicemodulesActivator.ORG_XTEXT_GUICEMODULES_GUICEMODULES) : null;
	}

}
