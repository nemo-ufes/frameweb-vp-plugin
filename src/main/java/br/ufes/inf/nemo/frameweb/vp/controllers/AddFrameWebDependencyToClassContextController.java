package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassDependency;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.frameweb.vp.view.SelectClassPanel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.DiagramElementUtils;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.model.*;
import com.vp.plugin.model.factory.IModelElementFactory;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Controller that handles the Add Dependency to Class action, activated by a context menu (right-click) for FrameWeb
 * Class elements.
 *
 * @author Igor Sunderhus e Silva <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class AddFrameWebDependencyToClassContextController implements VPContextActionController {
    /** The FrameWebClass of the selected element */
    private FrameWebClass frameWebClassSelected;
    public FrameWebClass getFrameWebClassSelected(){ return this.frameWebClassSelected; }

    /** The contents of the Add FrameWeb Dependency dialog. */
    private SelectClassPanel selectClassPanel;

    /** The Add FrameWeb Dependency dialog. */
    private IDialog selectClassDialog;

    /** A map of the Dependency Classes (controllers or DAO interfaces) of the project. */
    private Map<String, IClass> dependencyClassesMap;

    public Map<String, IClass> getDependencyClassesMap(){
        return this.dependencyClassesMap;
    }

    private VPContext context;

    /** List of all packages present in the current diagram */
    private List<IModelElement> currentDiagramPackages;
    /** Application packages in the diagram*/
    private List<IDiagramElement> applicationPackages;
    /** List of all Dependency Classes (controllers or DAO interfaces) present in the current diagram */
    private List<IDiagramElement> currentDiagramDependencyClasses;
    /** List of all Dependency Classes that have an association with a service interface or a service class */
    private List<IModelElement> associatedDependencyClasses;


    /** Called when the button is pressed. Adds the dependency to the class and diagram. */
    @Override
    public void performAction(final VPAction action, final VPContext context, final ActionEvent event) {
        Logger.log(Level.CONFIG, "Performing action: Add FrameWeb Dependency (Class) > {0}",
                event.getActionCommand());

        this.context = context;

        // Processes the selected element
        final IModelElement modelElement = context.getModelElement();

        final FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

        if (modelElementFrameWebClass == FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
            Logger.log(Level.WARNING, "The selected element is not a FrameWeb class, ignoring action.");
            return;
        }

        this.frameWebClassSelected = modelElementFrameWebClass;

        // Determines which FrameWeb Class Dependency to apply from the menu item.
        FrameWebClassDependency frameWebClassDependency = FrameWebClassDependency.ofPluginUIID(action.getActionId());

        // Processes the class that was right-clicked. Can be a service class (add DAO Interface dependency) or a
        // service interface (add Controller Class dependency)
        this.processesSelectedClass(action, context);

        // If a service class was right-clicked -> Add FrameWebDependency -> DAO Interface
        if(modelElementFrameWebClass == FrameWebClass.SERVICE_CLASS) {
            if (dependencyClassesMap.isEmpty()) {
                ViewManagerUtils.showMessageDialog("There are no DAO classes to be added.",
                        "Add FrameWeb Dependency - DAO Interface", ViewManagerUtils.INFORMATION_MESSAGE);
                return;
            }
        }

        // If a service interface was right-clicked -> Add FrameWebDependency -> Controller class
        else if(modelElementFrameWebClass == FrameWebClass.SERVICE_INTERFACE) {
            if (dependencyClassesMap.isEmpty()) {
                ViewManagerUtils.showMessageDialog("There are no Controller Classes to be added.",
                        "Add FrameWeb Dependency - Controller Class", ViewManagerUtils.INFORMATION_MESSAGE);
                return;
            }
        }

        // --------- Dialog ---------
        FrameWebPlugin plugin = FrameWebPlugin.instance();

        Logger.log(Level.INFO, "Adding {0} to {1}",
                new Object[]{frameWebClassDependency.getName(), modelElement.getName()});

        // If the dialog is already open, ignores the action.
        if (plugin.isSelectClassDialogOpen())
            return;

        // Otherwise, opens the dialog.
        plugin.setSelectClassDialogOpen(true);
        ViewManagerUtils.showDialog(new AddFrameWebDependencyToClassContextController.SelectClassDialogHandler());
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

    private void processesSelectedClass(final VPAction action, final VPContext context){
        final IModelElement modelElement = context.getModelElement();

        // Fill in the lists
        currentDiagramPackages = new ArrayList<>();
        currentDiagramDependencyClasses = new ArrayList<>();
        associatedDependencyClasses = new ArrayList<>();
        applicationPackages = new ArrayList<>();

        context.getDiagram().diagramElementIterator().forEachRemaining(element -> {
            final IDiagramElement diagramElement = (IDiagramElement) element;
            final IModelElement diagramElementModel = diagramElement.getModelElement();
            if (diagramElementModel.getModelType().equals(IModelElementFactory.MODEL_TYPE_PACKAGE)){
                currentDiagramPackages.add(diagramElementModel);
                final FrameWebPackage elementFrameWebPackage = FrameWebUtils.getFrameWebPackage(diagramElementModel);
                if (elementFrameWebPackage == FrameWebPackage.APPLICATION_PACKAGE) {
                    applicationPackages.add(diagramElement);
                }
            }
            else{
                final FrameWebClass elementFrameWebClass = FrameWebUtils.getFrameWebClass(diagramElementModel);
                if(this.frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                    if (elementFrameWebClass == FrameWebClass.DAO_INTERFACE) {
                        currentDiagramDependencyClasses.add(diagramElement);
                    }
                }
                else if(this.frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE){
                    if (elementFrameWebClass == FrameWebClass.CONTROLLER_CLASS) {
                        currentDiagramDependencyClasses.add(diagramElement);
                    }
                }

                if (diagramElementModel.getModelType().equals(IModelElementFactory.MODEL_TYPE_ASSOCIATION)){
                    // Get the "from" and the "to" class if the element is an association
                    final IAssociation associationElement = (IAssociation) diagramElementModel;
                    final IModelElement toElement = associationElement.getTo();
                    final IModelElement fromElement = associationElement.getFrom();

                    if (toElement.getId().equals(modelElement.getId()) || fromElement.getId().equals(modelElement.getId())){
                        if(this.frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                            if (FrameWebUtils.getFrameWebClass(toElement) == FrameWebClass.DAO_INTERFACE) {
                                associatedDependencyClasses.add(toElement);
                            } else if (FrameWebUtils.getFrameWebClass(fromElement) == FrameWebClass.DAO_INTERFACE) {
                                associatedDependencyClasses.add(fromElement);
                            }
                        }
                        else if(this.frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE) {
                            if (FrameWebUtils.getFrameWebClass(toElement) == FrameWebClass.CONTROLLER_CLASS) {
                                associatedDependencyClasses.add(toElement);
                            } else if (FrameWebUtils.getFrameWebClass(fromElement) == FrameWebClass.CONTROLLER_CLASS) {
                                associatedDependencyClasses.add(fromElement);
                            }
                        }
                    }
                }
            }
        });


        // Determines which FrameWeb Class Dependency to apply from the menu item.
        FrameWebClassDependency frameWebClassDependency = FrameWebClassDependency.ofPluginUIID(action.getActionId());

        final List<IClass> frameWebClasses = FrameWebUtils.getFrameWebClasses(ProjectManagerUtils.getCurrentProject(),
                FrameWebClass.of(frameWebClassDependency.getName()));

        // Create a map that associates the name of the classes (key) to their corresponding IClasses (value)
        this.dependencyClassesMap = new HashMap<>();

        // Checks if the DAO Interface/Controller is already in the diagram, if it has association with the service
        // class/interface and if its parent (persistence package/controller package) is in the diagram.
        // If one of these conditions is false, adds the DAO Interface/Controller Class to the Map.
        for (IClass iclass : frameWebClasses) {
            int flagPut = 0;

            if(this.frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                if ((iclass.getParent() == null) || (iclass.getParent().toStereotypeModelArray() == null) ||
                        (FrameWebUtils.getFrameWebPackage(iclass.getParent()) != FrameWebPackage.PERSISTENCE_PACKAGE)) {
                    Logger.log(Level.INFO, "Class {0} must belong to a FrameWeb Persistence Package to be selected.",
                            new Object[]{iclass.getName()});
                    continue;
                }
            }
            else if(this.frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE) {
                if ((iclass.getParent() == null) || (iclass.getParent().toStereotypeModelArray() == null) ||
                        (FrameWebUtils.getFrameWebPackage(iclass.getParent()) != FrameWebPackage.CONTROLLER_PACKAGE)) {
                    Logger.log(Level.INFO, "Class {0} must belong to a FrameWeb Controller Package to be selected.",
                            new Object[]{iclass.getName()});
                    continue;
                }
            }

            for(IModelElement modElem : associatedDependencyClasses){
                if(iclass.getName().equals(modElem.getName())) {
                    flagPut++;
                    break;
                }
            }
            for(IDiagramElement diaElem : currentDiagramDependencyClasses){
                if(iclass.getName().equals(diaElem.getModelElement().getName())) {
                    flagPut++;
                    break;
                }
            }
            for(IModelElement pkgElem : currentDiagramPackages){
                if(iclass.getParent().getName().equals(pkgElem.getName())) {
                    flagPut++;
                    break;
                }
            }
            if(!(flagPut == 3))
                dependencyClassesMap.put(iclass.getName(), iclass);
        }
    }

    public void addDependencyToSelectedClasses(List<String> selectedClassesNames){
        final IModelElement modelElement = this.context.getModelElement();

        // Get the position of the application package in the diagram (X and Y)
        // default values in case there is no application package in the diagram
        int classPkgX = 100;
        int classPkgY = 100;
        if(!applicationPackages.isEmpty()){
            int applicationWidth = applicationPackages.get(0).getWidth();
            int applicationHeight = applicationPackages.get(0).getHeight();
            if(frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                classPkgX = applicationPackages.get(0).getX() + applicationWidth + 30;
                classPkgY = applicationPackages.get(0).getY();
            }
            else if(frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE){
                classPkgX = applicationPackages.get(0).getX();
                // Positions the Controller Pkg above the Application Pkg.
                // If Y < 0, positions at Y = 0.
                if (applicationPackages.get(0).getY() - applicationHeight - 10 < 0)
                    classPkgY = 0;
                else
                    classPkgY = applicationPackages.get(0).getY() - applicationHeight - 10;
            }
        }

        // Map that associates the persistence packages to the number of DAO Interfaces already added
        // or the controller packages to the number of Controller Classes already added
        // Used to set the position of the DAO Interfaces or Controller Classes added
        Map<IModelElement, Integer> packagesAndChildrenNum = new HashMap<>();

        // logs the classes that will be added as dependencies
        for (final String className : selectedClassesNames){
            final IClass clazz = this.dependencyClassesMap.get(className);
            final IModelElement clazzPackage = clazz.getParent();

            if(!packagesAndChildrenNum.containsKey(clazzPackage)){
                packagesAndChildrenNum.put(clazzPackage, 0);    
            }
            
            Logger.log(Level.INFO, "Adding dependency {0} - {1} to {2}",
                    new Object[] { clazz.getName(), clazzPackage.getName(), modelElement.getName() });

            // Check if the package of the current Class is already in the diagram
            boolean pkgNotInTheDiagram = true;
            for (final IModelElement pkg : currentDiagramPackages){
                if (clazzPackage.getId().equals(pkg.getId())){
                    pkgNotInTheDiagram = false;
                    break;
                }
            }

            if (pkgNotInTheDiagram){
                // Add the package of the current Class to the diagram
                currentDiagramPackages.add(clazzPackage);

                final IDiagramElement packageAdded = DiagramElementUtils.createAuxiliaryViewInDiagram(context.getDiagram(), clazzPackage);
                final IModelElement pkgAddedElementModel = packageAdded.getModelElement();

                if(frameWebClassSelected == FrameWebClass.SERVICE_CLASS)
                    ModelElementUtils.changeFillColor(pkgAddedElementModel, FrameWebPackage.PERSISTENCE_PACKAGE.getColor());
                else if(frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE)
                    ModelElementUtils.changeFillColor(pkgAddedElementModel, FrameWebPackage.CONTROLLER_PACKAGE.getColor());

                // Change size and position of the package added
                packageAdded.setX(classPkgX);
                packageAdded.setY(classPkgY);
                if(!(clazzPackage.getDiagramElements()[0] == null)) {
                    packageAdded.setWidth(clazzPackage.getDiagramElements()[0].getWidth());
                    packageAdded.setHeight(clazzPackage.getDiagramElements()[0].getHeight());
                }
                else{
                    packageAdded.setWidth(200);
                    packageAdded.setHeight(200);
                }
                classPkgX += packageAdded.getWidth() + 10;

                Logger.log(Level.INFO, "Package {0} was added to the diagram.",
                        new Object[] {pkgAddedElementModel.getName()});
            }

            // Check if the current Class is already in the diagram
            IDiagramElement currentClassDiagElement = null;
            boolean ClassNotInTheDiagram = true;
            for (final IDiagramElement dependencyClass : currentDiagramDependencyClasses) {
                if (clazz.getId().equals(dependencyClass.getModelElement().getId())) {
                    ClassNotInTheDiagram = false;
                    currentClassDiagElement = dependencyClass;
                    break;
                }
            }

            if (ClassNotInTheDiagram){
                // Add the current Class to the diagram
                final IDiagramElement classAdded = DiagramElementUtils.createAuxiliaryViewInDiagram(context.getDiagram(), clazzPackage.getChildById(clazz.getId()));
                final IModelElement classAddedElementModel = classAdded.getModelElement();
                if(frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                    Logger.log(Level.INFO, "DAO Interface {0} was added to the diagram.",
                            new Object[]{classAddedElementModel.getName(), classAddedElementModel.getId(),
                                    String.valueOf(classAdded.isMasterView())});
                }
                else if(frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE) {
                    Logger.log(Level.INFO, "Controller Class {0} was added to the diagram.",
                            new Object[]{classAddedElementModel.getName(), classAddedElementModel.getId(),
                                    String.valueOf(classAdded.isMasterView())});
                }

                // Change the position of the class in the diagram
                int multi = packagesAndChildrenNum.get(classAddedElementModel.getParent());
                packagesAndChildrenNum.replace(classAddedElementModel.getParent(), multi+1);
                int len = classAddedElementModel.getParent().getDiagramElements().length;
                classAdded.setX(classAddedElementModel.getParent().getDiagramElements()[len-1].getX() + 20 + (100*multi));
                classAdded.setY(classAddedElementModel.getParent().getDiagramElements()[len-1].getY() + 30);

                if(frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                    ModelElementUtils.changeInterfaceBall(classAddedElementModel, true);
                    ModelElementUtils.changeFillColor(classAddedElementModel, FrameWebClass.DAO_INTERFACE.getColor());
                    classAdded.setWidth(30);
                    classAdded.setHeight(30);
                }
                else if(frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE) {
                    ModelElementUtils.changeFillColor(classAddedElementModel, FrameWebClass.CONTROLLER_CLASS.getColor());
                    classAdded.setWidth(120);
                    classAdded.setHeight(60);
                }
                currentClassDiagElement = classAdded;
            }


            // Check if the association to the Class exists
            boolean alreadyAssociated = false;
            for (final IModelElement dependencyClass : associatedDependencyClasses) {
                if (dependencyClass.getId().equals(clazz.getId())) {
                    alreadyAssociated = true;
                    break;
                }
            }


            if (!alreadyAssociated){
                // Add the association to the class.
                final IDiagramElement associationAdded = DiagramElementUtils.createAssociation(context.getDiagram(),
                        context.getDiagramElement(), currentClassDiagElement);
                final IModelElement associationAddedElementModel = associationAdded.getModelElement();
                final IAssociation associationElement = (IAssociation) associationAddedElementModel;
                final IModelElement toElement = associationElement.getTo();
                final IModelElement fromElement = associationElement.getFrom();

                if(frameWebClassSelected == FrameWebClass.SERVICE_CLASS) {
                    if (associationElement.getFromEnd() instanceof IAssociationEnd) {
                        final IAssociationEnd associationEnd = (IAssociationEnd) associationElement.getFromEnd();
                        associationEnd.setNavigable(IAssociationEnd.NAVIGABLE_NAV_UNSPECIFIED);
                        Logger.log(Level.INFO, "Association was added to the diagram. From: {0}, To: {1}.",
                                new Object[]{fromElement.getName(), toElement.getName()});
                    }
                }
                else if(frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE) {
                    if (associationElement.getToEnd() instanceof IAssociationEnd) {
                        final IAssociationEnd associationEnd = (IAssociationEnd) associationElement.getToEnd();
                        associationEnd.setNavigable(IAssociationEnd.NAVIGABLE_NAV_UNSPECIFIED);
                        Logger.log(Level.INFO, "Association was added to the diagram. From: {0}, To: {1}.",
                                new Object[]{fromElement.getName(), toElement.getName()});
                    }
                }
            }
        }
    }


    /**
     * A dialog handler that is used by Visual Paradigm to open a dialog based on a panel with its
     * contents.
     */
    protected class SelectClassDialogHandler implements IDialogHandler {
        /** Called once before the dialog is shown. Should return the contents of the dialog. */
        @Override
        public Component getComponent() {
            selectClassPanel = new SelectClassPanel(AddFrameWebDependencyToClassContextController.this);
            return selectClassPanel;
        }

        /** Called after getComponent(), dialog is created but not shown. Sets outlook of the dialog. */
        @Override
        public void prepare(IDialog dialog) {
            selectClassDialog = dialog;
            if(frameWebClassSelected == FrameWebClass.SERVICE_CLASS)
                selectClassDialog.setTitle("Add FrameWeb Dependency - DAO Interface");
            else if(frameWebClassSelected == FrameWebClass.SERVICE_INTERFACE)
                selectClassDialog.setTitle("Add FrameWeb Dependency - Controller Class");
            selectClassDialog.setModal(false);
            selectClassDialog.setResizable(true);
            selectClassDialog.setSize(400, 300);
            selectClassPanel.setContainerDialog(selectClassDialog);
        }

        /** Called when the dialog is shown. */
        @Override
        public void shown() {}

        /** Called when the dialog is closed by the user clicking on the close button of the frame. */
        @Override
        public boolean canClosed() {
            FrameWebPlugin.instance().setSelectClassDialogOpen(false);
            return true;
        }
    }
}