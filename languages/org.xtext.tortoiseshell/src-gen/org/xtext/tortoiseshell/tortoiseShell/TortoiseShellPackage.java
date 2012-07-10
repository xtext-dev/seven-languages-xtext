/**
 */
package org.xtext.tortoiseshell.tortoiseShell;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.xtext.tortoiseshell.tortoiseShell.TortoiseShellFactory
 * @model kind="package"
 * @generated
 */
public interface TortoiseShellPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "tortoiseShell";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.xtext.org/tortoiseshell/TortoiseShell";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "tortoiseShell";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TortoiseShellPackage eINSTANCE = org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl.init();

  /**
   * The meta object id for the '{@link org.xtext.tortoiseshell.tortoiseShell.impl.ExecutableImpl <em>Executable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtext.tortoiseshell.tortoiseShell.impl.ExecutableImpl
   * @see org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl#getExecutable()
   * @generated
   */
  int EXECUTABLE = 2;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXECUTABLE__BODY = 0;

  /**
   * The number of structural features of the '<em>Executable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXECUTABLE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.xtext.tortoiseshell.tortoiseShell.impl.ProgramImpl <em>Program</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtext.tortoiseshell.tortoiseShell.impl.ProgramImpl
   * @see org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl#getProgram()
   * @generated
   */
  int PROGRAM = 0;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROGRAM__BODY = EXECUTABLE__BODY;

  /**
   * The feature id for the '<em><b>Sub Programs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROGRAM__SUB_PROGRAMS = EXECUTABLE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Program</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROGRAM_FEATURE_COUNT = EXECUTABLE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtext.tortoiseshell.tortoiseShell.impl.SubProgramImpl <em>Sub Program</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtext.tortoiseshell.tortoiseShell.impl.SubProgramImpl
   * @see org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl#getSubProgram()
   * @generated
   */
  int SUB_PROGRAM = 1;

  /**
   * The feature id for the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_PROGRAM__BODY = EXECUTABLE__BODY;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_PROGRAM__NAME = EXECUTABLE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_PROGRAM__PARAMETERS = EXECUTABLE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Sub Program</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_PROGRAM_FEATURE_COUNT = EXECUTABLE_FEATURE_COUNT + 2;


  /**
   * Returns the meta object for class '{@link org.xtext.tortoiseshell.tortoiseShell.Program <em>Program</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Program</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.Program
   * @generated
   */
  EClass getProgram();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtext.tortoiseshell.tortoiseShell.Program#getSubPrograms <em>Sub Programs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Sub Programs</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.Program#getSubPrograms()
   * @see #getProgram()
   * @generated
   */
  EReference getProgram_SubPrograms();

  /**
   * Returns the meta object for class '{@link org.xtext.tortoiseshell.tortoiseShell.SubProgram <em>Sub Program</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Sub Program</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.SubProgram
   * @generated
   */
  EClass getSubProgram();

  /**
   * Returns the meta object for the attribute '{@link org.xtext.tortoiseshell.tortoiseShell.SubProgram#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.SubProgram#getName()
   * @see #getSubProgram()
   * @generated
   */
  EAttribute getSubProgram_Name();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtext.tortoiseshell.tortoiseShell.SubProgram#getParameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.SubProgram#getParameters()
   * @see #getSubProgram()
   * @generated
   */
  EReference getSubProgram_Parameters();

  /**
   * Returns the meta object for class '{@link org.xtext.tortoiseshell.tortoiseShell.Executable <em>Executable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Executable</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.Executable
   * @generated
   */
  EClass getExecutable();

  /**
   * Returns the meta object for the containment reference '{@link org.xtext.tortoiseshell.tortoiseShell.Executable#getBody <em>Body</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Body</em>'.
   * @see org.xtext.tortoiseshell.tortoiseShell.Executable#getBody()
   * @see #getExecutable()
   * @generated
   */
  EReference getExecutable_Body();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  TortoiseShellFactory getTortoiseShellFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.xtext.tortoiseshell.tortoiseShell.impl.ProgramImpl <em>Program</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtext.tortoiseshell.tortoiseShell.impl.ProgramImpl
     * @see org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl#getProgram()
     * @generated
     */
    EClass PROGRAM = eINSTANCE.getProgram();

    /**
     * The meta object literal for the '<em><b>Sub Programs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROGRAM__SUB_PROGRAMS = eINSTANCE.getProgram_SubPrograms();

    /**
     * The meta object literal for the '{@link org.xtext.tortoiseshell.tortoiseShell.impl.SubProgramImpl <em>Sub Program</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtext.tortoiseshell.tortoiseShell.impl.SubProgramImpl
     * @see org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl#getSubProgram()
     * @generated
     */
    EClass SUB_PROGRAM = eINSTANCE.getSubProgram();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SUB_PROGRAM__NAME = eINSTANCE.getSubProgram_Name();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUB_PROGRAM__PARAMETERS = eINSTANCE.getSubProgram_Parameters();

    /**
     * The meta object literal for the '{@link org.xtext.tortoiseshell.tortoiseShell.impl.ExecutableImpl <em>Executable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtext.tortoiseshell.tortoiseShell.impl.ExecutableImpl
     * @see org.xtext.tortoiseshell.tortoiseShell.impl.TortoiseShellPackageImpl#getExecutable()
     * @generated
     */
    EClass EXECUTABLE = eINSTANCE.getExecutable();

    /**
     * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXECUTABLE__BODY = eINSTANCE.getExecutable_Body();

  }

} //TortoiseShellPackage