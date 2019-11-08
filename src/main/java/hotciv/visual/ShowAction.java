package hotciv.visual;

import hotciv.factory.SemiCivFactory;
import hotciv.standard.GameImpl;
import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/** Template code for exercise FRS 36.43.

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
public class ShowAction {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Shift-Click unit to invoke its action",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Shift-Click on unit to see Game's performAction method being called.");

    // TODO: Replace the setting of the tool with your ActionTool implementation.
    editor.setTool(new ActionTool(editor, game));
  }

  static class ActionTool extends NullTool {
    private Game game;
    private DrawingEditor editor;
    private Position unitPosition;

    public ActionTool(DrawingEditor editor, Game game){
      this.editor = editor;
      this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y){}

    @Override
    public void mouseUp(MouseEvent e, int x, int y){
      unitPosition = GfxConstants.getPositionFromXY(x, y);
      System.out.print("Clicked " + unitPosition);
      System.out.println("Checking to do unit action");
      boolean isFigure = editor.drawing().findFigure(x,y) != null;
      boolean isOwnFigure = game.getUnitAt(unitPosition).getOwner().equals(game.getPlayerInTurn());
      if (isFigure && isOwnFigure) {
        super.mouseDown(e, x, y);
        System.out.println("Friendly unit found");
      } else {
        System.out.println("No unit is there");
        return;
      }
      boolean isShiftDown = e.isShiftDown();
      if(isShiftDown){
        System.out.println("Unit action will be performed..");
        game.performUnitActionAt(unitPosition);
      }
      System.out.println("Tried to perform unit action at " + unitPosition);
    }
  }
}
