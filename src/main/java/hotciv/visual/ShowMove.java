package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;
import minidraw.standard.handlers.StandardRubberBandSelectionStrategy;

/** Template code for exercise FRS 36.39.

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
public class ShowMove {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Move any unit using the mouse",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Move units to see Game's moveUnit method being called.");

    // TODO: Replace the setting of the tool with your UnitMoveTool implementation.
    editor.setTool( new MoveUnitTool(editor, game));
  }
}

class MoveUnitTool extends SelectionTool {
  private Game game;
  private Position startPosition;
  private Position currentPosition;
  private boolean hasUnit;

  public MoveUnitTool(DrawingEditor editor, Game game) {
    super(editor);
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    startPosition = GfxConstants.getPositionFromXY(x, y);
    System.out.print("Clicked " + startPosition);
    boolean isFigure = editor.drawing().findFigure(x,y) != null;
    if(isFigure) {
      System.out.println("Unit found");
    } else {
      return;
    }
    boolean isOwnFigure = game.getUnitAt(startPosition).getOwner().equals(game.getPlayerInTurn());
    if (isFigure && isOwnFigure) {
      super.mouseDown(e, x, y);
      hasUnit = true;
      System.out.println("A movable unit is there!");
    } else {
      System.out.println("no movable unit is there");
      System.out.println(game.getUnitAt(startPosition).getOwner() + " is owner");
      System.out.println(game.getPlayerInTurn() + " is in turn");
    }
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y){
    super.mouseDrag(e, x, y);
  }

  public void mouseUp(MouseEvent e, int x, int y){
    currentPosition = GfxConstants.getPositionFromXY(x,y);
    super.mouseUp(e, x, y);
    boolean isValidMove = game.moveUnit(startPosition, currentPosition);
    if(isValidMove){
      editor.showStatus(game.getUnitAt(currentPosition).getTypeString() + "Moved from " + startPosition + " to " + currentPosition + ".");
    }
  }
}