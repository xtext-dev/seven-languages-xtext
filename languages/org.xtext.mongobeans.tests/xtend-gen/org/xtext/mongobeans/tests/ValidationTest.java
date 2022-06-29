/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.mongobeans.tests;

import com.google.common.collect.Iterators;
import com.google.inject.Inject;
import java.util.Iterator;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtext.mongobeans.mongoBeans.MongoBeansPackage;
import org.xtext.mongobeans.mongoBeans.MongoFile;
import org.xtext.mongobeans.mongoBeans.MongoProperty;
import org.xtext.mongobeans.validation.MongoBeansValidator;

@RunWith(XtextRunner.class)
@InjectWith(MongoBeansInjectorProvider.class)
@SuppressWarnings("all")
public class ValidationTest {
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Inject
  @Extension
  private ParseHelper<MongoFile> _parseHelper;

  @Test
  public void testValidType() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package pkg {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("Foo {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("java.util.Date date");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("java.util.regex.Pattern pattern");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      this._validationTestHelper.assertNoErrors(this._parseHelper.parse(_builder));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testInvalidPropertyType() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Foo {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("java.io.File file");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("char character");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final Iterator<MongoProperty> properties = Iterators.<MongoProperty>filter(this._parseHelper.parse(_builder).eAllContents(), MongoProperty.class);
      this._validationTestHelper.assertError(IteratorExtensions.<MongoProperty>head(properties), MongoBeansPackage.Literals.MONGO_PROPERTY, MongoBeansValidator.ILLEGAL_TYPE);
      this._validationTestHelper.assertError(IteratorExtensions.<MongoProperty>last(properties), MongoBeansPackage.Literals.MONGO_PROPERTY, MongoBeansValidator.ILLEGAL_TYPE);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testInvalidPropertyName() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Foo {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String dbObject");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final MongoProperty property = IteratorExtensions.<MongoProperty>head(Iterators.<MongoProperty>filter(this._parseHelper.parse(_builder).eAllContents(), MongoProperty.class));
      this._validationTestHelper.assertError(property, MongoBeansPackage.Literals.MONGO_PROPERTY, MongoBeansValidator.ILLEGAL_PROPERTY_NAME);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
