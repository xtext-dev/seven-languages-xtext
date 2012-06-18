package org.xtext.template.jvmmodel

import com.google.inject.Inject
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.xtext.template.xtl.XTLFile
import org.eclipse.xtext.common.types.util.TypeReferences
import static extension org.eclipse.xtext.EcoreUtil2.*
import org.xtext.template.xtl.ExpressionStmt
import org.xtext.template.xtl.BlockStmt
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.xtext.template.xtl.TextStmt

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class TemplateJvmModelInferrer extends AbstractModelInferrer {

       /**
     * convenience API to build and initialize JVM types and their members.
     */
	@Inject extension JvmTypesBuilder
	
	@Inject extension TypeReferences

	/**
	 * The dispatch method {@code infer} is called for each instance of the
	 * given element's type that is contained in a resource.
	 * 
	 * @param element
	 *            the model to create one or more
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType declared
	 *            types} from.
	 * @param acceptor
	 *            each created
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType type}
	 *            without a container should be passed to the acceptor in order
	 *            get attached to the current resource. The acceptor's
	 *            {@link IJvmDeclaredTypeAcceptor#accept(org.eclipse.xtext.common.types.JvmDeclaredType)
	 *            accept(..)} method takes the constructed empty type for the
	 *            pre-indexing phase. This one is further initialized in the
	 *            indexing phase using the closure you pass to the returned
	 *            {@link org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing#initializeLater(org.eclipse.xtext.xbase.lib.Procedures.Procedure1)
	 *            initializeLater(..)}.
	 * @param isPreIndexingPhase
	 *            whether the method is called in a pre-indexing phase, i.e.
	 *            when the global index is not yet fully updated. You must not
	 *            rely on linking using the index if isPreIndexingPhase is
	 *            <code>true</code>.
	 */
   	def dispatch void infer(XTLFile element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		val simpleName = element.eResource.URI.trimFileExtension.lastSegment
   		val qualifiedName = if(element.^package != null) 
   				element.^package + "." + simpleName
   			else 
   				simpleName
   		val expressionNames = element.expressionNames
   		acceptor.accept(element.toClass(qualifiedName))
   			.initializeLater([
   				for(p:element.params) {
   					val field = p.toField(p.name, p.type)
//   					translateAnnotationsTo(p.annotations, field)
   					members += field
   				}
   				
   				
   				members += element.toMethod("generate", element.newTypeRef(typeof(String))) [
   					body = [
   						val type = findDeclaredType(typeof(StringBuilder), element)
   						append(type)
   						append(" out = new ")
   						append(type)
   						append("();");
   						newLine 
   						element.body.genStatement(it)
   						newLine
   						append("return out.toString();");
   					]
   				]

				for(p:element.params) {
   				    val method = element.toMethod("set" +p.name.toFirstUpper, element.newTypeRef("void")) [
   				    	parameters += p.toParameter(p.name, p.type)
   				    	body = [
   				    		append('''this.�p.name� = �p.name�;''')
   				    	]	
   					]
   					translateAnnotationsTo(p.annotations, method)
   					members += method
   				}
   				
   				for(es:element.getAllContentsOfType(typeof(ExpressionStmt))) {
   					members += element.toMethod(expressionNames.get(es), element.newTypeRef(typeof(String))) [
   						body = 	es.expresson
   					] 
   				}
   			])
   	}
   	
   	def getExpressionNames(XTLFile file) {
   		val result = <ExpressionStmt, String>newHashMap()
   		var i = -1
   		for(es:file.getAllContentsOfType(typeof(ExpressionStmt)))
   			result.put(es, "expression" + (i = i + 1))
   		result
   	}
   	
   	def dispatch void genStatement(BlockStmt blockStmt, ITreeAppendable out) {
   		for(s:blockStmt.statements) 
   			genStatement(s, out)
   	}
   	
   	def dispatch void genStatement(TextStmt textStmt, ITreeAppendable out) {
   		out.append("out.append(\"")
   		out.append(textStmt.text.replace("\n", "\\n").replace("\r", "\\r"))
   		out.append("\");")
   		
   	}
}

