# Wraeclast-Online
Currently in first Alpha version, 
Wraeclast-Online is a Proof of Concept for bringing any website into Path of Exile _pseudo_ in-game.

This is achieved by using JavaFX's WebView contained in an "undecorated" window.
The example below loads this url: https://poe-trademacro.github.io/awesome.html

![cappp1](https://cloud.githubusercontent.com/assets/75921/19652016/c72baec2-9a41-11e6-8721-e8a3cb4b10c5.jpg)

Hotkey `v` is used to toggle the Wraeclast-Online Browser (**WOB**). This hotkey is global like in AutoHotKey (AHK).
A system tray is provided, double click to open. Right click for options. You can set a different url here.

On the commandline, you can also set the default url to be used:

    java -jar poe-wol-0.1.jar http://reddit.com/r/pathofexile
    
On the sample awesome.html, you'll find a couple of useful callbacks:
 
 * onJavaLoadSucceed - this is called when WOB has finished loading the page, the bridge object is passed
 * onPoEItemCopy - called to provide the clipboard data, will be moved over to bridge
 * bridge.setSize(w, h) - call this to resize WOB
 * bridge.exit - force exit WOB

![cappp2](https://cloud.githubusercontent.com/assets/75921/19652015/c72a8a06-9a41-11e6-8be9-214a9454cfe2.jpg)

I'm hoping to bring useful functionality like:
 
  * Damage/Aura calculators
  * Build Guide explorer
  * Items for Trade Search
  * Uniques Viewer
  * The most useful wiki pages
  * Beginner Guide
  * Features that use the poe client log.txt
  * Features that use the clipboard data

Wraeclast-Online is fan made and is not affiliated with Grinding Gear Games. Wraeclast-Online is 100% free, no ads, gplv3 licensed, and supports Free Software Movement http://fsf.org.