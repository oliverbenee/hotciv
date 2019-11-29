package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.CivDrawing;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CompositionTool implements Tool {
  private Game game;
  private Position startPosition;
  private Position currentPosition;
  private DrawingEditor editor;
  private Tool childTool, cachedNullTool;
  private Tool actionTool, endOfTurnTool, moveTool, setFocusTool;
  private Position refreshBtn;

  public CompositionTool(DrawingEditor editor, Game game){
    this.editor = editor;
    this.game = game;
    childTool = cachedNullTool = new NullTool();
    actionTool = new ShowAction.ActionTool(editor, game);
    endOfTurnTool = new EndOfTurnTool(editor,game);
    moveTool = new MoveUnitTool(editor, game);
    setFocusTool = new SetFocusTool(editor, game);
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y){
    endOfTurnTool.mouseDown(e,x,y);
    moveTool.mouseDown(e,x,y);
    setFocusTool.mouseDown(e,x,y);
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    moveTool.mouseDrag(e,x,y);
  }

  @Override
  public void mouseMove(MouseEvent e, int x, int y){
  }

  @Override
  public void keyDown(KeyEvent e, int key) {

  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y){
    moveTool.mouseUp(e,x,y);
    actionTool.mouseUp(e,x,y);
    setFocusTool.mouseUp(e,x,y);
    startPosition = GfxConstants.getPositionFromXY(x, y);

    if(x > GfxConstants.REFRESH_BUTTON_X && x < GfxConstants.REFRESH_BUTTON_X + 45
            && GfxConstants.REFRESH_BUTTON_Y < y && y < GfxConstants.REFRESH_BUTTON_Y + 18){
      editor.showStatus("Requested refresh of the map. Might take a while..");
      editor.drawing().requestUpdate();
    }
  }
}
