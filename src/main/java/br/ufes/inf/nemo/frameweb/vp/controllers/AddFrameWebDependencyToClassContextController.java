package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassDependency;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.frameweb.vp.view.PluginSettingsPanel;
import br.ufes.inf.nemo.frameweb.vp.view.SelectDAOPanel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.DiagramElementUtils;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
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
    /** The contents of the Add FrameWeb Dependency dialog. */
    private SelectDAOPanel selectDAOPanel;

    /** The Plug-in Settings dialog. */
    private IDialog selectDAODialog;

    /** A map of the DAO Interfaces of the project. */
    private Map<String, IClass> daoClassesMap;

    public Map<String, IClass> getDaoClassesMap(){
        return this.daoClassesMap;
    }

    private VPContext context;

    /** List of all packages present in the current diagram */
    private List<IModelElement> currentDiagramPackages;
    /** List of all DAO Interfaces present in the current diagram */
    private List<IDiagramElement> currentDiagramDAOInterfaces;
    /** List of all DAO Interfaces that have an association with the service class */
    private List<IModelElement> associatedDAOInterfaces;
    /** Application packages in the diagram*/
    private List<IDiagramElement> applicationPackages;


    /** Called when the button is pressed. Adds the dependency to the class and diagram. */
    @Override
    public void performAction(final VPAction action, final VPContext context, final ActionEvent event) {
        Logger.log(Level.CONFIG, "Performing action: Add FrameWeb Constraint (Class Attribute) > {0}",
                event.getActionCommand());

        this.context = context;

        // Processes the selected element
        final IModelElement modelElement = context.getModelElement();

        final FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

        if (modelElementFrameWebClass == FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
            Logger.log(Level.WARNING, "The selected element is not a FrameWeb class, ignoring action.");
            return;
        }

        // Fill in the lists
        currentDiagramPackages = new ArrayList<>();
        currentDiagramDAOInterfaces = new ArrayList<>();
        associatedDAOInterfaces = new ArrayList<>();
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
                if (elementFrameWebClass == FrameWebClass.DAO_INTERFACE) {
                    currentDiagramDAOInterfaces.add(diagramElement);
                }

                if (diagramElementModel.getModelType().equals(IModelElementFactory.MODEL_TYPE_ASSOCIATION)){
                    // Get the "from" and the "to" class if the element is an association
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
                }
            }
        });


        // Determines which FrameWeb Class Dependency to apply from the menu item.
        FrameWebClassDependency frameWebClassDependency = FrameWebClassDependency.ofPluginUIID(action.getActionId());

        final List<IClass> frameWebClasses = FrameWebUtils.getFrameWebClasses(ProjectManagerUtils.getCurrentProject(),
                FrameWebClass.of(frameWebClassDependency.getName()));

        // Create a map that associates the name of the classes (key) to their corresponding IClasses (value)
        this.daoClassesMap = new HashMap<>();

        // Checks if the DAO Interface is already in the diagram, if it has association with the service class and
        // if it's parent (persistence package) is in the diagram. If one of these conditions is false, adds the DAO
        // Interface to the Map.
        for (IClass iclass : frameWebClasses) {
            int flagPut = 0;

            if((iclass.getParent() == null) || (iclass.getParent().toStereotypeModelArray() == null) ||
                        (FrameWebUtils.getFrameWebPackage(iclass.getParent()) != FrameWebPackage.PERSISTENCE_PACKAGE)){
                Logger.log(Level.INFO, "Class {0} must belong to a FrameWeb Persistence Package to be selected.",
                        new Object[]{iclass.getName()});
                continue;
            }

            for(IModelElement modElem : associatedDAOInterfaces){
                if(iclass.getName().equals(modElem.getName())) {
                    flagPut++;
                    break;
                }
            }
            for(IDiagramElement diaElem: currentDiagramDAOInterfaces){
                if(iclass.getName().equals(diaElem.getModelElement().getName())) {
                    flagPut++;
                    break;
                }
            }
            for(IModelElement pkgElem: currentDiagramPackages){
                if(iclass.getParent().getName().equals(pkgElem.getName())) {
                    flagPut++;
                    break;
                }
            }
            if(!(flagPut == 3))
                daoClassesMap.put(iclass.getName(), iclass);
        }
        if(daoClassesMap.isEmpty()){
            Logger.log(Level.INFO, "There are no DAO classes to be added.");
            return;
        }

        Logger.log(Level.INFO, "Adding {0} to {1}",
                new Object[] { frameWebClassDependency.getName(), modelElement.getName() });

        // --------- Dialog ---------
        FrameWebPlugin plugin = FrameWebPlugin.instance();

        // If the dialog is already open, ignores the action.
        if (plugin.isSelectDAODialogOpen())
            return;

        // Otherwise, opens the dialog.
        plugin.setSelectDAODialogOpen(true);
        ViewManagerUtils.showDialog(new AddFrameWebDependencyToClassContextController.SelectDAODialogHandler());

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

    public void addDependencyToSelectedDAO(List<String> selectedDAONames){
        final IModelElement modelElement = this.context.getModelElement();

        // Get the position of the application package in the diagram (X and Y)
        // default values in case there is no application package in the diagram
        int persistenceX = 100;
        int persistenceY = 100;
        if(!applicationPackages.isEmpty()){
            int applicationWidth = applicationPackages.get(0).getWidth();
            persistenceX = applicationPackages.get(0).getX() + applicationWidth + 30;
            persistenceY = applicationPackages.get(0).getY();
        }

        // Map that associates the persistence packages to the number of DAO Interfaces already added
        // Used to set the position of the DAO Interfaces added
        Map<IModelElement, Integer> packagesAndChildrenNum = new HashMap<>();

        // logs the classes that will be added as dependencies
        for (final String nameDAO : selectedDAONames){
            final IClass clazz = this.daoClassesMap.get(nameDAO);
            final IModelElement clazzPackage = clazz.getParent();

            if(!packagesAndChildrenNum.containsKey(clazzPackage)){
                packagesAndChildrenNum.put(clazzPackage, 0);    
            }
            
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

            if (pkgNotInTheDiagram){
                // Add the package of the current DAO Interface class to the diagram
                currentDiagramPackages.add(clazzPackage);

                final IDiagramElement packageAdded = DiagramElementUtils.createAuxiliaryViewInDiagram(context.getDiagram(), clazzPackage);
                final IModelElement pkgAddedElementModel = packageAdded.getModelElement();

                ModelElementUtils.changeFillColor(pkgAddedElementModel, FrameWebPackage.PERSISTENCE_PACKAGE.getColor());

                // Change size and position of the package added
                packageAdded.setX(persistenceX);
                packageAdded.setY(persistenceY);
                if(!(clazzPackage.getDiagramElements()[0] == null)) {
                    packageAdded.setWidth(clazzPackage.getDiagramElements()[0].getWidth());
                    packageAdded.setHeight(clazzPackage.getDiagramElements()[0].getHeight());
                }
                else{
                    packageAdded.setWidth(200);
                    packageAdded.setHeight(200);
                }
                persistenceX += packageAdded.getWidth() + 10;

                Logger.log(Level.INFO, "Package {0} was added to the diagram.",
                        new Object[] {pkgAddedElementModel.getName(), pkgAddedElementModel.getId(),
                                String.valueOf(packageAdded.isMasterView()), packageAdded.getWidth()});
            }


            // Check if the current DAO Interface class is already in the diagram
            IDiagramElement currentDAOInterfaceDiagElement = null;
            boolean DAONotInTheDiagram = true;
            for (final IDiagramElement DAOInterface : currentDiagramDAOInterfaces){
                if (clazz.getId().equals(DAOInterface.getModelElement().getId())){
                    DAONotInTheDiagram = false;
                    currentDAOInterfaceDiagElement = DAOInterface;
                    break;
                }
            }

            if (DAONotInTheDiagram){
                // Add the current DAO Interface class to the diagram
                final IDiagramElement DAOInterfaceAdded = DiagramElementUtils.createAuxiliaryViewInDiagram(context.getDiagram(), clazzPackage.getChildById(clazz.getId()));
                final IModelElement DAOAddedElementModel = DAOInterfaceAdded.getModelElement();
                Logger.log(Level.INFO, "DAO Interface {0} was added to the diagram.",
                        new Object[] {DAOAddedElementModel.getName(), DAOAddedElementModel.getId(),
                                String.valueOf(DAOInterfaceAdded.isMasterView())});

                // Change the position of the class in the diagram
                int multi = packagesAndChildrenNum.get(DAOAddedElementModel.getParent());
                packagesAndChildrenNum.replace(DAOAddedElementModel.getParent(), multi+1);
                int len = DAOAddedElementModel.getParent().getDiagramElements().length;
                DAOInterfaceAdded.setX(DAOAddedElementModel.getParent().getDiagramElements()[len-1].getX() + 20 + (70*multi));
                DAOInterfaceAdded.setY(DAOAddedElementModel.getParent().getDiagramElements()[len-1].getY() + 30);

                ModelElementUtils.changeInterfaceBall(DAOAddedElementModel, true);
                ModelElementUtils.changeFillColor(DAOAddedElementModel, FrameWebClass.DAO_INTERFACE.getColor());

                DAOInterfaceAdded.setWidth(30);
                DAOInterfaceAdded.setHeight(30);

                currentDAOInterfaceDiagElement = DAOInterfaceAdded;
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
                // Add the association to the class.
                final IDiagramElement associationAdded = DiagramElementUtils.createAssociation(context.getDiagram(),
                        context.getDiagramElement(), currentDAOInterfaceDiagElement);
                final IModelElement associationAddedElementModel = associationAdded.getModelElement();
                final IAssociation associationElement = (IAssociation) associationAddedElementModel;
                final IModelElement toElement = associationElement.getTo();
                final IModelElement fromElement = associationElement.getFrom();

                if(associationElement.getFromEnd() instanceof IAssociationEnd){
                    final IAssociationEnd associationEnd = (IAssociationEnd) associationElement.getFromEnd() ;
                    associationEnd.setNavigable(IAssociationEnd.NAVIGABLE_NAV_UNSPECIFIED);
                    Logger.log(Level.INFO, "Association was added to the diagram, From: {0}, To: {1}.",
                            new Object[] {fromElement.getName(), toElement.getName(), associationAdded.getShapeType(),
                                    associationEnd.getNavigable()});
                }
            }
        }
    }


    /**
     * A dialog handler that is used by Visual Paradigm to open a dialog based on a panel with its
     * contents.
     */
    protected class SelectDAODialogHandler implements IDialogHandler {
        /** Called once before the dialog is shown. Should return the contents of the dialog. */
        @Override
        public Component getComponent() {
            selectDAOPanel = new SelectDAOPanel(AddFrameWebDependencyToClassContextController.this);
            return selectDAOPanel;
        }

        /** Called after getComponent(), dialog is created but not shown. Sets outlook of the dialog. */
        @Override
        public void prepare(IDialog dialog) {
            selectDAODialog = dialog;
            selectDAODialog.setTitle("Add FrameWeb Dependency - DAO Interface");
            selectDAODialog.setModal(false);
            selectDAODialog.setResizable(true);
            selectDAODialog.setSize(400, 300);
            selectDAOPanel.setContainerDialog(selectDAODialog);
        }

        /** Called when the dialog is shown. */
        @Override
        public void shown() {}

        /** Called when the dialog is closed by the user clicking on the close button of the frame. */
        @Override
        public boolean canClosed() {
            FrameWebPlugin.instance().setSelectDAODialogOpen(false);
            return true;
        }
    }
}

