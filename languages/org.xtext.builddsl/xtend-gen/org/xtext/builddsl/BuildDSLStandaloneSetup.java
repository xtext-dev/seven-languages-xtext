/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.builddsl;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class BuildDSLStandaloneSetup extends BuildDSLStandaloneSetupGenerated {
  public static void doSetup() {
    new BuildDSLStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
