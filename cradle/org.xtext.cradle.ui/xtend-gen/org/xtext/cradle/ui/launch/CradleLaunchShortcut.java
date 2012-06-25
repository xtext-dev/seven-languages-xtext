package org.xtext.cradle.ui.launch;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.ui.editor.XbaseEditor;
import org.xtext.cradle.TaskExtensions;
import org.xtext.cradle.cradle.CradleFile;
import org.xtext.cradle.cradle.Task;
import org.xtext.cradle.ui.launch.LaunchConfigurationInfo;

@SuppressWarnings("all")
public class CradleLaunchShortcut implements ILaunchShortcut {
  public final static String BUNDLE_ID = "org.xtext.cradle.ui";
  
  public void launch(final ISelection selection, final String mode) {
    MessageDialog.openError(null, "Unsupported Launch Selection.", 
      "Please open the file inside an editor to launch a task.");
  }
  
  public String findTask(final XtextResource res, final int offset) {
    boolean _lessThan = (offset < 0);
    if (_lessThan) {
      return null;
    }
    IParseResult _parseResult = res.getParseResult();
    ICompositeNode _rootNode = _parseResult.getRootNode();
    final ILeafNode start = NodeModelUtils.findLeafNodeAtOffset(_rootNode, offset);
    boolean _isHidden = start.isHidden();
    if (_isHidden) {
      IParseResult _parseResult_1 = res.getParseResult();
      ICompositeNode _rootNode_1 = _parseResult_1.getRootNode();
      Iterable<ILeafNode> _leafNodes = _rootNode_1.getLeafNodes();
      final List<ILeafNode> list = IterableExtensions.<ILeafNode>toList(_leafNodes);
      final int index = list.indexOf(start);
      IntegerRange _upTo = new IntegerRange(index, 0);
      final Function1<Integer,Boolean> _function = new Function1<Integer,Boolean>() {
          public Boolean apply(final Integer it) {
            ILeafNode _get = list.get((it).intValue());
            boolean _isHidden = _get.isHidden();
            boolean _not = (!_isHidden);
            return Boolean.valueOf(_not);
          }
        };
      final Integer first = IterableExtensions.<Integer>findFirst(_upTo, _function);
      int _size = list.size();
      int _minus = (_size - 1);
      IntegerRange _upTo_1 = new IntegerRange(index, _minus);
      final Function1<Integer,Boolean> _function_1 = new Function1<Integer,Boolean>() {
          public Boolean apply(final Integer it) {
            ILeafNode _get = list.get((it).intValue());
            boolean _isHidden = _get.isHidden();
            boolean _not = (!_isHidden);
            return Boolean.valueOf(_not);
          }
        };
      final Integer last = IterableExtensions.<Integer>findFirst(_upTo_1, _function_1);
      ILeafNode _get = list.get((first).intValue());
      EObject _semanticElement = _get.getSemanticElement();
      final Task task1 = EcoreUtil2.<Task>getContainerOfType(_semanticElement, Task.class);
      ILeafNode _get_1 = list.get((last).intValue());
      EObject _semanticElement_1 = _get_1.getSemanticElement();
      final Task task2 = EcoreUtil2.<Task>getContainerOfType(_semanticElement_1, Task.class);
      boolean _equals = Objects.equal(task1, task2);
      if (_equals) {
        return task1.getName();
      }
    } else {
      EObject _semanticElement_2 = start.getSemanticElement();
      Task _containerOfType = EcoreUtil2.<Task>getContainerOfType(_semanticElement_2, Task.class);
      return _containerOfType.getName();
    }
    return null;
  }
  
