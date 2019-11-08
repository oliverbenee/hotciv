package hotciv.visual;

import hotciv.factory.*;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.standard.GameImpl;
import hotciv.standard.GameObserverImpl;
import hotciv.stub.StubGame1;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

public class ShowSemiCiv {
  public static void main(String[] args) {
    Game game = new GameImpl(new SemiCivFactory());
    GameObserver observer = new GameObserverImpl();
    game.addObserver(observer);

    DrawingEditor editor =
            new MiniDrawApplication( "Paint the HotCiv world map...",
                    new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click and drag any item to see Game's proper response.");

    // TODO: Replace the setting of the tool with your CompositionTool implementation.
    editor.setTool(new CompositionTool(editor, game));
  }
}
