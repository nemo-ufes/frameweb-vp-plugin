package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.managers.FrameWebStereotypesManager;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassDependency;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import v.bel.j;
import br.ufes.inf.nemo.vpzy.managers.StereotypesManager;

/**
 * Controller that handles the Add Dependency to Class action, activated by a context menu (right-click) for FrameWeb
 * Class elements.
 *
 * @author Igor Sunderhus e Silva <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class AddFrameWebDependencyToClassContextController implements VPContextActionController {
    /** Called when the button is pressed. Adds the dependency to the class and diagram. */
    @Override
    public void performAction(final VPAction action, final VPContext context, final ActionEvent event) {

        Logger.log(Level.CONFIG, "Performing action: Add FrameWeb Dependency (Class) > {0}", event.getActionCommand());

        // Processes the selected element
        final IModelElement modelElement = context.getModelElement();

        final FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

        if (modelElementFrameWebClass == FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
            Logger.log(Level.WARNING, "The selected element is not a FrameWeb class, ignoring action.");
            return;
        }

        final IDiagramUIModel currentDiagram = context.getDiagram();


        // List of all packages present in the current diagram
        final List<IModelElement> currentDiagramPackages = new ArrayList<>();
        // List of all DAO Interfaces present in the current diagram
        final List<IModelElement> currentDiagramDAOInterfaces = new ArrayList<>();
        // List of all DAO Interfaces that have an association with the service class
        final List<IModelElement> associatedDAOInterfaces = new ArrayList<>();

        context.getDiagram().diagramElementIterator().forEachRemaining(element -> {
            final IDiagramElement diagramElement = (IDiagramElement) element;
            final IModelElement diagramElementModel = diagramElement.getModelElement();
            Logger.log(Level.INFO, "DiagramElement: {0}, ModelType: {1}, Shape Type: {2}, Id: {3}",
                    new Object[] { diagramElementModel.getName(), diagramElementModel.getModelType(),
                                   diagramElement.getShapeType(), diagramElement.getId()});
            if (diagramElementModel.getModelType().equals(IModelElementFactory.MODEL_TYPE_PACKAGE)){
                currentDiagramPackages.add(diagramElementModel);
            }
            else{
                final FrameWebClass elementFrameWebClass = FrameWebUtils.getFrameWebClass(diagramElementModel);
                if (elementFrameWebClass == FrameWebClass.DAO_INTERFACE) {
                    currentDiagramDAOInterfaces.add(diagramElementModel);
                }

                if (diagramElementModel.getModelType().equals(IModelElementFactory.MODEL_TYPE_ASSOCIATION)){
                    // TODO - Get the "from" and the "to" class
                    final IAssociation associationElement = (IAssociation) diagramElementModel;
                    final IModelElement toElement = associationElement.getTo();
                    final IModelElement fromElement = associationElement.getFrom();

                    if (toElement.getId().equals(modelElement.getId()) || fromElement.getId().equals(modelElement.getId())){
                        if (FrameWebUtils.getFrameWebClass(toElement) == FrameWebClass.DAO_INTERFACE){
                            associatedDAOInterfaces.add(toElement);
                        }
                        else if (FrameWebUtils.getFrameWebClass(fromElement) == FrameWebClass.DAO_INTERFACE){
                            associatedDAOInterfaces.add(fromElement);
                        }
                    }
                    Logger.log(Level.INFO, "To: {0}, From: {1}",
                            new Object[] { associationElement.getTo().getName(), associationElement.getFrom().getName()});
                }
                }
        });

        // Print the name of all the packages in the diagram
        for (final IModelElement pkg : currentDiagramPackages){
            Logger.log(Level.INFO, "Package name: {0}, Id: {1}",
                    new Object[] {pkg.getName(), pkg.getId()});
        }

        // Error getting parentModel
//        final IModelElement parentModel = currentDiagram.getParentModel();
//        Logger.log(Level.INFO, "ModelElement: {0}, ModelType: {1}, Diagram: {2}, Parent Model: {3}",
//                new Object[] { modelElement.getName(), modelElement.getModelType(), currentDiagram.getName(),
//                        parentModel.getName() });

        final IModelElement parentModel = currentDiagram.getParentModel();
        Logger.log(Level.INFO, "ModelElement: {0}, ModelType: {1}, Diagram: {2}",
                new Object[] { modelElement.getName(), modelElement.getModelType(), currentDiagram.getName()});


        // Determines which FrameWeb Class Dependency to apply from the menu item.
        FrameWebClassDependency frameWebClassDependency = FrameWebClassDependency.ofPluginUIID(action.getActionId());
        Logger.log(Level.INFO, "Adding {0} to {1}",
                new Object[] { frameWebClassDependency.getName(), modelElement.getName() });

        final List<IClass> frameWebClasses = FrameWebUtils.getFrameWebClasses(ProjectManagerUtils.getCurrentProject(),
                FrameWebClass.of(frameWebClassDependency.getName()));

        // logs the classes that will be added as dependencies
        for (final IClass clazz : frameWebClasses) {
            final IModelElement clazzPackage = clazz.getParent();

            Logger.log(Level.INFO, "Adding dependency {0} - {1} to {2}",
                    new Object[] { clazz.getName(), clazzPackage.getName(), modelElement.getName() });

            // Check if the package of the current DAO Interface class is already in the diagram
            boolean pkgNotInTheDiagram = true;
            for (final IModelElement pkg : currentDiagramPackages){
                if (clazzPackage.getId().equals(pkg.getId())){
                    pkgNotInTheDiagram = false;
                    break;
                }
            }

            // Instance of DiagramManager to use method CreateDiagramElement()
            j diagManager = new j(null);

            // Gets the FrameWeb stereotypes manager associated with the current project.
            StereotypesManager stereotypesManager =
                    StereotypesManager.getInstance(FrameWebStereotypesManager.class);

            if (pkgNotInTheDiagram){
                // TODO - Add the package of the current DAO Interface class to the diagram
                Logger.log(Level.INFO, "Package to be added: {0}, Id: {1}",
                        new Object[] {clazzPackage.getName(), clazzPackage.getId()});
                currentDiagramPackages.add(clazzPackage);

                final IDiagramElement packageAdded = diagManager.createDiagramElement(context.getDiagram(), clazzPackage);
                final IModelElement pkgAddedElementModel = packageAdded.getModelElement();
                Logger.log(Level.INFO, "Package {0} was added to the diagram, Id: {1}, MasterView: {2}, Color: {3}",
                            new Object[] {pkgAddedElementModel.getName(), pkgAddedElementModel.getId(),
                                    String.valueOf(packageAdded.isMasterView()), clazzPackage.getDiagramElements()[0].getBackground().toString()});

                IStereotype newStereotype = stereotypesManager.getStereotype(
                        FrameWebPackage.PERSISTENCE_PACKAGE.getStereotypeName(), IModelElementFactory.MODEL_TYPE_PACKAGE);
                // Adds the new FrameWeb package stereotype.
                pkgAddedElementModel.addStereotype(newStereotype);
//                packageAdded.setBackground(FrameWebPackage.PERSISTENCE_PACKAGE.getColor().getAwtColor());


//                final IDiagramElement de = ProjectManagerUtils.getCurrentProject().getDiagramElementById(clazzPackage.getId());
//                if (context.getDiagram().addDiagramElement(clazzPackage.getDiagramElements()[0])){
//                    Logger.log(Level.INFO, "Package {0} was added to the diagram, Id: {1}, MasterView: {2}",
//                            new Object[] {clazzPackage.getName(), clazzPackage.getId(), String.valueOf(clazzPackage.getDiagramElements()[0].isMasterView())});
//                }
//                context.getDiagram().fireModuleAddedDiagramElement(clazzPackage.getDiagramElements()[0]);

//                ProjectManagerUtils.getCurrentProject().modelElementIterator("Package").forEachRemaining(element -> {
//                    IModelElement packageModelElement = (IModelElement) element;
//                    if (packageModelElement.getName().equals(clazzPackage.getName())) {
////                        if (context.getDiagram().addDiagramElement(packageModelElement.getDiagramElements()[0])) {
//                            IModelElementFactory packageModelElementFactory = (IModelElementFactory) element;
//                            IDiagramOverview overview =  packageModelElementFactory.createDiagramOverview();
//                            Logger.log(Level.INFO, "TESTE: Package: {0}, Id: {1}",
//                                    new Object[] {overview.getName(), overview.getId()});
////                        }
//                    }
//                });

//                IDiagramElement packageAdded = context.getDiagram().createDiagramElement(clazzPackage.getDiagramElements()[0].getShapeType(), true);
//                final IModelElement packageElementModel = packageAdded.getModelElement();
//                packageElementModel.setName("teste");
//                packageAdded.setModelElement(packageElementModel);
//                if (context.getDiagram().addDiagramElement(packageAdded)){
//                    Logger.log(Level.INFO, "Package {0} was added to the diagram, Id: {1}, MasterView: {2}",
//                            new Object[] {packageElementModel.getName(), packageAdded.getId(), String.valueOf(packageAdded.isMasterView())});
//                }
            }


            // Check if the current DAO Interface class is already in the diagram
            boolean DAONotInTheDiagram = true;
            for (final IModelElement DAOInterface : currentDiagramDAOInterfaces){
                if (clazz.getId().equals(DAOInterface.getId())){
                    DAONotInTheDiagram = false;
                    break;
                }
            }

            if (DAONotInTheDiagram){
                // TODO - Add the current DAO Interface class to the diagram
                Logger.log(Level.INFO, "DAO Interface to be added: {0}",
                        new Object[] {clazz.getName()});


                final IDiagramElement DAOInterfaceAdded = diagManager.createDiagramElement(context.getDiagram(), clazzPackage.getChildById(clazz.getId()));
                final IModelElement DAOAddedElementModel = DAOInterfaceAdded.getModelElement();
                Logger.log(Level.INFO, "DAO Interface {0} was added to the diagram, Id: {1}, MasterView: {2}, Color: {3}",
                        new Object[] {DAOAddedElementModel.getName(), DAOAddedElementModel.getId(),
                                String.valueOf(DAOInterfaceAdded.isMasterView()), clazzPackage.getChildById(clazz.getId()).getDiagramElements()[0].getBackground().toString()});

                IStereotype newStereotype = stereotypesManager.getStereotype(
                        FrameWebClass.DAO_INTERFACE.getStereotypeName(), IModelElementFactory.MODEL_TYPE_PACKAGE);
                // Adds the new FrameWeb package stereotype.
                DAOAddedElementModel.addStereotype(newStereotype);
            }


            // Check if the association to the DAO Interface class exists
            boolean alreadyAssociated = false;
            for (final IModelElement DAOInterface : associatedDAOInterfaces){
                if (DAOInterface.getId().equals(clazz.getId())){
                    alreadyAssociated = true;
                    break;
                }
            }

            if (!alreadyAssociated){
                // TODO - Add the association to the class.
                Logger.log(Level.INFO, "Association to be added: {0} - {1}",
                        new Object[] {modelElement.getName(), clazz.getName()});
            }



        }

    }

    /**
     * Called when the menu containing the button is accessed allowing for action manipulation, such as enable/disable
     * or selecting the button.
     */
    @Override
    public void update(final VPAction action, final VPContext context) {

        // Checks if the attribute that was clicked belongs to a FrameWeb class.
        final IModelElement modelElement = context.getModelElement();
        if (modelElement != null) {
            final FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

            final FrameWebClassDependency frameWebClassAction = FrameWebClassDependency.ofPluginUIID(
                    action.getActionId());
            boolean enabled = false;
            for (final FrameWebClass clazz : frameWebClassAction.getFrameWebClasses()) {
                if (modelElementFrameWebClass == clazz) {
                    enabled = true;
                    break;
                }
            }
            action.setEnabled(enabled);
        }
    }
}
