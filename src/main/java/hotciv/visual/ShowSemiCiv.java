package hotciv.visual;

import hotciv.factory.*;
import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.stub.StubGame1;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

public class ShowSemiCiv {
  public static void main(String[] args) {

    GameImpl game = new GameImpl(new AlphaCivFactory());

    DrawingEditor editor =
            new MiniDrawApplication( "Paint the HotCiv world map...",
                    new HotCivFactory4(game) );
    editor.open();
    editor.setTool( new ShowComposition.CompositionTool(editor, game) );
  }
}
