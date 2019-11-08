package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/** Template code for exercise FRS 36.44.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class ShowComposition {

  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor =
            new MiniDrawApplication("Click and/or drag any item to see all game actions",
                    new HotCivFactory4(game));
    editor.open();
    editor.showStatus("Click and drag any item to see Game's proper response.");

    // TODO: Replace the setting of the tool with your CompositionTool implementation.
    editor.setTool(new CompositionTool(editor, game));
  }
}

  class CompositionTool implements Tool {
    private Game game;
    private Position startPosition;
    private Position currentPosition;
    private DrawingEditor editor;
    private Tool childTool, cachedNullTool;
    private Tool actionTool, endOfTurnTool, moveTool, setFocusTool;

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
      actionTool.mouseDown(e, x, y);
      endOfTurnTool.mouseDown(e, x, y);
      moveTool.mouseDown(e, x, y);
      setFocusTool.mouseDown(e, x, y);
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
      actionTool.mouseDrag(e, x, y);
      moveTool.mouseDrag(e, x, y);
      setFocusTool.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y){
    }

    @Override
    public void keyDown(KeyEvent e, int key) {

    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y){ ;
      moveTool.mouseUp(e, x, y);
      setFocusTool.mouseUp(e,x,y);
    }
  }