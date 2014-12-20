package events;

/**
 * Created by Lemming on 30.07.2014.
 */
public abstract class KeyEvent extends Event{
    public int keycode;

    public KeyEvent(int keycode) {
        this.keycode = keycode;
    }

    public static class KeyIsDownEvent extends KeyEvent {

        public KeyIsDownEvent(int keycode) {
            super(keycode);
        }
    }

    public static class KeyDownEvent extends KeyEvent {

        public KeyDownEvent(int keycode) {
            super(keycode);
        }
    }
}
