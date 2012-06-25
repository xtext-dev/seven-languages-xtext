package org.xtext.cradle.jvmmodel

import com.google.common.collect.Sets
import com.google.inject.Inject
import java.util.Set
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.util.TypeReferences
import org.eclipse.xtext.util.Strings
import org.eclipse.xtext.xbase.compiler.TypeReferenceSerializer
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.eclipse.xtext.xbase.lib.Exceptions
import org.eclipse.xtext.xbase.lib.Procedures$Procedure1
import org.eclipse.xtext.xbase.typing.ITypeProvider
import org.xtext.cradle.cradle.CradleFile
import org.xtext.cradle.cradle.Parameter
import org.xtext.cradle.cradle.Task
import org.xtext.cradle.lib.impl.TaskSkippedException
import org.xtext.cradle.lib.impl.TaskState

import static extension org.xtext.cradle.TaskExtensions.*

class CradleJvmModelInferrer extends AbstractModelInferrer {

	@Inject extension JvmTypesBuilder
	@Inject ITypeProvider typeProvider
	@Inject extension TypeReferenceSerializer
	@Inject extension TypeReferences

   	def dispatch void infer(CradleFile element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		val fqn = element.javaClassName
   		val name = Strings::lastToken(fqn, ".")
   		acceptor.accept(element.toClass(fqn))
   			.initializeLater([
   				val data = element.toClass(name + "Params") [
   					^static = true
   					members += element.toConstructor[]
	   				for (param : element.declarations.filter(typeof(Parameter))) {
	   					val type = param.type ?: typeProvider.getType(param.init)
	   					members += param.toField(param.name, type) [
	   						visibility = JvmVisibility::PUBLIC
	   						if (param.init != null)
	   							initializer = param.init
	   					]
	   				}
   				]
   				members += data
   				
   				members += element.toMethod("main", element.newTypeRef(Void::TYPE)) [
   					parameters += element.toParameter("args", element.newTypeRef(typeof(String)).addArrayTypeDimension)
   					^static = true
   					body = [
   						val tasks = element.declarations.filter(typeof(Task)).toList
   						val params = element.declarations.filter(typeof(Parameter)).toList
   						append("int index = 0;").newLine
   						append("while(args.length == 0 || index < args.length) {").increaseIndentation.newLine
   						append('''if(args.length == 0 || "--help".equals(args[index]) ||  "-h".equals(args[index])) {''').increaseIndentation.newLine
   						append('''System.out.println("Cradle '«element.eResource.URI.trimFileExtension.lastSegment»'");''').newLine
   						append('''System.out.println();''').newLine
   						append('''System.out.println("Tasks:");''').newLine
   						for(dec : tasks)
   							append('''System.out.println("  «dec.name»");''').newLine
   						if(!params.empty) {
	   						append('''System.out.println();''').newLine
	   						append('''System.out.println("Parameters:");''').newLine
	   						for(dec : params) {
	   							append('''System.out.println("  --«dec.name» <''')
	   							(dec.type ?: typeProvider.getType(dec.init)).serialize(element, it)
	   							append('''>");''').newLine
	   						}
   						}
   						append('''return;''')
   						decreaseIndentation.newLine.append("} else")
   						increaseIndentation.newLine.append("index++;").decreaseIndentation
   						decreaseIndentation.newLine.append("}").newLine
   						serialize(newTypeRef(element, typeof(Set), newTypeRef(element, typeof(String))), element, it)
   						append(" tasks = ")
   						append(findDeclaredType(typeof(Sets), element))
   						append(".newLinkedHashSet();").newLine
   						append(data)
   						append(" parameter = new ")
   						append(data)
   						append("();").newLine
	   					append("index = 0;").newLine
   						append("while(index < args.length) {").increaseIndentation.newLine
	   					for(dec : element.declarations) {
	   						if(dec != element.declarations.head)
	   							append(" else ")
   							switch(dec) {
   								Parameter: {
			   						append('''if("--«dec.name»".equals(args[index])) {''').increaseIndentation.newLine
			   						append('''parameter.«dec.name» = args[++index];''')
			   						decreaseIndentation.newLine.append("}")	 
   								}
   								Task: {
			   						append('''if("«dec.name»".equals(args[index])) {''').increaseIndentation
			   						for(t:dec.findDependentTasks)
			   							newLine.append('''tasks.add("«t.name»");''')
			   						decreaseIndentation.newLine.append("}") 
   								}
   							}
	   					}
	   					append(" else {").increaseIndentation.newLine
	   					append('''System.out.println("Unknown task/parameter '" + args[index] + "'. Run program with --help to list available tasks/parameters");''').newLine
	   					append('''return;''')
	   					decreaseIndentation.newLine.append("}")
	   					newLine.append("index++;")
	   					decreaseIndentation.newLine.append("}").newLine
   						append('''try {''').increaseIndentation.newLine
   						append("for(String task:tasks) {").increaseIndentation.newLine
	   					for(dec : tasks) {
	   						if(dec != tasks.head)
	   							newLine.append("else ")
	   						append('''if("«dec.name»".equals(task))''').increaseIndentation.newLine
	   						append('''«dec.methodNameExecute»(parameter, true);''') 
	   						decreaseIndentation	
	   					}
	   					newLine.append("index++;")
	   					decreaseIndentation.newLine.append("}")
	   					decreaseIndentation.newLine.append("} catch(")
	   					append(findDeclaredType(typeof(Throwable), element))
	   					append(''' e) {''').increaseIndentation.newLine
	   					append('''System.out.println();''').newLine
	   					append('''e.printStackTrace();''')
	   					decreaseIndentation.newLine.append("}")
   					]
   				]
   				
   				for (task : element.declarations.filter(typeof(Task))) {
		   			members += task.toMethod(task.methodName, task.newTypeRef(Void::TYPE)) [
		   				parameters += task.toParameter("init", element.newTypeRef(typeof(Procedures$Procedure1),newTypeRef(data)))
		   				^static = true
		   				body = [
		   					append(data)
		   					append(' p = new ')
		   					append(data)
		   					append('();').newLine
		   					append('init.apply(p);')
		   					for (dependency : task.findDependentTasks) {
			   					newLine.append(dependency.methodNameExecute+"(p, false);")
		   					}
		   				]
		   			]
		   			members += task.toMethod(task.methodNameExecute, task.newTypeRef(Void::TYPE)) [
		   				^static = true
		   				visibility = JvmVisibility::PROTECTED
		   				parameters += task.toParameter("it", newTypeRef(data))
		   				parameters += task.toParameter("log", task.newTypeRef(Boolean::TYPE))
		   				body = [
		   					append('''try {''').increaseIndentation.newLine
		   					append('''if(log) System.out.println("running «task.name»...");''').newLine
	   						append(task.methodNameExecuteImpl)
	   						append('''(it);''').newLine
		   					append(findDeclaredType(typeof(TaskState), element))
		   					append('''.fireFinishTask("«task.name»", null);''').newLine
		   					append('''if(log) System.out.println("success");''')
		   					decreaseIndentation.newLine.append("} catch(")
		   					append(findDeclaredType(typeof(TaskSkippedException), element))
		   					append(''' e) {''').increaseIndentation.newLine
		   					append(findDeclaredType(typeof(TaskState), element))
		   					append('''.fireFinishTask("«task.name»", e);''').newLine
		   					append('''if(log) System.out.println("skipped: " + e.getMessage());''')
		   					decreaseIndentation.newLine.append("} catch(")
		   					append(findDeclaredType(typeof(Throwable), element))
		   					append(''' e) {''').increaseIndentation.newLine
		   					append(findDeclaredType(typeof(TaskState), element))
		   					append('''.setMaySkip(false);''').newLine
		   					append(findDeclaredType(typeof(TaskState), element))
		   					append('''.fireFinishTask("«task.name»", e);''').newLine
		   					append('''if(log) System.out.println("error: "+e.getMessage());''').newLine
		   					append(findDeclaredType(typeof(Exceptions), element))
	   						append('''.sneakyThrow(e);''')
		   					decreaseIndentation.newLine.append("}")
		   				]
		   			]
		   			members += task.toMethod(task.methodNameExecuteImpl, task.newTypeRef(Void::TYPE)) [
		   				^static = true
		   				visibility = JvmVisibility::PROTECTED
		   				parameters += task.toParameter("it", newTypeRef(data))
		   				body = task.action
		   			]
   				}
   			])
   	}
   	
   	def private getMethodName(Task task) {
   		task.name.toFirstLower
   	}
   	
   	def private getMethodNameExecuteImpl(Task task) {
   		"execute" + task.name.toFirstUpper + "Impl"
   	}
   	
   	def private getMethodNameExecute(Task task) {
   		"execute" + task.name.toFirstUpper
   	}
}
