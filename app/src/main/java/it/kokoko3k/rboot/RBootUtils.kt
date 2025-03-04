package it.kokoko3k.rboot

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.os.Build
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider

object RbootUtils {

    const val TAG = "Rboot"

    fun StartRboot(context: Context) {

        val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val bootscriptdir = File(documentsDir, "rboot")

        if (!bootscriptdir.exists()) {
            bootscriptdir.mkdirs()
        }

        val bootscriptname = "rboot.sh"
        val bootscriptfullpath = File(bootscriptdir, bootscriptname).absolutePath

        var cmd = "sh " + bootscriptfullpath + " &> " + bootscriptfullpath + ".log"
        Log.d(TAG, "starting rboot with cmd= " + cmd)

        //execute it:
        val rootexec = RootExec() // get instance
        val result = rootexec.executeAsRoot(cmd)
    }
}