/*
 * generated by Xtext 2.19.0
 */
package org.xtext.tortoiseshell.ui;

import com.google.inject.Injector;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.xtext.tortoiseshell.ui.internal.TortoiseshellActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class TortoiseShellExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return Platform.getBundle(TortoiseshellActivator.PLUGIN_ID);
	}
	
	@Override
	protected Injector getInjector() {
		TortoiseshellActivator activator = TortoiseshellActivator.getInstance();
		return activator != null ? activator.getInjector(TortoiseshellActivator.ORG_XTEXT_TORTOISESHELL_TORTOISESHELL) : null;
	}

}
