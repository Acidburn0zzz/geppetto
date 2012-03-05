/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import org.eclipse.xtext.formatting.IIndentationInformation;

/**
 * PP indentation is 2 spaces by default.
 * TODO: make it possible to change this via preferences.
 * 
 */
public class PPIndentationInformation implements IIndentationInformation {

	@Override
	public String getIndentString() {
		return "  ";
	}

}
