/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.ui.action;

import static org.cloudsmith.geppetto.injectable.CommonModuleProvider.getCommonModule;

import java.util.Collections;

import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.google.inject.Guice;
import com.google.inject.Module;

public class ImportModulesFromForgeActionDelegate extends ActionDelegate implements IViewActionDelegate {

	@Override
	public void init(IViewPart view) {
		// do nothing
	}

	@Override
	public void run(IAction action) {
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				try {
					Module forgeModule = ForgeService.getForgeModule(ForgeService.getDefaultPreferences());
					Forge forge = Guice.createInjector(getCommonModule(), forgeModule).getInstance(Forge.class);

					// Uh, this seems to import ALL MODULES from the Forge!!! Who calls this?
					// THH 2013-03-07
					for(ModuleInfo module : forge.search_v1(null)) {
						ModuleName fullName = module.getFullName();
						String projectName = fullName.getOwner();

						progressMonitor.subTask(UIPlugin.INSTANCE.getString(
							"_UI_CreatingPuppetProject_message", new Object[] { projectName })); //$NON-NLS-1$

						try {
							IProject project = ResourceUtil.createProject(
								new Path(projectName), null, Collections.<IProject> emptyList(), progressMonitor);

							forge.install(
								module.getFullName(), null, project.getLocation().append(fullName.getName()).toFile(),
								true, true);

							project.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(progressMonitor, 1));
						}
						catch(Exception exception) {
							UIPlugin.INSTANCE.log(exception);
						}
					}
				}
				catch(Exception exception) {
					UIPlugin.INSTANCE.log(exception);
				}
				finally {
					progressMonitor.done();
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(false, false, operation);
		}
		catch(Exception exception) {
			UIPlugin.INSTANCE.log(exception);
		}
	}
}
