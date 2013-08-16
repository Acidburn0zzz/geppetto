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
package org.cloudsmith.geppetto.forge;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.client.ForgeException;
import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.semver.VersionRange;

/**
 * This class basically mimics the PMT (Puppet Module Tool)
 */
public interface ForgeService {
	/**
	 * Downloads and installs all dependencies extending from the modules described by <tt>metadatas</tt>.
	 * 
	 * @param metadatas
	 *            The dependencies to resolve
	 * @param importedModulesDir
	 *            The directory where the dependent modules will be installed
	 * @return A list files appointing the installed modules.
	 * @throws IOException
	 */
	Collection<File> downloadDependencies(Iterable<Metadata> metadatas, File importedModulesDir, Diagnostic result)
			throws IOException;

	/**
	 * Install a module (eg, 'user-modname') from the Forge repository. A
	 * module is an archive that contains one single folder. In some cases,
	 * like when installing into a pre-existing workspace project, it's
	 * desirable to skip this folder and instead expand everything beneath
	 * it into the given <code>destination</code>. This behavior can be
	 * enforced by setting the <code>destinationIncludesTopFolder</code> to <code>true</code>.
	 * 
	 * @param fullName
	 *            The name of the module
	 * @param range
	 *            version constraint to apply when selecting the module release. Can be <code>null</code> in which case
	 *            the release with the highest
	 *            version wins
	 * @param destination
	 *            The destination for the install.
	 * @param destinationIncludesTopFolder
	 *            When <code>true</code>, assume that all content beneath the
	 *            top folder in the archive should be installed directly beneath the
	 *            given <code>destination</code>. When this flag is <code>false</code> the top folder of the archive
	 *            will be expanded as-is beneath
	 *            the <code>destination</code>.
	 * @param force
	 *            Set to <code>true</code> to overwrite an existing module.
	 * @return The metadata extracted from the metadata.json file
	 */
	Metadata install(ModuleName fullName, VersionRange range, File destination, boolean destinationIncludesTopFolder,
			boolean force) throws IOException;

	/**
	 * Install a specific release of a module from the Forge repository. A
	 * module is an archive that contains one single folder. In some cases,
	 * like when installing into a pre-existing workspace project, it's
	 * desirable to skip this folder and instead expand everything beneath
	 * it into the given <code>destination</code>. This behavior can be
	 * enforced by setting the <code>destinationIncludesTopFolder</code> to <code>true</code>.
	 * 
	 * @param release
	 *            The module release
	 * @param destination
	 *            The destination for the install.
	 * @param destinationIncludesTopFolder
	 *            When <code>true</code>, assume that all content beneath the
	 *            top folder in the archive should be installed directly beneath the
	 *            given <code>destination</code>. When this flag is <code>false</code> the top folder of the archive
	 *            will be expanded as-is beneath
	 *            the <code>destination</code>.
	 * @param force
	 *            Set to <code>true</code> to overwrite an existing module.
	 * @return The metadata extracted from the metadata.json file
	 */
	Metadata install(Release release, File destination, boolean destinationIncludesTopFolder, boolean force)
			throws IOException;

	/**
	 * Publish a gzipped module tarball to the Forge. The provided diagnostic is used for informational messages
	 * only. Any errors will yield an exception.
	 * 
	 * @param moduleTarball
	 *            The gzipped tarball
	 *            Set to <tt>true</tt> if all but the final step of sending to the Forge should be made
	 * @param result
	 *            The collector diagnostic.
	 * @throws AlreadyPublishedException
	 *             if the module is found on the forge at its current version prior to publishing
	 * @throws ForgeException
	 *             on communication errors with the forge
	 * @throws IOException
	 */
	void publish(File moduleTarball, boolean dryRun, Diagnostic diagnostic) throws ForgeException, IOException;

	/**
	 * Publish all gzipped module tarballs found under <tt>builtModulesDir</tt>. Report progress on the
	 * provided <tt>result</tt> diagnostic. The caller must check the severity of the <tt>result</tt> after this call
	 * has completed.
	 * 
	 * @param moduleTarballs
	 *            Module tarballs to be published.
	 * @param dryRun
	 *            Set to <tt>true</tt> if all but the final step of sending to the Forge should be made
	 * @param result
	 *            The collector diagnostic.
	 */
	void publishAll(File[] moduleTarballs, boolean dryRun, Diagnostic result);

	/**
	 * Resolves all dependencies extending from the modules described by <tt>metadatas</tt>.
	 * 
	 * @param metadatas
	 *            The dependencies to resolve
	 * @param unresolvedCollector
	 *            A collector where unresolved dependencies, if any, will be added.
	 * @return A set of releases that constitutes the successful part of the resolution
	 * @throws IOException
	 */
	Set<Release> resolveDependencies(Iterable<Metadata> metadatas, Set<Dependency> unresolvedCollector)
			throws IOException;

	/**
	 * Search the module repository for modules matching <code>term</code>
	 * 
	 * @param term
	 *            Search term
	 */
	List<Module> search(String term) throws IOException;

	/**
	 * Use the old v1 API to search the Forge for modules matching <code>term</code>
	 * 
	 * @param term
	 *            Search term
	 */
	List<ModuleInfo> search_v1(String term) throws IOException;
}
