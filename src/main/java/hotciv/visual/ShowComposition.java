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
      new MiniDrawApplication( "Click and/or drag any item to see all game actions",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click and drag any item to see Game's proper response.");

    // TODO: Replace the setting of the tool with your CompositionTool implementation.
    editor.setTool( new CompositionTool(editor, game) );
  }

  static class CompositionTool implements Tool {
    private Game game;
    private DrawingEditor editor;
    protected Tool childTool, cachedNullTool;

    public CompositionTool(DrawingEditor editor, Game game){
      this.editor = editor;
      this.game = game;
      childTool = cachedNullTool = new NullTool();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y){
      Position endOfTurnShield = GfxConstants.getPositionFromXY(x,y);
      Position unitPosition = GfxConstants.getPositionFromXY(x,y);

      if(endOfTurnShield.equals(GfxConstants.getPositionFromXY(GfxConstants.TURN_SHIELD_X,GfxConstants.TURN_SHIELD_Y))){
        cachedNullTool = new EndOfTurnTool(editor, game);
      }
      if(game.getUnitAt(unitPosition) != null && e.isShiftDown()){
        cachedNullTool = new ShowAction.ActionTool(editor, game);
      }
      childTool.mouseDown(e, x, y);
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {

    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y){
      childTool.mouseMove(e, x, y);
      cachedNullTool.mouseMove(e,x,y);
    }

    @Override
    public void keyDown(KeyEvent e, int key) {

    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y){
      childTool.mouseUp(e,x,y);
      cachedNullTool.mouseUp(e,x,y);
    }
  }
}