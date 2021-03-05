/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.template;

/**
 * Initialization support for running Xtext languages
 * without equinox extension registry
 */
@SuppressWarnings("all")
public class TemplateStandaloneSetup extends TemplateStandaloneSetupGenerated {
  public static void doSetup() {
    new TemplateStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
