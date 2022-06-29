/**
 * new * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.scripting.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.xbase.ui.labeling.XbaseLabelProvider;

/**
 * Use this class to register components to be used within the IDE.
 */
@FinalFieldsConstructor
@SuppressWarnings("all")
public class ScriptingUiModule extends AbstractScriptingUiModule {
  @Override
  public Class<? extends ILabelProvider> bindILabelProvider() {
    return XbaseLabelProvider.class;
  }

  public ScriptingUiModule(final AbstractUIPlugin plugin) {
    super(plugin);
  }
}