  public void launch(final IEditorPart editor, final String mode) {
    if ((editor instanceof XbaseEditor)) {
      final XbaseEditor xbe = ((XbaseEditor) editor);
      int _switchResult = (int) 0;
      ISelectionProvider _selectionProvider = xbe.getSelectionProvider();
      ISelection _selection = _selectionProvider.getSelection();
      final ISelection it = _selection;
      boolean _matched = false;
      if (!_matched) {
        if (it instanceof ITextSelection) {
          final ITextSelection _iTextSelection = (ITextSelection)it;
          _matched=true;
          int _offset = _iTextSelection.getOffset();
          _switchResult = _offset;
        }
      }
      if (!_matched) {
        int _minus = (-1);
        _switchResult = _minus;
      }
      final int offset = _switchResult;
      IEditorInput _editorInput = xbe.getEditorInput();
      if ((_editorInput instanceof IFileEditorInput)) {
        IEditorInput _editorInput_1 = xbe.getEditorInput();
        IFile _file = ((IFileEditorInput) _editorInput_1).getFile();
        IProject _project = _file.getProject();
        final String project = _project.getName();
        IXtextDocument _document = xbe.getDocument();
        final Function1<XtextResource,LaunchConfigurationInfo> _function = new Function1<XtextResource,LaunchConfigurationInfo>() {
            public LaunchConfigurationInfo apply(final XtextResource it) {
              LaunchConfigurationInfo _xblockexpression = null;
              {
                EList<EObject> _contents = it.getContents();
                Iterable<CradleFile> _filter = Iterables.<CradleFile>filter(_contents, CradleFile.class);
                final CradleFile file = IterableExtensions.<CradleFile>head(_filter);
                String _javaClassName = file==null?(String)null:TaskExtensions.getJavaClassName(file);
                String _findTask = CradleLaunchShortcut.this.findTask(it, offset);
                LaunchConfigurationInfo _launchConfigurationInfo = new LaunchConfigurationInfo(project, _javaClassName, _findTask);
                _xblockexpression = (_launchConfigurationInfo);
              }
              return _xblockexpression;
            }
          };
        final LaunchConfigurationInfo info = _document.<LaunchConfigurationInfo>readOnly(new IUnitOfWork<LaunchConfigurationInfo,XtextResource>() {
            public LaunchConfigurationInfo exec(XtextResource state) {
              return _function.apply(state);
            }
        });
        this.launch(mode, info);
        return;
      }
    }
    MessageDialog.openError(null, "Wrong editor kind.", "");
  }
  
  public void launch(final String mode, final LaunchConfigurationInfo info) {
    String _task = info.getTask();
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_task);
    if (_isNullOrEmpty) {
      MessageDialog.openError(null, "Launch Error", "Could not determine the task that should be executed.");
    } else {
      String _clazz = info.getClazz();
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(_clazz);
      if (_isNullOrEmpty_1) {
        MessageDialog.openError(null, "Launch Error", "Could not determine the class that should be executed.");
      } else {
        String _project = info.getProject();
        boolean _isNullOrEmpty_2 = StringExtensions.isNullOrEmpty(_project);
        if (_isNullOrEmpty_2) {
          MessageDialog.openError(null, "Launch Error", "Could not determine the project that should be executed.");
        } else {
          try {
            DebugPlugin _default = DebugPlugin.getDefault();
            ILaunchManager _launchManager = _default.getLaunchManager();
            final ILaunchConfiguration[] configs = _launchManager.getLaunchConfigurations();
            final Function1<ILaunchConfiguration,Boolean> _function = new Function1<ILaunchConfiguration,Boolean>() {
                public Boolean apply(final ILaunchConfiguration it) {
                  boolean _configEquals = info.configEquals(it);
                  return Boolean.valueOf(_configEquals);
                }
              };
            ILaunchConfiguration _findFirst = IterableExtensions.<ILaunchConfiguration>findFirst(((Iterable<ILaunchConfiguration>)Conversions.doWrapArray(configs)), _function);
            ILaunchConfiguration _createConfiguration = info.createConfiguration();
            final ILaunchConfiguration config = ObjectExtensions.<ILaunchConfiguration>operator_elvis(_findFirst, _createConfiguration);
            DebugUITools.launch(config, mode);
          } catch (final Throwable _t) {
            if (_t instanceof CoreException) {
              final CoreException e = (CoreException)_t;
              String _message = e.getMessage();
              MessageDialog.openError(null, "Problem running workflow.", _message);
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
        }
      }
    }
  }
}
