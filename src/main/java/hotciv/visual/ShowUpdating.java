package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.event.*;

import hotciv.framework.*;
import hotciv.stub.*;

/** Show how GUI changes can be induced by making
    updates in the underlying domain model.

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
public class ShowUpdating {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click anywhere to see Drawing updates",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.setTool( new UpdateTool(editor, game) );

    editor.showStatus("Click anywhere to state changes reflected on the GUI");
                      
    // Try to set the selection tool instead to see
    // completely free movement of figures, including the icon

    // editor.setTool( new SelectionTool(editor) );
  }
}

/** A tool that simply 'does something' new every time
 * the mouse is clicked anywhere; as a visual testing
 * of the 'from Domain to GUI' data flow is coded correctly*/
class UpdateTool extends NullTool {
  private Game game;
  private DrawingEditor editor;
  public UpdateTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }
  private int count = 0;
  public void mouseDown(MouseEvent e, int x, int y) {
    switch(count) {
    // Move archer to (1,1).
    case 0: {
      editor.showStatus( "State change: Moving archer to (1,1)" );
      game.moveUnit( new Position(2,0), new Position(1,1) );
      break;
    }
    // Move archer to (2,2).
    case 1: {
      editor.showStatus( "State change: Moving archer to (2,2)" );
      game.moveUnit( new Position(1,1), new Position(2,2) );
      break;
    }
    // End turn (player blue gets in turn).
    case 2: {
      editor.showStatus( "State change: End of Turn (over to blue)" );
      game.endOfTurn();
      break;
    }
    // Inspect blue unit at (2,3).
    case 3: {
      editor.showStatus( "State change: Inspecting unit at (2,3)");
      game.setTileFocus(new Position(2,3));
    }
    // End turn (player red gets in turn). Age update.
    case 4: {
      editor.showStatus( "State change: End of Turn (over to red). Check, that age got updated!" );
      game.endOfTurn();
      break;
    }
    // End turn (player blue gets in turn). No age update.
    case 5: {
      editor.showStatus( "State change: End of Turn (over to blue). Check, that age did not get updated!" );
      game.endOfTurn();
      break;
    }
    // End turn 80 times to go to AD.
    case 6: {
      editor.showStatus( "State change x80: End of turn. Check, that it goes to AD. Blue should be in turn. ");
      for(int i=0; i<79; i++){
        game.endOfTurn();
      }
      break;
    }
    // End turn (player red gets in turn). No age update.
      case 7: {
        editor.showStatus( "State change: End of Turn (over to red). Check, that age got updated!" );
        game.endOfTurn();
        break;
      }
    // Inspect unit at (3,2).
      case 8: {
        editor.showStatus( "State change: Inspect blue Unit at (3,2)");
        game.setTileFocus(new Position(2,3));
      }
      // TODO: Add more state changes for other things to test
    default: {
      editor.showStatus("No more changes in my list...");
    }
    }
    count ++;
  }
}

