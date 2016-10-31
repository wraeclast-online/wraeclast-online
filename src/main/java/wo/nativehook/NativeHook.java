/*
 *     This file is part of wraelclast-online.
 *
 *     wraelclast-online is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     wraelclast-online is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with wraelclast-online.  If not, see <http://www.gnu.org/licenses/>.
 */

package wo.nativehook;

import javafx.application.Platform;
import lombok.Getter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * TODO provide a better API
 *
 * Created 10/31/2016.
 */
@Getter
public class NativeHook implements NativeKeyListener {

    private boolean ctrlPressed = false;
    private boolean altPressed = false;
    private Callback callbackOnV;
    private Callback callbackOnEsc;

    public interface Callback {
        void callback();
    }

    public void onKeyV(Callback callback) {
        this.callbackOnV = callback;
    }

    public void onKeyEsc(Callback callback) {
        this.callbackOnEsc = callback;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            callbackOnEsc.callback();
        }

        if (isCtrl(e)) {
            ctrlPressed = true;
        }

        if (isAlt(e)) {
            altPressed = true;
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_V) {
            callbackOnV.callback();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
    private boolean isCtrl(NativeKeyEvent e) {
        return e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R;
    }
    private boolean isAlt(NativeKeyEvent e) {
        return e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (isCtrl(e)) {
            ctrlPressed = false;
        }
        if (isAlt(e)) {
            altPressed = false;
        }
    }
}
