/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.xtext.builddsl.jvmmodel

import com.google.inject.Inject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.util.Strings
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.xtext.builddsl.build.BuildFile
import org.xtext.builddsl.build.Parameter
import org.xtext.builddsl.build.Task
import org.xtext.builddsl.lib.BuildScript
import org.xtext.builddsl.lib.DependsOn
import org.xtext.builddsl.lib.Param

/**
 * Infers a Java class from a {@link BuildFile} allowing to execute it.
 */
class BuildDSLJvmModelInferrer extends AbstractModelInferrer {

	@Inject extension JvmTypesBuilder

	@Inject
	private TypesFactory typesFactory;
	
   	def dispatch void infer(BuildFile file, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		val fqn = file.javaClassName
   		val scriptName = Strings.lastToken(fqn, ".")
   		acceptor.accept(file.toClass(fqn)).initializeLater [
			superTypes += file.newTypeRef(BuildScript)
			
			// parameters become Java fields
			for (declaredParameter : file.parameters) {
				val type = declaredParameter.type 
					?: declaredParameter?.init?.inferredType
					?: file.newTypeRef(String)
				members += declaredParameter.toField(declaredParameter.name, type) [
					visibility = JvmVisibility.PUBLIC
					annotations += declaredParameter.toAnnotation(Param)
					initializer = declaredParameter.init
				]
			}
			
			// the main method		
   			val stringArray = file.newTypeRef(String).addArrayTypeDimension
			members += file.toMethod("main", file.newTypeRef(Void.TYPE)) [
				parameters += toParameter("args", stringArray)
				varArgs = true
				static = true
				body = '''
					«scriptName» script = new «scriptName»();
					if (script.showHelp(args)) {
						System.exit(HELP);
					}
					System.exit(script.doBuild(args));
				'''
			]
			
			// a method for the actual task body
   			members += file.tasks.map[ task | task.toMethod(task.methodName, task.newTypeRef(Void.TYPE)) [
   				visibility = JvmVisibility.PROTECTED
   				val annotationRef = task.toAnnotation(DependsOn)
   				if(task.depends.size > 0) {
					annotationRef.getExplicitValues().add(typesFactory.createJvmStringAnnotationValue() => [
						values += task.depends.map[name]
					])
   				}
   				annotations += annotationRef 
   				body = task.action
   			]]
   			
		]
   	}
   	
   	def private getTasks(BuildFile it) {
   		declarations.filter(Task)
   	}
   	
   	def private getParameters(BuildFile it) {
   		declarations.filter(Parameter)
   	}
   	
   	def private getMethodName(Task it) {
   		name
   	}
   	
   	def getJavaClassName(BuildFile it) {
		if (name == null)
			eResource.URI.trimFileExtension.lastSegment
		else
			name + "." + eResource.URI.trimFileExtension.lastSegment
	}
   	
}
