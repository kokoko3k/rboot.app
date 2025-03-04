# ((•)) Jdsp4Rp5 (temp root) app 
### *JamesDSP in temporary root mode for Retroid Pocket 5*

**Retroid Mini** is not officially supported, please see [here](https://github.com/kokoko3k/rboot.app/issues/4)

<br>

### The problem:
Retroid Pocket 5 speakers sounds really bad out of the box.
Unfortunately, rootless equalizer solutions cannot be applied to low latency
applications like most of the emulators, so a root solution is needed,
but rooting the Retroid Pocket 5 voids its warranty, so we'll use temporary root permissions just for the task, and a reboot will restore your console state.

### Benefit/what to expect:
* A dramatic improvement in sound quality from the speakers.
* A fairly linear frequency response from 400hz to 10khz.
obtained through profiling the sound via professional calibration 
microphone while the hands were "on" the console, That's the intended use.

### What NOT to expect:
* You won't hear anything lower than 400hz, nor did I bothered to
make it possible, since that would have lowered the volume
to absymal levels. RP5's little speakers are just not up to the task.

### Drawbacks:
* The audio output latency will increase by about 70ms.
* The output volume will be lower.
* CPU usage will be slightly higher (<1%).

-----------------------------
### **ELI5, OVER-DETAILED HOW TO:**
-----------------------------
![ ](https://github.com/kokoko3k/rboot.app/blob/main/repo_images/shot1.jpg?raw=true)

* Download and install rbootapp.apk
from the release/assets page
* Open the app
* Allow Jdsp4rp5 (temp root) to sent you notifications -> allow.

* Tap on "Enable JDSP"

* Tap on "Install bundled JamesDSP..."

	When asked:
	* Confirm to "Open with package installer"
	* Install unknown apps: -> allow from this source.
	* Finally confirm JamesDSP installation
	

* Open the newly installed application
	* Allow JamesDSP to send you notifications

* Now let's configure JamesDSP for Retroid pocket 5 speakers:
	* Set limiter threshold to -0.10dB
	* Set limiter release to 500.00ms
	* Set Post gain to 15.00dB
	* Enable Arbitrary response equalizer
	* Click on the graph and tap "Edit as string"
	* Paste this magic string:<br>
	```
  	GraphicEQ: 480 0; 600 -5; 700 -15; 850 -10; 1200 -10;
  	1670 -15; 2160 -18; 2800 -18; 3800 -28; 5000 -8; 7000 0;
	```
	* Tap the cog icon in the lower/left side of the screen
	* Select Audio processing, enable "Legacy mode".
	* If needed, turn on JamesDSP by tapping the "Power on" icon in the center/lower part of the screen.
	  
## You're done! <br>
* Now go back to the Jdsp4rp5 (temp root) app select if you want or not JamesDSP to start at every boot. <br>
* Reboot to verify everything works correctly (give the app a few secs to setup everything).


### Credits/License:
* This package/app and the scripts made by kokoko3k are released under the 
The GNU General Public License v2.0.
* Provided libjamesdsp.so by James Fung 
* Provided JamesDSPManagerThePBone.apk by James Fung
* Thanks to ShadoV from JamesDSP [Community] Telegram channel
for insights and support over audio_effects.xml
* Thanks to Sayrune from RP5's discord channel
for support on audio_policy_configuration.xml
* Audio analysis done via: Room EQ Wizard https://www.roomeqwizard.com
