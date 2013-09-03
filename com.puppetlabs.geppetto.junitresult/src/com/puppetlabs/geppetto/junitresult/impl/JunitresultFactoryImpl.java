/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.junitresult.impl;

import com.puppetlabs.geppetto.junitresult.Failure;
import com.puppetlabs.geppetto.junitresult.JunitresultFactory;
import com.puppetlabs.geppetto.junitresult.JunitresultPackage;
import com.puppetlabs.geppetto.junitresult.Property;
import com.puppetlabs.geppetto.junitresult.Skipped;
import com.puppetlabs.geppetto.junitresult.Testcase;
import com.puppetlabs.geppetto.junitresult.Testrun;
import com.puppetlabs.geppetto.junitresult.Testsuite;
import com.puppetlabs.geppetto.junitresult.Testsuites;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class JunitresultFactoryImpl extends EFactoryImpl implements JunitresultFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static JunitresultPackage getPackage() {
		return JunitresultPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static JunitresultFactory init() {
		try {
			JunitresultFactory theJunitresultFactory = (JunitresultFactory) EPackage.Registry.INSTANCE.getEFactory("http://www.puppetlabs.com/geppetto/1.0.0/Junitresult");
			if(theJunitresultFactory != null) {
				return theJunitresultFactory;
			}
		}
		catch(Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JunitresultFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public JunitresultFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch(eClass.getClassifierID()) {
			case JunitresultPackage.TESTSUITE:
				return createTestsuite();
			case JunitresultPackage.PROPERTY:
				return createProperty();
			case JunitresultPackage.TESTCASE:
				return createTestcase();
			case JunitresultPackage.ERROR:
				return createError();
			case JunitresultPackage.FAILURE:
				return createFailure();
			case JunitresultPackage.TESTRUN:
				return createTestrun();
			case JunitresultPackage.TESTSUITES:
				return createTestsuites();
			case JunitresultPackage.SKIPPED:
				return createSkipped();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public com.puppetlabs.geppetto.junitresult.Error createError() {
		ErrorImpl error = new ErrorImpl();
		return error;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Failure createFailure() {
		FailureImpl failure = new FailureImpl();
		return failure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Skipped createSkipped() {
		SkippedImpl skipped = new SkippedImpl();
		return skipped;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Testcase createTestcase() {
		TestcaseImpl testcase = new TestcaseImpl();
		return testcase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Testrun createTestrun() {
		TestrunImpl testrun = new TestrunImpl();
		return testrun;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Testsuite createTestsuite() {
		TestsuiteImpl testsuite = new TestsuiteImpl();
		return testsuite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Testsuites createTestsuites() {
		TestsuitesImpl testsuites = new TestsuitesImpl();
		return testsuites;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public JunitresultPackage getJunitresultPackage() {
		return (JunitresultPackage) getEPackage();
	}

} // JunitresultFactoryImpl
